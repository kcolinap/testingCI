package api.apps.android.nw.operaciones.pagoServicios.montoAbierto;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpenAmountPage extends BasePage {

    /**
     * Properties Section
     */

    private static OpenAmountPage sSoleInstance;

    public static String TITLE_TEXT = "Pagá tus facturas";
    public static String SUBTITLE_TEXT = "¿Cuánto queres pgar?";
    public static String AMOUNT_HIGHER_TEXT = "No pod\u00E9s pagar un monto mayor al permitido";
    public static String AMOUNT_LOWER_TEXT = "No pod\u00E9s pagar un monto menor al permitido";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "openAmountMessage")
    private MobileElement openAmountMessage;

    @AndroidFindBy(id = "image")
    private MobileElement image;

    @AndroidFindBy(id = "openAmountMessageCompany")
    private MobileElement openAmountMessageCompany;

    @AndroidFindBy(id = "amountInput")
    private static MobileElement amountInput;

    @AndroidFindBy(id = "nextButton")
    private MobileElement nextButton;

    @AndroidFindBy(id = "amountOpenErrorMessage")
    private MobileElement amountOpenErrorMessage;

    public MobileElement getOpenAmountMessage() {
        return openAmountMessage;
    }

    public MobileElement getImage() {
        return image;
    }

    public MobileElement getOpenAmountMessageCompany() {
        return openAmountMessageCompany;
    }

    public MobileElement getAmountInput() {
        return amountInput;
    }

    public MobileElement getNextButton() {
        return nextButton;
    }

    public MobileElement getAmountOpenErrorMessage() {
        return amountOpenErrorMessage;
    }

    /**
     * Methods Section
     */

    private OpenAmountPage() throws Exception {
        super();
        waitForElementVisibility(amountInput);
    }

    public synchronized static OpenAmountPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new OpenAmountPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(OpenAmountPage.class);
            wait.until(ExpectedConditions.visibilityOf(amountInput));
        }
        return sSoleInstance;
    }


    public void set_amount(String amount){
        amountInput.sendKeys(amount);
    }

    public void click_next_button(){
        Wrapper.waitForElementEnabled(nextButton);
        nextButton.click();
    }
}
