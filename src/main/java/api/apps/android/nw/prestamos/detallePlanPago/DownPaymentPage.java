package api.apps.android.nw.prestamos.detallePlanPago;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DownPaymentPage extends BasePage {

    /**
     * Properties Section
     */

    private static DownPaymentPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String MAIN_TTITLE_TEXT = "Plan de pago";
    public final String PRICE_DESCRIPTION_TEXT = "Hoy sólo pagás ";
    public final String MONEY_SYMBOL = "$";
    public final String FEES_DESCRIPTION_PREFIX_TEXT = "A partir del";
    public final String FEES_DESCRIPTION_SUFIX_TEXT = "pagás 12 cuotas de";
    public final String TNA_TEXT = "TNA";
    public final String CFT_TEXT = "X CFT C/IVA";
    public final String TOTAL_AMOUNT_TEXT = "*CUOTA PROMEDIO. EN TOTAL PAGÁS";
    public final String CONTINUE_BUTTON_TEXT = "Continuar";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "priceDescription")
    private static MobileElement lblPriceDescription;

    @AndroidFindBy(id = "emoji")
    private static MobileElement icnEmoji;

    @AndroidFindBy(id = "moneySymbol")
    private static MobileElement lblMoneySymbol;

    @AndroidFindBy(id = "moneyAmount")
    private static MobileElement lblMoneyAmount;

    @AndroidFindBy(id = "symbol")
    private static MobileElement lblSymbol;

    @AndroidFindBy(id = "feesDescription")
    private static MobileElement lblFeesDescription;

    @AndroidFindBy(id = "feePrice")
    private static MobileElement lblFeePrice;

    @AndroidFindBy(id = "disclaimer")
    private static MobileElement lblDisclaimer;

    @AndroidFindBy(id = "continueButton")
    private static MobileElement btnContinueButton;

    /**
     * Getter and Setter Section
     */

    public MobileElement getLblPriceDescription() { return lblPriceDescription; }
    public MobileElement getIcnEmoji() { return icnEmoji; }
    public MobileElement getLblMoneySymbol() { return lblMoneySymbol; }
    public MobileElement getLblMoneyAmount() { return lblMoneyAmount; }
    public MobileElement getLblSymbol() { return lblSymbol; }
    public MobileElement getLblFeesDescription() { return lblFeesDescription; }
    public MobileElement getLblFeePrice() { return lblFeePrice; }
    public MobileElement getLblDisclaimer() { return lblDisclaimer; }
    public MobileElement getBtnContinueButton() { return btnContinueButton; }

    /**
     * Methods Section
     */

    private DownPaymentPage(){
        super();
        waitForElementVisibility(btnContinueButton);
    }

    public synchronized static DownPaymentPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new DownPaymentPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(btnContinueButton)));
        }
        return sSoleInstance;
    }

    public void clickContinueButton() { btnContinueButton.click(); }
}
