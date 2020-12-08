package api.apps.android.nw.operaciones.sube.pantallaError;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.dashboard.DashboardPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScreenErrorPage extends BasePage {

    private static ScreenErrorPage sSoleInstance;

    @AndroidFindBy(id = "cardTitle")
    private static MobileElement cardTitle;

    @AndroidFindBy(id = "backHomeButton")
    private MobileElement backHomeButton;

    private ScreenErrorPage() {
        super();
        waitForElementVisibility(cardTitle);
    }

    public synchronized static ScreenErrorPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new ScreenErrorPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(cardTitle)));
        }
        return sSoleInstance;
    }

    public DashboardPage clickBackHomeButton() {
        backHomeButton.click();
        return new DashboardPage();
    }

    public void waitBackHomeButton() {
        waitForElementVisibility(backHomeButton);
    }

}
