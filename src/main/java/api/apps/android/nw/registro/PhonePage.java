package api.apps.android.nw.registro;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.RegistroBasePage;
import api.apps.android.nw.common.CommonPhonePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PhonePage extends CommonPhonePage {

    private static PhonePage sSoleInstance;

    public static final String LBL_TITULO_TEXT = "Registro";
    public static final String LBL_SUBTITLE_PHONE = "¿Cuál es tu número de teléfono?";
    public static final String LBL_HINT_TEXT = "Sin el 0 ni el 15. Ej: 1123456789";
    public static final String LBL_DISCLAIMER_TEXT = "Por ahora, solo aceptamos números de Argentina.";
    public static final String ERROR_MESSAGE_TEXT = "Revisá el número. Sin 0 ni 15. Ej.: 1165992400";

    @AndroidFindBy(id = "subtitlePhone")
    private MobileElement subtitlePhone;

    @AndroidFindBy(id = "phoneHint")
    private MobileElement phoneHint;

    @AndroidFindBy(id = "disclaimerText")
    private MobileElement disclaimerText;

    @AndroidFindBy(id = "button")
    private static MobileElement button;

    @AndroidFindBy(id = "errorMessage")
    private MobileElement errorMessage;

    /*** GETS  ***/
    public MobileElement getSubtitlePhone() {
        return subtitlePhone;
    }

    public static String getLblSubtitlePhone() {
        return LBL_SUBTITLE_PHONE;
    }

    public MobileElement getErrorMessage() {
        return errorMessage;
    }

    public MobileElement getPhoneHint() {
        return phoneHint;
    }

    public MobileElement getDisclaimerText() {
        return disclaimerText;
    }

    public MobileElement getButton() {
        return button;
    }

    public PhonePage(){
        super();
        waitForElementVisibility(button);

    }

    public synchronized static PhonePage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new PhonePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(PhonePage.class);
            wait.until(ExpectedConditions.visibilityOf(button));
        }
        return sSoleInstance;
    }

   public void setAreaCode(String areaCode){
        areaCodeInput.sendKeys(areaCode);
        hideKeyboardIfVisible();
   }

    public void setPhoneNumber(String phoneNumber){
        phoneInput.sendKeys(phoneNumber);
        hideKeyboardIfVisible();
    }

    public void click_on_next_button(){
        waitForElementEnabled(button);
        button.click();
    }

    public boolean validate_text_input_error_message(int opc){
        boolean result = false;
        boolean isVisible = false;

        try{
            isVisible = errorMessage.isDisplayed();
        }catch (Exception | Error e){
            isVisible = false;
        }


        switch (opc){
            case 0:
                if(isVisible)
                    waitForElementNotVisible(errorMessage);
                break;
            case 1:
                if(!isVisible)
                    waitForElementVisibility(errorMessage);

                result = true;
                break;
        }
        return result;
    }
}
