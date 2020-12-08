package api.apps.android.nw.common;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.basePages.BasePage;
import core.Util;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonPinPage extends BasePage {

    private static CommonPinPage sSoleInstance;


    private static String LBL_PIN = "1111";
    private static String LBL_INVALID_PIN = "0000";

    public static String SET_NUBI_PASSWORD_TEXT = "Ingresá tu Clave Nubi";
    public static String WRONG_NUBI_PASSWORD_TEXT = "La Clave Nubi no es correcta";

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "firstDigitInput")
    @AndroidFindBy(id = "pinMask1")
    protected static  MobileElement firstDigit;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "secondDigitInput")
    @AndroidFindBy(id = "pinMask2")
    protected MobileElement secondDigit;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "thirdDigitInput")
    @AndroidFindBy(id = "pinMask3")
    protected  MobileElement thirdDigit;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "fourthDigitInput")
    @AndroidFindBy(id = "pinMask4")
    protected static MobileElement fourthDigit;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "enterCurrentPinLabel")
    @AndroidFindBy(id = "enterNewPinLabel")
    @AndroidFindBy(id = "message")
    protected static MobileElement lblChangePin;

    @AndroidFindBy(id = "errorMessage")
    private MobileElement errorMessageElement;

    @AndroidFindBy(id="keyboardButton1")
    protected static MobileElement button1;

    @AndroidFindBy(id="keyboardButton2")
    protected static MobileElement button2;

    @AndroidFindBy(id="keyboardButton3")
    protected static MobileElement button3;

    @AndroidFindBy(id="keyboardButton4")
    protected static MobileElement button4;

    @AndroidFindBy(id="keyboardButton5")
    protected static MobileElement button5;

    @AndroidFindBy(id="keyboardButton6")
    protected static MobileElement button6;

    @AndroidFindBy(id="keyboardButton7")
    protected static MobileElement button7;

    @AndroidFindBy(id="keyboardButton8")
    protected static MobileElement button8;

    @AndroidFindBy(id="keyboardButton9")
    protected static MobileElement button9;

    @AndroidFindBy(id="keyboardButton0")
    protected static MobileElement button0;

    public MobileElement getErrorMessageElement() {
        return errorMessageElement;
    }

    public static MobileElement getLblChangePin() {
        return lblChangePin;
    }

    public MobileElement getFirstDigit() {
        return firstDigit;
    }

    public MobileElement getSecondDigit() {
        return secondDigit;
    }

    public MobileElement getThirdDigit() {
        return thirdDigit;
    }

    public MobileElement getFourthDigit() {
        return fourthDigit;
    }

    protected CommonPinPage(){
        super();
        super.waitForElementVisibility(firstDigit);
    }

    public synchronized static CommonPinPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new CommonPinPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(CommonPinPage.class);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(firstDigit)));
        }
        return sSoleInstance;
    }

    public void set_valid_pin() throws InterruptedException {
       set_pin_or_smsCode(LBL_PIN);

   }

   public void set_incomplete_pin(){
        set_incomplete_pin_or_smsCode(String.valueOf(util.generarNumeroRandom(2)));
   }

   public void set_invalid_pin() throws InterruptedException {
        set_pin_or_smsCode(String.valueOf(util.generarNumeroRandom(4)));
   }

    public void  set_pin_or_smsCode(String value) throws InterruptedException {
        char[] auxValue = {value.charAt(0), value.charAt(1), value.charAt(2), value.charAt(3)};


        firstDigit.click();

        if(Wrapper.elementExists(button1)){
            for(char character : auxValue ){
                switch (character){
                    case '1':
                        actions.singleTap(button1);
                        break;
                    case '2':
                        actions.singleTap(button2);
                        break;
                    case '3':
                        actions.singleTap(button3);
                        break;
                    case '4':
                        actions.singleTap(button4);
                        break;
                    case '5':
                        actions.singleTap(button5);
                        break;
                    case '6':
                        actions.singleTap(button6);
                        break;
                    case '7':
                        actions.singleTap(button7);
                        break;
                    case '8':
                        actions.singleTap(button8);
                        break;
                    case '9':
                        actions.singleTap(button9);
                        break;
                    case '0':
                        actions.singleTap(button0);
                        break;

                }
            }
        }else {
            Android.driver.pressKey(new KeyEvent(util.getKeyPad(String.valueOf(auxValue[0]))));
            Android.driver.pressKey(new KeyEvent(util.getKeyPad(String.valueOf(auxValue[1]))));
            Android.driver.pressKey(new KeyEvent(util.getKeyPad(String.valueOf(auxValue[2]))));
            Android.driver.pressKey(new KeyEvent(util.getKeyPad(String.valueOf(auxValue[3]))));
        }

        Thread.sleep(1500);
    }

    public void set_incomplete_pin_or_smsCode(String value){
        char[] auxValue = {value.charAt(0), value.charAt(1)};

        firstDigit.click();
        Android.driver.pressKey(new KeyEvent(util.getKeyPad(String.valueOf(auxValue[0]))));
        Android.driver.pressKey(new KeyEvent(util.getKeyPad(String.valueOf(auxValue[1]))));


    }

}
