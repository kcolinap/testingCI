package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.model.Sube;
import api.apps.android.nw.model.transactions.Transaction;
import api.apps.android.nw.operaciones.sube.EditAliasPage;
import api.apps.android.nw.operaciones.sube.NubiPassRechargeSubePage;
import api.apps.android.nw.operaciones.sube.cargarSaldo.SelectAmountPage;
import api.apps.android.nw.operaciones.sube.confirmacionDeRecarga.CreditConfirmationPage;
import api.apps.android.nw.operaciones.sube.listaTarjetas.CardListPage;
import api.apps.android.nw.operaciones.sube.pantallaError.ScreenErrorPage;
import api.apps.android.nw.operaciones.sube.primerUso.LoadSubePage;
import api.apps.android.nw.operaciones.sube.recargaExitosa.SuccessSubeRechargePage;
import api.apps.android.nw.operaciones.sube.registrarSube.SubeRegisterPage;
import core.Util;
import core.commons.apicall.Sube_API;
import core.commons.apicall.dtos.sube.DeleteSube;
import io.appium.java_client.MobileElement;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.interactions.Keyboard;

import java.text.SimpleDateFormat;
import java.util.*;

public class RGSSubeCard {

    private Date date = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es","AR"));
    private SimpleDateFormat hourFormat;
    private SimpleDateFormat amOrPmFormat = new SimpleDateFormat("a");

    public Util util = Apps.util;

    public static String validSubeNumber = "6061268880223082";//"6061267340141116";
    private String blackLisSubeNumber = "6061267187152044";
    private String rechargeAmount;
    private static String fixAlias;

    public RGSSubeCard(){
        if (amOrPmFormat.format(date).contains("AM"))
            hourFormat = new SimpleDateFormat("hh:mm 'a. m.'");
        else if (amOrPmFormat.format(date).contains("PM"))
            hourFormat = new SimpleDateFormat("hh:mm 'p. m.'");
    }



    /**
     * Metodo para verificar que en la pantalla de registro de tarjeta sube se muestran
     * los siguientes elementos: Titulo SUBE, boton back, ilustracion de la tarjeta Sube,
     * label griseado con el texto "Numero de tarjeta SUBE" y boton continuar deshabilitado
     */

    @Then("Se muestran los elementos de registracion de tarjeta")
    public void assertRegistrationSubeCardScreenElements() {

        SubeRegisterPage subeRegisterPage = SubeRegisterPage.getInstance();

        try{
            Assert.assertEquals(subeRegisterPage.getLblTitle().getText(), subeRegisterPage.TEXT_SUBE_UPPERCASE);
            Assert.assertTrue(subeRegisterPage.buttonBackExists());
            Assert.assertFalse(subeRegisterPage.continueButtonIsEnabled());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Metodo para ingresar un numero de tarjeta SUBE
     * @param partial =  nos permite indicar si el numero tendrá 16 digitos o solo 6.
     * @param valOrInv = nos permite indicar si el valor de la tarjeta sera valido o invalida.
     */

    @And("Ingresa un numero de tarjeta sube {string} y {string}")
    @Then("Se puede ingresar un numero de tarjeta sube {string} y {string}")
    public void typeTextOnNewSubeCardInput(String partial , String valOrInv) {

        String invalidSubeNumber;
        SubeRegisterPage subeRegisterPage = SubeRegisterPage.getInstance();

        try {
            if (partial.equals("completo") && valOrInv.equals("valido")){
                subeRegisterPage.typeTextOnSubeCardField(validSubeNumber);
                LastMovementsSteps.setTransctionDescription(validSubeNumber);
            }
            else if (partial.equals("completo") && valOrInv.equals("invalido")){
                invalidSubeNumber = validSubeNumber.substring(0,5) + "5" + validSubeNumber.substring(6,15) + "7";
                subeRegisterPage.typeTextOnSubeCardField(invalidSubeNumber);
            }
            else if (partial.equals("completo") && valOrInv.equals("lista_negra")){
                subeRegisterPage.typeTextOnSubeCardField(blackLisSubeNumber);

            }
            else if (partial.equals("parcial") && valOrInv.equals("invalido")){
                invalidSubeNumber = validSubeNumber.substring(0,5) + "5" ;
                subeRegisterPage.typeTextOnSubeCardField(invalidSubeNumber);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * Metodo para verificar que se muestren las validaciones de tarjeta SUBE
     * en la pantalla de registro de tarjeta
     **/
    @Then("Se valida el boton continuar y los elementos de advertencia si el numero de la tarjeta es {string} y {string}")
    public void assertScreenValidationElements(String partial , String valOrInv) {

        SubeRegisterPage subeRegisterPage = SubeRegisterPage.getInstance();

        try {

            if(Android.driver.isKeyboardShown())
                Android.driver.hideKeyboard();

            if (partial.equals("completo") && valOrInv.equals("valido")){

                String inputValue = subeRegisterPage.getCardNumber();
                Wrapper.waitForElementEnabled(subeRegisterPage.getNextButton());
                Assert.assertEquals( "606126", inputValue.substring(0,6));
                Assert.assertEquals( "2", inputValue.substring(15));
            }
            else if(partial.equals("completo") && valOrInv.equals("invalido")){
                Wrapper.waitForElementVisibility(subeRegisterPage.getSubeErrprInput());
                Assert.assertEquals(subeRegisterPage.getErrorMessage(), subeRegisterPage.TEXT_WARNING_INVALIDSUBE);
                Wrapper.waitForElementDisabled(subeRegisterPage.getNextButton());
            }
            else if (partial.equals("completo") && valOrInv.equals("lista negra")){
                Assert.assertTrue(subeRegisterPage.continueButtonIsEnabled());
            }
            else if(partial.equals("parcial") && valOrInv.equals("invalido")){
                Assert.assertEquals(subeRegisterPage.getErrorMessage(), subeRegisterPage.TEXT_WARNING_INVALIDSUBE);
                Assert.assertFalse(subeRegisterPage.continueButtonIsEnabled());
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new NoSuchElementException("Uno o mas elementos de validacion son diferentes o no estan presentes");
        }

        try {
            subeRegisterPage.clearSubeCardField();
            Assert.assertFalse(subeRegisterPage.continueButtonIsEnabled());
        }
        catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Metodo pulsar sobre el boton continuar de la pantalla registro SUBE
     * El mismo valida que el boton este habilitado, luego lo pulsa y finalmente
     * nos devuelve una instancia de la pantalla (clase) recarga de saldo.
     **/
    @And("Pulsa en el boton continuar")
    public void tapOnContinueButton() {
        SubeRegisterPage subeRegisterPage = SubeRegisterPage.getInstance();

        try{
           subeRegisterPage.hideKeyboardIfVisible();
            subeRegisterPage.waitForContinueButtonEnabled();
            subeRegisterPage.clickOnContinueButton();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new NoSuchElementException("No se pudo hacer click sobre el boton continuar");
        }
    }


    public void scrollToSpecificText(String desiredAmount, int maxSwipes) {
        int swipesCount = 0;
        while( swipesCount < maxSwipes && !SelectAmountPage.getInstance().existASpecificAmount(desiredAmount) ) {
            util.scrollTo();
            swipesCount++;
        }
    }

    /**
     * Metodo para verificar que todos los elementos de la pantalla recarga SUBE se muestran.
     * Si alguno de los montos no se muestran en la pantalla, hace scroll hasta hallarlo.
     */
    @And("Se muestran los elementos de recarga de saldo")
    public void assertRechargeSubeCardElements() {

        String subeCardNumberMasked =
                validSubeNumber.substring(0,4) + " " +
                validSubeNumber.substring(4,8) + " " +
                validSubeNumber.substring(8,12) + " " +
                validSubeNumber.substring(12,16);

        try {

            Assert.assertTrue(SelectAmountPage.getInstance().elementExists(SelectAmountPage.getInstance().getBackOrCloseBtn()));
            Assert.assertEquals(SelectAmountPage.getInstance().expectedMainTitleText, SelectAmountPage.getInstance().getCardAmountsTitleText().trim());
            Assert.assertEquals(SelectAmountPage.getInstance().expectedSubTitleText, SelectAmountPage.getInstance().getLblSubtitle().getText().trim());
            Assert.assertEquals(fixAlias + subeCardNumberMasked , SelectAmountPage.getInstance().getCardSubeDataText().trim().replace("\n",""));

            scrollToSpecificText("$60", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$60"));

            scrollToSpecificText("$100", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$100"));
            scrollToSpecificText("$150", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$150"));
            scrollToSpecificText("$200", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$200"));
            scrollToSpecificText("$250", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$250"));
            scrollToSpecificText("$300", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$300"));
            scrollToSpecificText("$350", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$350"));
            scrollToSpecificText("$400", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$400"));
            scrollToSpecificText("$450", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$450"));
            scrollToSpecificText("$500", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$500"));
            scrollToSpecificText("$600", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$600"));
            scrollToSpecificText("$700", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$700"));
            scrollToSpecificText("$800", 5);
            Assert.assertTrue(SelectAmountPage.getInstance().existASpecificAmount("$800"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Given("Usuario selecciona para recargar el monto: {string}")
    public void usuarioSeleccionaParaRecargarElMonto(String monto) throws Exception{

        try {
            //Get user balance
            String originalBalance = CommonStepsMovements.getBalance();
            if(Apps.util.getNumericBalance(originalBalance)<=0)
                throw new Exception("El usuario no tiene saldo para realizar la recarga Sube");

            SelectAmountPage.getInstance().selectOneAmount(monto);
        }
        catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
        finally {
            rechargeAmount = monto;
            LastMovementsSteps.setTransactionAmount(monto.replace("$", "") + ",00");
        }

    }

    @And("Se muestran los elementos de revision de recarga")
    public void assertRechargeRevisionElements() {

        try {

            String subeCardNumberMasked =
                    validSubeNumber.substring(0,4) + " " +
                            validSubeNumber.substring(4,8) + " " +
                            validSubeNumber.substring(8,12) + " " +
                            validSubeNumber.substring(12,16);

            Assert.assertEquals(CreditConfirmationPage.getInstance().expectedMainTitleText,
                    CreditConfirmationPage.getInstance().getCardChargeConfirmationTitleText());

            Assert.assertEquals(CreditConfirmationPage.getInstance().expectedChargeConfirmReviewMessageText,
                    CreditConfirmationPage.getInstance().getChargeConfirmReviewMessageText());

            Assert.assertTrue(CreditConfirmationPage.getInstance().existSubeIcon());

            Assert.assertEquals(fixAlias + subeCardNumberMasked,
                    CreditConfirmationPage.getInstance().getSubeCardChargeConfirmationTitleText().replace("\n",""));

            Assert.assertEquals(CreditConfirmationPage.getInstance().expectedMoneySymbolText,
                    CreditConfirmationPage.getInstance().getMoneySymbolText());

            Assert.assertEquals(rechargeAmount.replace("$", ""), CreditConfirmationPage.getInstance().getMoneyAmountText());

            Assert.assertTrue(CreditConfirmationPage.getInstance().isContinueButtonEnabled());
        }
        catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Metodo para pulsar sobre el boton Continuar de la pantalla de Confirmacion
     */
    @When("Presiona sobre el boton continuar en pantalla de confirmacion SUBE")
    public void tapOnCargarSubeButton() throws Exception{

        try {
           CreditConfirmationPage.getInstance().clickContinueButton();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }


    @And("Se validan los elementos de la pantalla recarga exitosa")
    public void assertSuccessPageElements() {
        try {
            SuccessSubeRechargePage successSubeRechargePage = SuccessSubeRechargePage.getInstance();
            //Asserts
            Assert.assertTrue(successSubeRechargePage.elementExists(successSubeRechargePage.getEmoji()));
            Assert.assertTrue(successSubeRechargePage.get_element_text(successSubeRechargePage.getMessage()).contains(SuccessSubeRechargePage.TEXTO_ACREDITACION));


        }
        catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Se presiona sobre el boton entendido y a la vez se setean las variables
     * que guardan el tipo de movimiento realizado para asi poder compararlo
     * en ultimos movimientos.
     */
    @When("presiona sobre el boton entendido")
    public void tapOnEntendidoButton() throws Exception{
        try{
            SuccessSubeRechargePage successSubeRechargePage = SuccessSubeRechargePage.getInstance();

            //Set transaction data
            List<Transaction> list = Apps.mongoQuery.getTransactionsList(Util.OUTGOING, NubiWallet.usuario.getId());
            Transaction ts1 = list.get(0);

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es","AR"));
            SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm aa");

            LastMovementsSteps.setTransctionDescription("Cargaste SUBE");
            LastMovementsSteps.setTransactionAmount(String.valueOf(ts1.getAmount()).replace(".",","));
            LastMovementsSteps.setTransctionDate(dateFormat.format(ts1.getCreatedAt()));
            LastMovementsSteps.setTransctionHour(hourFormat.format(ts1.getCreatedAt()));


            successSubeRechargePage.clickFinishButton();

        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Then("Pulsa en el boton cargar sube primer uso")
    public void pulsaEnElBotonCargarSube() {
        try{
            Login.usuario.refreshSubeList();

            List<Sube> subeList = Login.usuario.getSubeList();

            if(!subeList.isEmpty()){

                CardListPage cardListPage = CardListPage.getInstance();
                Assert.assertTrue(cardListPage.getCardItems().size()>0);

                for (Sube sube : subeList){
                    CardListPage.getInstance().clickDeleteCard();
                }
            }


            //commonActions.hideKeyBoard();
            LoadSubePage cargarSubePage = LoadSubePage.getInstance();
            cargarSubePage.clickOnAddSubeButton();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new NoSuchElementException("No se pudo hacer click sobre el boton continuar");
        }
    }

    @And("Ingresa un alias {string} sube {string}")
    public void ingresaUnAliasSube(String alias, String status) {
        SubeRegisterPage subeRegisterPage = SubeRegisterPage.getInstance();
        if (status.equals("valido")){
             subeRegisterPage.typeAliasOnSubeCardField(alias);
             fixAlias = alias;
        }
        if (status.equals("invalido")){
            subeRegisterPage.typeAliasOnSubeCardField(alias);
            // CHEQUEO DE ERROR !!
        }
    }

    @And("Usuario ingresa un alias")
    public void usuarioIngresaUnAlias(DataTable table) throws InterruptedException {

        List<List<String>> rows = table.asLists(String.class);
        for (List<String> columns : rows) {
            CharSequence alias = columns.get(0);
            String status = columns.get(1);
            String error = columns.get(2);

            Keyboard keyboard = Android.driver.getKeyboard();
            SubeRegisterPage subeRegisterPage = SubeRegisterPage.getInstance();
            subeRegisterPage.clearCardAliasField();
            keyboard.pressKey(alias);
            Android.hideKeyboardIfVisible();
            if (status.equals("invalido")) {
                if (!error.equals("none")) {
                    if (error.equals("button_disabled")) {
                        // Check button disabled
                        Assert.assertFalse(subeRegisterPage.continueButtonIsEnabled());
                    } else {
                        // Check Warning message
                        subeRegisterPage.waitForError();
                        Assert.assertEquals(error,subeRegisterPage.getErrorMessage());
                    }
                }
            }
        }

    }

    @Then("Pulsa en el boton agregar sube")
    public SubeRegisterPage pulsaEnElBotonAgregarSube() {
        try{
            Android.hideKeyboardIfVisible();
            return CardListPage.getInstance().clickAddSubeCardButton();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new NoSuchElementException("No se pudo hacer click sobre el boton agregar SUBE");
        }
    }

    @Then("Usuario elimina todas las tarjeta del listado")
    public void usuarioEliminaTodasLasTarjetaDelListado() {
        // ELIMINAR TODAS LAS TARJETAS
        CardListPage listPage = CardListPage.getInstance();
        HashMap<Integer, HashMap<String, MobileElement>> cardList = listPage.getCardList();
        if(cardList.isEmpty()){
            System.out.println("no hay tarjetas");
            //APICall apiCall = new APICall("hh", "67");
            //apiCall.recarga_sube(Login.cuil);
        }else {
            for(HashMap<String, MobileElement> value : cardList.values()){
                //for (MobileElement alias : value.get("alias")) {
                    System.out.println(value.get("alias").getText());
                    //for (MobileElement number : value.get("cardNumber")) {
                        System.out.println(value.get("cardNumber").getText());
                        //for (MobileElement actionBtn : value.get("actionBtn")) {
                            value.get("actionBtn").click();
                            listPage.clickDeleteCard();
                        //}
                    //}
                //}
            }
        }

    }

    @Then("Presiona sobre el boton volver al inicio")
    public void presionaSobreElBotonVolverAlInicio() {
        try{
            ScreenErrorPage.getInstance().clickBackHomeButton();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new NoSuchElementException("No se pudo hacer click sobre el boton agregar SUBE");
        }
    }

    @And("Validar que tiene tarjeta sube agregada")
    public void validar_tarjeta_sube() throws Exception{
        try {
            // boolean existe = db.existeSube(Login.usuario.getId());

            Sube_API sube_api = new Sube_API();
            sube_api.recarga_sube(NubiWallet.usuario.getCuil());

        }catch (Exception | Error err){
            throw err;
        }

    }

    @Then("Eliminar tarjetas")
    public void delete_sube_card_from_BE() throws Exception{
        try{
            Login.usuario.refreshSubeList();

            List<Sube> subeList = Login.usuario.getSubeList();

            if(!subeList.isEmpty()){
                //DElete user's sube card
                Sube_API sube_api = new Sube_API();
                DeleteSube deleteSube;

                for (Sube sube : subeList){
                    deleteSube = sube_api.delete_sube(sube.getId());
                    Assert.assertTrue(deleteSube.isSuccess());
                }
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @And("Pulsar action button para {string}")
    public void tapActionButton(String button){
        try {
            switch (button.toUpperCase()){
                case "EDIT_ALIAS":
                    CardListPage.getInstance().clickEditAlias();
                    break;
                case "DELETE_CARD":
                    CardListPage.getInstance().clickDeleteCard();
            }
        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @When("Editar alias de tarjeta sube por {string}")
    public void edit_sube_alias(String alias){
        try {

            EditAliasPage editAliasPage = EditAliasPage.getInstance();

            //Edit alias
            editAliasPage.setAlias(alias);
            fixAlias = alias;

            //Confirm changes
            editAliasPage.clickOnContinueButton();
        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @Then("Validar cambio en alias")
    public void Validate_alias_change(){
        try {

            CardListPage cardListPage = CardListPage.getInstance();

            //validate alias text
            Assert.assertEquals(fixAlias, Wrapper.getElementText(cardListPage.getCardAlias()));

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @When("Usuario ingresa clave nubi {string} en recarga SUBE")
    public void set_nubi_pass_sube_recharge(String type) throws InterruptedException {
        try {
            switch (type.toUpperCase()){
                case "INCORRECTO":
                    NubiPassRechargeSubePage.getInstance().set_invalid_pin();
                    break;
                case "CORRECTO":
                    break;
            }
        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @Then("Validar pantalla al ingresar Clave Nubi {string} en recarga SUBE")
    public void validar_pantalla_pin_recarga_sube(String type) {
        try {
            switch (type.toUpperCase()) {
                case "INCORRECTO":
                    NubiPassRechargeSubePage nubiPassRechargeSubePage = NubiPassRechargeSubePage.getInstance();
                    Assert.assertEquals(NubiPassRechargeSubePage.WRONG_NUBI_PASSWORD_TEXT, Wrapper.getElementText(nubiPassRechargeSubePage.getErrorMessageElement()));
                    break;
                case "CORRECTO":
                    break;
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            throw e;
        }
    }
}
