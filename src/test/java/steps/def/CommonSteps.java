package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.nw.datos_adicionales.JobsPage;
import api.apps.android.nw.login.OnboardingPage;
import api.apps.android.nw.model.Account;
import api.apps.android.nw.model.PrepaidCard;
import api.apps.android.nw.model.Sube;
import api.apps.android.nw.model.User;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.common.CommonPinPage;
import api.apps.android.nw.common.PermissionsPopUp;
import api.apps.android.nw.contactos.ContactsPage;
import api.apps.android.nw.contactos.addContact.AddContactPage;
import api.apps.android.nw.contactos.detallecontactos.ContactsDetailPage;
import api.apps.android.nw.contrasenia.NewPasswordPage;
import api.apps.android.nw.contrasenia.PasswordRecoveryLandingPage;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.datos_adicionales.ConfirmDirectionPage;
import api.apps.android.nw.datos_adicionales.DirectionPage;
import api.apps.android.nw.datos_adicionales.DisambiguationPagePage;
import api.apps.android.nw.movimientos.LastMovementsListPage;
import api.apps.android.nw.login.LoginPage;
import api.apps.android.nw.menu.MenuPage;
import api.apps.android.nw.operaciones.cashin.CashInLandingPage;
import api.apps.android.nw.operaciones.cashin.metodos.RapiPagoPage;
import api.apps.android.nw.operaciones.cashin.metodos.OwnAccountPage;
import api.apps.android.nw.operaciones.cashin.metodos.OtherAccountPage;
import api.apps.android.nw.operaciones.p2p.NubiPassP2PPage;
import api.apps.android.nw.operaciones.p2p.request.RequestsLandingPage;
import api.apps.android.nw.operaciones.pagoServicios.saldoInsuficiente.InsufficientBalance;
import api.apps.android.nw.operaciones.recarga_celular.*;
import api.apps.android.nw.operaciones.pagoServicios.ServicesPaymentPage;
import api.apps.android.nw.operaciones.pagoServicios.codigoManual.ManualInputPage;
import api.apps.android.nw.operaciones.pagoServicios.confirmacionDePago.PaymentConfirmationPage;
import api.apps.android.nw.operaciones.pagoServicios.errorGenerico.GenericErrorPage;
import api.apps.android.nw.operaciones.pagoServicios.escanearFactura.BillsScanner;
import api.apps.android.nw.operaciones.pagoServicios.listadoEmpresas.CompaniesListPage;
import api.apps.android.nw.operaciones.pagoServicios.montoAbierto.OpenAmountPage;
import api.apps.android.nw.operaciones.pagoServicios.multiplesFacturas.MultipleInvoicesPage;
import api.apps.android.nw.operaciones.pagoServicios.pagoDemorado.DelayedPaymentPage;
import api.apps.android.nw.operaciones.pagoServicios.pagoExitoso.SuccessPayPage;
import api.apps.android.nw.operaciones.pagoServicios.permisosDenegados.PermissionsDeniedPage;
import api.apps.android.nw.operaciones.pagoServicios.transaccionFallida.FailedTransactionPage;
import api.apps.android.nw.perfil.PerfilPage;
import api.apps.android.nw.perfil.ayuda.FAQPage;
import api.apps.android.nw.perfil.datospersonales.DatosPersonalesPage;
import api.apps.android.nw.perfil.seguridad.SecurityPage;
import api.apps.android.nw.perfil.seguridad.cambiopassword.ChangePasswordPage;
import api.apps.android.nw.perfil.seguridad.claveNubi.cambio.ChangeNubiPassPage;
import api.apps.android.nw.perfil.cerrarsesion.CloseSessionPage;
import api.apps.android.nw.perfil.seguridad.claveNubi.olvido.ForgotPinPage;
import api.apps.android.nw.perfil.seguridad.claveNubi.olvido.SetNubiPassPage;
import api.apps.android.nw.prestamos.detallePlanPago.DownPaymentPage;
import api.apps.android.nw.prestamos.direccionEnvio.DeliveryAddressPage;
import api.apps.android.nw.prestamos.listaProductos.ProductsListPage;
import api.apps.android.nw.registro.*;
import api.apps.android.nw.splash.SplashPage;
import api.apps.android.nw.operaciones.sube.NubiPassRechargeSubePage;
import api.apps.android.nw.operaciones.sube.cargarSaldo.SelectAmountPage;
import api.apps.android.nw.operaciones.sube.confirmacionDeRecarga.CreditConfirmationPage;
import api.apps.android.nw.operaciones.sube.listaTarjetas.CardListPage;
import api.apps.android.nw.operaciones.sube.pantallaError.ScreenErrorPage;
import api.apps.android.nw.operaciones.sube.primerUso.LoadSubePage;
import api.apps.android.nw.operaciones.sube.recargaExitosa.SuccessSubeRechargePage;
import api.apps.android.nw.operaciones.sube.registrarSube.SubeRegisterPage;
import api.apps.android.nw.tarjetaprepaga.fisica.CreateExtractionPinPage;
import api.apps.android.nw.tarjetaprepaga.fisica.LandingPhysicalPage;
import api.apps.android.nw.tarjetaprepaga.fisica.TravelNotifyPage;
import api.apps.android.nw.tarjetaprepaga.fisica.denunciar_tarjeta.ConfirmErrorPage;
import api.apps.android.nw.tarjetaprepaga.fisica.denunciar_tarjeta.ReportCardPage;
import api.apps.android.nw.tarjetaprepaga.landing.NoPrepaidCardPage;
import api.apps.android.nw.tarjetaprepaga.virtual.VirtualPage;
import com.aventstack.extentreports.model.BasicMongoReportElement;
import core.UserGenerator;
import core.Util;
import core.commons.DBQuery;
import api.apps.android.Wrapper;
import core.commons.apicall.Auth_API;
import core.commons.apicall.Contacts_API;
import core.commons.apicall.DNI_Validation_API;
import core.commons.apicall.dtos.auth.AuthToken;
import core.commons.apicall.dtos.auth.ChangePin;
import core.commons.apicall.dtos.dniValidation.DNIUserStatus;
import core.commons.apicall.dtos.registration.CompleteRegistration;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.NetworkSpeed;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import rp.com.google.common.collect.ImmutableMap;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;


public class CommonSteps {

    private static Util util = Apps.util;
    private static NubiWallet nubiWallet = Android.nubiWallet;
    private static DBQuery dbQuery = Apps.dbQuery;
    private static int
            CONTACTS_PERMISSION = 0,
            CAMERA_PERMISSION = 0;

    private static final Logger logger = LogManager.getLogger();


    @Then("Validate user is on screen {string}")
    @Given("Usuario esta en pantalla {string}")
    @And("Validate user is still on screen {string}")
    public void usuario_esta_en_pantalla(String screen) throws Exception {
        try {

            Android.hideKeyboardIfVisible();

            switch (screen.toLowerCase()){
                case "splash":
                        SplashPage.getInstance();
                        break;
                case "login":
                        LoginPage.getInstance();
                       break;
                case "dashboard":
                    DashboardPage.getInstance();

                    //Set balance
                    CommonStepsMovements cMovements = new CommonStepsMovements();
                    cMovements.setBalance();
                    break;
                case "email":
                    EmailPage.getInstance();
                    break;
                case "personal_information":
                      PersonalDataPage.getInstance();
                    break;
                case "user_information":
                        UserCreationPage.getInstance();
                    break;
                case "cuil":
                      CuilPage.getInstance();
                    break;
                case "reg_sms_code":
                    SmsCodePage.getInstance();
                    break;
                case "reg_phone_number":
                   PhonePage.getInstance();
                    break;
                case "reg_nubi_pass":
                      PinCreationPage.getInstance();
                    break;
                case "tyc":
                    TermsConditionsPage.getInstance();
                    break;
                case "con_contactos_y_permiso":
                   ContactsPage contactsPage = new ContactsPage();
                   contactsPage.waitForElementVisibility(contactsPage.getContactTitle());
                   Assert.assertEquals(ContactsPage.CONTACTS_PAGE_TITLE_TEXT, contactsPage.getContactTitle().getText());
                    break;
                case "contactos":
                   ContactsPage.getInstance();
                    break;
                case "contactos_desde_dashboard":
                    DashboardPage dashboardPageFromContacts = DashboardPage.getInstance();
                    //dashboardPageFromContacts.freqContactsWaitToDissapear();
                    CommonSteps commonSteps = new CommonSteps();
                    Thread.sleep(2);
                    commonSteps.press_recent_app_button();
                    ContactsPage.getInstance();
                    break;
                case "personal_data":
                    DatosPersonalesPage.getInstance();
                    break;
                case "detalle_contacto":
                    ContactsDetailPage.getInstance();
                    break;
                case "agregar_contacto":
                    AddContactPage.getInstance();
                    break;
                case "profile":
                    PerfilPage.getInstance();
                    Assert.assertTrue(Wrapper.getElementText(PerfilPage.getLblTitle()).contentEquals(PerfilPage.TITLE_TEXT));
                    break;
                case "security":
                    SecurityPage.getInstance();
                    break;
                case "change_password":
                    ChangePasswordPage.getInstance();
                    break;
                case "current_nubi_password":
                    ChangeNubiPassPage.getInstance();
                    break;
                case "new_nubi_password":
                    ChangeNubiPassPage.getInstance();
                    Assert.assertEquals(ChangeNubiPassPage.SET_NEW_PIN__SUBTITLE_TEXT, Wrapper.getElementText(ChangeNubiPassPage.getInstance().getLblChangePin()));
                    break;
                case "confirm_nubi_password":
                    ChangeNubiPassPage.getInstance();
                    Assert.assertEquals(ChangeNubiPassPage.CONFIRM_PIN__SUBTITLE_TEXT, Wrapper.getElementText(ChangeNubiPassPage.getInstance().getLblChangePin()));
                    break;
                case "faq":
                    FAQPage.getInstance();
                    break;
                case "close_session":
                    CloseSessionPage.getInstance();
                    break;
                case "sube_landing_page":
                case "cargar_sube":
                    Login.usuario.refreshSubeList();

                    List<Sube> subeList = Login.usuario.getSubeList();

                    if(subeList.isEmpty()) {
                        LoadSubePage.getInstance();
                    }else {

                    }
                    break;
                case "list_sube_card":
                    CardListPage cardListPage = CardListPage.getInstance();
                    Assert.assertTrue(cardListPage.getCardItems().size()>0);
                    break;
                case "registro_sube":
                case "registro_alias_sube":
                    SubeRegisterPage.getInstance();
                    break;
                case "recarga_de_saldo":
                    SelectAmountPage.getInstance();
                    break;
                case "revisar_datos_de_recarga":
                    CreditConfirmationPage.getInstance();
                    break;
                case "recarga_exitosa_SUBE":
                    SuccessSubeRechargePage.getInstance();
                    break;
                case "error_sube":
                    ScreenErrorPage.getInstance().waitBackHomeButton();
                    break;
                case "ultimos_movimientos":
                    LastMovementsListPage.getInstance();
                    break;
                case "sube_nubi_password":
                    NubiPassRechargeSubePage.getInstance();
                    break;
                case "cargar_saldo":
                    //nubiWallet.cashInOut.cargarSaldo.waitToLoadScreen();
                    break;
                case "extraer_saldo":
                    //nubiWallet.cashInOut.extraerSaldo.waitToLoadScreen();
                    break;
                case "pc_physical":
                case "pc_virtual":
                case "pc_landing":
                case "pc_nocard":
                    //Refresh prepaid card list
                    Login.usuario.refreshPrepaidCardList();

                    List<PrepaidCard> list = Login.usuario.getPrepaidCardList();

                    if(list.isEmpty()){
                        NoPrepaidCardPage.getInstance();
                        Wrapper.waitForElementEnabled(NoPrepaidCardPage.getInstance().getButton());
                    }else{
                        for(PrepaidCard pc : list){
                            if(pc.getKind().contentEquals("PHYSICAL")) {
                                LandingPhysicalPage.getInstance();
                                break;
                            }else {
                                VirtualPage.getInstance();
                                break;
                            }
                        }
                    }
                   /* nubiWallet.uiObjects.lblTitulo().waitToAppear(10);
                    Assert.assertTrue(nubiWallet.uiObjects.lblTitulo().getText().contains(TextList.TEXT_TITULO_TARJETA_PREPAGA.getText()));
                    int i =0;

                    *//**
                     * VALIDAR EN ESTE PUNTO QUE TIPO DE TARJETA TIENE EL USUARIO
                     * Logn.usuario.getACcounts().get(0).getPrepaidCard().getStatus() -> REQUESTED,ACTIVATED,CANECLLED
                     * Y ESE VALOR IRIA PARA auxTP
                     *//*

                    boolean auxTP = false;
                    do{
                        if(nubiWallet.tarjetaPrepagaLanding.tarjetaPrepagaLandingUiObjects.btnQuieroTarjeta().exists()) {
                            auxTP = true;
                            break;
                        }

//                        if(nubiWallet.tarjetaPrepaga.tarjetaPrepagaUiObjects.swhCongelarTarjeta().exists()) {
//                            auxTP = true;
//                            break;
//                        }
                        i++;
                    }while(!auxTP && i<4);*/

                    break;
                case "validar_identidad_cvu":
                    ///nubiWallet.movements.genCvu.waitScreenValidarIdentidadCvu();
                    break;
                case "generar_cvu":
                   // nubiWallet.movements.genCvu.waitScreenGenerarCvu();
                    break;
                case "cvu_generado":
                   // nubiWallet.movements.genCvu.waitScreenCvuGenerado();
                    break;
                case "request_page":
                    RequestsLandingPage requestsLandingPage = RequestsLandingPage.getInstance();
                    Assert.assertTrue(Wrapper.elementIsChecked(requestsLandingPage.getLeftOption()));
                    break;
                case "envio_exitoso":
                   // nubiWallet.movements.uiObject.finishButton().waitToAppear(20);
                    break;
                case "cash_in_landing":
                case "cash_in":
                    CashInLandingPage.getInstance();
                    break;
                case "rapipago":
                    RapiPagoPage.getInstance();
                    break;
                case "cuenta_propia":
                    OwnAccountPage.getInstance();
                    break;
                case "otras_cuentas":
                    OtherAccountPage.getInstance();
                    break;
                case "cash_out":
                    //nubiWallet.movements.cashOut.waitScreen();
                    break;
                case "efectivo-cout":
                    //nubiWallet.movements.cashOut.efectivo.waitScreen();
                    break;
                case "pago_de_servicios":
                    ServicesPaymentPage.getInstance();
                    break;
                case "escanear_codigo_de_barras":
                    BillsScanner.getInstance();
                    break;
                case "empresas_habilitadas":
                    CompaniesListPage.getInstance();
                    break;
                case "ingresar_codigo_manual":
                    ManualInputPage.getInstance();
                    break;
                case "confirmacion_pago_servicio":
                    PaymentConfirmationPage.getInstance();
                    break;
                case "factura_monto_abierto":
                    OpenAmountPage.getInstance();
                    break;
                case "multifacturas":
                    MultipleInvoicesPage.getInstance();
                    break;
                case "ps_error_generico":
                    GenericErrorPage.getInstance();
                    break;
                case "select_compania_recarga_celular":
                    SelectCompanyPage.getInstance();
                    Assert.assertTrue(SelectCompanyPage.getInstance().getCompanyImage().size()>0);
                    break;
                case "rc_ingresar_numero":
                    //nubiWallet.movements.recargaCelular.waitScreenIngresoTelefono();
                    break;
                case "rc_ingresar_monto":
                   AmountToRechargePage amountpage = AmountToRechargePage.getInstance();
                   Assert.assertEquals(AmountToRechargePage.SUBTITLE_TEXT, Wrapper.getElementText(amountpage.getSubtitleRechargeMobile()));
                    break;
                case "rc_nubi_password":
                    NubiPassRechargeMobilePage.getInstance();
                    break;
                case "permisos_camara_denegados":
                    PermissionsDeniedPage.getInstance();
                    break;
                case "rc_saldo_insuficiente":
                    InsufficientBalance.getInstance();
                   // nubiWallet.movements.recargaCelular.waitScreenSaldoInsuficiente();
                    break;
                case "rc_confirmacion":
                    //nubiWallet.movements.recargaCelular.waitScreenConfirmacion();
                    break;
                case "pago_exitoso_servicio":
                    SuccessPayPage.getInstance();
                    break;
                case "ps_transaccion_fallida":
                    FailedTransactionPage.getInstance();
                    break;
                case "solicitudes":
                    //nubiWallet.solicitudes.waitToLoadScreen();
                    break;
                case "extraction_pin_confirmation":
                case "extraction_pin_creation":
                    CreateExtractionPinPage extractionPinPage = CreateExtractionPinPage.getInstance();

                    //Assert
                    Assert.assertEquals(CreateExtractionPinPage.TITLE_TEXT, extractionPinPage.get_element_text(CreateExtractionPinPage.getLblTitle()).replace("\n", " "));
                    try{
                        Assert.assertEquals(CreateExtractionPinPage.SUBTITLE_CREATION_TEXT, extractionPinPage.get_element_text(extractionPinPage.getEnterNewPinLabel()).replace("\n", " "));
                    }catch (AssertionError err){
                        Assert.assertEquals(CreateExtractionPinPage.SUBTITLE_CONFIRMATION_TEXT, extractionPinPage.get_element_text(extractionPinPage.getEnterNewPinLabel()).replace("\n", " "));
                    }

                    break;
                case "extraction_pin_change":
                    CreateExtractionPinPage extraction  = CreateExtractionPinPage.getInstance();

                    //Assert
                    Assert.assertEquals(CreateExtractionPinPage.TITLE_TEXT, extraction.get_element_text(CreateExtractionPinPage.getLblTitle()).replace("\n", " "));
                    try{
                        Assert.assertEquals(CreateExtractionPinPage.SUBTITLE_CHANGE_TEXT, extraction.get_element_text(extraction.getEnterNewPinLabel()).replace("\n", " "));
                    }catch (AssertionError err){
                        Assert.assertEquals(CreateExtractionPinPage.SUBTITLE_CONFIRMATION_TEXT, extraction.get_element_text(extraction.getEnterNewPinLabel()).replace("\n", " "));
                    }
                case "aviso_viaje":
                    TravelNotifyPage.getInstance();
                    break;
                case "cambiopass_landing":
                    PasswordRecoveryLandingPage.getInstance();
                    break;
                case "demora_de_pago_servicio":
                    DelayedPaymentPage.getInstance();
                    break;
                case "forgot_nubipass":
                    ForgotPinPage.getInstance();
                    break;
                case "lista_de_articulos":
                    ProductsListPage.getInstance();
                    break;
                case "datos_del_prestamo":
                    DownPaymentPage.getInstance();
                    break;
                case "direccion_de_envio":
                    DeliveryAddressPage.getInstance();
                    break;
                case "report_card":
                    ReportCardPage.getInstance();
                    break;
                case "agregar_direccion":
                    //TODO hay que refactorizar la page DirectionPage para que tenga el getInstance()
                    DirectionPage direction = new DirectionPage();
                    Assert.assertTrue(direction.elementExists(direction.getStreetNameInput()));
                    break;
                case "confirmacion_de_pago":
                    //TODO falta crear la page de confirmacion de pago
                    break;
                case "confirmar_direccion":
                    //TODO hay que refactorizar la page ConfirmDirectionPage para que tenga el getInstance()
                    ConfirmDirectionPage confirmDirectionPage = new ConfirmDirectionPage();
                    Assert.assertTrue(confirmDirectionPage.elementExists(confirmDirectionPage.getConfirmButton()));
                    break;
                case "jobs_page":
                    JobsPage.getInstance();
                    break;
                case "onboarding":
                    OnboardingPage.getInstance();

                    break;
                default:
                    break;
            }
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    /***********
     *      VALIDA EL ESTADO DEL BOTON SIGUIENTE EN LA PANTALLA RESPECTIVA
     * @param screen
     * @param status
     */
    @Then("Boton siguiente en pantalla {string} esta {string}")
    public void validar_status_btn_siguiente(String screen, String status) throws Exception{
        try{

            Android.hideKeyboardIfVisible();
            status = status.toUpperCase();

            boolean aux;

            switch (screen.toUpperCase()){
                case "EMAIL":
                    EmailPage emailPage = EmailPage.getInstance();

                    Assert.assertTrue(Wrapper.elementExists(emailPage.getNextButton()));

                    if (status.contentEquals("HABILITADO")) {
                        Wrapper.waitForElementEnabled(emailPage.getNextButton());
                    } else {
                        if(Wrapper.elementIsEnabled(emailPage.getNextButton()))
                            Wrapper.waitForElementDisabled(emailPage.getNextButton());
                    }

                    break;
                case "agregar_contacto":
                    AddContactPage addContactPage = new AddContactPage();
                    if(status.contentEquals("habilitado"))
                        Assert.assertTrue(addContactPage.nextButtonIsEnabled());
                    else
                        Assert.assertFalse(addContactPage.nextButtonIsEnabled());
                    break;
                case "rc_ingresar_monto":
                    AmountToRechargePage amount = AmountToRechargePage.getInstance();

                    if(status.contentEquals("habilitado")) {
                        Wrapper.waitForElementEnabled(amount.getNextButton());
                    }else {
                        if(Wrapper.elementIsEnabled(amount.getNextButton()))
                            Wrapper.waitForElementDisabled(amount.getNextButton());
                    }
                    break;
                case "RC_INGRESAR_NUMERO":
                    PhoneForChargePage phoneForChargePage = PhoneForChargePage.getInstance();

                    if(status.contentEquals("habilitado")) {
                        Wrapper.waitForElementEnabled(phoneForChargePage.getNextButton());
                    }else {
                        if(Wrapper.elementIsEnabled(phoneForChargePage.getNextButton()))
                            Wrapper.waitForElementDisabled(phoneForChargePage.getNextButton());
                    }
                    break;
            }
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    /**
     * VALIDAR SI EL MENSAJE DE ERROR EN INPUTS SE ENCUENTRA PRESENTE EN LA PANTALLA RESPECTIVA
     *
     * @param visible
     * @param screen
     */
    @Then("Mensaje de error en input de pantalla {string} se encuentra {string}")
    public void validar_mensaje_error_on_inputs(String screen, String visible) throws InterruptedException, Exception {
        try {
            visible = visible.toLowerCase();
            screen = screen.toLowerCase();

            switch (screen.toUpperCase()){
                case "EMAIL":
                    EmailPage emailPage = EmailPage.getInstance();

                    if(visible.contentEquals("true") || visible.contentEquals("t")) {
                       Wrapper.waitForElementVisibility((emailPage.getTextinput_error()));
                       Assert.assertEquals(EmailPage.LBL_TEXT_INPUT_ERROR, Wrapper.getElementText(emailPage.getTextinput_error()));
                    }else{
                        if(Wrapper.elementExists(emailPage.getTextinput_error()))
                            Wrapper.waitForElementNotVisible((emailPage.getTextinput_error()));
                    }

                    break;
                case "PERSONAL_INFORMATION":

                    PersonalDataPage personalDataPage = PersonalDataPage.getInstance();

                    if(visible.contains("true") || visible.contentEquals("t")){
                        personalDataPage.waitForElementVisibility(personalDataPage.getTextinput_error());

                        //Assert text
                        try{
                            Assert.assertTrue(personalDataPage.getTextinput_error().getText().replace("\n", " ").contains(
                                    PersonalDataPage.LBL_INPUT_ERROR_TEXT
                            ));
                        }catch (AssertionError e){
                            Assert.assertTrue(personalDataPage.getTextinput_error().getText().replace("\n", " ").contains(
                                    PersonalDataPage.LBL_INPUT_ERROR_LENGHT_TEXT
                           ));
                        }


                    }else{
                        if(Wrapper.elementExists(personalDataPage.getTextinput_error()))
                            Wrapper.waitForElementNotVisible(personalDataPage.getTextinput_error());
                    }
                    break;
                case "USER_INFORMATION":

                    UserCreationPage userCreationPage = UserCreationPage.getInstance();

                    switch (visible.toUpperCase()){
                        case "USER_LENGTH":
                            Assert.assertTrue(userCreationPage.validate_text_input_error_message(1));
                            Assert.assertEquals(UserCreationPage.INPUT_ERROR_TEXT_LENGHT_USER, userCreationPage.getTextinput_error().getText().trim());
                            break;
                        case "USER_SPECIAL_CHAR":
                        case "USER_SPACE":
                            Assert.assertTrue(userCreationPage.validate_text_input_error_message(1));
                            Assert.assertEquals(UserCreationPage.INPUT_ERROR_USER_SPACE_SCHAR, userCreationPage.getTextinput_error().getText().trim());
                            break;
                        case "PASSWORD_SPACE":
                            Assert.assertTrue(userCreationPage.validate_text_input_error_message(1));
                            Assert.assertEquals(UserCreationPage.INPUT_ERROR_PASSWORD_SPACE, userCreationPage.getTextinput_error().getText().trim());
                            break;
                        case "REGISTERED":
                            Assert.assertTrue(userCreationPage.validate_text_input_error_message(1));
                            Assert.assertEquals(UserCreationPage.INPUT_ERROR_USER_REGISTERED, userCreationPage.getTextinput_error().getText().trim());
                            break;
                        default:
                            Assert.assertFalse(userCreationPage.validate_text_input_error_message(0));
                            break;
                    }

                    break;
                case "REG_PHONE_NUMBER":

                    PhonePage phonePage = PhonePage.getInstance();

                    if(visible.contains("true") || visible.contentEquals("t")){
                        Wrapper.waitForElementVisibility(phonePage.getErrorMessage());

                        //Assert
                        Assert.assertEquals(PhonePage.ERROR_MESSAGE_TEXT, Wrapper.getElementText(phonePage.getErrorMessage()));
                       //Assert.assertTrue(phonePage.validate_text_input_error_message(1));
                      // Assert.assertEquals(PhonePage.ERROR_MESSAGE_TEXT, phonePage.get_element_text(phonePage.getErrorMessage()).replace("\n", " "));
                    }else{
                        if(Wrapper.elementExists(phonePage.getErrorMessage()))
                            Wrapper.waitForElementNotVisible(phonePage.getErrorMessage());
                    }

                    break;
                case "login":
                    LoginPage loginPage = LoginPage.getInstance();

                    if(visible.contains("true") || visible.contentEquals("t")){
                        Wrapper.waitForElementVisibility(loginPage.getTextinput_error());

                        //Assert
                        Assert.assertEquals(LoginPage.WRONG_DATA_TEXT, Wrapper.getElementText(loginPage.getTextinput_error()));

                    }else{
                        if(Wrapper.elementExists(loginPage.getTextinput_error()))
                            Wrapper.waitForElementNotVisible(loginPage.getTextinput_error());
                    }
                    break;
            }
        }catch (Exception | AssertionError e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *      VALIDAR SI EL MENSAJE DE ERROR  SE ENCUENTRA PRESENTE EN LA PANTALLA RESPECTIVA
     *
     * @param visible
     * @param screen
     */
    @Then("Mensaje de error en pantalla {string} se encuentra {string}")
    public void validar_mensaje_error(String screen, String visible) throws Exception{
        try {
            visible = visible.toLowerCase();
            screen = screen.toLowerCase();

            switch (screen){
                case "email":
                   /* if(visible.contentEquals("true") || visible.contentEquals("t")) {
                        nubiWallet.registro.email.uiObject.lblInputError().waitToAppear(15);
                        Assert.assertTrue(nubiWallet.registro.email.uiObject.lblInputError().exists());
                        Assert.assertTrue(nubiWallet.registro.email.uiObject.lblInputError().getText().contains(
                                TextList.TEXT_NOT_VALID_EMAIL.getText()
                        ));
                    }else{
                        Assert.assertFalse(nubiWallet.registro.email.uiObject.lblInputError().exists());
                    }*/
                    break;
                case "informacionpersonal":
                   /* if(visible.contains("true") || visible.contentEquals("t")){

                    }else{
                        Assert.assertFalse(nubiWallet.registro.informacionPersonal.uiObject.lblInputError().exists());
                    }*/
                    break;
                case "informacionusuario":


                case "reg_sms_code":
                    SmsCodePage smsCodePage = SmsCodePage.getInstance();

                    if(visible.contains("true") || visible.contentEquals("t")){
                        Wrapper.waitForElementVisibility(smsCodePage.getErrorMessage());
                        Assert.assertEquals(SmsCodePage.TEXT_ERROR_MESSAGE, Wrapper.getElementText(smsCodePage.getErrorMessage()).trim());
                    }else{
                        if(Wrapper.elementExists(smsCodePage.getErrorMessage()))
                            Wrapper.waitForElementNotVisible(smsCodePage.getErrorMessage());
                    }
                    break;
            }
        }catch (Exception | AssertionError e){
            e.printStackTrace();
            throw e;
        }
    }

    /****
     * CLICK BOTON SIGUIENTE EN PANTALLA RESPECTIVA
     * @param screen: pantalla en la que se hara click al boton siguiente
     */
    @When("Click siguiente en pantalla {string}")
    public void click_on_next_button(String screen) throws Exception {
        try {
            screen = screen.toLowerCase();

            switch (screen){
                case "email":
                    EmailPage.getInstance().click_on_next_button();
                    break;
                case "agregar_contacto":
                    AddContactPage addContactPage = new AddContactPage();
                    addContactPage.clickNextButton();
                    break;
                case "enviar_o_solicitar":
                    if(Android.driver.isKeyboardShown())
                        Android.driver.hideKeyboard();
                    //nubiWallet.movements.uiObject.btnContinuar().waitToAppear(15);
                   // nubiWallet.movements.uiObject.btnContinuar().tap();
                    break;
                case "detalle_de_operacion":
                    //nubiWallet.movements.uiObject.nextButton().waitToAppear(15);
                    //nubiWallet.movements.uiObject.nextButton().tap();
                    break;
                case "set_phone_to_recharge" :
                    Android.hideKeyboardIfVisible();
                    PhoneForChargePage.getInstance().click_on_next_button();
                    break;
                case "rc_ingresar_monto" :
                    AmountToRechargePage.getInstance().click_on_next_button();
                    break;
                default:
                    break;
            }

        }catch (Exception | AssertionError e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Metodo que valida el status del boton siguiente circulo
     * @param pantalla
     * @param status
     */

    @Then("Boton circulo siguiente en pantalla {string} esta {string}")
    public void validate_status_circle_button(String pantalla, String status) throws Exception{
        try {

            if(status.toLowerCase().contains("t")){
                switch (pantalla.toLowerCase()){
                    case "personal_information":
                        Wrapper.waitForElementEnabled(PersonalDataPage.getInstance().getButton());
                        break;
                    case "user_information":
                        Wrapper.waitForElementEnabled(UserCreationPage.getInstance().getButton());
                        break;
                    case "reg_phone_number":
                        Wrapper.waitForElementEnabled(PhonePage.getInstance().getButton());
                        break;
                    case "registropin":
                        PinCreationPage.getInstance();
                        break;
                    case "cuil":
                        Wrapper.waitForElementEnabled(CuilPage.getInstance().getButton());
                        break;
                    case "cambiopass":
                        PasswordRecoveryLandingPage.getInstance();
                        break;
                }

            }else if(status.toLowerCase().contains("f")){
                switch (pantalla.toLowerCase()){
                    case "cambio_password":
                        break;
                    case "personal_information":
                        Wrapper.waitForElementDisabled(PersonalDataPage.getInstance().getButton());
                        break;
                    case "user_information":
                        Wrapper.waitForElementDisabled(UserCreationPage.getInstance().getButton());
                        break;
                    case "reg_phone_number":
                        Wrapper.waitForElementDisabled(PhonePage.getInstance().getButton());
                        break;
                    case "registropin":

                            //pinCreationPage = PinCreationPage.getInstance();

                        break;
                }
            }

        }   catch (Exception | Error e){
                throw e;
            }
    }

    /****
     * CLICK BOTON CIRCULO SIGUIENTE EN PANTALLA RESPECTIVA
     * @param screen: pantalla en la que se hara click al boton siguiente
     */
    @When("Click boton circulo siguiente en pantalla {string}")
    public void click_on_next_circle_button(String screen) throws Exception{
        try {
            screen = screen.toLowerCase();

            switch (screen){
                case "email":
                    break;
                case "personal_information":
                    PersonalDataPage.getInstance().click_on_next_button();
                    break;
                case "cuil":
                    CuilPage.getInstance().click_on_next_button();
                    break;
                case "cuil_existente":
                    CuilPage cuilPage = CuilPage.getInstance();
                    RGSRegistration registration = new RGSRegistration();
                    if(!cuilPage.get_element_text(cuilPage.getTxtPrefixDni()).contentEquals(RGSRegistration.CUIL.substring(0,2))) {
                        registration.cambiar_prefijo(RGSRegistration.CUIL.substring(0, 2));
                        cuilPage.waitForElementVisibility(cuilPage.getButton());
                    }
                    cuilPage.click_on_next_button();
                    break;
                case "user_information":
                      UserCreationPage.getInstance().click_on_next_button();
                    break;
                case "reg_phone_number":
                       PhonePage.getInstance().click_on_next_button();
                    break;
                case "registropin":
                    //PinCreationPage.getInstance().cl
                    break;
                case "cambiopass":
                    Android.hideKeyboardIfVisible();
                    NewPasswordPage.getInstance().click_next_button();
                    break;
                default:
                    break;
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @When("Pulsar back en {string} en pantalla {string}")
    public void tap_button_back_on_screen(String modo, String screen) throws Exception {
        try {
            Android.hideKeyboardIfVisible();

            //Valido cual boton se debe pulsar
            if(modo.toLowerCase().equals("dispositivo")){
                Thread.sleep(600);
                switch (screen.toLowerCase()) {
                    case "REGISTRATION_EMAIL":
                    case "email":
                        EmailPage.getInstance();
                        break;
                    case "confirmaremail":
                            ConfirmEmailPage.getInstance();
                        break;
                    case "registro_sube":
                        SubeRegisterPage.getInstance();
                        break;
                    case "cargar_sube":
                    case "registro_alias_sube":
                        LoadSubePage.getInstance();
                        break;
                    case "revisar_datos_de_recarga":
                        CreditConfirmationPage.getInstance();
                        break;
                    case "cuil":
                            CuilPage.getInstance();
                        break;
                    case "user_information":
                        UserCreationPage.getInstance();
                        break;
                    case "reg_phone_number":
                           PhonePage.getInstance();
                        break;
                    case "reg_sms_code":
                            SmsCodePage.getInstance();
                        break;
                    case "pin":
                        PinCreationPage.getInstance();
                        break;
                    case "tyc":
                       TermsConditionsPage.getInstance();
                        break;
                    case "contactos":
                        ContactsPage contactsPage = new ContactsPage();
                        break;
                    case "agregar_contacto":
                        AddContactPage addContactPage = new AddContactPage();
                        break;
                    case "profile":
                        PerfilPage.getInstance();
                        break;
                    case "personal_data":
                        DatosPersonalesPage.getInstance();
                        break;
                    case "security":
                        SecurityPage.getInstance();
                        break;
                    case "change_password":
                        ChangePasswordPage.getInstance();
                        break;
                    case "change_nubipass":
                    case "new_nubi_password":
                        ChangeNubiPassPage.getInstance();
                    case "wallet":
                        //Assert.assertTrue(nubiWallet.movements.uiObject.btnAtras().exists());
                        break;
                    case "pago_de_servicios":
                        ServicesPaymentPage.getInstance();
                        break;
                    case "escanear_codigo_de_barras":
                        BillsScanner.getInstance();
                        break;
                    case "empresas_habilitadas":
                        CompaniesListPage.getInstance();
                        break;
                    case "ingresar_codigo_manual":
                        if(Android.driver.isKeyboardShown())
                            Android.driver.hideKeyboard();;
                        ManualInputPage.getInstance();
                        break;
                    case "confirmacion_pago_servicio":
                        PaymentConfirmationPage.getInstance();
                        break;
                    case "factura_monto_abierto":
                        OpenAmountPage.getInstance();
                        break;
                    case "multifacturas":
                        MultipleInvoicesPage.getInstance();
                        break;
                    case "select_compania_recarga_celular":
                        SelectCompanyPage.getInstance();
                        break;
                    case "rc_recharge_success":
                        SuccessfulRechargePage.getInstance();
                        break;
                    case "rc_ingresar_monto":
                        AmountToRechargePage.getInstance();
                        break;
                    case "rc_ingresar_numero":
                        PhoneForChargePage.getInstance();
                        break;
                    case "permisos_camara_denegados":
                        PermissionsDeniedPage.getInstance();
                        break;
                    case "rc_saldo_insuficiente":
                        //nubiWallet.movements.recargaCelular.waitScreenSaldoInsuficiente();
                        break;
                    case "rc_confirmacion":
                        ConfirmationPage.getInstance();
                        break;
                    case "rc_exitosa":
                       // nubiWallet.movements.recargaCelular.waitScreenRecargaExitosa();
                        break;
                    case "ps_transaccion_fallida":
                        FailedTransactionPage.getInstance();
                        break;
                    case "ps_pago_exitoso":
                        SuccessPayPage.getInstance();
                        break;
                    case "aviso_viaje":
                        TravelNotifyPage.getInstance();
                        break;
                    case "lista_de_articulos":
                        ProductsListPage.getInstance();
                        break;
                    case "report_card":
                        ReportCardPage.getInstance();
                        break;
                    case "error_confirm_report_card":
                        ConfirmErrorPage.getInstance();
                        break;
                    case "datos_del_prestamo":
                        DownPaymentPage.getInstance();
                        break;
                    case "direccion_de_envio":
                        DeliveryAddressPage.getInstance();
                        break;
                    case "agregar_direccion":
                        //TODO: getInstance()
                        DirectionPage directionPage = new DirectionPage();
                        Assert.assertTrue(directionPage.elementExists(directionPage.getStreetNameInput()));
                        break;
                    case "confirmar_direccion":
                        ConfirmDirectionPage confirmDirectionPage = new ConfirmDirectionPage();
                        Assert.assertTrue(confirmDirectionPage.elementExists(confirmDirectionPage.getConfirmButton()));
                        break;
                }

               Android.hideKeyboardIfVisible();
                //Presionar el boton atras en dispositivo
                press_back_button();

            }
            else{
                switch (screen.toLowerCase()){
                    case "registration_email":
                    case "email":
                        EmailPage.getInstance().click_close_back_button();
                        break;
                    case "confirmaremail":
                        ConfirmEmailPage.getInstance().click_close_back_button();
                        break;
                    case "cuil":
                        CuilPage.getInstance().click_close_back_button();
                        break;
                    case "user_information":
                        UserCreationPage.getInstance().click_close_back_button();
                        break;
                    case "reg_phone_number":
                        PhonePage.getInstance().click_close_back_button();
                        break;
                    case "reg_sms_code":
                        SmsCodePage.getInstance().click_close_back_button();
                        break;
                    case "pin":
                        PinCreationPage.getInstance().click_close_back_button();
                        break;
                    case "tyc":
                        TermsConditionsPage.getInstance().click_close_back_button();
                        break;
                    case "contactosagenda":
                    case "contactos":
                        ContactsPage contactsPage = new ContactsPage();
                        Assert.assertTrue(contactsPage.btnBackExists());
                        contactsPage.click_close_back_button();
                        break;
                    case "wallet":
                    case "transferdetail":
                        //Assert.assertTrue(nubiWallet.movements.uiObject.btnAtras().exists());
                       // nubiWallet.movements.tapBackButton();
                        break;
                    case "personal_data":
                    case "informacionpersonal":
                        DatosPersonalesPage.getInstance().click_close_back_button();
                        break;
                    case "change_password":
                        ChangePasswordPage.getInstance().click_close_back_button();
                        break;
                    case "security":
                        SecurityPage.getInstance().click_close_back_button();
                        break;
                    case "change_nubipass":
                        ChangeNubiPassPage.getInstance().click_close_back_button();
                        break;
                    case "faq":
                        FAQPage.getInstance().clickBackButton();
                        break;
                    case "close_session":
                        CloseSessionPage.getInstance().clickReturnBtn();
                        break;
                    case "confirmarpinnuevo":
                        ChangeNubiPassPage changePinPage = ChangeNubiPassPage.getInstance();
                        changePinPage.click_close_back_button();
                        break;
                    case "registro_alias_sube":
                    case "registro_sube":
                        SubeRegisterPage.getInstance().click_close_back_button();
                        break;
                    case "cargar_sube":
                        LoadSubePage.getInstance().click_close_back_button();
                        //Assert.assertTrue(nubiWallet.sube.cargarSube.uiObject.btnAtras().exists());
                        //nubiWallet.sube.cargarSube.uiObject.btnAtras().tap();
                        break;
                    case "revisar_datos_de_recarga":
                        CreditConfirmationPage.getInstance().click_close_back_button();
                        break;
                    case "agregar_contacto":
                        AddContactPage addContactPage = new AddContactPage();
                        Assert.assertTrue(addContactPage.btnBackExists());
                        addContactPage.click_close_back_button();
                        break;
                    case "ultimos_movimientos":
                        //Assert.assertTrue(nubiWallet.lastMovements.lastMovementsList.lastMovUiObject.lblScreenTitle().exists());
                        LastMovementsListPage.getInstance().click_close_back_button();
                        break;
                    case "detalle_de_movimientos_desde_dashboard":
                        nubiWallet.uiObjects.btnAtras().tap();
                        break;
                    case "pago_de_servicios":
                        ServicesPaymentPage.getInstance().click_close_back_button();
                        break;
                    case "escanear_codigo_de_barras":
                        BillsScanner.getInstance().click_close_back_button();
                        break;
                    case "ingresar_codigo_manual":
                        if(Android.driver.isKeyboardShown())
                            Android.driver.hideKeyboard();
                        ManualInputPage.getInstance().click_close_back_button();
                        break;
                    case "confirmacion_pago_servicio":
                        PaymentConfirmationPage.getInstance().click_close_back_button();
                        break;
                    case "factura_monto_abierto":
                        OpenAmountPage.getInstance().click_close_back_button();
                        break;
                    case "multifacturas":
                        MultipleInvoicesPage.getInstance().click_close_back_button();
                        break;
                    case "permisos_camara_denegados":
                        PermissionsDeniedPage.getInstance().click_close_back_button();
                        break;
                    case "select_compania_recarga_celular":
                        SelectCompanyPage.getInstance().click_close_back_button();
                        break;
                    case "rc_saldo_insuficiente":
                        ///nubiWallet.movements.recargaCelular.waitScreenSaldoInsuficiente();
                        //.movements.recargaCelular.objects.btnAtras().tap();
                        break;
                    case "rc_confirmacion":
                       ConfirmationPage.getInstance().click_close_back_button();
                        break;
                    case "rc_nubi_password":
                        NubiPassRechargeMobilePage.getInstance().click_close_back_button();
                        break;
                    case "ps_transaccion_fallida":
                        FailedTransactionPage.getInstance().click_close_back_button();
                        break;
                    case "aviso_viaje":
                        TravelNotifyPage travelPage = TravelNotifyPage.getInstance();

                        travelPage.click_close_back_button();
                        break;
                    case "lista_de_articulos":
                        ProductsListPage.getInstance().click_close_back_button();
                        break;
                    case "report_card":
                        ReportCardPage.getInstance().click_close_back_button();
                        break;
                    case "error_confirm_report_card":
                        ConfirmErrorPage.getInstance().click_close_back_button();
                        break;
                    case "datos_del_prestamo":
                        DownPaymentPage.getInstance().click_close_back_button();
                        break;
                    case "direccion_de_envio":
                        DeliveryAddressPage.getInstance().click_close_back_button();
                        break;
                    case "agregar_direccion":
                        //TODO: getInstance()
                        DirectionPage directionPage = new DirectionPage();
                        Assert.assertTrue(directionPage.elementExists(directionPage.getStreetNameInput()));
                        directionPage.click_close_back_button();
                        break;
                    case "confirmar_direccion":
                        ConfirmDirectionPage confirmDirectionPage = new ConfirmDirectionPage();
                        Assert.assertTrue(confirmDirectionPage.elementExists(confirmDirectionPage.getConfirmButton()));
                        confirmDirectionPage.click_close_back_button();
                        break;
                }

            }

            Android.hideKeyboardIfVisible();

        }catch (Exception | Error e){
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @And("Tap boton {string}")
    public void click_boton(String nameButton){
        try{
            switch (nameButton.toUpperCase()){
                case "FINALIZAR":
                    nubiWallet.uiObjects.btnFinish().waitToAppear(10);
                    nubiWallet.uiObjects.btnFinish().tap();
                    break;
            }
        }catch (Exception | Error e){
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /*****
     *  Validar lbl error en inputs
     * @param msj: mensaje a validar
     */
    @Then("Validar mensaje de error: {string}")
    public void validate_message_already_registered(String msj){
        try {
            do{
                if(nubiWallet.uiObjects.lblErrorMsg().exists()) {
                    Assert.assertTrue(nubiWallet.uiObjects.lblErrorMsg().getText().contains(msj));
                }else if(nubiWallet.uiObjects.lblInputError().exists()){
                    Assert.assertTrue(nubiWallet.uiObjects.lblErrorMsg().getText().contains(msj));
                }
            }while (!nubiWallet.uiObjects.lblInputError().exists() && !nubiWallet.uiObjects.lblErrorMsg().exists());


        }catch (Exception | Error e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @When("Usuario pulsa boton cerrar en pantalla {string}")
    public void user_tap_close_button_on_screen(String screen) throws Exception {
        try {
            screen = screen.toLowerCase();

            switch (screen) {
                case "change_nubipass":
                    ChangeNubiPassPage.getInstance().click_close_back_button();
                    break;
                case "ingresar_pin":
                    // Esta pantalla es la de R
                    PinCreationPage.getInstance().click_close_back_button();
                    break;
                case "agregar_contacto":
                    AddContactPage addContactPage = new AddContactPage();
                    Assert.assertTrue(addContactPage.btnBackExists());
                    addContactPage.click_close_back_button();
                    break;
                case "detalle_contacto":
                    ContactsDetailPage contactsDetailPage = new ContactsDetailPage();
                    Assert.assertTrue(contactsDetailPage.btnBackExists());
                    contactsDetailPage.click_close_back_button();
                    break;
                case "empresas_habilitadas":
                    CompaniesListPage.getInstance().click_close_back_button();
                    break;
            }

            Thread.sleep(200);

        }catch (Exception | Error e){
            logger.error(e.getMessage());
            throw e;
        }
    }


    public void press_back_button() throws InterruptedException{
        try {
            Android.hideKeyboardIfVisible();
            Thread.sleep(300);
            ((AndroidDriver)Android.driver).pressKey(new KeyEvent(AndroidKey.BACK));
        }catch (Exception | Error e){
            logger.error("Unable to press device back button");
            throw e;
        }
    }

    @Then("Presiona boton de aplicaciones recientes")
    public void press_recent_app_button() throws InterruptedException{
        try {
            String activity = "";
            int i=0;
            do{
                //Android.driver.runAppInBackground(Duration.ofSeconds(1));
                Android.driver.pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
                Thread.sleep(500);
                Android.driver.pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
                i++;
            }while ((!((AndroidDriver)Android.driver).currentActivity().contains("nubi") && !((AndroidDriver)Android.driver).currentActivity().contains("zendesk"))  && i<3);
        }catch (Exception e){
            logger.error("Error making background");
            e.printStackTrace();
            throw e;
        }
    }

    @Then("Ir al dashboard")
    public void go_to_dashboard() throws Exception{

            try{

                int i = 0;
                boolean existeBtnHome = false;
                do{
                    Android.hideKeyboardIfVisible();
                    press_back_button();
                    try {
                       // MenuPage menuPage = MenuPage.getInstance();
                        if(Wrapper.elementExists(MenuPage.getBtnHome())) {
                            existeBtnHome = true;
                            MenuPage.clickBtnHome();
                        }
                    } catch(Exception e) {
                        press_back_button();
                        //Thread.sleep(100);
                        i++;
                    }

                }while (!existeBtnHome && i<10);

                if(i==10)
                    throw new Exception("No se pudo volver al dashboard");
                else {
                    DashboardPage.getInstance();
                }
            }catch (Exception e){
               e.printStackTrace();
               throw e;
            }

    }


    /***
     * Dar o revocar permisos de contactos a la aplicacion
     * @param modo
     */

    @Given("Permiso para ver contactos {string}")
    public void setPermisosContactos(String modo){
        try {

            modo = modo.toLowerCase();

            if(modo.contentEquals("si")) {
                nubiWallet.grantContactPermissions();
            }else if (modo.contentEquals("no")) {
                nubiWallet.revokeContactPermissions();
                nubiWallet.open();
            }

        }catch (Exception | AssertionError e){
            logger.error("Error al manejar permisos android");
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Given("Permiso para camara {string}")
    public void setCameraPermissions(String modo){
        try {

            modo = modo.toLowerCase();

            if(modo.contentEquals("si")) {
                nubiWallet.grantCameraPermissions();
            }else if (modo.contentEquals("no")) {
                nubiWallet.revokeCameraPermissions();
                nubiWallet.open();
            }

        }catch (Exception | AssertionError e){
            logger.error("Error al manejar permisos android");
            logger.error(e.getMessage());
            throw e;
        }
    }

    @And("Usuario cierra la app")
    @And("user close the app")
    @When("Usuario mata la aplicacion")
    public void user_kill_close_the_app() {
        try{
            nubiWallet.closeApp();
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }

    }


    @Then("Resetear app {string}")
    public void resetear_app(String opc) throws InterruptedException, Exception {

        opc = opc.toLowerCase();

        if(NubiWallet.INICIATED_APP==1) {
            NubiWallet.INICIATED_APP = 0;
        }else{
            if (opc.toLowerCase().contains("si")) {
                Android.nubiWallet.resetApp();
            }

        }
    }

    @When("Usuario abre la app")
    @Then("open the app")
    public void open_the_app() {

        nubiWallet.open();
    }

    @When("Inicia la app")
    public void start_app(){
        ((AndroidDriver)Android.driver).startActivity(new Activity(Android.nubiWallet.packageID(), "com.nubi.featuresplash.view.SplashActivity"));
    }

    /****
     * VALIDAR LOS ELEMTNOS DE LA PANTALLA DADA EN
     * PARAM
     * @param pantalla
     */
    @Then("Validar pantalla {string}")
    public void validarPantalla(String pantalla) throws Exception{
        try {

            switch (pantalla.toLowerCase()){
                case "cuil":
                    CuilPage cuilPage = CuilPage.getInstance();

                    //Asserts
                    Assert.assertEquals(CuilPage.LBL_TITULO_TEXT, cuilPage.get_element_text(CuilPage.getLblTitle()));
                    Assert.assertEquals(CuilPage.DESCRIPTION_CUIL_TEXT, cuilPage.get_element_text(cuilPage.getDescriptionCuil()).replace("\n", " "));
                    Assert.assertEquals(CuilPage.LBL_SUBTITLE_CUIL_TEXT, cuilPage.get_element_text(cuilPage.getSubtitleCuil()).replace("\n", " "));
                    Assert.assertEquals(Registro.DNI, cuilPage.get_element_attribute(cuilPage.getDniInput(), "text"));
                    cuilPage.waitForElementEnabled(cuilPage.getButton());
                    break;
                case "dashboard":

                    //vALIDAR QUE EXISTE EL TITULO
                    //Assert.assertTrue(nubiWallet.dashboard.uiObject.lblDashboardTitle().exists());
                    DashboardPage dashboardPage = DashboardPage.getInstance();

                    int count = 0;
                    while (!dashboardPage.elementExists(dashboardPage.getBalance()) && count < 10) {
                        util.scrollTo();
                        count++;
                    }

                    //validacion de tarjeta balance

                    Assert.assertEquals(DashboardPage.BALANCE_TITLE_TEXT, dashboardPage.get_element_text(dashboardPage.getBalanceTitle()));
                    Assert.assertTrue(dashboardPage.elementExists(dashboardPage.getCashInBtn()));

                    //Validacion prestamos
                    count = 0;
                    if(dashboardPage.elementExists(dashboardPage.getImgPromotionImage())){
                        do{
                            util.scrollTo();
                            count++;
                        }while (!dashboardPage.elementExists(dashboardPage.getBtnPhonePurchaseButton()) && count < 10);

                        Assert.assertTrue(dashboardPage.elementExists(dashboardPage.getLblPromotionDescription()));
                    }

                    /**validacion de tarjeta cashin **/
                    count = 0;
                    while (!dashboardPage.elementExists(dashboardPage.getCashInCardText()) && count < 10) {
                        util.scrollTo();
                        count++;
                    }

                    Assert.assertTrue(dashboardPage.elementExists(dashboardPage.getCashInCardIcon()));

                    /**validacion de tarjeta prepaga **/
                    count = 0;
                    while (!dashboardPage.elementExists(dashboardPage.getPrepaidCardText()) && count < 10) {
                        util.scrollTo();
                        count++;
                    }

                    Assert.assertTrue(dashboardPage.elementExists(dashboardPage.getPrepaidCardIcon()));

                    util.scrollTo();

                    /**Validacion tarjeta de solicitudes**//*
                    count = 0;
                    if(dashboardPage.elementExists(dashboardPage.getMainRequestText())){
                        do{
                            util.scrollTo();
                            count++;
                        }while (!dashboardPage.elementExists(dashboardPage.getBtnAcceptRequest()) && count < 10);

                        Assert.assertTrue(dashboardPage.elementExists(dashboardPage.getBtnRejectRequest()));
                    }*/

                    /**Validacion tarjeta ultimos movimientos **/

                    while (!dashboardPage.elementExists(dashboardPage.getRecentTransactionsTitle()))
                        util.scrollTo();

                    Assert.assertEquals(DashboardPage.LAST_MOVEMENTS_TEXT, dashboardPage.get_element_text(dashboardPage.getRecentTransactionsTitle()));

                    while (!dashboardPage.elementExists(dashboardPage.getSeeMoreButton()))
                        util.scrollTo();


                    /**Validacion tarjeta de contactos **/
                    while (!dashboardPage.elementExists(dashboardPage.getFrequentContactsContent()))
                        util.scrollTo();

                    Assert.assertEquals("Contactos", dashboardPage.get_element_text(dashboardPage.getFrequentContactsTitle()));

                   break;
                case "caducated_token":
                    CaducatedTokenPage caducatedTokenPage = CaducatedTokenPage.getInstance();

                    //Asserts
                    Assert.assertEquals(CaducatedTokenPage.LBL_TITULO_TEXT, Wrapper.getElementText(CaducatedTokenPage.getLblTitle()));
                    Assert.assertTrue(Wrapper.elementExists(caducatedTokenPage.getDeviceImage()));
                    Assert.assertEquals(CaducatedTokenPage.SUBTITLE_TEXT, Wrapper.getElementText(caducatedTokenPage.getSubtitle()).replace("\n", " "));
                    Assert.assertEquals(CaducatedTokenPage.DISCLAIMER_TEXT, Wrapper.getElementText(caducatedTokenPage.getDisclaimerText()).replace("\n", " "));
                    break;
                case "prepaid_card":

                    //Refresh prepaid card list
                    Login.usuario.refreshPrepaidCardList();

                    List<PrepaidCard> list = Login.usuario.getPrepaidCardList();

                    if(list.isEmpty()){
                        NoPrepaidCardPage.getInstance();
                        Wrapper.waitForElementEnabled(NoPrepaidCardPage.getInstance().getButton());
                    }else{
                        for(PrepaidCard pc : list){
                            if(pc.getKind().contentEquals("PHYSICAL")) {
                                LandingPhysicalPage physicalPage = LandingPhysicalPage.getInstance();
                            }else {
                                VirtualPage virtualPage = VirtualPage.getInstance();

                                //Asserts
                                System.out.println(virtualPage.getCardTitle().getText());
                            }
                        }
                    }
                    break;
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @And("Espera {int} milisegundos")
    public void esperaSegundos(int ms) {
        try {
            Thread.sleep(ms);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Metodo para activar o desactivar el wifi del telefono
     * @param status: sirve para indicarle al metodo si queremos activar o desactivar el wifi.
     */
    @When("se {string} el wifi del telefono")
    public void switchWifiStatus(String status){
        try {
            switch (status){
                case "desactiva":
                    if(Android.driver.getConnection().isWiFiEnabled()){
                        Android.driver.toggleWifi();
                    }
                    break;
                case "activa":
                    if(!Android.driver.getConnection().isWiFiEnabled()){
                        Android.driver.toggleWifi();
                    }
                    break;
                default:
                    break;
            }
        }
        catch (Exception | Error e){
           logger.error(e.getMessage());
           throw  e;
        }
    }

    /******
     * DELETE ALL APK DATA
     */
    @Then("Elimina todos los datos de la aplicacion")
    public void clearData() {
        Android.nubiWallet.clearData();
    }


    /****
     * establece el status de validacion de dni
     * @param tipoStatus: 1 dni validado,
     *                     2 validacion dni baneada
     *                     3 validacion dni pendiente
     */
    @And("Validar que usuario tiene dni status {int}")
    public void set_status_dni_validation(int tipoStatus) throws SQLException, IOException, ParseException, Exception {
        try {

            int validation_status;
            DNI_Validation_API dni_validation_api = new DNI_Validation_API();
            DNIUserStatus userStatus = dni_validation_api.User_Status();

            //validation_status = userStatus.getValidation_status();

            Apps.dbQuery.actualizar_dni_validacion_status(tipoStatus, nubiWallet.usuario.getId());


        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    /**
     * setear fecha de nacimiento
     * @param tipo: 1 mayor de edad
     *              0 menor de edad
     */
    @And("Validar que usuario es mayor de edad {int}")
    public void set_fecha_nacimiento(int tipo) throws  Exception{
        try {
            Apps.dbQuery.actualizar_fecha_nacimiento(tipo, nubiWallet.usuario.getId());
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    /**
     * setear fecha de nacimiento
     * @param tipo: 0 para obligatorios, 1 para full
     * @param modoDatos: desambiguos o correctos
     */
    @When("Usuario completa form de datos adicionales {int} y modo {string} desde {string}")
    public void fill_datos_adicionales(int tipo, String modoDatos, String feature) throws  Exception{
        try {

            if (feature.toLowerCase().contentEquals("tarjeta_prepaga")){
                NoPrepaidCardPage noCard = NoPrepaidCardPage.getInstance();

                //Click on want my card
                noCard.click_on_want_mycard();
            }

            //Fill form
            DirectionPage directionPage = DirectionPage.getInstance();

            //Asserts
            if (feature.toLowerCase().contentEquals("tarjeta_prepaga"))
                Assert.assertEquals(directionPage.SUBTITLE_TEXT, directionPage.get_element_text(directionPage.getLblSubtitle()));
            else if (feature.toLowerCase().contentEquals("prestamos"))
                Assert.assertEquals(directionPage.SUBTITLE_LOAN_TEXT, directionPage.get_element_text(directionPage.getLblSubtitle()));
            Assert.assertFalse(directionPage.elementIsEnabled(directionPage.getButton()));
            directionPage.llenar_form(tipo, modoDatos);

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }


    /**
     * Validar la pantalla dependiendo de la manera que se lleno el fotm de datos adicionale
     * @param modoCompletitud: para evaluar si se lleno el form completo(1) o solo obligatorios(0)
     * @param tipoDatos: tipo de datos: correctos o desambiguos
     * @throws Exception
     */
    @Then("Validar pantalla con {int} y datos {string} desde {string}")
    public void validar_pantalla_datos_adicionales(int modoCompletitud, String tipoDatos, String feature){
        try {

            if(tipoDatos.toUpperCase().contentEquals("CORRECTOS")){
                ConfirmDirectionPage confirmDirectionPage = new ConfirmDirectionPage();
                confirmDirectionPage.waitForElementEnabled(confirmDirectionPage.getConfirmButton());

                //Asserts
                Assert.assertEquals(confirmDirectionPage.SUBTITLE_TEXT, confirmDirectionPage.get_element_text(confirmDirectionPage.getLblSubtitle()));
                if (feature.toLowerCase().contentEquals("tarjeta_prepaga")){
                    Assert.assertEquals(confirmDirectionPage.DISCLAIMER_TEXT, confirmDirectionPage.get_element_text(
                            confirmDirectionPage.getDisclaimerText()
                    ).replace("\n"," "));
                }

                //Assert street name
                while (!confirmDirectionPage.elementExists(confirmDirectionPage.getStreetName()))
                    util.scrollTo();
                Assert.assertTrue(confirmDirectionPage.elementExists(confirmDirectionPage.getStreetName()));

                // Assert stree number
                while (!confirmDirectionPage.elementExists(confirmDirectionPage.getStreetNumber()))
                    util.scrollTo();
                Assert.assertTrue(confirmDirectionPage.elementExists(confirmDirectionPage.getStreetNumber()));

                //If mode == 1, user fill all the form, and floor number field must be displayed
                if(modoCompletitud==1){
                    while (!confirmDirectionPage.elementExists(confirmDirectionPage.getFloorNumber()))
                        util.scrollTo();
                    Assert.assertTrue(confirmDirectionPage.elementExists(confirmDirectionPage.getFloorNumber()));
                }

                //Assert intersections streets, is not always displayed
                int swipes = 0;
                while (!confirmDirectionPage.elementExists(confirmDirectionPage.getStreetsIntersection()) && (swipes < 3)) {
                    util.scrollTo();
                    swipes++;
                }
                if (swipes < 3)
                    Assert.assertTrue(confirmDirectionPage.elementExists(confirmDirectionPage.getStreetsIntersection()));
                else
                    Assert.assertFalse(confirmDirectionPage.elementExists(confirmDirectionPage.getStreetsIntersection()));

                // Assert zip code
                while (!confirmDirectionPage.elementExists(confirmDirectionPage.getZipCode()))
                    util.scrollTo();
                Assert.assertTrue(confirmDirectionPage.elementExists(confirmDirectionPage.getZipCode()));

                // Assertcity
                while (!confirmDirectionPage.elementExists(confirmDirectionPage.getCity()))
                    util.scrollTo();
                Assert.assertTrue(confirmDirectionPage.elementExists(confirmDirectionPage.getCity()));

                // Assert province
                while (!confirmDirectionPage.elementExists(confirmDirectionPage.getProvince()))
                    util.scrollTo();
                Assert.assertTrue(confirmDirectionPage.elementExists(confirmDirectionPage.getProvince()));
            }else {
                DisambiguationPagePage disambiguationPagePage = new DisambiguationPagePage();

                //Asserts
                Assert.assertEquals(disambiguationPagePage.SUBTITLE_TEXT, disambiguationPagePage.get_element_text(
                        disambiguationPagePage.getLblSubtitle()
                ));

                int options = disambiguationPagePage.get_direction_options_size();
                Assert.assertTrue(options>1);

                disambiguationPagePage.waitForElementEnabled(disambiguationPagePage.getContinueButton());
                disambiguationPagePage.click_on_continue_button();

                //Recall validate form
                validar_pantalla_datos_adicionales(modoCompletitud, "correctos", feature);
            }
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }


    /**
     * Metodo para permitir o denegar el acceso a la camara del dispositivo
     * @param access acepta los valores 'si' o 'no', sino por defecto habilita la camara
     * @throws Exception en caso de no encontrar los botones Permitir o Denegar.
     */
    @Then("Permitir el acceso a la camara {string}")
    public void allowCameraFromDevice(String access) throws Exception{

        access = access.toLowerCase();
        ContactsPage contactsPage = new ContactsPage();

        try {
            switch (access){
                case "si":
                default:
                    contactsPage.clickAllow();
                    break;
                case "no":
                    contactsPage.clickReject();
                    break;
            }
        }
        catch (Exception e){
            logger.error(e.getStackTrace());
            throw new Exception("No se pudo encontrar el elemento para permitir o denegar acceso a la camara");
        }
        finally {
            Thread.sleep(3);
        }
    }

    /**
     * Valida que exista el pop up para dar permisos a tener acceso a la camara del dispositivo.
     * @throws Exception cuando no se encuentra el boton Permitir o Rechazar
     */
    @Then("Valida que existe el pop-up para permitir o denegar los permisos de la camara")
    public void existAccessCameraPopUp() throws Exception{
        try {
            ContactsPage contactsPage = new ContactsPage();
        }
        catch (Exception e){
            logger.error(e.getCause());
            throw new Exception("No se halló el pop up para dar accesos a la camara del dispositivo");
        }
    }


    /**
     * Valida que el teclado del dispositivo este activo u oculto
     * @param keyboardStatus el estatus que esperamos que tenga el teclado (activo u oculto)
     * @throws Exception no se pudo validar el estatus del teclado.
     */
    @And("Valida que el teclado este {string}")
    public void assertKeyboardIsDisplayed(String keyboardStatus) throws Exception{

        keyboardStatus = keyboardStatus.toLowerCase();

        try {
            switch (keyboardStatus){
                case "activo":
                    Assert.assertTrue(Android.driver.isKeyboardShown());
                    break;
                case "oculto":
                    Assert.assertFalse(Android.driver.isKeyboardShown());
                    break;
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new Exception("No se pudo validar el status del teclado");
        }
    }

    /**
     * Permite borrar el contenido de cualquier input en cualquier pantalla
     * este metodo debe ser antecedido por un assertion que valide que se esta en la pantalla deseada.
     * @param input nombre del input al que queremos borrar su contenido
     * @throws Exception cuando no halla el input o no puede borrarlo
     */
    @And("Se borra el contenido del campo {string}")
    public void clearAnSpecificInput(String input) throws Exception{

        input = input.toLowerCase();

        try {
            switch (input){
                case "monto_abierto_de_factura":
                    //nubiWallet.pagoServicios.montoAbierto.clearAmountOpenInput();
                    break;
                case "codigo_factura_manual":
                    ManualInputPage.getInstance().clearTextIntoInvoiceNumberInput();
                    break;
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new Exception("No se puedo hallar / borrar el input " + input);
        }
    }

    /**
     * Permite validar los elementos de la pantalla de ingreso de PIN
     * @param validPin (nos permite validar si se introdujo o no un pin incorrecto)
     */
    @And("Se validan los elementos de la pantalla pin despues de {string}")
    public void assertPinScreenElements(String validPin) {

        try {
            // Assert.assertTrue(nubiWallet.uiObjects.btnCerrar().exists());
            //Assert.assertEquals(nubiWallet.registro.pin.getTEXT_SCREEN_TITLE(), nubiWallet.registro.pin.uiObject.title().getText());
            Assert.assertTrue(nubiWallet.uiObjects.txtPrimerDigito().exists());
            Assert.assertTrue(nubiWallet.uiObjects.txtSegundoDigito().exists());
            Assert.assertTrue(nubiWallet.uiObjects.txtTercerDigito().exists());
            Assert.assertTrue(nubiWallet.uiObjects.txtCuartoDigito().exists());

            if (validPin.contentEquals("invalido")){
//                PinPage pinPage = new PinPage();
//                Assert.assertEquals(pinPage.TEXT_INVALID_PIN.trim(), pinPage.getErrorMessage().trim());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @When("Usuario ingresa un numero de pin {string}")
    public void typeAPinNumber(String validPin) {
        try {
            CommonPinPage.getInstance().set_valid_pin();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Nos permite crear uno o mas usuarios desde el backend y agregarle si se desea, contactos Nubi y Bancario
     * @param cantUsers número de usuarios que se desean crear
     * @param genero masculino / femenino
     * @param contactType el tipo de contactos que le queremos agregar a los nuevos usuarios
     *                   (nubi_y_bancario, nubi, bancario o ninguno)
     * @throws Exception
     */
    @Given("Se crean {int} usuarios desde el backend de genero {string} con contactos {string}")
    public void createNewUserFromBackEnd(int cantUsers, String genero, String contactType) throws Exception {
        try {
            UserGenerator userGenerator = new UserGenerator();
            Auth_API auth_api = new Auth_API();
            Contacts_API contacts = new Contacts_API();
            CommonStepsMovements commonStepsMovements = new CommonStepsMovements();
            DBQuery dbQuery = new DBQuery();

            String userName;
            String password = Apps.util.getProperty("credentials","user.password", "properties");

            userGenerator.createNewUser(cantUsers, genero);

            switch (contactType){

                case "nubi_y_bancario":
                    for (int i=0; i < cantUsers; i++){
                        //Delete previous cellphone contacts
                        CommonStepsMovements.originUser = new User();
                        CommonStepsMovements.originUser.setId(userGenerator.getCreatedUsers().get(i).getUser_data().getId());
                        commonStepsMovements.delete_contacts();
                        //Add the new contacts to device depending the number of new users
                        //if the new users are less than 2, the nubi contact will be added from Database
                        if (cantUsers < 2) {
                            String phoneNumber = userGenerator.getCreatedUsers().get(0).getUser_data().getPhone_number();
                            User contactNubi =  dbQuery.getDestinationUser(phoneNumber);
                            userGenerator.addNewUsersToDevice(contactNubi);
                        }
                        //else, the new contacts will be added to each other
                        else{
                            //if the new user is the last one, add the first as nubi contact
                            if (i == cantUsers - 1)
                                userGenerator.addNewUsersToDevice(userGenerator.getCreatedUsers().get(0));
                            //else, the second new user will be added to the first, the third to the second and so on
                            else
                                userGenerator.addNewUsersToDevice(userGenerator.getCreatedUsers().get(i + 1));
                        }
                        //Set the auth token needed to add a bank contact
                        userName = userGenerator.getCreatedUsers().get(i).getUser_data().getUsername();
                        AuthToken token =  auth_api.setAuthTokenSpecificUser(userName);
                        //Add a bank contact to the actual user
                        contacts.createBankingContact(token.getAccess_token());
                        //Login into Nubi App to synchronize with Nubi contacts stored in the cellphone
                        LoginPage loginPage = LoginPage.getInstance();
                        loginPage.login(userName, password);
                        DashboardPage.getInstance();
                        setPermisosContactos("si");
                        MenuPage menuPage = MenuPage.getInstance();
                        menuPage.clickBtnMenu();
                        menuPage.clickContactsButton();
                        usuario_esta_en_pantalla("con_contactos_y_permiso");
                        //Reset the app for the next user
                        nubiWallet.resetApp();
                    }
                    break;
                case "nubi":
                    for (int i=0; i < cantUsers; i++){
                        //Delete previous cellphone contacts
                        CommonStepsMovements.originUser = new User();
                        CommonStepsMovements.originUser.setId(userGenerator.getCreatedUsers().get(i).getUser_data().getId());
                        commonStepsMovements.delete_contacts();
                        //Add the new contacts to device depending the number of new users
                        //if the new users are less than 2, the nubi contact will be added from Database
                        if (cantUsers < 2) {
                            String phoneNumber = userGenerator.getCreatedUsers().get(0).getUser_data().getPhone_number();
                            User contactNubi =  dbQuery.getDestinationUser(phoneNumber);
                            userGenerator.addNewUsersToDevice(contactNubi);
                        }
                        //else, the new contacts will be added to each other
                        else{
                            //if the new user is the last one, add the first as nubi contact
                            if (i == cantUsers - 1)
                                userGenerator.addNewUsersToDevice(userGenerator.getCreatedUsers().get(0));
                                //else, the second new user will be added to the first, the third to the second and so on
                            else
                                userGenerator.addNewUsersToDevice(userGenerator.getCreatedUsers().get(i + 1));
                        }
                        //Login into Nubi App to synchronize with Nubi contacts stored in the cellphone
                        userName = userGenerator.getCreatedUsers().get(i).getUser_data().getUsername();
                        LoginPage loginPage = LoginPage.getInstance();
                        loginPage.login(userName, password);
                        DashboardPage.getInstance();
                        setPermisosContactos("si");
                        MenuPage menuPage = MenuPage.getInstance();
                        menuPage.clickBtnMenu();
                        menuPage.clickContactsButton();
                        usuario_esta_en_pantalla("con_contactos_y_permiso");
                        //Reset the app for the next user
                        nubiWallet.resetApp();
                    }
                    break;
                case "bancario":
                    for (CompleteRegistration user : userGenerator.getCreatedUsers()){
                        //Set the auth token needed to add a bank contact
                        userName = user.getUser_data().getUsername();
                        AuthToken token =  auth_api.setAuthTokenSpecificUser(userName);
                        //Add a bank contact to the actual user
                        contacts.createBankingContact(token.getAccess_token());
                    }
                    break;
                case "ninguno":
                    break;
            }
        }
        catch (Exception e){
            logger.error(e);
            throw new Exception();
        }
    }

    /****
     * Metodo para setear el pin
     * @param pin: incorrecto , correcto o incompleto
     */
    @Then("Usuario confirma clave Nubi {string}")
    @And("Ingresa clave nubi {string}")
    @When("Usuario ingresa una nueva clave Nubi {string}")
    public void setear_pin(String pin) throws Exception {
        try{

            //Before set the nubi password, we change it in order
            //to avoid the error for incorrect nubi password
            Auth_API auth_api = new Auth_API();
            ChangePin changePin = auth_api.change_pin();

            try{
                boolean succes = changePin.isSuccess();
                Assert.assertTrue(succes);
            }catch (AssertionError e){
                logger.info("No change needed");
            }

            CommonPinPage commonPinPage = CommonPinPage.getInstance();

            switch (pin.toUpperCase()){
                case "CORRECTA":
                case "CORRECTO":
                    commonPinPage.set_valid_pin();
                    break;
                case "INCOMPLETO":
                    commonPinPage.set_incomplete_pin();
                    break;
                case "INCORRECTO":
                    commonPinPage.set_invalid_pin();
                  /*  Random randomDigit = new Random();
                    changePinPage.clickFirstDigit();
                    Android.adb.setTexto(Integer.toString(randomDigit.nextInt(10)));

                    //Android.app.nubiWallet.perfil.seguridad.cambioPin.uiObject.txtSegundoDigito().tap();
                    Android.adb.setTexto(Integer.toString(randomDigit.nextInt(10)));

                    //Android.app.nubiWallet.perfil.seguridad.cambioPin.uiObject.txtTercerDigito().tap();
                    Android.adb.setTexto(Integer.toString(randomDigit.nextInt(10)));

                    //Android.app.nubiWallet.perfil.seguridad.cambioPin.uiObject.txtCuartoDigito().tap();
                    Android.adb.setTexto(Integer.toString(randomDigit.nextInt(10)));*/
                    break;
                case"":
                    break;
            }
        }catch (Exception | Error err){
            err.printStackTrace();
            logger.error(err);
            throw err;
        }
    }

    /****
     * Set nubi password method
     * @param flow: flujo o funcionalidad a probar
     */
    @And("Usuario ingresa nueva clave nubi para flujo {string}")
    @When("Usuario ingresa clave nubi para flujo {string}")
    public void set_nubi_password(String flow) throws Exception{
        try{
            //Before set the nubi password, we change it in order



            switch (flow.toUpperCase()){
                   case "MOBILE_RECHARGE":
                        NubiPassRechargeMobilePage.getInstance().set_valid_pin();
                        break;
                   case "SUBE_RECHARGE":
                        NubiPassRechargeSubePage.getInstance().set_valid_pin();
                        break;
                case "P2P":
                    NubiPassP2PPage.getInstance().set_valid_pin();
                        break;
                case "FORGOT_NUBI_PASS":
                       SetNubiPassPage nubiPassPage = SetNubiPassPage.getInstance();

                        //Asserts
                        Assert.assertTrue(Wrapper.elementExists(SetNubiPassPage.getBackOrCloseBtn()));
                        Assert.assertTrue(Wrapper.getElementText(SetNubiPassPage.getLblTitle()).contentEquals(SetNubiPassPage.TITLE_TEXT) ||
                                Wrapper.getElementText(SetNubiPassPage.getLblTitle()).contentEquals("Me olvidé el PIN"));
                       //Assert.assertEquals(api.apps.android.nw.perfil.seguridad.claveNubi.olvido.SetNubiPassPage.TITLE_TEXT, Apps.wrapper.getElementText(api.apps.android.nw.perfil.seguridad.claveNubi.olvido.SetNubiPassPage.getLblTitle()).replace("\n", " "));

                       Assert.assertTrue(Wrapper.getElementText(SetNubiPassPage.getLblChangePin()).contentEquals(SetNubiPassPage.SET_NEW_NUBI_PASSWORD_SUBTITLE_TEXT) ||
                               Wrapper.getElementText(SetNubiPassPage.getLblChangePin()).contentEquals("Ingresá tu nuevo PIN"));
                       //Assert.assertEquals(api.apps.android.nw.perfil.seguridad.claveNubi.olvido.SetNubiPassPage.SET_NEW_NUBI_PASSWORD_SUBTITLE_TEXT, Apps.wrapper.getElementText(api.apps.android.nw.perfil.seguridad.claveNubi.olvido.SetNubiPassPage.getLblChangePin()).replace("\n", " "));

                       nubiPassPage.set_valid_pin();
                       Thread.sleep(400);
                        break;
                case "CHANGE_CURRENT_NUBIPASS":
                    ChangeNubiPassPage changeNubiPassPage = ChangeNubiPassPage.getInstance();

                    //Asserts
                    Assert.assertTrue(Wrapper.elementExists(ChangeNubiPassPage.getBackOrCloseBtn()));

                    Assert.assertTrue(Wrapper.getElementText(ChangeNubiPassPage.getLblTitle()).contentEquals(ChangeNubiPassPage.CHANGE_PIN_TITLE_TEXT) ||
                            Wrapper.getElementText(ChangeNubiPassPage.getLblTitle()).contentEquals("Cambiar PIN"));
                    //Assert.assertEquals(ChangeNubiPassPage.CHANGE_PIN_TITLE_TEXT, Apps.wrapper.getElementText(ChangeNubiPassPage.getLblTitle()).replace("\n", " "));

                    Assert.assertTrue(Wrapper.getElementText(changeNubiPassPage.getLblChangePin()).contentEquals(ChangeNubiPassPage.SET_CURRENT_PIN__SUBTITLE_TEXT) ||
                            Wrapper.getElementText(changeNubiPassPage.getLblChangePin()).contentEquals("Ingresá tu PIN actual"));
                    //Assert.assertEquals(ChangeNubiPassPage.SET_CURRENT_PIN__SUBTITLE_TEXT, Apps.wrapper.getElementText(changeNubiPassPage.getLblChangePin()).replace("\n", " "));

                    changeNubiPassPage.set_valid_pin();
                    Thread.sleep(400);
                    break;
                case "CHANGE_NUBIPASS":
                    ChangeNubiPassPage nubipass = ChangeNubiPassPage.getInstance();

                    //Asserts
                    Assert.assertTrue(Wrapper.elementExists(ChangeNubiPassPage.getBackOrCloseBtn()));

                    Assert.assertTrue(Wrapper.getElementText(ChangeNubiPassPage.getLblTitle()).contentEquals(ChangeNubiPassPage.CHANGE_PIN_TITLE_TEXT) ||
                            Wrapper.getElementText(ChangeNubiPassPage.getLblTitle()).contentEquals("Cambiar PIN"));
                    //Assert.assertEquals(ChangeNubiPassPage.CHANGE_PIN_TITLE_TEXT, Apps.wrapper.getElementText(ChangeNubiPassPage.getLblTitle()).replace("\n", " "));

                    Assert.assertTrue(Wrapper.getElementText(nubipass.getLblChangePin()).contentEquals(ChangeNubiPassPage.SET_NEW_PIN__SUBTITLE_TEXT) ||
                            Wrapper.getElementText(nubipass.getLblChangePin()).contentEquals("Ingresá tu nuevo PIN"));
                    //Assert.assertEquals(ChangeNubiPassPage.SET_NEW_PIN__SUBTITLE_TEXT, Apps.wrapper.getElementText(nubipass.getLblChangePin()).replace("\n", " "));

                    nubipass.set_valid_pin();
                    Thread.sleep(400);
                    break;
            }



        }catch (Exception | Error err){
            err.printStackTrace();
            logger.error(err);
            throw err;
        }
    }

    @Then("Validar que no esta activa la aplicacion NUBI")
    public void validate_nubi_is_not_activated() throws Exception{
        try{
            do{
                Thread.sleep(100);
            }while (((AndroidDriver)Android.driver).currentActivity().contains("nubi"));

        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    /**
     * Metodo para activar o desactivar el modo avion del telefono
     * @param status: sirve para indicarle al metodo si queremos activar o desactivar el modo avion.
     */
    @When("se {string} el modo avion del telefono")
    public void switcAirplaneModeStatus(String status){

        try {
            switch (status){
                case "desactiva":
                    if(Android.driver.getConnection().isAirplaneModeEnabled()){
                        Android.driver.toggleAirplaneMode();
                    }
                    break;
                case "activa":
                    if(!Android.driver.getConnection().isAirplaneModeEnabled()){
                        Android.driver.toggleAirplaneMode();
                    }
                    break;
                default:
                    break;
            }
        }
        catch (Exception | Error e){
            logger.error(e.getMessage());
            throw  e;
        }
    }

    /**
     * Metodo para activar o desactivar los datos moviles del telefono
     * @param status: sirve para indicarle al metodo si queremos activar o desactivar los datos moviles.
     */
    @When("se {string} los datos moviles del telefono")
    public void switcMobileDataStatus(String status){

        try {
            switch (status){
                case "desactiva":
                    if(Android.driver.getConnection().isDataEnabled()){
                        Android.driver.toggleData();
                    }
                    break;
                case "activa":
                    if(!Android.driver.getConnection().isDataEnabled()){
                        Android.driver.toggleData();;
                    }
                    break;
                default:
                    break;
            }
        }
        catch (Exception | Error e){
            logger.error(e.getMessage());
            throw  e;
        }
    }

    /**
     * Metodo para cambiar el tipo de conexion del dispositivo
     * @param conexion: GSM, EDGE, EVDO, GPRS, HSDPA, LTE, SCSD, UMTS, FULL.
     */
    @When("se cambia la conexion movil a {string}")
    public void switchMobileConexionSpeed(String conexion){

        try {
            switch (conexion.toLowerCase()){
                case "gsm":
                    Android.driver.setNetworkSpeed(NetworkSpeed.GSM);
                    break;
                case "edge":
                    Android.driver.setNetworkSpeed(NetworkSpeed.EDGE);
                    break;
                case "evdo":
                    Android.driver.setNetworkSpeed(NetworkSpeed.EVDO);
                    break;
                case "gprs":
                    Android.driver.setNetworkSpeed(NetworkSpeed.GPRS);
                    break;
                case "hsdpa":
                    Android.driver.setNetworkSpeed(NetworkSpeed.HSDPA);
                    break;
                case "lte":
                    Android.driver.setNetworkSpeed(NetworkSpeed.LTE);
                    break;
                case "scsd":
                    Android.driver.setNetworkSpeed(NetworkSpeed.SCSD);
                    break;
                case "umts":
                    Android.driver.setNetworkSpeed(NetworkSpeed.UMTS);
                    break;
                case "full":
                    Android.driver.setNetworkSpeed(NetworkSpeed.FULL);
                    break;
                default:
                    break;
            }
        }
        catch (Exception | Error e){
            logger.error(e.getMessage());
            throw  e;
        }
    }

    @When("Usuario minimiza la app {int} segundos y vuelve abrirla")
    public void minimizeTheApp(int seconds) throws Exception{
        try {
            Android.driver.pressKey((new KeyEvent(AndroidKey.HOME)));
            Thread.sleep(seconds * 1000);
            press_recent_app_button();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error in background method");
        }
    }



    @Given("Se crean {string} usuarios desde el backend separados por {string} desde un archivo csv")
    public void createNewUserFromCSVFileBackEnd(String fileName, String separatod) throws Exception {
        try {

            List<String[]> content = new ArrayList<>();
            String userDir = System.getProperty("user.dir");
            String filePath = userDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "properties" + File.separator + fileName + ".csv";
            try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    content.add(line.split(separatod));
                }
            } catch (FileNotFoundException e) {
                //Some error logging
            }

            int rowCount = 0;
            for  ( String[] row : content ) {

                UserGenerator userGenerator = new UserGenerator();
                String password = util.getProperty("credentials", "loan.user.password", "properties");

                //userGenerator.createNewUser(cantUsers, genero);
                // Column 3 GENDER should be F or M (if it doesn't have a value then it pics one randomlly)

                if ( 3 >= row.length ) {
                    if (new Random().nextBoolean()) {
                        //Male
                        userGenerator.setPrefix(20);
                        userGenerator.setGender(0);
                    } else {
                        //Female
                        userGenerator.setPrefix(27);
                        userGenerator.setGender(1);
                    }
                }
                else {
                    if ( row[3].trim().equals("") ) {
                        if (new Random().nextBoolean()) {
                            //Male
                            userGenerator.setPrefix(20);
                            userGenerator.setGender(0);
                        } else {
                            //Female
                            userGenerator.setPrefix(27);
                            userGenerator.setGender(1);
                        }
                    }
                    switch (row[3].toLowerCase()) {
                        case "m":
                            userGenerator.setPrefix(20);
                            userGenerator.setGender(0);
                            break;
                        case "f":
                            userGenerator.setPrefix(27);
                            userGenerator.setGender(1);
                            break;
                    }
                }


                //1. Se genera un email y se confirma mediante un token.
                String email = util.gerenarEmail();
                userGenerator.setEmail(email);
                userGenerator.emailRegistrationAndConfirmation(email);

                //2. Se valida si se hizo efectivo la confirmación de email
                userGenerator.confirmRegistrationProcess();

                if (userGenerator.getRegistrationConfirmed().getEmail_confirmed()) {

                    //3. Se reserva el numero de CUIL del nuevo usuario
                    // DNI en la columna 2
                    // Prefijo del CUIL en la columna 31
                    // Sufijo del cuil en la columna 32
                    String cuil = "";
                    String dni = "";
                    if (  2 >= row.length  )
                        dni = util.calcularCuil(userGenerator.getGender(), userGenerator.getPrefix(), "DNI");
                    else
                        if ( row[2].trim().equals("") )
                            dni = util.calcularCuil(userGenerator.getGender(), userGenerator.getPrefix(), "DNI");
                        else {
                            dni = row[2];
                            userGenerator.setPrefix(userGenerator.calcularSoloPrefijo(userGenerator.getGender(), dni));
                        }



                    if ( 33 >= row.length  )
                        if ( 32 >= row.length )
                            cuil = util.calcularCuil(userGenerator.getGender(), userGenerator.getPrefix(), "CUIL", dni);
                        else
                            cuil = util.calcularCuil(userGenerator.getGender(), userGenerator.getPrefix(), "CUIL", dni);
                    else
                        if ( row[33].trim().equals("")  )
                            if ( row[32].trim().equals("")  )
                                cuil = util.calcularCuil(userGenerator.getGender(), userGenerator.getPrefix(), "CUIL", dni);
                            else
                                cuil = util.calcularCuil(userGenerator.getGender(), userGenerator.getPrefix(), "CUIL", dni);
                        else
                            cuil = row[32] + dni + row[33];

                    userGenerator.cuilReservation(cuil);

                    //4. Se registra el username del nuevo usuario
                    String username = util.generateAnUsernameFromAnEmail(userGenerator.getEmail());
                    userGenerator.usernameRegistration(username, password);

                    //5. Se registra el numero de telefono del nuevo usuario
                    // phone number column 5 like: 011415415415
                    String phoneNumber = "";
                    if (  5 >= row.length  )
                        phoneNumber = util.generateAPhoneNumber();
                    else {
                        if ( row[5].trim().equals("")  )
                            phoneNumber = util.generateAPhoneNumber();
                        else
                            phoneNumber = "+549" + row[5].substring(1);
                    }
                    userGenerator.phoneRegistration(phoneNumber);

                    //6. Se confirma el numero de telefono ingresando el codigo sms
                    userGenerator.phoneConfirmation();
                    //7. Se registra el pin del nuevo usuario
                    userGenerator.pinRegistration();
                    //8. Se completa el registro enviando nombre, apellido, sexo y dni
                    // Column 0 FIRST NAME - Column 1 LAST NAME
                    String name = "";
                    String lastName = "";
                    if (  0 >= row.length  )
                        name = Apps.util.generarInformacionPersonal("NAMES");
                    else
                        if ( row[0].trim().equals("")  )
                            name = Apps.util.generarInformacionPersonal("NAMES");
                        else
                            name = row[0];

                    if (  1 >= row.length  )
                        lastName = Apps.util.generarInformacionPersonal("LASTNAMES");
                    else
                        if ( row[1].trim().equals("")  )
                            lastName = Apps.util.generarInformacionPersonal("LASTNAMES");
                        else
                            lastName = row[1];

                    userGenerator.completeRegistration(name, lastName, dni);

                    List<Account> userAccounts = dbQuery.getAccountFromUser(userGenerator.getCompleteRegistration().getUser_data().getId(),
                            userGenerator.getCompleteRegistration().getUser_data().getEmail(),
                            password,
                            "", "");

                    logger.info("Usuario de fila -> " + rowCount + ": "
                            + "\r\nUsername: " + userGenerator.getCompleteRegistration().getUser_data().getUsername()
                            + "\r\nPhoneNumber: " + userGenerator.getCompleteRegistration().getUser_data().getPhone_number()
                            + "\r\nUserId: " + userGenerator.getCompleteRegistration().getUser_data().getId()
                            + "\r\nEmail: " + userGenerator.getCompleteRegistration().getUser_data().getEmail()
                            + "\r\nName: " + userGenerator.getCompleteRegistration().getUser_data().getName()
                            + "\r\nLast Name: " + userGenerator.getCompleteRegistration().getUser_data().getLast_name()
                            + "\r\nCuil: " + userGenerator.getCompleteRegistration().getUser_data().getCuil()
                            + "\r\nAccount Number: " + userAccounts.get(0).getNumber()
                            + "\r\nGender: " + ((userGenerator.getGender() == 0) ? "Male" : "Female"));
                    rowCount++;
                } else {
                    logger.info("No se pudo confirmar el proceso de registro");
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        }

    @Then("Confirmar datos adicionales")
    public void tap_btn_confirmar(){
        try{
            ConfirmDirectionPage.getInstance().click_confirm_button();
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    /**
     * Chequea si los permiso ya fueron otorgados
     * @param
     */
    @And("Aceptar permisos de {string}")
    public void evaluate_app_permissions(String permissionType) throws InterruptedException{
        try{
            switch (permissionType.toUpperCase()){
                case "CAMARA":
                case "CONTACTOS":
                    //if(CONTACTS_PERMISSION==0){
                        PermissionsPopUp.getInstance().click_allow_permission();
                        //CONTACTS_PERMISSION = 1;
                   // }
                    break;
            }
        }catch (Exception | Error er){
            logger.error(er);
            throw er;
        }
    }

    @When("Pulsar DeepLink {string}")
    public void pulsarDeepLink(String linkType) {
        try {
            Util util = Apps.util;

            //Set link header
            String link = util.getProperty("DeepLinks", "HEADER", "properties");

            switch (linkType.toUpperCase()){
                case "SUBE":
                    //Add sube path to link
                    link+=util.getProperty("DeepLinks", "SUBE_LINK", "properties");

                    break;
                case "PREPAID_CARD":
                    link+= util.getProperty("DeepLinks", "PREPAID_CARD_LINK", "properties");
                    break;
                case "REGISTER":
                    link+= util.getProperty("DeepLinks", "REGISTER_LINK", "properties");
                    break;
                case "SERVICE_PAYMENT":
                    link+= util.getProperty("DeepLinks", "SERVICE-PAYMENT_LINK", "properties");
                    break;
                case "CASH_IN":
                    link+= util.getProperty("DeepLinks", "CASH-IN_LINK", "properties");
                    break;
                case "MOBILE_RECHARGE":
                    link+= util.getProperty("DeepLinks", "MOBILE-RECHARGE_LINK", "properties");
                    break;
            }

            //Execute deep link command
            ((AndroidDriver)Android.driver).executeScript("mobile: deepLink", ImmutableMap.of("url", link, "package", Android.nubiWallet.packageID()));

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    @When("Pulsar desde background DeepLink de {string}")
    public void click_deepLink_from_background(String linkType) throws InterruptedException {
        try {
            Util util = Apps.util;

            //Set link header
            String link = util.getProperty("DeepLinks", "HEADER", "properties");

            do{
                //Android.driver.runAppInBackground(Duration.ofSeconds(1));
                Android.driver.pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
                Thread.sleep(400);
                Android.driver.pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
                Thread.sleep(400);
            }while ((((AndroidDriver)Android.driver).currentActivity().contains("nubi") && ((AndroidDriver)Android.driver).currentActivity().contains("zendesk")));

            switch (linkType.toUpperCase()){
                case "SUBE":
                    //Add sube path to link
                    link+=util.getProperty("DeepLinks", "SUBE_LINK", "properties");

                    break;
                case "PREPAID_CARD":
                    //Add prepaid_card path to link
                    link+=util.getProperty("DeepLinks", "PREPAID_CARD_LINK", "properties");
                    break;
                case "REGISTER":

                    link+=util.getProperty("DeepLinks", "REGISTER_LINK", "properties");
                    break;
                case "SERVICE_PAYMENT":
                    link+=util.getProperty("DeepLinks", "SERVICE-PAYMENT_LINK", "properties");
                    break;
                case "CASH_IN":
                    link+= util.getProperty("DeepLinks", "CASH-IN_LINK", "properties");
                    break;
                case "MOBILE_RECHARGE":
                    link+= util.getProperty("DeepLinks", "MOBILE-RECHARGE_LINK", "properties");
                    break;
            }

            //Execute deep link command
            ((AndroidDriver)Android.driver).executeScript("mobile: deepLink", ImmutableMap.of("url", link, "package", Android.nubiWallet.packageID()));

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    @When("Pulsar desde flujo {string} DeepLink de {string}")
    public void pulsarDesdeFlujoDeepLinkDe(String flow, String linkType) throws Exception {
        try {
            Util util = Apps.util;

            //Set link header
            String link = util.getProperty("DeepLinks", "HEADER", "properties");

            //Validate the deepLin text
            switch (linkType.toUpperCase()){
                case "SUBE":
                    //Add sube path to link
                    link+=util.getProperty("DeepLinks", "SUBE_LINK", "properties");
                    break;
                case "PREPAID_CARD":
                    //Add prepaid card path to link
                    link+=util.getProperty("DeepLinks", "PREPAID_CARD_LINK", "properties");
                    break;
                case "REGISTER":
                    link+=util.getProperty("DeepLinks", "REGISTER_LINK", "properties");
                    break;
                case "SERVICE_PAYMENT":
                    link+=util.getProperty("DeepLinks", "SERVICE-PAYMENT_LINK", "properties");
                    break;
                case "CASH_IN":
                    link+= util.getProperty("DeepLinks", "CASH-IN_LINK", "properties");
                    break;
                case "MOBILE_RECHARGE":
                    link+= util.getProperty("DeepLinks", "MOBILE-RECHARGE_LINK", "properties");
                    break;
            }

            //validate flow to navigate
            switch (flow.toUpperCase()){
                case "CASH_IN":
                    //Go to chas_in page
                    DashboardPage.getInstance().click_cashin_button();
                    CashInLandingPage.getInstance();

                    break;
                case "PROFILE":
                    DashboardPage.getInstance();
                    MenuPage.getInstance().clickBtnPerfil();
                    PerfilPage.getInstance();


                    break;
                case "MOBILE_RECHARGE":
                    DashboardPage.getInstance();
                    MenuPage.getInstance().clickBtnMenu();
                    MenuPage.getInstance().clickRecargaCelular();
                    SelectCompanyPage.getInstance();


                    break;
                case "SUBE":
                    DashboardPage.getInstance();
                    MenuPage menuPage = MenuPage.getInstance();
                    menuPage.clickBtnMenu();
                    menuPage.clickSubeButton();

                    usuario_esta_en_pantalla("sube");
                    break;
                case "SERVICE_PAYMENT":
                    DashboardPage.getInstance();
                    MenuPage menuPage2 = MenuPage.getInstance();
                    menuPage2.clickBtnMenu();
                    menuPage2.clickPayBillsButton();

                    usuario_esta_en_pantalla("pago_de_servicios");
                    break;
            }

            //Click deepLink
            ((AndroidDriver)Android.driver).executeScript("mobile: deepLink", ImmutableMap.of("url", link, "package", Android.nubiWallet.packageID()));

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    @Then("Validar snackbar {string}")
    public void validarSnackbar(String snack) throws Exception {
        try {
            switch (snack.toUpperCase()){
                case "RAPIPAGO_CODE_COPY":
                    Assert.assertEquals(RapiPagoPage.SNACKBAR_TEXT, Wrapper.getElementText(RapiPagoPage.getInstance().getSnackbar_text()));
                    break;
            }
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }
}


