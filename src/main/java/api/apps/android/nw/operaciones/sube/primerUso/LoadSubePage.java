package api.apps.android.nw.operaciones.sube.primerUso;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoadSubePage extends BasePage {

    private static LoadSubePage sSoleInstance;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Cargar SUBE\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Carg\u00E1 tu SUBE\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Cargá SUBE\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Carga SUBE\")")
    private static MobileElement subtitleSube;

    @AndroidFindBy(id = "addSubeButtonInNoCardsView")
    private static MobileElement loadButton;

    private LoadSubePage() {
        super();
        waitForElementVisibility(loadButton);
    }

    public synchronized static LoadSubePage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new LoadSubePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(LoadSubePage.class);
            wait.until(ExpectedConditions.visibilityOf(loadButton));
        }

        return sSoleInstance;
    }

    public void clickOnAddSubeButton() {
        loadButton.click();
    }

}
