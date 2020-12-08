package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.contactosagenda.ContactosAgenda;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.login.LoginPage;
import api.apps.android.nw.login.OnboardingPage;
import api.apps.android.nw.registro.*;
import core.Util;
import core.commons.DBQuery;
import api.apps.android.Wrapper;
import core.commons.apicall.API_Util;
import core.commons.apicall.Registration_API;
import core.commons.yopmail.YopmailReader;
import interfaces.Registration;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rp.com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Registro implements Registration {

    WebDriverWait wait = new WebDriverWait(Android.driver,10);
    private DBQuery dbQuery = Apps.dbQuery;
    private static String fname;
    boolean aux = false;
    private static final Logger logger = LogManager.getLogger();
    public Registration_API registration_api = new Registration_API();

    public static String

            email = "",
            user = "",
            pass = "Test-0000",
            pin = "",
            DNI = "",
            NAME = "",
            LASTNAME = "",
            PHONE = "",
            NUMBER = "",
            AREA_CODE = "",
            accountNumber = "",
            TOKEN;


    private static NubiWallet nubiWallet = Android.nubiWallet;
    private static ContactosAgenda contactosAgenda = new ContactosAgenda();

    public String getNombre() {
        return fname;
    }

    public void setNombre(String nombre) {
        this.fname = nombre;
    }

    public String getNombreUsuario() {

        return this.user;
    }

    public void setNumeroTelefono(String numero){
        this.PHONE = numero;
    }
    public String getNumeroTelefono(){
        return PHONE;
    }
    public String getDni(){ return DNI;}


    /**
     * @param opc: numero de pantalla a completar:
     *           1- email
     *           2-Confirma email
     *
     *
     */
    @Given("Completar hasta pantalla {int}")
    public void completar_pantalla(int opc) throws Exception{
        try {
            switch (opc){
                case 1:
                    completar_pantalla_email();
                    break;
                case 2:
                    completar_pantalla_email();
                    completar_pantalla_confirmar_email();
                    break;
                case 3:
                    completar_pantalla_email();
                    completar_pantalla_confirmar_email();
                    completar_pantalla_informacion_personal();
                    break;
                case 4:
                    completar_pantalla_email();
                    completar_pantalla_confirmar_email();
                    completar_pantalla_informacion_personal();
                    completar_pantalla_cuil();
                    break;
                case 5:
                    completar_pantalla_email();
                    completar_pantalla_confirmar_email();
                    completar_pantalla_informacion_personal();
                    completar_pantalla_cuil();
                    completar_pantalla_usuario();
                    break;
                case 6:
                    completar_pantalla_email();
                    completar_pantalla_confirmar_email();
                    completar_pantalla_informacion_personal();
                    completar_pantalla_cuil();
                    completar_pantalla_usuario();
                    completar_pantalla_telefono();
                    break;
                case 7:
                    completar_pantalla_email();
                    completar_pantalla_confirmar_email();
                    completar_pantalla_informacion_personal();
                    completar_pantalla_cuil();
                    completar_pantalla_usuario();
                    completar_pantalla_telefono();
                    completar_pantalla_codigo_sms();
                    break;
                case 8:
                    completar_pantalla_email();
                    completar_pantalla_confirmar_email();
                    completar_pantalla_informacion_personal();
                    completar_pantalla_cuil();
                    completar_pantalla_usuario();
                    completar_pantalla_telefono();
                    completar_pantalla_codigo_sms();
                    completar_pantalla_pin();
                    break;
                case 9:
                    completar_pantalla_email();
                    completar_pantalla_confirmar_email();
                    completar_pantalla_informacion_personal();
                    completar_pantalla_cuil();
                    completar_pantalla_usuario();
                    completar_pantalla_telefono();
                    completar_pantalla_codigo_sms();
                    completar_pantalla_pin();
                    completar_pantalla_terminos_condiciones();
                    break;
                default:
                    break;

            }

        }catch (Exception | Error er){
            logger.error(er);
            throw er;
        }
    }

    @Test
    @And("User complete email screen")
    @When("Completa la pantalla de email")
    public void completar_pantalla_email() throws Exception {
        try {
            boolean status, flagEmail;
            EmailPage emailPage = EmailPage.getInstance();

            //Asserts
            Assert.assertEquals(EmailPage.LBL_TITULO_TEXT, EmailPage.getLblTitle().getText().replace("\n", " "));
            Assert.assertEquals(EmailPage.LBL_SUBTITULO_TEXT, emailPage.getLblSubtitle().getText().replace("\n", " "));

            //Validamos si existe el email
            do {
                email = util.gerenarEmail();
            } while (db.existeEmail(email));

            //Set email
            emailPage.setEmail(email);

            Android.hideKeyboardIfVisible();

            //Pulsar el boton siguiente mientras este exista
            //O se agoten los  3 intentos de avanzar
            //int flag = 0;
            //boolean botonPresente = false;
            //do {

                emailPage.click_on_next_button();
                Thread.sleep(700);
                //flag++;

          // } while (emailPage.elementExists(emailPage.getNextButton()) && flag < 5);


        } catch (Exception | Error err) {
            logger.error("Error completando pantalla de email");
            throw err;
        }
    }

    @And("Completa pantalla de confirmacion de email")
    public void completar_pantalla_confirmar_email() throws InterruptedException, Exception{
        try {

            ConfirmEmailPage confirmEmailPage = ConfirmEmailPage.getInstance();


            //Get acces token
            //Call confirm mail api
            TOKEN = Apps.dbQuery.getAccesToken(email);
            registration_api.confirm_email(TOKEN);

            //Asserts
            Assert.assertTrue(confirmEmailPage.elementExists(confirmEmailPage.getIcnEmailSent()));
            Assert.assertTrue(confirmEmailPage.elementExists(ConfirmEmailPage.getBackOrCloseBtn()));
            Assert.assertEquals(ConfirmEmailPage.LBL_TITULO_TEXT, ConfirmEmailPage.getLblTitle().getText());
            Assert.assertEquals(ConfirmEmailPage.LBL_MESSAGE, confirmEmailPage.getMessage().getText().replace("\n", " "));


           /* //Api url
            API_Util util = new API_Util();
            String apiurl = util.getEnvUrl(Registration_API.getRegistrationEndpoint());

            ((AndroidDriver)Android.driver).get(apiurl+"/registration/web/registration/confirm/"+TOKEN);*/
            String link = util.getProperty("DeepLinks", "HEADER", "properties") +
                    util.getProperty("DeepLinks", "CONFIRM_MAIL_LINK", "properties");

            ((AndroidDriver)Android.driver).executeScript("mobile: deepLink", ImmutableMap.of("url", link+TOKEN, "package", Android.nubiWallet.packageID()));

            //Open app
            //nubiWallet.open();

        } catch (Exception | Error err) {
            logger.error(err.getMessage());
            throw err;
        }
    }

    @And("Completa pantalla de informacion personal")
    public void completar_pantalla_informacion_personal() throws SQLException, Exception {

        String nombre, apellido, dni;
        boolean existDni, statusEnabled;

        try {

            Thread.sleep(1000);
            Android.hideKeyboardIfVisible();
            PersonalDataPage personalDataPage = PersonalDataPage.getInstance();

            //Asserts
            Assert.assertEquals(PersonalDataPage.LBL_TITULO_TEXT, PersonalDataPage.getLblTitle().getText());
            Assert.assertEquals(PersonalDataPage.LBL_SUBTITLE_USER_DATA, personalDataPage.getSubtitleUserData().getText());

            //Set name
            nombre = "Android";
            NAME = nombre;
            personalDataPage.setName(NAME);

            Android.hideKeyboardIfVisible();
            setNombre(NAME);

            //Set last name
            apellido = "UITest";
            LASTNAME = apellido;
            personalDataPage.setLastName(LASTNAME);
            Android.hideKeyboardIfVisible();

            //Set dni
            do{
                dni = "9" + String.valueOf(Apps.util.generarNumeroRandom(7));
                existDni = Apps.dbQuery.existDni(dni);
            }while (existDni || dni.length()<8);

            DNI = dni;
            personalDataPage.setDni(DNI);
            Android.hideKeyboardIfVisible();

            switch (Apps.util.generarNumeroRandom(1)){
                case 1: case 2: case 3: case 4:
                    personalDataPage.setMaleGender();
                    break;
                default:
                    personalDataPage.setFemaleGender();
                    break;
            }


            //Click on next button
            personalDataPage.click_on_next_button();


        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }


    @And("Completa pantalla de CUIL")
    public void completar_pantalla_cuil() {
        try {

            CuilPage cuilPage = new CuilPage();

            //Asserts
            Assert.assertTrue(cuilPage.elementExists(CuilPage.getBackOrCloseBtn()));
            Assert.assertEquals(CuilPage.LBL_TITULO_TEXT, cuilPage.get_element_text(CuilPage.getLblTitle()));
            Assert.assertEquals(CuilPage.LBL_SUBTITLE_CUIL_TEXT, cuilPage.getSubtitleCuil().getText().replace("\n", " "));
            Assert.assertEquals(DNI, cuilPage.getDniInput().getText());

            //Click on next button
            cuilPage.click_on_next_button();

        }catch (Exception | AssertionError e){
            e.printStackTrace();
        }
    }

    @And("Completa pantalla de informacion de usuario")
    public void completar_pantalla_usuario() throws SQLException, Exception{
        try{
            boolean existeUsuario;

            String env = util.getEnv();
            String userEnv = "";

            if(env.contentEquals(Util.TEST)){
                userEnv = "wi2";
            }else if(env.contentEquals(Util.DEV)){
                userEnv = "wi1";
            }else if(env.contentEquals(Util.STAGE)){
                userEnv = "sg";
            }else if(env.contentEquals(Util.STAGING)){
                userEnv = "sgi";
            }

            //Esperamos a que cargue la pantalla de informacion del usuario
            UserCreationPage userCreationPage = UserCreationPage.getInstance();


            //Asserts
            Assert.assertEquals(UserCreationPage.LBL_TITULO_TEXT, UserCreationPage.getLblTitle().getText());
            Assert.assertEquals(UserCreationPage.LBL_SUBTITLE_USER_CREATION, userCreationPage.getlblSubtitleUserCreation().getText().replace("\n", " "));


            //Validatar si el suario existe en BD
            do{
                if(userEnv.contentEquals("sgi"))
                    user = userEnv+"user"+email.substring(6,12);
                else
                    user = email.substring(0,11);

                existeUsuario = dbQuery.existeUsuario(user);
            }while (existeUsuario || user.length()<4);

            //Set user
            userCreationPage.set_user(user);

            //Set password
            userCreationPage.set_password(pass);

            //Check if validators are enabled
            wait.until(ExpectedConditions.attributeContains(userCreationPage.getOptDigitsValidator(),"enabled","true"));
            Assert.assertTrue(userCreationPage.elementIsEnabled(userCreationPage.getOptDigitsValidator()));
            Assert.assertTrue(userCreationPage.elementIsEnabled(userCreationPage.getOptUpperCasevalidator()));
            Assert.assertTrue(userCreationPage.elementIsEnabled(userCreationPage.getOptAlphanumericValidator()));
            Assert.assertTrue(userCreationPage.elementIsEnabled(userCreationPage.getOptSpecialCharValidator()));

            //Click on next button
           userCreationPage.click_on_next_button();

        }catch (Exception | AssertionError e){
            e.printStackTrace();
            throw e;
        }
    }



    @And("Completa pantalla de informacion de telefono")
    public void completar_pantalla_telefono() throws SQLException, InterruptedException, Exception {
        try {

            String phoneNumber;
            boolean existPhone;

            PhonePage phonePage = PhonePage.getInstance();


            //Asserts
            Assert.assertEquals(PhonePage.LBL_TITULO_TEXT, PhonePage.getLblTitle().getText());
            Assert.assertEquals(PhonePage.LBL_SUBTITLE_PHONE, phonePage.getSubtitlePhone().getText().replace("\n", " "));

            //Set area code
            Pair<Integer, String> areaCode = util.getAreaCode();
            phonePage.setAreaCode(areaCode.getValue());
            AREA_CODE = areaCode.getValue();

            //Setear numero del telefono
            do {
                phoneNumber = "999" + String.valueOf(util.generarNumeroRandom(areaCode.getKey()));
                existPhone = dbQuery.existPhoneNumber("+549" + AREA_CODE + phoneNumber);
            } while (existPhone);

            phonePage.setPhoneNumber(phoneNumber);

            //PHONE = "+54911" + phoneNumber;
            // cambiar el codigo de area al registrarse
            setNumeroTelefono("+549" + AREA_CODE + phoneNumber);
            NUMBER = phoneNumber;

            //Click on next button
            phonePage.click_on_next_button();


        } catch (Exception | AssertionError e) {
            e.printStackTrace();
            throw e;
        }
    }

    @And("Completa pantalla de codigo sms")
    public void completar_pantalla_codigo_sms() throws IOException, SQLException,Exception {
        try {

            String digito;

            //wait for sms code page
            SmsCodePage smsCodePage = SmsCodePage.getInstance();

            //Asserts
            Assert.assertEquals(SmsCodePage.LBL_TITLE_TEXT, SmsCodePage.getLblTitle().getText());
            Assert.assertEquals(SmsCodePage.LBL_SUBTITLE_PHONE_TEXT, smsCodePage.getSubtitlePhone().getText().replace("\n", " "));
            Assert.assertTrue(smsCodePage.elementExists(smsCodePage.getBackOrCloseBtn()));
            Assert.assertEquals(smsCodePage.get_subtitle_sms_code_text(AREA_CODE,NUMBER).replace(" ",""),
                    smsCodePage.getSubtitleSmsCode().getText().replace("\n", " ").replace(" ",""));

            Android.hideKeyboardIfVisible();

            //Get the sms code from db
            String smsCode = dbQuery.getCodigoSms(PHONE);

            //Set the sms code
            smsCodePage.set_code_sms(smsCode);


        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }


    @And("Completa pantalla de PIN")
    public void completar_pantalla_pin() throws Error,Exception{
        try {
            //Wait for Pin creation page
           PinCreationPage pinCreationPage = PinCreationPage.getInstance();

           //Asserts
            Assert.assertEquals(PinCreationPage.LBL_TITLE_TEXT, PinCreationPage.getLblTitle().getText());
            Assert.assertTrue(Wrapper.getElementText(pinCreationPage.getPinCreationTitle()).contentEquals(PinCreationPage.pin_creation_title) ||
                    Wrapper.getElementText(pinCreationPage.getPinCreationTitle()).contentEquals("Ahora, creá tu PIN"));
            Assert.assertTrue(Wrapper.getElementText(pinCreationPage.getSubtitlePinCreation()).contentEquals(PinCreationPage.pin_creation_subtitle) ||
                    Wrapper.getElementText(pinCreationPage.getSubtitlePinCreation()).contentEquals("Te pediremos este PIN para confirmar tus transacciones"));

            //Set pin
            pinCreationPage.set_pin(PinCreationPage.RAW_PIN);

        }catch (Exception | Error e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @And("Completa pantalla de terminos y condiciones")
    public void completar_pantalla_terminos_condiciones() throws Exception{
        try {

            //Wait for term and conditions page
            TermsConditionsPage termsConditionsPage = TermsConditionsPage.getInstance();

            termsConditionsPage.hideKeyboardIfVisible();

            //Asserts
            Assert.assertEquals(TermsConditionsPage.LBL_TITULO_TEXT, TermsConditionsPage.getLblTitle().getText());
            Assert.assertEquals(TermsConditionsPage.SUBTITLE_TEXT, termsConditionsPage.getLblSubtitle().getText().replace("\n", " "));
            Assert.assertEquals(TermsConditionsPage.TERMS_TEXT, termsConditionsPage.getTerms().getText().replace("\n", " "));
            Assert.assertTrue(termsConditionsPage.elementExists(termsConditionsPage.getBackOrCloseBtn()));

            //Click on check box
            termsConditionsPage.click_checkbox_tac();
            Assert.assertEquals(TermsConditionsPage.CHECKBOX_TEXT, termsConditionsPage.getCheckboxTerms().getText().replace("\n", " "));

            //Click on create account button
            Assert.assertEquals(TermsConditionsPage.BTN_CREATE_ACCOUNT_TEXT, termsConditionsPage.getButton().getText().replace("\n", " "));



        }catch (Exception | AssertionError e){
            logger.error(e);
            throw e;
        }
    }

    @And("Crear cuenta de usuario")
    public void crear_cuenta() throws Exception {
        try {

            TermsConditionsPage.getInstance().click_create_account();

           /* WelcomePage welcomePage = WelcomePage.getInstance();

            //Asserts
            Assert.assertTrue(welcomePage.elementExists(welcomePage.getWelcomeIcon()));
            //welcomePage.waitForElementEnabled(welcomePage.getContinueButton());
            Assert.assertEquals(welcomePage.getWelcomeMessageTitle(NAME), welcomePage.get_element_text(welcomePage.getWelcomeMessage()).replace("\n", " "));
            Assert.assertEquals(WelcomePage.WELCOME_SUBTITLE_TEXT, welcomePage.get_element_text(welcomePage.getWelcomeSubtitle()).replace("\n", " "));

            //Click on continue button
            welcomePage.click_continue_button();*/

            //Wait for dashboard page
            DashboardPage.getInstance();

            //Add user to list user
            //Android.nubiWallet.usuarios.add(Apps.util.obtenerUsuarioByEmail(email));

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @Then("Save user on device")
    public void save_user_on_device() {
      /*  try {

            // Para poder usar este comando VALIDAR uando el celular NO TIENE CUENTA asociada, SOLO la primera vez falla.
            // Android.adb.addContact(NAME + " " + LASTNAME, PHONE);

            //Abrimos la aplicacion de contactos para
            //agregar ek suario creado y operar sobre este
            String app = contactosAgenda.open();

            if (app.contains("samsung")) {

                contactosAgenda.homeSamsung.waitToLoadScreen();

                contactosAgenda.homeSamsung.tapAgregarContacto();

                if (!contactosAgenda.homeSamsung.uiObject.txtTelefono().exists()) {
                    Android.driver.hideKeyboard();
                    contactosAgenda.homeSamsung.uiObject.txtTelefono().waitToAppear(10);
                }

                contactosAgenda.homeSamsung.uiObject.txtTelefono().tap();
                contactosAgenda.homeSamsung.setTlfn(PHONE);

                Android.driver.hideKeyboard();

                contactosAgenda.homeSamsung.uiObject.txtNombre().waitToAppear(10);
                contactosAgenda.homeSamsung.uiObject.txtNombre().tap();
                contactosAgenda.homeSamsung.setNombre(NAME + " " + LASTNAME);
                Android.driver.hideKeyboard();

                contactosAgenda.homeSamsung.tapBtnGuardarContacto();
                contactosAgenda.homeSamsung.uiObject.txtNombre().waitToDisappear(5);

            } else if (app.contains("moto")) {
                contactosAgenda.homeMoto.waitToLoadScreen();

                contactosAgenda.homeMoto.tapAddContact();
                contactosAgenda.homeMoto.uiObject.txtNombre().waitToAppear(10);
                contactosAgenda.homeMoto.setNombre(NAME + " " + LASTNAME);


                contactosAgenda.homeMoto.uiObject.txtTelefono().waitToAppear(10);
                contactosAgenda.homeMoto.setTlfn(PHONE);
                Android.driver.hideKeyboard();

                contactosAgenda.homeMoto.tapSaveButton();
                contactosAgenda.homeMoto.uiObject.txtNombre().waitToDisappear(5);
            }

            contactosAgenda.forceStop();


        } catch (Exception e) {
            util.showTrackTraceError("Unable to save the user on device", e);
        }*/
    }


    /***************************************************************
     *
     * Metodos para casos de regresion
     *
     **************************************************************/

    /**Setear el email*/
    /**@param: email - el email que sera seteado */
    @When("Usuario ingresa el email {string}")
    public void usuario_ingresa_email(String email) throws Exception {
        try{

            if(email.contains("255caracteres"))
                email = email_255_caracteres();

            //Set email
           EmailPage.getInstance().setEmail(email);

           Android.hideKeyboardIfVisible();


        }catch (Exception | Error e){
            e.printStackTrace();
            logger.error(e);
            throw e;
        }
    }


    /**Generar mail de 255 caracteres
     *
     */
    public String email_255_caracteres() {

            int resultado;
            char letra;
            String texto ="";

            for (int i=0; i<257; i++){
                resultado=(int)(Math.random()*26+65);//Sumamos al numero de letras (sin ñ) el valor en ASCII
                letra = (char)resultado;
                texto+=letra;
            }

            return texto;
    }

    @Then("Verificar email de confirmacion")
    public void verifyConfirmationEmail() throws Exception {
        String emailsContent =  YopmailReader.getInstance().getEmailsContent(Registro.email, 1);
        if ( emailsContent != null ) {
            try{
                Assert.assertTrue(Pattern.matches(".*?" + Registro.email + ".*?Confirmar direcci.*n de correo.*", emailsContent.trim()));
            }catch (AssertionError e){
                logger.warn("doesnt match");
            }

        } else {
            logger.warn("Confirmation email not found");
        }

    }

    /*****
     *  Validar mensaje del mail ya registrado
     */
    @Then("Validar pantalla mail ya registrado")
    public void validate_message_on_screen() throws Exception{
        try {

                    ConfirmEmailPage confirmEmailPage = ConfirmEmailPage.getInstance();

                    //Asserts
                    Assert.assertEquals(ConfirmEmailPage.LBL_MESSAGE_REGISTERED, Wrapper.getElementText(confirmEmailPage.getMessage()));
                    Assert.assertEquals(ConfirmEmailPage.LBL_DISCLAIMER_TEXT, confirmEmailPage.get_element_text(confirmEmailPage.getDisclaimer()).replace("\n", " "));


        }catch (Exception | Error e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Then("Validar error en creacion de cuenta")
    public void validate_create_account_error() throws Exception{
        try{
            CommonSteps commonSteps = new CommonSteps();
            TermsConditionsPage.getInstance().click_create_account();
            commonSteps.switchWifiStatus("desactiva");
            System.out.println("h");
        }catch (Exception | Error er){
            logger.error(er);
            throw  er;
        }
    }

    @When("Iniciar registro con la data anterior")
    public void set_used_data_to_register() throws Exception{
        try {

            //Quit onboarding
            Apps.util.jumpOnboardingPage("REGISTER");

            //EmailPage
            EmailPage emailPage = EmailPage.getInstance();
            emailPage.setEmail(email);
            Android.hideKeyboardIfVisible();
            Wrapper.waitForElementEnabled(emailPage.getNextButton());
            emailPage.click_on_next_button();
            Thread.sleep(500);

            //ConfirmEmailPage
            ConfirmEmailPage.getInstance();

            String link = util.getProperty("DeepLinks", "HEADER", "properties") +
                    util.getProperty("DeepLinks", "CONFIRM_MAIL_LINK", "properties");

            ((AndroidDriver)Android.driver).executeScript("mobile: deepLink", ImmutableMap.of("url", link+TOKEN, "package", Android.nubiWallet.packageID()));

          /*  //Api url
            API_Util util = new API_Util();
            String apiurl = util.getEnvUrl(Registration_API.getRegistrationEndpoint());
            ((AndroidDriver)Android.driver).get(apiurl+"/registration/web/registration/confirm/"+TOKEN);*/

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }
}