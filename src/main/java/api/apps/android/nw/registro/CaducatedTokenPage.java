package api.apps.android.nw.registro;

import api.apps.android.Android;
import api.apps.android.nw.basePages.RegistroBasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CaducatedTokenPage extends RegistroBasePage {

    private static CaducatedTokenPage sSoleInstance;


    public static final String SUBTITLE_TEXT = "Uy, el link ya no anda";
    public static final String DISCLAIMER_TEXT = "Ese link se venció, pero ¡no te preocupes! Podés volver a comenzar el registro desde la app.";

    @AndroidFindBy(id = "deviceImage")
    private MobileElement deviceImage;

    @AndroidFindBy(id = "subtitle")
    private MobileElement subtitle;

    @AndroidFindBy(id = "disclaimerText")
    private MobileElement disclaimerText;

    @AndroidFindBy(id = "actionButtonPrimary")
    private static MobileElement actionButtonPrimary;

    public MobileElement getDeviceImage() {
        return deviceImage;
    }

    public MobileElement getSubtitle() {
        return subtitle;
    }

    public MobileElement getDisclaimerText() {
        return disclaimerText;
    }

    public static MobileElement getActionButtonPrimary() {
        return actionButtonPrimary;
    }

    /** GETS **/


    public CaducatedTokenPage(){
        super();
        waitForElementVisibility(actionButtonPrimary);
    }

    public synchronized static CaducatedTokenPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new CaducatedTokenPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,20);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(actionButtonPrimary)));
        }
        return sSoleInstance;
    }


}
