package api.apps.android.nw.tarjetaprepaga.virtual;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.tarjetaprepaga.PrepaidCardBasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VirtualPage extends PrepaidCardBasePage {

    private static VirtualPage sSoleInstance;

    public static final String VIRTUAL_CARD_TITLE = "Tarjeta Virtual";
    public static final String FREEZE_MESSAGE_TEXT = "No vas a poder usarla hasta que la descongeles.";

    @AndroidFindBy(id = "cardImage")
    private MobileElement cardImage;

    @AndroidFindBy(id = "cardTitle")
    private static MobileElement cardTitle;

    @AndroidFindBy(id = "cardNumberTitle")
    private MobileElement cardNumberTitle;

    @AndroidFindBy(id = "cardNumberText")
    private MobileElement cardNumberText;

    @AndroidFindBy(id = "cardNumberCopyIcon")
    private MobileElement cardNumberCopyIcon;

    @AndroidFindBy(id = "securityCodeTitle")
    private MobileElement securityCodeTitle;

    @AndroidFindBy(id = "securityCodeText")
    private MobileElement securityCodeText;

    @AndroidFindBy(id = "expirationDataTitle")
    private MobileElement expirationDataTitle;

    @AndroidFindBy(id = "expirationDataText")
    private MobileElement expirationDataText;

    @AndroidFindBy(id = "seeCardInfoButton")
    private MobileElement seeCardInfoButton;

    @AndroidFindBy(id = "cardFreezeSwitch")
    private MobileElement cardFreezeSwitch;

    @AndroidFindBy(id = "shippingDetailButton")
    private MobileElement shippingDetailButton;

    @AndroidFindBy(id = "cardActivationButton")
    private MobileElement cardActivationButton;

    @AndroidFindBy(id = "freezeIcon")
    private MobileElement freezeIcon;

    @AndroidFindBy(id = "freezeMessage")
    private MobileElement freezeMessage;


    public MobileElement getCardImage() {
        return cardImage;
    }

    public MobileElement getCardTitle() {
        return cardTitle;
    }

    public MobileElement getCardNumberTitle() {
        return cardNumberTitle;
    }

    public MobileElement getCardNumberText() {
        return cardNumberText;
    }

    public MobileElement getCardNumberCopyIcon() {
        return cardNumberCopyIcon;
    }

    public MobileElement getSecurityCodeTitle() {
        return securityCodeTitle;
    }

    public MobileElement getSecurityCodeText() {
        return securityCodeText;
    }

    public MobileElement getExpirationDataTitle() {
        return expirationDataTitle;
    }

    public MobileElement getExpirationDataText() {
        return expirationDataText;
    }

    public MobileElement getSeeCardInfoButton() {
        return seeCardInfoButton;
    }

    public MobileElement getCardFreezeSwitch() {
        return cardFreezeSwitch;
    }

    public MobileElement getShippingDetailButton() {
        return shippingDetailButton;
    }

    public MobileElement getCardActivationButton() {
        return cardActivationButton;
    }

    public MobileElement getFreezeIcon() {
        return freezeIcon;
    }

    public MobileElement getFreezeMessage() {
        return freezeMessage;
    }



    public VirtualPage(){
        super();
        waitForElementVisibility(cardTitle);
    }

    public synchronized static VirtualPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new VirtualPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(VirtualPage.class);
            wait.until(ExpectedConditions.visibilityOf(cardTitle));
        }

        return sSoleInstance;
    }

    public void click_card_freeze_switch(){
        waitForElementVisibility(cardFreezeSwitch);
        cardFreezeSwitch.click();
    }
}
