package api.apps.android.nw.operaciones.sube.recargaExitosa;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuccessSubeRechargePage extends BasePage {

    public static final String
            TEXTO_LISTO = "\u00A1Carga exitosa\u0021",
            TEXTO_ACREDITACION = "¡Cargaste $60 en la SUBE de";

    private static SuccessSubeRechargePage sSoleInstance;

    @AndroidFindBy(id = "emoji")
    private MobileElement emoji;

    @AndroidFindBy(id = "message")
    private MobileElement message;

    @AndroidFindBy(id = "mainButton")
    private static MobileElement mainButton;

    @AndroidFindBy(id = "finishButton")
    private MobileElement finishButton;

    public MobileElement getEmoji() {
        return emoji;
    }

    public MobileElement getMessage() {
        return message;
    }

    public static MobileElement getMainButton() {
        return mainButton;
    }

    public MobileElement getFinishButton() {
        return finishButton;
    }

    private SuccessSubeRechargePage() {
        super();
        waitForElementVisibility(mainButton);
    }

    public synchronized static SuccessSubeRechargePage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new SuccessSubeRechargePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(mainButton)));
        }

        return sSoleInstance;
    }

    public void clickFinishButton() {
        mainButton.click();
    }

    public boolean existsFinishButton() {
        return elementExists(finishButton);
    }

    public void waitForMessage() {
        waitForElementVisibility(message);
    }

    public String getMessageText() {
        return message.getText();
    }

    public void click_back_dashboard(){
        waitForElementEnabled(mainButton);
        mainButton.click();
    }

}
