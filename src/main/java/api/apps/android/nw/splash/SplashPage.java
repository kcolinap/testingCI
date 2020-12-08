package api.apps.android.nw.splash;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SplashPage extends BasePage {

    private static SplashPage sSoleInstance;


    @AndroidFindBy(id = "logo")
    private static MobileElement logoImage;


    public static MobileElement getLogoImage() {
        return logoImage;
    }


    public SplashPage() {
       super();
       waitForElementVisibility(logoImage);
    }


    public synchronized static SplashPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new SplashPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            Wrapper.init_elements(SplashPage.class);
            wait.until(ExpectedConditions.visibilityOf(logoImage));
        }
        return sSoleInstance;
    }

}
