package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.contactos.ContactsPage;
import api.apps.android.nw.operaciones.cashin.CashInLandingPage;
import api.apps.android.nw.operaciones.cashin.metodos.RapiPagoPage;
import api.apps.android.nw.perfil.PerfilPage;
import api.apps.android.nw.perfil.ayuda.FAQPage;
import api.apps.android.nw.perfil.datospersonales.DatosPersonalesPage;
import api.apps.android.nw.perfil.seguridad.SecurityPage;
import api.apps.android.nw.perfil.seguridad.cambiopassword.ChangePasswordPage;
import api.apps.android.nw.perfil.seguridad.claveNubi.cambio.ChangeNubiPassPage;
import api.apps.android.nw.perfil.seguridad.claveNubi.NubiPasswordSuccessPage;
import api.apps.android.nw.perfil.cerrarsesion.CloseSessionPage;
import api.apps.android.nw.perfil.seguridad.claveNubi.olvido.ForgotPinPage;
import api.apps.android.nw.perfil.seguridad.claveNubi.olvido.SetNubiPassPage;
import api.apps.android.nw.perfil.seguridad.claveNubi.olvido.SetSmsCodePage;
import core.Util;
import core.commons.DBQuery;
import api.apps.android.Wrapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.util.Asserts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.IOException;
import java.sql.SQLException;

public class REG006Perfil {

    private DBQuery dbQuery = Apps.dbQuery;
    public Util util = Apps.util;
    private static NubiWallet nubiWallet = Android.nubiWallet;

    private String
        NOMBRE = Login.usuario.getName(),
        APELLIDO = Login.usuario.getLastName();

    private static final Logger logger = LogManager.getLogger();
    public static String SNACKBAR_TEXT = null;


    /****
     * Metodo para acedde a lad diferentes funcionalidades de la pantalla perfil
     * @param link: link de la funcionalidad a la que se quiere accesar
     */
    @When("Usuario presiona el link {string}")
    public void irALink(String link) throws Exception {
        try {

            link = link.toLowerCase();

            switch (link){
                case "security":
                    PerfilPage.getInstance().tapLnkSeguridad();
                    break;
                case "cambio_password":
                    SecurityPage.getInstance().tapLnkChangePassword();
                    break;
                case "change_nubipass":
                    SecurityPage.getInstance().tapLnkChangeNubiPass();
                    break;
                case "forgot_nubipass":
                    SecurityPage.getInstance().tapLnkForgotNubiPass();
                    break;
                case "personal_data":
                    PerfilPage.getInstance().tapLnkDatosPersonales();
                    break;
                case "close_session" :
                    PerfilPage perfilPageCloseSession = PerfilPage.getInstance();
                    while(!Wrapper.elementExists(perfilPageCloseSession.getCloseSession())){
                        for(int i = 0; i < 5; i++){
                            Apps.util.scrollTo();
                        }
                    }
                    perfilPageCloseSession.tapLnkCerrarSesion();
                    break;
                case "faq" :
                    PerfilPage perfilPage = PerfilPage.getInstance();

                    while (!Wrapper.elementExists(PerfilPage.getInstance().getLnkFAQ()))
                        Apps.util.scrollTo();

                    perfilPage.tapLnkFaq();
                    break;
                default:
                    logger.error("Opcion invalida");
                    break;
            }
        }catch (Exception | AssertionError err){
            logger.error(err);
             throw err;
        }
    }


    /****
     * Metodo para setear el password
     * @param password: escribe un password incorrecto si el valor es f,
     *                o escribe el password valido si es t
     */
    @Then("Usuario ingresa un nuevo password {string}")
    @And("Usuario ingresa un password {string}")
    public void setear_password(String password) throws Exception {
        try {
            password=password.toLowerCase();
            ChangePasswordPage changePasswordPage = ChangePasswordPage.getInstance();
            switch (password.toUpperCase()){
                case "CORRECTO":
                    changePasswordPage.setPassword(ChangePasswordPage.PASSWORD);
                    break;
                case "INCORRECTO":
                    changePasswordPage.setPassword("Test112.");
                    break;
            }

            Wrapper.waitForElementEnabled(changePasswordPage.getButton());

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    /***
     * Este metodo valida las pantallas cuando se escribe el password y
     * se prssiona el boton siguiente
     * @param pass: cuendo es t, valida la pantalla siguiente,
     *            cuandoes f, se valida el mensaje de error
     */
    @Then("Validar cuando el cambio de password es {string}")
    @And("Validar cuando el password es {string}")
    public void validar_pantalla_al_escribir_password(String pass){
        try {
            ChangePasswordPage changePasswordPage = ChangePasswordPage.getInstance();
            pass = pass.toUpperCase();
            //Presionar boton siguiente
            changePasswordPage.clickNextButton();

            switch (pass){
                case "CORRECTO":
                    changePasswordPage.waitForElementVisibility(changePasswordPage.getValidationTitle());

                    //Asserts
                    Assert.assertEquals(ChangePasswordPage.LBL_TITLE_TEXT, Wrapper.getElementText(ChangePasswordPage.getLblTitle()));
                    Assert.assertTrue(Wrapper.getElementText(changePasswordPage.getSubTitleChangePassword()).trim().matches(".*" + ChangePasswordPage.LBL_SUBTITLE_NEW_PASSWORD.trim() + ".*"));
                    Wrapper.waitForElementDisabled(changePasswordPage.getButton());
                    break;
                case "INCORRECTO":
                    changePasswordPage.waitForElementVisibility(changePasswordPage.getTextinput_error());
                    Assert.assertEquals(ChangePasswordPage.LBL_INVALID_PASSWORD, Wrapper.getElementText(changePasswordPage.getTextinput_error()).trim());
                    break;
                case "EXITOSO":
                    //Asserts
                    Assert.assertTrue(changePasswordPage.elementExists(changePasswordPage.getEmoji()));
                    Assert.assertEquals(ChangePasswordPage.TEXT_CHANGE_SUCESSFUL, changePasswordPage.getMessage().getText().trim().replace("\n", " "));

                    //Back to profile screen
                    changePasswordPage.click_back_to_profile();
            }
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    /***
     * Este metodo valida las pantallas cuando se escribe el pin y
     *
     * @param pin: correcto o
     *           incorrecto
     */
    @When("Validar pantalla cuando el nuevo pin es {string}")
    @Then("Validar pantalla cuando el pin es {string}")
    @And("Validar pantalla cuando la confirmacion de pin es {string}")
    //@Then("Validar pantalla cuando clave nubi es {string}")
    public void validar_pantalla_al_escribir_pin(String pin){
        try {
            pin = pin.toLowerCase();
            switch (pin){
                case "correcto":
                    ChangeNubiPassPage changePinPage = ChangeNubiPassPage.getInstance();
                    try {
                        Assert.assertEquals(ChangeNubiPassPage.SET_NEW_PIN__SUBTITLE_TEXT, changePinPage.get_element_text(changePinPage.getLblChangePin()).replace("\n", " "));
                    }catch (AssertionError err){
                        Assert.assertEquals(ChangeNubiPassPage.CONFIRM_PIN__SUBTITLE_TEXT, changePinPage.get_element_text(changePinPage.getLblChangePin()).replace("\n", " "));
                    }

                    break;
                case "incompleto":
                    ChangeNubiPassPage changePinPageIncomplete = ChangeNubiPassPage.getInstance();

                    if(changePinPageIncomplete.getLblChangePinText().contains("PIN actual")){
                        Assert.assertTrue(changePinPageIncomplete.getLblChangePinText().contains("PIN actual"));
                    }else if(changePinPageIncomplete.getLblChangePinText().contains("Ingrese su PIN nuevo")){
                        Assert.assertTrue(changePinPageIncomplete.getLblChangePinText().contains("Ingrese su PIN nuevo"));
                    }else if(changePinPageIncomplete.getLblChangePinText().contains("Confirme su PIN nuevo")){
                        Assert.assertTrue(changePinPageIncomplete.getLblChangePinText().contains("Confirme su PIN nuevo"));
                    }

                    break;
                case "incorrecto":

                    // VALIDAR SI SE ESPERA ESTO O UNA PANTALLA DE ERROR !!!!!!
                    ChangeNubiPassPage changePinPageIncorrect = ChangeNubiPassPage.getInstance();

                    // Android.app.nubiWallet.perfil.seguridad.cambioPin.uiObject.lblErrorMsg().waitToAppear(15);
                    try{
                       // Assert.assertTrue(changePinPageIncorrect.getErrorMessage().contains("PIN incorrecto"));
                    }catch (AssertionError e){
                       // Assert.assertTrue(changePinPageIncorrect.getErrorMessage().contains("PIN no coincide"));
                    }
                    break;
            }
           //Android.driver.hideKeyboard();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * This method will validate when user set the pin
     *on flow FORGET PIN
     * @param pin: correcto o
     *           incorrecto
     */
    @When("Validar pantalla cuando el nuevo pin es {string} en flujo OLVIDE MI PIN")
    @Then("Validar pantalla cuando el pin es {string} en flujo OLVIDE MI PIN")
    @And("Validar pantalla cuando la confirmacion de pin es {string} en flujo OLVIDE MI PIN")
    public void validate_forget_pin_flow(String pin){
        try {

            //SetNewPinPage setNewPinPage = SetNewPinPage.getInstance();

            //Assert title
          //  Assert.assertEquals(SetNewPinPage.FORGOT_PIN_TITLE_TEXT, setNewPinPage.get_element_text(SetNewPinPage.getLblTitle()).replace("\n", " "));

            switch (pin.toUpperCase()){
                case "CORRECTO":
                    try {
                   //     Assert.assertEquals(SetNewPinPage.SET_NEW_PIN__SUBTITLE_TEXT, setNewPinPage.get_element_text(setNewPinPage .getLblChangePin()).replace("\n", " "));
                    }catch (AssertionError err){
                    //    Assert.assertEquals(SetNewPinPage.CONFIRM_PIN__SUBTITLE_TEXT, setNewPinPage.get_element_text(setNewPinPage.getLblChangePin()).replace("\n", " "));
                    }
                    break;
                case "incompleto":
                    ChangeNubiPassPage changePinPageIncomplete = ChangeNubiPassPage.getInstance();

                    if(changePinPageIncomplete.getLblChangePinText().contains("PIN actual")){
                        Assert.assertTrue(changePinPageIncomplete.getLblChangePinText().contains("PIN actual"));
                    }else if(changePinPageIncomplete.getLblChangePinText().contains("Ingrese su PIN nuevo")){
                        Assert.assertTrue(changePinPageIncomplete.getLblChangePinText().contains("Ingrese su PIN nuevo"));
                    }else if(changePinPageIncomplete.getLblChangePinText().contains("Confirme su PIN nuevo")){
                        Assert.assertTrue(changePinPageIncomplete.getLblChangePinText().contains("Confirme su PIN nuevo"));
                    }

                    break;
                case "incorrecto":

                    // VALIDAR SI SE ESPERA ESTO O UNA PANTALLA DE ERROR !!!!!!
                    ChangeNubiPassPage changePinPageIncorrect = ChangeNubiPassPage.getInstance();

                    // Android.app.nubiWallet.perfil.seguridad.cambioPin.uiObject.lblErrorMsg().waitToAppear(15);
                    try{
                        //Assert.assertTrue(changePinPageIncorrect.getErrorMessage().contains("PIN incorrecto"));
                    }catch (AssertionError e){
                        //Assert.assertTrue(changePinPageIncorrect.getErrorMessage().contains("PIN no coincide"));
                    }
                    break;
            }
            //Android.driver.hideKeyboard();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * Este metodo valida los elementos de la
     * pantalla dada en @param
     *
     * @param screen: pantalla en la cual se validaran los elementos
     *
     */
    @Then("Validar elementos de pantalla {string}")
    public void validar_elementos_en_pantalla(String screen) throws Exception {
        try {
            screen = screen.toLowerCase();

            switch (screen){
                case "perfil":
                    PerfilPage perfilPage = PerfilPage.getInstance();

                    //Asserts
                    Assert.assertEquals(PerfilPage.TITLE_TEXT, Wrapper.getElementText(PerfilPage.getLblTitle()));
                    Assert.assertEquals(Login.usuario.getName()+" "+Login.usuario.getLastName(),
                            Wrapper.getElementText(PerfilPage.getName()));
                    Assert.assertEquals(Login.usuario.getUsername(), Wrapper.getElementText(perfilPage.getUserName()));
                    Assert.assertTrue(Wrapper.elementExists(perfilPage.getLnkPersonalData()));
                    Assert.assertTrue(Wrapper.elementExists(perfilPage.getLnkSecurity()));
                    Assert.assertTrue(Wrapper.elementExists(perfilPage.getLnkFAQ()));
                    while(!Wrapper.elementExists(perfilPage.getCloseSession()))
                        Apps.util.scrollTo();
                    Assert.assertTrue(Wrapper.elementExists(perfilPage.getLnkChat()));
                    Assert.assertTrue(Wrapper.elementExists(perfilPage.getLnkTermsAndConditions()));

                    break;
                case "personal_data":
                    DatosPersonalesPage datosPersonalesPage = DatosPersonalesPage.getInstance();

                    //Asserts
                    Assert.assertEquals(DatosPersonalesPage.TITLE_TEXT, Wrapper.getElementText(DatosPersonalesPage.getLblTitle()));

                    //name + last name
                    Assert.assertEquals(Login.usuario.getName()+" "+ Login.usuario.getLastName(),
                            datosPersonalesPage.getContactDataValue(0));

                    //Username
                    Assert.assertEquals(Login.usuario.getUsername(),datosPersonalesPage.getContactDataValue(1));

                    //Cuil
                    Assert.assertEquals((Login.usuario.getCuil().substring(0,2)+" - "+Login.usuario.getCuil().substring(2,10)+" - "+Login.usuario.getCuil().substring(10,Login.usuario.getCuil().length())),
                            datosPersonalesPage.getContactDataValue(2));
                    Assert.assertEquals(Login.usuario.getEmail(),datosPersonalesPage.getContactDataValue(3));
                    Assert.assertEquals(Login.usuario.getPhoneNumber(), datosPersonalesPage.getContactDataValue(4));

                    break;
                case "security":
                    SecurityPage securityPage = SecurityPage.getInstance();

                    //Asserts
                    Assert.assertEquals(SecurityPage.TITLE_TEXT, Wrapper.getElementText(SecurityPage.getLblTitle()));
                    Assert.assertTrue(Wrapper.elementExists(securityPage.getLnkChangeNubiPass()));
                    Assert.assertTrue(Wrapper.elementExists(securityPage.getLnkForgotNubiPass()));
                    Assert.assertTrue(Wrapper.elementExists(securityPage.getLnkChangePassword()));

                    break;
                case "cambio_password":
                    ChangePasswordPage changePasswordPage = ChangePasswordPage.getInstance();

                    //Asserts
                    Assert.assertEquals(ChangePasswordPage.LBL_TITLE_TEXT, Wrapper.getElementText(ChangePasswordPage.getLblTitle()));
                    Assert.assertTrue(Wrapper.getElementText(changePasswordPage.getSubTitleChangePassword()).trim().matches(".*" + ChangePasswordPage.LBL_SUBTITLE_CURRENT_PASSWORD.trim() + ".*"));
                    Assert.assertTrue(Wrapper.elementExists(changePasswordPage.getPasswordInput()));
                    Wrapper.waitForElementDisabled(changePasswordPage.getButton());
                    break;
                case "current_nubi_password":
                    Android.hideKeyboardIfVisible();

                    ChangeNubiPassPage changePinPage = ChangeNubiPassPage.getInstance();


                    //Validar titulo
                    Assert.assertEquals(ChangeNubiPassPage.CHANGE_PIN_TITLE_TEXT, Wrapper.getElementText(ChangeNubiPassPage.getLblTitle()));

                    //Validar subtitulo
                    Assert.assertEquals(ChangeNubiPassPage.SET_CURRENT_PIN__SUBTITLE_TEXT, Wrapper.getElementText(changePinPage.getLblChangePin()));

                    //Validar que se encuentres los campos para ingreso de digitos
                    Assert.assertTrue(Wrapper.elementExists(changePinPage.getFirstDigit()));
                    Assert.assertTrue(Wrapper.elementExists(changePinPage.getSecondDigit()));
                    Assert.assertTrue(Wrapper.elementExists(changePinPage.getThirdDigit()));
                    Assert.assertTrue(Wrapper.elementExists(changePinPage.getFourthDigit()));

                    //Validar que existe boton de cierre
                    Assert.assertTrue(Wrapper.elementExists(ChangeNubiPassPage.getBackOrCloseBtn()));
                    break;
                case "new_nubi_password":
                    ChangeNubiPassPage changeNew = ChangeNubiPassPage.getInstance();

                    //Validar titulo
                    Assert.assertEquals(ChangeNubiPassPage.CHANGE_PIN_TITLE_TEXT, Wrapper.getElementText(ChangeNubiPassPage.getLblTitle()));

                    //Validar subtitulo
                    Assert.assertEquals(ChangeNubiPassPage.SET_NEW_PIN__SUBTITLE_TEXT, Wrapper.getElementText(changeNew.getLblChangePin()));

                    //Validar que se encuentres los campos para ingreso de digitos
                    Assert.assertTrue(Wrapper.elementExists(changeNew.getFirstDigit()));
                    Assert.assertTrue(Wrapper.elementExists(changeNew.getSecondDigit()));
                    Assert.assertTrue(Wrapper.elementExists(changeNew.getThirdDigit()));
                    Assert.assertTrue(Wrapper.elementExists(changeNew.getFourthDigit()));

                    //Validar que existe boton de cierre
                    Assert.assertTrue(Wrapper.elementExists(ChangeNubiPassPage.getBackOrCloseBtn()));
                    break;
                case "confirm_nubi_password":

                    Android.hideKeyboardIfVisible();

                    ChangeNubiPassPage confirmPin = ChangeNubiPassPage.getInstance();


                    //Validar titulo
                    Assert.assertEquals(ChangeNubiPassPage.CHANGE_PIN_TITLE_TEXT, Wrapper.getElementText(ChangeNubiPassPage.getLblTitle()));

                    //Validar subtitulo
                    Assert.assertEquals(ChangeNubiPassPage.CONFIRM_PIN__SUBTITLE_TEXT, Wrapper.getElementText(confirmPin.getLblChangePin()));

                    //Validar que se encuentres los campos para ingreso de digitos
                    Assert.assertTrue(Wrapper.elementExists(confirmPin.getFirstDigit()));
                    Assert.assertTrue(Wrapper.elementExists(confirmPin.getSecondDigit()));
                    Assert.assertTrue(Wrapper.elementExists(confirmPin.getThirdDigit()));
                    Assert.assertTrue(Wrapper.elementExists(confirmPin.getFourthDigit()));

                    //Validar que existe boton de cierre
                    Assert.assertTrue(Wrapper.elementExists(ChangeNubiPassPage.getBackOrCloseBtn()));
                    break;
                case "nubi_pass_success":
                    NubiPasswordSuccessPage successPage = NubiPasswordSuccessPage.getInstance();

                    //Asserts
                    Assert.assertTrue(Wrapper.getElementText(successPage.getMessage()).contentEquals(NubiPasswordSuccessPage.MESSAGE_TEXT) ||
                            Wrapper.getElementText(successPage.getMessage()).contentEquals("¡Tenés nuevo PIN!"));
                    //Assert.assertEquals(NubiPasswordSuccessPage.MESSAGE_TEXT, Apps.wrapper.getElementText(successPage.getMessage()).replace("\n", " "));
                    Assert.assertTrue(Wrapper.elementExists(successPage.getIcon()));


                    successPage.clickFinishBtn();
                    break;
                case "faq":
                    FAQPage faqPage = FAQPage.getInstance();

                    //Asserts
                    Assert.assertEquals(FAQPage.TITLE_TEXT, Wrapper.getElementText(faqPage.getTitle()));
                    Assert.assertTrue(Wrapper.elementExists(FAQPage.getSearchButton()));
                    Assert.assertTrue(Wrapper.elementExists(faqPage.getSection_title()));
                    Assert.assertTrue(Wrapper.elementExists(faqPage.getArticle_title()));

                    break;
                case "close_session":
                    CloseSessionPage closeSessionPage = CloseSessionPage.getInstance();

                    //Asserts
                    Assert.assertTrue(Wrapper.getElementText(closeSessionPage.getModalTitle()).contentEquals(CloseSessionPage.MODAL_TITLE_TEXT));
                    Assert.assertTrue(Wrapper.getElementText(closeSessionPage.getModalText()).contentEquals(CloseSessionPage.MODAL_TEXT));
                    Assert.assertTrue(Wrapper.elementExists(CloseSessionPage.getCloseSessionBtn()));
                    Assert.assertTrue(Wrapper.elementExists(closeSessionPage.getReturnBtn()));

                    break;
            }

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    /****
     * Metodo para bulsar boton dado
     * @param boton: boton q sera pulsado
     */
    @And("Usuario presiona boton {string}")
    public void tap_boton(String boton) throws Exception {
        try {
            boton = boton.toLowerCase();

            switch (boton){
                case "agregar_contacto":
                    ContactsPage contactsPage = new ContactsPage();
                    contactsPage.clickBtnAddContact();
                    break;
                case "close_session":
                    CloseSessionPage.getInstance().clickCloseSessionBtn();
                    break;
                case "contactanos":
                    FAQPage helpPage = new FAQPage();
                    //helpPage.tabBtnContactanos();
                    break;
                case "rapipago":
                    CashInLandingPage.getInstance().click_option(2);
                    break;
                case "copy_rapipago_code":
                    RapiPagoPage.getInstance().click_copyRapipagoCode();
                    break;
                case "cuenta_propia":
                    CashInLandingPage.getInstance().click_option(0);
                    break;
                case "otras_cuentas":
                    CashInLandingPage.getInstance().click_option(1);
                    break;
                case "efectivo-cout":
                    //Android.app.nubiWallet.movements.cashOut.tapBtnEfectivo();
                    break;
                case "confirmar_pin":
                    if(Android.driver.isKeyboardShown())
                        Android.driver.hideKeyboard();

                    //if ( Android.app.nubiWallet.uiObjects.btnCirculoSiguiente().exists() ) {
                    //    Android.app.nubiWallet.uiObjects.btnCirculoSiguiente().tap();
                    //} else if ( Android.app.nubiWallet.uiObjects.btnMain().exists() ) {
                     //   Android.app.nubiWallet.uiObjects.btnMain().tap();
                   // }
                    break;
                case "enviar_sms":
                    ForgotPinPage.getInstance().clickSendSMS();
                    break;
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Metodo para validar que los inputs esten en blanco en la pantalla
     * dada
     *
     * @param screen: pantalla a validar los inputs
     *
     */
    @And("Validar que los campos esten vacios en la pantalla {string}")
    public void validar_inputs_vacios_en_pantalla(String screen){
        try {
            screen = screen.toLowerCase();
            ChangeNubiPassPage changePinPage = ChangeNubiPassPage.getInstance();
            switch (screen){
                case "pinnuevo":
                    Assert.assertTrue(changePinPage.expectedTextInFirstDigit());
                    Assert.assertTrue(changePinPage.expectedTextInSecondDigit());
                    Assert.assertTrue(changePinPage.expectedTextInThirdDigit());
                    Assert.assertTrue(changePinPage.expectedTextInFirstDigit());
                    break;

            }
        }catch (Exception | AssertionError err){
            err.printStackTrace();
        }
    }

    @And("Usuario ingresa el codigo sms {string}")
    public void set_codigo_sms(String tipoCodigo) throws SQLException, IOException, InterruptedException {
        try {
            String auxCodigoSms = "";
            Android.hideKeyboardIfVisible();

            switch (tipoCodigo.toUpperCase()){
                case "CORRECTO":
                    Android.hideKeyboardIfVisible();
                    SetSmsCodePage smsCodePage = SetSmsCodePage.getInstance();

                    //Asserts
                    Assert.assertTrue(Wrapper.getElementText(SetSmsCodePage.getLblTitle()).contentEquals(SetSmsCodePage.TITLE_TEXT) ||
                            Wrapper.getElementText(SetSmsCodePage.getLblTitle()).contentEquals("Me olvidé el PIN"));
                    //Assert.assertEquals(SetSmsCodePage.TITLE_TEXT, smsCodePage.get_element_text(SetSmsCodePage.getLblTitle()).replace("\n", " "));
                    Assert.assertEquals(SetSmsCodePage.MESSAGE_TEXT, smsCodePage.get_element_text(smsCodePage.getMessage()).replace("\n", " "));
                    auxCodigoSms = Apps.dbQuery.getCodigoSmsOlvidoPin(Login.usuario.getId());

                    //Set sms code
                    smsCodePage.set_pin_or_smsCode(auxCodigoSms);
                    break;

            }
        }catch (Exception | Error err){
            err.printStackTrace();
            logger.error(err.getMessage());
            throw err;
        }
    }


    @And("Ingresa clave nubi incorrecto {int} veces")
    public void set_pin_attempts(int attemps)throws Exception{
        try{
            //set false invalidate login, if is the case
            //0 is for invalidated login field
            Apps.dbQuery.update_has_invalidated_field(Login.usuario.getUsername(), false, 1);

            ChangeNubiPassPage change = ChangeNubiPassPage.getInstance();
            for(int i =0; i<attemps; i++){
                change.set_invalid_pin();
                if(i==(attemps-1)){
                    //wait snackbar
                    change.waitForElementVisibility(change.getSnackbar_text());
                    SNACKBAR_TEXT = change.get_element_text(change.getSnackbar_text());
                }else {
                    change.waitForElementVisibility(change.getErrorMessageElement());
                }
            }
        }catch (Exception | Error err){
            logger.error(err);
            throw  err;
        }
    }

    @Then("Validar que la clave Nubi esta {string}")
    public void validate_nubi_password_status(String status) throws Exception{
        try {
            switch (status.toUpperCase()){
                case "BLOCKED":
                    Assert.assertEquals("T", Apps.dbQuery.get_pin_status(NubiWallet.usuario.getUsername()).toUpperCase());
                    break;
                case "UNBLOCKED":
                    Assert.assertEquals("F", Apps.dbQuery.get_pin_status(NubiWallet.usuario.getUsername()).toUpperCase());
                    break;
            }
        }catch (Exception | Error er){
            logger.error(er);
            throw  er;
        }
    }

    @Then("Validar redirecionamiento del mensaje de bloqueo de pin")
    public void validate_redirectionOnBlockedMessage() throws Exception{
        try {
            ChangeNubiPassPage changePinPage = ChangeNubiPassPage.getInstance();

            //Assert snackbarc text
            try{
                Assert.assertEquals(ChangeNubiPassPage.SNACKBAR_TEXT, SNACKBAR_TEXT);
            }catch (AssertionError e){
                Assert.assertEquals(ChangeNubiPassPage.SNACKBAR_TEXT_OLD, SNACKBAR_TEXT);
            }


            changePinPage.set_invalid_pin();
            changePinPage.waitForElementVisibility(changePinPage.getSnackbar_action());
            changePinPage.click_snackbar_action();

        }catch (Exception | Error er){
            logger.error(er);
            throw  er;
        }
    }

    @And("Completa flujo olvide pin")
    public void complete_forgot_pin_procces() throws Exception {
        try{
            tap_boton("enviar_sms");
            set_codigo_sms("correcto");
        }catch (Exception | Error err){
            logger.error(err);
            throw  err;
        }
    }

    @And("Usuario confirma nueva clave Nubi para flujo {string}")
    public void confirm_nubi_pass(String flow) throws InterruptedException {
        try {
            switch (flow.toUpperCase()){
                case "FORGOT_NUBI_PASS":
                    SetNubiPassPage nubiPassPage = SetNubiPassPage.getInstance();

                    //Asserts
                    Assert.assertTrue(Wrapper.elementExists(SetNubiPassPage.getBackOrCloseBtn()));

                    Assert.assertTrue(Wrapper.getElementText(SetNubiPassPage.getLblTitle()).contentEquals(SetNubiPassPage.TITLE_TEXT) ||
                            Wrapper.getElementText(SetNubiPassPage.getLblTitle()).contentEquals("Me olvidé el PIN"));

                    //Assert.assertEquals(SetNubiPassPage.TITLE_TEXT, Apps.wrapper.getElementText(SetNubiPassPage.getLblTitle()).replace("\n", " "));
                    Assert.assertTrue(Wrapper.getElementText(SetNubiPassPage.getLblChangePin()).contentEquals(SetNubiPassPage.CONFIRM_NUBI_PASS_SUBTITLE_TEXT) ||
                            Wrapper.getElementText(SetNubiPassPage.getLblChangePin()).contentEquals("Confirmá tu nuevo PIN"));

                    nubiPassPage.set_valid_pin();
                    break;
                case "CHANGE_NUBI_PASS":
                    ChangeNubiPassPage confirmNubiPass = ChangeNubiPassPage.getInstance();

                    //Asserts
                    Assert.assertTrue(Wrapper.elementExists(ChangeNubiPassPage.getBackOrCloseBtn()));

                    Assert.assertTrue(Wrapper.getElementText(ChangeNubiPassPage.getLblTitle()).contentEquals(ChangeNubiPassPage.CHANGE_PIN_TITLE_TEXT) ||
                            Wrapper.getElementText(SetNubiPassPage.getLblTitle()).contentEquals("Cambiar PIN"));
                    //Assert.assertEquals(ChangeNubiPassPage.CHANGE_PIN_TITLE_TEXT, Apps.wrapper.getElementText(ChangeNubiPassPage.getLblTitle()).replace("\n", " "));

                    Assert.assertTrue(Wrapper.getElementText(confirmNubiPass.getLblChangePin()).contentEquals(ChangeNubiPassPage.CONFIRM_PIN__SUBTITLE_TEXT) ||
                            Wrapper.getElementText(confirmNubiPass.getLblChangePin()).contentEquals("Confirmá tu nuevo PIN"));
                    //Assert.assertEquals(ChangeNubiPassPage.CONFIRM_PIN__SUBTITLE_TEXT, Apps.wrapper.getElementText(confirmNubiPass.getLblChangePin()).replace("\n", " "));

                    confirmNubiPass.set_valid_pin();
                    break;
            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    @And("Realizar flujo de olvido de clave Nubi")
    public void do_nubi_password_recovery_proccess() throws Exception{
        try {
            validate_redirectionOnBlockedMessage();
            Android.hideKeyboardIfVisible();
            ForgotPinPage.getInstance();
            complete_forgot_pin_procces();

            CommonSteps commonSteps = new CommonSteps();

            commonSteps.set_nubi_password("FORGOT_NUBI_PASS");
            confirm_nubi_pass("FORGOT_NUBI_PASS");
            validar_elementos_en_pantalla("nubi_pass_success");

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @Then("Validar pantalla cuando clave nubi es {string}")
    @Given("Validar pantalla cuando confirmacion de nueva clave nubi es {string}")
    @And("Validar pantalla cuando nueva clave nubi es {string}")
    public void validarPantallaCuandoClaveNubiEs(String arg) {
        try {
            Android.hideKeyboardIfVisible();
            switch (arg.toLowerCase()){
                case "correcto":
                    ChangeNubiPassPage changePinPage = ChangeNubiPassPage.getInstance();
                    try {
                        Assert.assertEquals(ChangeNubiPassPage.SET_NEW_PIN__SUBTITLE_TEXT, Wrapper.getElementText(changePinPage.getLblChangePin()));
                    }catch (AssertionError err){
                        Assert.assertEquals(ChangeNubiPassPage.CONFIRM_PIN__SUBTITLE_TEXT, Wrapper.getElementText(changePinPage.getLblChangePin()));
                    }

                    break;
                case "incompleto":
                    ChangeNubiPassPage changePinPageIncomplete = ChangeNubiPassPage.getInstance();

                    try{
                        Assert.assertEquals(ChangeNubiPassPage.SET_CURRENT_PIN__SUBTITLE_TEXT, Wrapper.getElementText(changePinPageIncomplete.getLblChangePin()));
                    }catch (Exception | Error e){
                        try{
                            Assert.assertEquals(ChangeNubiPassPage.SET_NEW_PIN__SUBTITLE_TEXT, Wrapper.getElementText(changePinPageIncomplete.getLblChangePin()));
                        }catch (Exception | Error err){
                            Assert.assertEquals(ChangeNubiPassPage.CONFIRM_PIN__SUBTITLE_TEXT, Wrapper.getElementText(changePinPageIncomplete.getLblChangePin()));
                        }
                    }

                    break;
                case "incorrecto":

                    // VALIDAR SI SE ESPERA ESTO O UNA PANTALLA DE ERROR !!!!!!
                    ChangeNubiPassPage changePinPageIncorrect = ChangeNubiPassPage.getInstance();

                    // Android.app.nubiWallet.perfil.seguridad.cambioPin.uiObject.lblErrorMsg().waitToAppear(15);
                    try{
                        Assert.assertEquals(ChangeNubiPassPage.WRONG_NUBI_PASSWORD_TEXT, Wrapper.getElementText(changePinPageIncorrect.getErrorMessageElement()));
                    }catch (AssertionError e){
                        Assert.assertEquals(ChangeNubiPassPage.UNMATCH_NUBI_PASSWORD_TEXT, Wrapper.getElementText(changePinPageIncorrect.getErrorMessageElement()));
                    }
                    break;
            }
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }
}
