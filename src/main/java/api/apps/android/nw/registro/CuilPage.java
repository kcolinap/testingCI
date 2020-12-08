package api.apps.android.nw.registro;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.RegistroBasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CuilPage extends RegistroBasePage {

    private static CuilPage sSoleInstance;

    public static final String LBL_SUBTITLE_CUIL_TEXT = "¿El CUIL es correcto?";
    public static final String DESCRIPTION_CUIL_TEXT = "Revisá que esté bien. Va a ser necesario para algunas funciones de la app.";
    public static final String DUPLICATED_CUIL_TEXT = "El CUIL ya está en uso. ¿Estás seguro que es el tuyo? Escribinos a hola@tunubi.com";

    @AndroidFindBy(id = "subtitleCuil")
    private MobileElement lblSubtitleCuil;

    @AndroidFindBy(id = "descriptionCuil")
    private MobileElement descriptionCuil;

    @AndroidFindBy(id = "dniInput")
    private static MobileElement dniInput;

    @AndroidFindBy(id = "prefixInput")
    private MobileElement txtPrefixDni;

    @AndroidFindBy(id = "verificationCodeInput")
    private MobileElement txtVerificationCode;

    @AndroidFindBy(id = "button")
    private MobileElement button;

    @AndroidFindBy(id = "list")
    private MobileElement list;

    @AndroidFindBy(id = "snackbar_text")
    private static MobileElement snackbar_text;

    @AndroidFindBy(id = "snackbar_action")
    private MobileElement snackbar_action;

    /**** GETS ****/
    public MobileElement getDescriptionCuil() {
        return descriptionCuil;
    }

    public MobileElement getSnackbar_action() {
        return snackbar_action;
    }

    public static MobileElement getSnackbar_text() {
        return snackbar_text;
    }

    public MobileElement getSubtitleCuil() {
        return lblSubtitleCuil;
    }

    public MobileElement getTxtPrefixDni() {
        return txtPrefixDni;
    }

    public MobileElement getDniInput() {
        return dniInput;
    }

    public MobileElement getTxtVerificationCode() {
        return txtVerificationCode;
    }

    public MobileElement getButton() {
        return button;
    }

    public MobileElement getList() {
        return list;
    }

    public CuilPage(){
        super();
        waitForElementVisibility(button);
    }

    public synchronized static CuilPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new CuilPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(CuilPage.class);
            wait.until(ExpectedConditions.visibilityOf(dniInput));
        }
        return sSoleInstance;
    }

    public void click_on_next_button(){
        waitForElementEnabled(button);
        button.click();
    }

    public void click_on_snackbar(){
        snackbar_action.click();
    }

    public MobileElement getPrefix(String prefix){
        MobileElement element;
        try {
            element = (MobileElement)Android.driver.findElement(By.xpath("//android.widget.TextView[@text='"+prefix+"']"));
        }catch (Exception | Error e){
            return null;
        }

        return element;
    }
}
