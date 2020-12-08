package api.apps.android.nw.operaciones.pagoServicios.pagoDemorado;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DelayedPaymentPage extends BasePage {

    /**
     * Properties Section
     */

    private static DelayedPaymentPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String EXPECTED_PROCESSING_TEXT = "Procesando...";
    public final String EXPECTED_WAIT_FEW_SECONDS_TEXT = "Esper\u00E1 unos segundos m\u00E1s";
    public final String EXPECTED_WAIT_A_LITTLE_BIT_TEXT = "Uh.. nos est\u00E1 tomando m\u00E1s de lo planeado";
    public final String EXPECTED_WE_DONT_GIVE_UP_TEXT = "No nos damos por vencidos, seguimos procesando";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "animationView")
    private static MobileElement icnProcessingIcon;

    @AndroidFindBy(id = "delayedMessageTitle")
    private static MobileElement lblDelayedMessageTitle;

    @AndroidFindBy(id = "delayedMessageText")
    private static MobileElement lblDelayedMessage;

    /**
     * Methods Section
     */

    private DelayedPaymentPage(){
        super();
        waitForElementVisibility(lblDelayedMessageTitle);
    }

    public synchronized static DelayedPaymentPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new DelayedPaymentPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(lblDelayedMessageTitle)));
        }
        return sSoleInstance;
    }

    public boolean isProcessingIconDisplayed() { return icnProcessingIcon.isDisplayed(); }

    public String getDelayedMessageTitleText() { return lblDelayedMessageTitle.getText(); }
    public String getDelayedMessageText() { return lblDelayedMessage.getText(); }

    public void waitForDelayedMessageVisibility() { waitForElementVisibility(lblDelayedMessage); }


}
