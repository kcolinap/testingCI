package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.TextList;
import api.apps.android.nw.basePages.BasePage;
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
import api.apps.android.nw.operaciones.pagoServicios.saldoInsuficiente.InsufficientBalance;
import api.apps.android.nw.operaciones.pagoServicios.transaccionFallida.FailedTransactionPage;
import core.Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.AssertionFailedError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.text.SimpleDateFormat;
import java.util.*;


public class RGSPagoDeServicios {

    public Util util = Apps.util;
    //private static NubiWallet nubiWallet = Android.app.nubiWallet;
    private static final Logger logger = LogManager.getLogger();

    private Date date = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es","AR"));
    private SimpleDateFormat hourFormat;
    private SimpleDateFormat amOrPmFormat = new SimpleDateFormat("a");

    private String manualInvoiceCodeNumber;
    public String getManualInvoiceCodeNumber() { return manualInvoiceCodeNumber; }
    
    private String companyName;
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    private String amountToPay;
    public String getAmountToPay() { return amountToPay; }
    public void setAmountToPay(String amountToPay) { this.amountToPay = amountToPay; }

    private HashMap<String,String> codesForInvoiceTypes = new HashMap<>();

    public RGSPagoDeServicios(){
        codesForInvoiceTypes.put("montoFijo","80280020269200000000290720190000905014451670000005");
        codesForInvoiceTypes.put("montoAbierto","052100052546182001343878");
        codesForInvoiceTypes.put("multiplesFacturas","72000001885000018300220720202110012462524300005870");
    }

    @And("Valida los elementos de la pantalla principal de pago de servicios")
    public void assertServicesPaymentPageElements() throws InterruptedException {

        ServicesPaymentPage servicesPaymentPage = ServicesPaymentPage.getInstance();

        try {
            //Asserts
            Assert.assertEquals(ServicesPaymentPage.SUBTITLE_TEXT, servicesPaymentPage.get_element_text(servicesPaymentPage.getLblSubtitle()));
            Assert.assertEquals(ServicesPaymentPage.MESSAGE_TEXT, servicesPaymentPage.get_element_text(servicesPaymentPage.getMessage()));
            Assert.assertEquals(ServicesPaymentPage.SCAN_TEXT, servicesPaymentPage.get_element_text(servicesPaymentPage.getEscanearFactura()));
            Assert.assertEquals(ServicesPaymentPage.EMPRESAS_TEXT, servicesPaymentPage.get_element_text(servicesPaymentPage.getVerEmpresasHabilitadas()));
        }
        catch (AssertionFailedError e){
            logger.error(e.getCause());
            throw new AssertionFailedError("Uno o mas elementos en la pantalla Pago de Servicios (Primer Uso) no coinciden con el resultado esperado");
        }
    }

    @And("Valida los elementos de la pantalla primer uso de pago de servicios")
    public void assertPayBillsPageElements() throws InterruptedException {

        ServicesPaymentPage servicesPaymentPage = ServicesPaymentPage.getInstance();

        try {
            Assert.assertTrue(servicesPaymentPage.getBackOrCloseBtn().isDisplayed());
            Assert.assertTrue(servicesPaymentPage.elementExists(servicesPaymentPage.getEmoji()));
            Assert.assertEquals(servicesPaymentPage.SUBTITLE_TEXT, servicesPaymentPage.getLblSubtitle().getText());
            Assert.assertEquals(servicesPaymentPage.MESSAGE_TEXT, servicesPaymentPage.get_element_text(servicesPaymentPage.getMessage()));
            Assert.assertEquals(servicesPaymentPage.SCAN_TEXT, servicesPaymentPage.get_element_text(servicesPaymentPage.getEscanearFactura()));
            Assert.assertEquals(servicesPaymentPage.EMPRESAS_TEXT, servicesPaymentPage.get_element_text(servicesPaymentPage.getVerEmpresasHabilitadas()));
        }
        catch (AssertionFailedError e){
            logger.error(e.getCause());
            throw new AssertionFailedError("Uno o mas elementos en la pantalla Pago de Servicios (Primer Uso) no coinciden con el resultado esperado");
        }
    }

    @And("Valida los elementos de la pantalla empresas habilitadas de pago de servicios con {int} empresas")
    public void assertCompaniesAvailablePageElements(int rowsToCompare) throws Exception {

        try {
            CompaniesListPage companiesListPage = CompaniesListPage.getInstance();
            Assert.assertEquals(companiesListPage.MAIN_TITLE_TEXT, CompaniesListPage.getInstance().getAvailableCompaniesTitle());
            Assert.assertEquals(companiesListPage.MAIN_TITLE_TEXT, companiesListPage.get_element_text(companiesListPage.getLblAvailableCompaniesTitle()));
            Assert.assertTrue(companiesListPage.getBackOrCloseBtn().isDisplayed());
            Assert.assertTrue(companiesListPage.companiesOrderedAndDisplayed(10));
        }
        catch (AssertionError e){
            logger.error(e.getMessage());
            throw new AssertionError("Uno o mas elementos del listado de compañias no coinciden con los resultados esperados");
        }
    }

    @Then("Valida los elementos de la pantalla escanear codigo de factura")
    public void assertScanCodeBarPageElements() {

        BillsScanner billsScanner = BillsScanner.getInstance();

        try {
            Assert.assertEquals(billsScanner.MAIN_TITLE_TEXT, BillsScanner.getLblTitle().getText());
            Assert.assertTrue(billsScanner.getBackOrCloseBtn().isDisplayed());
            Assert.assertEquals(billsScanner.SUBTITLE_TEXT, billsScanner.getLblSubtitle().getText());
            Assert.assertEquals(billsScanner.MANUAL_INPUT_BUTTON_TEXT, billsScanner.get_element_text(billsScanner.getBtnManualInput()));
        }
        catch (AssertionError e){
            logger.error(e.getMessage());
            throw new AssertionError("Uno o mas elementos en la pantalla de escaneo de facturas no coinciden" +
                    "con los resultados esperados");
        }


    }

    @Then("Valida los elementos de la pantalla ingresar codigo manual {string}")
    public void assertTypeYourBillCodeScreenElements(String inputStatus) {

        ManualInputPage manualInputPage = ManualInputPage.getInstance();
        manualInputPage.hideKeyboardIfVisible();

        try{

            Assert.assertEquals(manualInputPage.MAIN_TITLE_TEXT, ManualInputPage.getLblTitle().getText());
            Assert.assertTrue(manualInputPage.getBackOrCloseBtn().isDisplayed());

            switch (inputStatus){
                case "sin_codigo_ingresado":
                    Assert.assertEquals(manualInputPage.PLACE_HOLDER_INPUT_TEXT, manualInputPage.get_element_text(manualInputPage.getTxtInvoiceNumberInput()));
                    Assert.assertFalse(manualInputPage.elementIsEnabled(manualInputPage.getBtnManualCodeInputButton()));
                    break;
                case "con_codigo_ingresado":
                    Assert.assertEquals(getManualInvoiceCodeNumber(), manualInputPage.get_element_text(manualInputPage.getTxtInvoiceNumberInput()));
                    Assert.assertTrue(manualInputPage.elementIsEnabled(manualInputPage.getBtnManualCodeInputButton()));
                    break;
            }
        }
        catch (AssertionError e){
            logger.error(e.getMessage());
            throw new AssertionError("Uno o mas elementos de la pantalla ingreso de codigo manual no coincide con los criterios de aceptacion");
        }
    }

    @And("Valida los elementos de la pantalla permisos de camara denegados")
    public void assertDeniedCameraAccesPageElements() throws Exception {

        PermissionsDeniedPage permissionsDeniedPage = PermissionsDeniedPage.getInstance();

        try {
            Assert.assertEquals(permissionsDeniedPage.MAIN_TITLE_TEXT, PermissionsDeniedPage.getLblTitle().getText());
            Assert.assertTrue(permissionsDeniedPage.getBackOrCloseBtn().isDisplayed());
            Assert.assertTrue(permissionsDeniedPage.elementExists(permissionsDeniedPage.getImgCamera()));
            Assert.assertEquals(permissionsDeniedPage.MESSAGE_TEXT, permissionsDeniedPage.get_element_text(permissionsDeniedPage.getLblMessage()));
            Assert.assertEquals(permissionsDeniedPage.ACTIVATE_PERMISSIONS_BUTTON_TEXT, permissionsDeniedPage.get_element_text(permissionsDeniedPage.getBtnActivatePermissions()));
            Assert.assertEquals(permissionsDeniedPage.MANUAL_INPUT_LINK_TEXT, permissionsDeniedPage.get_element_text(permissionsDeniedPage.getLnkManualInputButton()));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @When("Presiona sobre el boton escanear codigo de barras")
    public void tapOnScanCodeBarButton() throws Exception{
        try {
            ServicesPaymentPage.getInstance().clickScanCodeBarButton();
        }
        catch (Exception e){
            logger.error(e.getCause());
            throw  new Exception("El boton escanear codigo no pudo ser pulsado o hallado");
        }

    }

    @When("Presiona sobre el link ver empresas habilitadas")
    public void tapOnShowAvailableCompaniesLink() throws Exception{
        try{
            ServicesPaymentPage.getInstance().clickShowAvailableCompaniesLink();
        }
        catch (Exception e){
            logger.error(e.getCause());
            throw  new Exception("El Link Ver empresas habilitadas no pudo ser pulsado o hallado");
        }
    }

    @When("Presiona sobre el boton ingresar codigo manualmente en la pantalla {string}")
    public void tapOnTypeYourBillCodeButton(String screen) throws Exception{
        try {
            switch (screen){
                case "escaner_factura":
                    BillsScanner.getInstance().clickManualInputButton();
                    break;
                case "permisos_denegados":
                    PermissionsDeniedPage.getInstance().clickManualInputButton();
                    break;
            }
        }
        catch (Exception e){
            logger.error(e.getCause());
            throw  new Exception("El boton Ingresar codigo manualmente no pudo ser pulsado o hallado");
        }

    }

    @When("Ingresa manualmente el codigo de la factura de tipo {string} a pagar")
    public void typeInvoiceCodeNumber(String typeOfInvoice) throws Exception{

        try {

            switch (typeOfInvoice){
                case "random":
                    Random randomDigit = new Random();
                    manualInvoiceCodeNumber = String.valueOf(randomDigit.nextInt(1000000000)) + "error";
                    break;
                case "monto_fijo":
                    manualInvoiceCodeNumber = codesForInvoiceTypes.get("montoFijo");
                    break;
                case "monto_abierto":
                    manualInvoiceCodeNumber = codesForInvoiceTypes.get("montoAbierto");
                    break;
                case "multiples_facturas":
                    manualInvoiceCodeNumber = codesForInvoiceTypes.get("multiplesFacturas");
                    break;
            }

            ManualInputPage.getInstance().typeTextIntoInvoiceNumberInput(manualInvoiceCodeNumber);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new Exception("No se pudo ingresar los valores en el input codigo manual de factura");
        }
    }

    @And("Presiona sobre el boton continuar en pantalla ingresar codigo manualmente")
    public void tapOnContinueButtonInManualCodeScreen() throws Exception{

        Android.driver.hideKeyboard();
        try {
            ManualInputPage.getInstance().clickManualCodeInputButton();
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new Exception("No se puedo hacer click sobre el boton Continuar");
        }
    }

    @When("Presiona sobre el boton activar permisos de acceso a la camara")
    public void tapOnActiveAccessToCamera() throws Exception{
        try {
            PermissionsDeniedPage.getInstance().clickActivatePermissionsButton();
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new Exception("No se pudo pulsar el boton activar permisos");
        }
    }

    @And("Valida los elementos de la pantalla confirmacion de pago de servicio desde {string}")
    public void assertPaymentConfirmationScreenElements(String ancestorScreen) {

        try{
            Assert.assertEquals(PaymentConfirmationPage.getInstance().expectedMainTitleText, PaymentConfirmationPage.getLblTitle().getText());
            Assert.assertTrue(PaymentConfirmationPage.getInstance().getBackOrCloseBtn().isDisplayed());
            Assert.assertEquals(PaymentConfirmationPage.getInstance().expectedSubtitleText, PaymentConfirmationPage.getInstance().getLblSubtitle().getText());
            Assert.assertTrue(PaymentConfirmationPage.getInstance().isBillsImageDisplayed());
            Assert.assertEquals(PaymentConfirmationPage.getInstance().expectedConfirmationText, PaymentConfirmationPage.getInstance().getConfirmationMessageText());
            Assert.assertEquals(PaymentConfirmationPage.getInstance().expectedPayButtonText, PaymentConfirmationPage.getInstance().getPayButtonText());

            switch (ancestorScreen){
                case "monto_fijo":
                    Assert.assertFalse(PaymentConfirmationPage.getInstance().getCompanyNameText().isEmpty());
                    Assert.assertFalse(PaymentConfirmationPage.getInstance().getAmountText().isEmpty());
                    break;
                case "monto_abierto":
                case "multiples_facturas":
                    Assert.assertEquals(getCompanyName(), PaymentConfirmationPage.getInstance().getCompanyNameText());
                    Assert.assertEquals(getAmountToPay(), PaymentConfirmationPage.getInstance().getAmountText());
                    break;
            }
        }
        catch (AssertionError e){
            logger.error(e.getMessage());
            throw new AssertionError(e.getStackTrace());
        }
    }

    @And("Valida los elementos de la pantalla factura con monto abierto")
    public void assertOpenAmountScreenElements() throws Exception {
        try {
            OpenAmountPage openAmountPage = OpenAmountPage.getInstance();

            //Asserts

            Assert.assertEquals(OpenAmountPage.TITLE_TEXT, Wrapper.getElementText(OpenAmountPage.getLblTitle()));
            Assert.assertEquals(OpenAmountPage.SUBTITLE_TEXT, Wrapper.getElementText(openAmountPage.getOpenAmountMessage()));
            Assert.assertTrue(Wrapper.elementExists(openAmountPage.getOpenAmountMessageCompany()));
            Assert.assertTrue(Wrapper.elementExists(openAmountPage.getImage()));
            Assert.assertFalse(Wrapper.elementIsEnabled(openAmountPage.getNextButton()));

        }
        catch (Error | Exception e){
            logger.error(e);
            throw e;
        }

    }

    @And("Presiona el boton siguiente en la pantalla monto abierto")
    public void tapOnNextButton() throws Exception{

       OpenAmountPage openAmountPage = OpenAmountPage.getInstance();

        setCompanyName(openAmountPage.get_element_text(openAmountPage.getOpenAmountMessageCompany()));
        setAmountToPay(openAmountPage.get_element_attribute(openAmountPage.getAmountInput(),"text"));

        try {
            openAmountPage.hideKeyboardIfVisible();
            openAmountPage.click_next_button();
        }
        catch (Exception | Error e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @And("Selecciona una factura de {string} en la pantalla de multiples facturas")
    public void tapOnSpecificBill(String billType) throws Exception{
        try {

            MultipleInvoicesPage multipleInvoices = MultipleInvoicesPage.getInstance();

            switch (billType){
                case "monto_fijo":
                    setCompanyName(multipleInvoices.getTextFromOneInvoiceItem(0,"company"));
                    setAmountToPay(multipleInvoices.getTextFromOneInvoiceItem(0,"amount"));
                    multipleInvoices.tapOnSpecificBill(0);
                    break;
                case "hasta_quince_segundos":
                    //actualmente no hay factura que intencionalmente despliegue la pantalla de retraso de 15 segundos (17/06/2020)
                    break;
            }
            LastMovementsSteps.setTransctionDescription("Pagaste " + getCompanyName());
            LastMovementsSteps.setTransactionAmount("-" + getAmountToPay());
        }
        catch (Exception e){
           throw new Exception();
        }
    }

    @When("Presiona el boton pagar en la pantalla confirmacion de pago de servicio")
    public void tapOnPayButton() throws Exception{

        try {
            PaymentConfirmationPage.getInstance().clickPayButton();
        }
        catch (Exception e){
            throw new Exception("No se pudo presionar el boton pagar");
        }
    }


    @When("Ingresa un monto {string} al permitido")
    public void typeIntoAmountInput(String type) throws Exception{

        try {

            double newAmount = 0;
            OpenAmountPage openAmountPage = OpenAmountPage.getInstance();
            String defaultAmount = openAmountPage.get_element_attribute(openAmountPage.getAmountInput(),"text").replace(",",".");
            defaultAmount = defaultAmount.replace("$","");

            openAmountPage.clean_inpun_text_element(openAmountPage.getAmountInput());

            switch (type){
                case "mayor":
                    newAmount = (Double.parseDouble(defaultAmount) * 2) + 0.1;
                    break;
                case "menor":
                    newAmount = Double.parseDouble(defaultAmount) / 100;
                    break;
                case "menor(cero)":
                    newAmount = 0;
                    break;
            }

            //set amount
            openAmountPage.set_amount(String.valueOf(newAmount));
        }
        catch (Exception e){
            e.getStackTrace();
            throw new Exception();
        }
    }

    @Then("Se muestra advertencia de monto {string} y se deshabilita el boton siguiente")
    public void assertErrorElementsInOpenAmountScreen(String type) throws Exception{

        try {

            OpenAmountPage openAmountPage = OpenAmountPage.getInstance();

            switch (type){
                case "mayor":
                    Assert.assertEquals(openAmountPage.AMOUNT_HIGHER_TEXT,
                            openAmountPage.get_element_text(openAmountPage.getAmountOpenErrorMessage()));
                    break;
                case "menor(cero)":
                case "menor":
                    Assert.assertEquals(openAmountPage.AMOUNT_LOWER_TEXT,
                            openAmountPage.get_element_text(openAmountPage.getAmountOpenErrorMessage()));
                    break;
            }

            openAmountPage.hideKeyboardIfVisible();
            Assert.assertFalse(openAmountPage.elementIsEnabled(openAmountPage.getNextButton()));
        }
        catch (AssertionError e){
            throw new AssertionError(e.getCause());
        }
    }

    @Then("Valida los elementos de la pantalla error generico de pago de servicios")
    public void assertGenericErrorScreenElements(){

        GenericErrorPage genericError = GenericErrorPage.getInstance();

        try {

            Assert.assertEquals(genericError.MAIN_TITLE_TEXT, genericError.get_element_text(genericError.getLblMainTitle()));
            Assert.assertTrue(genericError.getBackOrCloseBtn().isDisplayed());
            Assert.assertTrue(genericError.elementExists(genericError.getErrorIcon()));
            Assert.assertEquals(genericError.TITLE_TEXT, GenericErrorPage.getLblTitle().getText());
            Assert.assertEquals(genericError.TRY_AGAIN_BUTTON_TEXT, genericError.get_element_text(genericError.getBtnTryAgain()));
            Assert.assertEquals(genericError.BACK_TO_HOME_LINK_TEXT, genericError.get_element_text(genericError.getLnkBackToHome()));
            Assert.assertTrue(genericError.getLblSubtitle().getText().contains(genericError.SUBTITLE_LINE1_TEXT));
            Assert.assertTrue(genericError.getLblSubtitle().getText().contains(genericError.SUBTITLE_LINE2_TEXT));
            Assert.assertTrue(genericError.getLblSubtitle().getText().contains(genericError.SUBTITLE_LINE3_TEXT));
            Assert.assertTrue(genericError.getLblSubtitle().getText().contains(genericError.SUBTITLE_LINE4_TEXT));
        }
        catch (AssertionError e){
            e.printStackTrace();
            throw new AssertionError();
        }
    }

    @And("Valida los elementos de la pantalla transaccion fallida")
    public void assertFailedTransactionElements() throws Exception {

        FailedTransactionPage failedTransactionPage = FailedTransactionPage.getInstance();
        try {
            Assert.assertTrue(failedTransactionPage.elementIsEnabled(FailedTransactionPage.getBackButton()));
            Assert.assertEquals(TextList.TEXT_TRANSACCION_FALLIDA.getText(), failedTransactionPage.getFailedTransactionTitleText());
            Assert.assertTrue(failedTransactionPage.elementExists(FailedTransactionPage.getErrorIcon()));
            Assert.assertEquals(TextList.TEXT_ALGO_SALIO_MAL.getText(), failedTransactionPage.get_element_text(FailedTransactionPage.getTitle()));
            Assert.assertEquals(TextList.TEXT_TUVIMOS_UN_PROBLEMA.getText(), failedTransactionPage.get_element_text(FailedTransactionPage.getSubtitle()));
            Assert.assertEquals(TextList.TEXT_BACK_TO_HOME.getText(), failedTransactionPage.get_element_text(FailedTransactionPage.getBackButton()));
            Assert.assertTrue(failedTransactionPage.elementIsEnabled(FailedTransactionPage.getBackButton()));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Uno o mas elementos en la pantalla transaccion fallida son diferentes o no existen");
        }
    }

    @Then("Valida los elementos de pago exitoso")
    public void assertSuccessPaymentElements() throws Exception{

        try {
            //Assert.assertTrue(nubiWallet.pagoServicios.pagoExitoso.existSuccessIcon());
            Assert.assertEquals(SuccessPayPage.getInstance().TEXT_LISTO,
                    BasePage.getLblTitle().getText());
            Assert.assertEquals(SuccessPayPage.getInstance().TEXT_CONFIRMACION_PAGO,
                    SuccessPayPage.getInstance().getSuccessPaymentMessageText());
            Assert.assertEquals(SuccessPayPage.getInstance().TEXT_DESCARGAR_COMPROBANTE,
                    SuccessPayPage.getInstance().getFinishButtonText());
        }
        catch (Exception|AssertionError e){
            e.printStackTrace();
            throw new AssertionError();
        }
    }

    @When("Presiona el boton Reintentar en pantalla error generico")
    public void tapOnTryAgainButton() throws Exception{

        try {
            GenericErrorPage.getInstance().clickTryAgainButton();
        }
        catch (Exception e){
            throw new Exception("No se pudo pulsar el boton reintentar");
        }
    }

    @When("Presiona sobre el link volver al inicio")
    public void tapOnGoToHomeButton() throws Exception{

        try {
            GenericErrorPage.getInstance().clickBackToHomeLink();
        }
        catch (Exception e){
            throw new Exception("No se pudo pulsar sobre el link 'volver al inicio'");
        }
    }

    @When("Presiona sobre el boton volver al inicio en la pantalla transaccion fallida")
    public void tapOnBackToHomeButton() throws Exception{

        try {
            FailedTransactionPage.getInstance().clickBackToDashboard();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @When("Presiona sobre el boton volver al inicio en la pantalla pago exitoso")
    public void tapOnFinishButton() throws Exception{

        try {
            SuccessPayPage.getInstance().clickOnGoHomeButton();
            LastMovementsSteps.setTransctionDate(dateFormat.format(date));
            //RGSDashboard.setTransctionHour(hourFormat.format(date));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * Metodo provisorio mientras se implementa la descarga del comprobante de pago
     */
    @Then("Se valida que el enlace descargar comprobante exista y este habilitado")
    public void isDownloadVoucherAvailable() {

        try {
            Assert.assertTrue(SuccessPayPage.getInstance().isBtnFinishEnabled());
        }
        catch (Exception e){
            e.printStackTrace();
            throw new AssertionError();
        }
    }

    @Then("Valida los elementos de la pantalla multiples facturas")
    public void assertMultipleInvoicesElements() throws Exception{

        try {

            MultipleInvoicesPage multipleInvoices = MultipleInvoicesPage.getInstance();

            Assert.assertEquals(multipleInvoices.MAIN_TITLE_TEXT, multipleInvoices.get_element_text(multipleInvoices.getLblSelectInvoiceTitle()));
            Assert.assertTrue(multipleInvoices.getBackOrCloseBtn().isDisplayed());
            Assert.assertTrue(multipleInvoices.elementExists(multipleInvoices.getImgInvoice()));
            Assert.assertEquals(multipleInvoices.INFORMATION_MESSAGE_TEXT, multipleInvoices.get_element_text(multipleInvoices.getLblInformationMessage()));
            Assert.assertTrue(multipleInvoices.billsExistence());
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @And("Valida los elementos de la pantalla demora de pago hasta {string}")
    public void assertDelayedPaymentElements(String delayedTime) throws AssertionError{

        try {
            Assert.assertTrue(DelayedPaymentPage.getInstance().isProcessingIconDisplayed());

            switch (delayedTime){
                case "5_segundos":
                    Assert.assertEquals(DelayedPaymentPage.getInstance().EXPECTED_PROCESSING_TEXT, DelayedPaymentPage.getInstance().getDelayedMessageTitleText());
                    break;
                case "10_segundos":
                    DelayedPaymentPage.getInstance().waitForDelayedMessageVisibility();
                    Assert.assertEquals(DelayedPaymentPage.getInstance().EXPECTED_PROCESSING_TEXT, DelayedPaymentPage.getInstance().getDelayedMessageTitleText());
                    Assert.assertEquals(DelayedPaymentPage.getInstance().EXPECTED_WAIT_FEW_SECONDS_TEXT, DelayedPaymentPage.getInstance().getDelayedMessageText());
                    break;
                case "15_segundos":
                    DelayedPaymentPage.getInstance().waitForDelayedMessageVisibility();
                    Assert.assertEquals(DelayedPaymentPage.getInstance().EXPECTED_PROCESSING_TEXT, DelayedPaymentPage.getInstance().getDelayedMessageTitleText());
                    Assert.assertEquals(DelayedPaymentPage.getInstance().EXPECTED_WAIT_A_LITTLE_BIT_TEXT, DelayedPaymentPage.getInstance().getDelayedMessageText());
                    break;
                case "20_segundos":
                    DelayedPaymentPage.getInstance().waitForDelayedMessageVisibility();
                    Assert.assertEquals(DelayedPaymentPage.getInstance().EXPECTED_PROCESSING_TEXT, DelayedPaymentPage.getInstance().getDelayedMessageTitleText());
                    Assert.assertEquals(DelayedPaymentPage.getInstance().EXPECTED_WE_DONT_GIVE_UP_TEXT, DelayedPaymentPage.getInstance().getDelayedMessageText());
                    break;
            }

        }
        catch (AssertionError e){
            e.printStackTrace();
        }
    }
    @Then("Valida los elementos de pantalla de saldo insuficiente")
    public void assertInsufficientBalanceElements() throws Exception{

        InsufficientBalance insufficientBalance = InsufficientBalance.getInstance();
        try {
            Assert.assertEquals(InsufficientBalance.TITLE_PAYMENT, insufficientBalance.get_element_text(insufficientBalance.getTitlePayment()));
            Assert.assertEquals(InsufficientBalance.TITLE, insufficientBalance.get_element_text(insufficientBalance.getTitle()));
            Assert.assertEquals(InsufficientBalance.SUBTITLE, insufficientBalance.get_element_text(insufficientBalance.getSubtitle()));
            Assert.assertEquals(InsufficientBalance.OWN_ACOUNT_TEXT, insufficientBalance.get_element_text(insufficientBalance.getOwnAccountText()));
            Assert.assertEquals(InsufficientBalance.OTHER_ACOUNT_TEXT, insufficientBalance.get_element_text(insufficientBalance.getOtheraccountText()));
            Assert.assertEquals(InsufficientBalance.RAPIPAGO_TEXT, insufficientBalance.get_element_text(insufficientBalance.getRapipagoText()));

        }
        catch (Exception|AssertionError e){
            e.printStackTrace();
            throw new AssertionError();
        }
    }

}

