package api.apps.android.nw.operaciones.pagoServicios;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ServicesPaymentPage extends BasePage {

    private static ServicesPaymentPage sSoleInstance;

    public static final String
            SUBTITLE_TEXT = "Pagá tus facturas",
            MESSAGE_TEXT = "Escaneá la factura o ingresá los datos manualmente.",
            SCAN_TEXT = "Escanear factura",
            EMPRESAS_TEXT = "Ver empresas habilitadas";

    @AndroidFindBy(id = "emoji")
    private static MobileElement emoji;

    @AndroidFindBy(id = "message")
    private static MobileElement message;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Escanear factura\")")
    private MobileElement escanearFactura;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Ver empresas habilitadas\")")
    private MobileElement verEmpresasHabilitadas;

    public ServicesPaymentPage(){
        super();
        wait.until(ExpectedConditions.visibilityOf(emoji));
    }

    public synchronized static ServicesPaymentPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new ServicesPaymentPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(emoji)));
        }
        return sSoleInstance;
    }

    public MobileElement getEmoji() {
        return emoji;
    }

    public static MobileElement getMessage() { return message;  }

    public MobileElement getEscanearFactura() { return escanearFactura; }

    public MobileElement getVerEmpresasHabilitadas() { return verEmpresasHabilitadas; }

    public void clickScanCodeBarButton() { escanearFactura.click(); }

    public void clickShowAvailableCompaniesLink() { verEmpresasHabilitadas.click(); }
}
