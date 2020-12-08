package api.apps.android.nw.operaciones.recarga_celular;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.GenericErrorPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MobileRechargeErrorPage extends GenericErrorPage {

    private static MobileRechargeErrorPage sSoleInstance;


    public static final String
            RECHARGE_CELULAR_TITLE_TEXT = "Recargá tu celular";


    public MobileRechargeErrorPage(){
        super();
        waitForElementVisibility(mainButton);
    }

    public synchronized static MobileRechargeErrorPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new MobileRechargeErrorPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(MobileRechargeErrorPage.class);
            wait.until(ExpectedConditions.visibilityOf(mainButton));
        }
        return sSoleInstance;
    }

    public void back_to_dashboard(){
       Wrapper.waitForElementEnabled(mainButton);
        mainButton.click();
    }


}
