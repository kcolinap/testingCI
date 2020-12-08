package api.apps.android.nw.operaciones.sube.registrarSube;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SubeRegisterPage extends BasePage {

    public final String
            TEXT_INPUT_PLACEHOLDER = "Número de tarjeta SUBE",
            TEXT_WARNING_INVALIDSUBE = "El número de tarjeta no es correcto",
            TEXT_SUBE_UPPERCASE="SUBE";

    private static SubeRegisterPage sSoleInstance;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Cargar SUBE\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Carg\u00E1 tu SUBE\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Cargá SUBE\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Carga SUBE\")")
    private static MobileElement subtitleSube;

    @AndroidFindBy(className = "android.widget.ImageView")
    private MobileElement subeImage;

    @AndroidFindBy(id = "subeCardInputLabel")
    private MobileElement subeLblInputCard;

    @AndroidFindBy(id = "textinput_error")
    private MobileElement subeErrprInput;

    @AndroidFindBy(id = "cardNumberInput")
    private MobileElement cardNumberInput;

    @AndroidFindBy(id = "nextButton")
    private MobileElement nextButton;

    @AndroidFindBy(id = "cardAliasInput")
    private MobileElement cardAliasInput;

    public static MobileElement getSubtitleSube() {
        return subtitleSube;
    }

    public MobileElement getSubeImage() {
        return subeImage;
    }

    public MobileElement getSubeLblInputCard() {
        return subeLblInputCard;
    }

    public MobileElement getSubeErrprInput() {
        return subeErrprInput;
    }

    public MobileElement getCardNumberInput() {
        return cardNumberInput;
    }

    public MobileElement getNextButton() {
        return nextButton;
    }

    public MobileElement getCardAliasInput() {
        return cardAliasInput;
    }

    private SubeRegisterPage() {
        super();
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        waitForElementVisibility(subtitleSube);
    }

    public synchronized static SubeRegisterPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new SubeRegisterPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(SubeRegisterPage.class);
            wait.until(ExpectedConditions.visibilityOf(subtitleSube));
        }

        return sSoleInstance;
    }

    public SubeRegisterPage typeTextOnSubeCardField(String tarjeta) {
        cardNumberInput.sendKeys(tarjeta);
        return this;
    }

    public SubeRegisterPage typeAliasOnSubeCardField(String alias) {
        cardAliasInput.sendKeys(alias);
        return this;
    }

    public SubeRegisterPage clearSubeCardField() {
        cardNumberInput.clear();
        return this;
    }

    public SubeRegisterPage clearCardAliasField() {
        cardAliasInput.clear();
        return this;
    }

    public void clickOnContinueButton() {
        nextButton.click();
    }

    public boolean continueButtonIsEnabled() {
        return elementIsEnabled(nextButton);
    }

    public void waitForContinueButtonEnabled() {
        waitForElementEnabled(nextButton);
    }

    public void waitForError() {
        waitForElementVisibility(subeErrprInput);
    }

    public String getErrorMessage() {
        return subeErrprInput.getText().trim();
    }

    public boolean buttonBackExists() {
        return elementExists(getBackOrCloseBtn());
    }

    public String getCardNumber() {
        return cardNumberInput.getText().trim();
    }
}
