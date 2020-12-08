package api.apps.android.nw.basePages;

import api.apps.android.Actions;
import api.apps.android.Android;
import api.apps.android.nw.NubiWallet;
import core.Util;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    private AppiumDriver<MobileElement> driver;
    public WebDriverWait wait;
    public Util util;
    public Actions actions = new Actions();

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "closeButton")
    @AndroidFindBy(id = "backButton")
    protected static MobileElement backOrCloseBtn;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "title")
    protected static MobileElement lblTitle;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "subtitle")
    @AndroidFindBy(id = "subTitle")
    protected MobileElement lblSubtitle;



    public BasePage() {
        this.driver = Android.driver;
        util = NubiWallet.util;
        wait = new WebDriverWait(Android.driver,30);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static MobileElement getBackOrCloseBtn() {
        return backOrCloseBtn;
    }

    public static MobileElement getLblTitle() {
        return lblTitle;
    }

    public MobileElement getLblTitleNonStatic() {
        return lblTitle;
    }
    public MobileElement getLblSubtitle() {
        return lblSubtitle;
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public void waitForElementVisibility(MobileElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementNotVisible(MobileElement element){
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public  void  waitForElementEnabled(MobileElement element){
        wait.until(ExpectedConditions.attributeContains(element,"enabled","true"));
    }

    public  void  waitForElementDisabled(MobileElement element){
        wait.until(ExpectedConditions.attributeContains(element,"enabled","false"));
    }

    public boolean elementExists(MobileElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean elementIsEnabled(MobileElement element){
        try {
            return element.isEnabled();
        }catch (Exception e){
            return false;
        }
    }

    public boolean elementIsCheked(MobileElement element){
        try {
            return element.getAttribute("checked").equals("true");
        }catch (Exception e){
            return false;
        }
    }

    public void hideKeyboardIfVisible() {
        if (((AndroidDriver)driver).isKeyboardShown()) {
            driver.hideKeyboard();
        }
    }

    public void click_close_back_button(){
        wait.until(ExpectedConditions.visibilityOf(backOrCloseBtn));
        backOrCloseBtn.click();
    }

    public String get_element_text(MobileElement element){
        return element.getText();
    }

    public String get_element_attribute(MobileElement element, String attribute){
        return element.getAttribute(attribute);
    }

    public void clean_inpun_text_element(MobileElement element){
        element.clear();
    }


    /**
     * Esto lo traslade de lo que tenemos un UiObject pero QUIZA no sea necesaria
     * Lo que hacia era borrar desde el ultimo caracter hacia el primero, dado que no funcionaba bien en los
     * emuladores, ahora QUIZA el clear() lo realiza correctaemente (VALIDAR COMPORTAMIENTO)
     *
     * BORRAR DESPUES DE VALIDAR
     *
     * @param element
     */
    public void clearTextFromEndChar(MobileElement element) {
        element.click();

        for (int i = 0; i < element.getText().length(); i++) {
            ((AndroidDriver)driver).pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
        }

        for (int j = 0; j < element.getText().length(); j++) {
            ((AndroidDriver)driver).pressKey(new KeyEvent(AndroidKey.DEL));
        }
    }

}
