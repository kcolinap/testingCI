package api.apps.android.nw.operaciones.pagoServicios.saldoInsuficiente;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InsufficientBalance extends BasePage {

    private static InsufficientBalance sSoleInstance;
    public static final String
            TITLE_PAYMENT = "Pagá tus facturas",
            TITLE = "No tenés saldo suficiente",
            SUBTITLE = "Elegí como cargar tu cuenta para\ncontinuar:",
            OWN_ACOUNT_TEXT = "Una cuenta\nbancaria tuya",
            OTHER_ACOUNT_TEXT = "Cualquier cuenta\nbancaria",
            RAPIPAGO_TEXT = "Rapipago";

    @AndroidFindBy(id = "titlePayment")
    private static MobileElement titlePayment;

    @AndroidFindBy(id = "emoji")
    private static MobileElement emoji;

    @AndroidFindBy(id = "title")
    private static MobileElement title;

    @AndroidFindBy(id = "currentBalance")
    private static MobileElement currentBalance;

    @AndroidFindBy(id = "subtitle")
    private static MobileElement subtitle;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Una cuenta\n" +
            "bancaria tuya\")")
    private MobileElement ownAccountText;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Cualquier cuenta\n" +
            "bancaria\")")
    private MobileElement otheraccountText;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Rapipago\")")
    private MobileElement rapipagoText;

    @AndroidFindBy(id = "mainButton")
    private static MobileElement mainButton;

    public InsufficientBalance() {
        super();
        wait.until(ExpectedConditions.visibilityOf(mainButton));
    }

    public synchronized static InsufficientBalance getInstance() {
        if (sSoleInstance == null){
            sSoleInstance = new InsufficientBalance();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(mainButton)));
        }
        return sSoleInstance;
    }

    public static MobileElement getTitlePayment() { return titlePayment; }

    public static MobileElement getEmoji() { return emoji;}

    public static MobileElement getTitle() { return title; }

    public static MobileElement getCurrentBalance() { return currentBalance; }

    public static MobileElement getSubtitle() { return subtitle; }

    public MobileElement getOwnAccountText() { return ownAccountText; }

    public MobileElement getOtheraccountText() { return otheraccountText; }

    public MobileElement getRapipagoText() { return rapipagoText; }

    public static MobileElement getMainButton() { return mainButton; }
}
