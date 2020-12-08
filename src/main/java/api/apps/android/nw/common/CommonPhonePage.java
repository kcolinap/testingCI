package api.apps.android.nw.common;

import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public abstract class CommonPhonePage extends BasePage {

    private static CommonPhonePage sSoleInstance;

    @AndroidFindBy(id = "areaCodeInput")
    protected MobileElement areaCodeInput;

    @AndroidFindBy(id = "phoneInput")
    protected static MobileElement phoneInput;

    public MobileElement getAreaCodeInput() {
        return areaCodeInput;
    }

    public MobileElement getPhoneInput() {
        return phoneInput;
    }

    protected CommonPhonePage(){
        super();
        super.waitForElementVisibility(phoneInput);
    }


}
