package api.apps.android.nw.operaciones.cashin.metodos;

import api.apps.android.Android;
import api.apps.android.nw.TextList;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OwnAccountPage extends BasePage {

    private static OwnAccountPage sSoleInstance;

    public static final String
            TITLE_TEXT = "Cargá por transferencia",
            SUBTITLE_TEXT = "Desde una cuenta a tu nombre",
            DESCRIPTION_TEXT = "Hacé una transferencia desde tu banco a esta cuenta.",
            DISCLAIMER_TEXT = "La cuenta emisora deberá estar a tu nombre.",
            ALIAS_TEXT = "Alias:",
            CBU_TEXT = "CBU:",
            CUIT_TEXT = "CUIT:",
            RAZONSOCIAL_TEXT = "Razón Social:";

    @AndroidFindBy(id = "accountDataSubtitle")
    private MobileElement accountDataSubtitle;

    @AndroidFindBy(id = "account_description")
    private MobileElement account_description;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"La cuenta emisora deberá estar a tu nombre.\")")
    private MobileElement disclaimerText;

    @AndroidFindBy(id = "disclaimerIcon")
    private MobileElement disclaimerIcon;

    @AndroidFindBy(id = "dataTitle")
    private static MobileElement dataTitle;

    @AndroidFindBy(id = "dataValue")
    private MobileElement dataValue;

   // @AndroidFindBy(xpath = "//android.widget.TextView[@text='Alias:']")
   @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Alias:\")")
    private MobileElement alias;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"CBU:\")")
    private MobileElement cbu;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"CUIT:\")")
    private MobileElement cuit;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Razón Social:\")")
    private MobileElement razonSocial;

    public OwnAccountPage(){
        super();
        wait.until(ExpectedConditions.visibilityOf(dataTitle));
    }
    public synchronized static OwnAccountPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new OwnAccountPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(dataTitle)));
        }
        return sSoleInstance;
    }

    public MobileElement getAccountDataSubtitle() { return accountDataSubtitle; }
    public MobileElement getAccount_description() { return account_description; }

    public MobileElement getDisclaimerText() { return disclaimerText; }

    public MobileElement getDisclaimerIcon() { return disclaimerIcon; }

    public static MobileElement getDataTitle() { return dataTitle; }

    public MobileElement getDataValue() { return dataValue; }

    public MobileElement getAlias() { return alias; }

    public MobileElement getCbu() { return cbu; }

    public MobileElement getCuit() { return cuit; }

    public MobileElement getRazonSocial() { return razonSocial; }

}
