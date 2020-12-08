package api.apps.android.nw.basePages;

import api.apps.android.Android;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;

public abstract class PinBasePage extends BasePage {

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "firstDigitInput")
    @AndroidFindBy(id = "pinMask1")
    protected MobileElement firstDigit;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "secondDigitInput")
    @AndroidFindBy(id = "pinMask2")
    protected MobileElement secondDigit;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "thirdDigitInput")
    @AndroidFindBy(id = "pinMask3")
    protected MobileElement thirdDigit;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "fourthDigitInput")
    @AndroidFindBy(id = "pinMask4")
    protected MobileElement fourthDigit;

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

    public PinBasePage(){
        super();
    }

    public void set_pin_or_smsCode(String value){
        char[] auxValue = {value.charAt(0), value.charAt(1), value.charAt(2), value.charAt(3)};

        firstDigit.click();
        Android.adb.setTexto(String.valueOf(auxValue[0]));
        Android.adb.setTexto(String.valueOf(auxValue[1]));
        Android.adb.setTexto(String.valueOf(auxValue[2]));
        Android.adb.setTexto(String.valueOf(auxValue[3]));
    }

    public void set_incomplete_pin_or_smsCode(String value){
        char[] auxValue = {value.charAt(0), value.charAt(1), value.charAt(2), value.charAt(3)};

        firstDigit.click();
        Android.adb.setTexto(String.valueOf(auxValue[0]));
        Android.adb.setTexto(String.valueOf(auxValue[1]));

    }
}
