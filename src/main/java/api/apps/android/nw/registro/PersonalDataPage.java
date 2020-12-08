package api.apps.android.nw.registro;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.RegistroBasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PersonalDataPage extends RegistroBasePage {

    private static PersonalDataPage sSoleInstance;

    private AppiumDriver<MobileElement> driver;
    public WebDriverWait wait ;

    public static final String LBL_SUBTITLE_USER_DATA = "Contanos sobre vos";
    public static final String  LBL_INPUT_ERROR_TEXT = "Sólo se permiten letras";
    public static final String  LBL_INPUT_ERROR_LENGHT_TEXT = "Mínimo 7 números";

    @AndroidFindBy(id = "subtitleUserData")
    private MobileElement subtitleUserData;

    @AndroidFindBy(id = "textinput_error")
    private MobileElement textinput_error;

    @AndroidFindBy(id = "nameInput")
    private MobileElement txtName;

    @AndroidFindBy(id = "lastNameInput")
    private MobileElement txtLastName;

    @AndroidFindBy(id = "dniInput")
    private MobileElement dniInput;

    @AndroidFindBy(id = "femaleGenderOption")
    private MobileElement optFemale;

    @AndroidFindBy(id = "maleGenderOption")
    private MobileElement optMale;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "circleButton")
    @AndroidFindBy(id = "button")
    private static MobileElement button;

    public MobileElement getSubtitleUserData() {
        return subtitleUserData;
    }

    public MobileElement getTxtName() {
        return txtName;
    }

    public MobileElement getDniInput() {
        return dniInput;
    }

    public MobileElement getTxtLastName() {
        return txtLastName;
    }

    public MobileElement getOptFemale() {
        return optFemale;
    }

    public MobileElement getOptMale() {
        return optMale;
    }

    public MobileElement getButton() {
        return button;
    }

    public MobileElement getTextinput_error() {
        return textinput_error;
    }


    public PersonalDataPage(){
       super();
        waitForElementVisibility(button);
    }

    public synchronized static PersonalDataPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new PersonalDataPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(PersonalDataPage.class);
            wait.until(ExpectedConditions.visibilityOf(button));
        }
        return sSoleInstance;
    }

   public void setName(String name){
        txtName.click();
      // if(!Apps.wrapper.get_element_attribute(txtName, "text").contentEquals(""))
          // txtName.clear();

      txtName.sendKeys(name);
   }

    public void setLastName(String lastName){
        txtLastName.click();
        //if(!Apps.wrapper.get_element_attribute(txtLastName, "text").contentEquals(""))
         //   txtLastName.clear();

        txtLastName.sendKeys(lastName);
    }

    public void setDni(String dni){
        dniInput.click();
        //if(!Apps.wrapper.get_element_attribute(dniInput, "text").contentEquals(""))
         //   dniInput.clear();

        dniInput.sendKeys(dni);

    }

    public void setFemaleGender(){
        optFemale.click();
    }

    public void setMaleGender(){
        optMale.click();
    }

    public void click_on_next_button(){
       waitForElementEnabled(button);
        button.click();
    }

}
