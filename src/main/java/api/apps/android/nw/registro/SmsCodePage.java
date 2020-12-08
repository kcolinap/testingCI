package api.apps.android.nw.registro;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.common.CommonPinPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SmsCodePage extends CommonPinPage {

    private static SmsCodePage sSoleInstance;

    public static final String LBL_TITLE_TEXT = "Registro";
    public static final String LBL_SUBTITLE_PHONE_TEXT = "Confirmá tu teléfono";
    public static final String TEXT_ERROR_MESSAGE = "Código incorrecto";

    @AndroidFindBy(id = "subtitlePhone")
    private MobileElement subtitlePhone;

    @AndroidFindBy(id = "subtitleSmsCode")
    private MobileElement subtitleSmsCode;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "resendSmsCodeLink")
    private static MobileElement lnkResendSmsCode;

    @AndroidFindBy(id = "errorMessage")
    private MobileElement errorMessage;

    public MobileElement getSubtitlePhone() {
        return subtitlePhone;
    }

    public MobileElement getSubtitleSmsCode() {
        return subtitleSmsCode;
    }

    public MobileElement getLnkResendSmsCode() {
        return lnkResendSmsCode;
    }

    public MobileElement getErrorMessage() {
        return errorMessage;
    }

    public String get_subtitle_sms_code_text(String areaCode, String phone){
        return "Ingresá el código que te mandamos a " + areaCode + " " +phone;
    }

    /** GETS **/


    public SmsCodePage(){
        super();
        waitForElementVisibility(lnkResendSmsCode);
    }

    public synchronized static SmsCodePage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new SmsCodePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(SmsCodePage.class);
            wait.until(ExpectedConditions.visibilityOf(lnkResendSmsCode));
    }
        return sSoleInstance;
    }


    public void set_code_sms(String smsCode) throws InterruptedException {
        super.set_pin_or_smsCode(smsCode);
    }

    public void click_resend_sms_code(){
        waitForElementEnabled(lnkResendSmsCode);
        lnkResendSmsCode.click();
    }
}
