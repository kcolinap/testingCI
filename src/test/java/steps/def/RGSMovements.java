package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.contactos.detallecontactos.ContactsDetailPage;
import api.apps.android.nw.model.Account;
import api.apps.android.nw.model.transactions.Transaction;
import api.apps.android.nw.operaciones.cashin.CashInLandingPage;
import api.apps.android.nw.operaciones.cashin.metodos.OwnAccountPage;
import api.apps.android.nw.operaciones.cashin.metodos.RapiPagoPage;
import api.apps.android.nw.operaciones.p2p.SetAmountPage;
import core.Timer;
import core.Util;
import core.commons.DBQuery;
import core.commons.apicall.P2P_API;
import core.commons.apicall.TRANSACTIONS_API;
import core.commons.apicall.dtos.p2p.P2PRequest;
import interfaces.MovementsInter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class RGSMovements {

    private CommonStepsMovements commonStepsMovements = new CommonStepsMovements();
    public static Double balance, moneyToSendRequest;
    private static final Logger logger = LogManager.getLogger();
    private Timer timer = new Timer();
    private static String senderUser;
    private static String receptorUser;
    private static Util util = Apps.util;
    private static Properties prop = new Properties();

    public static String getSenderUser() { return senderUser; }

    public void setSenderUser(String senderUser) {
        RGSMovements.senderUser = senderUser;
    }

    public static String getReceptorUser() {
        return receptorUser;
    }

    public void setReceptorUser(String receptorUser) { RGSMovements.receptorUser = receptorUser; }

    @Test
    @Given("Usuario conoce su balance")
    public void user_knows_balance(){
        try {

            balance = commonStepsMovements.getNumericBalance();

        }catch (Exception e){
            util.showTrackTraceError("Unable to get user balance", e);
        }
    }

    @Then("{string} ingresa un monto {string} que el disponible en su cuenta")
    public void setAmount(String user, String value) throws Exception{
        try {

            if(user.contains("1")) {
                prop.load(util.loadTxtFile("transactions"));
                balance = Double.parseDouble(prop.getProperty("BalanceUser1"));
            }

            if(value.toLowerCase().contentEquals("mayor")){
                moneyToSendRequest=balance+1.01;
                //commonStepsMovements.setAmount("user1", String.valueOf(moneyToSendRequest));
            }else if(value.toLowerCase().contentEquals("menor")){
                if(balance>50)
                    moneyToSendRequest=10.01;
                else
                    moneyToSendRequest = balance - 1.01;
                //commonStepsMovements.setAmount("user1", String.valueOf(moneyToSendRequest));
            }

            if (moneyToSendRequest == null) {
                //util.getUserBalance
                moneyToSendRequest = 1.01;
            }

            //nubiWallet.movements.setMonto(moneyToSendRequest);
            LastMovementsSteps.setTransactionAmount("-$"+ String.valueOf(moneyToSendRequest).replace('.',','));

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("No se puedo ingresar el monto en el campo deseado");
        }
    }

    @Then("Campo monto vuelve a su estado por defecto despues de borrar el monto")
    public void clear_amount_field(){
        try {
            //nubiWallet.movements.uiObject.amountInput().clearText();
            Android.driver.hideKeyboard();

            //nubiWallet.movements.uiObject.btnContinuar().waitToAppear(10);

           // Assert.assertEquals("$0,00", nubiWallet.movements.uiObject.amountInput().getText());

        }catch (Exception e){
            util.showTrackTraceError("Error erasing amount field", e);
        }
    }

    @Then("Usuario {string} el dispositivo movil")
    public void block_unblock_device(String mode){
        try {
            if(mode.toLowerCase().contentEquals("bloquea")){
                Android.driver.lockDevice();

            }else if(mode.toLowerCase().contentEquals("desbloquea")){
                Android.driver.unlockDevice();
                slideScreen();
            }

        }catch (Exception e){
            util.showTrackTraceError("Error erasing amount field", e);
        }
    }

    public void slideScreen(){
        try {

            PointOption pointOption = new PointOption();
            PointOption pointOptionEnd = new PointOption();
            pointOptionEnd.withCoordinates(200, 200);

            pointOption.withCoordinates(1000, 1000);
            TouchAction actions = new TouchAction(Android.driver);

            actions.press(pointOption).moveTo(pointOptionEnd).release().perform();
            Thread.sleep(400);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @And("Confirmar contacto {string}")
    public void confirm_contact(String contactType){
        try{
            ContactsDetailPage contactsDetailPage = ContactsDetailPage.getInstance();

            //Asserts
            Assert.assertEquals(ContactsDetailPage.DETAIL_CONTACT_TITLE_TEXT, Wrapper.getElementText(ContactsDetailPage.getLblTitle()));

            switch (contactType.toUpperCase()){
                case "NUBI":
                    String fullName = CommonStepsMovements.destinationUser.getName() + " " + CommonStepsMovements.destinationUser.getLastName();
                    LastMovementsSteps.setTransctionDescription("Enviaste a " + fullName);

                    //Asserts
                    List<MobileElement> contacUsernameList = contactsDetailPage.getUserName();
                    boolean finded = false;

                    while (!finded){
                        for(MobileElement contactusername : contacUsernameList){
                            if(Wrapper.getElementText(contactusername).equals(CommonStepsMovements.destinationUser.getUsername())){
                                finded = true;
                                contactsDetailPage.confirmContact(contactusername);
                                break;
                            }
                        }
                        Apps.util.scrollTo();
                    }
                    break;
            }
        }catch (Exception | Error err){
            throw err;
        }
    }

    @And("Ingresar monto {string} para {string}")
    public void set_amount(String amountType, String transactionType){
        try{
            SetAmountPage setAmountPage = SetAmountPage.getInstance();

            //Asserts
            Assert.assertEquals(SetAmountPage.TITLE_TEXT, SetAmountPage.getLblTitle().getText());

            //Validate origin user balance before tarnsaction

            String auxBalance = Wrapper.getElementText(setAmountPage.getCurrentBalance());
            auxBalance = auxBalance.substring(3, auxBalance.length());
            auxBalance = auxBalance.replaceAll("[a-zA-Z.,$é ]", "");
            Assert.assertEquals(CommonStepsMovements.ORIGIN_USER_BALANCE_STRING.replaceAll(",", ""), auxBalance);


            switch (transactionType.toUpperCase()){
                case "SEND":
                    Assert.assertTrue(Wrapper.elementIsChecked(setAmountPage.getLeftOption()));
                    Assert.assertFalse(Wrapper.elementIsChecked(setAmountPage.getRightOption()));
                    break;
                case "REQUEST_PENDING":
                    Assert.assertFalse(Wrapper.elementIsChecked(setAmountPage.getLeftOption()));
                    Assert.assertTrue(Wrapper.elementIsChecked(setAmountPage.getRightOption()));
                    break;
            }

            double auxAmount;
            String amount="";
            if(amountType.toUpperCase().contentEquals("<")) {
                auxAmount = CommonStepsMovements.getNumBalance(CommonStepsMovements.getBalance());
                auxAmount = (auxAmount * 1) / 100;
                auxAmount = Math.round(auxAmount);
                amount = String.valueOf(auxAmount).replace(".","");
            }

            setAmountPage.set_amount(amount);
            CommonStepsMovements.amountToString = setAmountPage.get_element_text(setAmountPage.getAmountInput());
            CommonStepsMovements.amountToNumeric = Double.parseDouble(CommonStepsMovements.amountToString.replace(",","."));

            //Set transaction amount
            LastMovementsSteps.setTransactionAmount(CommonStepsMovements.amountToString);

            //Click continue button
            setAmountPage.click_continue_button();

        }catch (Exception | Error e){
            throw e;
        }
    }

    /**
     * Nos permite seleccionar el tipo de cuenta a la que deseamos enviar o solicitar dinero
     * @param accountType puede ser nubi o bancaria
     */
    @And("Presiona sobre la opcion de cuenta {string}")
    public void chooseASpecificAccountTypeToSendOrAskMoney(String accountType) throws Exception {
        try {
            String beneficiary = ContactsDetailPage.getInstance().getTextFromSpecificElementInAccountCard(accountType,"nombre_de_propietario");
            LastMovementsSteps.setTransctionDescription("Enviaste dinero a " + beneficiary);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new Exception("No se pudo pulsar sobre el tipo de cuenta");
        }
    }

    @When("Usuario presiona background desde el dispositivo")
    public void pressBackgroundFromDevice() throws Exception {
        try {
            CommonSteps commonSteps = new CommonSteps();
            commonSteps.press_recent_app_button();
        }
        catch (Exception e){
            logger.error(e.getStackTrace());
            throw new Exception();
        }
    }

    @And("el monto ingresado persiste")
    public void assertAmountInSendOrRequestScreen() {
        try {
            String amount = "$" + String.valueOf(moneyToSendRequest).replace('.',',');
           // Assert.assertEquals(amount,nubiWallet.movements.uiObject.amountInput().getText());
        }
        catch (AssertionError e){
            logger.error(e.getCause());
            throw new AssertionError("El monto luego de hacer background es distinto");
        }

    }

    @And("Pulsa sobre el boton finalizar")
    public void tapOnFinishButton() throws Exception {
        try {
            LastMovementsSteps.setTransctionDate(timer.setActualDateForNubi());
            LastMovementsSteps.setTransctionHour(timer.setActualHourForNubi());
            //nubiWallet.movements.uiObject.finishButton().waitToAppear(5);
            //nubiWallet.movements.uiObject.finishButton().tap();
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new Exception("No se puedo pulsar sobre el boton finalizar");
        }
    }

    @And("Se ejecuta una solicitud p2p para expirar")
    public void forceRequestExpiration() throws Exception {

        P2P_API p2pRequest = new P2P_API();
        DBQuery dbQuery = new DBQuery();
        TRANSACTIONS_API transactionsApi = new TRANSACTIONS_API();

        String originAccount = Login.usuario.getAcounts().get(0).getNumber();
        String destinationUserID = Login.usuario.getNubiContacts().get(0).getWalletData().get(0).getUserId();

        P2PRequest p2pResponse = p2pRequest.createANewMoneyRequest(null, null, null,"ARS");
        dbQuery.updateExpirationDateForP2PRequest(Timer.setActualDateForExpiration(), p2pResponse.getTransaction_id());
        dbQuery.updateStatusForP2PRequest("EXPIRED", p2pResponse.getTransaction_id());
        transactionsApi.changeTransactionsOutdatedToExpired();

        setSenderUser(Login.usuario.getUsername());
        setReceptorUser(Login.usuario.getNubiContacts().get(0).getWalletData().get(0).getUsername());
    }

    @And("Receptor {string} la solicitud p2p")
    public void acceptOrCancelAP2PRequest(String decision) throws Exception {
        try{
            String title = "SOLICITUD DE " + Login.usuario.getName() + " " + Login.usuario.getLastName();
           // nubiWallet.solicitudes.acceptOrCancelARequest(decision, title.toUpperCase(),"$0,01");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }


    @Then("Valida que la solicitud aparezca caducada")
    public void assertExpiredRequest() throws Exception{
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd 'de' MMMM", new Locale("es","AR"));
            String expirationDate = dateFormat.format(LocalDateTime.now());
            String title = "SOLICITUD A " + Login.usuario.getNubiContacts().get(0).getName();

           // while (!nubiWallet.solicitudes.uiObject.lblHistorial().exists()) util.scrollTo();
            for(int i = 0; i<=1; i++) util.scrollTo();

           // Assert.assertTrue(nubiWallet.solicitudes.existExpiredSentRequest(title.toUpperCase(),"$0,01", expirationDate.toUpperCase()));

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * Validar los elemtnos de una pantalla determinada dentro del flujo de cashIn
     * @param screen
     */
    @Then("Validar pantalla flujo cash-in {string}")
    public void validate_screen(String screen) throws Exception{
        try {
            switch (screen.toUpperCase()){
                case "LANDING":
                    CashInLandingPage landingPage = CashInLandingPage.getInstance();

                    //Asserts
                    Assert.assertEquals(CashInLandingPage.TITLE_TEXT, Wrapper.getElementText(landingPage.getSelectCashInSourceTitle()));
                    Assert.assertEquals(CashInLandingPage.SUBTITLE_TEXT, Wrapper.getElementText(landingPage.getCashInSelectTitle()));

                    Assert.assertTrue(
                            Wrapper.elementExists(CashInLandingPage.getIcons().get(0)) &&
                                    Wrapper.elementExists(CashInLandingPage.getIcons().get(1)) &&
                                    Wrapper.elementExists(CashInLandingPage.getIcons().get(2))
                    );

                    Assert.assertEquals(CashInLandingPage.OWN_ACOUNT_TEXT, Wrapper.getElementText(CashInLandingPage.getTexts().get(0)));
                    Assert.assertEquals(CashInLandingPage.OTHER_ACOUNT_TEXT, Wrapper.getElementText(CashInLandingPage.getTexts().get(1)));
                    Assert.assertEquals(CashInLandingPage.RAPIPAGO_TEXT, Wrapper.getElementText(CashInLandingPage.getTexts().get(2)));

                    break;
                case "OWN_ACCOUNT":
                    OwnAccountPage ownAccountPage = OwnAccountPage.getInstance();

                    //Asserts
                    Assert.assertEquals(OwnAccountPage.TITLE_TEXT, ownAccountPage.get_element_text(ownAccountPage.getLblTitleNonStatic()));
                    Assert.assertEquals(OwnAccountPage.SUBTITLE_TEXT, ownAccountPage.get_element_text(ownAccountPage.getAccountDataSubtitle()));
                    Assert.assertEquals(OwnAccountPage.DESCRIPTION_TEXT, ownAccountPage.get_element_text(ownAccountPage.getAccount_description()));
                    Assert.assertEquals(OwnAccountPage.DISCLAIMER_TEXT, ownAccountPage.get_element_text(ownAccountPage.getDisclaimerText()));
                    Assert.assertEquals(OwnAccountPage.ALIAS_TEXT, ownAccountPage.get_element_text(ownAccountPage.getAlias()));
                    Assert.assertEquals(OwnAccountPage.CBU_TEXT, ownAccountPage.get_element_text(ownAccountPage.getCbu()));
                    Assert.assertEquals(OwnAccountPage.CUIT_TEXT, ownAccountPage.get_element_text(ownAccountPage.getCuit()));
                    Assert.assertEquals(OwnAccountPage.RAZONSOCIAL_TEXT, ownAccountPage.get_element_text(ownAccountPage.getRazonSocial()));

                    Assert.assertTrue(
                            ownAccountPage.elementExists(ownAccountPage.getDisclaimerIcon()));
                    break;
                case "RAPIPAGO":
                    RapiPagoPage rapiPagoPage = RapiPagoPage.getInstance();

                    //Asserts
                    Assert.assertEquals(RapiPagoPage.TITLE_TEXT, Wrapper.getElementText(rapiPagoPage.getCashInCodeTitle()));
                    Assert.assertEquals(RapiPagoPage.CODE_DESCRIPTION_TEXT, Wrapper.getElementText(rapiPagoPage.getRapipagoCodeDescription()));
                    Assert.assertEquals(RapiPagoPage.DISCLAIMER_TEXT, Wrapper.getElementText(rapiPagoPage.getDisclaimerText()));

                    while (!Wrapper.elementExists(RapiPagoPage.getRapipagoCodeTitle())){
                        Apps.util.scrollTo();
                    }
                    Assert.assertEquals(RapiPagoPage.CODE_TITLE_TEXT, Wrapper.getElementText(RapiPagoPage.getRapipagoCodeTitle()));
                    Assert.assertTrue(Wrapper.elementExists(rapiPagoPage.getRapipagoCode()));
                    Assert.assertTrue(Wrapper.elementExists(rapiPagoPage.getRapipagoCodeCopy()));

                    while (!Wrapper.elementExists(rapiPagoPage.getShareButton()))
                        Apps.util.scrollTo();

            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

}
