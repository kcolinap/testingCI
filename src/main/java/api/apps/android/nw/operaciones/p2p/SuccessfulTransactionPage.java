package api.apps.android.nw.operaciones.p2p;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.dashboard.DashboardPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SuccessfulTransactionPage extends BasePage {

    private static SuccessfulTransactionPage sSoleInstance;

    public static final String SUCCES_SEND_TEXT = "¡Se envió la plata!";
    public static final String SUCCES_REQUEST_TEXT = "¡Pediste plata!";
    public static final String SUCCES_ACCEPT_REQUEST = "Enviaste el dinero solicitado con éxito";


    @AndroidFindBy(id = "emoji")
    private MobileElement emoji;

    @AndroidFindBy(id = "mainButton")
    private static MobileElement btnFinish;

    @AndroidFindBy(id = "message")
    private MobileElement message;

    public MobileElement getEmoji() {
        return emoji;
    }

    public MobileElement getBtnFinish() {
        return btnFinish;
    }

    public MobileElement getMessage() {
        return message;
    }

    public SuccessfulTransactionPage() {
       super();
        waitForElementVisibility(btnFinish);
    }

    public synchronized static SuccessfulTransactionPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new SuccessfulTransactionPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(SuccessfulTransactionPage.class);
            wait.until(ExpectedConditions.visibilityOf(btnFinish));
        }
        return sSoleInstance;
    }

    public DashboardPage click_back_to_dashboard() throws Exception{
        waitForElementEnabled(btnFinish);
        btnFinish.click();

        return DashboardPage.getInstance();
    }
}
