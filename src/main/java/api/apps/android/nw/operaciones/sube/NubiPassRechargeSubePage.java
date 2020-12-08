package api.apps.android.nw.operaciones.sube;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.common.CommonPinPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NubiPassRechargeSubePage extends CommonPinPage {

    private static NubiPassRechargeSubePage sSoleInstance;


    public NubiPassRechargeSubePage(){
        super();
        waitForElementVisibility(firstDigit);
    }

    public synchronized static NubiPassRechargeSubePage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new NubiPassRechargeSubePage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(NubiPassRechargeSubePage.class);
            wait.until(ExpectedConditions.visibilityOf(firstDigit));
        }
        return sSoleInstance;
    }

}
