package api.apps.android.nw.tarjetaprepaga.landing;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.tarjetaprepaga.PrepaidCardBasePage;
import api.apps.android.nw.tarjetaprepaga.fisica.LandingPhysicalPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NoPrepaidCardPage extends PrepaidCardBasePage {

    private static NoPrepaidCardPage sSoleInstance;

    public static final String NO_CARD_TITLE = "Tarjeta Nubi Visa";
    public static final String BUTTON_TEXT =  "Quiero mi tarjeta Nubi Visa";

    @AndroidFindBy(id = "button")
    private static MobileElement button;

    @AndroidFindBy(id = "card")
    private MobileElement card;

    public MobileElement getButton() {
        return button;
    }

    public MobileElement getCard() {
        return card;
    }

    public NoPrepaidCardPage(){
        super();
        waitForElementVisibility(button);
    }

    public synchronized static NoPrepaidCardPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new NoPrepaidCardPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(NoPrepaidCardPage.class);
            wait.until(ExpectedConditions.visibilityOf(button));
        }

        return sSoleInstance;
    }

    public void click_on_want_mycard(){
        waitForElementEnabled(button);
        button.click();
    }
}
