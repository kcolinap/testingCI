package api.apps.android.nw.operaciones.sube.confirmacionDeRecarga;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreditConfirmationPage extends BasePage {

    /**
     * Properties Section
     */

    private static CreditConfirmationPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String expectedMainTitleText = "Carg\u00E1 tu SUBE";
    public final String expectedChargeConfirmReviewMessageText = "Est\u00E1s por cargar";
    public final String expectedMoneySymbolText = "$";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "cardChargeConfirmationTitle")
    private static MobileElement cardChargeConfirmationTitle;

    @AndroidFindBy(id = "chargeConfirmReviewMessage")
    private static MobileElement chargeConfirmReviewMessage;

    @AndroidFindBy(id = "subeIcon")
    private static MobileElement subeIcon;

    @AndroidFindBy(id = "subeCardChargeConfirmationTitle")
    private static MobileElement subeCardChargeConfirmationTitle;

    @AndroidFindBy(id = "moneySymbol")
    private static MobileElement moneySymbol;

    @AndroidFindBy(id = "moneyAmount")
    private static MobileElement moneyAmount;

    @AndroidFindBy(id = "confirmationChargeButton")
    private static MobileElement confirmationChargeButton;

    /**
     * Methods Section
     */

    private CreditConfirmationPage(){
        super();
        waitForElementVisibility(cardChargeConfirmationTitle);

    }

    public synchronized static CreditConfirmationPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new CreditConfirmationPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(cardChargeConfirmationTitle)));
        }
        return sSoleInstance;
    }

    public String getCardChargeConfirmationTitleText() { return cardChargeConfirmationTitle.getText().trim(); }

    public String getChargeConfirmReviewMessageText() { return chargeConfirmReviewMessage.getText().trim(); }

    public String getSubeCardChargeConfirmationTitleText() { return subeCardChargeConfirmationTitle.getText().trim(); }

    public String getMoneySymbolText() { return moneySymbol.getText().trim(); }

    public String getMoneyAmountText() { return moneyAmount.getText().trim(); }

    public boolean existSubeIcon() { return subeIcon.isDisplayed(); }

    public boolean isContinueButtonEnabled() { return confirmationChargeButton.isEnabled(); }

    public void clickContinueButton() { confirmationChargeButton.click(); }


}
