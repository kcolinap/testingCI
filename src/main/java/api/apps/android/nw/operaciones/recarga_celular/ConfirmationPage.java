package api.apps.android.nw.operaciones.recarga_celular;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPage extends BasePage {

    private static ConfirmationPage sSoleInstance;

    public static final String
            RECHARGE_CELULAR_TITLE_TEXT = "Recargá tu celular",
            CONFIRMATION_SUBTITLE_TEXT = "Estás por recargar el",
            RECHARGE_LABEL_TEXT = "Vas a recargar el celular:";


    @AndroidFindBy(id = "subtitleRechargeMobile")
    private static MobileElement subtitleRechargeMobile;

    @AndroidFindBy(id = "rechargeLabel")
    private MobileElement rechargeLabel;

    @AndroidFindBy(id = "amountMoney")
    private MobileElement amountMoney;

    @AndroidFindBy(id = "companyImage")
    private MobileElement companyImage;

    @AndroidFindBy(id = "nextButton")
    private static MobileElement nextButton;

    public MobileElement getSubtitleRechargeMobile() {
        return subtitleRechargeMobile;
    }

    public MobileElement getRechargeLabel() {
        return rechargeLabel;
    }

    public MobileElement getAmountMoney() {
        return amountMoney;
    }

    public MobileElement getNextButton() {
        return nextButton;
    }

    public MobileElement getCompanyImage() {
        return companyImage;
    }

    public ConfirmationPage(){
        super();
        waitForElementVisibility(nextButton);
    }

    public synchronized static ConfirmationPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new ConfirmationPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(ConfirmationPage.class);
            wait.until(ExpectedConditions.visibilityOf(nextButton));
        }
        return sSoleInstance;
    }


    public void click_on_next_button(){
        waitForElementEnabled(nextButton);
        nextButton.click();
    }

}
