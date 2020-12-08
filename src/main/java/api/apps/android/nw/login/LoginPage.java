package api.apps.android.nw.login;

import api.apps.android.Actions;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class    LoginPage extends BasePage {

    private static LoginPage sSoleInstance;

    private static Actions actions = new Actions();

    public static String CREATE_ACCOUNT_TEXT = "¿No tenés cuenta? Registrate";
    public static String FORGOT_PASSWORD_TEXT = "¿Te olvidaste la contraseña?";
    public static String WRONG_DATA_TEXT = "Datos incorrectos";

    public static String SNACKBAR_TEXT = "¡Uy, superaste el número de intentos! Cambiá tu contraseña para continuar.";

    @AndroidFindBy(id = "loginButton")
    private static MobileElement loginBtn;

    @AndroidFindBy(id = "linkRegister")
    private MobileElement registerBtn;

    @AndroidFindBy(id = "linkRememberPassword")
    private MobileElement forgotPassLink;

    @AndroidFindBy(id = "userInput")
    private MobileElement userInput;

    @AndroidFindBy(id = "passwordInput")
    private MobileElement passwordInput;

    @AndroidFindBy(id = "textinput_error")
    private MobileElement textinput_error;

    @AndroidFindBy(id = "snackbar_text")
    private MobileElement snackbar_text;

    @AndroidFindBy(id = "snackbar_action")
    private MobileElement snackbar_action;


    /** GETS **/
    public static MobileElement getLoginBtn() {
        return loginBtn;
    }

    public MobileElement getSnackbar_text() {
        return snackbar_text;
    }

    public MobileElement getSnackbar_action() {
        return snackbar_action;
    }

    public MobileElement getRegisterBtn() {
        return registerBtn;
    }

    public MobileElement getForgotPassLink() {
        return forgotPassLink;
    }

    public MobileElement getUserInput() {
        return userInput;
    }

    public MobileElement getPasswordInput() {
        return passwordInput;
    }

    public MobileElement getTextinput_error() {
        return textinput_error;
    }

    public LoginPage() {
        super();
        waitForElementVisibility(getLoginBtn());
    }

    public synchronized static LoginPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new LoginPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,60);
            Wrapper.init_elements(LoginPage.class);
            wait.until(ExpectedConditions.visibilityOf(loginBtn));
        }
        return sSoleInstance;
    }

    public boolean isDisplayed() {
        try {
            return loginBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean loginBtnIsEnabled() {
        return getLoginBtn().getAttribute("enabled").equals("true");
    }

    public void typeUsername(String name) {
        userInput.clear();
        userInput.sendKeys(name);
    }

    public void typePassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        //wait.until(ExpectedConditions.attributeContains(loginBtn,"enabled","true"));
       Wrapper.waitForElementEnabled(loginBtn);
        actions.singleTap(loginBtn);
    }

    public void clickRegister() {
        registerBtn.click();
    }

    public String getRegisterLinkText() {
        return registerBtn.getText();
    }

    public void clickForgotPassword() {
        forgotPassLink.click();
    }

    public void click_snackbar_action(){
        waitForElementVisibility(snackbar_action);
        snackbar_action.click();
    }


    public void login (String name, String password) {
        hideKeyboardIfVisible();
        typeUsername(name);
        typePassword(password);
        hideKeyboardIfVisible();
        clickLogin();
    }

}
