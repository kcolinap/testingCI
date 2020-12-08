package api.apps.android.nw.operaciones.recarga_celular;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.common.CommonPhonePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PhoneForChargePage extends CommonPhonePage {

    private static PhoneForChargePage sSoleInstance;

    public static final String
            SET_PHONE_TO_CHARGE_TITLE_TEXT = "Recargá tu celular",
            SET_PHONE_TO_CHARGE_SUBTITLE_TEXT = "¿Qué número querés recargar?",
            TOOL_TIP_TEXT = "Sin el 0 ni el 15. Ej: 1123456789",
            LENGHT_ERROR_TEXT = "El número telefónico debe tener 10 dígitos en total",
            INVALID_NUMBER_TEXT = "Revisá el número. Sin 0 ni 15. Ej.: 1165992400",
            NO_EXIST_TEXT = "Código de área inexistente";

    public static final String[] validNumbers = {"51658208", "51658209"};

    public static final String[] validCodes = {"11"};


    @AndroidFindBy(id = "subtitleRechargeMobile")
    private static MobileElement subtitleRechargeMobile;

    @AndroidFindBy(id = "useMyPhoneLink")
    private MobileElement useMyPhoneLink;

    @AndroidFindBy(id = "nextButton")
    private MobileElement nextButton;

    @AndroidFindBy(id = "tooltipText")
    private MobileElement tooltipText;

    @AndroidFindBy(id = "errorMessage")
    private MobileElement errorMessage;

    public MobileElement getErrorMessage() {
        return errorMessage;
    }

    public MobileElement getTooltipText() {
        return tooltipText;
    }

    public MobileElement getNextButton() {
        return nextButton;
    }

    public MobileElement getUseMyPhoneLink() {
        return useMyPhoneLink;
    }

    public MobileElement getSubtitleRechargeMobile() {
        return subtitleRechargeMobile;
    }

    public PhoneForChargePage(){
        super();
        waitForElementVisibility(phoneInput);
    }

    public synchronized static PhoneForChargePage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new PhoneForChargePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(PhoneForChargePage.class);
            wait.until(ExpectedConditions.visibilityOf(phoneInput));
        }
        return sSoleInstance;
    }

    public void click_on_next_button(){
        waitForElementEnabled(nextButton);
        nextButton.click();
    }

    public void click_on_use_my_phone(){
        useMyPhoneLink.click();
    }

    public void setCode(String code){
        areaCodeInput.sendKeys(code);
    }

    public void setNumber(String number){
        phoneInput.sendKeys(number);
    }

    public void clic_next_button(){
        waitForElementEnabled(nextButton);
        nextButton.click();
    }
}
