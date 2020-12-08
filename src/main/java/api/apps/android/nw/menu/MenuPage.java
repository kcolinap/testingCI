package api.apps.android.nw.menu;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.operaciones.cashout.CashOut;
import api.apps.android.nw.perfil.PerfilPage;
import api.apps.android.nw.tarjetaprepaga.PrepaidCardBasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MenuPage extends BasePage {

    private static MenuPage sSoleInstance;

    public static String
        MOBILE_RECHARGE_TEXT = "Recarga Celular";

    @AndroidFindBy(id = "menuFab")
    private static MobileElement btnMenu;

    @AndroidFindBy(id = "bottomNavigationItemHome")
    private static MobileElement btnHome;

    @AndroidFindBy(id = "bottomNavigationItemCard")
    private MobileElement btnPrepaidCard;

    @AndroidFindBy(id = "bottomNavigationItemStats")
    private MobileElement btnStats;

    @AndroidFindBy(id = "bottomNavigationItemProfile")
    private MobileElement btnProfile;

    @AndroidFindBy(id = "itemIcon")
    private List<MobileElement> popUpButtons;

    @AndroidFindBy(id = "itemName")
    private List<MobileElement> itemName;


    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Celular\")")
    private MobileElement btnCell;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Solicitudes\")")
    private MobileElement btnRequests;

    public static MobileElement getBtnMenu() {
        return btnMenu;
    }

    public static MobileElement getBtnHome() {
        return btnHome;
    }

    public MobileElement getBtnPrepaidCard() {
        return btnPrepaidCard;
    }

    public MobileElement getBtnStats() {
        return btnStats;
    }

    public MobileElement getBtnProfile() {
        return btnProfile;
    }

    public List<MobileElement> getPopUpButtons() {
        return popUpButtons;
    }

    public MobileElement getBtnCell() {
        return btnCell;
    }

    public MobileElement getBtnRequests() {
        return btnRequests;
    }

    public MenuPage() {
       super();
       super.waitForElementVisibility(btnMenu);
    }

    public synchronized static MenuPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new MenuPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(MenuPage.class);
            wait.until(ExpectedConditions.visibilityOf(btnMenu));
        }
        return sSoleInstance;
    }

    public void clickBtnMenu() {
        btnMenu.click();
    }

    public static void clickBtnHome() {
        btnHome.click();
    }

    public PrepaidCardBasePage clickTarjetaPrepagaButton() {
        btnPrepaidCard.click();
        return new PrepaidCardBasePage();
    }

    public PerfilPage clickBtnPerfil() throws Exception {
        btnProfile.click();
        return PerfilPage.getInstance();
    }

    public MobileElement getPopupButton(int index) {

        Wrapper.waitForElementVisibility(popUpButtons.get(0));
        return popUpButtons.get(index);
    }

    public String getTextButton(int index) {

        return itemName.get(index).getText();
    }

    // Send position 1
    public void clickBtnEnviar() {
        //waitForElementVisible(getPopupButton(0));
        actions.singleTap(getPopupButton(0));
    }

    // Requests position 2
    public void clickSolicitarButton() {
        getPopupButton(1).click();
    }

    // Request position 3
    public CashOut clickExtraerButton() {
        //getPopupButton(2).click();
        return null;
    }

    // SUBE position 5
    public void clickSubeButton() {
        getPopupButton(3).click();
    }

    // CellRecharge position 4
    public void clickRecargaCelular() {
        getPopupButton(4).click();
    }

    // Pay Bills position 6
    public void clickPayBillsButton(){
        getPopupButton(5).click();
    }


    public void clickBtnSolicitudes() {
        getPopupButton(6).click();
    }


    // Contacts position 8
    public void clickContactsButton(){
        getPopupButton(7).click();
    }

    // Contacts position 9
    public void clickMovementsButton() throws Exception{
        getPopupButton(8).click();
    }


    public boolean btnExtraerExists() {
        return elementExists(btnCell);
    }

    public boolean btnCellRechargeExists() {
        return elementExists(getPopupButton(3));
    }

    public boolean btnSubeExists() {
        return elementExists(getPopupButton(4));
    }

    public void waitRechargeCellBtn() {
        waitForElementVisible(getPopupButton(3));
    }

    public void waitForElementVisible(MobileElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
