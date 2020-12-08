package api.apps.android.nw.operaciones.cashin;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CashInLandingPage extends BasePage {

    private static CashInLandingPage sSoleInstance;

    public static final String
            TITLE_TEXT = "Cargá plata",
            SUBTITLE_TEXT = "Elegí cómo cargar plata a tu cuenta Nubi",
            OWN_ACOUNT_TEXT = "Una cuenta bancaria tuya",
            OTHER_ACOUNT_TEXT = "Cualquier cuenta bancaria",
            RAPIPAGO_TEXT = "Rapipago";


    @AndroidFindBy(id = "selectCashInSourceTitle")
    private static MobileElement selectCashInSourceTitle;

    @AndroidFindBy(id = "cashInSelectTitle")
    private MobileElement cashInSelectTitle;

    @AndroidFindBy(xpath = "//androidx.cardview.widget.CardView//android.widget.ImageView")
    private static List<MobileElement> icons;

    @AndroidFindBy(xpath = "//androidx.cardview.widget.CardView//android.widget.TextView")
    private static List<MobileElement> texts;


    public MobileElement getSelectCashInSourceTitle() {
        return selectCashInSourceTitle;
    }

    public MobileElement getCashInSelectTitle() {
        return cashInSelectTitle;
    }

    public static List<MobileElement> getIcons() {
        return icons;
    }

    public static List<MobileElement> getTexts() {
        return texts;
    }

    public CashInLandingPage() {
        super();
        wait.until(ExpectedConditions.visibilityOf(texts.get(2)));
    }

    public synchronized static CashInLandingPage getInstance() {
        if (sSoleInstance == null){
            sSoleInstance = new CashInLandingPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(CashInLandingPage.class);
            wait.until(ExpectedConditions.visibilityOf(texts.get(2)));
        }
        return sSoleInstance;
    }

    public void click_option(int option){
        actions.singleTap(icons.get(option));
    }
}
