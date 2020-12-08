package api.apps.android.nw.perfil.seguridad.claveNubi.cambio;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.common.CommonPinPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangeNubiPassPage extends CommonPinPage {

    private static ChangeNubiPassPage sSoleInstance;

    public static String CHANGE_PIN_TITLE_TEXT = "Cambiá tu Clave Nubi";
    public static String SET_CURRENT_PIN__SUBTITLE_TEXT = "Ingresá tu Clave Nubi actual";
    public static String SET_NEW_PIN__SUBTITLE_TEXT = "Ingresá tu nueva Clave Nubi";
    public static String CONFIRM_PIN__SUBTITLE_TEXT = "Confirmá tu nueva Clave Nubi";
    public static String WRONG_NUBI_PASSWORD_TEXT = "La Clave Nubi no es correcta";
    public static String UNMATCH_NUBI_PASSWORD_TEXT = "La Clave Nubi no coincide.";

    public static String SNACKBAR_TEXT = "¡Uy, superaste el número de intentos! Cambiá tu Clave Nubi para continuar.";
    public static String SNACKBAR_TEXT_OLD = "¡Uy, superaste el número de intentos! Cambiá tu PIN para continuar.";

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "enterCurrentPinLabel")
    @AndroidFindBy(id = "enterNewPinLabel")
    @AndroidFindBy(id = "message")
    private static MobileElement lblChangePin;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "closeButton") // v0.9.11
    @AndroidFindBy(id = "backButton") // v0.9.10
    private MobileElement closeButton;

    @AndroidFindBy(id = "snackbar_text")
    private MobileElement snackbar_text;

    @AndroidFindBy(id = "snackbar_action")
    private MobileElement snackbar_action;

    public MobileElement getSnackbar_text() {
        return snackbar_text;
    }

    public MobileElement getSnackbar_action() {
        return snackbar_action;
    }


    public MobileElement getCloseButton() {
        return closeButton;
    }

    public ChangeNubiPassPage() {
        super();
        waitForElementVisibility(firstDigit);
    }

    public synchronized static ChangeNubiPassPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new ChangeNubiPassPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(ChangeNubiPassPage.class);
            wait.until(ExpectedConditions.visibilityOf(firstDigit));
        }

        return sSoleInstance;
    }

    public void click_snackbar_action(){
        snackbar_action.click();
    }
    public String getLblChangePinText() {
        return lblChangePin.getText();
    }


    public boolean firstDigitExists() {
        return elementExists(firstDigit);
    }

    public boolean secondDigitExists() {
        return elementExists(secondDigit);
    }

    public boolean thirdDigitExists() {
        return elementExists(thirdDigit);
    }

    public boolean forthDigitExists() {
        return elementExists(fourthDigit);
    }

    public void clickFirstDigit() {
        firstDigit.click();
    }

    public boolean expectedTextInFirstDigit() {
        return validatePinMask(firstDigit);
    }
    public boolean expectedTextInSecondDigit() {
        return validatePinMask(secondDigit);
    }

    public boolean expectedTextInThirdDigit() {
        return validatePinMask(thirdDigit);
    }

    private boolean validatePinMask(MobileElement element) {
        if (element.getText().equals("") || element.getText().equals("•"))
            return true;
        return false;
    }


}
