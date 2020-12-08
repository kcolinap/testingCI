package api.apps.android.nw.operaciones.sube.listaTarjetas;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.operaciones.sube.registrarSube.SubeRegisterPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.smartcardio.Card;
import java.util.HashMap;
import java.util.List;

public class CardListPage extends BasePage {

    private static CardListPage sSoleInstance;

    public static String TITLE_TEXT = "Cargá tu SUBE";

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id="addSubeButtonInCardListView")
    @AndroidFindBy(id = "addSubeButton")
    private static MobileElement addSubeButton;

    @AndroidFindBy(id = "actionButton")
    private static MobileElement actionButton;

    @AndroidFindBy(id = "cardAlias")
    private MobileElement cardAlias;

    @AndroidFindBy(id = "actionEdit")
    private static MobileElement actionEdit;

    @AndroidFindBy(id = "actionDelete")
    private MobileElement actionDelete;

    @AndroidFindBy(id = "label")
    private MobileElement titlePopUpScreen;

    @AndroidFindBy(id = "design_bottom_sheet")
    private MobileElement popUpScreen;

    @AndroidFindBy(id = "cardItem")
    private List<MobileElement> cardItems;

    public static MobileElement getActionButton() {
        return actionButton;
    }

    public MobileElement getCardAlias() {
        return cardAlias;
    }

    public static MobileElement getAddSubeButton() {
        return addSubeButton;
    }

    public static MobileElement getActionEdit() {
        return actionEdit;
    }

    public MobileElement getActionDelete() {
        return actionDelete;
    }

    public MobileElement getTitlePopUpScreen() {
        return titlePopUpScreen;
    }

    public MobileElement getPopUpScreen() {
        return popUpScreen;
    }

    public List<MobileElement> getCardItems() {
        return cardItems;
    }

    private CardListPage() {
        super();
        waitForElementVisibility(addSubeButton);
    }

    public synchronized static CardListPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new CardListPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(CardListPage.class);
            wait.until(ExpectedConditions.visibilityOf(addSubeButton));
        }
        return sSoleInstance;
    }

    public SubeRegisterPage clickAddSubeCardButton() {
        addSubeButton.click();
        return SubeRegisterPage.getInstance();
    }

    public void clickEditAlias() {
        actionButton.click();
        waitForElementVisibility(actionEdit);
        actionEdit.click();
    }

    public void clickDeleteCard() {
        actionButton.click();
        waitForElementVisibility(actionDelete);
        actionDelete.click();
    }

    public HashMap<Integer, HashMap<String, MobileElement>> getCardList() {
        HashMap<Integer, HashMap<String, MobileElement>> cardList = new HashMap<>();

        for (int i = 0; i < cardItems.size(); i++) {

            AndroidElement cardDetails = (AndroidElement) cardItems.get(i);

            MobileElement alias = cardDetails.findElement(By.id("com.tunubi.wallet.qa:id/cardAlias"));
            MobileElement cardNumber = cardDetails.findElement(By.id("com.tunubi.wallet.qa:id/cardNumber"));
            MobileElement actionBtn = cardDetails.findElement(By.id("com.tunubi.wallet.qa:id/actionButton"));

            HashMap<String, MobileElement> itemsMap = new HashMap<>();
            itemsMap.put("alias", alias);
            itemsMap.put("cardNumber", cardNumber);
            itemsMap.put("actionBtn", actionBtn);
            cardList.put(i, itemsMap);
        }

        return cardList;
    }
}
