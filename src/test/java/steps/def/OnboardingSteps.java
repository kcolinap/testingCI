package steps.def;

import api.apps.Apps;
import api.apps.android.Actions;
import api.apps.android.Wrapper;
import api.apps.android.nw.login.OnboardingPage;
import com.github.javafaker.App;
import com.sun.javafx.scene.traversal.Direction;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

public class OnboardingSteps {

    private static final Logger logger = LogManager.getLogger();

    @When("Swipe hasta pantalla {int}")
    public void swipe_to_page(int nroPage) throws Exception {
        try {

            OnboardingPage onboardingPage = OnboardingPage.getInstance();
            Actions actions = new Actions();

            for (int i = 1; i < nroPage; i++) {
                actions.swipeOnElement(onboardingPage.getOnBoardingStepTitle(), Direction.LEFT);
            }

        }catch (Exception | Error err){
            logger.error(err);
            err.printStackTrace();
            throw err;
        }
    }

    @Then("Validar elementos de pantalla {int}")
    public void validarElementosDePantallaNroPage(int nroPage) throws Exception {
        try{

            OnboardingPage onboardingPage = OnboardingPage.getInstance();

            Assert.assertTrue(Wrapper.elementExists(onboardingPage.getLoginLink()));
            Assert.assertTrue(Wrapper.elementExists(onboardingPage.getRegisterButton()));

            switch (nroPage){
                case 1:
                    Assert.assertEquals(OnboardingPage.PAGE1_TITLE, Wrapper.getElementText(onboardingPage.getOnBoardingStepTitle()));
                    Assert.assertEquals(OnboardingPage.PAGE1_SUBTITLE, Wrapper.getElementText(onboardingPage.getOnBoardingStepSubTitle()));
                    break;
                case 2:
                    Assert.assertEquals(OnboardingPage.PAGE2_TITLE, Wrapper.getElementText(onboardingPage.getOnBoardingStepTitle()));
                    Assert.assertEquals(OnboardingPage.PAGE2_SUBTITLE, Wrapper.getElementText(onboardingPage.getOnBoardingStepSubTitle()));
                    break;
                case 3:
                    Assert.assertEquals(OnboardingPage.PAGE3_TITLE, Wrapper.getElementText(onboardingPage.getOnBoardingStepTitle()));
                    Assert.assertEquals(OnboardingPage.PAGE3_SUBTITLE, Wrapper.getElementText(onboardingPage.getOnBoardingStepSubTitle()));
                    break;
                case 4:
                    Assert.assertEquals(OnboardingPage.PAGE4_TITLE, Wrapper.getElementText(onboardingPage.getOnBoardingStepTitle()));
                    Assert.assertEquals(OnboardingPage.PAGE4_SUBTITLE, Wrapper.getElementText(onboardingPage.getOnBoardingStepSubTitle()));
                    break;
            }

        }catch (Exception | Error err){
            logger.error(err);
            err.printStackTrace();
            throw err;
        }
    }

    @When("Presionar boton {string}")
    public void presionarBoton(String typeButton) throws Exception {
        try{
            Apps.util.jumpOnboardingPage(typeButton.toUpperCase());
        }catch (Exception | Error e){
            logger.error(e);
            e.printStackTrace();
            throw e;
        }
    }
}
