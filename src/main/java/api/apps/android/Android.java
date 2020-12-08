package api.apps.android;

import api.apps.Apps;
import api.apps.android.nw.NubiWallet;
import core.ADB;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Android extends Apps {

    public static AndroidDriver<MobileElement> driver;
    public static ADB adb;
    public static WebDriverWait wait;

    //Apps here
    public static NubiWallet nubiWallet = new NubiWallet();

    public static void hideKeyboardIfVisible() {
        do{
            ((AndroidDriver)driver).hideKeyboard();
        }while (((AndroidDriver)driver).isKeyboardShown());
    }

}
