package api.apps.android.nw.registro;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.RegistroBasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TermsConditionsPage extends RegistroBasePage {

    private static TermsConditionsPage sSoleInstance;


    public static final String SUBTITLE_TEXT = "Último, pero no menos importante";
    public static final String TERMS_TEXT = "Para utilizar los servicios de Nubi tenés que aceptar los términos y condiciones.";
    public static final String CHECKBOX_TEXT = "Leí y acepto los términos y condiciones";
    public static final String BTN_CREATE_ACCOUNT_TEXT = "Crear cuenta";

    @AndroidFindBy(id = "checkboxTerms")
    private static MobileElement checkboxTerms;

    @AndroidFindBy(id = "button")
    private MobileElement button;

    @AndroidFindBy(id = "terms")
    private MobileElement terms;


    public MobileElement getCheckboxTerms() {
        return checkboxTerms;
    }

    public MobileElement getButton() {
        return button;
    }

    public MobileElement getTerms() {
        return terms;
    }

    /** GETS **/


    public TermsConditionsPage(){
        super();
        waitForElementVisibility(checkboxTerms);
    }

    public synchronized static TermsConditionsPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new TermsConditionsPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(TermsConditionsPage.class);
            wait.until(ExpectedConditions.visibilityOf(checkboxTerms));
        }
        return sSoleInstance;
    }

    public void click_checkbox_tac(){
        checkboxTerms.click();
    }

    public void click_create_account(){
        waitForElementEnabled(button);
        button.click();
    }

    public void click_tyc_link(){
        terms.click();
    }

}
