package api.apps.android.nw.tarjetaprepaga.fisica;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuccessfulExtractionPinPage extends BasePage {

    private static SuccessfulExtractionPinPage sSoleInstance;

    public static final String MESSAGE_CREATION_TEXT = "¡Creaste tu PIN de extracción!";
    public static final String MESSAGE_UPDATE_TEXT = "¡Cambiaste tu PIN de extracción!";
    public static final String SECONDARY_MESSAGE_TEXT = "Ya podés usarlo para extraer efectivo desde cualquier cajero Banelco o Link.";

    @AndroidFindBy(id = "emoji")
    private MobileElement emoji;

    @AndroidFindBy(id = "message")
    private MobileElement message;

    @AndroidFindBy(id = "secondaryMessage")
    private MobileElement secondaryMessage;

    @AndroidFindBy(id = "mainButton")
    private static MobileElement mainButtonStatic;

    @AndroidFindBy(id = "mainButton")
    private MobileElement mainButton;

    public MobileElement getEmoji() {
        return emoji;
    }

    public MobileElement getMessage() {
        return message;
    }

    public MobileElement getSecondaryMessage() {
        return secondaryMessage;
    }

    public static MobileElement getMainButtonStatic() {
        return mainButtonStatic;
    }

    public MobileElement getMainButton() {
        return mainButton;
    }

    public SuccessfulExtractionPinPage() {
        super();
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        waitForElementVisibility(mainButtonStatic);
    }

    public synchronized static SuccessfulExtractionPinPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new SuccessfulExtractionPinPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(mainButtonStatic)));
        }

        return sSoleInstance;
    }

    public LandingPhysicalPage click_finish_button(){
        mainButton.click();
        return LandingPhysicalPage.getInstance();
    }

}
