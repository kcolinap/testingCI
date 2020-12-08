package api.apps.android.nw.movimientos.details.movementTypes;

import api.apps.android.Android;
import api.apps.android.nw.movimientos.details.MovementDetailBasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PrepaidCardDetailPage extends MovementDetailBasePage {

    private static PrepaidCardDetailPage sSoleInstance;

    public static String TRANSACTION_DESCRIPTION_TEXT = "Retiraste de tu cuenta Nubi";
    public static String DISCLAIMER_REFUND_TEXT = "Esta operación corresponde a una Devolución o Cancelación. ¿Tenés dudas? Escribinos";
    public static String DISCLAIMER_REJECTED_TEXT = "Este pago fue rechazado. No se debitó dinero de tu cuenta.";
    public static String DISCLAIMER_CANCELED_TEXT = "Este pago fue cancelado. Ya devolvimos el saldo a tu cuenta.";

    //label raw amount
    @AndroidFindBy(id = "itemTitle")
    private MobileElement itemTitle;

    @AndroidFindBy(id = "itemValue")
    private MobileElement itemValue;

    @AndroidFindBy(id = "disclaimerText")
    private MobileElement disclaimerText;

    public MobileElement getItemTitle() {
        return itemTitle;
    }

    public MobileElement getItemValue() {
        return itemValue;
    }

    public MobileElement getDisclaimerText() {
        return disclaimerText;
    }

    public PrepaidCardDetailPage() {
        super();
        waitForElementVisibility(transactionIcon);
    }

    public synchronized static PrepaidCardDetailPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new PrepaidCardDetailPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(transactionIcon)));
        }
        return sSoleInstance;
    }

}
