package api.apps.android.nw.tarjetaprepaga.fisica;

import api.apps.android.Android;
import api.apps.android.nw.common.CommonPinPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateExtractionPinPage extends CommonPinPage {

    private static CreateExtractionPinPage sSoleInstance;

    public static final String TITLE_TEXT = "PIN de Extracción";
    public static final String SUBTITLE_CREATION_TEXT = "Creá un PIN para cajeros automáticos";
    public static final String SUBTITLE_CONFIRMATION_TEXT = "Confirmá tu nuevo PIN";
    public static final String SUBTITLE_CHANGE_TEXT = "Ingresá un nuevo PIN";
    public static final String ERROR_MESSAGE_TEXT = "Los números ingresados no coinciden.";

    @AndroidFindBy(id = "enterNewPinLabel")
    private static MobileElement enterNewPinLabel;

    @AndroidFindBy(id = "errorMessage")
    private MobileElement errorMessage;

    public MobileElement getErrorMessage() {
        return errorMessage;
    }

    public MobileElement getEnterNewPinLabel() {
        return enterNewPinLabel;
    }

    public CreateExtractionPinPage() {
        super();
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        waitForElementVisibility(enterNewPinLabel);
    }

    public synchronized static CreateExtractionPinPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new CreateExtractionPinPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(enterNewPinLabel)));
        }

        return sSoleInstance;
    }

}
