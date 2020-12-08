package api.apps.android.nw.operaciones.cashin.metodos;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RapiPagoPage extends BasePage {

    private static RapiPagoPage sSoleInstance;

    public static final String
            TITLE_TEXT = "Cargá con efectivo",
            CODE_DESCRIPTION_TEXT = "Para cargar efectivo en tu cuenta acercate a una sucursal de Rapipago y comentale al cajero que tenés un código Nubi.",
            DISCLAIMER_TEXT = "Podés compartir tu código Nubi para que carguen efectivo en tu cuenta.",
            CODE_TITLE_TEXT = "Tu código Nubi",
            CODE_SUBTITLE_TEXT = "Mostrá este código junto con tu DNI.",
            SNACKBAR_TEXT = "¡Listo! Copiaste tu código de Rapipago.";

    @AndroidFindBy(id = "cashInCodeTitle")
    private MobileElement cashInCodeTitle;

    @AndroidFindBy(id = "cashIcon")
    private MobileElement cashIcon;

    @AndroidFindBy(id = "rapipagoCodeDescription")
    private MobileElement rapipagoCodeDescription;

    @AndroidFindBy(id = "disclaimerText")
    private MobileElement disclaimerText;

    @AndroidFindBy(id = "rapipagoCodeTitle")
    private static MobileElement rapipagoCodeTitle;

    @AndroidFindBy(id = "rapipagoCode")
    private MobileElement rapipagoCode;

    @AndroidFindBy(id = "rapipagoCodeCopy")
    private MobileElement rapipagoCodeCopy;

    @AndroidFindBy(id = "shareButton")
    private MobileElement shareButton;

    @AndroidFindBy(id = "snackbar_text")
    private MobileElement snackbar_text;

    public MobileElement getSnackbar_text() {
        return snackbar_text;
    }

    public MobileElement getRapipagoCodeCopy() {
        return rapipagoCodeCopy;
    }

    public MobileElement getShareButton() {
        return shareButton;
    }

    public RapiPagoPage(){
        super();
        wait.until(ExpectedConditions.visibilityOf(rapipagoCodeTitle));
        Wrapper.waitForElementVisibility(rapipagoCodeCopy);
    }

    public synchronized static RapiPagoPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new RapiPagoPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(RapiPagoPage.class);
            wait.until(ExpectedConditions.visibilityOf(rapipagoCodeTitle));
        }
        return sSoleInstance;
    }

    public void click_copyRapipagoCode(){
        actions.singleTap(rapipagoCodeCopy);
    }

    public MobileElement getCashInCodeTitle() { return cashInCodeTitle; }

    public MobileElement getCashIcon() { return cashIcon; }

    public MobileElement getRapipagoCodeDescription() { return rapipagoCodeDescription; }

    public MobileElement getDisclaimerText() { return disclaimerText; }

    public static MobileElement getRapipagoCodeTitle() { return rapipagoCodeTitle; }

    public MobileElement getRapipagoCode() { return rapipagoCode; }

}
