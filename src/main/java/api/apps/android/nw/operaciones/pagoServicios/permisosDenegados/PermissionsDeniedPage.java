package api.apps.android.nw.operaciones.pagoServicios.permisosDenegados;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PermissionsDeniedPage extends BasePage {

    /**
     * Properties Section
     */

    private static PermissionsDeniedPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String MAIN_TITLE_TEXT = "Pago de servicios";
    public final String MESSAGE_TEXT = "Necesitamos tu permiso para que puedas escanear el c\u00F3digo de barras.";
    public final String ACTIVATE_PERMISSIONS_BUTTON_TEXT = "Activar permisos";
    public final String MANUAL_INPUT_LINK_TEXT = "Ingresar c\u00F3digo manualmente";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "image")
    private static MobileElement imgCamera;

    @AndroidFindBy(id = "message")
    private static MobileElement lblMessage;

    @AndroidFindBy(id = "activatePermissions")
    private static MobileElement btnActivatePermissions;

    @AndroidFindBy(id = "manualInputButton")
    private static MobileElement lnkManualInputButton;

    /**
     * Methods Section
     */

    private PermissionsDeniedPage(){
        super();
        waitForElementVisibility(btnActivatePermissions);
    }

    public synchronized static PermissionsDeniedPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new PermissionsDeniedPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(btnActivatePermissions)));
        }
        return sSoleInstance;
    }

    public MobileElement getImgCamera() { return imgCamera; }
    public MobileElement getLblMessage() { return lblMessage; }
    public MobileElement getBtnActivatePermissions() { return btnActivatePermissions; }
    public MobileElement getLnkManualInputButton() { return lnkManualInputButton; }

    public void clickActivatePermissionsButton() { btnActivatePermissions.click(); }
    public void clickManualInputButton() { lnkManualInputButton.click(); }

}
