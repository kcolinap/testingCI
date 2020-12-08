package api.apps.android.nw.operaciones.pagoServicios.errorGenerico;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericErrorPage extends BasePage {

    /**
     * Properties Section
     */

    private static GenericErrorPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String MAIN_TITLE_TEXT = "Pago de servicios";
    public final String TITLE_TEXT = "Algo sali\u00F3 mal";
    public final String TRY_AGAIN_BUTTON_TEXT = "Reintentar";
    public final String BACK_TO_HOME_LINK_TEXT = "Volver al inicio";
    public final String SUBTITLE_LINE1_TEXT = "Verific\u00E1 que:";
    public final String SUBTITLE_LINE2_TEXT = "La factura no est\u00E9 vencida o paga.";
    public final String SUBTITLE_LINE3_TEXT = "La empresa est\u00E9 habilitada.";
    public final String SUBTITLE_LINE4_TEXT = "El monto no supere el l\u00EDmite diario.";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "titlePayment")
    private static MobileElement lblMainTitle;

    @AndroidFindBy(id = "errorIcon")
    private static MobileElement errorIcon;

    @AndroidFindBy(id = "mainButton")
    private static MobileElement btnTryAgain;

    @AndroidFindBy(id = "secondaryButton")
    private static MobileElement lnkBackToHome;


    /**
     * Methods Section
     */

    private GenericErrorPage(){
        super();
        waitForElementVisibility(lblTitle);
    }

    public synchronized static GenericErrorPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new GenericErrorPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(lblTitle)));
        }
        return sSoleInstance;
    }

    public MobileElement getLblMainTitle() { return lblMainTitle; }
    public MobileElement getErrorIcon() { return errorIcon; }
    public MobileElement getBtnTryAgain() { return btnTryAgain; }
    public MobileElement getLnkBackToHome() { return lnkBackToHome; }

    public void clickTryAgainButton() { btnTryAgain.click(); }
    public void clickBackToHomeLink() { lnkBackToHome.click(); }

}
