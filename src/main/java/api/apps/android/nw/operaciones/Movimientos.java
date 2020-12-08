package api.apps.android.nw.operaciones;

import api.apps.android.nw.contactos.ContactsPage;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.operaciones.cashout.CashOut;
import api.apps.android.nw.cvu.GeneracionCvu;
import api.interfaces.Activity;

public class Movimientos implements Activity {

    public MovimientosUIObjects uiObject = new MovimientosUIObjects();
    public GeneracionCvu genCvu = new GeneracionCvu();
    //public CashIn cashIn = new CashIn();
    public CashOut cashOut = new CashOut();


    //Pantalla billetera
    private final String TEXT_TITLE_BILLETERA = "Monto";
    private final String SEND_QUESTION = "\u00BFCu\u00E1nto dinero vas a enviar?";

    private final String REQUEST_QUESTION = "¿Cu\u00E1nto dinero vas a solicitar?";
    private final String DEFAULT_AMOUNT = "$0,00";

    public String getREQUEST_QUESTION() {
        return REQUEST_QUESTION;
    }

    public String getTEXT_TITLE_BILLETERA() {
        return this.TEXT_TITLE_BILLETERA;
    }

    public String getSEND_QUESTION() {
        return this.SEND_QUESTION;
    }

    public String getDEFAULT_AMOUNT() {
        return DEFAULT_AMOUNT;
    }

    //Pantalla de confirmacion
    private final String
            TRANSFER_DETAIL = "\u00BFEst\u00E1 todo ok?",
            TRANSACTION_DESCRIPTION = "Una vez confirmado no podr\u00E1s cancelar el env\u00EDo";

    public String getTRANSFER_DETAIL() {

        return TRANSFER_DETAIL;
    }

    public String getTRANSACTION_DESCRIPTION() {
        return TRANSACTION_DESCRIPTION;
    }

    @Override
    public Movimientos waitToLoadScreen() {
        try {
            uiObject.transactionSwitch().waitToAppear(10);
            return null;
        } catch (AssertionError e) {
            throw new AssertionError("Fail to load 5Movements screen");
        }
    }

    /*******************
     *  Pantalla ingreso de monto
     ***********/
    public Movimientos setMonto(Double monto) {
        try {
            uiObject.amountInput().typeText(String.valueOf(monto));
            return null;
        } catch (AssertionError e) {
            throw new AssertionError("Error al setear monto");
        }
    }

    public DashboardPage tapBackButton() {
        try {
            uiObject.btnAtras().tap();
            return new DashboardPage();
        } catch (AssertionError e) {
            throw new AssertionError("Fail to tap back button on wallet screen");
        }
    }

    public Movimientos tapTransactionButton() {
        try {
            uiObject.transactionSwitch().tap();
            return null;
        } catch (AssertionError e) {
            throw new AssertionError("Fail to tap transaction button to send/request money");
        }
    }


    public ContactsPage tapBtnSiguiente() {// button continuar en pantalla monto
        try {
            uiObject.btnContinuar().tap();
            return new ContactsPage();
        } catch (AssertionError e) {
            throw new AssertionError("Error al pulsar boton siguiente en pantalla Monto");
        }
    }

    public Movimientos tapConfirmButton() { //Button to confirm the movement
        try {

            uiObject.btnSiguiente().tap();

            return null;
        } catch (AssertionError e) {
            throw new AssertionError("Fail to tap next button to confirm the movement");
        }
    }

    public DashboardPage tapFinishTransaction() {   // button to finish transaction
        try {
            uiObject.finishButton().tap();
            return new DashboardPage();
        } catch (AssertionError e) {
            throw new AssertionError("Fail to tap finish button in order to complete the transaction");
        }
    }

}
