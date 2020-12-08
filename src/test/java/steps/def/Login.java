package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.login.OnboardingPage;
import api.apps.android.nw.model.User;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.contrasenia.NewPasswordPage;
import api.apps.android.nw.contrasenia.PasswordRecoveryLandingPage;
import api.apps.android.nw.contrasenia.PasswordRecoveryMailPage;
import api.apps.android.nw.contrasenia.PasswordRecoverySuccessPage;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.login.LoginPage;
import com.github.javafaker.App;
import core.Util;
import core.commons.DBQuery;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import rp.com.google.common.collect.ImmutableMap;

public class Login extends NubiWallet {

    private static NubiWallet nubiWallet = Android.nubiWallet;
    private static Util util = Apps.util;
    private static DBQuery dbQuery = new DBQuery();
    private static String SNACKBAR_TEXT = null;

    public static User usuario = null;

    private static final Logger logger = LogManager.getLogger();


    @Test
    @And("Iniciar sesion")
    public void log_in() throws Exception {

        Apps.util.jumpOnboardingPage("LOGIN");

        NubiWallet.usuario = usuario;
        nubiWallet.setUserData(usuario);

        if ( usuario != null ) {
            //Log into the App
            LoginPage.getInstance().login(usuario.getUsername(), usuario.getRawPassword());
            logger.info(usuario.getUsername());
            DashboardPage.getInstance();

            //Set balance
            CommonStepsMovements cMovements = new CommonStepsMovements();
            cMovements.setBalance();
        }
    }

    @When("Usuario {string} inicia sesion")
    public void setear_credenciales(String usr) throws Exception{
        try{

           Apps.util.jumpOnboardingPage("LOGIN");

            LoginPage.getInstance();

            if(!usr.contentEquals("")){
                if(!usr.toUpperCase().contentEquals("DESTINATION_USER"))
                        usuario = Apps.util.obtenerUsuario(usr);
                else
                    usuario = CommonStepsMovements.destinationUser;

                NubiWallet.usuario = usuario;
                nubiWallet.setUserData(usuario);

                if(usr.toUpperCase().contentEquals("CON_CONTACTO_NUBI") || usr.toUpperCase().contentEquals("CON_CONTACTO_BANCARIO"))
                    CommonStepsMovements.originUser = usuario;

                if ( usuario != null ) {
                    //Pre set user data before login into the app
                    //Apps.util.setPreUserData(usuario);

                    //Log into the App
                    LoginPage.getInstance().login(usuario.getUsername(),usuario.getRawPassword());
                    logger.info(usuario.getUsername());
                    DashboardPage.getInstance();

                    //Set balance
                    CommonStepsMovements cMovements = new CommonStepsMovements();
                    cMovements.setBalance();
                } else {
                    throw new Exception("No se encontro ningun usuario para el tipo: " + usr);
                }
            }
        }
        catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @Given("Iniciar sesion {string}")
    public void validar_iniciar_sesion(String opc) throws Exception{
        try {
            opc = opc.toUpperCase();
            switch (opc){
                case "SI":
                    LoginPage loginPage = new LoginPage();
                    loginPage.login(usuario.getUsername(), usuario.getRawPassword());
                    break;
                default:
                    break;
            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    @Then("User sees dashboard screen")
    public void showDashboardScreen(){
        try {
            DashboardPage.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Given("Pulsar registrar {string}")
    public void tap_registrarme(String opc) throws Exception, Error{
        try{

            int i = 0;
             if(opc.toUpperCase().contentEquals("TRUE") || opc.toUpperCase().contentEquals("SI")){
                 Apps.util.jumpOnboardingPage("REGISTER");
                 //LoginPage.getInstance().clickRegister();
            }

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @When("Usuario pulsa olvide contrasenia")
    public void tap_olvide_contrasenia() throws Exception{
        try{
            //Jump onboarding page
            Apps.util.jumpOnboardingPage("LOGIN");

            //Pulsar olvido de contrasenia
            LoginPage.getInstance().clickForgotPassword();

        }catch (Exception | AssertionError e){
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * cambio de clave
     */
    @And("Ingresa email {string}")
    public void set_email(String email) throws Exception{
        try{

            if(!nubiWallet.usuarios.isEmpty()){
                usuario = nubiWallet.usuarios.get(0);
                PasswordRecoveryLandingPage passwordRecoveryLandingPage = PasswordRecoveryLandingPage.getInstance();

                //Asserts
                Assert.assertEquals(PasswordRecoveryLandingPage.TITLE_TEXT, passwordRecoveryLandingPage.get_element_text(PasswordRecoveryLandingPage.getLblTitle()).replace("\n", " "));
                Assert.assertEquals(PasswordRecoveryLandingPage.SUBTITLE_TEXT, passwordRecoveryLandingPage.get_element_text(passwordRecoveryLandingPage.getLblSubtitle()).replace("\n", " "));
                Assert.assertFalse(passwordRecoveryLandingPage.elementIsEnabled(passwordRecoveryLandingPage.getBtnSend()));

                switch (email.toUpperCase()){
                    case "CORRECTO":
                        passwordRecoveryLandingPage.setEmail(usuario.getEmail());
                        passwordRecoveryLandingPage.hideKeyboardIfVisible();

                        //Send button must be enabled at this time
                        Assert.assertTrue(passwordRecoveryLandingPage.elementIsEnabled(passwordRecoveryLandingPage.getBtnSend()));
                        break;
                }

            }else {
                logger.warn("No se cargo ningun usuario para la prueba");
                throw new Exception("Sin usuario para la prueba");
            }

        }catch (Exception | AssertionError e){
            e.printStackTrace();
            throw e;
        }
    }


    @And("Tap recuperar contrasenia")
    public void tap_recover_password() throws Exception{
        try {
            PasswordRecoveryLandingPage.getInstance().click_send_button();

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @And("Ir al link de recuperacion de clave")
    public void ir_link_recuperar_clave() throws Exception {
        try {
            PasswordRecoveryMailPage passMailPage = PasswordRecoveryMailPage.getInstance();

            //Asserts
            Assert.assertEquals(PasswordRecoveryMailPage.TITLE_TEXT, passMailPage.get_element_text(passMailPage.getForgotPasswordTitle()));
            Assert.assertTrue(passMailPage.elementExists(passMailPage.getSendIcon()));
            Assert.assertEquals(passMailPage.get_disclaimer_text(usuario.getEmail()), passMailPage.get_element_text(
                    passMailPage.getUserEmail()
            ).replace("\n", " "));
            Assert.assertTrue(passMailPage.elementIsEnabled(PasswordRecoveryMailPage.getGoToEmailButton()));

            //Get recovery token
            String token = dbQuery.returnPassRecoveryToken(usuario.getId());

            Util util = Apps.util;

            String link = util.getProperty("DeepLinks", "HEADER", "properties") +
                    util.getProperty("DeepLinks", "RESET_PASSWORD_LINK", "properties");
            //(AndroidDriver)
            ((AndroidDriver)Android.driver).executeScript("mobile: deepLink", ImmutableMap.of("url", link+token, "package", Android.nubiWallet.packageID()));

            Android.hideKeyboardIfVisible();

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }


    @And("Ingresa clave {string}")
    public void set_password(String clave) throws Exception{
        try {
            Android.hideKeyboardIfVisible();
            Android.hideKeyboardIfVisible();
            Android.hideKeyboardIfVisible();
            NewPasswordPage newPasswordPage = NewPasswordPage.getInstance();
            Android.hideKeyboardIfVisible();

            //Asserts
            Assert.assertEquals(NewPasswordPage.TITLE_TEXT, newPasswordPage.get_element_text(newPasswordPage.getForgotPasswordTitle()));
            Assert.assertEquals(NewPasswordPage.SUBTITLE_TEXT, newPasswordPage.get_element_text(newPasswordPage.getLblSubtitle()).replace("\n", " "));

            newPasswordPage.set_password(clave);
            newPasswordPage.hideKeyboardIfVisible();

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @And("Validar clave {string}")
    public void password_type_validation(String passwordType) throws Exception{
        try {

            NewPasswordPage newPassword = NewPasswordPage.getInstance();

            switch (passwordType.toUpperCase()){
                case"CORRECTA":
                    newPassword.waitForElementEnabled(newPassword.getDigitsValidatorTitle());
                    Assert.assertTrue(newPassword.elementIsEnabled(NewPasswordPage.getButton()));
                    Assert.assertTrue(newPassword.elementIsEnabled(NewPasswordPage.getUppercaseValidatorTitle()));
                    Assert.assertTrue(newPassword.elementIsEnabled(NewPasswordPage.getAlphanumericValidatorTitle()));
                    Assert.assertTrue(newPassword.elementIsEnabled(NewPasswordPage.getSpecialCharacterValidatorTitle()));

                    break;
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Metodo para iniciar sesion con el emisor o el receptor de una solicitud p2p sin tener que hacer una llamada a DB
     * @param userType puede receptor o emisor
     * @throws Exception
     */
    @And("{string} de solicitud p2p inicia sesion")
    public void logInByP2PSenderOrReceptorUser(String userType) throws Exception {
        try{
            LoginPage loginPage = LoginPage.getInstance();
            String user = "";
            if(userType.toUpperCase().contentEquals("RECEPTOR"))
                user = RGSMovements.getReceptorUser();
            else if(userType.toUpperCase().contentEquals("EMISOR"))
                user = RGSMovements.getSenderUser();

            loginPage.login(user, util.getProperty("credentials","user.password","properties"));
        }
        catch (Error error){
            error.printStackTrace();
            throw new Exception();
        }
    }


    @Then("Validar cambio de contrasenia")
    public void validate_password_recovery() throws Exception {
        try {
            Android.hideKeyboardIfVisible();
            Android.hideKeyboardIfVisible();
            Android.hideKeyboardIfVisible();
            PasswordRecoverySuccessPage successPage = PasswordRecoverySuccessPage.getInstance();

            //Asserts
            successPage.waitForElementEnabled(PasswordRecoverySuccessPage.getMainButton());

            Assert.assertEquals(PasswordRecoverySuccessPage.MESSAGE_TEXT, successPage.get_element_text(successPage.getMessage()).replace("\n", " "));
            Assert.assertEquals(PasswordRecoverySuccessPage.SECONDARY_MESSAGE, successPage.get_element_text(successPage.getSecondaryMessage()).replace("\n", " "));
            Assert.assertTrue(successPage.elementExists(successPage.getEmoji()));


            //Click finish
            successPage.click_finish_button();

        }catch (Exception | Error err){
            logger.error("Hubo un error al finalizar el proceso de recuperacion de contrasenia");
            logger.error(err);
            throw err;
        }
    }

    @When("Usuario intenta loguearse {int} veces")
    public void login_attempts(int attempts) throws Exception {
        try {

            Apps.util.jumpOnboardingPage("LOGIN");

            LoginPage loginPage = LoginPage.getInstance();


            if(!Android.nubiWallet.usuarios.isEmpty()){
                Login.usuario = Android.nubiWallet.usuarios.get(0);

                //set false invalidate login, if is the case
                //0 is for invalidated login field
                Apps.dbQuery.update_has_invalidated_field(Login.usuario.getUsername(), false, 0);

                loginPage.typeUsername(usuario.getUsername());
                loginPage.typePassword( String.valueOf(util.generarNumeroRandom(8)));
                loginPage.hideKeyboardIfVisible();
                for(int i = 0; i < attempts; i++ ){
                    loginPage.clickLogin();
                    if(i==attempts-1){
                        loginPage.waitForElementVisibility(loginPage.getSnackbar_text());
                        SNACKBAR_TEXT = loginPage.get_element_text(loginPage.getSnackbar_text());
                    }else{
                        loginPage.waitForElementVisibility(loginPage.getTextinput_error());
                    }

                }
            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }


    @Then("Validar que el intento de login esta {string}")
    public void validate_login_status(String status) throws Exception{
        try {
            switch (status.toUpperCase()){
                case "BLOCKED":
                    Assert.assertEquals("T", Apps.dbQuery.get_login_status(Login.usuario.getUsername()).toUpperCase());
                    break;
                case "UNBLOCKED":
                    Assert.assertEquals("F", Apps.dbQuery.get_login_status(Login.usuario.getUsername()).toUpperCase());
                    break;
            }
        }catch (Exception | Error er){
            logger.error(er);
            throw  er;
        }
    }

    @Then("Validar redirecionamiento del mensaje de bloqueo de login")
    public void validate_redirectionOnBlockedMessage() throws Exception{
        try {
           LoginPage loginPage = LoginPage.getInstance();

            //Assert snackbar text
            Assert.assertEquals(LoginPage.SNACKBAR_TEXT, SNACKBAR_TEXT);

           //Click login button to show snackbar
            //loginPage.waitForElementVisibility(loginPage.getLoginBtn());
            loginPage.clickLogin();


           //Wait snackbar
            //loginPage.waitForElementVisibility(loginPage.getSnackbar_action());
            LoginPage.getInstance().click_snackbar_action();

        }catch (Exception | Error er){
            logger.error(er);
            throw  er;
        }
    }

    @When("Usuario realiza proceso de recupero de contrasenia")
    public void do_password_recovery_procces() throws Exception{
        try {
            PasswordRecoveryLandingPage.getInstance();

            set_email("CORRECTO");
            tap_recover_password();
            ir_link_recuperar_clave();
            set_password("Test-0000");
            password_type_validation("CORRECTA");
            CommonSteps commonSteps = new CommonSteps();
            commonSteps.click_on_next_circle_button("cambiopass");
            validate_password_recovery();

        }catch (Exception | Error er){
            logger.error(er);
            throw  er;
        }
    }


    @And("Usuario puede loguearse")
    public void log_on_wallet()throws Exception{
        try {
           LoginPage loginPage = LoginPage.getInstance();
           loginPage.typeUsername(usuario.getUsername());
           loginPage.typePassword("Test-0000");
           loginPage.hideKeyboardIfVisible();
           loginPage.clickLogin();

            DashboardPage.getInstance();

        }catch (Exception | Error er){
            logger.error(er);
            throw  er;
        }
    }
}
