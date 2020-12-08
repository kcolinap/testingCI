package api.apps.android.nw.common;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PermissionsPopUp extends BasePage {

    private static PermissionsPopUp sSoleInstance;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.permissioncontroller:id/permission_allow_button\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.packageinstaller:id/permission_allow_button\")")
    @AndroidFindBy(id="permission_message")
    private MobileElement permission_message;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.permissioncontroller:id/permission_allow_button\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.packageinstaller:id/permission_allow_button\")")
    @AndroidFindBy(id="permission_allow_button")
    private static MobileElement permission_allow_button;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.permissioncontroller:id/permission_allow_button\")")
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.packageinstaller:id/permission_allow_button\")")
    @AndroidFindBy(id="permission_deny_button")
    private MobileElement permission_deny_button;

    public MobileElement getPermission_message() {
        return permission_message;
    }

    public MobileElement getPermission_allow_button() {
        return permission_allow_button;
    }

    public MobileElement getPermission_deny_button() {
        return permission_deny_button;
    }

    public PermissionsPopUp(){
        super();
        super.waitForElementVisibility(permission_allow_button);
    }

    public synchronized static PermissionsPopUp getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new PermissionsPopUp();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(permission_allow_button)));
        }
        return sSoleInstance;
    }
    public void click_allow_permission(){
        permission_allow_button.click();
    }
}
