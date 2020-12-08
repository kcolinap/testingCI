package api.apps.android.nw.operaciones.p2p;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SetAmountPage extends BasePage {

    private static SetAmountPage sSoleInstance;

    public static final String TITLE_TEXT = "¿Cuánto?";
    public static final String SEND_TEXT = "Enviar";
    public static final String REQUEST_TEXT = "Pedir";

    @AndroidFindBy(id = "leftOption")
    private MobileElement leftOption;

    @AndroidFindBy(id = "rightOption")
    private MobileElement rightOption;

    @AndroidFindBy(id = "currentBalance")
    private static MobileElement currentBalance;

    @AndroidFindBy(id = "amountInput")
    private MobileElement amountInput;

    @AndroidFindBy(id = "continueButton")
    private MobileElement continueButton;

    public String getCurrentBalanceText(String balance) {
        return "Tenés $"+balance+" en tu cuenta";
    }

    public MobileElement getLeftOption() {
        return leftOption;
    }

    public MobileElement getRightOption() {
        return rightOption;
    }

    public MobileElement getCurrentBalance() {
        return currentBalance;
    }

    public MobileElement getAmountInput() {
        return amountInput;
    }

    public MobileElement getContinueButton() {
        return continueButton;
    }

    public SetAmountPage() {
        super();
        waitForElementVisibility(currentBalance);
    }

    public synchronized static SetAmountPage getInstance(){
        Android.hideKeyboardIfVisible();
        if (sSoleInstance == null){
            sSoleInstance = new SetAmountPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(SetAmountPage.class);
            wait.until(ExpectedConditions.visibilityOf(currentBalance));
        }
        return sSoleInstance;
    }

    public void set_amount(String amount){
        hideKeyboardIfVisible();
        amountInput.sendKeys(amount);
        hideKeyboardIfVisible();
    }

    public void click_continue_button(){
        waitForElementEnabled(continueButton);
        continueButton.click();
    }

    public boolean requestTransactionSwitchIsChecked() {
        return false; //requestTransactionSwitch.getAttribute("checked").equals("true");
    }

    
}
