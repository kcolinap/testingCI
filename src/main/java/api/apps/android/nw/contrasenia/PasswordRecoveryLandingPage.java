package api.apps.android.nw.contrasenia;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PasswordRecoveryLandingPage extends BasePage {

    private static PasswordRecoveryLandingPage sSoleInstance;

    public static String
            TITLE_TEXT = "Recuperar contraseña",
            SUBTITLE_TEXT = "¿Con qué email te registraste?";

    @AndroidFindBy(id = "input")
    private static MobileElement emailInput;

    @AndroidFindBy(id = "button")
    private MobileElement btnSend;

    public MobileElement getEmailInput() {
        return emailInput;
    }

    public MobileElement getBtnSend() {
        return btnSend;
    }

    public MobileElement getLblSubtitle() {
        return lblSubtitle;
    }


    public PasswordRecoveryLandingPage() {
      super();
      waitForElementVisibility(emailInput);
    }

    public synchronized static PasswordRecoveryLandingPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new PasswordRecoveryLandingPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(PasswordRecoveryLandingPage.class);
            wait.until(ExpectedConditions.visibilityOf(emailInput));
        }
        return sSoleInstance;
    }

    public void setEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void click_send_button() {
        Wrapper.waitForElementEnabled(btnSend);
        btnSend.click();
    }

}
