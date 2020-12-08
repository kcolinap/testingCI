package api.apps.android.nw.operaciones.pagoServicios.codigoManual;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManualInputPage extends BasePage {

    /**
     * Properties Section
     */

    private static ManualInputPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String MAIN_TITLE_TEXT = "Pago de servicios";
    public final String PLACE_HOLDER_INPUT_TEXT = "Ingres\u00E1 el c\u00F3digo de la factura";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "invoiceNumberInput")
    private static MobileElement txtInvoiceNumberInput;

    @AndroidFindBy(id = "manualCodeInputButton")
    private static MobileElement btnManualCodeInputButton;


    /**
     * Methods Section
     */

    private ManualInputPage(){
        super();
        waitForElementVisibility(txtInvoiceNumberInput);
    }

    public synchronized static ManualInputPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new ManualInputPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(txtInvoiceNumberInput)));
        }
        return sSoleInstance;
    }

    public MobileElement getTxtInvoiceNumberInput() { return txtInvoiceNumberInput; }
    public MobileElement getBtnManualCodeInputButton() { return btnManualCodeInputButton; }

    public void typeTextIntoInvoiceNumberInput(String billCode) { txtInvoiceNumberInput.sendKeys(billCode); }
    public void clearTextIntoInvoiceNumberInput() { txtInvoiceNumberInput.clear(); }
    public void clickManualCodeInputButton() { btnManualCodeInputButton.click(); }

}
