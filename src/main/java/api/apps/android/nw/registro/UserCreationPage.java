package api.apps.android.nw.registro;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.RegistroBasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserCreationPage extends RegistroBasePage {

    private static UserCreationPage sSoleInstance;

    public static final String LBL_SUBTITLE_USER_CREATION = "Creá tu usuario";
    public static final String
        INPUT_ERROR_TEXT_LENGHT_USER = "Mínimo 4 caracteres",
        INPUT_ERROR_USER_SPACE_SCHAR = "Sólo se permiten letras y números",
        INPUT_ERROR_PASSWORD_SPACE = "No se permite espacio",
        INPUT_ERROR_USER_REGISTERED = "El nombre de usuario no está disponible";

    @AndroidFindBy(id = "subtitleUserCreation")
    private static MobileElement lblSubtitleUserCreation;

    @AndroidFindBy(id = "userInput")
    private MobileElement userInput;


    @AndroidFindBy(id = "passwordInput")
    private MobileElement passwordInput;

    @AndroidFindBy(id = "textinput_error")
    private MobileElement textinput_error;

    @AndroidFindBy(id = "validationTitle")
    private MobileElement lblValidationTitle;

    @AndroidFindBy(id = "digitsValidatorTitle")
    private MobileElement optDigitsValidator;

    @AndroidFindBy(id = "uppercaseValidatorTitle")
    private MobileElement optUpperCasevalidator;

    @AndroidFindBy(id = "alphanumericValidatorTitle")
    private MobileElement optAlphanumericValidator;

    @AndroidFindBy(id = "specialCharacterValidatorTitle")
    private MobileElement optSpecialCharValidator;

    @AndroidFindBy(id = "button")
    private static MobileElement button;


    /*** GETS ***/

    public MobileElement getTextinput_error() {
        return textinput_error;
    }

    public MobileElement getUserInput() {
        return userInput;
    }

    public MobileElement getPasswordInput() {
        return passwordInput;
    }

    public MobileElement getlblSubtitleUserCreation() {
        return lblSubtitleUserCreation;
    }

    public MobileElement getLblValidationTitle() {
        return lblValidationTitle;
    }

    public MobileElement getOptDigitsValidator() {
        return optDigitsValidator;
    }

    public MobileElement getOptUpperCasevalidator() {
        return optUpperCasevalidator;
    }

    public MobileElement getOptAlphanumericValidator() {
        return optAlphanumericValidator;
    }

    public MobileElement getOptSpecialCharValidator() {
        return optSpecialCharValidator;
    }

    public MobileElement getButton() {
        return button;
    }

    public UserCreationPage(){
        super();
        waitForElementVisibility(lblSubtitleUserCreation);

    }

    public synchronized static UserCreationPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new UserCreationPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(UserCreationPage.class);
            wait.until(ExpectedConditions.visibilityOf(lblSubtitleUserCreation));
        }
        return sSoleInstance;
    }

    public void set_user(String user){
        userInput.clear();
        userInput.sendKeys(user);
        Android.hideKeyboardIfVisible();
    }

    public void set_password(String pass){
        passwordInput.clear();
        passwordInput.sendKeys(pass);
       Android.hideKeyboardIfVisible();
    }

    public void click_on_next_button(){
        waitForElementEnabled(button);
        button.click();
    }

    public boolean validate_text_input_error_message(int opc){
        boolean result = false;
        boolean isVisible = false;

        try{
            isVisible = textinput_error.isDisplayed();
        }catch (Exception | Error e){
            isVisible = false;
        }


        switch (opc){
            case 0:
                if(isVisible)
                    waitForElementNotVisible(textinput_error);
                break;
            case 1:
                if(!isVisible)
                    waitForElementVisibility(textinput_error);

                result = true;
                break;
        }
        return result;
    }
}
