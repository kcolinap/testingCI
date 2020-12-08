package api.apps.android.nw.prestamos.direccionEnvio;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAddressPage extends BasePage {

    /**
     * Properties Section
     */

    private static DeliveryAddressPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String MAIN_TITLE_TEXT = "Configurá tu envío";
    public final String PREFIX_SUBTITLE_TEXT_DEF = "¿Dónde mandamos tu ";
    public final String SUFIX_SUBTITLE_TEXT = "?";
    public final String DISCLAIMER_TEXT = "Cuando llegue tu celular vas a necesitar estar presente, mostrar tu DNI.";
    public final String ADD_NEW_DIRECTION_BUTTON_TEXT = "Crear otra dirección";
    public final String CONTINUE_BUTTON_TEXT = "Continuar";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "disclaimerText")
    private static MobileElement lblDisclaimer;

    @AndroidFindBy(xpath = "//android.widget.RadioGroup/android.widget.RadioButton")
    private static List<MobileElement> addressOptionsContainer;

    @AndroidFindBy(id = "creationButton")
    private static MobileElement btnCreateNewAddress;

    @AndroidFindBy(id = "continueButton")
    private static MobileElement btnContinue;

    /**
     * Methods Section
     */

    private DeliveryAddressPage(){
        super();
        waitForElementVisibility(btnContinue);
    }

    public synchronized static DeliveryAddressPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new DeliveryAddressPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(btnContinue)));
        }
        return sSoleInstance;
    }

    public MobileElement getLblDisclaimer() { return lblDisclaimer; }
    public MobileElement getBtnCreateNewAddress() { return btnCreateNewAddress; }
    public MobileElement getBtnContinue() { return btnContinue; }

    public void clickAddNewAddressButton() { btnCreateNewAddress.click(); }
    public void clickContinueButton() { btnContinue.click(); }

    public String getAnSpecificAddressText(int address) { return addressOptionsContainer.get(address).getText(); }
    public void clickAnSpecificAddressOption(int address) { addressOptionsContainer.get(address).click(); }

    public List<String> getAllAddressText() {
        List<String> addressName = new ArrayList<>();
        for (MobileElement address : addressOptionsContainer){
            addressName.add(address.getText());
        }
        return addressName;
    }
}
