package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.registro.*;
import core.commons.DBQuery;
import api.apps.android.Wrapper;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import interfaces.Registration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;
import java.util.Set;

public class RGSRegistration implements Registration {

    private Registro registro = new Registro();
    private static String email, textOnElement, auxName, auxLname, auxDni, num, especial,
    userRegistered, smsCode, phoneNumber, sufijo;
    private static String newestUser, oldToken;
    private static DBQuery dbQuery = Apps.dbQuery;
    public static String CUIL;

    static int intGenero, intPrefijo;
    CommonSteps commonSteps = new CommonSteps();

    private static final Logger logger = LogManager.getLogger();

    private static PersonalDataPage personalDataPage = null;
    private static CuilPage cuilPage = null;
    private static UserCreationPage userCreationPage = null;
    private static PhonePage phonePage = null;
    private static SmsCodePage smsCodePage = null;
    private static PinCreationPage pinCreationPage = null;
    private static TermsConditionsPage termsConditionsPage = null;


    @Test
    @When("User set a valid email")
    @And("Usuario ingresa un email valido {string}")
    public void user_set_a_valid_email(String typeEmail) throws SQLException, Exception {
        try{
            boolean existEmail;

            typeEmail = typeEmail.toLowerCase();

            if(typeEmail.toLowerCase().contains("nuevo")){
                do{

                    email = util.gerenarEmail();
                    existEmail = db.existeEmail(email);

                }while (existEmail);
            }

//            if(!nubiWallet.email.uiObject.inputEmail().getText().contentEquals(""))
//                nubiWallet.email.uiObject.inputEmail().clearText();

           EmailPage.getInstance().setEmail(email);
            Android.hideKeyboardIfVisible();

        }catch (Exception | Error e){
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /*@When("User set a new valid email")
    public void user_set_a_new_valid_email_as() {
        try{
            boolean existEmail;

            do{

                email = util.generateArmedEmail();
                existEmail = commonActions.existEmail(email);

            }while (existEmail);

            nubiWallet.email.setEmail(email);

            Thread.sleep(300);

            Android.driver.hideKeyboard();

        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    @When("Usuario ingresa un email ya registrado")
    public void set_alredy_registered_email(){
        try {
            String mail = util.obtenerEmail();

            EmailPage.getInstance().setEmail(mail);

            Android.hideKeyboardIfVisible();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

/*
    @Then("Validate elements on next screen")
    public void validate_elements_on_next_screen(){
        try {
            nubiWallet.registro.confirmarEmail.waitToLoadScreen();

            //Check for lblPrimarymessage present
            status = nubiWallet.registro.confirmarEmail.uiObject.lblMensaje().exists();
            Assert.assertEquals(true, status);

            //Check for lblSecondaryMesg present
            status = nubiWallet.registro.confirmarEmail.uiObject.lblSecondaryMsg().exists();
            Assert.assertEquals(true, status);

            //Check for btnOpenMail present
            status = nubiWallet.registro.confirmarEmail.uiObject.btnAbrirMail().exists();
            Assert.assertEquals(true, status);

            //Check for btnOpenMail enabled
            status = nubiWallet.registro.confirmarEmail.uiObject.btnAbrirMail().isEnabled();
            Assert.assertEquals(true, status);

            //Check for btnUpdateMail enabled
            status = nubiWallet.registro.confirmarEmail.uiObject.btnModificarEmail().isEnabled();
            Assert.assertEquals(true, status);

        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    /**
     * Metodo para pulsar boton para editar el mail
     */
    @When("Pulsar {string} para editar el email")
    public void click_on_back_update_button(String button) {
        try {

            ConfirmEmailPage confirmEmailPage = ConfirmEmailPage.getInstance();

            button =button.toLowerCase();
            //valido el boton a ser pulsado
            switch (button){
                case"back":
                    confirmEmailPage.click_close_back_button();
                    break;
                case "modificar":
                    confirmEmailPage.click_update_mail();
                    break;
            }


                EmailPage.getInstance();


        }catch (Exception | AssertionError e){
            e.printStackTrace();
            //util.showTrackTraceError("Error al presionar "+button+" en pantalla de confirmacion de email", e);
        }
    }

  /*  *//**
     * Click on update button method
     *//*
    @When("Click on update button")
    public void click_on_update_button() {
        try {
            nubiWallet.registro.confirmarEmail.waitToLoadScreen();
            Thread.sleep(300);
            nubiWallet.registro.confirmarEmail.tapBtnModificarEmail();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    @Then("Email ingresado esta en el input")
    public void validate_that_previous_setted_email_is_show() {
        try {

            EmailPage emailPage = EmailPage.getInstance();

            Assert.assertEquals(email, Wrapper.getElementText(emailPage.getEmailInput()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("Validar que nuevo email se muestra en pantalla")
    public void validate_that_newest_setted_email_is_show() {
        try {

            ConfirmEmailPage confirmEmailPage = ConfirmEmailPage.getInstance();

            //Assert
            Assert.assertTrue(confirmEmailPage.get_element_text(confirmEmailPage.getEmailSentDisclaimer()).replace("\n", " ").contains(email));


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /****
     *      Validar que se genere el token
     ****/
     @Then("Un {string} fue generado")
     public void validate_generated_token(String type){
         try{

             if(type.toLowerCase().contentEquals("token")){
                 oldToken = dbQuery.getAccesToken(email);
             }else if (type.toLowerCase().contentEquals("nuevotoken")){
                 String newToken = dbQuery.getAccesToken(email);
                 Assert.assertNotEquals(newToken, oldToken);
             }
         }catch (Exception e){
             util.showTrackTraceError("Error validando el token", e);
         }
     }

    /*******************************************
     *   STEPS PARA PANTALLA DE INFORMACION DE DATOS PERSONALES
     ******************************************/


    @When("Ingresar nombre {string}")
    public void user_set_name(String name) throws Exception{
        try {
           PersonalDataPage personalDataPage = PersonalDataPage.getInstance();

            auxName = Wrapper.getElementText(personalDataPage.getTxtName());

            if(name.contentEquals("") && !auxName.toUpperCase().contentEquals("¿CUÁL ES TU NOMBRE")) {
                personalDataPage.setName(name);
            }else if(name.contentEquals("") && auxName.toUpperCase().contentEquals("¿CUÁL ES TU NOMBRE")){

            }else if(auxName.contentEquals(name)){

            }else if(name.toLowerCase().contentEquals("especialchar")){
                especial = "Be\u00f1at";
                personalDataPage.setName(especial);
            }else{
                personalDataPage.setName(name);
            }

            Android.hideKeyboardIfVisible();

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @When("Ingresar apellido {string}")
    public void user_set_lname(String lname){
        try {

            personalDataPage = PersonalDataPage.getInstance();

            auxLname = Apps.wrapper.get_element_attribute(personalDataPage.getTxtLastName(), "text");

            if (lname.contentEquals("") && !auxLname.toUpperCase().contentEquals("¿tu apellido?")) {
                personalDataPage.setLastName(lname);
            }else if(lname.contentEquals("") && !auxLname.toUpperCase().contentEquals("¿tu apellido?")){

            }else if(auxLname.contentEquals(lname)){

            }else if(lname.toLowerCase().contentEquals("especialchar")){
                especial = "Oca\u00f1a";
                personalDataPage.setLastName(especial);
            }else{
                personalDataPage.setLastName(lname);
            }

            Android.hideKeyboardIfVisible();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @When("Ingresar DNI {string}")
    public void user_set_dni(String dni){
        try {

            personalDataPage = PersonalDataPage.getInstance();

            auxDni = personalDataPage.get_element_attribute(personalDataPage.getDniInput(), "text");

            num = "N\u00famero";

            if(dni.contentEquals("") && !auxDni.toUpperCase().contentEquals("Y, ¿tu DNI?")) {
                personalDataPage.setDni(dni);
            }else if(dni.contentEquals("") && auxDni.toUpperCase().contentEquals("Y, ¿tu DNI?")){

            }else if(auxDni.contentEquals(dni)){

            }else if(dni.toLowerCase().contentEquals("random")){
                boolean existDni;
                do{
                    dni = "9" + String.valueOf(Apps.util.generarNumeroRandom(7));
                    existDni = Apps.dbQuery.existDni(dni);
                }while (existDni || dni.length()<8);

                //set dni
                personalDataPage.setDni(dni);
            }else if(dni.toLowerCase().contentEquals("existente")){
                personalDataPage.setDni(util.obtenerDni());
                CUIL = util.obtenerCuil();
            }else{
                personalDataPage.setDni(dni);
            }

            Android.hideKeyboardIfVisible();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @When("Seleccionar genero {string}")
    public void tap_genero(String genero) throws Exception{
        try {
            genero.toLowerCase();

                personalDataPage = PersonalDataPage.getInstance();

            switch (genero){
                case "h":
                    personalDataPage.setMaleGender();
                    intGenero = 0;
                    intPrefijo = 20;
                    break;
                case "m":
                case "f":
                    personalDataPage.setFemaleGender();
                    intGenero = 1;
                    intPrefijo = 27;
                    break;
            }

        }catch (Exception | Error e){
           logger.error(e.getMessage());
           throw e;
        }
    }

    /****
     * STEP FOR CUIL SCREEN
     */
    @When("Validar que el prefijo es {string}")
    public void validarPrefijoCuil(String prefijo) throws InterruptedException, Exception{
        try {
            CuilPage cuilPage = CuilPage.getInstance();

            switch (prefijo){
                case "20":
                case "27":
                case "23":
                case "24":
                    Assert.assertEquals(prefijo, cuilPage.getTxtPrefixDni().getText());
                    break;
                default:
                    if(prefijo.toLowerCase().contentEquals("cambiar a 23")){
                        cambiar_prefijo("23");
                        validarPrefijoCuil("23");
                    }

                    if(prefijo.toLowerCase().contentEquals("cambiar a 24")){
                        cambiar_prefijo("24");
                        validarPrefijoCuil("24");
                    }

                    break;
            }

            sufijo = cuilPage.getTxtVerificationCode().getText();

        }catch (Exception |  Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @When("Cambiar prefijo a {string}")
    public void cambiar_prefijo(String prefijo) throws InterruptedException, Exception{
        try {


                cuilPage = CuilPage.getInstance();

            boolean error = false;
            //presionamos txt de prefijo
            cuilPage.getTxtPrefixDni().click();

            //Wait for list
            cuilPage.waitForElementVisibility(cuilPage.getList());

            /*boolean list;
            int i = 0;
            do {
                list = Android.driver.findElement(By.xpath(TextList.BUTTON_SHEET_PREFIJOS_XPATH.getText())).isDisplayed();
                i++;
            }while (!list && i<4);

            if(i==4)*/
                //throw new InterruptedException("La lista de prefijos no esta disponible");


            switch (prefijo){
                case "20":
                    if(cuilPage.elementExists(cuilPage.getPrefix("20")))
                        cuilPage.getPrefix("20").click();
                    else
                        error = true;

                    break;
                case "23":

                    if(cuilPage.elementExists(cuilPage.getPrefix("23")))
                        cuilPage.getPrefix("23").click();
                    else
                        error = true;
                    break;
                case "24":
                    if(cuilPage.elementExists(cuilPage.getPrefix("24")))
                        cuilPage.getPrefix("24").click();
                    else
                        error = true;
                    break;
                case "27":
                    if(cuilPage.elementExists(cuilPage.getPrefix("27")))
                        cuilPage.getPrefix("27").click();
                    else
                        error = true;
                    break;
                default:
                    //System.out.println("No se encuentra listado el prefijo: "+prefijo);
                    error = true;
                    break;
            }

            if(error){
                commonSteps.press_back_button();
                throw new AssertionError("No se encuentra listado el prefijo: "+prefijo);
            }

            CuilPage.getInstance();

        }catch(Exception | Error e){

          logger.error(e);
            throw e;
        }
    }

    @Then("Validar cambio en codigo verificador")
    public void validar_cambio_codigo_verificador() throws Exception{
        try {


                cuilPage= CuilPage.getInstance();

            Assert.assertNotEquals(sufijo, cuilPage.get_element_attribute(cuilPage.getTxtVerificationCode(), "text"));
        }catch (Exception | Error e){
            logger.error(e);
            e.printStackTrace();
            throw e;
        }
    }

    /*******************************************
     *   STEPS FOR user INFORMATION SCREEN
     ******************************************/

    @When("Ingresar nombre de usuario {string}")
    public void user_set_his_user_as(String user) throws Exception {
        try {
            String auxUser;
            boolean auxExist;

            UserCreationPage userCreationPage = UserCreationPage.getInstance();

            if(user.toLowerCase().contentEquals("random") || user.toLowerCase().contentEquals("random2")){
                //Validate if user exist on bd
                do{
                    auxUser = "user"+Apps.util.generarNumeroRandom(4);
                    auxExist = Apps.dbQuery.existeUsuario(auxUser);
                }while (auxExist || auxUser.length()<4);

                //Set user name
                userCreationPage.set_user(auxUser);
                newestUser = auxUser;

            }else{
                userCreationPage.set_user(user);
            }

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }
    @When("Ingresar password {string}")
    public void set_his_password_as(String pass) throws Exception{
        try {

            UserCreationPage userCreationPage = UserCreationPage.getInstance();

            //Set Password
            userCreationPage.set_password(pass);

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @When("Usuario ingresa username ya registrado")
    public void set_registered_user() throws Exception{
        try {
            // userRegistered = dbQuery.returnUser();
            userRegistered = util.obtenerUserName();
           Thread.sleep(200);

           userCreationPage = UserCreationPage.getInstance();
           userCreationPage.set_user(userRegistered);

        }catch (Exception | AssertionError e){
            logger.error(e);
            e.printStackTrace();
            throw e;
        }
    }

    @Then("Validar persistencia de usuario {string} y password vacio")
    public void validate_user_persistence(String user) throws Exception{
        try {

           UserCreationPage  userCreationPage = UserCreationPage.getInstance();

            String auxUser = Wrapper.getElementText(userCreationPage.getUserInput());

            Assert.assertEquals(newestUser,auxUser);

            Assert.assertTrue(Wrapper.get_element_attribute(userCreationPage.getPasswordInput(), "text").contentEquals("Creá una contraseña"));

        }catch (Exception | Error e){
           logger.error(e);
           e.printStackTrace();
           throw e;
        }
    }

    /*******************
     * STEPS FOR CUIL SCREEN
     *****************/


    /*******************
     * STEPS FOR PHONE NUMBER SCREEN
     *****************/

    @When("Ingresar codigo de area {string}")
    public void SetAreaCode(String code) throws Exception{
        try {

           //Set area code
           PhonePage.getInstance().setAreaCode(code);

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @When("Ingresar numero telefonico {string}")
    public void set_his_phone_number_as(String number)throws Exception{
        try {
            String pNumber;
            boolean exist;

            PhonePage phonePage = PhonePage.getInstance();

            if(number.toLowerCase().contentEquals("random")) {

                //Set number phone
                do {
                    pNumber = "999" + String.valueOf(Apps.util.generarNumeroRandom(5));
                    exist = Apps.dbQuery.existPhoneNumber(pNumber);
                } while (exist || pNumber.length() < 8);

                phonePage.setPhoneNumber(pNumber);
            }else{
                phonePage.setPhoneNumber(number);
            }

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @Then("El campo {string} tiene solo la longitud valida")
    public void validate_the_input_field_has_only_the_permit_lenght(String campo) {
        try {
            int length;

                phonePage = PhonePage.getInstance();

            if(campo.toLowerCase().contentEquals("codigo")){
                length = 4;
                Assert.assertEquals(length, Wrapper.getElementText(phonePage.getAreaCodeInput()).length());
            }else if(campo.toLowerCase().contentEquals("numero")){
                length = 8;
                Assert.assertEquals(length, Wrapper.getElementText(phonePage.getPhoneInput()).length());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("validate ui elements")
    public void validate_ui_elements(){
        try {
            //Assert.assertTrue(nubiWallet.registro.codigoSms.uiObject.lblSubtituloCodigoSms().exists());

            //Assert.assertTrue(nubiWallet.registro.numeroTel.uiObject.inputFirstDigitCode().exists());

           // Assert.assertTrue(nubiWallet.registro.codigoSms.uiObject.lnkReenviarCodigo().exists());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @When("Ingresar codigo SMS {string}")
    public void user_set_sms_code_as(String smscode) throws Exception{
        try {

            Registro registro = new Registro();
            String digit;

             smsCodePage = SmsCodePage.getInstance();


            char[] auxSmsCode = {'a', 'b', 'c', 'd'};

            if(smscode.length()<4) {
                auxSmsCode[0] = smscode.charAt(0);
                auxSmsCode[1] = smscode.charAt(1);
                auxSmsCode[2] = smscode.charAt(2);
                auxSmsCode[3] = 'x';
            }else if(smscode.toLowerCase().contains("correcto")){
                String auxSmsCodigo = dbQuery.getCodigoSms(registro.getNumeroTelefono());
               smsCodePage.set_code_sms(auxSmsCodigo);
            }else{
                smsCodePage.set_code_sms(smscode);
            }


        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @Then("Usuario ingresa un codigo sms viejo")
    public void set_old_sms_code(){
        try {
            user_set_sms_code_as(smsCode);
        }catch (Exception e){
            e.printStackTrace();
            logger.error('\n'+"Unable to set old sms code");
        }
    }


    @When("tap reenviar codigo sms")
    public void tap_resend_link(){
        try{

                smsCodePage = SmsCodePage.getInstance();

            //Old sms code
            //El numero lo obtengo desde la clase registro, que fue la que genero el numero
            Registro registro = new Registro();
            smsCode = Apps.dbQuery.getCodigoSms(registro.getNumeroTelefono());

            smsCodePage.click_resend_sms_code();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("Validar que un nuevo codigo sms fue generado")
    public void validate_new_sms_code(){
        try{

            //Obtener el nuevo codigo sms
            //El numero lo obtengo desde la clase registro, que fue la que genero el numero
            Registro registro = new Registro();
            String auxSmsCode = Apps.dbQuery.getCodigoSms(registro.getNumeroTelefono());

            Assert.assertNotEquals(smsCode, auxSmsCode);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /***********************
     *  STEPS FOR pin SCREEN
     **********************/
  /*    @Given("Phone number screen is completed until code sms screen")
    public void phone_number_screen_is_completed_until_code_sms_screen(){
        try {

            boolean existPhone;

            nubiWallet.registro.numeroTel.waitToLoadScreen();

            //Set code phone
            nubiWallet.registro.numeroTel.setCodigoTelefono("11");

            //Set number phone
            do {
                phoneNumber = "999" + String.valueOf(util.generarNumeroRandom(5));
                existPhone = dbQuery.existPhoneNumber(phoneNumber);
            } while (existPhone || phoneNumber.length() < 8);

            nubiWallet.registro.numeroTel.setNumeroTelefono(phoneNumber);

            commonActions.hideKeyBoard();
            nubiWallet.registro.numeroTel.uiObject.btnCirculoSiguiente().waitToAppear(5);

            Assert.assertTrue(nubiWallet.registro.numeroTel.uiObject.btnCirculoSiguiente().isEnabled());

            nubiWallet.registro.numeroTel.tapBtnCirculoSiguiente();

            nubiWallet.registro.numeroTel.uiObject.txtNumeroTelefono().waitToDisappear(5);

            //sms code screen
            String smsCode = dbQuery.getCodigoSms("+54911"+phoneNumber);

            user_set_sms_code_as(smsCode);

            nubiWallet.registro.codigoSms.uiObject.lnkReenviarCodigo().waitToDisappear(5);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
*/
/*    @Then("Validate ui elements on pin screen")
    public void validate_ui_elements_on_pin_screen(){
        try {
            PinPage pinPage = new PinPage();
            Assert.assertFalse(pinPage.getTitleText().isEmpty());
            //Assert.assertTrue(nubiWallet.registro.pin.uiObject.textTitle().exists());

            //Assert.assertTrue(nubiWallet.registro.numeroTel.uiObject.inputFirstDigitCode().exists());

        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    @When("Usuario ingresa pin {string}")
    public void user_set_pin(String pin) throws Exception{
        try {
                pinCreationPage = PinCreationPage.getInstance();

            char[] auxSmsCode = {'a', 'b', 'c', 'd'};

            if(pin.length()<4) {
               pinCreationPage.set_pin("123 ");
            }else if(pin.toLowerCase().contains("correcto")){
                //String auxSmsCodigo = dbQuery.getCodigoSms(registro.getNumeroTelefono());
                pinCreationPage.set_valid_pin();
            }else{
                boolean isNumeric = false;
                try {
                    Integer.parseInt(pin);
                    isNumeric = true;
                }catch (NumberFormatException err){
                    isNumeric = false;
                }
                if(isNumeric)
                    pinCreationPage.set_pin(pin);
                else
                    pinCreationPage.set_pin("    ");
            }
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    /***********************
     *  STEPS FOR terms and conditions screen
     **********************/
   /* @Then("Validate ui elements on terms and conditions screen")
    public void validate_elements_on_terms_and_and_conditions(){
        try {
            Assert.assertTrue(nubiWallet.registro.tyc.uiObject.chkTerminosCondiciones().exists());
            Assert.assertTrue(nubiWallet.registro.tyc.uiObject.chkTerminosCondiciones().exists());
            Assert.assertTrue(nubiWallet.registro.tyc.uiObject.btnCrearCuenta().exists());
            Assert.assertFalse(nubiWallet.registro.tyc.uiObject.btnCrearCuenta().isEnabled());
            Assert.assertFalse(nubiWallet.registro.tyc.uiObject.chkTerminosCondiciones().isChecked());
            Assert.assertFalse(nubiWallet.registro.tyc.uiObject.chkPE().isChecked());
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    @Given("Usuario pulsa check de {string}")
    public void user_tap_check_button(String chkBoton) throws Exception{
        try {

            chkBoton = chkBoton.toLowerCase();

                termsConditionsPage = TermsConditionsPage.getInstance();

            switch (chkBoton){
                case "tyc":
                    if(!Wrapper.elementIsChecked(termsConditionsPage.getCheckboxTerms()))
                        termsConditionsPage.click_checkbox_tac();

                    break;
            }

        }catch (Exception | Error e){
            logger.error(e);
           throw e;
        }
    }

    @Given("Usuario pulsa link terminos y condiciones")
    public void user_tap_link_tyc() throws Exception{
        try {

            termsConditionsPage = TermsConditionsPage.getInstance();

           termsConditionsPage.click_tyc_link();
           Thread.sleep(600);

           logger.info("before siwtching: " + ((AndroidDriver)Android.driver).getContext());

           Set<String> context = ((AndroidDriver)Android.driver).getContextHandles();

           for(String c : context){
               if(c.toUpperCase().contains("WEBVIEW")){
                   ((AndroidDriver)Android.driver).context(c);
                   break;
               }
           }

            logger.info("After siwtching: " + ((AndroidDriver)Android.driver).getContext());

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @Then("Validar status de boton crear cuenta {string}")
    public void validate_status_create_button(String status) throws Exception{
        try {
            status = status.toLowerCase();

                termsConditionsPage = TermsConditionsPage.getInstance();

            if(status.contentEquals("t")){
                Wrapper.waitForElementEnabled(termsConditionsPage.getButton());
            }else{
                Wrapper.waitForElementDisabled(termsConditionsPage.getButton());
            }
        }catch (Exception |Error e){
            logger.error(e);
            throw e;
        }
    }

    @When("Usuario pulsa crear cuenta")
    public void user_tap_o_create_account_button() {
      try {

            TermsConditionsPage.getInstance().click_create_account();

      }catch (Exception | AssertionError e){
          e.printStackTrace();
      }
    }

    @Then("Validar creacion de cuenta")
    public void user_sees_splash_welcome_screen() throws Exception {
        try {

            /*WelcomePage welcomePage = WelcomePage.getInstance();

            //Asserts
            Assert.assertTrue(Wrapper.elementExists(welcomePage.getWelcomeIcon()));
            //welcomePage.waitForElementEnabled(welcomePage.getContinueButton());
            Assert.assertEquals(welcomePage.getWelcomeMessageTitle(registro.getNombre()), welcomePage.getWelcomeMessage().getText().replace("\n", " "));

            //Click on continue button
            welcomePage.click_continue_button();*/

            //Wait for dashboard page
            DashboardPage.getInstance();

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @Then("Validar CUIL duplicado")
    public void validate_cuil_duplicated(){
        try {

           String msj = Wrapper.getElementText(CuilPage.getSnackbar_text());

           //cuilPage.click_on_snackbar();

           Assert.assertEquals(CuilPage.DUPLICATED_CUIL_TEXT, msj);

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @Then("Validar ingreso a terminos y condiciones")
    public void validate_tyc_screen(){
        try{
            Set<String> contextNames = Android.driver.getContextHandles();
            String contextWeb = "";

            for (String contextName : contextNames) {
                if(!contextName.contentEquals("NATIVE_APP")){
                    contextWeb = contextName;
                    break;
                }
            }

            Android.driver.context(contextWeb);

            WebElement
                    logo,
                    title,
                    subtitle;

            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until((ExpectedConditions.visibilityOf(Android.driver.findElement(By.className("header-container")))));

        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }
}
