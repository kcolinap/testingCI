package api.apps.android.nw.tarjetaprepaga.fisica;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.tarjetaprepaga.landing.NoPrepaidCardPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LandingPhysicalPage extends BasePage {

    private static LandingPhysicalPage sSoleInstance;

    public static final String PHYSICAL_CARD_TITLE = "Tarjeta Nubi Visa";
    public static final String CREATE_PIN_TEXT = "Creá tu PIN de extracción";
    public static final String CHANGE_PIN_TEXt = "Cambiá tu PIN de extracción";
    public static final String ENABLED_SWITCH_TITLE_TEXT = "Congelar tarjeta";
    public static final String DISABLED_SWITCH_TITLE_TEXT = "Descongelar tarjeta";
    public static final String FREEZE_MESSAGE_TEXT = "No vas a poder usarla hasta que la descongeles.";


    @AndroidFindBy(id = "cardImage")
    private MobileElement cardImage;

    @AndroidFindBy(id = "cardTitle")
    private static MobileElement cardTitle;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[1]//android.widget.ImageView")
    private MobileElement travelButtonIcon;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]//android.widget.ImageView")
    private MobileElement createPinButtonIcon;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[1]//android.widget.TextView")
    private MobileElement travelButtonText;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]//android.widget.TextView")
    private MobileElement createPinButtonText;

    @AndroidFindBy(id = "switchTitle")
    private MobileElement switchTitle;

    @AndroidFindBy(id = "cardFreezeSwitch")
    private MobileElement cardFreezeSwitch;

    @AndroidFindBy(id = "freezeIcon")
    private MobileElement freezeIcon;

    @AndroidFindBy(id = "freezeMessage")
    private MobileElement freezeMessage;

    @AndroidFindBy(id = "prepaidCardLossReportLink")
    private MobileElement prepaidCardLossReportLink;

    public MobileElement getPrepaidCardLossReportLink() {
        return prepaidCardLossReportLink;
    }

    public MobileElement getFreezeIcon() {
        return freezeIcon;
    }

    public MobileElement getFreezeMessage() {
        return freezeMessage;
    }

    public LandingPhysicalPage() {
        super();
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        waitForElementVisibility(cardTitle);
    }

    public synchronized static LandingPhysicalPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new LandingPhysicalPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,20);
            Wrapper.init_elements(LandingPhysicalPage.class);
            wait.until(ExpectedConditions.visibilityOf(cardTitle));

        }

        return sSoleInstance;
    }

    public MobileElement getCardImage() {
        return cardImage;
    }

    public MobileElement getCardTitle() {
        return cardTitle;
    }

    public MobileElement getTravelButtonIcon() {
        return travelButtonIcon;
    }

    public MobileElement getCreatePinButtonIcon() {
        return createPinButtonIcon;
    }

    public MobileElement getTravelButtonText() {
        return travelButtonText;
    }

    public MobileElement getCreatePinButtonText() {
        return createPinButtonText;
    }

    public MobileElement getSwitchTitle() {
        return switchTitle;
    }

    public MobileElement getCardFreezeSwitch() {
        return cardFreezeSwitch;
    }


    public void click_freeze_card_switch(){
        cardFreezeSwitch.click();
    }

    public void click_create_update_pin(){
        createPinButtonIcon.click();
    }

    public void click_travel_notify(){
        travelButtonIcon.click();
    }

    public  void click_report_card(){
        prepaidCardLossReportLink.click();
    }

}
