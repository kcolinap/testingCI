package api.apps.android.nw.operaciones.p2p;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.operaciones.p2p.request.RequestsLandingPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmTransactionPage extends BasePage {

    private static ConfirmTransactionPage sSoleInstance;

    public static final String TITLE_SEND_TEXT = "Estás por enviar";
    public static final String TITLE_REQUEST_TEXT = "Estás por pedir";


    @AndroidFindBy(id = "contactName")
    private MobileElement contactName;

    @AndroidFindBy(id = "moneyAmount")
    private MobileElement moneyAmount;

    @AndroidFindBy(id = "categoryIcon")
    private MobileElement categoryIcon;

    @AndroidFindBy(id = "categoryName")
    private MobileElement categoryName;

    @AndroidFindBy(id = "nextButton")
    private static MobileElement nextButton;

    public MobileElement getContactName() {
        return contactName;
    }

    public MobileElement getMoneyAmount() {
        return moneyAmount;
    }

    public MobileElement getCategoryIcon() {
        return categoryIcon;
    }

    public MobileElement getCategoryName() {
        return categoryName;
    }

    public MobileElement getNextButton() {
        return nextButton;
    }

    public ConfirmTransactionPage() {
        super();
        waitForElementVisibility(nextButton);
    }

    public synchronized static ConfirmTransactionPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new ConfirmTransactionPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(nextButton)));
        }
        return sSoleInstance;
    }

    public void click_continue_button(){
        waitForElementEnabled(nextButton);
        nextButton.click();
    }

    
}
