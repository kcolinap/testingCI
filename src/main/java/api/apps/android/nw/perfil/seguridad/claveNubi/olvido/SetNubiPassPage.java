package api.apps.android.nw.perfil.seguridad.claveNubi.olvido;

import api.apps.android.Android;
import api.apps.android.nw.common.CommonPinPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SetNubiPassPage extends CommonPinPage {

    private static SetNubiPassPage sSoleInstance;

    public static String TITLE_TEXT = "Me olvidé la Clave Nubi";
    public static String SET_NEW_NUBI_PASSWORD_SUBTITLE_TEXT = "Ingresá tu nueva Clave Nubi";
    public static String CONFIRM_NUBI_PASS_SUBTITLE_TEXT = "Confirmá tu nueva Clave Nubi";

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "enterCurrentPinLabel")
    @AndroidFindBy(id = "enterNewPinLabel")
    @AndroidFindBy(id = "message")
    private static MobileElement lblChangePin;

    public SetNubiPassPage(){
        super();
        waitForElementVisibility(firstDigit);
    }

    public synchronized static SetNubiPassPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new SetNubiPassPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(firstDigit)));
        }
        return sSoleInstance;
    }

}
