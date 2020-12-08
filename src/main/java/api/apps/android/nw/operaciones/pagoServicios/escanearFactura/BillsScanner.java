package api.apps.android.nw.operaciones.pagoServicios.escanearFactura;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BillsScanner extends BasePage {

    /**
     * Properties Section
     */

    private static BillsScanner sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String MAIN_TITLE_TEXT = "Escane\u00E1 tu factura";
    public final String SUBTITLE_TEXT = "El c\u00F3digo de barra de tu factura se encuentra en la parte inferior, en el tal\u00F3n de pago.";
    public final String MANUAL_INPUT_BUTTON_TEXT = "Ingresar c\u00F3digo manualmente";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "manualInputButton")
    private static MobileElement btnManualInput;

    /**
     * Methods Section
     */

    private BillsScanner(){
        super();
        waitForElementVisibility(btnManualInput);
    }

    public synchronized static BillsScanner getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new BillsScanner();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(btnManualInput)));
        }
        return sSoleInstance;
    }

    public MobileElement getBtnManualInput() { return btnManualInput; }

    public void clickManualInputButton(){ btnManualInput.click(); }

}
