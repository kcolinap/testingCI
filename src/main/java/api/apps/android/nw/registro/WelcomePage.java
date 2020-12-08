package api.apps.android.nw.registro;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomePage extends BasePage {

    private static WelcomePage sSoleInstance;

    private final String LBL_WELCOME_MESSAGE_TEXT = "¡Hola,";
    public static final String WELCOME_SUBTITLE_TEXT = "Conocé lo que podés hacer con Nubi:";

    @AndroidFindBy(id = "welcomeIcon")
    private MobileElement welcomeIcon;

    @AndroidFindBy(id = "welcomeMessage")
    private MobileElement welcomeMessage;

    @AndroidFindBy(id = "welcomeSubtitle")
    private MobileElement welcomeSubtitle;

    @AndroidFindBy(id = "continueButton")
    private static MobileElement continueButton;


    /** GETS **/
    public String getWelcomeMessageTitle(String name) {
        return LBL_WELCOME_MESSAGE_TEXT + " " + name + "!";
    }

    public MobileElement getWelcomeIcon() {
        return welcomeIcon;
    }

    public MobileElement getWelcomeSubtitle() {
        return welcomeSubtitle;
    }

    public MobileElement getWelcomeMessage() {
        return welcomeMessage;
    }

    public MobileElement getContinueButton() {
        return continueButton;
    }


    public WelcomePage(){
       super();
        waitForElementVisibility(continueButton);
    }

    public synchronized static WelcomePage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new WelcomePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(continueButton)));
        }
        return sSoleInstance;
    }

    //Actions
    public void click_continue_button(){
        waitForElementEnabled(continueButton);
        continueButton.click();
    }


}
