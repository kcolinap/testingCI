package api.apps.android.nw.movimientos.details.movementTypes;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.movimientos.details.MovementDetailBasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class P2PSendDetailPage extends MovementDetailBasePage {

    private static P2PSendDetailPage sSoleInstance;


    public P2PSendDetailPage() {
        super();
        waitForElementVisibility(transactionIcon);
    }

    public synchronized static P2PSendDetailPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new P2PSendDetailPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(PrepaidCardDetailPage.class);
            wait.until(ExpectedConditions.visibilityOf(transactionIcon));
        }
        return sSoleInstance;
    }

}
