package api.apps.android;

import api.apps.android.Android;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrapper {

    private static WebDriverWait wait = Android.wait;

    public Wrapper(){

    }

    /**GET ELEMENT TEXT
     *
     * @param element
     * @return mobile element text
     */
    public static String getElementText(MobileElement element){
        waitForElementVisibility(element);
        return element.getText().replace("\n", " ");
    }

    /**
     *
     * @param element
     * @param attribute
     * @return return a given element attribute
     */
    public static String get_element_attribute(MobileElement element, String attribute){
        return element.getAttribute(attribute);
    }

    /**
     * Check if the element exist on page
     * @param element
     * @return true if exist
     *         false if not
     */
    public static boolean elementExists(MobileElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean elementIsEnabled(MobileElement element){
        try {
            return element.isEnabled();
        }catch (Exception e){
            return false;
        }
    }

    public static boolean elementIsChecked(MobileElement element){
        try {
            return element.getAttribute("checked").equals("true");
        }catch (Exception e){
            return false;
        }
    }

    public static void clean_inpun_text_element(MobileElement element){
        element.clear();
    }

    public static void waitForElementVisibility(MobileElement element) {
        // wait = new WebDriverWait(Android.driver,30);
        Android.wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementNotVisible(MobileElement element){
        //wait = new WebDriverWait((AndroidDriver)Android.driver,30);
        Android.wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void  waitForElementEnabled(MobileElement element){
       // wait = new WebDriverWait((AndroidDriver)Android.driver,30);
        Android.wait.until(ExpectedConditions.attributeToBe(element,"enabled","true"));
    }

    public static void  waitForElementDisabled(MobileElement element){
        //wait = new WebDriverWait(Android.driver,30);
        Android.wait.until(ExpectedConditions.attributeToBe(element, "enabled", "false"));
    }

    public static void init_elements(Class pageClass){
        PageFactory.initElements(new AppiumFieldDecorator(Android.driver), pageClass);

    }
}
