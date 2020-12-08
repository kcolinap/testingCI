package api.apps.android.nw.tarjetaprepaga.fisica;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TravelNotifyPage extends BasePage {

    private static TravelNotifyPage sSoleInstance;

    public static final String TITLE_TEXT = "Aviso de viaje";
    public static final String MESSAGE_TEXT = "¿Estás por viajar al exterior?";
    public static final String SECONDARY_MESSAGE_TEXT = "Escribinos a hola@tunubi.com con la fecha y los destinos para que activemos el uso de tu Tarjeta Prepaga Visa fuera de Argentina.";

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

    public TravelNotifyPage() {
        super();
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        waitForElementVisibility(mainButton);
    }

    public synchronized static TravelNotifyPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new TravelNotifyPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(TravelNotifyPage.class);
            wait.until(ExpectedConditions.visibilityOf(mainButtonStatic));
        }

        return sSoleInstance;
    }

    public void click_notify(){
        mainButton.click();
    }

}
