package api.apps.android.nw.operaciones.p2p.request;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.operaciones.recarga_celular.AmountToRechargePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RequestsLandingPage extends BasePage {

    private static RequestsLandingPage sSoleInstance;

    public static final String TITLE_TEXT = "Solicitudes";
    public static final String NO_REQUEST_RECEIVED_TEXT = "No tenés ninguna solicitud pendiente";
    public static final String NO_SENDED_REQUEST_TEXT = "No enviaste ninguna solicitud";

    @AndroidFindBy(id = "leftOption")
    private static MobileElement leftOption;

    @AndroidFindBy(id = "rightOption")
    private MobileElement rightOption;

    @AndroidFindBy(id = "requestImage")
    private MobileElement requestImage;

    @AndroidFindBy(id = "message")
    private MobileElement message;

    @AndroidFindBy(id = "pendingTitle")
    private MobileElement pendingTitle;

    @AndroidFindBy(xpath = "//android.widget.ScrollView//android.widget.FrameLayout[1]//android.widget.RelativeLayout")
    private List<MobileElement> requestsPending;

    @AndroidFindBy(xpath = "//android.widget.ScrollView//android.widget.FrameLayout[1]//android.widget.RelativeLayout/android.widget.TextView[1]")
    private List<MobileElement> requestsPendingTitle;

    @AndroidFindBy(xpath = "//android.widget.ScrollView//android.widget.FrameLayout[1]//android.widget.RelativeLayout/android.widget.TextView[2]")
    private List<MobileElement> requestsPendingAmount;

    @AndroidFindBy(xpath = "//android.widget.ScrollView//android.widget.FrameLayout[1]//android.widget.RelativeLayout//android.widget.TextView[@text='Cancelar']")
    private List<MobileElement> requestsPendingCancelButton;

    @AndroidFindBy(xpath = "//android.widget.ScrollView//android.widget.FrameLayout[1]//android.widget.RelativeLayout//android.widget.TextView[@text='Aceptar']")
    private List<MobileElement> requestsPendingAcceptButton;

    @AndroidFindBy(id = "initials")
    private List<MobileElement> initials;

    @AndroidFindBy(id = "itemTitle")
    private List<MobileElement> itemTitle;

    @AndroidFindBy(id = "itemAmount")
    private List<MobileElement> itemAmount;

    @AndroidFindBy(id = "itemExpiration")
    private List<MobileElement> itemExpiration;

    @AndroidFindBy(id = "cancelAction")
    private List<MobileElement> cancelAction;



    public MobileElement getLeftOption() {
        return leftOption;
    }

    public MobileElement getRightOption() {
        return rightOption;
    }

    public MobileElement getRequestImage() {
        return requestImage;
    }

    public MobileElement getPendingTitle() {
        return pendingTitle;
    }

    public List<MobileElement> getRequestsPending() {
        return requestsPending;
    }

    public List<MobileElement> getInitials() {
        return initials;
    }

    public List<MobileElement> getRequestsPendingTitle() {
        return requestsPendingTitle;
    }

    public List<MobileElement> getRequestsPendingAmount() {
        return requestsPendingAmount;
    }

    public List<MobileElement> getRequestsPendingCancelButton() {
        return requestsPendingCancelButton;
    }

    public List<MobileElement> getRequestsPendingAcceptButton() {
        return requestsPendingAcceptButton;
    }

    public void setInitials(List<MobileElement> initials) {
        this.initials = initials;
    }

    public List<MobileElement> getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(List<MobileElement> itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<MobileElement> getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(List<MobileElement> itemAmount) {
        this.itemAmount = itemAmount;
    }

    public List<MobileElement> getItemExpiration() {
        return itemExpiration;
    }

    public void setItemExpiration(List<MobileElement> itemExpiration) {
        this.itemExpiration = itemExpiration;
    }

    public List<MobileElement> getCancelAction() {
        return cancelAction;
    }

    public void setCancelAction(List<MobileElement> cancelAction) {
        this.cancelAction = cancelAction;
    }

    public RequestsLandingPage() {
        super();
        waitForElementVisibility(leftOption);
    }

    public synchronized static RequestsLandingPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new RequestsLandingPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(RequestsLandingPage.class);
            wait.until(ExpectedConditions.visibilityOf(leftOption));
        }
        return sSoleInstance;
    }

    public void click_sended_request(){
        actions.singleTap(rightOption);
    }
}
