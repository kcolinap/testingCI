package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.login.LoginPage;
import api.apps.android.nw.login.OnboardingPage;
import api.apps.android.nw.tarjetaprepaga.fisica.*;
import api.apps.android.nw.tarjetaprepaga.fisica.denunciar_tarjeta.ConfirmErrorPage;
import api.apps.android.nw.tarjetaprepaga.fisica.denunciar_tarjeta.ReportCardPage;
import api.apps.android.nw.tarjetaprepaga.landing.NoPrepaidCardPage;
import api.apps.android.nw.tarjetaprepaga.virtual.VirtualPage;
import com.github.javafaker.App;
import core.Util;
import core.commons.DBQuery;
import core.commons.apicall.PrepaidCard_API;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.mk_latn.No;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.beans.Visibility;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


public class PrepaidCardSteps {

    public Util util = NubiWallet.util;

    private static final Logger logger = LogManager.getLogger();


    public PrepaidCardSteps(){
    }

    /**
     * Metodo para validar todos los elementos de la tarjeta prepaga
     */
    @And("Actualizar estado de tarjeta {int}")
    public void update_card_status(int statusType) throws Exception {

        try {

            DBQuery dbQuery = Apps.dbQuery;
            PrepaidCard_API card_api = new PrepaidCard_API();
            JSONObject response= null;

            String statuscard = dbQuery.getPrepaidCardstatus(Login.usuario.getId());

            logger.info("Estatus actual: "+statuscard);
            //Set status
            //0 to activated
            //1 for blocked
           switch (statusType){
               case 0://0 for status unblocked
                   if(!statuscard.toUpperCase().contentEquals("ACTIVATED")) {
                       response = card_api.call_Prepaid_Card_Update("ACTIVATED");
                       Apps.dbQuery.update_card_status(0, Login.usuario.getId());
                       logger.info("Tarjeta activada");

                       //login into the app, because the previous rqueste
                       //erase the session
                       Android.driver.resetApp();
                       OnboardingPage.getInstance().click_login();
                       LoginPage.getInstance().login(Login.usuario.getUsername(), "Test-0000");
                       DashboardPage.getInstance();

                   }
                   break;
               case 1: // 1 to block
                   if(!statuscard.toUpperCase().contentEquals("BLOCKED")) {
                       response = card_api.call_Prepaid_Card_Update("BLOCKED");
                       Apps.dbQuery.update_card_status(1, Login.usuario.getId());
                       logger.info("tarjeta congelada");

                       //login into the app, because the previous rqueste
                       //erase the session
                       Android.driver.resetApp();
                       OnboardingPage.getInstance().click_login();
                       LoginPage.getInstance().login(Login.usuario.getUsername(), "Test-0000");
                       DashboardPage.getInstance();
                   }
                   break;
           }

        }
        catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @When("Pulsar boton para {string} la tarjeta {string}")
    public void click_action_button(String action, String cardType){
        try {
            if (cardType.toUpperCase().contentEquals("PC_PHYSICAL")){
                LandingPhysicalPage landingPhysicalPage = LandingPhysicalPage.getInstance();

                switch (action.toUpperCase()){
                    case "TO_BLOCK":
                        Assert.assertEquals(LandingPhysicalPage.ENABLED_SWITCH_TITLE_TEXT, landingPhysicalPage.get_element_text(landingPhysicalPage.getSwitchTitle()).replace("\n", " "));
                        Assert.assertFalse(landingPhysicalPage.elementIsCheked(landingPhysicalPage.getCardFreezeSwitch()));
                        landingPhysicalPage.click_freeze_card_switch();
                        break;
                    case "TO_UNBLOCK":
                        Assert.assertEquals(LandingPhysicalPage.DISABLED_SWITCH_TITLE_TEXT, landingPhysicalPage.get_element_text(landingPhysicalPage.getSwitchTitle()).replace("\n", " "));
                        Assert.assertTrue(landingPhysicalPage.elementIsCheked(landingPhysicalPage.getCardFreezeSwitch()));
                        landingPhysicalPage.click_freeze_card_switch();
                        break;
                    case "CREATE_PIN":
                        Assert.assertEquals(LandingPhysicalPage.CREATE_PIN_TEXT, landingPhysicalPage.get_element_text(landingPhysicalPage.getCreatePinButtonText()).replace("\n", " "));
                        landingPhysicalPage.click_create_update_pin();
                        break;
                    case "CHANGE_PIN":
                        Assert.assertEquals(LandingPhysicalPage.CHANGE_PIN_TEXt, landingPhysicalPage.get_element_text(landingPhysicalPage.getCreatePinButtonText()).replace("\n", " "));
                        landingPhysicalPage.click_create_update_pin();
                        break;
                    case "AVISO_VIAJE":
                        landingPhysicalPage.click_travel_notify();
                        break;
                    case "REPORT_CARD":
                        landingPhysicalPage.click_report_card();
                        break;
                }

            }else{
                VirtualPage virtualPage = VirtualPage.getInstance();

                while (!virtualPage.elementExists(virtualPage.getCardFreezeSwitch()))
                    util.scrollTo();

                switch (action.toUpperCase()){
                    case "TO_BLOCK":
                        Assert.assertFalse(virtualPage.elementIsCheked(virtualPage.getCardFreezeSwitch()));
                        break;
                    case "TO_UNBLOCK":
                        Assert.assertTrue(virtualPage.elementIsCheked(virtualPage.getCardFreezeSwitch()));
                        break;
                }

                //Click to block the virtual card
                virtualPage.click_card_freeze_switch();

            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    @Then("Validar pantalla al {string} la tarjeta {string}")
    public void validate_after_change_status_card(String action, String cardType){
        try {
            if(cardType.toUpperCase().contentEquals("PC_PHYSICAL")){
                LandingPhysicalPage physicalPage = LandingPhysicalPage.getInstance();

                switch (action.toUpperCase()){
                    case "TO_BLOCK":
                        physicalPage.waitForElementEnabled(physicalPage.getPrepaidCardLossReportLink());

                        //Asserts
                        Assert.assertTrue(physicalPage.elementExists(physicalPage.getFreezeIcon()));
                        Assert.assertTrue(physicalPage.elementIsCheked(physicalPage.getCardFreezeSwitch()));
                        Assert.assertEquals(physicalPage.FREEZE_MESSAGE_TEXT, physicalPage.get_element_text(physicalPage.getFreezeMessage()).replace("\n"," "));
                        Assert.assertEquals(physicalPage.DISABLED_SWITCH_TITLE_TEXT, physicalPage.get_element_text(physicalPage.getSwitchTitle()).replace("\n", " "));
                        break;
                    case "TO_UNBLOCK":
                        physicalPage.waitForElementVisibility(physicalPage.getCreatePinButtonIcon());

                        //Asserts
                        Assert.assertFalse(physicalPage.elementExists(physicalPage.getFreezeIcon()));
                        Assert.assertFalse(physicalPage.elementIsCheked(physicalPage.getCardFreezeSwitch()));
                        Assert.assertFalse(physicalPage.elementExists(physicalPage.getFreezeMessage()));
                        Assert.assertEquals(physicalPage.ENABLED_SWITCH_TITLE_TEXT, physicalPage.get_element_text(physicalPage.getSwitchTitle()).replace("\n", " "));
                        break;
                }
            }else {
                VirtualPage virtualPage = VirtualPage.getInstance();

                switch (action.toUpperCase()){
                    case "TO_BLOCK":
                        virtualPage.waitForElementVisibility(virtualPage.getFreezeIcon());

                        //Asserts
                        Assert.assertTrue(virtualPage.elementIsCheked(virtualPage.getCardFreezeSwitch()));
                        Assert.assertEquals(virtualPage.FREEZE_MESSAGE_TEXT, virtualPage.get_element_text(virtualPage.getFreezeMessage()).replace("\n", " "));

                        Assert.assertFalse(virtualPage.elementExists(virtualPage.getCardNumberTitle()));
                        Assert.assertFalse(virtualPage.elementExists(virtualPage.getSecurityCodeTitle()));
                        Assert.assertFalse(virtualPage.elementExists(virtualPage.getExpirationDataTitle()));
                        break;
                    case "TO_UNBLOCK":
                        virtualPage.waitForElementVisibility(virtualPage.getSeeCardInfoButton());

                        //Asserts
                        Assert.assertFalse(virtualPage.elementIsCheked(virtualPage.getCardFreezeSwitch()));

                        Assert.assertTrue(virtualPage.elementExists(virtualPage.getExpirationDataTitle()));
                        Assert.assertTrue(virtualPage.elementExists(virtualPage.getSecurityCodeTitle()));
                        Assert.assertTrue(virtualPage.elementExists(virtualPage.getExpirationDataTitle()));
                        break;
                }
            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }

    }

    @Then("Rollback status card")
    public void rollback_status_card() throws SQLException{
        try {

            DBQuery dbQuery = Apps.dbQuery;
            PrepaidCard_API card_api = new PrepaidCard_API();
            JSONObject response= null;

            String statuscard = dbQuery.getPrepaidCardstatus(Login.usuario.getId());

            if(!statuscard.toUpperCase().contentEquals("ACTIVATED")) {
                response = card_api.call_Prepaid_Card_Update("ACTIVATED");
                Apps.dbQuery.update_card_status(0, Login.usuario.getId());
                statuscard = dbQuery.getPrepaidCardstatus(Login.usuario.getId());
                logger.info("Se restablecio estado a " + statuscard);
            }

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    /**
     * Metodo para actualizar estado del pin de extraccion
     */
    @And("Actualizar estado de pin de extraccion {string}")
    public void update_card_pin_status(String statusType) throws SQLException, IOException, ParseException {

        try {

            DBQuery dbQuery = Apps.dbQuery;

            String statuscard = dbQuery.getPrepaidCardPinStatus(Login.usuario.getId());

            logger.info("Estatus actual: "+statuscard);
            //Set status
            //0 to activated
            //1 for blocked
            switch (statusType.toUpperCase()){
                case "DISABLED":
                    if(!statuscard.toUpperCase().contentEquals("F")) {
                       dbQuery.update_pin_extraction_status(false, Login.usuario.getId());
                        logger.info("Se deshabilito el pin de extraccion");
                    }
                    break;
                case "ENABLED":
                    if(!statuscard.toUpperCase().contentEquals("T")) {
                        dbQuery.update_pin_extraction_status(true, Login.usuario.getId());
                        logger.info("Se habilito el pin de extraccion");
                    }
                    break;
            }

        }
        catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @Then("Validar pantalla de {string}")
    public void validate_result_screen(String screenToValidate){
        try {
            switch (screenToValidate.toUpperCase()){
                case "CAMBIO_PIN_EXITOSO":
                case "CREATION_PIN_EXITOSA":
                    SuccessfulExtractionPinPage successPage = SuccessfulExtractionPinPage.getInstance();

                    //Asserts
                    try{
                        Assert.assertEquals(SuccessfulExtractionPinPage.MESSAGE_CREATION_TEXT, successPage.get_element_text(successPage.getMessage()).replace("\n", " "));
                    }catch (AssertionError error){
                        Assert.assertEquals(SuccessfulExtractionPinPage.MESSAGE_UPDATE_TEXT, successPage.get_element_text(successPage.getMessage()).replace("\n", " "));
                    }

                    Assert.assertEquals(SuccessfulExtractionPinPage.SECONDARY_MESSAGE_TEXT, successPage.get_element_text(successPage.getSecondaryMessage()).replace("\n", " "));
                    Assert.assertTrue(successPage.elementExists(successPage.getEmoji()));
                    successPage.waitForElementEnabled(successPage.getMainButton());

                    //back to landing prepaid card
                    successPage.click_finish_button();

                    break;
                case "ERROR_CONFIRMACION_PIN":
                    CreateExtractionPinPage createExtractionPinPage = CreateExtractionPinPage.getInstance();

                    //Assert error message
                    Assert.assertEquals(CreateExtractionPinPage.ERROR_MESSAGE_TEXT, createExtractionPinPage.get_element_text(createExtractionPinPage.getErrorMessage()).replace("\n", " "));
                    break;
                case "AVISO_VIAJE":
                    TravelNotifyPage travelPage = TravelNotifyPage.getInstance();

                    //Asserts
                    Assert.assertEquals(TravelNotifyPage.TITLE_TEXT, travelPage.get_element_text(TravelNotifyPage.getLblTitle()).replace("\n"," "));
                    Assert.assertEquals(TravelNotifyPage.MESSAGE_TEXT, travelPage.get_element_text(travelPage.getMessage()).replace("\n"," "));
                    Assert.assertEquals(TravelNotifyPage.SECONDARY_MESSAGE_TEXT, travelPage.get_element_text(travelPage.getSecondaryMessage()).replace("\n"," "));

                    travelPage.waitForElementEnabled(travelPage.getMainButton());
                    break;
                case "REPORT_CARD":
                    ReportCardPage reportCardPage = ReportCardPage.getInstance();

                    //Asserts
                    Assert.assertEquals(ReportCardPage.TITLE_TEXT, reportCardPage.get_element_text(reportCardPage.getPrepaidCardTitle()));
                    Assert.assertEquals(ReportCardPage.REASON_SUBTITLE_TEXT, reportCardPage.get_element_text(reportCardPage.getReasonSubtitle()).replace("\n", " "));
                    Assert.assertTrue(
                            reportCardPage.elementExists(reportCardPage.getRadioLoss()) &&
                                    reportCardPage.elementExists(reportCardPage.getRadioSteal()) &&
                                    reportCardPage.elementExists(reportCardPage.getRadioDamage())
                    );

                    Assert.assertEquals(ReportCardPage.DISCLAIMER_TEXT, reportCardPage.get_element_text(reportCardPage.getDisclaimerText()).replace("\n", " "));
                    Assert.assertEquals(ReportCardPage.ADDRESS_SUBTITLE_TEXT, reportCardPage.get_element_text(reportCardPage.getAddressSubtitle()).replace("\n", " "));

                    Assert.assertTrue(reportCardPage.adresses_quantity()>0);
                    break;
                case "CONFIRM_REPORT_ERROR":
                    ConfirmErrorPage errorPage = ConfirmErrorPage.getInstance();

                    //Asserts
                    Assert.assertTrue(errorPage.elementExists(errorPage.getEmoji()));
                    Assert.assertEquals(ConfirmErrorPage.TITLE_TEXT, errorPage.get_element_text(ConfirmErrorPage.getLblTitle()));
                    Assert.assertEquals(ConfirmErrorPage.SUBTITLE_TEXT, errorPage.get_element_text(errorPage.getLblSubtitle()).replace("\n", " "));
                    Assert.assertEquals(ConfirmErrorPage.DISCLAIMER_TEXT, errorPage.get_element_text(errorPage.getDisclaimerText()).replace("\n", " "));
                    break;
            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    @Then("Volver a la pantalla de landing")
    public void back_landing_prepaid_card(){
        SuccessfulExtractionPinPage successPage = SuccessfulExtractionPinPage.getInstance();
        successPage.click_finish_button();
    }

    @When("Pulsar boton avisar")
    public void click_notify_button(){
        TravelNotifyPage travelpage = TravelNotifyPage.getInstance();

        //Click to notify travel
        travelpage.click_notify();
        try {
            if(travelpage.elementExists(travelpage.getMainButton()))
                travelpage.waitForElementNotVisible(travelpage.getMainButton());

        }catch (Exception e){
            throw e;
        }

    }

    @When("Pulsar confirmar denuncia")
    public void confirm_report_card(){
        try{
            ReportCardPage.getInstance().click_confirm_report_card();
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @Then("Validar elementos de tarjeta prepaga {string}")
    public void validate_kind_prepaid_card(String kind){
        try{
            switch (kind.toUpperCase()){
                case "NO_PC":
                    NoPrepaidCardPage noCard = NoPrepaidCardPage.getInstance();

                    //Asserts
                    Assert.assertEquals(NoPrepaidCardPage.NO_CARD_TITLE, Wrapper.getElementText(NoPrepaidCardPage.getLblTitle()));
                    Assert.assertEquals(NoPrepaidCardPage.BUTTON_TEXT, Wrapper.getElementText(noCard.getButton()));
                    Assert.assertTrue(Wrapper.elementExists(noCard.getCard()));
                    Wrapper.waitForElementEnabled(noCard.getButton());
                    break;
                case "VIRTUAL_PC":
                    VirtualPage virtualPage = VirtualPage.getInstance();

                    //Asserts
                    Assert.assertEquals(VirtualPage.VIRTUAL_CARD_TITLE, Wrapper.getElementText(virtualPage.getCardTitle()));

                    while (!Wrapper.elementExists(virtualPage.getCardNumberText())){
                        Apps.util.scrollTo();
                    }

                    while (!Wrapper.elementExists(virtualPage.getSecurityCodeText())){
                        Apps.util.scrollTo();
                    }

                    while (!Wrapper.elementExists(virtualPage.getExpirationDataText())){
                        Apps.util.scrollTo();
                    }

                    while (!Wrapper.elementExists(virtualPage.getSeeCardInfoButton())){
                        Apps.util.scrollTo();
                    }

                    while (!Wrapper.elementExists(virtualPage.getCardFreezeSwitch())){
                        Apps.util.scrollTo();
                    }

                    while (!Wrapper.elementExists(virtualPage.getCardActivationButton())){
                        Apps.util.scrollTo();
                    }

                    Assert.assertTrue(VirtualPage.VIRTUAL_CARD_TITLE, Wrapper.elementExists(virtualPage.getShippingDetailButton()));
                    break;
                case "PHYSICAL_PC":
                    LandingPhysicalPage physicalPage = LandingPhysicalPage.getInstance();

                    //Asserts
                    Assert.assertEquals(LandingPhysicalPage.PHYSICAL_CARD_TITLE, Wrapper.getElementText(physicalPage.getCardTitle()));
                    Assert.assertTrue(Wrapper.elementExists(physicalPage.getCreatePinButtonIcon()));
                    Assert.assertTrue(Wrapper.elementExists(physicalPage.getCreatePinButtonText()));
                    Assert.assertTrue(Wrapper.elementExists(physicalPage.getTravelButtonIcon()));
                    Assert.assertTrue(Wrapper.elementExists(physicalPage.getTravelButtonText()));

                    while (!Wrapper.elementExists(physicalPage.getCardFreezeSwitch())){
                        Apps.util.scrollTo();
                    }

                    while (!Wrapper.elementExists(physicalPage.getPrepaidCardLossReportLink())){
                        Apps.util.scrollTo();
                    }

                    break;

            }

        }catch (Exception | Error e){
            logger.error(e);
            e.printStackTrace();
            throw e;
        }
    }
}
