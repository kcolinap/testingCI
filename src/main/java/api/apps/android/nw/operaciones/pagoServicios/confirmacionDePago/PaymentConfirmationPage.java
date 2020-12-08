package api.apps.android.nw.operaciones.pagoServicios.confirmacionDePago;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentConfirmationPage extends BasePage {

    /**
     * Properties Section
     */

    private static PaymentConfirmationPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String expectedMainTitleText = "Confirmaci\u00F3n";
    public final String expectedSubtitleText = "\u00BFEst\u00E1 todo bien?";
    public final String expectedConfirmationText = "Est\u00E1s por pagar";
    public final String expectedPayButtonText = "Pagar";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "image")
    private static MobileElement imgBills;

    @AndroidFindBy(id = "confirmationMessage")
    private static MobileElement lblConfirmationMessage;

    @AndroidFindBy(id = "confirmationMessageCompany")
    private static MobileElement lblCompanyName;

    @AndroidFindBy(id = "amount")
    private static MobileElement lblAmount;

    @AndroidFindBy(id = "payButton")
    private static MobileElement btnPay;

    /**
     * Methods Section
     */

    private PaymentConfirmationPage(){
        super();
        waitForElementVisibility(btnPay);
    }

    public synchronized static PaymentConfirmationPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new PaymentConfirmationPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(btnPay)));
        }
        return sSoleInstance;
    }

    public boolean isBillsImageDisplayed() { return imgBills.isDisplayed(); }
    public boolean isPayButtonEnabled() { return btnPay.isEnabled(); }

    public String getConfirmationMessageText() { return lblConfirmationMessage.getText(); }
    public String getCompanyNameText() { return lblCompanyName.getText(); }
    public String getAmountText() { return lblAmount.getText(); }
    public String getPayButtonText() { return btnPay.getText(); }

    public void clickPayButton() { btnPay.click(); }

}
