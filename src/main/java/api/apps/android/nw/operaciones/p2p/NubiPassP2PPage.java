package api.apps.android.nw.operaciones.p2p;

import api.apps.android.Android;
import api.apps.android.nw.common.CommonPinPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NubiPassP2PPage extends CommonPinPage {

    private static NubiPassP2PPage sSoleInstance;


    public NubiPassP2PPage(){
        super();
        waitForElementVisibility(firstDigit);
    }

    public synchronized static NubiPassP2PPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new NubiPassP2PPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(firstDigit)));
        }
        return sSoleInstance;
    }

}
