package api.apps.android.nw.perfil.cerrarsesion;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CloseSessionPage extends BasePage {

    private static CloseSessionPage sSoleInstance;

    public static String MODAL_TEXT = "¿Estás seguro que querés cerrar tu sesión?";
    public static String MODAL_TITLE_TEXT = "Cerrar sesión";


    @AndroidFindBy(id = "closeSessionButton")
    private static MobileElement closeSessionBtn;

    @AndroidFindBy(id = "returnButton")
    private MobileElement returnBtn;

    @AndroidFindBy(id = "modalTitle")
    private MobileElement modalTitle;

    @AndroidFindBy(id = "modalText")
    private MobileElement modalText;

    public static MobileElement getCloseSessionBtn() {
        return closeSessionBtn;
    }

    public MobileElement getReturnBtn() {
        return returnBtn;
    }

    public MobileElement getModalTitle() {
        return modalTitle;
    }

    public MobileElement getModalText() {
        return modalText;
    }

    public CloseSessionPage() {
        super();
        waitForElementVisibility(closeSessionBtn);
    }

    public synchronized static CloseSessionPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new CloseSessionPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(CloseSessionPage.class);
            wait.until(ExpectedConditions.visibilityOf(closeSessionBtn));
        }
        return sSoleInstance;
    }
    public void clickCloseSessionBtn() {
        closeSessionBtn.click();
    }
    public boolean closeSessionBtnExists() {
        return closeSessionBtn.isDisplayed();
    }

    public void clickReturnBtn() {
        returnBtn.click();
    }

    public void clickCloseButton() {
        backOrCloseBtn.click();
    }

    public boolean exitBtnAcceptExists() {

        return returnBtn.isDisplayed();
    }

}
