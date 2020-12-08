package api.apps.android.nw.operaciones.sube;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditAliasPage extends BasePage {

    public final String
            TITLE_TEXT = "Carg\u00E1 tu SUBE",
            TEXT_WARNING_INVALIDSUBE = "El número de tarjeta no es correcto",
            TEXT_SUBE_UPPERCASE="SUBE";

    private static EditAliasPage sSoleInstance;

    @AndroidFindBy(id = "cardAliasTitle")
    private MobileElement cardAliasTitle;

    @AndroidFindBy(id = "cardAliasInput")
    private static MobileElement cardAliasInput;

    @AndroidFindBy(id = "nextButton")
    private MobileElement nextButton;

    public MobileElement getCardAliasTitle() {
        return cardAliasTitle;
    }

    public static MobileElement getCardAliasInput() {
        return cardAliasInput;
    }

    public MobileElement getNextButton() {
        return nextButton;
    }

    private EditAliasPage() {
        super();
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        waitForElementVisibility(cardAliasInput);
    }

    public synchronized static EditAliasPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new EditAliasPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(EditAliasPage.class);
            wait.until(ExpectedConditions.visibilityOf(cardAliasInput));
        }

        return sSoleInstance;
    }



    public void setAlias(String alias) {
        cardAliasInput.clear();
        cardAliasInput.sendKeys(alias);
    }



    public void clickOnContinueButton() {
        Wrapper.waitForElementEnabled(nextButton);
        nextButton.click();
    }

}
