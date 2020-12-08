package api.apps.android.nw.perfil.seguridad.claveNubi.olvido;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotPinPage extends BasePage {

    private static ForgotPinPage sSoleInstance;

    @AndroidFindBy(id = "sendSmsButton")
    private static MobileElement sendSmsButton;

    @AndroidFindBy(id = "message")
    private MobileElement lblMessage;

    public ForgotPinPage() {
        super();
        waitForElementVisibility(sendSmsButton);
    }

    public synchronized static ForgotPinPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new ForgotPinPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(sendSmsButton)));
        }

        return sSoleInstance;
    }

    public void clickSendSMS() {
        sendSmsButton.click();
    }

}
