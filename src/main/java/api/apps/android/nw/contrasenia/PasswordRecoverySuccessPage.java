package api.apps.android.nw.contrasenia;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PasswordRecoverySuccessPage extends BasePage {

    private static PasswordRecoverySuccessPage sSoleInstance;

    public static String
            MESSAGE_TEXT = "¡Listo! Cambiaste tu contraseña",
            SECONDARY_MESSAGE = "Ya podés iniciar sesión con tu nueva contraseña.";

    @AndroidFindBy(id = "emoji")
    private MobileElement emoji;

    @AndroidFindBy(id = "message")
    private MobileElement message;

    @AndroidFindBy(id = "secondaryMessage")
    private MobileElement secondaryMessage;

    @AndroidFindBy(id = "mainButton")
    private static MobileElement mainButton;

    public MobileElement getEmoji() {
        return emoji;
    }

    public MobileElement getMessage() {
        return message;
    }

    public MobileElement getSecondaryMessage() {
        return secondaryMessage;
    }

    public static MobileElement getMainButton() {
        return mainButton;
    }

    public PasswordRecoverySuccessPage() {
      super();
      waitForElementVisibility(mainButton);
    }

    public synchronized static PasswordRecoverySuccessPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new PasswordRecoverySuccessPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(PasswordRecoverySuccessPage.class);
            wait.until(ExpectedConditions.visibilityOf(mainButton));
        }
        return sSoleInstance;
    }

    public void click_finish_button(){
        mainButton.click();
    }
}
