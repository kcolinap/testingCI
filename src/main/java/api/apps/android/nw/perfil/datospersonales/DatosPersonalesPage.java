package api.apps.android.nw.perfil.datospersonales;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.registro.CuilPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DatosPersonalesPage extends BasePage {

    private static DatosPersonalesPage sSoleInstance;

    public static String TITLE_TEXT = "Tus datos";

    @AndroidFindBy(id = "name")
    private static MobileElement name;

    @AndroidFindBy(id = "userName")
    private MobileElement userName;

    @AndroidFindBy(id = "cuil")
    private MobileElement cuil;

    @AndroidFindBy(id = "email")
    private MobileElement email;

    @AndroidFindBy(id = "phoneNumber")
    private MobileElement phoneNumber;

    @AndroidFindBy(id = "contactDataValue")
    private List<MobileElement> contactDataValues;

    public MobileElement getName() {
        return name;
    }

    public MobileElement getUserName() {
        return userName;
    }

    public MobileElement getCuil() {
        return cuil;
    }

    public MobileElement getEmail() {
        return email;
    }

    public MobileElement getPhoneNumber() {
        return phoneNumber;
    }

    public List<MobileElement> getContactDataValues() {
        return contactDataValues;
    }

    public DatosPersonalesPage() {
        super();
        waitForElementVisibility(name);
    }

    public synchronized static DatosPersonalesPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new DatosPersonalesPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(DatosPersonalesPage.class);
            wait.until(ExpectedConditions.visibilityOf(name));
        }
        return sSoleInstance;
    }

    public String getContactDataValue(int index) {
        return contactDataValues.get(index).getText();
    }

}
