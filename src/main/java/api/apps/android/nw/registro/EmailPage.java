package api.apps.android.nw.registro;

import api.apps.android.Android;
import api.apps.android.nw.basePages.RegistroBasePage;
import api.apps.android.Wrapper;
import api.apps.android.nw.dashboard.DashboardPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmailPage extends RegistroBasePage {

    private static EmailPage sSoleInstance;

    public static final String LBL_SUBTITULO_TEXT = "¿Cuál es tu email?";
    public static final String LBL_TEXT_INPUT_ERROR = "Formato de correo inválido";

    @AndroidFindBy(id = "emailInput")
    private  static MobileElement emailInput;

    @AndroidFindBy(id = "textinput_error")
    private MobileElement textinput_error;

    @AndroidFindBy(id = "nextButton")
    private MobileElement nextButton;

    @AndroidFindBy(id = "alreadyRegisteredLink")
    private MobileElement alreadyRegisteredLink;

    /***  GETS ***/

    public MobileElement getEmailInput() {
        return emailInput;
    }

    public MobileElement getTextinput_error() {
        return textinput_error;
    }

    public MobileElement getNextButton() {
        return nextButton;
    }

    public MobileElement getAlreadyRegisteredLink() {
        return alreadyRegisteredLink;
    }

    /*****************************************/

    public EmailPage(){
        super();
        super.waitForElementVisibility(emailInput);
    }

    public synchronized static EmailPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new EmailPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,15);
            Wrapper.init_elements(EmailPage.class);
            wait.until(ExpectedConditions.visibilityOf(emailInput));
        }
        return sSoleInstance;
    }

    public void setEmail(String email){
        emailInput.click();
        if(!Wrapper.getElementText(emailInput).equals(""))
            emailInput.clear();

        emailInput.sendKeys(email);
    }

    public void click_on_next_button(){
        nextButton.click();
    }

    public void click_title(){
        lblTitle.click();
    }

}
