package api.apps.android.nw.perfil;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.perfil.datospersonales.DatosPersonalesPage;
import api.apps.android.nw.perfil.seguridad.SecurityPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PerfilPage extends BasePage {

    private AppiumDriver<MobileElement> driver;
    public WebDriverWait wait = new WebDriverWait(Android.driver,10);
    private static PerfilPage sSoleInstance;

    public static String TITLE_TEXT = "Perfil";

    @AndroidFindBy(id = "profileName")
    protected static MobileElement name;

    @AndroidFindBy(id = "profileUserName")
    private MobileElement userName;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Tus datos\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Datos Personales\")")
    private MobileElement lnkPersonalData;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Seguridad\")")
    private MobileElement lnkSecurity;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Preguntas frecuentes\")")
    private MobileElement lnkFAQ;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Chat\")")
    private MobileElement lnkChat;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Términos y condiciones\")")
    private MobileElement lnkTermsAndConditions;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Cerrar sesión\")")
    private MobileElement closeSession;

    public static MobileElement getName() {
        return name;
    }

    public MobileElement getUserName() {
        return userName;
    }

    public MobileElement getLnkPersonalData() {
        return lnkPersonalData;
    }

    public MobileElement getLnkSecurity() {
        return lnkSecurity;
    }

    public MobileElement getLnkTermsAndConditions() {
        return lnkTermsAndConditions;
    }

    public MobileElement getLnkFAQ() {
        return lnkFAQ;
    }

    public MobileElement getCloseSession() {
        return closeSession;
    }

    public MobileElement getLnkChat() {
        return lnkChat;
    }

    public PerfilPage() {
        super();
        wait.until(ExpectedConditions.visibilityOf(name));
    }

    public synchronized static PerfilPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new PerfilPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(PerfilPage.class);
            wait.until(ExpectedConditions.visibilityOf(name));
        }
        return sSoleInstance;
    }


    public SecurityPage tapLnkSeguridad() {
        lnkSecurity.click();
        return new SecurityPage();
    }

    public DatosPersonalesPage tapLnkDatosPersonales() {
        lnkPersonalData.click();
        return new DatosPersonalesPage();
    }

    public void tapLnkCerrarSesion() {
        closeSession.click();
    }

    public void tapLnkFaq(){
        actions.singleTap(lnkFAQ);
    }

}
