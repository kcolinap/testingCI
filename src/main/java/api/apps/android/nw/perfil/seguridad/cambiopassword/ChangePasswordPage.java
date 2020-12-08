package api.apps.android.nw.perfil.seguridad.cambiopassword;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangePasswordPage extends BasePage {

    public static final String LBL_TITLE_TEXT = "Nueva contraseña";
    public static final String LBL_SUBTITLE_CURRENT_PASSWORD = "Ingresá tu contraseña actual|Nueva contraseña";
    public static final String LBL_SUBTITLE_NEW_PASSWORD = "Ingresá tu nueva contraseña|Nueva contraseña" ;
    public static final String PASSWORD = "Test-0000";
    public static final String LBL_INVALID_PASSWORD = "Parece que no es la correcta";
    public static final String TEXT_CHANGE_SUCESSFUL = "¡Listo! Cambiaste tu contraseña";
    private static ChangePasswordPage sSoleInstance;

    @AndroidFindBy(id = "passwordInput")
    private MobileElement passwordInput;

    @AndroidFindBy(id = "textinput_error")
    private MobileElement textinput_error;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "mainButton")
    @AndroidFindBy(id = "nextButton")
    @AndroidFindBy(id = "button")
    private static MobileElement button;

    @AndroidFindBy(id = "validationTitle")
    private MobileElement validationTitle;

    @AndroidFindBy(id = "emoji")
    private MobileElement emoji;

    @AndroidFindBy(id = "message")
    private MobileElement message;

    @AndroidFindBy(xpath = "//android.view.ViewGroup/android.widget.TextView[2]")
    private MobileElement subTitleChangePassword;

    public MobileElement getEmoji() {
        return emoji;
    }

    public MobileElement getMessage() {
        return message;
    }

    public MobileElement getSubTitleChangePassword() {
        return subTitleChangePassword;
    }

    public MobileElement getValidationTitle() {
        return validationTitle;
    }

    public MobileElement getPasswordInput() {
        return passwordInput;
    }

    public MobileElement getTextinput_error() {
        return textinput_error;
    }

    public MobileElement getButton() {
        return button;
    }

    public ChangePasswordPage() {
        super();
        waitForElementVisibility(button);
    }

    public synchronized static ChangePasswordPage getInstance() {
        if (sSoleInstance == null){
            sSoleInstance = new ChangePasswordPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(ChangePasswordPage.class);
            wait.until(ExpectedConditions.visibilityOf(button));
        }
        return sSoleInstance;
    }

    public void setPassword(String pass) {
        passwordInput.sendKeys(pass);
        hideKeyboardIfVisible();
    }

    public void clickNextButton() {
        waitForElementEnabled(button);
        button.click();
    }

    public void click_back_to_profile(){
        waitForElementEnabled(button);
        button.click();
    }
}
