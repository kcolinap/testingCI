package api.apps.android.nw.contrasenia;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PasswordRecoveryMailPage extends BasePage {

    private static PasswordRecoveryMailPage sSoleInstance;

    public static String
            TITLE_TEXT = "Recuperar contraseña";

    @AndroidFindBy(id = "forgotPasswordTitle")
    private MobileElement forgotPasswordTitle;

    @AndroidFindBy(id = "sendIcon")
    private MobileElement sendIcon;

    @AndroidFindBy(id = "userEmail")
    private MobileElement userEmail;

    @AndroidFindBy(id = "goToEmailButton")
    private static MobileElement goToEmailButton;

    public MobileElement getForgotPasswordTitle() {
        return forgotPasswordTitle;
    }

    public MobileElement getSendIcon() {
        return sendIcon;
    }

    public MobileElement getUserEmail() {
        return userEmail;
    }

    public static MobileElement getGoToEmailButton() {
        return goToEmailButton;
    }

    public String get_disclaimer_text(String email){
        return "Revisá " + email + ", para confirmar que sos vos.";
    }

    public PasswordRecoveryMailPage() {
      super();
      waitForElementVisibility(goToEmailButton);
    }

    public synchronized static PasswordRecoveryMailPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new PasswordRecoveryMailPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(PasswordRecoveryMailPage.class);
            wait.until(ExpectedConditions.visibilityOf(goToEmailButton));
        }
        return sSoleInstance;
    }

    public void click_open_mail_button(){
        goToEmailButton.click();
    }

}
