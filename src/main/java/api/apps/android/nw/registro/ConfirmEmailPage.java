package api.apps.android.nw.registro;

import api.apps.android.Android;
import api.apps.android.nw.basePages.RegistroBasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmEmailPage extends RegistroBasePage {

    private static ConfirmEmailPage sSoleInstance;

    public static final String LBL_MESSAGE = "¡Te mandamos un mail!";
    public static final String LBL_MESSAGE_REGISTERED = "Mmm… ese email ya está registrado";
    public static final String LBL_DISCLAIMER_TEXT = "Inici\u00E1 sesi\u00F3n ahora o revis\u00E1 que est\u00E9 bien escrito.";

    @AndroidFindBy(id = "emailSentIcon")
    private MobileElement icnEmailSent;

    @AndroidFindBy(id = "icon")
    private MobileElement icon;

    @AndroidFindBy(id = "message")
    private MobileElement message;

    @AndroidFindBy(id = "emailSentDisclaimer")
    private MobileElement emailSentDisclaimer;

    @AndroidFindBy(id = "disclaimer")
    private MobileElement disclaimer;

    @AndroidFindBy(id = "actionButtonPrimary")
    private static MobileElement actionButtonPrimary;

    @AndroidFindBy(id = "actionButtonLink")
    private MobileElement actionButtonLink;

    /*** GETS ***/
    public MobileElement getIcnEmailSent() {
        return icnEmailSent;
    }

    public MobileElement getMessage() {
        return message;
    }

    public MobileElement getEmailSentDisclaimer() {
        return emailSentDisclaimer;
    }

    public MobileElement getActionButtonPrimary() {
        return actionButtonPrimary;
    }

    public MobileElement getActionButtonLink() {
        return actionButtonLink;
    }

    public MobileElement getIcon() {
        return icon;
    }

    public MobileElement getDisclaimer() {
        return disclaimer;
    }

    /***************************************************/

    public ConfirmEmailPage(){
        super();
        waitForElementVisibility(actionButtonPrimary);
    }

    public synchronized static ConfirmEmailPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new ConfirmEmailPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(actionButtonPrimary)));
        }
        return sSoleInstance;
    }


    public void click_open_mail(){
        super.waitForElementEnabled(actionButtonPrimary);
        actionButtonPrimary.click();
    }

    public void click_update_mail(){
       actionButtonLink.click();
    }
}
