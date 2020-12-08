package api.apps.android.nw.tarjetaprepaga.fisica.denunciar_tarjeta;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmErrorPage extends BasePage {

    private static ConfirmErrorPage sSoleInstance;

    public static final String TITLE_TEXT = "Tarjeta Prepaga";
    public static final String SUBTITLE_TEXT = "¡Uy, algo salió mal!";
    public static final String DISCLAIMER_TEXT = "Hablá con nosotros para hacer la denuncia de tu tarjeta.";


    @AndroidFindBy(id = "emoji")
    private MobileElement emoji;

    @AndroidFindBy(id = "disclaimerText")
    private MobileElement disclaimerText;

    @AndroidFindBy(id = "mainButton")
    private MobileElement mainButton;

    @AndroidFindBy(id = "secondaryButton")
    private static MobileElement secondaryButton;


    public ConfirmErrorPage() {
        super();
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        waitForElementVisibility(secondaryButton);
    }

    public synchronized static ConfirmErrorPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new ConfirmErrorPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(secondaryButton)));
        }

        return sSoleInstance;
    }

    public MobileElement getEmoji() {
        return emoji;
    }

    public MobileElement getDisclaimerText() {
        return disclaimerText;
    }

    public MobileElement getMainButton() {
        return mainButton;
    }

    public static MobileElement getSecondaryButton() {
        return secondaryButton;
    }
}
