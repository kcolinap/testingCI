package api.apps.android.nw.operaciones.recarga_celular;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuccessfulRechargePage extends BasePage {

    private static SuccessfulRechargePage sSoleInstance;


    public static final String
            RECHARGE_CELULAR_TITLE_TEXT = "Recargá tu celular",
            MESSAGE_TEXT = "¡Recargaste el celu!";



    @AndroidFindBy(id = "mainButton")
    private static MobileElement mainButton;

    @AndroidFindBy(id = "emoji")
    private MobileElement emoji;

    @AndroidFindBy(id = "message")
    private MobileElement message;

    @AndroidFindBy(id = "secondaryMessage")
    private MobileElement secondaryMessage;

    public static MobileElement getMainButton() {
        return mainButton;
    }

    public MobileElement getEmoji() {
        return emoji;
    }

    public MobileElement getMessage() {
        return message;
    }

    public MobileElement getSecondaryMessage() {
        return secondaryMessage;
    }

    public String getDisclaimerText(String amount, String number){
        return "Se acreditaron $" + amount + " al número  " + number;
    }

    public SuccessfulRechargePage(){
        super();
        waitForElementVisibility(mainButton);
    }

    public synchronized static SuccessfulRechargePage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new SuccessfulRechargePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(SuccessfulRechargePage.class);
            wait.until(ExpectedConditions.visibilityOf(mainButton));
        }
        return sSoleInstance;
    }

    public void back_to_dashboard(){
        super.waitForElementEnabled(mainButton);
        mainButton.click();
    }


}
