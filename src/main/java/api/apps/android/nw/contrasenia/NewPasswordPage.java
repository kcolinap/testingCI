package api.apps.android.nw.contrasenia;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NewPasswordPage extends BasePage {

    private static NewPasswordPage sSoleInstance;

    public static String
            TITLE_TEXT = "Nueva contraseña",
            SUBTITLE_TEXT = "Creá tu nueva contraseña";

    @AndroidFindBy(id = "forgotPasswordTitle")
    private MobileElement forgotPasswordTitle;

    @AndroidFindBy(id = "passwordInput")
    private static MobileElement passwordInput;

    @AndroidFindBy(id = "digitsValidatorTitle")
    private MobileElement digitsValidatorTitle;

    @AndroidFindBy(id = "uppercaseValidatorTitle")
    private static MobileElement uppercaseValidatorTitle;

    @AndroidFindBy(id = "alphanumericValidatorTitle")
    private static MobileElement alphanumericValidatorTitle;

    @AndroidFindBy(id = "specialCharacterValidatorTitle")
    private static MobileElement specialCharacterValidatorTitle;

    @AndroidFindBy(id = "button")
    private static MobileElement button;

    public MobileElement getForgotPasswordTitle() {
        return forgotPasswordTitle;
    }

    public MobileElement getPasswordInput() {
        return passwordInput;
    }

    public MobileElement getDigitsValidatorTitle() {
        return digitsValidatorTitle;
    }

    public static MobileElement getUppercaseValidatorTitle() {
        return uppercaseValidatorTitle;
    }

    public static MobileElement getAlphanumericValidatorTitle() {
        return alphanumericValidatorTitle;
    }

    public static MobileElement getSpecialCharacterValidatorTitle() {
        return specialCharacterValidatorTitle;
    }

    public static MobileElement getButton() {
        return button;
    }

    public NewPasswordPage() {
      super();
      waitForElementVisibility(passwordInput);
    }

    public synchronized static NewPasswordPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new NewPasswordPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(NewPasswordPage.class);
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
        }
        return sSoleInstance;
    }

   public void set_password(String password){
        passwordInput.sendKeys(password);
   }

   public void click_next_button(){
        Wrapper.waitForElementEnabled(button);
        button.click();
   }

}
