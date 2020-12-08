package api.apps.android.nw.operaciones.pagoServicios.transaccionFallida;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FailedTransactionPage extends BasePage {

    @AndroidFindBy(id = "titlePayment")
    private static MobileElement titlePayment;

    @AndroidFindBy(id = "errorIcon")
    private static MobileElement errorIcon;

    @AndroidFindBy(id = "title")
    private static MobileElement title;

    @AndroidFindBy(id = "subtitle")
    private static MobileElement subtitle;

    @AndroidFindBy(id = "mainButton")
    private static MobileElement backToDashboard;

    private static FailedTransactionPage sSoleInstance;

    public FailedTransactionPage() {
        super();
    }

    public String getFailedTransactionTitleText() { return titlePayment.getText();  }
    public FailedTransactionPage waitToLoadScreen() throws InterruptedException {
        subtitle.wait(15);
        return this;
    }

    public synchronized static FailedTransactionPage getInstance() {
        if (sSoleInstance == null) {
            sSoleInstance = new FailedTransactionPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver, 10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(errorIcon)));
        }
        return sSoleInstance;
    }

    public static MobileElement getBackButton() {
        return backToDashboard;
    }

    public static MobileElement getTitlePayment() {
        return titlePayment;
    }

    public static MobileElement getErrorIcon() {
        return errorIcon;
    }

    public static MobileElement getTitle() {
        return title;
    }

    public static MobileElement getSubtitle() {
        return subtitle;
    }

    public void clickBackToDashboard() { backToDashboard.click(); }
}
