package core.managers;

import api.apps.android.Android;
import core.ADB;
import core.Resources;
import core.Timer;
import core.Util;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

@SuppressWarnings("unchecked")
public class DriverManager {

    public static String remoteConfig = Util.KOBITON;

    //private static Util util = new Util();

    private static HashMap<String, URL> hosts;
    private static String unlockPackage = "io.appium.unlock";
    private static Properties prop = new Properties();
    private static String urlNubiToConfirm = "http://tunubi.app/registration/confirm/";
    private static ADB adb;
    private static DriverService service;
    private static String deviceID;
    private static int portUsed;
    private static int port = 820;
    private static String device;


    public static int EXPLICIT_WAIT_TIME;
    public static int IMPLICIT_WAIT_TIME;
    public static int DEFAULT_WAIT_TIME;
    public static String APP_NAME;
    public static String BASE_PKG;
    private static String APPIUM_PORT;
    public static String PLATFORM_NAME;
    public static String PLATFORM_VERSION;
    public static String AUTOMATION_NAME;
    public static String APK_VERSION;
    public static String DEVICE_NAME;

    private static final Logger logger = LogManager.getLogger();

    public static String getDeviceId() {
        return deviceID;
    }


    public static void loadConfigProp(String propertyFileName) throws IOException {

        FileInputStream fis = new FileInputStream(ServerManager.getWorkingDir() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "properties" + File.separator + propertyFileName);
        prop.load(fis);

        EXPLICIT_WAIT_TIME = Integer.parseInt(prop.getProperty("explicit.wait"));
        DEFAULT_WAIT_TIME = Integer.parseInt(prop.getProperty("default.wait"));
        IMPLICIT_WAIT_TIME = Integer.parseInt(prop.getProperty("implicit.wait"));
        APK_VERSION = prop.getProperty("apk.version");
        APP_NAME = prop.getProperty("app.path");
        APPIUM_PORT = prop.getProperty("appiumserver.port");
        PLATFORM_NAME = prop.getProperty("platform.name");
        //DEVICE_NAME = prop.getProperty("device.name");
        PLATFORM_VERSION = prop.getProperty("platform.version");
        AUTOMATION_NAME = prop.getProperty("automationName");
    }

    private static DesiredCapabilities getCaps(String deviceID) {
        DesiredCapabilities caps = new DesiredCapabilities();
        adb = new ADB(deviceID);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, adb.getAndroidVersionAsString());
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, DriverManager.PLATFORM_NAME);
        // caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, DriverManager.AUTOMATION_NAME);
        caps.setCapability(MobileCapabilityType.APP, ServerManager.getWorkingDir() + DriverManager.APP_NAME);
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceID);
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 10000);
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);

        caps.setCapability("appPackage", "com.tunubi.wallet.qa");
        caps.setCapability("appActivity", "com.nubi.featuresplash.view.SplashActivity");

        caps.setCapability("androidInstallTimeout", 480000);
        caps.setCapability("adbExecTimeout", 480000);

        caps.setCapability("ignoreUnimportantViews", true);
        caps.setCapability("noReset", false);
        caps.setCapability("isHeadless", true);
        caps.setCapability("avdArgs", "-no-window");
        //caps.setCapability("unicodeKeyboard", true);
        // caps.setCapability("resetKeyboard", true);
        return caps;
    }

    private static URL host(String deviceID) throws MalformedURLException {
        if (hosts == null) {
            hosts = new HashMap<String, URL>();
            hosts.put(deviceID, new URL("http://127.0.0.1:4723/wd/hub"));
        }
        return hosts.get(deviceID);
    }

    private static ArrayList<String> getAvailableDevices() {
        ArrayList<String> availableDevices = new ArrayList<String>();
        ArrayList connectedDevices = ADB.getConnectedDevices();

        for (Object connectedDevice : connectedDevices) {
            String device = connectedDevice.toString();
            ArrayList apps = new ADB(device).getInstalledPackages();
            if (!apps.contains(unlockPackage)) {
                if (useDevice(deviceID))
                    availableDevices.add(device);
                else
                    logger.warn("Dispositivo: " + device + " ya esta en uso");
            } else {
                if (useDevice(deviceID))
                    availableDevices.add(device);
                else
                    logger.warn("Dispositivo: " + device + " ya esta en uso");
            }
        }
        if (availableDevices.size() == 0) throw new RuntimeException("No device available for test");
        return availableDevices;
    }

    private static DriverService createService() throws MalformedURLException {
        service = new AppiumServiceBuilder()
                //.usingDriverExecutable(new File(nodeJS))
                //.withAppiumJS(new File(appiumJS))
                .withIPAddress(host(deviceID).toString().split(":")[1].replace("//", ""))
                .usingPort(Integer.parseInt(host(deviceID).toString().split(":")[2].replace("/wd/hub", "")))
                //.withArgument(, "120")
                //.withArgument(Arg.LOG_LEVEL, "warn")
                .build();
        portUsed = Integer.parseInt(host(deviceID).toString().split(":")[2].replace("/wd/hub", ""));
        return service;
    }

    public static boolean checkIfServerIsRunnning(int port) {

        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            //If control comes here, then it means that the port is in use
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }

    public static void createDriver(String propertiesFileName) throws MalformedURLException {

        remoteConfig = propertiesFileName;

        if (propertiesFileName.equals(Util.LOCAL)) {
            ArrayList<String> devices = getAvailableDevices();
            for (String device : devices) {
                try {
                    deviceID = device;
                    Android.driver = new AndroidDriver(host(device), getCaps(device));
                    Android.wait = new WebDriverWait(Android.driver,30);
                    Android.adb = new ADB(device);
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        } else {
            try {
                Properties properties = Util.propertiesConfiguration(propertiesFileName);
                if (properties != null) {
                    hosts = new HashMap<String, URL>();
                    deviceID = properties.getProperty("deviceID");
                    hosts.put(deviceID, new URL(properties.getProperty("serverURL")));
                    Android.driver = new AndroidDriver(hosts.get(deviceID), getRemoteCapabilities(properties));
                    Android.wait = new WebDriverWait(Android.driver,30);
                    Android.adb = new ADB(device);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean useDevice(String deviceID) {
        try {
            JSONObject json = Resources.getQueue();
            if (json.containsKey(deviceID)) {
                JSONObject deviceJson = (JSONObject) json.get(deviceID);
                long time = (long) deviceJson.get("queued_at");
                int diff = Timer.getDifference(time, Timer.getTimeStamp());
                if (diff >= 30) return true;
                else return false;
            } else return true;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void queueUp() {
        try {
            //MyLogger.log.info("Queueing Up: "+deviceID);
            JSONObject json = new JSONObject();
            json.put("queued_at", Timer.getTimeStamp());
            JSONObject jsonQueue = Resources.getQueue();
            jsonQueue.put(deviceID, json);
            //MyLogger.log.info("JSON Queue: "+jsonQueue);
            ServerManager.write(new File(Resources.QUEUE), jsonQueue.toString());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void gracePeriod() {
        int waitTime = 0;
        try {
            JSONObject json = Resources.getQueue();
            Set keys = json.keySet();

            JSONObject ourDeviceJson = (JSONObject) json.get(deviceID);
            json.remove(deviceID);
            long weQueuedAt = (long) ourDeviceJson.get("queued_at");

            for (Object key : keys) {
                JSONObject deviceJson = (JSONObject) json.get(key);
                long theyQueuedAt = (long) deviceJson.get("queued_at");
                //If we did not queue first we need to wait for the other device to initialize driver so there is no collision
                if (weQueuedAt > theyQueuedAt) {
                    //But only if device queued first and recently, otherwise we can assume device was already initialized or no longer being used
                    int diff = Timer.getDifference(theyQueuedAt, Timer.getTimeStamp());
                    if (diff < 50) {
                        //MyLogger.log.info("Device: "+key+" queued first, I will need to give it extra time to initialize");
                        waitTime += 15;
                    }
                }
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void leaveQueue() {
        try {
            JSONObject jsonQueue = Resources.getQueue();
            jsonQueue.remove(deviceID);
            ServerManager.write(new File(Resources.QUEUE), jsonQueue.toString());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public static void killDriver() {
        if (Android.driver != null) {
            Android.driver.quit();
            //service.stop();
            //Android.adb.uninstallApp("com.tunubi.wallet");
        } else {
            logger.warn("Driver was not initialized");
        }
    }

    private static DesiredCapabilities getRemoteCapabilities(Properties properties) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        /**
         * KOBITON
         */
        // Important ones
        capabilities.setCapability(MobileCapabilityType.APP, properties.getProperty("deviceName"));
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getProperty("deviceID"));

        // The generated session will be visible to you only.
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, properties.getProperty("capability.platformVersion"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, properties.getProperty("capability.platformName"));
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, Integer.parseInt(properties.getProperty("capability.new_command_timeout")));
        capabilities.setCapability("sessionName", properties.getProperty("capability.sessionName"));
        capabilities.setCapability("sessionDescription", properties.getProperty("capability.sessionDescription"));
        capabilities.setCapability("deviceOrientation", properties.getProperty("capability.deviceOrientation"));
        capabilities.setCapability("captureScreenshots", Boolean.parseBoolean(properties.getProperty("capability.captureScreenshots")));
        capabilities.setCapability("deviceGroup", properties.getProperty("capability.deviceGroup"));
        capabilities.setCapability("ignoreUnimportantViews", Boolean.parseBoolean(properties.getProperty("capability.ignoreUnimportantViews")));
        capabilities.setCapability("disableAndroidWatchers", Boolean.parseBoolean(properties.getProperty("capability.disableAndroidWatchers")));
        capabilities.setCapability("isHeadless", Boolean.parseBoolean(properties.getProperty("capability.isHeadless")));
        capabilities.setCapability("avdArgs", properties.getProperty("capability.avdArgs"));
        capabilities.setCapability("appPackage", "com.tunubi.wallet.qa");
        capabilities.setCapability("appActivity", "com.nubi.featuresplash.view.SplashActivity");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, Boolean.parseBoolean(properties.getProperty("capability.noReset")));


        /**
         * BROWSERSTACK
         */
        Util.AUTOMATION_NAME = properties.getProperty("capability.sessionName") + Util.randomString(7);
        capabilities.setCapability("sessionName", Util.AUTOMATION_NAME);
        capabilities.setCapability("browserstack.networkLogs", properties.getProperty("capability.browserstack.networkLogs"));
        capabilities.setCapability("device", properties.getProperty("deviceID"));
        capabilities.setCapability("os_version", properties.getProperty("capability.platformVersion"));
        capabilities.setCapability("project", properties.getProperty("capability.projectName"));
        capabilities.setCapability("build", properties.getProperty("capability.build"));
        capabilities.setCapability("name", properties.getProperty("capability.sessionDescription"));
        capabilities.setCapability("browserstack.appium_version", properties.getProperty("capability.browserstack.appium_version"));
        //capabilities.setCapability("browserstack.acceptInsecureCerts", properties.getProperty("capability.browserstack.acceptInsecureCerts"));

        /**
         * SOUCELABS
         */
        capabilities.setCapability("testobject_api_key", properties.getProperty("capability.testobject_api_key"));
        capabilities.setCapability("phoneOnly", properties.getProperty("capability.phoneOnly"));
        capabilities.setCapability("tabletOnly", properties.getProperty("capability.tabletOnly"));
        capabilities.setCapability("privateDevicesOnly", properties.getProperty("capability.privateDevicesOnly"));
        capabilities.setCapability("appiumVersion", properties.getProperty("capability.appiumVersion"));
        capabilities.setCapability("cacheId", properties.getProperty("capability.cacheId"));
        capabilities.setCapability("testobject_session_creation_timeout", properties.getProperty("capability.testobject_session_creation_timeout"));
        capabilities.setCapability("testobject_suite_name", properties.getProperty("capability.testobject_suite_name"));
        capabilities.setCapability("testobject_test_name", properties.getProperty("capability.testobject_test_name"));
        capabilities.setCapability("name", properties.getProperty("capability.name"));
        capabilities.setCapability("automationName", properties.getProperty("capability.automationName"));
        capabilities.setCapability("username", properties.getProperty("capability.username"));
        capabilities.setCapability("accessKey", properties.getProperty("capability.accessKey"));

        return capabilities;
    }

    public String getPropertyValue(Properties prop, String key) {
        return prop.getProperty(key);
    }
}
