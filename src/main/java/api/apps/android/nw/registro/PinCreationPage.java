package api.apps.android.nw.registro;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.common.CommonPinPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PinCreationPage extends CommonPinPage {

    private static PinCreationPage sSoleInstance;

    public static final String
            RAW_PIN = "1111",
            LBL_TITLE_TEXT = "Registro",
            pin_creation_title = "Ahora, creá tu Clave Nubi",
            pin_creation_subtitle = "Te pediremos esta clave para confirmar tus transacciones";

    @AndroidFindBy(id = "pinCreationTitle")
    private static MobileElement pinCreationTitle;


    @AndroidFindBy(id = "subtitlePinCreation")
    private MobileElement subtitlePinCreation;


    public MobileElement getPinCreationTitle() {
        return pinCreationTitle;
    }

    public MobileElement getSubtitlePinCreation() {
        return subtitlePinCreation;
    }

    public PinCreationPage(){
        super();
        waitForElementVisibility(pinCreationTitle);
    }

    public synchronized static PinCreationPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new PinCreationPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(PinCreationPage.class);
            wait.until(ExpectedConditions.visibilityOf(pinCreationTitle));
        }
        return sSoleInstance;
    }


   public void set_pin(String pin) throws InterruptedException {
       super.set_pin_or_smsCode(pin);

   }

   public void set_correct_nubi_passwprd(){
   }
}
