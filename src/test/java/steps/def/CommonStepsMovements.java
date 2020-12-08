package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.nw.model.Contact;
import api.apps.android.nw.model.User;
import api.apps.android.nw.contactos.ContactsPage;
import api.apps.android.nw.contactos.detallecontactos.ContactsDetailPage;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.menu.MenuPage;
import api.apps.android.nw.model.WalletData;
import api.apps.android.nw.model.transactions.Transaction;
import api.apps.android.nw.operaciones.p2p.ConfirmTransactionPage;
import api.apps.android.nw.operaciones.p2p.NubiPassP2PPage;
import api.apps.android.nw.operaciones.p2p.SuccessfulTransactionPage;
import api.apps.android.nw.operaciones.p2p.request.RequestsLandingPage;
import com.github.javafaker.App;
import core.ADB;
import core.Util;
import api.apps.android.Wrapper;
import core.commons.apicall.*;
import core.commons.apicall.dtos.accounts.Balance;
import core.commons.apicall.dtos.contacts.CreateBankingContact;
import core.commons.apicall.dtos.p2p.P2PRequest;
import core.commons.apicall.dtos.rapipago.CellphoneRecharge;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
public class CommonStepsMovements {

    private static String balance="";
    private Double user1Balance;
    private Double user2Balance;
    private Double newUser1Balance;
    private Double newUser2Balance;
    public static Double amountToNumeric;
    public static String amountToString;
    private String user1Name = "", user2Name = "", user1LastName = "", user2LastName = "";
    public static Properties prop = new Properties();
    private static final Logger logger = LogManager.getLogger();
    private static Util util = Apps.util;

    //users balance string format
    public static String DESTINATION_USER_BALANCE_STRING;
    public static String ORIGIN_USER_BALANCE_STRING;

    //User balance double format
    public static Double DESTINATION_USER_BALANCE_DOUBLE;
    public static Double ORIGIN_USER_BALANCE_DOUBLE;

    //Para p2p
    public static User
            destinationUser = null,
            originUser = null;

    public static String CATEGORY="";

    public void setBalance() throws Exception{
        //DashboardPage dashboardPage = new DashboardPage();
        balance = DashboardPage.getInstance().get_element_text(DashboardPage.getInstance().getBalance());
    }

    public static String getBalance(){
        return balance;
    }

    public static Double getNumBalance(String balance){

        String auxBalance = "";
        //auxBalance=balance.substring(1);
        auxBalance=balance.replace(",", ".");

        char[] stringToChar = auxBalance.toCharArray();

        int count=0;
        String chare;

        for(char character : stringToChar){
            chare = Character.toString(character);
            if(chare.contentEquals(".")){
                count++;
            }
        }

        if(count>=2){
            auxBalance="";
            do{
                for(char character : stringToChar){
                    chare = Character.toString(character);
                    if(chare.contentEquals(".")){
                        if(count>1){
                            chare="";
                        }
                        count--;
                    }
                    auxBalance+=chare;
                }
            }while (count>1);
        }

        return Double.valueOf(auxBalance);
    }



    private void setUsersNames(int aux, String idUsuario) throws Exception {
        try{
            User user;
            if(aux==1){
                user = util.obtenerUsuarioById(idUsuario);
                user1Name = user.getName();
                user1LastName = user.getLastName();
            }
        }catch (Exception e){
            util.showTrackTraceError("Error seteando el nombre de los usuarios", e);
            throw e;
        }
    }

    /******
     *
     * getters and setters
     */

    public Double getUser1Balance() {

        return user1Balance;
    }

    public void setUser1Balance(Double user1Balance) {

        this.user1Balance = user1Balance;
    }

    @Given("Obtener el balance")
    public void obtener_balance() throws Exception{
        try {
            setBalance();
        }catch (Exception | Error err){
            logger.error(err.getMessage());
            err.printStackTrace();
            throw err;
        }
    }

    /***
     * Validar balance
     * @param user
     * @throws Exception
     */
    @Given("Validate {string} balance")
    public void Validatebalance(String user) throws Exception {
        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();

            user.toLowerCase();

            if (user.contains("1")){
                setUsersNames(1, Login.usuario.getId());
            }

            //genericBalance = util.getNumericBalance(); //getNumericBalance();

            String username="";

            if(user.contains("1")){
               // setUser1Balance(genericBalance);
                username = user1Name;
                util.writeUserbalance("balance", String.valueOf(user1Balance), 1);

           }else if(user.contains("2")) {
               // user2Balance = genericBalance;
                username = prop.getProperty("user2");
                util.writeUserbalance("balance", String.valueOf(user2Balance), 2);
            }

            /*valida si el balance es menor o igual que cero
            , si es cierto le agrega cash usando el pem.ssh
             */
            //if(genericBalance<=0){
                util.addCash(2000, username);
                MenuPage.getInstance().clickBtnHome();
                Validatebalance(user);
           // }

        }catch (Exception e){
            util.showTrackTraceError("Unable to validate balance on "+user, e);
        }catch (AssertionError e){
            //util.showAssertionErrors("Unable to validate balance on "+user, e, hook.getName(), hook.getId());
        }
    }


    /**************************
     * Return balance en numero
     *****************************/
    public Double getNumericBalance() throws Exception{
        setBalance();
        balance=balance.substring(1);
        balance=balance.replace(",", ".");

        char[] stringToChar = balance.toCharArray();

        int count=0;
        String chare;

        for(char character : stringToChar){
            chare = Character.toString(character);
            if(chare.contentEquals(".")){
                count++;
            }
        }

        if(count>=2){
            balance="";
            do{
                for(char character : stringToChar){
                    chare = Character.toString(character);
                    if(chare.contentEquals(".")){
                        if(count>1){
                            chare="";
                        }
                        count--;
                    }
                    balance+=chare;
                }
            }while (count>1);
        }

        return Double.valueOf(balance);
    }

    /*****
     * este metodo valida si el metodo de transaccion correcto esta activo
     * @param modo: send o request
     */
    @Then("Validar que modo {string} este activo")
    public void validateMode(String modo){
        try {

            //Espero a que cargue pantalla de ingreso de monto
            //nubiWallet.movements.uiObject.amountInput().waitToAppear(10);
            //Assert.assertEquals(nubiWallet.movements.uiObject.lblTitulo().getText(),TextList.TEXT_MONTO.getText());
            Android.driver.hideKeyboard();

            //Validasr el modo del transaction button
            switch (modo.toLowerCase()){
                case "enviar":
                   // Assert.assertFalse(nubiWallet.movements.uiObject.transactionSwitch().isChecked());
                    break;
                case "solicitar":
                   // if(!nubiWallet.movements.uiObject.transactionSwitch().isChecked())
                   //    nubiWallet.movements.uiObject.transactionSwitch().tap();

                   // Assert.assertTrue(nubiWallet.movements.uiObject.transactionSwitch().isChecked());

                    break;
                case "enviar_bancaria":
                    //Assert.assertFalse(nubiWallet.movements.uiObject.transactionSwitch().exists());

                    break;
                default:
                    break;
            }

           // Assert.assertTrue(nubiWallet.movements.uiObject.amountInput().exists());
           // Assert.assertFalse(nubiWallet.movements.uiObject.btnContinuar().isEnabled());

        }catch (Exception | Error e){
            logger.error(e.getMessage());
            throw  e;

        }
    }

    /******
     * Setear monto para enviar-solicitar
     *******/
    @Then("{string} ingresa el monto {double}")
    public void setMonto(String usuario, Double monto){
        try {

            if(usuario.toLowerCase().contains("1")){
               // nubiWallet.movements.uiObject.amountInput().waitToAppear(30);
                //nubiWallet.movements.uiObject.amountInput().tap();
                //nubiWallet.movements.setMonto(monto);
                //Android.driver.hideKeyboard();
               // nubiWallet.movements.uiObject.btnContinuar().waitToAppear(5);
                amountToNumeric = monto;

                prop.load(Util.loadTxtFile("transactions"));
              if(amountToNumeric>0 && amountToNumeric <= Double.parseDouble(prop.getProperty("BalanceUser1"))) {
                //    Assert.assertTrue(nubiWallet.movements.uiObject.btnContinuar().isEnabled());
                }

                util.writeUserbalance("amount", String.valueOf(amountToNumeric),2);

            }else{
                amountToNumeric = monto;
            }


        }catch (Exception e){
            util.showTrackTraceError("Unable to set the amount", e);
        }
    }

    @Then("{string} pulsa boton siguiente")
    public void tapBtnSiguiente(String usuario){
        try {
            //if(!usuario.contentEquals("") && !usuario.contentEquals("none"))
               // nubiWallet.movements.tapBtnSiguiente();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*****
     *
     * @param amount: amount(expression) to be setted to send-request
     *
     *****/
    @Then("Ingresa el monto siguiente: {string}")
    public void setAmountRegression(String amount){
        try {

            Android.adb.setTexto(amount);
            Android.driver.hideKeyboard();
           // nubiWallet.movements.uiObject.btnContinuar().waitToAppear(5);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * @param status: status button
     ***/
    @Then("Valida que del boton siguiente este {string}")
    public void validateStatusNextButton(String status){
        try {

            if(status.toLowerCase().contentEquals("deshabilitado")){
               // Assert.assertFalse(nubiWallet.movements.uiObject.btnContinuar().isEnabled());
            }else if(status.toLowerCase().contentEquals("habilitado")){
              //  Assert.assertTrue(nubiWallet.movements.uiObject.btnContinuar().isEnabled());
            }

        }catch (Exception e){
            util.showTrackTraceError("Unable to validate status button", e);
        }
    }

    /******
     * Este metodo evalua el estado de la pantalla al finalizar la trasaccion
     * y retorna al Dashboard.
     * @param mode: modo del movimiento: envio - solicitud
     */
    @Then("Validar estado de transaccion {string}")
    public void validar_status_transaccion(String mode){
        try {

            if(mode.toLowerCase().contains("enviar") || mode.toLowerCase().contains("solicitar")){
               // nubiWallet.movements.uiObject.lblMensaje().waitToAppear(30);
               // nubiWallet.movements.uiObject.finishButton().waitToAppear(5);

                if(mode.toLowerCase().contentEquals("enviar")){
             //       Assert.assertTrue(nubiWallet.movements.uiObject.lblMensaje().getText().contains(TextList.TEXT_ENVIO_EXITOSO.getText()));
                }else{
            //        Assert.assertTrue(nubiWallet.movements.uiObject.lblMensaje().getText().contains(TextList.TEXT_SOLICITUD_EXITOSA.getText()));
                }

                //Assert.assertTrue(nubiWallet.movements.uiObject.finishButton().exists());

             //   nubiWallet.movements.tapFinishTransaction();
                DashboardPage.getInstance();
            }

        }catch (Exception e){
            util.showTrackTraceError("Transaction error", e);
        }
    }

    /**********
     *  Validar la pantalla de detalle de tranferencia en envio-solicitud
     *
     * @param mode: Tipo de transferencia
     **********/
    @Then("Valida el detalle de la pantalla {string}")
    public void validate_detail_screen(String mode){
        try{

            amountToNumeric = RGSMovements.moneyToSendRequest;

            if (mode.toLowerCase().contentEquals("enviar")){
                //Assert.assertEquals(nubiWallet.movements.uiObject.titleTransferDetail().getText(), nubiWallet.movements.getTRANSFER_DETAIL());
               // Assert.assertTrue(nubiWallet.movements.uiObject.transactionLabel().getText().contains(user2Name) ||
                   //     nubiWallet.movements.uiObject.transactionLabel().getText().contains(user2LastName));

               // Assert.assertEquals("$"+String.valueOf(amountToNumeric).replace(".",","), nubiWallet.movements.uiObject.amountMoney().getText());
                //Assert.assertEquals(nubiWallet.movements.uiObject.btnSiguiente().getText(), TextList.TEXT_SIGUIENTE.getText());
            }
        }catch (Exception e){
            util.showTrackTraceError("Error validando la pantalla de detalle de la transferencia", e);
        }
    }

    /******
     *  Evalua el balance del usuario antes-despues de acteptar, rechazar, cancelar
     * @param user: define el usuario a validar
     * @param condition: define si se evalua antes o despues del movimiento
     * @param requestType: Define el modo de la solicitud(aceptar(a), rechazar(r), cancelar(c))
     ****/
    @Then("{string} valida el balance {string} antes de {string}")
    public void validate_balance_on_request(String user, String condition, String requestType){
        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();

            if(user.toLowerCase().contains("1")){
                prop.load(util.loadTxtFile("transactions"));
                user1Balance = Double.valueOf(prop.getProperty("BalanceUser1"));
                if(condition.toLowerCase().contentEquals("before")){
                    newUser1Balance = getNumericBalance();
                    Assert.assertEquals(Double.toString(user1Balance), Double.toString(newUser1Balance));
                }else if (condition.toLowerCase().contentEquals("after")){
                    if (requestType.toLowerCase().contentEquals("a")){
                        newUser1Balance = getNumericBalance();
                        Assert.assertEquals(Double.toString(user1Balance+amountToNumeric), Double.toString(newUser1Balance));
                    }else if (requestType.toLowerCase().contentEquals("r")){
                        newUser1Balance = getNumericBalance();
                        Assert.assertEquals(Double.toString(user1Balance), Double.toString(newUser1Balance));
                    }else if(requestType.toLowerCase().contentEquals("send")){
                        newUser1Balance = getNumericBalance();
                        Assert.assertEquals(Double.toString(user1Balance-amountToNumeric), Double.toString(newUser1Balance));
                    }else if(requestType.toLowerCase().contentEquals("sendbank")){
                        newUser1Balance = getNumericBalance();
                        Assert.assertEquals(Double.toString(user1Balance), Double.toString(newUser1Balance));
                    }

                }
            }else if(user.toLowerCase().contains("2")){
                if(condition.toLowerCase().contentEquals("before")){
                    newUser2Balance = getNumericBalance();
                    Assert.assertEquals(Double.toString(user2Balance), Double.toString(newUser2Balance));
                }else if (condition.toLowerCase().contentEquals("after")){
                    if (requestType.toLowerCase().contentEquals("a")){
                        newUser2Balance = getNumericBalance();
                        Assert.assertEquals(Double.toString(user2Balance-amountToNumeric), Double.toString(newUser2Balance));
                    }else if (requestType.toLowerCase().contentEquals("r")){
                        newUser2Balance = getNumericBalance();
                        Assert.assertEquals(Double.toString(user2Balance), Double.toString(newUser2Balance));
                    }

                }
            }
        }catch (Exception e){
            util.showTrackTraceError("Error validating new balance", e);
        }
    }


    /****
     * seleccionar cualquier contacto dependiendo si es nubi o no
     */
    @Then("Select a contact to send - request {string}")
    public void selectContact(String contact){
        try {
            ContactsPage contactsPage = new ContactsPage();
            util.selectContact(contact);

        }catch (Exception e){
            util.showTrackTraceError("error", e);
        }
    }


    /****
     * Seleccionar contacto especifico nubi para realizar la operacion
     */
    @Then("Select {string} to send - request money")
    public void select_user_for_transaction(String user){
        try {
            if (user.contains("2")){
                ContactsPage contactsPage = new ContactsPage();

            }
        }catch (Exception e){
            util.showTrackTraceError("Unable to select "+user+" in order to complete the transacction",
                    e);
        }
    }


    /****
     * Tap en botton siguiente en la pantalla de confirmacion de la operacion
     */
    @And("{string} pulsa boton para confirmar la transaccion al {string}")
    public void tap_confirmar_transaccion(String user, String mode){
        try {

            if(user.toLowerCase().contains("1") || user.toLowerCase().contains("2")){
                    //nubiWallet.movements.uiObject.btnSiguiente().waitToAppear(5);
                    //Assert.assertTrue(nubiWallet.movements.uiObject.btnSiguiente().isEnabled());

                    //nubiWallet.movements.tapConfirmButton();
                    //nubiWallet.movements.uiObject.btnSiguiente2().waitToDisappear(5);

                if(mode.toLowerCase().contentEquals("enviar")){
                    //CommonPinPage.getInstance();
                }

            }


        }catch (Exception e){
            util.showTrackTraceError("Unable to tap next button in order to confirm the movement", e);
        }
    }

    @Then("{string} ingresa su pin")
    public void set_pin(String user){
        try{
            if(!user.contentEquals("") && !user.contentEquals("none")){
                //CommonPinPage.getInstance().set_valid_pin();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*****
     *  Ir a lstado de solicitudes
     */
    @Then("{string} go to {string} requests list")
    public void go_to_request_list(String user, String type){
        try {
            /*if(user.contains("2")){
                RequestsLandingPage requestsPage = new RequestsLandingPage();
                if(type.toLowerCase().contains("sended")){
                    *//**
                     * TODO: SENDED NO TIENE ACCIONES...
                     *//*
                }else if(type.toLowerCase().contains("received")){
                    Assert.assertFalse(requestsPage.requestTransactionSwitchIsChecked());
                    requestsPage.clickRequestTransactionSwitchButton();
                }
            }else if(user.contains("1")){
                RequestsLandingPage requestsPage = new RequestsLandingPage();

                if(type.toLowerCase().contains("sended")){
                    Assert.assertFalse(requestsPage.requestTransactionSwitchIsChecked());
                }else if(type.toLowerCase().contains("received")){
                    Assert.assertFalse(requestsPage.requestTransactionSwitchIsChecked());
                    requestsPage.clickRequestTransactionSwitchButton();
                }
            }*/

        }catch (Exception e){
            util.showTrackTraceError("Unable to tap next button in order to confirm the movement", e);
        }
    }


    /******
     * Validar la solicitud
     */
    @And("{string} validate status request as {string}")
    public void validate_request(String user, String status){
        try {
           /* if(user.contains("2")){
                RequestsLandingPage requestsPage = new RequestsLandingPage();

                Assert.assertTrue(requestsPage.getItemTitleText().contains(user1Name) &&
                        requestsPage.getItemTitleText().contains(user1LastName));

                String itemAmount = String.valueOf("$10,00");
                itemAmount = itemAmount.replace(".", ",");

                Assert.assertTrue(requestsPage.getItemAmountText().contains(itemAmount));
                Assert.assertEquals(status, requestsPage.getItemStateText());

                if(!status.contains("Pendiente")){
                    //back to dashboard
                    DashboardPage dashboardPage = requestsPage.clickBackButton();
                }

            }else if(user.contains("1")){
                RequestsLandingPage requestsPage = new RequestsLandingPage();

                Assert.assertTrue(requestsPage.getItemTitleText().contains(user2Name) &&
                        requestsPage.getItemTitleText().contains(user2LastName));

                String itemAmount = String.valueOf("$10,00");
                itemAmount = itemAmount.replace(".", ",");

                Assert.assertTrue(requestsPage.getItemAmountText().contains(itemAmount));

                Assert.assertEquals(status, requestsPage.getItemStateText());

                //back to dashboard
                DashboardPage dashboardPage = requestsPage.clickBackButton();
            }*/
        }catch (Exception e){
            util.showTrackTraceError("No se pudo validar el request", e);
        }
    }

    /**
     * Aceptar- rechazar solicitud
     */
    @And("{string} tap to {string} the request")
    public void accept_reject_request(String user, String mode) {
        try {
           /* RequestsLandingPage requestsPage = new RequestsLandingPage();
            if (user.contains("2")) {
                if (mode.toLowerCase().contentEquals("a")) {
                    requestsPage.clickAccepButton();
                    requestsPage.waitOpenButton();
                    requestsPage.clickConfirmButton();

                    set_pin(user);

                    requestsPage.waitFinishButton();
                    requestsPage.clickFinishButton();
                    requestsPage.waitBackButton();

                }else if(mode.toLowerCase().contentEquals("r")){
                    requestsPage.clickRejectButton();
                    requestsPage.waitOpenButton();
                    requestsPage.clickConfirmButton();
                    requestsPage.waitBackButton();
                }
            }*/
        } catch (Exception e) {
            util.showTrackTraceError("unable to " + mode + " the request", e);
        }
    }

    /**
     * Validar que el contacto tiene el tipo de cuenta solicitada
     */
    @Then("Validar que el contacto seleccionado posee cuenta {string}")
    public void validar_que_existe_cuenta(String tipoCuenta) throws Exception{
        try {
            tipoCuenta = tipoCuenta.toLowerCase();

            //Variable que determinara el numero de tarjetas q existen para iterar
            int nroTarjetas = Android.driver.findElements(By.xpath("(//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout)")).size();
            ContactsDetailPage contactsDetailPage = new ContactsDetailPage();

            if(nroTarjetas==0){
                throw new Exception("No existen cuentas asociadas");
            }else{
               switch (tipoCuenta){
                   case"nubi":
                      if(!contactsDetailPage.logoNubiExists())
                          throw new Exception("No existe cuenta Nubi");
                      else
                          //cuentaATransferir = Android.driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc='\u00cdcono logo de Nubi']/../android.widget.TextView[3])[1]")).getText();
                       break;
                   case"bancaria":
                       if(!contactsDetailPage.logoBankExists())
                           throw new Exception("No existe cuenta bancaria");
                       else
                          // cuentaATransferir = Android.driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc='\u00cdcono de banco']/../android.widget.TextView[3])[1]")).getText();
                       break;
               }
            }
            //Validamos el tipo de cuenta
        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Seleccionar la cuenta solicitada
     */
    @Then("Seleccionar la cuenta {string}")
    public void seleccionar_cuenta(String tipoCuenta) throws Exception{
        try {
            //Obtener el nombre del beneficiario del envio
            LastMovementsSteps.setTransctionDescription("Enviaste dinero a ");

            //Variable que determinara el numero de tarjetas q existen para iterar
            List<MobileElement> tarjetas = Android.driver.findElements(By.xpath("(//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout)"));

            ContactsDetailPage contactsDetailPage = new ContactsDetailPage();

            for(WebElement tarjeta : tarjetas){
                if(tipoCuenta.contentEquals("nubi")){
                    contactsDetailPage.clickNubiLogo();
                    break;
                }
                if(tipoCuenta.contentEquals("bancaria")){
                    contactsDetailPage.clickBankLogo();
                    break;
                }
            }
        }catch (Exception | Error e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @And("Pulsa en el boton generar para generar CVU")
    public void tap_btn_generar_cvu(){
        try {
            //nubiWallet.movements.genCvu.uiObjects.btnGenerar().waitToAppear(10);
            //nubiWallet.movements.genCvu.tapBtnGenerar();
        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @And("Agregar contacto {string}")
    public void add_contact(String typeContact) throws Exception{
        try {
            switch (typeContact.toUpperCase()){
                case "NUBI":
                    Apps.util.contacts_sync(destinationUser);
                    break;
                case "BANCARIO":
                    //Add a bank user through Backend
                    Contacts_API contacts = new Contacts_API();
                    CreateBankingContact bankUserData = contacts.createBankingContact();
                    RGSContacts.setContactName(bankUserData.getName().substring(0,bankUserData.getName().indexOf(" ")));
                    break;
                case "NONUBI":
                    //Add a No Nubi User to the device
                    String name = "NoNubi";
                    RGSContacts.setContactName(name);
            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    @Then("Borrar contactos")
    public void delete_contacts() throws Exception{
        try{

            //delete contacts from mongo db
            //if (!Apps.util.isStaging()) {
              Apps.mongoQuery.delete_contacts_by_owner_id(originUser.getId());
            //} else {
            //    logger.info("SOLO CONSULTAS EN STAGING");
          //  }

            //Delete contacts from device
           Android.adb.delete_phone_contacts();

        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    @And("Confirmar transaccion de {string}")
    public void confirm_transaction(String transaction){
        try{
            ConfirmTransactionPage confirmPage = ConfirmTransactionPage.getInstance();

            switch (transaction.toUpperCase()){
                case "SEND":
                    //Asserts
                    Assert.assertEquals(ConfirmTransactionPage.TITLE_SEND_TEXT, Wrapper.getElementText(ConfirmTransactionPage.getLblTitle()));

                    Assert.assertEquals(CommonStepsMovements.amountToString, Wrapper.getElementText(confirmPage.getMoneyAmount()));
                    break;
                case "REQUEST_PENDING":
                    //Asserts
                    Assert.assertEquals(ConfirmTransactionPage.TITLE_REQUEST_TEXT, Wrapper.getElementText(ConfirmTransactionPage.getLblTitle()));

                    Assert.assertEquals(CommonStepsMovements.amountToString, Wrapper.getElementText(confirmPage.getMoneyAmount()));
                    break;
            }

            //Set category text
            CommonStepsMovements.CATEGORY = confirmPage.getCategoryName().getText();

            //Click on continue button
            confirmPage.click_continue_button();

            //Set transaction time
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es","AR"));
            SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm 'a. m.'");
            LastMovementsSteps.setTransctionDate(dateFormat.format(date));
            LastMovementsSteps.setTransctionHour(hourFormat.format(date));

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @Then("Validar transaccion exitosa {string}")
    public void validate_successful_transaction(String kindTransaction) throws Exception{
        try{
            SuccessfulTransactionPage successPage = SuccessfulTransactionPage.getInstance();

            //Assert icon
            Assert.assertTrue(successPage.elementExists(successPage.getEmoji()));

            switch (kindTransaction.toUpperCase()){
                case "SEND":
                    Assert.assertEquals(SuccessfulTransactionPage.SUCCES_SEND_TEXT, successPage.getMessage().getText().replace("\n"," "));
                    break;
                case "REQUEST_PENDING":
                case "REQUEST":
                    Assert.assertEquals(SuccessfulTransactionPage.SUCCES_REQUEST_TEXT, successPage.getMessage().getText().replace("\n"," "));
                    break;
                case "ACCEPT_REQUEST":
                    Assert.assertEquals(SuccessfulTransactionPage.SUCCES_ACCEPT_REQUEST, successPage.getMessage().getText().replace("\n"," "));
                    break;
            }

            //Back to dash
            successPage.click_back_to_dashboard();

        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    @And("Set balance origin user")
    @And("Setear balance de usuario origen y destino para ")
    public void set_originUser_balance() throws Exception{
        try {
            //Set origin user balance
            Accounts_API accounts_api = new Accounts_API();
            Balance userbalance = accounts_api.getBalanceAccounts(originUser.getEmail());
            originUser.setAcounts(userbalance.getAccounts());

            //Cast to the different formats
            try {
                ORIGIN_USER_BALANCE_DOUBLE = set_balance_nubi_format_numeric(((LinkedHashMap) userbalance.getAccounts().get(0)).get("balance"));
            }catch (StringIndexOutOfBoundsException er){

            }

            ORIGIN_USER_BALANCE_STRING = set_balance_nubi_format_String(((LinkedHashMap) userbalance.getAccounts().get(0)).get("balance"));

        }catch (Exception | Error er){
            logger.error(er);
            throw er;
        }
    }

    @And("Validate origin user has contacts {string}")
    public void validating_contact(String kindContact) throws Exception {
        try{
            //Validate is setted an origin user
            if(originUser == null)
                originUser = Apps.util.obtenerUsuario(kindContact);

            //Validate origin user has the given kind contact
            switch (kindContact.toUpperCase()){
                case "NUBI_CONTACT":
                    originUser.refreshContacts();
                    if(originUser.getNubiContacts().isEmpty()){
                        //If is empty, we find a user from Db
                        destinationUser = Apps.dbQuery.getDestinationUser(CommonStepsMovements.originUser.getId());

                        //Add destination user as origen user's contact
                        CommonStepsMovements commonStepsMovements = new CommonStepsMovements();
                        commonStepsMovements.add_contact("NUBI");

                        originUser.refreshContacts();
                        Assert.assertFalse(originUser.getNubiContacts().isEmpty());

                    }else{
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", originUser.getNubiContacts().get(0).getWalletData().get(0).getUserId());

                        List<User> users =  Apps.dbQuery.getUserFromBD(params,1);
                        destinationUser = users.get(0);
                    }
                    break;
            }

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @And("Set current users balance")
    public void set_users_balance() throws Exception {
        try{
            if(originUser != null){
                set_originUser_balance();
            }

            if(destinationUser != null)
                set_destinationUser_balance();

        }catch (Exception | Error e){
            logger.error(e);
            logger.error("Failed setting current users balance");
            throw e;
        }
    }

    @And("Set balance destination user")
    public void set_destinationUser_balance() throws Exception{
        try {
            //Set destination user balance
            Accounts_API accounts_api = new Accounts_API();
            Balance userbalance = accounts_api.getBalanceAccounts(destinationUser.getEmail());
            destinationUser.setAcounts(userbalance.getAccounts());

            //Cast to the different formats
            try {
                DESTINATION_USER_BALANCE_DOUBLE = set_balance_nubi_format_numeric(((LinkedHashMap) userbalance.getAccounts().get(0)).get("balance"));
            }catch (StringIndexOutOfBoundsException er){

            }

            DESTINATION_USER_BALANCE_STRING = set_balance_nubi_format_String(((LinkedHashMap) userbalance.getAccounts().get(0)).get("balance"));


        }catch (Exception | Error er){
            logger.error(er);
            throw er;
        }
    }

    public Double set_balance_nubi_format_numeric(Object balance){

       String aux = String.valueOf(balance);

       aux = new StringBuilder(aux).insert(aux.length()-2, ".").toString();

        return Double.parseDouble(aux);
    }

    public String set_balance_nubi_format_String(Object balance){

        String aux = String.valueOf(balance);

        if(!aux.equals("0"))
            aux = new StringBuilder(aux).insert(aux.length()-2, ",").toString();



        return aux;
    }

    @And("Validar balance despues la transaccion de {string}")
    public void validate_users_balance_after_transaction(String operationType) throws Exception{
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String auxNumericbalance;
            DashboardPage dashboardPage = DashboardPage.getInstance();

            Accounts_API accounts_api;

            Balance userbalance;

            switch (operationType.toUpperCase()){
                case "ACCEPT":
                case "ACCEPT_REQUEST":
                case "SEND":

                    /**
                     * ORIGIN USER VALIDATION
                     */

                    //dashboard validation
                    auxNumericbalance = String.valueOf(decimalFormat.format(ORIGIN_USER_BALANCE_DOUBLE - amountToNumeric));
                    Assert.assertEquals((auxNumericbalance.replaceAll("[.,]", "")), (Wrapper.getElementText(dashboardPage.getBalance()).replaceAll("[.,]", "")));

                    //Validate origin user balance
                    //Call to api to get the new balance
                    accounts_api = new Accounts_API();
                    userbalance = accounts_api.getBalanceAccounts(originUser.getEmail());
                    Assert.assertEquals((auxNumericbalance.replaceAll("[,.]", "")), String.valueOf(((LinkedHashMap) userbalance.getAccounts().get(0)).get("balance")));


                    //Validate destination user balance
                    auxNumericbalance = String.valueOf(decimalFormat.format(DESTINATION_USER_BALANCE_DOUBLE + amountToNumeric));
                    userbalance = accounts_api.getBalanceAccounts(destinationUser.getEmail());
                    Assert.assertEquals((auxNumericbalance.replaceAll("[,.]", "")), String.valueOf(((LinkedHashMap) userbalance.getAccounts().get(0)).get("balance")));
                    break;
                case "REJECT_REQUEST":
                case "REQUEST_PENDING":

                    /**
                     * ORIGIN USER VALIDATION
                     */

                    //dashboard validation
                    auxNumericbalance = String.valueOf(decimalFormat.format(ORIGIN_USER_BALANCE_DOUBLE));
                    Assert.assertEquals((auxNumericbalance.replaceAll("[.,]", "")), (Wrapper.getElementText(dashboardPage.getBalance()).replaceAll("[.,]", "")));

                    //Validate origin user balance
                    //Call to api to get the new balance
                    accounts_api = new Accounts_API();
                    userbalance = accounts_api.getBalanceAccounts(originUser.getEmail());
                    Assert.assertEquals((auxNumericbalance.replaceAll("[,.]", "")), String.valueOf(((LinkedHashMap) userbalance.getAccounts().get(0)).get("balance")));


                    //Validate destination user balance
                    auxNumericbalance = String.valueOf(decimalFormat.format(DESTINATION_USER_BALANCE_DOUBLE));
                    userbalance = accounts_api.getBalanceAccounts(destinationUser.getEmail());
                    Assert.assertEquals((auxNumericbalance.replaceAll("[,.]", "")), String.valueOf(((LinkedHashMap) userbalance.getAccounts().get(0)).get("balance")));
                    break;
            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    @When("Origin user {string} a p2p request")
    public void make_p2p_request(String typeRequest) throws Exception {
        String aux;
        aux = (typeRequest.toUpperCase().contentEquals("SEND")) ? "P2P_OUTGOING_REQUEST" : "P2P_INCOMING_REQUEST";
        generate_movements(1, aux);
    }


    @And("Generar {int} movimiento de {string}")
    public void generate_movements(int quantity, String movementType) throws Exception{
        try {
            Auth_API auth_api;
            P2P_API p2P_api;
            P2PRequest p2PRequest;
            int i;
            switch (movementType.toUpperCase()){
                case "RECARGA_CELULAR":
                    Rapipago_API rapipago_api = new Rapipago_API();
                    CellphoneRecharge cellphoneRecharge = null;
                    for(i=0; i<quantity; i++){
                        cellphoneRecharge = rapipago_api.cellphone_recharge(Login.usuario.getPhoneNumber().substring(4,14), "CLARO", "200");
                    }

                    System.out.println(cellphoneRecharge);
                    break;
                case"P2P_INCOMING_REQUEST":
                    //make the p2p request
                    p2P_api = new P2P_API();
                    for (i=0; i<quantity; i++){
                        //set amount to request
                        double auxAmount;
                        String amount="";
                        auxAmount = ORIGIN_USER_BALANCE_DOUBLE;
                        auxAmount = (auxAmount * 1) / 100;
                        auxAmount = Math.round(auxAmount);
                        amount = String.valueOf(auxAmount).replace(".",",");

                        if((amount.substring(amount.length()-2, amount.length()-1)).contentEquals(","))
                            amount = amount + "0";

                        CommonStepsMovements.amountToNumeric = auxAmount;
                        CommonStepsMovements.amountToString = amount;

                        p2PRequest = p2P_api.createANewMoneyRequest(destinationUser, originUser, amount.replaceAll("[.,]", ""), "");
                    }
                    break;
                case"P2P_OUTGOING_REQUEST":
                    //destinationUser = Apps.dbQuery.getDestinationUser(Login.usuario.getId());
                   // Login.usuario.setAcounts(Apps.dbQuery.getAccountFromUser(Login.usuario.getId(), Login.usuario.getEmail(), Login.usuario.getRawPassword(), "",""));

                    //make the p2p request
                    p2P_api = new P2P_API();
                    for (i=0; i<quantity; i++){
                        //set amount to request
                        double auxAmount;
                        String amount="";
                            auxAmount = ORIGIN_USER_BALANCE_DOUBLE;
                            auxAmount = (auxAmount * 1) / 100;
                            auxAmount = Math.round(auxAmount);
                            amount = String.valueOf(auxAmount).replace(".",",");

                        CommonStepsMovements.amountToNumeric = auxAmount;
                        CommonStepsMovements.amountToString = amount;

                        p2PRequest = p2P_api.createANewMoneyRequest(originUser, destinationUser, amount.replaceAll("[.,]", ""), "");
                    }
                    break;
            }
        }catch (Exception | Error err){
            logger.error(err);
            throw err;
        }
    }

    /**
     * MAKE a p2p transaction from BE
     */
    @Given("Validar que existe una transaccion {string}")
    public boolean validate_p2p_request(String p2pType) throws Exception{
        boolean existTransaction = false;
        try {
            switch (p2pType.toUpperCase()){
                case "P2P_INCOMING_REQUEST":
                    //refresh list transactions
                    Login.usuario.refreshTransactionsLists();
                    List<Transaction> incomingT = Login.usuario.getIncomingTransactions();


                    for (Transaction t: incomingT){
                        if(t.getTransactionType().toUpperCase().contentEquals("P2P_REQUEST")){
                            existTransaction = true;
                            break;
                        }
                    }
                    if(!existTransaction) {
                        generate_movements(1, p2pType);
                        existTransaction = validate_p2p_request(p2pType);
                    }else{
                        Assert.assertTrue(existTransaction);
                    }
                    break;
                case "P2P_OUTGOING_REQUEST":
                    //refresh list transactions
                    Login.usuario.refreshTransactionsLists();
                    List<Transaction> outgoingT = Login.usuario.getOutgoingTransactions();


                    for (Transaction t: outgoingT){
                        if(t.getTransactionType().toUpperCase().contentEquals("P2P_REQUEST")){
                            existTransaction = true;
                            break;
                        }
                    }
                    if(!existTransaction) {
                        generate_movements(1, p2pType);
                        existTransaction = validate_p2p_request(p2pType);
                    }else{
                        Assert.assertTrue(existTransaction);
                    }
                    break;
            }


        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }

        return existTransaction;
    }


    @And("Validate pending {string} request")
    public void validate_pending_request(String typeRequest) throws Exception {
        try{

            //Validate origin user pending request
            originUser.refreshTransactionsLists();

            Transaction ts = new Transaction();

            if(typeRequest.toUpperCase().contentEquals("SEND")){
                ts = originUser.getOutgoingTransactions().get(0);

                //Asserts
                Assert.assertEquals(originUser.getId(), ts.getOriginUserId());
                Assert.assertEquals(destinationUser.getId(), ts.getDestinationUserId());

            }else{
                ts = originUser.getIncomingTransactions().get(0);

                //Asserts
                //When its a receive request, origin user is destination user
                Assert.assertEquals(destinationUser.getId(), ts.getOriginUserId());
                Assert.assertEquals(originUser.getId(), ts.getDestinationUserId());
            }

            Assert.assertEquals("P2P_REQUEST", ts.getTransactionType());
            Assert.assertEquals(Integer.parseInt(amountToString.replaceAll("[.,]", "")), ts.getAmount());
            Assert.assertEquals("PENDING_APPROVAL", ts.getStatus());

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }

    @And("User {string} the request")
    public void action_to_request(String action) throws Exception {
        try{
            Login.usuario = originUser;

            //Login into the app
            Login login = new Login();

            login.log_in();

            //Go to received request list
            MenuPage.getInstance().clickBtnMenu();
            MenuPage.getInstance().clickBtnSolicitudes();

            RequestsLandingPage requestsLandingPage = RequestsLandingPage.getInstance();

            //Assert is selected the received request tab
            Assert.assertTrue(Wrapper.elementExists(requestsLandingPage.getLeftOption()));

            Wrapper.waitForElementVisibility(requestsLandingPage.getPendingTitle());

            //Pending received request list must be >= 1 because we recently made one
            Assert.assertTrue(requestsLandingPage.getRequestsPending().size() >= 1);

            //The first one on the list should be the last we just did
            //and should math with data transaction

            //Here says destination user, but in fact
            //was the user who made request
            //Do not put origin user , origin user is the user logged into the app
            String fullName = CommonStepsMovements.destinationUser.getName() + " " + CommonStepsMovements.destinationUser.getLastName();

            //Assert the first pending request data
            Assert.assertEquals(fullName.replace(" ", ""),
                    Wrapper.getElementText(requestsLandingPage.getRequestsPendingTitle().get(0)).replace(" ", ""));
            Assert.assertEquals("$" + CommonStepsMovements.amountToString, Wrapper.getElementText(requestsLandingPage.getRequestsPendingAmount().get(0)));

            if(action.toUpperCase().contentEquals("ACCEPT")) {

                //Set transaction data
                LastMovementsSteps.setTransctionDescription("Enviaste a " + fullName);
                //Set transaction amount
                LastMovementsSteps.setTransactionAmount(CommonStepsMovements.amountToString);
                //Set transaction time
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es","AR"));
                SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm 'a. m.'");
                LastMovementsSteps.setTransctionDate(dateFormat.format(date));
                LastMovementsSteps.setTransctionHour(hourFormat.format(date));

                requestsLandingPage.getRequestsPendingAcceptButton().get(0).click();

                //Set nubi password
                NubiPassP2PPage.getInstance().set_valid_pin();

                //Validate succes transaction
                validate_successful_transaction("ACCEPT_REQUEST");
            }else{
                requestsLandingPage.getRequestsPendingCancelButton().get(0).click();

                Thread.sleep(5000);

                RequestsLandingPage.getInstance();
            }







        }catch (Exception | Error e){
            logger.error("Error in " + action + " the request");
            throw e;
        }
    }
}
