package steps.def;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.TextList;
import api.apps.android.nw.contrasenia.PasswordRecoveryLandingPage;
import api.apps.android.nw.login.LoginPage;
import api.apps.android.nw.registro.EmailPage;
import com.github.javafaker.App;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;

public class RGSLogin {

    boolean status;

    //private static NubiWallet nubiWallet = Android.app.nubiWallet;

    private CommonSteps commonSteps = new CommonSteps();

    @Test
    @When("Usuario ingresa {string} y {string}")
    public void setCredentials(String user, String pass) throws Exception{
        try {

            Apps.util.jumpOnboardingPage("LOGIN");

            LoginPage loginPage = LoginPage.getInstance();
            loginPage.typeUsername(user);
            loginPage.typePassword(pass);

            Android.hideKeyboardIfVisible();

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @Then("Validar que boton iniciar sesion esta {string}")
    public void validate_condition_on_next_button_as(String condition) throws Exception {
        try{

            LoginPage loginPage = LoginPage.getInstance();

            if(condition.toLowerCase().contentEquals("habilitado")) {
                Wrapper.waitForElementEnabled(LoginPage.getLoginBtn());
            }else {
                if(Wrapper.elementIsEnabled(LoginPage.getLoginBtn()))
                    Wrapper.waitForElementDisabled(LoginPage.getLoginBtn());
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }

    }

    @Then("User tap init session button")
    public void user_tap_init_session_button(){
        try {
            LoginPage loginPage = new LoginPage();
            loginPage.clickLogin();;
            Thread.sleep(150);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Then("Validar texto de link {string}")
    public void validar_link(String link) throws Exception {
        try {

            link = link.toLowerCase();

            Apps.util.jumpOnboardingPage("LOGIN");

            LoginPage loginPage = LoginPage.getInstance();

            switch (link){
                case "crear_cuenta":
                    Assert.assertEquals(LoginPage.CREATE_ACCOUNT_TEXT, Wrapper.getElementText(loginPage.getRegisterBtn()));
                    break;
                case "forgot_password":
                    Assert.assertEquals(LoginPage.FORGOT_PASSWORD_TEXT, Wrapper.getElementText(loginPage.getForgotPassLink()));
                    break;
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @Then("Validar pantalla al pulsar link {string}")
    public void validar_pantalla_desde_link(String link) throws Exception{
        try {

            link = link.toLowerCase();
            LoginPage loginPage = LoginPage.getInstance();

            switch (link){
                case "crear_cuenta":
                    loginPage.clickRegister();
                    //commonSteps.esperaSegundos(1000);
                    EmailPage.getInstance();
                    break;
                case "forgot_password":
                    loginPage.clickForgotPassword();
                    //commonSteps.esperaSegundos(1000);
                    PasswordRecoveryLandingPage.getInstance();
                    break;
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

}
