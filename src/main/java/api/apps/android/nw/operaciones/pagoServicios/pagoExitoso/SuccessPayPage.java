package api.apps.android.nw.operaciones.pagoServicios.pagoExitoso;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuccessPayPage extends BasePage {

    public final String TEXT_LISTO = "\u00A1Listo\u0021";
    public final String TEXT_CONFIRMACION_PAGO = "Pod\u00e9s consultar el detalle de tu pago en \u00DAltimos movimientos y ver el comprobante";
    public final String TEXT_FINALIZAR = "Finalizar";
    public final String TEXT_DESCARGAR_COMPROBANTE = "Descargar comprobante";

    private static SuccessPayPage sSoleInstance;

    @AndroidFindBy(id = "animationView")
    private static MobileElement animationView;

    @AndroidFindBy(id = "message")
    private static MobileElement message;

    @AndroidFindBy(id = "finishButton")
    private static MobileElement finishButton;

    @AndroidFindBy(id = "ghostButton")
    private static MobileElement goHome;

    private SuccessPayPage() {
        super();
        waitForElementVisibility(goHome);
    }

    public synchronized static SuccessPayPage getInstance() {
        if (sSoleInstance == null) {
            sSoleInstance = new SuccessPayPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver, 10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(goHome)));
        }
        return sSoleInstance;
    }

    public String getSuccessPaymentMessageText() {
        return message.getText().trim();
    }

    public String getFinishButtonText() {
        return finishButton.getText().trim();
    }

    public void clickOnFinishButton() {
        finishButton.click();
    }

    public void clickOnGoHomeButton() {
        goHome.click();
    }

    public boolean isBtnFinishEnabled() { return elementIsEnabled(finishButton); }

}
