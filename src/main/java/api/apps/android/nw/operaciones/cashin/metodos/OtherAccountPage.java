package api.apps.android.nw.operaciones.cashin.metodos;

import api.apps.android.Android;
import api.apps.android.nw.TextList;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OtherAccountPage extends BasePage {

    private static OtherAccountPage sSoleInstance;

    public static final String
            TITLE_TEXT = "Cargá por transferencia",
            SUBTITLE_TEXT = "Desde cualquier cuenta bancaria",
            DESCRIPTION_TEXT = "Hacé una transferencia desde cualquier banco. La verás acreditada en el momento.",
            DISCLAIMER_TEXT = "Compartí estos datos a quien vos quieras para que te mande plata desde su banco.";


    @AndroidFindBy(id = "disclaimerText")
    private static MobileElement disclaimerText;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Alias:']")
    private MobileElement alias;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='CBU:']")
    private MobileElement cbu;

    public OtherAccountPage(){
        super();
        wait.until(ExpectedConditions.visibilityOf(disclaimerText));
    }
    public synchronized static OtherAccountPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new OtherAccountPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(disclaimerText)));
        }
        return sSoleInstance;
    }

}
