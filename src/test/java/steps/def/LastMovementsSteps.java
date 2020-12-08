package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.TextList;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.menu.MenuPage;
import api.apps.android.nw.model.transactions.Transaction;
import api.apps.android.nw.movimientos.LastMovementsListPage;
import api.apps.android.nw.movimientos.details.movementTypes.P2PSendDetailPage;
import api.apps.android.nw.movimientos.details.movementTypes.PrepaidCardDetailPage;
import api.apps.android.nw.movimientos.details.movementTypes.SubeCardDetailPage;
import api.apps.android.nw.operaciones.p2p.request.RequestsLandingPage;
import core.Util;
import api.apps.android.Wrapper;
import core.commons.apicall.Dealer_API;
import core.commons.apicall.Rapipago_API;
import core.commons.apicall.dtos.dealer_api.DealerApi;
import core.commons.apicall.dtos.rapipago.CellphoneRecharge;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class LastMovementsSteps {

    public static Util util = Apps.util;
    public CommonSteps commonSteps = new CommonSteps();
    private static NubiWallet nubiWallet = Android.nubiWallet;
    private static final Logger logger = LogManager.getLogger();


    /**
     * Variables para guardar y verficar los ultimos movimientos
     */

    private static String transctionDescription;
    private static String transctionAmount;
    private static String transctionDate;
    private static String transctionHour;
    private static String transactionFees;
    private static String transactionStatus;
    private static String transactionFeeAmount;
    private static String transactionOriginalAmount;


//=========== ENCAPSULAMIENTO =================
    public static String getTransactionFeeAmount() {
        return transactionFeeAmount;
    }

    public static void setTransactionFeeAmount(String transactionFeeAmount) {
        LastMovementsSteps.transactionFeeAmount = transactionFeeAmount;
    }

    public static String getTransactionOriginalAmount() {
        return transactionOriginalAmount;
    }

    public static void setTransactionOriginalAmount(String transactionOriginalAmount) {
        LastMovementsSteps.transactionOriginalAmount = transactionOriginalAmount;
    }

    public static String getTransactionFees() {
        return transactionFees;
    }

    public static void setTransactionFees(String transactionFees) { LastMovementsSteps.transactionFees = transactionFees; }

    public static String getTransactionDescription() {
        return transctionDescription;
    }

    public static void setTransctionDescription(String transctionDescription) { LastMovementsSteps.transctionDescription = transctionDescription; }

    public static String getTransctionAmount() {
        return transctionAmount;
    }

    public static void setTransactionAmount(String transctionAmount) { LastMovementsSteps.transctionAmount = transctionAmount; }

    public static String getTransctionDate() {
        return transctionDate;
    }

    public static void setTransactionStatus(String transactionStatus){
        LastMovementsSteps.transactionStatus = transactionStatus;
    }

    public static String getTransactionStatus(){
        return transactionStatus;
    }
    public static void setTransctionDate(String transctionDate) {
        LastMovementsSteps.transctionDate = transctionDate;
    }

    public static String getTransctionHour() {
        return transctionHour;
    }

    public static void setTransctionHour(String transctionHour) {
        LastMovementsSteps.transctionHour = transctionHour;
    }

    /**
     * =========================================================================
     */



    /**
     * GENERAR MOVIMIENTO
     * @param movementType
     * @param withTaxes
     * @param amountFromStep
     * @throws ParseException
     */
    @Then("Generar movimiento de tipo {string}, estatus {string}, {string} y monto {string}")
    public void generar_mov(String movementType, String status, String withTaxes, String amountFromStep) throws ParseException, Exception {
        try {

            String transactionType = "", auxDescription="";

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es","AR"));
            SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm aa");

            switch (movementType.toUpperCase()){
                case "EXTRACCION_TP":
                    Dealer_API dealer_api = new Dealer_API();

                    transactionType = Dealer_API.CASHOUT_WITHDRAW_GP;

                    //make the movement
                    double amount = Double.parseDouble(amountFromStep);

                    if ( dealer_api.call_CashoutGP(transactionType, status, NubiWallet.usuario.getId(), withTaxes, String.valueOf(amount)) == null ) {
                        throw new Exception("Fallo llamada al dealer API para generar transaccion de extraccion tarjeta prepaga");
                    }else{

                        Login.usuario.refreshTransactionsLists();

                        //Set transaction data
                        setTransctionDescription(PrepaidCardDetailPage.TRANSACTION_DESCRIPTION_TEXT);
                        setTransctionDate(dateFormat.format(Login.usuario.getOutgoingTransactions().get(0).getCreatedAt()));
                        setTransctionHour(hourFormat.format(Login.usuario.getOutgoingTransactions().get(0).getCreatedAt()));
                        setTransactionAmount(String.valueOf(Login.usuario.getOutgoingTransactions().get(0).getAmount()).replace(".",","));

                        if(withTaxes.toUpperCase().contentEquals("CON_IMPUESTO")) {
                            ArrayList taxes = Login.usuario.getOutgoingTransactions().get(0).getTaxes();
                            Double auxFees = 0.00;
                            String strFees = "";
                            Document auxDoc = new Document();
                            for (int i = 0; i < taxes.size(); i++) {
                                auxDoc = (Document) taxes.get(i);
                                strFees = String.valueOf(auxDoc.get("amount"));
                                auxFees = auxFees + Double.parseDouble(strFees) / 100;
                            }

                            setTransactionOriginalAmount(String.valueOf(Login.usuario.getOutgoingTransactions().get(0).getOriginalAmount()).replace(".", ","));
                            setTransactionFees(String.valueOf(Apps.util.formatearDecimales(auxFees, 2)).replace(".", ","));
                        }
                    }

                    //Set transaction status
                    if(!status.contentEquals("")){
                        setTransactionStatus(status);
                    }

                    break;
                case "CARGO":
                case "CONSUMO":
                case "SUSCRIPCION":
                    transactionType = Dealer_API.CASHOUT_AUTH_GP;
                    //setTransctionDescription("Pagaste");
                    auxDescription = "Pagaste";
                    break;
                case "RECARGA_CELULAR":
                   setTransctionDescription("Recarga de celular");
                    break;

            }

            if(movementType.toUpperCase().contentEquals("RECARGA_CELULAR")){
                Rapipago_API rapipago_api = new Rapipago_API();
                CellphoneRecharge cellphoneRecharge = rapipago_api.cellphone_recharge(Login.usuario.getPhoneNumber().substring(4,14), RecargaCelular.getCompany(), amountFromStep+"00");

                setTransactionAmount(amountFromStep+",00");

               //Date date = new Date();
                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es","AR"));
               // SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm 'a. m.'");
                setTransctionDate(dateFormat.format(date));
                setTransctionHour(hourFormat.format(date));


            }else if(movementType.toUpperCase().contentEquals("SUSCRIPCION")){

                Dealer_API dealer_api = new Dealer_API();
                JSONObject response=null;
                JSONObject params;

                double amount = Double.parseDouble(amountFromStep);
                DealerApi dealerApi = dealer_api.call_CashoutGP_Suscriptions(transactionType, status, Login.usuario.getId(), withTaxes, String.valueOf(amount));

                JSONParser parser = new JSONParser();
                JSONObject jObject = (JSONObject)dealerApi.getParams();
                JSONObject additionalData = (JSONObject) parser.parse(jObject.get("additionalData").toString());
                DecimalFormat decimalFormat = new DecimalFormat("#.00");

                String original_amount = "";
                JSONObject commerce = null;

                JSONArray fees=null;

                if(withTaxes.toUpperCase().contentEquals("CON_IMPUESTO")){
                    //get fees
                    fees =  (JSONArray)additionalData.get("fees");

                    //Get original amount, formated to 2 decimals
                    original_amount = additionalData.get("original_amount").toString();

                    //get commerce name
                    commerce = (JSONObject)additionalData.get("commerce");
                }

             //Set transacctin description
                setTransctionDescription(auxDescription + " " + commerce.get("name").toString());

                double auxfeeAmount=0.0;
                if(fees!=null){

                    //JSONArray auxFees = (JSONArray) additionalData.get("fees");
                    for(Object fee : fees){
                        auxfeeAmount+=Double.parseDouble(((JSONObject) fee).get("amount").toString());
                    }

                    setTransactionFeeAmount(String.valueOf(decimalFormat.format(auxfeeAmount)));
                    double sum = Double.parseDouble(original_amount.replace(",","."))+auxfeeAmount;
                    setTransactionAmount(String.valueOf(decimalFormat.format(sum)));
                }else {
                    setTransactionAmount(String.valueOf(decimalFormat.format(Double.parseDouble(original_amount))));
                }

                if(movementType.toUpperCase().contentEquals("CARGO")){
                    double auxFee = amount%3;
                    String  fe = String.format("%.2f",auxFee).replace(",",".");
                    response = dealer_api.call_cashoutGP_fee(jObject.get("transactionId").toString(), nubiWallet.usuario.getId(), fe);
                    if(response!= null){
                      setTransactionFeeAmount(fe);
                    }
                }

                //Date date = new Date();
                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es","AR"));
               //SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm 'a. m.'");
                setTransctionDate(dateFormat.format(date));
                setTransctionHour(hourFormat.format(date));
            }else {
               /* Dealer_API dealer_api = new Dealer_API();
                JSONObject response;
                JSONObject params;

                double amount = Double.parseDouble(amountFromStep);
                response = dealer_api.call_CashoutGP(transactionType, status, nubiWallet.usuario.getId(), withTaxes, String.valueOf(amount));
                if ( response != null ) {
                    params = (JSONObject) response.get("params");
                    if (params != null) {
                        JSONParser jsonParser = new JSONParser();
                        JSONObject additionalData = (JSONObject) jsonParser.parse(params.get("additionalData").toString());
                        if(withTaxes.toUpperCase().contentEquals("CON_IMPUESTO")){
                            if (additionalData != null) {
                                JSONArray fees = (JSONArray) additionalData.get("fees");
                                Double auxFees=0.00;
                                for (Object fee : fees) {
                                    amount = amount + Double.parseDouble(((JSONObject) fee).get("amount").toString());
                                    auxFees = auxFees + Double.parseDouble(((JSONObject) fee).get("amount").toString());
                                }
                                amount = util.formatearDecimales(amount,2);
                                setTransactionFees(util.formatearDecimales(auxFees, 2).toString());
                        }
                        }

                        if(movementType.toUpperCase().contentEquals("CARGO")){
                            double auxFee = amount%3;
                            String  fe = String.format("%.2f",auxFee).replace(",",".");
                            response = dealer_api.call_cashoutGP_fee(params.get("transactionId").toString(), nubiWallet.usuario.getId(), fe);
                            if(response!= null){
                                setTransactionFeeAmount(fe);
                            }
                        }

                        if(!status.contentEquals("")){
                            setTransactionStatus(status);
                        }
                    }
                }

                setTransactionAmount(String.valueOf(amount));
                //Date date = new Date();
                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es","AR"));
                //SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm 'a. m.'");
                setTransctionDate(dateFormat.format(date));
                setTransctionHour(hourFormat.format(date));*/
            }




        }catch (Exception | Error err){
            err.printStackTrace();
            throw err;
        }
    }


    @Then("Validar movimiento {string}, {string} en {string} y {string} con status {string}")
    public void validar_movimiento(String tipoMovimiento, String tipoImpuesto, String tipoValidacion, String amount, String status) throws Exception{
        try{

            //Para validar todo movimiento, menos cancelado
            HashMap<String, MobileElement> transaction = null;

            DashboardPage dashboardPage;

            if(tipoMovimiento.toUpperCase().contentEquals("P2P_NUBI_REQ_PENDING")){

                dashboardPage = DashboardPage.getInstance();

                //Go to send request List
                MenuPage.getInstance().clickBtnMenu();
                MenuPage.getInstance().clickBtnSolicitudes();

                //Click to sended tab
                RequestsLandingPage requestsLandingPage = RequestsLandingPage.getInstance();
                requestsLandingPage.click_sended_request();

                //Refresh origin user transactions list in order to get the last one
                CommonStepsMovements.originUser.refreshTransactionsLists();

                //Set the first transaction, ordered BY desc
                List<Transaction> ts1 = CommonStepsMovements.originUser.getOutgoingTransactions();

                Wrapper.waitForElementVisibility(requestsLandingPage.getPendingTitle());

                //Sended request list must be >= 1 because we recently made one
                Assert.assertTrue(requestsLandingPage.getInitials().size() >= 1);

                //Get list of initial icons to iterate over the request list
                List<MobileElement> elements = requestsLandingPage.getInitials();

                String fullName = CommonStepsMovements.destinationUser.getName() + CommonStepsMovements.destinationUser.getLastName();

                for(MobileElement element : elements){

                    //The first element of the list must match with the recent transaction data
                    //Then, break the loop
                    Assert.assertEquals(fullName.replace(" ", ""),
                            Wrapper.getElementText(requestsLandingPage.getItemTitle().get(0)).replace(" ", ""));

                    Assert.assertEquals(fullName.replace(" ", ""), ts1.get(0).getDestinationDescription().replace(" ", ""));
                    Assert.assertEquals("$" + CommonStepsMovements.amountToString, Wrapper.getElementText(requestsLandingPage.getItemAmount().get(0)));

                    //Should exist a cancel button
                    Assert.assertTrue(Wrapper.elementExists(requestsLandingPage.getCancelAction().get(0)));
                    break;

                }
            }else if(!tipoMovimiento.contentEquals("")){
                dashboardPage = DashboardPage.getInstance();
                while(!Wrapper.elementExists(dashboardPage.getSeeMoreButton()))
                    Apps.util.scrollTo();

                //Para validar cuando el movimiento es cancelado, se necesitan
                //validar 2 movimientos
                Map<Integer, Map<Integer, MobileElement>> listTransaction = null;

                //Se valida el movimiento en el dashboard
                transaction = assertRecentTransactionInMovements(tipoMovimiento, status);

                switch (tipoValidacion.toUpperCase()){
                    case "LISTA_MOVIMIENTO":
                        //Tap en link ultimos movimientos, que lleva al listado de ultimos movimientos

                        while(!Wrapper.elementExists(dashboardPage.getSeeMoreButton()))
                            Apps.util.scrollTo();

                        dashboardPage.clickSeeMoreButton();

                        //Last movements list page
                        LastMovementsListPage lMovementsLanding = LastMovementsListPage.getInstance();

                        Assert.assertTrue(lMovementsLanding.getTransactions() > 0);

                        //Validamos que exista el movimiento en la lista
                        assertSpecificTransaction(tipoMovimiento, status);

                        if(status.toUpperCase().contentEquals("CANCELADO")){
                            switch (tipoMovimiento.toUpperCase()){
                                case "EXTRACCION_TP":
                                    assertSpecificTransaction("devolucion_extraccion_tp", "");
                                    break;
                            }
                        }

                        break;
                    case "DETALLE_MOVIMIENTO":

                        while(!Apps.wrapper.elementExists(dashboardPage.getSeeMoreButton()))
                            Apps.util.scrollTo();

                        transaction.get("description").click();

                        //Metodo que valida el detalle
                        assertLastMovementDetailsElements(tipoMovimiento, tipoImpuesto, amount, status);

                        if(status.toUpperCase().contentEquals("CANCELADO")){
                            commonSteps.go_to_dashboard();
                            while(!Apps.wrapper.elementExists(dashboardPage.getSeeMoreButton()))
                                Apps.util.scrollTo();

                            MobileElement element = (MobileElement) Android.driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]"));
                            element.click();

                            switch (tipoMovimiento.toUpperCase()){
                                case "EXTRACCION_TP":
                                    setTransctionDescription("Te devolvimos del retiro");
                                    assertLastMovementDetailsElements("devolucion_extraccion_tp", tipoImpuesto, amount, "");
                                    break;
                            }

                        }
                        break;
                    default:
                        break;
                }
            }

        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    @Then("Validar movimiento de suscripcion {string}, {string} en {string} y {string} con nombreComercio {string}")
    public void validar_movimiento_suscripcion(String tipoMovimiento, String tipoImpuesto, String tipoValidacion, String amount, String nombreComercio) throws Exception{
        try{

            //Para validar todo movimiento, menos cancelado
            HashMap<String, MobileElement> transaction = null;

            //Para validar cuando el movimiento es cancelado, se necesitan
            //validar 2 movimientos
            Map<Integer, Map<Integer, MobileElement>> listTransaction = null;

            //Se valida el movimiento en el dashboard
            transaction = assertRecentTransactionInMovements(tipoMovimiento, "");

            switch (tipoValidacion.toUpperCase()){
                case "LISTA_MOVIMIENTO":
                    //Tap en link ultimos movimientos, que lleva al listado de ultimos movimientos
                    tapOnLastMovementsLink();
                    LastMovementsListPage lMovementsLanding = LastMovementsListPage.getInstance();

                    //Validamos que exista el movimiento en la lista
                    assertSpecificTransaction(tipoMovimiento, "");

                    break;
                case "DETALLE_MOVIMIENTO":
                    transaction.get("description").click();

                    //Metodo que valida el detalle
                    assertLastMovementDetailsElements(tipoMovimiento, tipoImpuesto, amount, "");

                    break;
                default:
                    break;
            }

            /********
             * VALIDAR ACA NOMBRE DEL COMERCIO
             **********/
            if(nombreComercio.length()>=24){
               /* MovementDetailBasePage detailBasePage = MovementDetailBasePage.getInstance();

                if((nombreComercio.substring(24,24) == " ") || (nombreComercio.substring(23,23) == " ") ){
                    Assert.assertEquals(nombreComercio.substring(0,23), detailBasePage.get_element_text(detailBasePage.getTransactionDescription()));
                }
                else Assert.assertEquals(nombreComercio.substring(0,24), detailBasePage.get_element_text(detailBasePage.getTransactionDescription()));
*/
            }


        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    /**
     * Nos permite verificar que una transaccion aparezca con los detalles correctos
     * desde el apartado "ultimos movimientos" del dashboard
     * @param transactionType - el tipo de movimiento que se hizo (ej:SUBE, Envio, Solicitud, etc)
     */

    @Then("Se valida que exista el ultimo movimiento de tipo {string} y estado {string} que acabamos de realizar")
    public HashMap<String, MobileElement> assertRecentTransactionInMovements(String transactionType, String status) throws NoSuchElementException {

        HashMap<String, MobileElement> returnTransaction = null;
        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();
            while(!dashboardPage.elementExists(dashboardPage.getRecentTransactionsTitle())){
                Apps.util.scrollUp();
            }


            // Validacion de UI
            HashMap<Integer, HashMap<String, MobileElement>> uiTransactions = dashboardPage.getMovements();
            for ( HashMap<String, MobileElement> transaction : uiTransactions.values() ) {
                if ( transaction.get("description").getText().contains(getTransactionText(transactionType.toLowerCase())) ) {
                        String
                            auxAmount = (transactionType.toUpperCase().contentEquals("CARGO")) ? getTransactionFeeAmount() : getTransctionAmount(),
                            auxDate = getTransctionDate();

                    Assert.assertTrue(transaction.get("amount").getText().contains(auxAmount));
                    Assert.assertEquals(auxDate.substring(0,getTransctionDate().length()-5), transaction.get("date").getText());

                    if(!status.contentEquals("")){
                        Assert.assertEquals(status.toUpperCase(), getTransactionStatus().toUpperCase());
                    }
                    returnTransaction = transaction;
                    break;
                }
            }


            // Validacion de BD
            //Login.usuario.refreshTransactionsLists();
            /*
            // Validacion de BD
            Login.usuario.refreshTransactionsLists();
            List<Transaction> transactionsOutgoing = Login.usuario.getOutgoingTransactions();
            for ( Transaction transaction : transactionsOutgoing ) {
                if ( transaction.getTransactionType().equals(dbTransactionType)) {
                    Assert.assertTrue(getTransctionAmount().equals(transaction.getAmount()));
                    break;
                }
            }

            List<Transaction> transactionsIncoming = Login.usuario.getIncomingTransactions();
            for ( Transaction transaction : transactionsIncoming ) {
                if ( transaction.getTransactionType().equals(dbTransactionType)) {
                    Assert.assertTrue(getTransctionAmount().equals(transaction.getAmount()));
                    break;
                }
            }


             */

        }
        catch (Exception e){
            e.printStackTrace();
            throw new NoSuchElementException();
        }

        return returnTransaction;
    }

    public Map<Integer, Map<Integer, AndroidElement>> asser_canceled_movement(String transactionType, String status) throws InterruptedException, Exception{
        Map<Integer, Map<Integer, AndroidElement>> returnTransactions = null;

        Map<Integer, AndroidElement> transaction = null;

        try{
            DashboardPage dashboardPage = DashboardPage.getInstance();

            while(!dashboardPage.elementExists(dashboardPage.getRecentTransactionsTitle())) util.scrollUp();

            //Validacion de las transacciones en el dashboard
            int dashboardTransactions = Android.driver.findElements(By.xpath("//androidx.recyclerview.widget.RecyclerView/" + TextList.CLASSNAME_VIEW_GROUP.getText())).size();

            //Objetos de la transaction
            AndroidElement
                    icon = null,
                    description = null,
                    amount = null,
                    auxStatus = null,
                    date = null;


            String xpath;
            for(int i = 0; i<dashboardTransactions; i++){
                //Inicializamos los objetos
                try{
                    /*switch (i){
                        case 0:
                            xpath = "//androidx.recyclerview.widget.RecyclerView/" + TextList.CLASSNAME_VIEW_GROUP.getText() + "[" + (i+1) + "]//android.widget.ImageView[1]";
                            icon = (AndroidElement)Android.driver.findElement(By.xpath(xpath));
                            break;
                        case 1:
                            break;
                    }*/

                    //iCON
                    xpath = "//androidx.recyclerview.widget.RecyclerView/" + TextList.CLASSNAME_VIEW_GROUP.getText() + "[" + (i+1) + "]//android.widget.ImageView[1]";
                    icon = (AndroidElement)Android.driver.findElement(By.xpath(xpath));

                    //description
                    xpath = "//androidx.recyclerview.widget.RecyclerView/" + TextList.CLASSNAME_VIEW_GROUP.getText() + "[" + (i+1) + "]//" + TextList.CLASSNAME_TEXT_VIEW.getText() + "[1]";
                    description = (AndroidElement)Android.driver.findElement(By.xpath(xpath));

                    //amount
                    xpath = "//androidx.recyclerview.widget.RecyclerView/" + TextList.CLASSNAME_VIEW_GROUP.getText() + "[" + (i+1) + "]//" + TextList.CLASSNAME_TEXT_VIEW.getText() + "[2]";
                    description = (AndroidElement)Android.driver.findElement(By.xpath(xpath));

                    //date
                    xpath = "//androidx.recyclerview.widget.RecyclerView/" + TextList.CLASSNAME_VIEW_GROUP.getText() + "[" + (i+1) + "]//" + TextList.CLASSNAME_TEXT_VIEW.getText() + "[1]";
                    description = (AndroidElement)Android.driver.findElement(By.xpath(xpath));

                }catch (Exception er){
                    continue;
                }

            }
            HashMap<Integer, HashMap<String,  MobileElement>> uiTransactions = dashboardPage.getMovements();


        }catch (Exception | Error er){
            logger.error(er);
            throw er;
        }

        return null;
    }

    public String getTransactionText(String transactionType){

        String transactionText = "";
        String dbTransactionType = "";
        switch (transactionType){
            case "sube":
                transactionText = getTransactionDescription();
                dbTransactionType = "SUBE_CHARGE";
                break;
            case "p2p_nubi_send":
            case"envio":
                transactionText = getTransactionDescription();
                dbTransactionType = "P2P_SENDING";
                break;
            case"pago_de_servicio":
                transactionText = getTransactionDescription();
                dbTransactionType = "SERVICE_PAYMENT";
                break;
            case "extraccion_tp_rc":
            case"extraccion_tp":
                transactionText = getTransactionDescription();
                dbTransactionType = "CASHOUT_WITHDRAW_PC";
                break;
            case "consumo":
                transactionText = "Pagaste";
                dbTransactionType = "CASHOUT_AUTH_PC";
                break;
            case "cargo":
                transactionText = "Pagaste";
                dbTransactionType = "";
                break;
            default:
                break;
        }

        return transactionText;
    }



    @And("Se pulsa sobre el ultimo movimiento de tipo {string} que acabamos de realizar con status {string}")
    public void tapOnLastMovement(String transactionType, String status) {

        HashMap<String, MobileElement> transaction = assertRecentTransactionInMovements(transactionType, status);
        try {
            transaction.get("icon").click();
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new AssertionError("No se puedo pulsar sobre el movimiento" + transactionType + "buscado");
        }
    }

    @When("Presiona sobre ultimos movimientos")
    public void tapOnLastMovementsLink() {
        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();

            while(!Apps.wrapper.elementExists(dashboardPage.getSeeMoreButton()))
                Apps.util.scrollTo();

            dashboardPage.clickSeeMoreButton();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Validar que existe el ultimo movimiento en la pantalla
     * "ultimos movimientos"
     * @param type: el tipo de transaccion que se realizó
     */
    @Then("Verifica si el movimiento {string} con estado {string} se encuentra en la lista")
    public void assertSpecificTransaction(String type, String status) throws Exception{

        boolean existTransaction;
        String transactionType;
        LastMovementsListPage lastMovementsLanding = LastMovementsListPage.getInstance();

        try {
            switch (type.toLowerCase()){
                case "recarga_sube":
                    transactionType = "Cargaste SUBE";
                    break;
                case "pago_de_servicio":
                case "recepcion_de_dinero":
                    transactionType = "Recepci\u00F3n de dinero";
                    break;
                case "envio_de_dinero":
                    transactionType = "Envío de dinero";
                    break;
                case "consumo":
                    transactionType = "Recepci\u00F3n de dinero";
                    break;
                case "extraccion_tp":
                    transactionType = "Retiraste";
                    break;
                case "recarga_celular":
                    transactionType = "Recarga de celular";
                    break;
                case "devolucion_extraccion_tp":
                    transactionType = "Extraccion";
                    setTransctionDescription("Te devolvimos del retiro");
                    break;
                default:
                    transactionType = null;
                    break;
            }

            existTransaction = lastMovementsLanding.
                    getMovementInformation(status,transactionType,
                            getTransactionDescription(),
                            getTransctionAmount(),
                            getTransctionDate().substring(0, getTransctionDate().length()-5));

            Assert.assertTrue(existTransaction);
        }
        catch (Exception e){
            e.printStackTrace();
            //Assert.fail("La transacción no fue encontrada en la lista de ultimos movimientos");
        }
    }


    /**
     * Nos permite validar los elementos y valores de la pantalla detalle de movimiento segun el tipo de transaccion
     * que se haya realizado (sube, envios, solicitudes, recarga de celulares, etc)
     * @param transaction para indicar que tipo de transaccion se realizó
     * @throws Exception en caso de que no exista algun elemento o que un valor sea distinto
     */
    @Then("Se valida los elementos de la pantalla detalle de movimiento {string}, con impuesto {string}, con monto {string} y estado {string}")
    public void assertLastMovementDetailsElements(String transaction, String taxable, String rawAmount, String status) throws Exception{
        String transactionAmount;

        if(!transaction.toUpperCase().contentEquals("CARGO"))
            transactionAmount = "$" + getTransctionAmount().replace(".", ",");
        else
            transactionAmount = "$" + getTransactionFeeAmount().replace(".", ",");


        try {

            Login.usuario.refreshTransactionsLists();

            Transaction ts1;

            switch (transaction.toLowerCase()) {
                case "sube_recharge":

                    SubeCardDetailPage sc_detail = SubeCardDetailPage.getInstance();

                    //Map for card and operation details
                    HashMap<String, String> operationdetails = new HashMap<>();

                    ts1 = Login.usuario.getOutgoingTransactions().get(0);

                    operationdetails.put("sube_number", ts1.getSubeCardNumber());
                    operationdetails.put("alias", ts1.getCard_alias());
                    operationdetails.put("id", ts1.getSubeTransactionId());
                    operationdetails.put("operation", ts1.getOperationCode());

                    //Asserts
                    Assert.assertEquals(SubeCardDetailPage.TITLE_TEXT,Wrapper.getElementText(sc_detail.getTransactionDetailTitle()));
                    Assert.assertTrue(Wrapper.elementExists(sc_detail.getTransactionIcon()));
                    Assert.assertEquals(getTransactionDescription(), Wrapper.getElementText(sc_detail.getTransactionDescription()).replace("\n", " "));
                    Assert.assertEquals("$"+getTransctionAmount()+"0",  Wrapper.getElementText(sc_detail.getTransactionAmount()));
                    Assert.assertTrue(Wrapper.getElementText(sc_detail.getDateTimeLabel()).replace("\n", " ").contains(getTransctionDate().toUpperCase()));
                    if(!NubiWallet.mode.equals(Util.BROWSERSTACK))
                        Assert.assertTrue(Wrapper.getElementText(sc_detail.getDateTimeLabel()).replace("\n", " ").contains(getTransctionHour().toUpperCase().substring(0, getTransctionHour().length()-2)));
                    Assert.assertTrue(Wrapper.elementExists(sc_detail.getCategoryIcon()));
                    Assert.assertTrue(Wrapper.elementExists(sc_detail.getCategoryName()));

                    while (!Wrapper.elementExists(sc_detail.getDisclaimerText()))
                        Apps.util.scrollTo();

                    Assert.assertEquals(SubeCardDetailPage.DISCLAIMER_SUBE_TEXT, Wrapper.getElementText(sc_detail.getDisclaimerText()).replace("\n", " "));

                    while(sc_detail.getItemValue().size()<4)
                        Apps.util.scrollTo();

                    for(int i = 0; i < sc_detail.getItemValue().size(); i++){
                        switch (i){
                            case 0:
                                Assert.assertEquals(operationdetails.get("sube_number"), Wrapper.getElementText(sc_detail.getItemValue().get(i)).replace(" ", ""));
                                break;
                            case 1:
                                Assert.assertEquals(operationdetails.get("alias"), Wrapper.getElementText(sc_detail.getItemValue().get(i)).replace(" ", ""));
                                break;
                            case 2:
                                Assert.assertEquals(operationdetails.get("id"), Wrapper.getElementText(sc_detail.getItemValue().get(i)).replace(" ", ""));
                                break;
                            case 3:
                                Assert.assertEquals(operationdetails.get("operation"), Wrapper.getElementText(sc_detail.getItemValue().get(i)).replace(" ", ""));
                                break;
                        }
                    }

                 break;
                case "pago_de_servicio":
                    break;
                case "envio":
                case "p2p_nubi_send":
                    P2PSendDetailPage detailPage = P2PSendDetailPage.getInstance();

                    ts1 = Login.usuario.getOutgoingTransactions().get(0);

                    //Asserts
                    Assert.assertEquals(P2PSendDetailPage.TITLE_TEXT, Wrapper.getElementText(detailPage.getTransactionDetailTitle()));
                    Assert.assertEquals(getTransactionDescription(), Wrapper.get_element_attribute(detailPage.getTransactionIcon(), "content-desc"));
                    Assert.assertTrue(Wrapper.elementExists(detailPage.getTransactionIcon()));
                    Assert.assertEquals(getTransactionDescription(), Wrapper.getElementText(detailPage.getTransactionDescription()));
                    Assert.assertEquals("$" + getTransctionAmount(), Wrapper.getElementText(detailPage.getTransactionAmount()));
                    Assert.assertTrue(Wrapper.getElementText(detailPage.getDateTimeLabel()).contains(getTransctionDate().toUpperCase()));
                    //if(!NubiWallet.mode.equals(Util.BROWSERSTACK))
                        //Assert.assertTrue(Wrapper.getElementText(detailPage.getDateTimeLabel()).contains(getTransctionHour().toUpperCase().substring(0, getTransctionHour().length()-2)));
                    Assert.assertTrue(Wrapper.elementExists(detailPage.getCategoryIcon()));
                    Assert.assertTrue(Wrapper.elementExists(detailPage.getCategoryName()));
                    Assert.assertTrue(Wrapper.elementExists(detailPage.getEditCategory()));
                    break;
                case "solicitud":
             //       Assert.assertTrue(nubiWallet.lastMovements.lastMovementsDetail.getTransactionDescription().contains(getTransctionDescription()));
             //       Assert.assertEquals(TextList.TEXT_CATEGORY_NAME_OTROS.getText(), nubiWallet.lastMovements.lastMovementsDetail.getCategoryIcon());
             //       Assert.assertEquals(TextList.TEXT_CATEGORY_NAME_OTROS.getText(), nubiWallet.lastMovements.lastMovementsDetail.getCategoryName());
                    break;
                case "cargo":
                case "consumo":
                case "extraccion_tp":

                    PrepaidCardDetailPage pc_detail = PrepaidCardDetailPage.getInstance();

                    ts1 = Login.usuario.getOutgoingTransactions().get(0);

                    //Asserts
                    Assert.assertEquals(PrepaidCardDetailPage.TITLE_TEXT, Apps.wrapper.getElementText(pc_detail.getTransactionDetailTitle()));
                    Assert.assertTrue(Apps.wrapper.elementExists(pc_detail.getTransactionIcon()));
                    Assert.assertEquals(getTransactionDescription(), Apps.wrapper.getElementText(pc_detail.getTransactionDescription()).replace("\n", " "));
                    Assert.assertEquals("$"+getTransctionAmount(),  Apps.wrapper.getElementText(pc_detail.getTransactionAmount()));
                    Assert.assertTrue(Apps.wrapper.getElementText(pc_detail.getDateTimeLabel()).replace("\n", " ").contains(getTransctionDate().toUpperCase()));
                    Assert.assertTrue(Apps.wrapper.getElementText(pc_detail.getDateTimeLabel()).replace("\n", " ").contains(getTransctionHour().toUpperCase().substring(0, getTransctionHour().length()-2)));
                    Assert.assertTrue(Apps.wrapper.elementExists(pc_detail.getCategoryIcon()));
                    Assert.assertTrue(Apps.wrapper.elementExists(pc_detail.getCategoryName()));

                    if (status.toUpperCase().contentEquals("RECHAZADO")){
                        while (!Apps.wrapper.elementExists(pc_detail.getDisclaimerText()))
                            Apps.util.scrollTo();

                        Assert.assertEquals(PrepaidCardDetailPage.DISCLAIMER_REJECTED_TEXT, Apps.wrapper.getElementText(pc_detail.getDisclaimerText()).replace("\n", " "));
                    }

                    if (status.toUpperCase().contentEquals("CANCELADO")){
                        while (!Apps.wrapper.elementExists(pc_detail.getDisclaimerText()))
                            Apps.util.scrollTo();

                        Assert.assertEquals(PrepaidCardDetailPage.DISCLAIMER_CANCELED_TEXT, Apps.wrapper.getElementText(pc_detail.getDisclaimerText()).replace("\n", " "));
                    }

                    if(taxable.toUpperCase().contentEquals("CON_IMPUESTO")){

                        while (!Apps.wrapper.elementExists(pc_detail.getItemValue()))
                            Apps.util.scrollTo();

                        //Asserts
                        Assert.assertEquals("$" + getTransactionOriginalAmount(), Apps.wrapper.getElementText(pc_detail.getItemValue()));

                    }

                    break;
                case "devolucion_extraccion_tp":
                    pc_detail = PrepaidCardDetailPage.getInstance();

                    //Asserts
                    Assert.assertEquals(PrepaidCardDetailPage.TITLE_TEXT, Apps.wrapper.getElementText(pc_detail.getTransactionDetailTitle()));
                    Assert.assertTrue(Apps.wrapper.elementExists(pc_detail.getTransactionIcon()));
                    Assert.assertEquals(getTransactionDescription(), Apps.wrapper.getElementText(pc_detail.getTransactionDescription()).replace("\n", " "));
                    Assert.assertEquals("$"+getTransctionAmount(),  Apps.wrapper.getElementText(pc_detail.getTransactionAmount()));
                    Assert.assertTrue(Apps.wrapper.getElementText(pc_detail.getDateTimeLabel()).replace("\n", " ").contains(getTransctionDate().toUpperCase()));
                    Assert.assertTrue(Apps.wrapper.getElementText(pc_detail.getDateTimeLabel()).replace("\n", " ").contains(getTransctionHour().toUpperCase().substring(0, getTransctionHour().length()-2)));
                    Assert.assertTrue(Apps.wrapper.elementExists(pc_detail.getCategoryIcon()));
                    Assert.assertTrue(Apps.wrapper.elementExists(pc_detail.getCategoryName()));

                    //Assert disclaimer text
                    Assert.assertTrue(Apps.wrapper.getElementText(pc_detail.getDisclaimerText()).replace("\n", " ").contains(PrepaidCardDetailPage.DISCLAIMER_REFUND_TEXT));
                    break;
            }


        }catch (Exception error){
            logger.error(error.getMessage());
            throw new Exception("Uno o mas elementos de la pantalla detalle de movimiento no coincideron con lo esperado");
        }
    }

    @Then("Se edita la categoria de movimiento")
    public void editarCategoriaUltimoMovimiento() {
       // nubiWallet.lastMovements.lastMovementsDetail.waitToLoadScreen();
        //categorias = nubiWallet.lastMovements.lastMovementsDetail.tapCategoryName();
        //categorias.waitToLoadScreen();
    }
}
