package api.apps.android.contactosagenda;

import api.apps.android.Android;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;

public class OpenNativeContact {

    public WebDriverWait wait = new WebDriverWait(Android.driver,10);
    private final String device;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.google.android.contacts:id/floating_action_button\")")
    protected static MobileElement addBtn;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.google.android.contacts:id/editor_menu_save_button\")")
    protected static MobileElement saveBtn;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(className = "android.widget.EditText")
    protected List<MobileElement> fields;

    public OpenNativeContact() {

        MutableCapabilities allCapabilities = (MutableCapabilities)Android.driver.getCapabilities();
        this.device = allCapabilities.getCapability("device").toString().toUpperCase();

        startContactApplication(getPackageAndActivitys());

        // Load the Add page
        loadElements();

        // Wait for add Btn
        wait.until(ExpectedConditions.visibilityOf(addBtn));
    }

    private void loadElements() {
        PageFactory.initElements(new AppiumFieldDecorator(((AndroidDriver)Android.driver)), this);
    }

    private void startContactApplication(HashMap<String, String> deviceActivities) {
        // Open the native contact application
        try {
            ((AndroidDriver)Android.driver).startActivity(new Activity(deviceActivities.get("package"), deviceActivities.get("mainActivity")));
        } catch(Exception e) {
            if (((AndroidDriver)Android.driver).currentActivity().equals(deviceActivities.get("secondaryActivity")))
                Android.driver.findElement(By.xpath(deviceActivities.get("btnXpath"))).click();
            else
                throw e;
        }

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < 1000)
            if (((AndroidDriver)Android.driver).currentActivity().equals(deviceActivities.get("nubiActivity")))
                break;

        ((AndroidDriver)Android.driver).pressKey(new KeyEvent(AndroidKey.APP_SWITCH));

        while(System.currentTimeMillis() - startTime < 1000)
            if (((AndroidDriver)Android.driver).getCurrentPackage().contains(deviceActivities.get("launcherActivity")))
                break;

        ((AndroidDriver)Android.driver).pressKey(new KeyEvent(AndroidKey.APP_SWITCH));

        startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < 1000)
            if (((AndroidDriver)Android.driver).currentActivity().equals(deviceActivities.get("mainActivity")))
                break;
    }

    private void clickAddButton() {
        addBtn.click();
    }

    private void clickSaveButton() {
        saveBtn.click();
    }

    private void switchApp() {
        HashMap<String, String> deviceActivities = getPackageAndActivitys();

        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < 1000)
            if (((AndroidDriver)Android.driver).currentActivity().equals(deviceActivities.get("mainActivity")))
                break;

        ((AndroidDriver)Android.driver).pressKey(new KeyEvent(AndroidKey.APP_SWITCH));

        while(System.currentTimeMillis() - startTime < 1000)
            if (((AndroidDriver)Android.driver).getCurrentPackage().contains(deviceActivities.get("launcherActivity")))
                break;

        ((AndroidDriver)Android.driver).pressKey(new KeyEvent(AndroidKey.APP_SWITCH));

        startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < 1000)
            if (((AndroidDriver)Android.driver).currentActivity().equals(deviceActivities.get("nubiActivity")))
                break;
    }

    private void setFirstName(String name) {
        for( MobileElement field : fields ) {
            if ( field.getText().equals("First name") )
                field.sendKeys(name);
        }
    }

    private void setLastName(String lastName) {
        for( MobileElement field : fields ) {
            if ( field.getText().equals("Last name") )
                field.sendKeys(lastName);
        }
    }

    private void setPhone(String phoneNumber) {
        for( MobileElement field : fields ) {
            if ( field.getText().equals("Phone") )
                field.sendKeys(phoneNumber);
        }
    }

    private MobileElement afterSaveValidation(String text) {
        String xpathValidation = getPackageAndActivitys().get("afterSaveValidation").replace("#TEXT#", text);
        return (MobileElement) Android.driver.findElement(By.xpath(xpathValidation));
    }

    public void addContact(String firstName, String lastName, String phone) {
        clickAddButton();
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        clickSaveButton();
        // Validate it was saved.
        if ( afterSaveValidation(firstName + " " + lastName) != null )
            switchApp();
        else
            System.out.println("Contact NOT saved");
    }

    private HashMap<String, String> getPackageAndActivitys() {
        HashMap<String, String> deviceConfig = new HashMap<>();
        deviceConfig.put("nubiActivity", "com.nubi.featurelogin.view.LoginActivity");

        String brand = "";
        if ( device.toUpperCase().contains("GOOGLE") )
            brand = "GOOGLE";
        else if ( device.toUpperCase().contains("SAMSUNG") )
            brand = "SAMSUNG";
        else if ( device.toUpperCase().contains("MOTOROLA") )
            brand = "MOTOROLA";

        switch(brand) {
            case "GOOGLE":
                deviceConfig.put("package", "com.google.android.contacts");
                deviceConfig.put("mainActivity", "com.android.contacts.activities.PeopleActivity");
                deviceConfig.put("secondaryActivity", "com.google.android.apps.contacts.activities.OnboardingSignInActivity");
                deviceConfig.put("btnXpath", "//*[@text='SKIP']");
                deviceConfig.put("launcherActivity", "com.google.android.apps.nexuslauncher");
                deviceConfig.put("afterSaveValidation", "//android.widget.TextView[@text='#TEXT#']");
                break;
            case "SAMSUNG":
                deviceConfig.put("package", "com.samsung.android.contacts");
                deviceConfig.put("mainActivity", "com.android.contacts.activities.PeopleActivity");
                deviceConfig.put("secondaryActivity", "");
                deviceConfig.put("btnXpath", "");
                deviceConfig.put("launcherActivity", "com.google.android.apps.nexuslauncher");
                deviceConfig.put("afterSaveValidation", "//android.widget.TextView[@text='#FULLNAME#']");
                break;
            case "MOTOROLA":
                deviceConfig.put("package", "com.android.contacts");
                deviceConfig.put("mainActivity", "com.android.contacts.activities.PeopleActivity");
                deviceConfig.put("secondaryActivity", "com.google.android.apps.contacts.activities.OnboardingSignInActivity");
                deviceConfig.put("btnXpath", "");
                deviceConfig.put("launcherActivity", "");
                deviceConfig.put("afterSaveValidation", "");
                break;
            default:
                deviceConfig.put("package", "com.google.android.contacts");
                deviceConfig.put("mainActivity", "com.android.contacts.activities.PeopleActivity");
                deviceConfig.put("secondaryActivity", "com.google.android.apps.contacts.activities.OnboardingSignInActivity");
                deviceConfig.put("btnXpath", "//*[@text='SKIP']");
                deviceConfig.put("launcherActivity", "com.google.android.apps.nexuslauncher");
                deviceConfig.put("afterSaveValidation", "//android.widget.TextView[@text='#FULLNAME#']");
                break;
        }

        return deviceConfig;
    }

}
