package api.apps.android.nw.contactos;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.contactos.addContact.AddContactPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ContactsPage extends BasePage {

    public static String CONTACTS_PAGE_TITLE_TEXT = "Contactos";
    private static ContactsPage sSoleInstance;
    public static boolean contacts_permission = false;


    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.permissioncontroller:id/permission_allow_button\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.packageinstaller:id/permission_allow_button\")")
    private static MobileElement btnAllow;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.permissioncontroller:id/permission_deny_button\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.packageinstaller:id/permission_deny_button\")")
    private MobileElement btnReject;

    @AndroidFindBy(id = "refreshButton")
    private MobileElement refreshButton;

    @AndroidFindBy(id = "addContactFab")
    private static MobileElement addContactFab;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Contactos con otras cuentas\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Otras cuentas\")")
    private MobileElement bankSeparator;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Contactos con Nubi Cuenta\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Contactos NUBI\")")
    private MobileElement nubiSeparator;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Contactos del tel\u00E9fono\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Otros contactos\")")
    private MobileElement phoneSeparator;

    @AndroidFindBy(id = "contactList")
    private List<MobileElement> contactList;

    @AndroidFindBy(id = "contactName")
    private List<MobileElement> contactName;

    @AndroidFindBy(id = "contactDetail")
    private List<MobileElement> contactUserName;



    @AndroidFindBy(id = "error_image")
    private MobileElement errorImage;

    @AndroidFindBy(id = "message")
    private MobileElement errormessage;

    @AndroidFindBy(id = "retryButton")
    private MobileElement retryButton;

    @AndroidFindBy(id = "contactTitle")
    private MobileElement contactTitle;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"ContactosAgenda NUBI\")")
    private MobileElement lblNubiContacts;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"ContactosAgenda\")")
    private MobileElement lblContacts;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"contactListEmpty\")")
    private MobileElement lblContactsEmpty;

    public MobileElement getBtnAddContact() { return addContactFab; }

    public MobileElement getNubiSeparator() { return nubiSeparator; }

    public MobileElement getBankSeparator() { return bankSeparator; }

    public MobileElement getPhoneSeparator() { return phoneSeparator; }

    public MobileElement getContactTitle() {
        return contactTitle;
    }

    public List<MobileElement> getContactName() {
        return contactName;
    }

    public List<MobileElement> getContactUserName() {
        return contactUserName;
    }

    public ContactsPage() {
        super();
            waitForElementVisibility(addContactFab);

    }

    public synchronized static ContactsPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new ContactsPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(ContactsPage.class);
            wait.until(ExpectedConditions.visibilityOf(addContactFab));
        }
        return sSoleInstance;
    }

    public AddContactPage clickBtnAddContact() {
        addContactFab.click();
        return new AddContactPage();
    }

    public ContactsPage clickAllow() {
        waitForElementVisibility(btnAllow);
       actions.singleTap(btnAllow);
        return this;
    }

    public ContactsPage clickReject() {
        waitForElementVisibility(btnReject);
        btnReject.click();
        return this;
    }

    public boolean btnBackExists() {
        return super.elementExists(backOrCloseBtn);
    }

    public boolean nubiSeparatorExists() {
        return super.elementExists(nubiSeparator);
    }

    public boolean bankSeparatorExists() {
        return super.elementExists(bankSeparator);
    }

    public boolean phoneSeparatorExists() {
        return super.elementExists(phoneSeparator);
    }


    public void clickContactName(MobileElement element) {
        actions.singleTap(element);
    }

    //Locator por Linktext y Name esta deprecado, ver explicaciones aca: https://github.com/appium/appium/issues/6186
    public MobileElement contactFullName(String fullName) {
        return (MobileElement) getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + fullName + "']"));
    }

    public boolean lblNubiContactsExists() {
        return super.elementExists(lblNubiContacts);
    }

    public boolean lblContactsExists() {
        return super.elementExists(lblContacts);
    }

    public boolean errorImageExists() {
        return super.elementExists(errorImage);
    }

    public boolean contactTitleExists() {
        return super.elementExists(contactTitle);
    }




}
