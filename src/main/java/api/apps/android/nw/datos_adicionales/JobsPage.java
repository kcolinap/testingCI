package api.apps.android.nw.datos_adicionales;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class JobsPage extends BasePage {

    private static JobsPage sSoleInstance;

    public static final String SUBTITLE_TEXT = "¿A qué te dedicás?";
    public static final String DISCLAIMER_TEXT = "Al continuar declarás que tu plata se obtuvo legalmente.";


    @AndroidFindBy(xpath = "//android.widget.RadioGroup/android.widget.RadioButton")
    private static List<MobileElement> occupationsGroup;

    @AndroidFindBy(id = "disclaimerText")
    private MobileElement disclaimerText;

    @AndroidFindBy(id = "button")
    private MobileElement button;

    public static List<MobileElement> getOccupationsGroup() {
        return occupationsGroup;
    }

    public MobileElement getDisclaimerText() {
        return disclaimerText;
    }

    public MobileElement getButton() {
        return button;
    }

    public JobsPage(){
        super();
        waitForElementVisibility(occupationsGroup.get(0));
    }

    public synchronized static JobsPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new JobsPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,20);
            Wrapper.init_elements(JobsPage.class);
            wait.until(ExpectedConditions.visibilityOf(occupationsGroup.get(0)));

        }

        return sSoleInstance;
    }

    public void click_on_occupation(int index){
        occupationsGroup.get(index).click();
    }


}
