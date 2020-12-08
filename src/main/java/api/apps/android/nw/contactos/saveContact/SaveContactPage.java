package api.apps.android.nw.contactos.saveContact;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.contactos.ContactsPage;
import api.apps.android.nw.dashboard.DashboardPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveContactPage extends BasePage {

    private static SaveContactPage sSoleInstance;
    public Map<String, String> staticObjects;

    @AndroidFindBy(id = "textinput_error")
    private MobileElement errorMessage;

    @AndroidFindBy(id = "contactFullNameInput")
    private MobileElement contactFullNameInput;

    @AndroidFindBy(id = "saveContactButton")
    private MobileElement saveContactButton;

    @AndroidFindBy(id = "contactDataLabel")
    private List<MobileElement> contactLabels;

    @AndroidFindBy(id = "contactDataValue")
    private List<MobileElement> contactValues;

    public SaveContactPage() {
        super();
        waitForElementVisibility(getLblTitle());
        staticObjects = getScreenStaticObjects();
    }

    public synchronized static SaveContactPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new SaveContactPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(getLblTitle())));
        }
        return sSoleInstance;
    }

    public DashboardPage clickBackButton() throws Exception {
        click_close_back_button();
        return DashboardPage.getInstance();
    }

    public String getCBU() {
        if (staticObjects.isEmpty())
            staticObjects = getScreenStaticObjects();

        return staticObjects.get("CBU");
    }

    public String getAlias() {
        if (staticObjects.isEmpty())
            staticObjects = getScreenStaticObjects();

        return staticObjects.get("Alias");
    }

    public String getFullName() {
        if (staticObjects.isEmpty())
            staticObjects = getScreenStaticObjects();

        return staticObjects.get("Nombre y Apellido");
    }

    public String getCUIL() {
        if (staticObjects.isEmpty())
            staticObjects = getScreenStaticObjects();

        return staticObjects.get("CUIL");
    }

    public String getError() {
        return errorMessage.getText();
    }

    public ContactsPage saveContact() {
            saveContactButton.click();
            return new ContactsPage();
    }

    public void clearContactName() {
        contactFullNameInput.clear();
    }

    public void addContactName(String contactname) {
        contactFullNameInput.setValue(contactname);
    }

    /**
     * Gets all the contacts visible in the page (DO WE NEED TO SCROLL ????)
     * @return
     */
    public Map<String, String> getScreenStaticObjects() {
        Map<String, String> LabelAndTexts = new HashMap<>();

        for (int i = 0; i < contactLabels.size(); i++) {
            LabelAndTexts.put(contactLabels.get(i).getText(), contactValues.get(i).getText());
        }

        return LabelAndTexts;
    }

}
