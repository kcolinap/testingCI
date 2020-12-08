package api.apps.android.nw.operaciones.recarga_celular;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmountToRechargePage extends BasePage {

    private static AmountToRechargePage sSoleInstance;

    public static final String
            RECHARGE_CELULAR_TITLE_TEXT = "Recargá tu celular",
            SUBTITLE_TEXT = "¿Cuánto querés recargar?";

    public static final String[] BEAN_COMPANY_TEXT = {
            "Podés cargar entre $10 y $600.",
            "Podés cargar entre $5 y $200.",
            "Podés cargar entre $5 y $10.000.",
            "Podés cargar entre $5 y $10.000.",
            "Podés cargar entre $5 y $500."

    };


    @AndroidFindBy(id = "subtitleRechargeMobile")
    private static MobileElement subtitleRechargeMobile;

    @AndroidFindBy(id = "subtitleRechargeRangeMessage")
    private static MobileElement subtitleRechargeRangeMessage;

    @AndroidFindBy(id = "currentBalance")
    private MobileElement currentBalance;

    @AndroidFindBy(id = "amountInput")
    private static MobileElement amountInput;

    @AndroidFindBy(id = "nextButton")
    private MobileElement nextButton;

    public MobileElement getNextButton() {
        return nextButton;
    }

    public MobileElement getCurrentBalance() {
        return currentBalance;
    }

    public MobileElement getAmountInput() {
        return amountInput;
    }

    public MobileElement getSubtitleRechargeMobile() {
        return subtitleRechargeMobile;
    }

    public MobileElement getSubtitleRechargeRangeMessage() {
        return subtitleRechargeRangeMessage;
    }

    public AmountToRechargePage(){
        super();
        waitForElementVisibility(amountInput);
    }

    public synchronized static AmountToRechargePage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new AmountToRechargePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(AmountToRechargePage.class);
            wait.until(ExpectedConditions.visibilityOf(amountInput));
        }
        return sSoleInstance;
    }

    public void set_amount(String amount){
        amountInput.sendKeys(amount);
        hideKeyboardIfVisible();
    }

    public void click_on_next_button(){
        //waitForElementEnabled(nextButton);
        nextButton.click();
    }

}
