package api.apps.android.nw.tarjetaprepaga.fisica.denunciar_tarjeta;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ReportCardPage extends BasePage {

    private static ReportCardPage sSoleInstance;

    public static final String TITLE_TEXT = "Tarjeta Prepaga";
    public static final String REASON_SUBTITLE_TEXT = "¿Por qué la querés denunciar?";
    public static final String REASON_LOST_TEXT = "Pérdida";
    public static final String REASON_STEAL_TEXT = "Robo";
    public static final String REASON_DAMAGE_TEXT = "Rotura";
    public static final String DISCLAIMER_TEXT = "Se te descontará $200 de tu saldo por la reposición de la tarjeta.";
    public static final String ADDRESS_SUBTITLE_TEXT = "¿Dónde enviamos tu tarjeta?";


    @AndroidFindBy(id = "prepaidCardTitle")
    private MobileElement prepaidCardTitle;

    @AndroidFindBy(id = "reasonSubtitle")
    private MobileElement reasonSubtitle;

    @AndroidFindBy(id = "radioLoss")
    private MobileElement radioLoss;

    @AndroidFindBy(id = "radioSteal")
    private MobileElement radioSteal;

    @AndroidFindBy(id = "radioDamage")
    private MobileElement radioDamage;

    @AndroidFindBy(id = "disclaimerText")
    private MobileElement disclaimerText;

    @AndroidFindBy(id = "addressSubtitle")
    private MobileElement addressSubtitle;

    @AndroidFindBy(id = "selectAddressButton")
    private MobileElement selectAddressButton;

    @AndroidFindBy(id = "button")
    private static MobileElement button;

    @AndroidFindBy(xpath= "//android.widget.ScrollView//android.widget.RadioGroup/android.widget.RadioButton")
    private List<MobileElement> radioAddresses;


    public ReportCardPage() {
        super();
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        waitForElementVisibility(button);
    }

    public synchronized static ReportCardPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new ReportCardPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(button)));
        }

        return sSoleInstance;
    }

    public MobileElement getPrepaidCardTitle() {
        return prepaidCardTitle;
    }

    public MobileElement getRadioLoss() {
        return radioLoss;
    }

    public MobileElement getRadioSteal() {
        return radioSteal;
    }

    public MobileElement getRadioDamage() {
        return radioDamage;
    }

    public MobileElement getDisclaimerText() {
        return disclaimerText;
    }

    public MobileElement getAddressSubtitle() {
        return addressSubtitle;
    }

    public MobileElement getSelectAddressButton() {
        return selectAddressButton;
    }

    public static MobileElement getButton() {
        return button;
    }

    public int adresses_quantity(){
        return radioAddresses.size();
    }

    public MobileElement getReasonSubtitle() {
        return reasonSubtitle;
    }

    public void click_confirm_report_card(){
        button.click();
    }
}
