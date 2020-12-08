package api.apps.android.nw.contactos.addContact;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.contactos.saveContact.SaveContactPage;
import api.apps.android.nw.dashboard.DashboardPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddContactPage extends BasePage {

    private static AddContactPage sSoleInstance;

    public static String SUBTITLE = "Agregá una cuenta por CBU, CVU o Alias";

    @AndroidFindBy(id = "message")
    private MobileElement lblMessage;

    @AndroidFindBy(id = "bankingKeyInput")
    private MobileElement inputCBUAlias;

    @AndroidFindBy(id = "warningText")
    private MobileElement warningMessage;

    @AndroidFindBy(id = "nextButton")
    private MobileElement nextButton;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "searchButton")
    private MobileElement searchButton;


    public AddContactPage() {
        super();
        waitForElementVisibility(getLblTitle());
    }

    public synchronized static AddContactPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new AddContactPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(getLblTitle())));
        }
        return sSoleInstance;
    }

    public void setCBUAlias(String cbuAlias) {
        inputCBUAlias.sendKeys(cbuAlias);
    }

    public MobileElement getCBUAliasInput() {
        return inputCBUAlias;
    }

    public String getWarningMessage() {
        return warningMessage.getText();
    }

    public DashboardPage clickBackButton() throws Exception{
        click_close_back_button();
        return DashboardPage.getInstance();
    }

    public SaveContactPage clickNextButton() {
        nextButton.click();
        return new SaveContactPage();
    }

    public boolean nextButtonIsEnabled() {
        return super.elementIsEnabled(nextButton);
    }

    public boolean btnBackExists() {
        return super.elementExists(backOrCloseBtn);
    }
}
