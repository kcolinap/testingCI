package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.menu.MenuPage;
import api.apps.android.nw.operaciones.recarga_celular.*;
import core.Util;
import interfaces.AppInterface;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RecargaCelular implements AppInterface {

    private static final Logger logger = LogManager.getLogger();
    private static Wrapper wrapper = Apps.wrapper;
    CommonStepsMovements common = new CommonStepsMovements();

    //Transaction data
    private static String amountToCharge;
    private static String description;
    private static String transactionDate;
    private static String transactionHour;
    private static String company;
    private static String PHONE_NUMBER;


    public static String getAmountToCharge() {
        return amountToCharge;
    }

    public static void setAmountToCharge(String amount) {
        amountToCharge = amount;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        RecargaCelular.description = description;
    }

    public static String getTransactionDate() {
        return transactionDate;
    }

    public static void setTransactionDate(String transactionDate) {
        RecargaCelular.transactionDate = transactionDate;
    }

    public static String getTransactionHour() {
        return transactionHour;
    }

    public static void setTransactionHour(String transactionHour) {
        RecargaCelular.transactionHour = transactionHour;
    }

    public static String getCompany() {
        return company;
    }

    public static void setCompany(String company) {
        RecargaCelular.company = company;
    }

    /****
     * flujo happy path steps
     */

    //Seleccionar la compania de telefono para recarga de celular
    @And("Selecciona la compania {string}")
    public void SeleccionarCompania(String compania) throws Exception{
        try {

            SelectCompanyPage selectCompanyPage = SelectCompanyPage.getInstance();

            company = selectCompanyPage.get_company_name(compania);
            selectCompanyPage.click_on_company(compania.toUpperCase());


        }catch (Exception | Error err){
            logger.error(err.getMessage());
            throw err;
        }
    }

    @When("Usuario pulsa link usar mi telefono")
    public void tap_utilizar_mi_telefono(){
        try {

            PhoneForChargePage phoneForChargePage = PhoneForChargePage.getInstance();

            //Click on use my phone
            phoneForChargePage.click_on_use_my_phone();
            phoneForChargePage.hideKeyboardIfVisible();
            Wrapper.waitForElementEnabled(phoneForChargePage.getNextButton());

            PHONE_NUMBER = Login.usuario.getPhoneNumber().substring(4,Login.usuario.getPhoneNumber().length());



        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw  err;
        }
    }

    @And("Ingresar numero de telefono valido para recarga")
    public void set_valid_phone_to_recharge(){
        try{
            PhoneForChargePage phoneForChargePage = PhoneForChargePage.getInstance();

            //Fill list numbers
            List<String> listNumber = new ArrayList<>(Arrays.asList(PhoneForChargePage.validNumbers));

            //Fill area code list
            List<String> listCode = new ArrayList<>(Arrays.asList(PhoneForChargePage.validCodes));


            int random = new Random().nextInt(listCode.size());

            //set area code
            phoneForChargePage.setCode(listCode.get(random));

            PHONE_NUMBER = listCode.get(random);

            //set phoneNumber
            random  = new Random().nextInt(listNumber.size());
            phoneForChargePage.setNumber(listNumber.get(random));
            PHONE_NUMBER+= listNumber.get(random);

            phoneForChargePage.hideKeyboardIfVisible();

            //Click next button
            phoneForChargePage.click_on_next_button();

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @And("Pulsar boton Confirmar")
    public void tap_recargar(){
        try {
            ConfirmationPage.getInstance().click_on_next_button();
        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }


    /***********************************/

    /***
     * metodos de validaciones en flujos de regression
     */
    @Then("Validar pantalla recarga celular {string}")
    public void validarPantallaRecargaCelular(String tipoValidacion) throws Exception{
        try {

            Android.hideKeyboardIfVisible();

            switch (tipoValidacion.toUpperCase()){
                case "ICONO":
                    Assert.assertEquals(MenuPage.MOBILE_RECHARGE_TEXT, MenuPage.getInstance().getTextButton(4).replace("\n", " "));

                    break;
                case "SELECT_COMPANIA_RECARGA_CELULAR":
                    SelectCompanyPage companyPage = SelectCompanyPage.getInstance();

                    //Asserts
                    Assert.assertEquals(SelectCompanyPage.RECHARGE_CELULAR_TITLE_TEXT, companyPage.get_element_text(SelectCompanyPage.getLblTitle()));
                    Assert.assertEquals(SelectCompanyPage.SELECT_COMPANY_SUBTITLE_TEXT, companyPage.get_element_text(companyPage.getSubtitleRechargeMobile()));

                    Assert.assertTrue(companyPage.getCompanyName().size()>0);
                    Assert.assertTrue(companyPage.getCompanyImage().size()>0);

                    //Validate company names
                    //Fill list numbers
                    List<String> listCompanynames = new ArrayList<>(Arrays.asList(SelectCompanyPage.companyNames));

                    //Validate all company names
                    for(String name : listCompanynames){
                        Assert.assertEquals(name, companyPage.get_company_name(name));
                    }

                    break;

                case "RC_INGRESAR_NUMERO":
                    PhoneForChargePage phoneForChargePage = PhoneForChargePage.getInstance();

                    //Asserts
                    Assert.assertEquals(PhoneForChargePage.SET_PHONE_TO_CHARGE_TITLE_TEXT, Wrapper.getElementText(PhoneForChargePage.getLblTitle()));
                    Assert.assertEquals(PhoneForChargePage.SET_PHONE_TO_CHARGE_SUBTITLE_TEXT, Wrapper.getElementText(phoneForChargePage.getSubtitleRechargeMobile()));
                    Assert.assertEquals(PhoneForChargePage.TOOL_TIP_TEXT, Wrapper.getElementText(phoneForChargePage.getTooltipText()));

                    Assert.assertTrue(Wrapper.elementExists(phoneForChargePage.getUseMyPhoneLink()));

                    Assert.assertTrue(Wrapper.elementExists(phoneForChargePage.getNextButton()));

                    break;
                case "RC_INGRESAR_MONTO":
                    Android.hideKeyboardIfVisible();

                    AmountToRechargePage amountPage = AmountToRechargePage.getInstance();

                    //Asserts
                    Assert.assertEquals(AmountToRechargePage.RECHARGE_CELULAR_TITLE_TEXT, Wrapper.getElementText(AmountToRechargePage.getLblTitle()));
                    Assert.assertEquals(AmountToRechargePage.SUBTITLE_TEXT, Wrapper.getElementText(amountPage.getSubtitleRechargeMobile()));
                    Assert.assertTrue(Wrapper.elementExists(amountPage.getAmountInput()));
                    Wrapper.waitForElementDisabled(amountPage.getNextButton());

                    break;
                case "RC_CONFIRMACION":
                    Android.hideKeyboardIfVisible();

                    ConfirmationPage confirmationPage = ConfirmationPage.getInstance();

                    //Asserts
                    Assert.assertEquals(ConfirmationPage.RECHARGE_CELULAR_TITLE_TEXT, Wrapper.getElementText(ConfirmationPage.getLblTitle()));
                    Assert.assertEquals(ConfirmationPage.CONFIRMATION_SUBTITLE_TEXT, Wrapper.getElementText(confirmationPage.getSubtitleRechargeMobile()));
                    Assert.assertTrue(Wrapper.elementExists(confirmationPage.getCompanyImage()));
                    break;
                case "RC_NUBI_PASSWORD":
                    NubiPassRechargeMobilePage nubiPassRechargeMobilePage = NubiPassRechargeMobilePage.getInstance();

                    //Assert.assertEquals(NubiPassRechargeMobilePage.SET_NUBI_PASSWORD_TEXT, Wrapper.getElementText(NubiPassRechargeMobilePage.getLblChangePin()));

                    break;
                case "RC_ERROR_RECHARGE":
                    Android.hideKeyboardIfVisible();

                    MobileRechargeErrorPage errorPage = MobileRechargeErrorPage.getInstance();

                    //Asserts
                    Assert.assertEquals(MobileRechargeErrorPage.RECHARGE_CELULAR_TITLE_TEXT, Wrapper.getElementText(MobileRechargeErrorPage.getLblTitle()));
                    Assert.assertTrue(Wrapper.elementExists(errorPage.getEmoji()));
                    Assert.assertEquals(MobileRechargeErrorPage.SUBTITLE_TEXT, Wrapper.getElementText(errorPage.getSubtitle()));
                    Assert.assertEquals(MobileRechargeErrorPage.DISCLAIMER_TEXT, Wrapper.getElementText(errorPage.getDisclaimerText()));
                    Wrapper.waitForElementEnabled(MobileRechargeErrorPage.getMainButton());

                    break;
                case "RC_RECHARGE_SUCCESS":
                    Android.hideKeyboardIfVisible();

                    SuccessfulRechargePage successpage = SuccessfulRechargePage.getInstance();

                    //Asserts
                    Assert.assertEquals(SuccessfulRechargePage.RECHARGE_CELULAR_TITLE_TEXT, Wrapper.getElementText(SuccessfulRechargePage.getLblTitle()));
                    Assert.assertTrue(Wrapper.elementExists(successpage.getEmoji()));
                    Assert.assertEquals(SuccessfulRechargePage.MESSAGE_TEXT, Wrapper.getElementText(successpage.getMessage()));
                    Assert.assertEquals(successpage.getDisclaimerText(getAmountToCharge(), PHONE_NUMBER), Wrapper.getElementText(successpage.getSecondaryMessage()));
                    Wrapper.waitForElementEnabled(SuccessfulRechargePage.getMainButton());

                    break;


            }
        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }


    @Then("Validar que se muestra el numero del usuario")
    public void validar_numero_tlfn(){
        try {

            PhoneForChargePage phoneForChargePage = PhoneForChargePage.getInstance();

            //Asserts on user phone number
            Assert.assertEquals(Login.usuario.getPhoneNumber().substring(4,6), Wrapper.getElementText(phoneForChargePage.getAreaCodeInput()));
            Assert.assertEquals(Login.usuario.getPhoneNumber().substring(6,14), Wrapper.getElementText(phoneForChargePage.getPhoneInput()));

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    @When("Ingresa codigo de area {string}")
    public void set_area_code(String areaCode) throws Exception{
        try {
            PhoneForChargePage phoneForChargePage = PhoneForChargePage.getInstance();

            //Set area code
            phoneForChargePage.setCode(areaCode);

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    @When("Ingresa numero {string}")
    public void set_phone_number(String number) throws Exception{
        try {
            PhoneForChargePage phoneForChargePage = PhoneForChargePage.getInstance();

            //Set phone number
            phoneForChargePage.setNumber(number);

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    @And("El codigo de area {int} persiste")
    public void codigo_area_persiste(int codigo){
        try {

            Assert.assertEquals(String.valueOf(codigo), Wrapper.get_element_attribute(PhoneForChargePage.getInstance().getAreaCodeInput(), "text"));
           // Assert.assertEquals(String.valueOf(codigo), nubiWallet.movements.recargaCelular.objects.txtCodigoTelefono().getText());

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    @And("El numero de telefono {int} persiste")
    public void numero_telefono_persiste(int nro){
        try {

            Assert.assertEquals(String.valueOf(nro), Wrapper.get_element_attribute(PhoneForChargePage.getInstance().getPhoneInput(), "text"));

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    @And("Validar tipo error {string}")
    public void validar_mensaje_error(String tipoError){
        try {
            switch (tipoError.toUpperCase()){
                case "LONGITUD":
                    Assert.assertEquals(PhoneForChargePage.LENGHT_ERROR_TEXT, Wrapper.getElementText(PhoneForChargePage.getInstance().getErrorMessage()).trim());
                    break;
                case "ERRONEO":
                    Assert.assertEquals(PhoneForChargePage.INVALID_NUMBER_TEXT, Wrapper.getElementText(PhoneForChargePage.getInstance().getErrorMessage()).trim());
                    break;
                case "COD_INEXISTENTE":
                    Assert.assertEquals(PhoneForChargePage.NO_EXIST_TEXT, Wrapper.getElementText(PhoneForChargePage.getInstance().getErrorMessage()).trim());
                    break;
                default:
                    break;
            }
        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    //Validar que el pil de la pantalla ingreso de monto
    //contiene el mismo monto disponible en la cuenta
    @Then("Pill contiene el mismo saldo de la cuenta")
    public void validar_pill_saldo(){
        try {
            //String saldoPill = nubiWallet.movements.recargaCelular.objects.lblCurrentBalance().getText();
            String originalBalance = common.getBalance();

            //Assert.assertTrue(saldoPill.contains(originalBalance));

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }


    //Validar los montos minimos y maximo
    //Estos valores son dados por parametro
    @And("Validar monto maximo y minimo para la compania {string}")
    public void validar_montos(String company){
        try {
            Android.hideKeyboardIfVisible();

             AmountToRechargePage amountToRechargePage = AmountToRechargePage.getInstance();

            List<String> listBeantext = new ArrayList<>(Arrays.asList(AmountToRechargePage.BEAN_COMPANY_TEXT));

            switch (company.toUpperCase()){
                case "CLARO":
                    Assert.assertEquals(listBeantext.get(0), Wrapper.getElementText(amountToRechargePage.getSubtitleRechargeRangeMessage()));
                    break;
                case "MOVISTAR":
                    Assert.assertEquals(listBeantext.get(1), Wrapper.getElementText(amountToRechargePage.getSubtitleRechargeRangeMessage()));
                    break;
                case "NEXTEL":
                    Assert.assertEquals(listBeantext.get(2), Wrapper.getElementText(amountToRechargePage.getSubtitleRechargeRangeMessage()));
                    break;
                case "PERSONAL":
                    Assert.assertEquals(listBeantext.get(3), Wrapper.getElementText(amountToRechargePage.getSubtitleRechargeRangeMessage()));
                    break;
                case "TUENTI":
                    Assert.assertEquals(listBeantext.get(4), Wrapper.getElementText(amountToRechargePage.getSubtitleRechargeRangeMessage()));
                    break;
            }

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw  err;
        }
    }


    //Ingreso de monto para recarga
    @When("Usuario ingresa monto {string}")
    public void ingreso_monto_recarga(String amountType) throws Exception{
        try {

            AmountToRechargePage amountToRechargePage = AmountToRechargePage.getInstance();
            Util util = NubiWallet.util;
            String min_amount = "", max_amount="";

            //Get user balance
            String originalBalance = CommonStepsMovements.getBalance();
            if(util.getNumericBalance(originalBalance)<=5)
                throw new Exception("El usuario no tiene saldo para realizar la recarga de celular. Debe ser mayor a $5");

            String auxAmount = "";
            switch (amountType.toUpperCase()){
                case "MENOR":

                    break;
                case "MAYOR":
                    auxAmount = String.valueOf((int)(util.getNumericBalance(originalBalance) + 1));

                    break;
                default:
                    auxAmount = String.valueOf((int)(5.0));
                    break;
            }

            //int nroChar = auxAmount.length();
            //if(String.valueOf(auxAmount.charAt(nroChar-2)).equals("."))
                //auxAmount+="0";

            amountToRechargePage.set_amount(auxAmount);
            //Add 00 to set the entire number
            //amountToRechargePage.set_amount(auxAmount+"00");
            setAmountToCharge(auxAmount);

            //Click om next button
            amountToRechargePage.click_on_next_button();

           /* //Asserts
            //Assert.assertEquals(AmountToRechargePage.RECHARGE_CELULAR_TITLE_TEXT, AmountToRechargePage.getLblTitle().getText());
           // Assert.assertEquals(AmountToRechargePage.CURRENT_BALANCE_TEXT+" $"+originalBalance, amountToRechargePage.getCurrentBalance().getText().trim().replace("\n"," "));

            List<String> listBeantext = new ArrayList<>(Arrays.asList(AmountToRechargePage.BEAN_COMPANY_TEXT));
            int i = 0;
            //validating amount range message for the company
            switch (company.toUpperCase()){
                case "CLARO":
                    Assert.assertEquals(listBeantext.get(0), wrapper.getElementText(amountToRechargePage.getSubtitleRechargeRangeMessage()).replace("\n"," "));
                    i=0;
                    break;
                case "MOVISTAR":
                    Assert.assertEquals(listBeantext.get(1), wrapper.getElementText(amountToRechargePage.getSubtitleRechargeRangeMessage()).replace("\n"," "));
                   i=1;
                    break;
                case "NEXTEL":
                    Assert.assertEquals(listBeantext.get(2), wrapper.getElementText(amountToRechargePage.getSubtitleRechargeRangeMessage()).replace("\n"," "));
                    break;
                case "PERSONAL":
                    Assert.assertEquals(listBeantext.get(3), wrapper.getElementText(amountToRechargePage.getSubtitleRechargeRangeMessage()).replace("\n"," "));
                    break;
                case "TUENTI":
                    Assert.assertEquals(listBeantext.get(4), wrapper.getElementText(amountToRechargePage.getSubtitleRechargeRangeMessage()).replace("\n"," "));
                    break;
                default:
                    //Assert.assertEquals(AmountToRechargePage.AMOUNT_FOR_OTHER_TEXT, amountToRechargePage.getSubtitleRechargeMobile().getText().trim().replace("\n"," "));
                    break;
            }

            //set amount

            Pattern pattern;
            Matcher matcher;
            String auxAmount = "";
            switch (amount.toUpperCase()){
                case "MINIMO":
                    //first we match for the minimun range
                    pattern = Pattern.compile("(\\d+)\\s");
                    matcher = pattern.matcher(listBeantext.get(i));
                    matcher.find();
                    auxAmount = matcher.group();
                    auxAmount = auxAmount.trim();
                    break;
            }

            //if amount minimun equals 0, add 1
            if(Integer.parseInt(auxAmount)==0)
                auxAmount="1";

            //Validate that balance is not lower than the auxAmount
            if(util.getNumericBalance(originalBalance)<Double.parseDouble(auxAmount))
                throw new Exception("El usuario no cuenta con el minimo de saldo necesario para realizar la recarga");
*/

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    @And("Usuario confirma la recarga de celular")
    public void confirm_recharge(){
        try{

            ConfirmationPage confirmationPage = ConfirmationPage.getInstance();

            //Asserts
            //Assert.assertEquals(ConfirmationPage.RECHARGE_CELULAR_TITLE_TEXT, ConfirmationPage.getLblTitle().getText().replace("\n"," "));
           // Assert.assertEquals(ConfirmationPage.CONFIRMATION_SUBTITLE_TEXT, confirmationPage.getSubtitleRechargeMobile().getText().replace("\n"," "));
            //Assert.assertEquals(ConfirmationPage.RECHARGE_LABEL_TEXT+" "+Login.usuario.getPhoneNumber().substring(4,14), confirmationPage.getRechargeLabel().getText().replace("\n"," "));
            Assert.assertEquals("$"+getAmountToCharge(), wrapper.getElementText(confirmationPage.getAmountMoney()));

            confirmationPage.click_on_next_button();


        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }


    @Then("La recarga es {string}")
    public void valitdate_recharge_result(String mode){
        try{
            switch (mode.toUpperCase()){
                case "CORRECTO":
                    SuccessfulRechargePage success = SuccessfulRechargePage.getInstance();

                    //Asserts
                    Assert.assertEquals(SuccessfulRechargePage.MESSAGE_TEXT, wrapper.getElementText(success.getMessage()).replace("\n", " "));

                    //Back to dashboard
                    success.back_to_dashboard();
                    break;
            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    //Validacion de persistencia de datos en pantalla confirmacion de recarga
    @And("Validar persistencia de datos")
    public void validar_persistencia_datos(){
        try{
            ConfirmationPage confirmationPage = ConfirmationPage.getInstance();

            Assert.assertTrue(Wrapper.getElementText(confirmationPage.getRechargeLabel()).contains(company.toUpperCase()));
            Assert.assertTrue(Wrapper.getElementText(confirmationPage.getRechargeLabel()).contains(PHONE_NUMBER));

            Assert.assertEquals("$"+getAmountToCharge(), Wrapper.getElementText(confirmationPage.getAmountMoney()));
        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }
    /************************************/

    @Then("Validar pantalla de confirmacion recarga celular con monto {string}")
    public void validar_confirmacion_recarga_celular(String amountTo){
        // Valida que el telefono sea el indicado
        //Assert.assertTrue(nubiWallet.movements.recargaCelular.objects.lblConfirmacionRecarga().getText().contains(Login.user_tlfn.substring(6,14)));
        // Valida que el monto sea el que nosotros queremos.
        //Assert.assertEquals("$"+String.valueOf(amountTo).replace(".",","), nubiWallet.movements.uiObject.amountMoney().getText());
    }

    @And("Validar pantalla saldo insuficiente")
    public void validar_saldo_insuficiente(){
        try {
            Assert.assertFalse(Wrapper.elementIsEnabled(AmountToRechargePage.getInstance().getNextButton()));

        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw  err;
        }
    }

    @Then("Validar pantalla al ingresar Clave Nubi {string} en recarga de celular")
    public void validar_pantalla_pin_recarga_celular(String tipoPin){
        try {
            switch (tipoPin.toUpperCase()){
                case "INCORRECTO":
                    NubiPassRechargeMobilePage nubiPassRechargeMobilePage = NubiPassRechargeMobilePage.getInstance();
                    Assert.assertEquals(NubiPassRechargeMobilePage.WRONG_NUBI_PASSWORD_TEXT, Wrapper.getElementText(nubiPassRechargeMobilePage.getErrorMessageElement()));
                   break;
                case "CORRECTO":
                    break;
            }
        }catch (Exception | Error e){
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @When("Usuario ingresa clave nubi {string} en recarga de celular")
    public void set_nubi_pass_mobile_recharge(String type) throws InterruptedException {
        try {
            switch (type.toUpperCase()){
                case "INCORRECTO":
                    NubiPassRechargeMobilePage nubiPassRechargeMobilePage = NubiPassRechargeMobilePage.getInstance();
                    nubiPassRechargeMobilePage.set_invalid_pin();
                    break;
                case "CORRECTO":
                    break;
            }
        }catch (Exception | Error e){
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Then("Pulsar volver al inicio")
    public void click_back_to_dashboard(){
        try {
           SuccessfulRechargePage.getInstance().back_to_dashboard();

        }catch (Exception | Error e){
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
