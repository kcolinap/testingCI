package api.apps.android.nw.movimientos.details.movementTypes;

import api.apps.android.Android;
import api.apps.android.nw.movimientos.details.MovementDetailBasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SubeCardDetailPage extends MovementDetailBasePage {

    private static SubeCardDetailPage sSoleInstance;

    public static String DISCLAIMER_SUBE_TEXT = "Para acreditar tu carga acercá la tarjeta a una terminal automática o descargá la app Carga SUBE. ¿Tenés dudas? Entrá acá";

    //label raw amount
    @AndroidFindBy(id = "itemTitle")
    private MobileElement itemTitle;

    @AndroidFindBy(id = "itemValue")
    private List<MobileElement> itemValue;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[last()]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.TextView")
    private MobileElement disclaimerText;

    public MobileElement getItemTitle() {
        return itemTitle;
    }

    public List<MobileElement> getItemValue() {
        return itemValue;
    }

    public MobileElement getDisclaimerText() {
        return disclaimerText;
    }

    public SubeCardDetailPage() {
        super();
        waitForElementVisibility(transactionIcon);
    }

    public synchronized static SubeCardDetailPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new SubeCardDetailPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(transactionIcon)));
        }
        return sSoleInstance;
    }

}
