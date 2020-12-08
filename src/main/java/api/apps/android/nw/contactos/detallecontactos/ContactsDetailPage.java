package api.apps.android.nw.contactos.detallecontactos;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ContactsDetailPage extends BasePage {

    public static final String DETAIL_CONTACT_TITLE_TEXT = "Elegí una cuenta";
    private static ContactsDetailPage sSoleInstance;

    @AndroidFindBy(id = "initials")
    private static MobileElement initialsIcon;

    @AndroidFindBy(id = "secondaryButton")
    private MobileElement secondaryButton;

    @AndroidFindBy(id = "contactName")
    private MobileElement lblContactName;

    @AndroidFindBy(id = "phoneNumber")
    private MobileElement phoneNumber;

    @AndroidFindBy(id = "userName")
    private List<MobileElement>  userName;

    @AndroidFindBy(id = "ownerName")
    private List<MobileElement> ownerName;

    @AndroidFindBy(id = "logo")
    private MobileElement logo;

    @AndroidFindBy(id = "editButton")
    private MobileElement editButton;


    @AndroidFindBy(xpath = "//*[@resource-id='accountsList']//descendant::android.widget.FrameLayout/descendant::android.view.ViewGroup")
    private List<MobileElement> accountsObjects;

    @AndroidFindBy(uiAutomator = "new UiSelector().descriptionContains(\"\u00CDcono logo de Nubi\")")
    private MobileElement lblLogoNubi;

    @AndroidFindBy(uiAutomator = "new UiSelector().descriptionContains(\"\u00CDcono de banco\")")
    private MobileElement lblLogoBank;

    public MobileElement getSecondaryButton() {
        return secondaryButton;
    }

    public MobileElement getLblContactName() {
        return lblContactName;
    }

    public MobileElement getPhoneNumber() {
        return phoneNumber;
    }

    public List<MobileElement> getUserName() {
        return userName;
    }

    public List<MobileElement> getOwnerName() {
        return ownerName;
    }

    public MobileElement getLogo() {
        return logo;
    }

    public MobileElement getEditButton() {
        return editButton;
    }

    public ContactsDetailPage() {
        super();
        waitForElementVisibility(initialsIcon);
    }

    public synchronized static ContactsDetailPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new ContactsDetailPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(ContactsDetailPage.class);
            wait.until(ExpectedConditions.visibilityOf(initialsIcon));
        }
        return sSoleInstance;
    }

    public void confirmContact(MobileElement contact){
        actions.singleTap(contact);
    }

    public boolean logoNubiExists() {
        return super.elementExists(lblLogoNubi);
    }

    public void clickNubiLogo() {
        lblLogoNubi.click();
    }

    public void clickBankLogo() {
        lblLogoBank.click();
    }

    public boolean logoBankExists() {
        return super.elementExists(lblLogoBank);
    }

    public boolean btnBackExists() {
        return super.elementExists(backOrCloseBtn);
    }

    public String getContactName() {
        return lblContactName.getText();
    }

    public void clickSecondaryBtn() {
        secondaryButton.click();
    }

    /**
     * Metodo para obtener el texto de los elementos que componen una tarjeta de tipo de cuenta
     *
     * @param typeAccount el tipo de cuenta del que deseamos obtener informacion (NUBI o Cta Bancaria)
     * @param element     el elemento del que deseamos obtener el texto (titulo de la cuenta, username, etc)
     * @return retorna un string con el texto del elemento deseado
     * @throws Exception se arroja cuando no se pudo hallar el elemento buscado.
     */
    public String getTextFromSpecificElementInAccountCard(String typeAccount, String element) throws Exception {
        try {
            for (MobileElement accountsObject : accountsObjects) {

                String typeAccountIcon = null;
                switch (typeAccount) {
                    case "nubi":
                        typeAccountIcon = accountsObject.findElement(
                                By.id("\u00CDcono logo de Nubi")).
                                getAttribute("contentDescription").toLowerCase();
                        break;
                    case "bancaria":
                        typeAccountIcon = accountsObject.findElement(
                                By.id("\u00CDcono de banco")).
                                getAttribute("contentDescription").toLowerCase();
                        break;
                    default:
                        break;
                }

                if (typeAccountIcon.contains(typeAccount)) {
                    switch (element) {
                        case "titulo_tipo_cuenta":
                            String locator = "//accountsList/android.view.ViewGroup/android.widget.TextView[@index='1']";
                            return accountsObject.findElement(By.xpath(locator)).getText();
                        case "nombre_de_usuario":
                            return accountsObject.findElement(By.id("userName")).getText();
                        case "nombre_de_propietario":
                            return accountsObject.findElement(By.id("ownerName")).getText();
                    }
                }
            }

            return null;
        } catch (Exception e) {
            throw new Exception("No se pudo hallar el elemento buscado");
        }
    }

}
