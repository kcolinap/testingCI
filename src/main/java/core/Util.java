package core;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.login.OnboardingPage;
import api.apps.android.nw.model.Account;
import api.apps.android.nw.model.PrepaidCard;
import api.apps.android.nw.model.User;
import api.apps.android.nw.NubiWallet;
import com.github.javafaker.Faker;
import com.jcraft.jsch.JSchException;
import core.commons.DBQuery;
import core.commons.apicall.Auth_API;
import core.commons.apicall.Contacts_API;
import core.commons.apicall.DNI_Validation_API;
import core.commons.apicall.Users_API;
import core.commons.apicall.dtos.auth.*;
import core.commons.apicall.dtos.contacts.ContactsSync;
import core.commons.apicall.dtos.dniValidation.DNIManualConfirm;
import core.commons.apicall.dtos.dniValidation.DNIUserStatus;
import core.commons.apicall.dtos.users.ValidateUser;
import core.managers.DriverManager;
import core.managers.ServerManager;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.touch.offset.PointOption;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofSeconds;

public class Util {

    private String email;
    private static Properties properties = new Properties();
    private static Date date = new Date();
    private static DBQuery dbQuery = Apps.dbQuery;

    // Environments constants
    public final static String TEST = "test";
    public final static String DEV = "development";
    public final static String STAGE = "stage";
    public final static String STAGING = "staging";

    // Mode constants
    public static final String SOUCELABS = "soucelabs";
    public static final String KOBITON = "kobiton";
    public static final String BROWSERSTACK = "browserstack";
    public static final String LOCAL = "local";

    // Transactions type
    public static final String OUTGOING = "outgoing";
    public static final String INCOMING = "incoming";

    private static final Logger logger = LogManager.getLogger();

    // KIND Prepaid Card
    public static String VIRTUAL = "VIRTUAL";
    public static String PHYSICAL = "PHYSICAL";

    // STATUS Prepaid Card
    public static String ACTIVATED = "ACTIVATED";
    public static String REQUESTED = "REQUESTED";
    public static String RECEIVED = "RECEIVED";
    public static String BLOCKED = "BLOCKED";

    public static String AUTOMATION_NAME = "Automation UI test";

    // URL
    public static String RESET_PASSWORD_URL = "http://tunubi.app/password/reset/";

    public void setEnv(String env, String mode) {
        NubiWallet.environment = env;
        NubiWallet.mode = mode;
    }

    public static String getScreenShootDirectory() {
        return ServerManager.getWorkingDir() + File.separator + "build" + File.separator + "tmp" + File.separator + "screenshots";
    }

    public String getEnv() {
        return NubiWallet.environment;
    }


    /************
     *  GENERATE RANDOM NUMBER
     ***********/
    public int generarNumeroRandom(int lenght) {
        int numeroGenerado;
        do{
            switch (lenght) {
                case 1:
                    numeroGenerado = (int) (10 * Math.random());
                    break;
                case 2:
                    numeroGenerado = (int) (100 * Math.random());
                    break;
                case 3:
                    numeroGenerado = (int) (1000 * Math.random());
                    break;
                case 4:
                    numeroGenerado = (int) (10000 * Math.random());
                    break;
                case 5:
                    do
                        numeroGenerado = (int) Math.floor(Math.random() * 99999 + 1) ;
                    while (String.valueOf(numeroGenerado).length() < 5);
                    break;
                case 6:
                    numeroGenerado = (int) (1000000 * Math.random());
                    break;
                case 7:
                    numeroGenerado = (int) (10000000 * Math.random());
                    break;
                case 8:
                    numeroGenerado = (int) (100000000 * Math.random() + 1);
                    break;
                default:
                    numeroGenerado = (int) (1 * Math.random());
                    break;
            }
        }while (String.valueOf(numeroGenerado).length()<lenght);


        return numeroGenerado;
    }

    private String getEnvServerPrefix() {
        if (getEnv().equals(TEST))
            return "wi2";
        if (getEnv().equals(DEV))
            return "wi1";
        if (getEnv().equals(STAGE))
            return "sg";
        if (getEnv().equals(STAGING))
            return "sgi";
        return "";
    }

    public String gerenarEmail() {
        try {
            String prefixmail;
            do {
                prefixmail = String.valueOf(generarNumeroRandom(6));
            } while (prefixmail.length() < 6);

            String envPrefix = getEnvServerPrefix();

            email = getEmailPrefixWithoutPercent() + prefixmail + "@yopmail.com";
            //email = getEmailPrefixWithoutPercent() + "_" + prefixmail + "@yopmail.com";
            // email = "and"+ envPrefix + "_" + prefixmail + "@yopmail.com";

            //return armedEmail;
        } catch (Exception e) {
            throw new RuntimeException("Cannot generate email");
        }
        return email;
    }

    /***
     * Metodo para generar nombre y?o apellido random
     * @param tipo: tipo de dato a generar
     * @return
     */
    public String generarInformacionPersonal(String tipo) {
        String data = "";
        try {
            Faker faker = new Faker();
            if (tipo.toUpperCase().contentEquals("NAMES")) {
                data = faker.name().firstName();
            } else if (tipo.toUpperCase().contentEquals("LASTNAMES")) {
                data = faker.name().lastName();
            } else if (tipo.toUpperCase().contentEquals("USERNAME")) {
                data = faker.name().username();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /***
     *      Carga y devuelve el property de;l archivo dado
     * @param fileName : nombre del archivo a cargar
     * @param ext: extension del archivo
     * @return el archivo a ser leido por el propertie
     ***/
    public String getProperty(String fileName, String property, String ext) {
        try {
            String userDir = System.getProperty("user.dir");
            if (ext.contains("properties")) {
                FileInputStream fis = new FileInputStream(userDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "properties" + File.separator + fileName + ".properties");
                properties.load(fis);
            } else if (ext.contains("txt")) {
                FileInputStream fis = new FileInputStream(userDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + fileName + ".txt");
                properties.load(fis);
            }

            return properties.getProperty(property);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties.getProperty(property);
    }

    /***
     * Load txt file method
     *
     * Carga y devuelve el archivo txt dado
     ***/
    public static FileInputStream loadTxtFile(String txtFilename) throws IOException {

        String userDir = System.getProperty("user.dir");
        FileInputStream fis = new FileInputStream(userDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + txtFilename + ".txt");
        return fis;
    }


    /****
     * Escribir user en archivo
     */
    public void writeUser(String name, String lname, String email, String user, String pass, String pin, String dni, String cuil,
                          String pNumber, String index) {
        try {
            String useDir = System.getProperty("user.dir");
            FileWriter fw = null;
            BufferedWriter pw = null;

            try {

                File file = new File(useDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "user.properties");
                if (index.contentEquals("1")) {
                    if (file.exists())
                        file.delete();

                    file.createNewFile();
                }

                fw = new FileWriter(useDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "user.properties", true);
                pw = new BufferedWriter(fw);

                pw.write("     User# " + index + "\n");
                pw.write("NameUser" + index + " = " + name + "\n");
                pw.write("LastNameUser" + index + " = " + lname + "\n");
                pw.write("user" + index + " = " + user + "\n");
                pw.write("emailUser" + index + " = " + email + "\n");
                pw.write("passUser" + index + " = " + pass + "\n");
                pw.write("pinUser" + index + " = " + pin + "\n");
                pw.write("DNIUser" + index + " = " + dni + "\n");
                pw.write("CUILUser" + index + " = " + cuil + "\n");
                pw.write("PhoneNumberUser" + index + " = " + pNumber + "\n");
                pw.write("User" + index + "CreatedAt = " + date + "\n\n");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pw != null) {
                        pw.close();
                    }
                    if (fw != null) {
                        pw.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****
     * Escribir user en archivo
     */
    public void writeUserbalance(String type, String balance, int index) {
        try {
            String useDir = System.getProperty("user.dir");
            FileWriter fw = null;
            BufferedWriter pw = null;

            try {

                File file = new File(useDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "transactions.txt");

                if (String.valueOf(index).contains("1")) {
                    //si el archivo exite, lo borra y crea uno nuevo
                    if (file.exists())
                        file.delete();

                    file.createNewFile();
                }


                fw = new FileWriter(useDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "transactions.txt", true);
                pw = new BufferedWriter(fw);

                if (type.contentEquals("balance")) {
                    pw.write("BalanceUser" + index + ": " + balance + "\n");
                } else if (type.contentEquals("amount")) {
                    pw.write("dineroAOperar: " + balance + "\n");
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pw != null) {
                        pw.close();
                    }
                    if (fw != null) {
                        pw.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * SET GLOBAL ENVIRONMENT
     */
    public void setGlobalEnvironment(String env, String modo) {

        String environment = TEST;
        switch (env.toLowerCase()) {
            case "t":
                environment = TEST;
                break;
            case "d":
                environment = DEV;
                break;
            case "s":
                environment = STAGE;
                break;
            case "sg":
                environment = STAGING;
                break;

        }
        setEnv(environment, modo.toLowerCase());
    }

    /********                             ******
     *          SHOW ERROR TRACKTRACE
     *******                              *****/
    public void showTrackTraceError(String msg, Exception e) {
        logger.error(e.getMessage() + "\n" + "\n");
        e.printStackTrace();
    }

    /****
     *  SHOW ASSERTION ERROR
     */
    public void showAssertionErrors(String msj, AssertionError e, String nameTest, String id) {
        try {
            logger.error(msj + "\n" + "\n");
            logger.error(e.getMessage() + "\n" + "\n");
            logger.error(DriverManager.getDeviceId() + "\n" + "\n");
            e.printStackTrace();

            File file = ((TakesScreenshot) Android.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File(ServerManager.getWorkingDir() + File.separator + "screenshoots" + File.separator + nameTest + File.separator + id + ".jpg"));
        } catch (Exception error) {
            error.printStackTrace();
        }

    }

    /******
     * take screenshoot
     */
    public void takeScreenShoot(String testName) throws IOException {

    }

    public void selectContact(String contact) throws Exception {
        String element;
        int nroElement = Android.driver.findElements(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout/android.widget.TextView")).size();

        for (int i = 0; i < nroElement; i++) {
            element = Android.driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[" + (i + 1) + "]/android.widget.TextView")).getText();
            if (contact.toLowerCase().contentEquals("nubi")) {
                if (element.contains("NUBI")) {
                    Android.driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[" + (i + 2) + "]/android.widget.TextView")).click();
                } else {
                    throw new Exception("There is not NUBI contact");
                }
                break;
            }
        }
    }

    /**
     * select contact by name
     */
    public void selectEspecificContact(String name, String lname) throws Exception {

        //logger.info("//androidx.recyclerview.widget.RecyclerView//android.widget.TextView[contains(@text, '" +name+ "') and contains(@text, '" +lname+ "')]");
        Android.driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView//android.widget.TextView[contains(@text, '" + name + "') and contains(@text, '" + lname + "')]")).click();
        //Android.driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView//android.widget.TextView[contains(@text, '"+name+"') and contains(@text, '"+lname+"')]")).click();
        //Android.driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView//android.widget.TextView[@text='"+name+" "+lname+"']")).click();
    }

    public void scrollTo() {

        Dimension dim = Android.driver.manage().window().getSize();

        Double scrollHeightStart = dim.getHeight() * 0.50;
        int scrollstart = scrollHeightStart.intValue();

        Double scrollHeightEnd = dim.getHeight() * 0.40;
        int scrollEnd = scrollHeightEnd.intValue();

        int startVerticalX = (int) (dim.getWidth() / 2.1);

        new TouchAction((PerformsTouchActions) Android.driver)
                .press(PointOption.point(startVerticalX, scrollstart))
                .waitAction(waitOptions(ofSeconds(3)))
                .moveTo(PointOption.point(startVerticalX, scrollEnd))
                .release().perform();
    }

    public void scrollUp() {

        Dimension dim = Android.driver.manage().window().getSize();

        Double scrollHeightStart = dim.getHeight() * 0.50;
        int scrollstart = scrollHeightStart.intValue();

        Double scrollHeightEnd = dim.getHeight() * 0.57;
        int scrollEnd = scrollHeightEnd.intValue();

        new TouchAction((PerformsTouchActions) Android.driver)
                .press(PointOption.point(10, scrollstart))
                .moveTo(PointOption.point(10, scrollEnd))
                .release().perform();

    }




    /**************************
     * Return balance en numero
     *****************************/
    public Double getNumericBalance(String balance) throws Exception{
       // DashboardPage dashboardPage = DashboardPage.getInstance();
        //String balance = dashboardPage.get_element_text(dashboardPage.getBalance());

        if(balance.contains("$"))
            balance = balance.substring(1);
        balance = balance.replace(",", ".");

        char[] stringToChar = balance.toCharArray();

        int count = 0;
        String chare;

        for (char character : stringToChar) {
            chare = Character.toString(character);
            if (chare.contentEquals(".")) {
                count++;
            }
        }

        if (count >= 2) {
            balance = "";
            do {
                for (char character : stringToChar) {
                    chare = Character.toString(character);
                    if (chare.contentEquals(".")) {
                        if (count > 1) {
                            chare = "";
                        }
                        count--;
                    }
                    balance += chare;
                }
            } while (count > 1);
        }

        return Double.valueOf(balance);
    }


    /****
     * CALCULAR CUIL
     */
    public String calcularCuil(int genero, int prefijo, String documentoRequerido) throws SQLException {
        try {
            String dni;
            boolean existeDni;
            int resultado = 0, sufijo = 0, auxPrefijo = 0;

            do {
                do {
                    dni = String.valueOf(generarNumeroRandom(8));
                    existeDni = Apps.dbQuery.existDni(dni);
                } while (existeDni || dni.length() < 8);

                resultado = (Integer.parseInt(String.valueOf(dni.charAt(0))) * 3)
                        + (Integer.parseInt(String.valueOf(dni.charAt(1))) * 2)
                        + (Integer.parseInt(String.valueOf(dni.charAt(2))) * 7)
                        + (Integer.parseInt(String.valueOf(dni.charAt(3))) * 6)
                        + (Integer.parseInt(String.valueOf(dni.charAt(4))) * 5)
                        + (Integer.parseInt(String.valueOf(dni.charAt(5))) * 4)
                        + (Integer.parseInt(String.valueOf(dni.charAt(6))) * 3)
                        + (Integer.parseInt(String.valueOf(dni.charAt(7))) * 2);

                switch (genero) {
                    case 0:
                        resultado += 10;
                        auxPrefijo = 20;
                        break;
                    case 1:
                        resultado += 38;
                        auxPrefijo = 27;
                        break;
                }

                resultado %= 11;

                switch (resultado) {
                    case 0:
                        sufijo = 0;
                        break;
                    case 1:
                        auxPrefijo = 23;
                        if (genero == 0)
                            sufijo = 9;
                        if (genero == 1)
                            sufijo = 4;
                        break;
                    default:
                        sufijo = 11 - resultado;
                        break;
                }
            } while (!String.valueOf(prefijo).contentEquals(String.valueOf(auxPrefijo)));

            switch (documentoRequerido.toUpperCase()) {
                default:
                case "DNI":
                    return dni;
                case "CUIL":
                    return prefijo + dni + sufijo;
            }

            // logger.info(prefijo+dni+sufijo);

        } catch (Exception e) {
            throw e;
        }
    }

    /****
     * CALCULAR CUIL
     */
    public String calcularCuil(int genero, int prefijo, String documentoRequerido, String dni) throws SQLException {
        try {
            boolean existeDni;
            int resultado = 0, sufijo = 0, auxPrefijo = 0;

            do {

                resultado = (Integer.parseInt(String.valueOf(dni.charAt(0))) * 3)
                        + (Integer.parseInt(String.valueOf(dni.charAt(1))) * 2)
                        + (Integer.parseInt(String.valueOf(dni.charAt(2))) * 7)
                        + (Integer.parseInt(String.valueOf(dni.charAt(3))) * 6)
                        + (Integer.parseInt(String.valueOf(dni.charAt(4))) * 5)
                        + (Integer.parseInt(String.valueOf(dni.charAt(5))) * 4)
                        + (Integer.parseInt(String.valueOf(dni.charAt(6))) * 3)
                        + (Integer.parseInt(String.valueOf(dni.charAt(7))) * 2);

                switch (genero) {
                    case 0:
                        resultado += 10;
                        auxPrefijo = 20;
                        break;
                    case 1:
                        resultado += 38;
                        auxPrefijo = 27;
                        break;
                }

                resultado %= 11;

                switch (resultado) {
                    case 0:
                        sufijo = 0;
                        break;
                    case 1:
                        auxPrefijo = 23;
                        if (genero == 0)
                            sufijo = 9;
                        if (genero == 1)
                            sufijo = 4;
                        break;
                    default:
                        sufijo = 11 - resultado;
                        break;
                }
            } while (!String.valueOf(prefijo).contentEquals(String.valueOf(auxPrefijo)));

            switch (documentoRequerido.toUpperCase()) {
                default:
                case "DNI":
                    return dni;
                case "CUIL":
                    return prefijo + dni + sufijo;
            }

            // logger.info(prefijo+dni+sufijo);

        } catch (Exception e) {
            throw e;
        }
    }

    public int calcularSoloPrefijo(int genero, String dni) throws SQLException {
        try {
            boolean existeDni;
            int resultado = 0, sufijo = 0, auxPrefijo = 0;

            do {

                resultado = (Integer.parseInt(String.valueOf(dni.charAt(0))) * 3)
                        + (Integer.parseInt(String.valueOf(dni.charAt(1))) * 2)
                        + (Integer.parseInt(String.valueOf(dni.charAt(2))) * 7)
                        + (Integer.parseInt(String.valueOf(dni.charAt(3))) * 6)
                        + (Integer.parseInt(String.valueOf(dni.charAt(4))) * 5)
                        + (Integer.parseInt(String.valueOf(dni.charAt(5))) * 4)
                        + (Integer.parseInt(String.valueOf(dni.charAt(6))) * 3)
                        + (Integer.parseInt(String.valueOf(dni.charAt(7))) * 2);

                switch (genero) {
                    case 0:
                        resultado += 10;
                        auxPrefijo = 20;
                        break;
                    case 1:
                        resultado += 38;
                        auxPrefijo = 27;
                        break;
                }

                resultado %= 11;

                switch (resultado) {
                    case 0:
                        sufijo = 0;
                        break;
                    case 1:
                        auxPrefijo = 23;
                        if (genero == 0)
                            sufijo = 9;
                        if (genero == 1)
                            sufijo = 4;
                        break;
                    default:
                        sufijo = 11 - resultado;
                        break;
                }
            } while (auxPrefijo == 0);
            return auxPrefijo;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * Retornar mensaje de al pulsar boton en pantalla
     */
    public String getMsjErrorAlPulsarBoton(String pantalla, String boton) {
        String msj = "";
        try {
            pantalla = pantalla.toUpperCase();
            boton = boton.toLowerCase();


            switch (boton) {
                case "back":
                    msj = "Error al pualsar boton BACk en pantalla " + pantalla;
                    break;
                case "siguiente":
                    msj = "Error al pualsar boton SIGUIENTE en pantalla " + pantalla;
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return msj;
    }

    /*********************
     * ADD CASH TO THE BALANCE
     */
    public void addCash(int cash, String username) {
        try {

            SSHConector sshConector = new SSHConector();

            String env = getEnv();
            String host;

            User user = obtenerUsuarioByUsername(username);

            //Lee el archivo de properties
            // properties.load(loadPropertyFile("db"));

            if (env.contentEquals(DEV))  //por ahora both environemnet works with just one lmb
                host = this.getProperty("db", "properties", "urlDEV");
            else
                host = this.getProperty("db", "urlTEST", "properties");

            sshConector.connect("ubuntu", "lmb-nw.stage.tunubi.com", 7022);
            String result = sshConector.executeCommand("./money.sh " + user.getId() + " " + user.getAcounts().get(0).getNumber() + " " + cash);
            sshConector.disconnect();

            //logger.info(result);
        } catch (JSchException | IllegalAccessException e) {
            showTrackTraceError("fail", e);
        } catch (IOException e) {
            showTrackTraceError("Fail", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Properties propertiesConfiguration(String filename) {
        InputStream is = null;
        try {
            Properties prop = new Properties();
            String filePath = ServerManager.getWorkingDir() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "properties" + File.separator + filename + ".properties";
            FileInputStream fis = new FileInputStream(filePath);
            prop.load(fis);
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------------------- METODOS PARA EL MODELO DE USUARIO ---------------------------------

    public List<User> obtenerUsuarios(int cantUsuarios) throws Exception {
        return Apps.dbQuery.getUserFromBD(null, cantUsuarios);
    }

    public User obtenerUsuarioById(String userId) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", userId);

        List<User> usuarios = Apps.dbQuery.getUserFromBD(params, 1);
        if (!usuarios.isEmpty())
            return usuarios.get(0);
        return null;
    }

    public User obtenerUsuarioByEmail(String email) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);

        List<User> usuarios = Apps.dbQuery.getUserFromBD(params, 1);
        if (!usuarios.isEmpty())
            return usuarios.get(0);
        return null;
    }

    public User obtenerUsuarioByCvu(String cvu) throws Exception {
        if (cvu.isEmpty())
            cvu = "any";

        HashMap<String, String> params = new HashMap<>();
        params.put("cvu", cvu);

        List<User> usuarios = dbQuery.getUserFromBD(params, 1);
        if (!usuarios.isEmpty())
            return usuarios.get(0);
        return null;
    }

    public User obtenerUsuarioByUsername(String username) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);

        List<User> usuarios = Apps.dbQuery.getUserFromBD(params, 1);
        if (!usuarios.isEmpty())
            return usuarios.get(0);
        return null;
    }

    public User obtenerUsuarioByNOCvu(String cvu) throws Exception {
        if (cvu.isEmpty())
            cvu = "no";

        HashMap<String, String> params = new HashMap<>();
        params.put("cvu", cvu);

        List<User> usuarios = dbQuery.getUserFromBD(params, 1);
        if (!usuarios.isEmpty())
            return usuarios.get(0);
        return null;
    }

    public User getUserWithoutPrepaidCard() throws Exception {
        List<User> usuarios;
        for (int i = 0; i <= 5; i++) {
            usuarios = dbQuery.getUserFromBD(null, 10);
            if (!usuarios.isEmpty()) {
                for (User user : usuarios) {
                    for (Account acc : user.getAcounts()) {
                        if (acc.getPrepaidCard() == null)
                            return user;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retorna un usuario SIN tarjeta Prepaga
     *
     * @return
     * @throws Exception
     */
    public User getPrefixedUserWithoutPrepaidCard() throws Exception {
        return Apps.dbQuery.getPrefixedUserWithoutPrepaidCard();
    }

    public User getUserWithoutSubeCard() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("no_sube", "true");
        return Apps.dbQuery.getUserWithoutSubeCard(params, 10);
    }

    public User getUserWithSubeCard() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("sube", "true");
        return Apps.dbQuery.getUserWithoutSubeCard(params, 10);
    }

    /**
     * Retorna un usuario con tarjeta prepaga
     *
     * @param kind   : PHYSICAL - VIRTUAL
     * @param status : ACTIVATED - REQUESTED - RECEIVED - BLOCKED
     * @return
     * @throws Exception
     */
    public PrepaidCard getPrefixedPrepaidCard(String kind, String status) throws Exception {
        String queryClause = " WHERE kind = '" + kind + "'::kind AND status = '" + status + "'::status";
        return Apps.dbQuery.getPrefixedPrepaidCard(queryClause);
    }

    public User obtenerUsuario(String tipoUsuario) throws Exception {

        Random rand;

        if (Android.nubiWallet.usuarios.isEmpty())
            Android.nubiWallet.usuarios = obtenerUsuarios(3);
        try {
            User usuario = null;
            switch (tipoUsuario.toUpperCase()) {
                case "ANY":
                    rand = new Random();
                    if (Android.nubiWallet.usuarios.size() > 0)
                        usuario = Android.nubiWallet.usuarios.get(rand.nextInt(Android.nubiWallet.usuarios.size()));
                    if (usuario == null)
                        throw new Exception("No hay ningun usuario para utilizar");
                    break;
                case "NUBI_CONTACT":
                case "ORIGIN_USER_P2P_NUBI_REQUEST":
                case "ORIGIN_USER_P2P_NUBI_SENDING":
                case "CON_CONTACTO_NUBI":
                    //if(NubiWallet.mode.contentEquals(Util.BROWSERSTACK) || isStaging())
                        usuario = Android.nubiWallet.getNubiUser();

                    break;
                case "CON_BANCARIO":
                    usuario = Android.nubiWallet.getBankUser();
                    if (usuario == null)
                        throw new Exception("No hay usuario con cuenta bancaria");
                    break;
                case "CON_NUBI_BANCARIO":
                    usuario = Android.nubiWallet.getNubiBankUser();
                    if (usuario == null)
                        throw new Exception("No hay usuario con cuenta Nubi y Bancaria");
                    break;
                case "USER1":
                    String email = getProperty("user", "emailUser1", "txt");
                    usuario = obtenerUsuarioByEmail(email);
                    if (usuario == null)
                        throw new Exception("No hay usuario en la BD existente con email: " + email + " - cambiar en el archivo: user.txt");

                    Android.nubiWallet.usuarios.add(usuario);
                    break;
                case "CON_CVU":
                    //Obtengo la lista de usersId que tienen CVU
                    usuario = Android.nubiWallet.getUserWithCVU();
                    // No hay usuarios en la lista de nubiWallet, busco en la BD porq deberia haber alguno
                    if (usuario == null)
                        usuario = obtenerUsuarioByCvu(null);
                    if (usuario == null)
                        throw new Exception("No hay usuario en la BD existente con cvu no nulo");
                    else
                        Android.nubiWallet.usuarios.add(usuario);
                    break;
                case "NOCVU_BANCARIO":
                    //Obtengo la lista de usersId que no tiene CVU
                    usuario = Android.nubiWallet.getUserWithNOCVUBancario();

                    if (usuario == null)
                        throw new Exception("No hay usuario con SIN CVU con cuenta bancaria");
                    break;
                case "NOCVU_NUBIBANCARIO":
                    usuario = Android.nubiWallet.getUserWithNOCVUNubi();

                    if (usuario == null)
                        throw new Exception("No hay usuario con SIN CVU con cuenta Nubi");
                    break;
                case "WITHOUT_PREPAID_CARD":
                    usuario = Android.nubiWallet.getUserWithoutPrepaidCard();

                    //Get user status, if doesnt exist, create a row
                    DNI_Validation_API dni_validation_api = new DNI_Validation_API();
                    dni_validation_api.User_Status();

                    //Update the status to 1(validated)
                    logger.info("Updating dni user status");
                    Apps.dbQuery.actualizar_dni_validacion_status(1, usuario.getId());

                    //Udpadte date DOB
                    logger.info("Updatig date of birth");
                    Apps.dbQuery.actualizar_fecha_nacimiento(1, usuario.getId());

                    logger.info("Validting user");
                    Apps.util.PEP_SO(usuario);

                    if (usuario == null)
                        throw new Exception("No hay usuario SIN tarjeta prepaga");
                    break;
                case "VIRTUAL_PREPAID_CARD":
                case "virtual c":
                    usuario = Android.nubiWallet.getUserWithPrepaidCard(VIRTUAL, ACTIVATED);


                    /*DNI_Validation_API dniApi;
                    //DNIManualConfirm dtoDni;
                    //DNIUserStatus userStatus;
                    Users_API usersApi;
                    ValidateUser dtoValidateUser;
                    rand = new Random();

                    usuario = Android.nubiWallet.usuarios.get(rand.nextInt(Android.nubiWallet.usuarios.size()));

                    usuario.refreshPrepaidCardList();

                    if(usuario.getPrepaidCardList().isEmpty() ) {
                        //Set dni user status
                        dniApi = new DNI_Validation_API();
                        dniApi.User_Status();

                        //Update the status to 1(validated)
                        Apps.dbQuery.actualizar_dni_validacion_status(1, usuario.getId());

                        //Udpadte date DOB
                        Apps.dbQuery.actualizar_fecha_nacimiento(1, usuario.getId());

                        //Validate user
                        usersApi = new Users_API();
                        dtoValidateUser = usersApi.patch_users();
                        System.out.println(dtoValidateUser.getNationality());

                    }*/

                    if (usuario == null)
                        throw new Exception("No hay usuario con tarjeta virtual activa");
                    break;
                case "VIRTUAL_PREPAID_CARD_BLOCKED":
                    usuario = Android.nubiWallet.getUserWithPrepaidCard(VIRTUAL, BLOCKED);
                    if (usuario == null)
                        throw new Exception("No hay usuario con tarjeta virtual bloqueada");
                    break;
                case "PHYSICAL_PREPAID_CARD":
                    usuario = Android.nubiWallet.getUserWithPrepaidCard(PHYSICAL, ACTIVATED);

                    if (usuario == null)
                        throw new Exception("No hay usuario con tarjeta fisica activa");
                    break;
                case "SIN_SUBE":
                    usuario = Android.nubiWallet.getUserWithoutSube();
                    if (usuario == null)
                        throw new Exception("No hay usuario sin tarjeta Sube");
                    break;
                case "CON_SUBE":
                    usuario = Android.nubiWallet.getUserWithSube();
                    if (usuario == null)
                        throw new Exception("No hay usuario con tarjeta Sube");
                    break;
                case "CON_PRESTAMO":
                    for (User us: Android.nubiWallet.usuarios){
                        if (us.getLendingCandidate()){
                            usuario = us;
                            break;
                        }
                    }

                    if (usuario == null){
                        Android.nubiWallet.addLoanUsers();
                        usuario = Android.nubiWallet.getLoanUser();
                    }

                    if (usuario == null)
                        throw new Exception("No hay usuario calificado para prestamo");
                    break;
            }

            return usuario;

        } catch (Exception | Error e) {
            logger.error("Error obteniendo usuario de tipo: " + tipoUsuario.toUpperCase());
            throw e;
        }
    }

    public String obtenerEmail() throws SQLException {

        return Apps.dbQuery.getUserInfo("email");
    }

    public String getUserEmail(String userId) throws SQLException {
        String mail = Apps.dbQuery.getUserEmail(userId);
        return mail;
    }


    public String obtenerDni() throws SQLException {
        String mail = Apps.dbQuery.getUserInfo("dni");

        return mail;
    }

    public String obtenerUserName() throws SQLException {
        String mail = Apps.dbQuery.getUserInfo("username");

        return mail;
    }

    public String obtenerCuil() throws SQLException {
        String cuil = Apps.dbQuery.getUserInfo("cuil");

        return cuil;
    }

    public String getEmailPrefix() {
        String env = getEnv();
        String emailPrefix = "andw2%";
        //String emailPrefix = "jmeterw2%";

        if (env.equals(DEV))
            emailPrefix = "andw1%";
        else if (env.equals(STAGE))
            emailPrefix = "andsg%";
        else if (env.equals(STAGING))
            emailPrefix = "andstg%";
            //emailPrefix = "jmeterstg%";

        return emailPrefix;
    }

    public String getEmailPrefixWithoutPercent() {
        return getEmailPrefix().substring(0, getEmailPrefix().length() - 1);
    }

    public boolean isStaging() {
        if (getEnv().equals(STAGING))
            return true;
        return false;
    }

    public void scrollToElementAndSelectFromList(MobileElement elementToScroll, String xpathElementToFind, int max_swipes) {
        boolean encontrado = false;
        int already_swiped = 0;
        boolean aux;

        while (!encontrado && already_swiped < max_swipes) {
            try {
                WebElement element = (WebElement) Android.driver.findElement(By.xpath(xpathElementToFind));
                if (element.isDisplayed()) {
                    encontrado = true;
                    element.click();
                }
            } catch (Exception | Error e) {
                TouchAction action = new TouchAction(Android.driver);
                MobileElement list = elementToScroll;
                int midX = list.getLocation().getX() + list.getSize().getWidth() / 2;
                int upY = list.getLocation().getY();
                int lowY = upY + list.getSize().getHeight() - 400;
                action.press(PointOption.point(midX, lowY)).waitAction().moveTo(PointOption.point(midX, upY)).release().perform();
                continue;
            }
            already_swiped++;
        }
    }


    public String generateAnUsernameFromAnEmail(String email) {
        try {
            String username = email.replace("@yopmail.com", "");
            username = username.replace("_", "");
            return username;
        } catch (Error e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateAPhoneNumber() throws SQLException {
        try {
            String phoneNumber;
            Pair<Integer, String> areaCode = Apps.util.getAreaCode();
            do {
                phoneNumber = "+549" + areaCode.getValue() + "999" + generarNumeroRandom(areaCode.getKey());

            } while (Apps.dbQuery.existPhoneNumber(phoneNumber));

            return phoneNumber;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Date get_limit_date_to_query(){
        Calendar cal = new GregorianCalendar();

        cal.add(Calendar.MONTH, -1);

        return new java.sql.Date(cal.getTime().getTime());
    }

    public Double formatearDecimales(Double numero, Integer numeroDecimales) {
        return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
    }

    public AndroidKey getKeyPad(String key){
        AndroidKey auxkey=null;
        switch (key){
            case "0":
                auxkey = AndroidKey.NUMPAD_0;
                break;
            case "1":
                auxkey = AndroidKey.NUMPAD_1;
                break;
            case "2":
                auxkey = AndroidKey.NUMPAD_2;
                break;
            case "3":
                auxkey = AndroidKey.NUMPAD_3;
                break;
            case "4":
                auxkey = AndroidKey.NUMPAD_4;
                break;
            case "5":
                auxkey = AndroidKey.NUMPAD_5;
                break;
            case "6":
                auxkey = AndroidKey.NUMPAD_6;
                break;
            case "7":
                auxkey = AndroidKey.NUMPAD_7;
                break;
            case "8":
                auxkey = AndroidKey.NUMPAD_8;
                break;
            case "9":
                auxkey = AndroidKey.NUMPAD_9;
                break;
        }

        return auxkey;
    }

    public Pair<Integer, String> getAreaCode() {
        String[] areaCodeList = new String[]{"11","351","379","370","221","380","261","299","343","376","280",
                "362","2966","387","383","264","266","381","388","342","2954","385","2920","2901"};
        Random rand = new Random();
        String areaCode = areaCodeList[(rand.nextInt(areaCodeList.length))];
        int numberOfPhoneDigits = 7 - areaCode.length();
        return new Pair<>(numberOfPhoneDigits, areaCode);
    }

    public static String randomString(int length){
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public void ChangePassword(User user) {
        try {
            Auth_API auth_api;
            POSTPasswordRecover dtoPasswordRecover;
            PatchPasswordRecover dtoPatch;

            auth_api = new Auth_API();
            dtoPasswordRecover = auth_api.post_password_recover(user.getEmail());
            Assert.assertTrue(dtoPasswordRecover.isSuccess());

            //Get recovery token
            String token = Apps.dbQuery.returnPassRecoveryToken(user.getId());
            dtoPatch = auth_api.patch_password_recover(token);

            Assert.assertTrue(dtoPatch.isSuccess());

        } catch (Exception | Error e) {

        }
    }

    public void PEP_SO(User user) {
        try {
            Users_API usersApi;
            ValidateUser validateUser;

            usersApi = new Users_API();
            validateUser = usersApi.patch_users(user);

        } catch (Exception | Error e) {

        }
    }

    public void swipeLeft(MobileElement mobileElement) {
        Dimension size = Android.driver.manage().window().getSize();

        int x1 = (int) (size.width * 0.01);
        //new TouchAction((AndroidDriver)Android.driver).l.waitAction(Duration.ofSeconds(1)).moveTo(x1,0).release().perform();
    }

    public void ChangeNubiPassword(User user) throws Exception {
        try{
            Auth_API auth_api = new Auth_API();
            PatchPinRecover changePin = auth_api.patch_pin_recover();

            Assert.assertTrue(changePin.getPhone_number().contentEquals(user.getPhoneNumber()));

            String smsCode = Apps.dbQuery.getCodigoSmsOlvidoPin(user.getId());

            PostPinRecover postPinRecover = auth_api.post_pin_recover(smsCode);

            Assert.assertTrue(postPinRecover.isSuccess());


        }catch (Exception | Error err){
            logger.info("No change nubi passwprd needed");
        }
    }

    public void contacts_sync(User destinationUser) throws Exception {
        try{

            Contacts_API contacts_api = new Contacts_API();
            contacts_api.contactsSync(destinationUser);

           // Assert.assertNotNull(contactsSync.getPhone_contacts());


        }catch (Exception | Error err){
            logger.error("Error on contact sync");
        }
    }

    public void jumpOnboardingPage(String jumpType) throws Exception {

        OnboardingPage onboardingPage = OnboardingPage.getInstance();

        while (!Wrapper.elementExists(onboardingPage.getLoginLink()))
            Apps.util.scrollTo();

        switch (jumpType.toUpperCase()){
            case "LOGIN":
                onboardingPage.click_login();
                break;
            case "REGISTER":
                onboardingPage.click_register();
                break;
        }
    }
}
