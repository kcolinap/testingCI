package api.apps.android.nw.perfil.seguridad.claveNubi;

import api.apps.android.Android;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NubiPasswordSuccessPage {
    private static NubiPasswordSuccessPage sSoleInstance;
    private AppiumDriver<MobileElement> driver;
    WebDriverWait wait;

    public static String MESSAGE_TEXT = "¡Tenés nueva Clave Nubi!";

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "finishButton")
    @AndroidFindBy(id = "mainButton")
    private static MobileElement finishBtn;

    @AndroidFindBy(id = "emoji")
    private MobileElement icon;

    @AndroidFindBy(id = "message")
    private MobileElement message;

    public static MobileElement getFinishBtn() {
        return finishBtn;
    }

    public MobileElement getIcon() {
        return icon;
    }

    public MobileElement getMessage() {
        return message;
    }

    public NubiPasswordSuccessPage() {
        this.driver = Android.driver;
        wait = new WebDriverWait(driver,30);
        loadElements();
        wait.until(ExpectedConditions.visibilityOf(finishBtn));
    }

    public synchronized static NubiPasswordSuccessPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new NubiPasswordSuccessPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(finishBtn)));
        }

        return sSoleInstance;
    }

    public void loadElements() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickFinishBtn() {
        finishBtn.click();
    }

}
