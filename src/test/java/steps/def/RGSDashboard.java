package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.dashboard.DashboardPage;
import core.Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.AssertionFailedError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOError;


public class RGSDashboard {

    private static Util util = Apps.util;
    private static final Logger logger = LogManager.getLogger();


    @Test

    @Then("Valida que exista la tarjeta del balance")
    public void validate_balance_card_element(){
        try {
           // Assert.assertTrue(nubiWallet.dashboard.uiObject.balanceCard().exists());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("Valida el titulo de tarjeta de balance")
    public void validate_balance_card_title(){
        try {
           /* String auxTitle;
            Assert.assertTrue(nubiWallet.dashboard.uiObject.balanceCardTitle().exists());
            auxTitle = nubiWallet.dashboard.uiObject.balanceCardTitle().getText();
            Assert.assertEquals("Mi Saldo", auxTitle);*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("validate frequent contactosagenda container")
    public void validate_frequent_contacts_container(){
        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();
            util.scrollTo();
            //Assert.assertTrue(dashboardPage.frequentContactContentExists());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("validate last 5Movements container")
    public void validate_latest_movements_container(){
        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();
            Assert.assertTrue(dashboardPage.elementExists(dashboardPage.getRecentTransactionsTitle()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @And("Valida que exista la tarjeta ultimos movimientos")
    public void validaQueExistaLaTarjetaUltimosMovimientos() throws Exception{
        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();
            Assert.assertTrue(dashboardPage.elementExists(dashboardPage.getRecentTransactionsTitle()));
        }
        catch (AssertionError e){
            logger.error("El elemento recent Transactions Title no existe");
        }
    }

    @And("Valida que exista la tarjeta contactos")
    public void validaQueExistaLaTarjetaContactos() throws Exception {
        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();
            Assert.assertTrue(dashboardPage.elementExists(dashboardPage.getFrequentContactsTitle()));
        }
        catch (AssertionError e){
            logger.error("El elemento Frequent contact container no existe");
        }
    }

    @And("Valida que exista la tarjeta de solicitudes")
    public void validaQueExistaLaTarjetaDeSolicitudes() throws Exception{
        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();
            Assert.assertTrue(dashboardPage.elementExists(dashboardPage.getRequestTitle()));
        }
        catch (AssertionError e){
            logger.error("El elemento requests no existe");
        }
    }


    @When("Pulsa sobre la tarjeta paga tus facturas")
    public void tapOnPayYourBillsCard() throws Exception {

        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();
            int count = 0;
          /*  while(!dashboardPage.elementExists(dashboardPage.getServicesPaymentIcon()) && count < 6 ) {
                util.scrollTo();
                count++;
            }
            dashboardPage.click_service_payment_button();*/
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("No se pudo encontrar o pulsar sobre la tarjeta de pago de servicios");
        }

    }


    /**
     * Valida que los elementos de la tarjeta de pago de servicios cumplan con los criterios
     * @throws Exception uno o mas elementos no coinciden con los parametros
     */
    @Then("Valida los elementos de la tarjeta pago de servicios")
    public void assertServicesPaymentCardElements() throws Exception{

        int count = 0;
        DashboardPage dashboardPage = DashboardPage.getInstance();
        /*while (!dashboardPage.elementExists(dashboardPage.getServicesPaymentText()) && count < 6) {
            util.scrollTo();
            count++;
        }*/

       /* try {
                Assert.assertEquals(DashboardPage.SERVICE_PAYMENT_TEXT, dashboardPage.get_element_text(dashboardPage.getServicesPaymentText()).replace("\n"," "));
                Assert.assertTrue(dashboardPage.elementExists(dashboardPage.getServicesPaymentIcon()));
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new Exception("Uno o mas elementos no cumplian con los criterios esperados");
        }*/

    }

    @Then("Se visualiza la tarjeta de promocion de prestamos")
    public void assertLoanPromotionCard() throws Exception {
        DashboardPage dashboard = DashboardPage.getInstance();
        while(!dashboard.elementExists(dashboard.getBtnPhonePurchaseButton())) util.scrollTo();

        try {
            Assert.assertTrue(dashboard.elementExists(dashboard.getImgPromotionImage()));
            Assert.assertTrue(dashboard.elementExists(dashboard.getLblPromotionDescription()));
            Assert.assertEquals(DashboardPage.ASK_FOR_YOUR_LOAN_BUTTON_TEXT, dashboard.get_element_text(dashboard.getBtnPhonePurchaseButton()));
        }
        catch (Exception | AssertionFailedError | IOError e){
            e.printStackTrace();
            throw new Exception("Uno o mas elementos de la tarjeta de promociones no coinciden con los criterios");
        }
    }

    @When("Usuario presiona sobre el boton pedi el tuyo en la tarjeta de promociones")
    public void clickAskYourLoanButton() throws Exception {

        try {
            DashboardPage dashboard = DashboardPage.getInstance();
            dashboard.clickAskForYourLoanButton();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("No se pudo hacer tap sobre el boton \"Pedi el tuyo\"");
        }
    }

    @When("Desde el dashboard, ir a la funcionalidad {string}")
    public void goto_from_dashboard(String module) throws Exception {
    try{

        DashboardPage dashboardPage = DashboardPage.getInstance();

        switch (module.toUpperCase()){
            case "ADD_ACCOUNT_MONEY":
                while (!Wrapper.elementExists(dashboardPage.getCashInCardIcon()))
                    Apps.util.scrollTo();

                dashboardPage.click_add_money();
                break;
            case "PREPAID_CARD":
                while (!Wrapper.elementExists(dashboardPage.getCashInCardIcon()))
                    Apps.util.scrollTo();
                dashboardPage.click_prepaidCard();
        }

    }catch (Exception | Error e){
        logger.error("Error al ir a funcionalidad: " + module.toUpperCase() + " desde el dashboar");
        throw e;
    }


    }
}
