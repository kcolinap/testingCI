package api.apps.android.nw.operaciones.recarga_celular;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.common.CommonPinPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NubiPassRechargeMobilePage extends CommonPinPage {

    private static NubiPassRechargeMobilePage sSoleInstance;


    public NubiPassRechargeMobilePage(){
        super();
        waitForElementVisibility(firstDigit);
    }

    public synchronized static NubiPassRechargeMobilePage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new NubiPassRechargeMobilePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(NubiPassRechargeMobilePage.class);
            wait.until(ExpectedConditions.visibilityOf(firstDigit));
        }
        return sSoleInstance;
    }

}
