package api.apps.android.nw.perfil.seguridad;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecurityPage extends BasePage {

    private static SecurityPage sSoleInstance;

    public static String TITLE_TEXT = "Seguridad";

    @AndroidFindBy(id = "securityPinSection")
    private static MobileElement securityPinSection;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Cambiar PIN\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"¿Querés cambiarlo?\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"¿Querés cambiarla?\")")
    private MobileElement lnkChangeNubiPass;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Olvidé mi PIN\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"¿Te la olvidaste?\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"¿Te lo olvidaste?\")")
    private MobileElement lnkForgotNubiPass;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Cambiar contraseña\")")
    @AndroidFindBy(xpath = "//android.widget.ImageView[last()]/..")
    private MobileElement lnkChangePassword;

    public static MobileElement getSecurityPinSection() {
        return securityPinSection;
    }

    public MobileElement getLnkChangeNubiPass() {
        return lnkChangeNubiPass;
    }

    public MobileElement getLnkForgotNubiPass() {
        return lnkForgotNubiPass;
    }

    public MobileElement getLnkChangePassword() {
        return lnkChangePassword;
    }

    public SecurityPage() {
        super();
        waitForElementVisibility(securityPinSection);
    }

    public synchronized static SecurityPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new SecurityPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(SecurityPage.class);
            wait.until(ExpectedConditions.visibilityOf(securityPinSection));
        }
        return sSoleInstance;
    }

    public void tapLnkChangePassword() throws Exception {
        lnkChangePassword.click();
    }

    public void tapLnkChangeNubiPass() {
        lnkChangeNubiPass.click();
    }

    public void tapLnkForgotNubiPass() {
        lnkForgotNubiPass.click();
    }

}
