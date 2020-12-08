package api.apps.android.nw.perfil.seguridad.claveNubi.olvido;

import api.apps.android.Android;
import api.apps.android.nw.common.CommonPinPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SetSmsCodePage extends CommonPinPage {

    private static SetSmsCodePage sSoleInstance;

    public static String
            TITLE_TEXT = "Me olvidé la Clave Nubi",
            MESSAGE_TEXT = "Ingresá el código que te mandamos";

    @AndroidFindBy(id = "resendButton")
    private static MobileElement resendButton;

    @AndroidFindBy(id = "message")
    private MobileElement message;

    public static MobileElement getResendButton() {
        return resendButton;
    }

    public MobileElement getMessage() {
        return message;
    }

    public SetSmsCodePage() {
        super();
        waitForElementVisibility(resendButton);
    }

    public synchronized static SetSmsCodePage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new SetSmsCodePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(resendButton)));
        }

        return sSoleInstance;
    }

}
