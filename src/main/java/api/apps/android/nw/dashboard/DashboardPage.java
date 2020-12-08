package api.apps.android.nw.dashboard;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;

public class DashboardPage extends BasePage {

    private static DashboardPage sSoleInstance;


    public static final String
            ASK_FOR_YOUR_LOAN_BUTTON_TEXT = "Ped\u00ED el tuyo",
            BALANCE_TITLE_TEXT = "Saldo",
            MOBILE_RECHARGE_TEXT = "Recargar celular",
            SERVICE_PAYMENT_TEXT = "Pagar facturas",
            LAST_MOVEMENTS_TEXT = "\u00daltimos movimientos";

    @AndroidFindBy(id = "dashboardTitle")
    private MobileElement dashboardTitle;

    @AndroidFindBy(id = "balanceTitle")
    private MobileElement balanceTitle;

    @AndroidFindBy(id = "balance")
    private static MobileElement balance;

    @AndroidFindBy(id = "cashInButton")
    private MobileElement cashInBtn;

    @AndroidFindBy(id = "loanIntentionImage")
    private MobileElement loanIntentionImage;

    @AndroidFindBy(id = "cashInCardIcon")
    private MobileElement cashInCardIcon;

    @AndroidFindBy(id = "cashInCardText")
    private MobileElement cashInCardText;

    @AndroidFindBy(id = "prepaidCardIcon")
    private MobileElement prepaidCardIcon;

    @AndroidFindBy(id = "prepaidCardText")
    private MobileElement prepaidCardText;

    @AndroidFindBy(id = "acceptRequestButton")
    private MobileElement btnAcceptRequest;

    @AndroidFindBy(id = "rejectRequestButton")
    private MobileElement btnRejectRequest;

    @AndroidFindBy(id = "moneyText")
    private MobileElement moneyRequestText;

    @AndroidFindBy(id = "pendingRequestMessage")
    private MobileElement pendingRequesMessage;

    @AndroidFindBy(id = "requestsTitle")
    private MobileElement requestTitle;

    @AndroidFindBy(id = "recentTransactionsTitle")
    private MobileElement recentTransactionsTitle;

    @AndroidFindBy(id = "frequentContactsTitle")
    private MobileElement frequentContactsTitle;

    @AndroidFindBy(id = "frequentContactsContent")
    private MobileElement frequentContactsContent;

    @AndroidFindBy(id = "requests")
    private MobileElement requests;

    @AndroidFindBy(id = "seeMoreButton")
    private MobileElement seeMoreButton;

    @AndroidFindBy(id = "promotionImage")
    private MobileElement imgPromotionImage;

    @AndroidFindBy(id = "promotionDescription")
    private MobileElement lblPromotionDescription;


    @AndroidFindBy(id = "phonePurchaseButton")
    private MobileElement btnPhonePurchaseButton;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.tunubi.wallet.qa:id/recentTransactionsList']/android.view.ViewGroup")
    private List<MobileElement> transactionList;

    public MobileElement getCashInCardIcon() {
        return cashInCardIcon;
    }

    public MobileElement getCashInCardText() {
        return cashInCardText;
    }

    public MobileElement getPrepaidCardIcon() {
        return prepaidCardIcon;
    }

    public MobileElement getPrepaidCardText() {
        return prepaidCardText;
    }

    public MobileElement getBalanceTitle() {
        return balanceTitle;
    }

    public MobileElement getCashInBtn() {
        return cashInBtn;
    }

    public MobileElement getBalance() {
        return balance;
    }

    public MobileElement getBtnAcceptRequest() {
        return btnAcceptRequest;
    }

    public MobileElement getBtnRejectRequest() {
        return btnRejectRequest;
    }

    public MobileElement getMoneyRequestText() {
        return moneyRequestText;
    }

    public MobileElement getPendingRequesMessage() {
        return pendingRequesMessage;
    }

    public MobileElement getRequestTitle() {
        return requestTitle;
    }

    public MobileElement getRecentTransactionsTitle() {
        return recentTransactionsTitle;
    }

    public MobileElement getFrequentContactsTitle() {
        return frequentContactsTitle;
    }

    public MobileElement getFrequentContactsContent() {
        return frequentContactsContent;
    }

    public MobileElement getRequests() {
        return requests;
    }

    public MobileElement getSeeMoreButton() {
        return seeMoreButton;
    }


    public MobileElement getImgPromotionImage() {
        return imgPromotionImage;
    }

    public MobileElement getLblPromotionDescription() {
        return lblPromotionDescription;
    }

    public MobileElement getBtnPhonePurchaseButton() {
        return btnPhonePurchaseButton;
    }

    public List<MobileElement> getTransactionList() {
        return transactionList;
    }



    public DashboardPage() {
        super();
        wait.until(ExpectedConditions.visibilityOf(balance));
    }

    public synchronized static DashboardPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new DashboardPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(DashboardPage.class);
            wait.until(ExpectedConditions.visibilityOf(balance));
        }
        return sSoleInstance;
    }

    public void clickSeeMoreButton() {
        seeMoreButton.click();
    }

    public void click_frequent_contact_button(){
        frequentContactsTitle.click();
    }

    public void clickAskForYourLoanButton() { btnPhonePurchaseButton.click(); }

    public void click_cashin_button(){
        cashInBtn.click();
    }

    public void click_add_money(){
        actions.singleTap(cashInCardIcon);
    }

    public void click_prepaidCard(){
        actions.singleTap(prepaidCardIcon);
    }

    public HashMap<Integer, HashMap<String,  MobileElement>> getMovements(){
        HashMap<Integer, HashMap<String, MobileElement>> lastMovements = new HashMap<>();

        int i = 0;
        for (MobileElement transaction : transactionList) {
            HashMap<String, MobileElement> itemsMap = new HashMap<>();
            itemsMap.put("icon", !transaction.findElements(By.id("transactionIcon")).isEmpty() ? transaction.findElement(By.id("transactionIcon")) : null);
            itemsMap.put("description", !transaction.findElements(By.id("descriptionText")).isEmpty() ? transaction.findElement(By.id("descriptionText")) : null);
            itemsMap.put("amount", !transaction.findElements(By.id("moneyText")).isEmpty() ? transaction.findElement(By.id("moneyText")) : null);
            itemsMap.put("date", !transaction.findElements(By.id("timestampText")).isEmpty() ? transaction.findElement(By.id("timestampText")) : null);
            itemsMap.put("status", !transaction.findElements(By.id("statusText")).isEmpty() ? transaction.findElement(By.id("statusText")) : null);

            lastMovements.put(i, itemsMap);
            i++;
        }
        return lastMovements;
    }
}
