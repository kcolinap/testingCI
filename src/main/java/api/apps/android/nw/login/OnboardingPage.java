package api.apps.android.nw.login;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

public class OnboardingPage extends BasePage {

    private static OnboardingPage sSoleInstance;


    public static String
        PAGE1_TITLE = "Bienvenido a Nubi Cuenta",
        PAGE1_SUBTITLE = "Manejá tu plata más fácil con una cuenta 100% digital. ";

    public static String
            PAGE2_TITLE = "Pedí tu Visa Internacional",
            PAGE2_SUBTITLE = "Comprá lo que quieras, pagá tus apps, juegos favoritos y más.";

    public static String
            PAGE3_TITLE = "Simplificá tu modo de pagar",
            PAGE3_SUBTITLE = "Pagá tus servicios, recargá el celu o la SUBE desde donde estés.";

    public static String
            PAGE4_TITLE = "Mandá o pedí plata",
            PAGE4_SUBTITLE = "Rápido, fácil y sin moverte de tu casa. Cuentas claras conservan amistades.";

    @AndroidFindBy(id = "onboardingStepImage")
    private static MobileElement onboardingStepImage;

    @AndroidFindBy(id = "onBoardingStepTitle")
    private MobileElement onBoardingStepTitle;

    @AndroidFindBy(id = "onBoardingStepSubTitle")
    private MobileElement onBoardingStepSubTitle;

    @AndroidFindBy(id = "registerButton")
    private MobileElement registerButton;

    @AndroidFindBy(id = "loginLink")
    private MobileElement loginLink;


    public static MobileElement getOnboardingStepImage() {
        return onboardingStepImage;
    }

    public MobileElement getOnBoardingStepTitle() {
        return onBoardingStepTitle;
    }

    public MobileElement getOnBoardingStepSubTitle() {
        return onBoardingStepSubTitle;
    }

    public MobileElement getRegisterButton() {
        return registerButton;
    }

    public MobileElement getLoginLink() {
        return loginLink;
    }

    /** GETS **/


    public OnboardingPage() {
        super();
        waitForElementVisibility(onboardingStepImage);
    }

    public synchronized static OnboardingPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new OnboardingPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,60);
            Wrapper.init_elements(OnboardingPage.class);
            wait.until(ExpectedConditions.visibilityOf(onboardingStepImage));
        }
        return sSoleInstance;
    }

    public void click_login(){
        actions.singleTap(loginLink);
    }

    public void click_register(){
        actions.singleTap(registerButton);

    }

}
