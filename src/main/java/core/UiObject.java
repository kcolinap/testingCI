package core;

import api.apps.android.Android;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class UiObject {

    private String locator;


    UiObject(String locator) {

        this.locator = locator;
    }

    private boolean isXpath() {

        return !locator.contains("UiSelector");
    }

    public boolean exists() {
        try {
            MobileElement element;
            if (isXpath())
                element = (MobileElement) Android.driver.findElement(By.xpath(locator));
            else
                element = (MobileElement) Android.driver.findElementByAndroidUIAutomator(locator);

            return element.isDisplayed();

        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isChecked() {
        MobileElement element;
        if (isXpath())
            element = (MobileElement) Android.driver.findElement(By.xpath(locator));
        else
            element = (MobileElement) Android.driver.findElementByAndroidUIAutomator(locator);

        return element.getAttribute("checked").equals("true");
    }


    public boolean isEnabled() {
        MobileElement element;

        if (isXpath())
            element = (MobileElement) Android.driver.findElement(By.xpath(locator));
        else
            element = (MobileElement) Android.driver.findElementByAndroidUIAutomator(locator);


        return element.getAttribute("enabled").equals("true");
    }

    public String getText() {
        MobileElement element;
        if (isXpath())
            element = (MobileElement) Android.driver.findElement(By.xpath(locator));
        else
            element = (MobileElement) Android.driver.findElementByAndroidUIAutomator(locator);

        return element.getAttribute("name");
    }

    public String getResourceId() {
        MobileElement element;
        if (isXpath())
            element = (MobileElement) Android.driver.findElement(By.xpath(locator));
        else
            element = (MobileElement) Android.driver.findElementByAndroidUIAutomator(locator);

        return element.getAttribute("resourceId");
    }

    public String getContentDesc() {
        MobileElement element;
        if (isXpath())
            element = (MobileElement) Android.driver.findElement(By.xpath(locator));
        else
            element = (MobileElement) Android.driver.findElementByAndroidUIAutomator(locator);

        return element.getAttribute("contentDescription");
    }

    public UiObject clearText() {
        if (isXpath())
            Android.driver.findElement(By.xpath(locator)).clear();
        else
            Android.driver.findElementByAndroidUIAutomator(locator).clear();

        return this;
    }

    public UiObject clearTextFromEndChar() {
        int inputLength = 0;
        if (isXpath()) {
            inputLength = Android.driver.findElementByXPath(locator).getText().length();
            Android.driver.findElementByXPath(locator).click();
        } else {
            inputLength = Android.driver.findElementByAndroidUIAutomator(locator).getText().length();
            Android.driver.findElementByAndroidUIAutomator(locator).click();
        }

        for (int i = 0; i < inputLength; i++) {
            Android.driver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
        }

        for (int i = 0; i < inputLength; i++) {
            Android.driver.pressKey(new KeyEvent(AndroidKey.DEL));
        }

        return this;
    }

    public UiObject typeText(String value) {
        MobileElement element;
        if (isXpath()) {
            element = (MobileElement) Android.driver.findElement(By.xpath(locator));
            element.sendKeys(value);
        } else {
            element = (MobileElement) Android.driver.findElementByAndroidUIAutomator(locator);
            element.sendKeys(value);
        }

        if (Android.driver.isKeyboardShown())
            Android.driver.hideKeyboard();

        return this;
    }

    public UiObject sendText(String value) {
        MobileElement element;

        if (isXpath()) {
            element = (MobileElement) Android.driver.findElement(By.xpath(locator));
            element.setValue(value);
        } else {
            element = (MobileElement) Android.driver.findElementByAndroidUIAutomator(locator);
            element.setValue(value);
        }

        if (Android.driver.isKeyboardShown())
            Android.driver.hideKeyboard();

        return this;
    }

    public UiObject tap() {
        if (isXpath())
            Android.driver.findElement(By.xpath(locator)).click();
        else
            Android.driver.findElementByAndroidUIAutomator(locator).click();

        return this;
    }

    public UiObject waitToAppear(int seconds) {
        try{
            WebDriverWait wait = new WebDriverWait(Android.driver, seconds, 100);
            if (isXpath())
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            else {
                wait.ignoring(org.openqa.selenium.NoSuchElementException.class);
                wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AndroidUIAutomator(locator)));
            }
            return this;
        }catch(Exception e){
            throw new Error("El elemento " + locator + " no pudo ser encontrado en " + seconds + " segundos");
        }
    }

/*
    public UiObject waitToAppear(int seconds) {
        Timer timer = new Timer();
        timer.start();
        while (!timer.expired(seconds)) {
            if (exists())
                break;
        }
        if (timer.expired(seconds) && !exists())
            throw new Error("El elemento " + locator + " no pudo ser encontrado en " + seconds + " segundos");

        return this;
    }
*/
    public UiObject waitToDisappear(int seconds) {
        Timer timer = new Timer();
        timer.start();
        while (!timer.expired(seconds)) {
            if (!exists())
                break;
        }

        if (timer.expired(seconds) && exists())
            throw new AssertionError("Element " + locator + " failed to disappear within " + seconds + " seconds");

        return this;
    }

    public UiObject waitToBeEnabled(int seconds) {
        Timer timer = new Timer();
        timer.start();
        while (!timer.expired(seconds)) {
            if (exists() && isEnabled())
                break;
        }
        if (timer.expired(seconds) && !isEnabled())
            throw new Error("El elemento " + locator + " no se habilito en " + seconds + " segundos");
        return this;
    }

   /* public boolean isFocusable(){
        WebElement element;
        if(isXpath()) element = Android.driver.findElementByXPath(locator);
        else element = Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("focusable").equals("true");
    }

    public boolean isFocused(){
        WebElement element;
        if(isXpath()) element = Android.driver.findElementByXPath(locator);
        else element = Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("focused").equals("true");
    }

    public boolean isScrollable(){
        WebElement element;
        if(isXpath()) element = Android.driver.findElementByXPath(locator);
        else element = Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("scrollable").equals("true");
    }

    public boolean isLongClickable(){
        WebElement element;
        if(isXpath()) element = Android.driver.findElementByXPath(locator);
        else element = Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("longClickable").equals("true");
    }

    public boolean isSelected(){
        WebElement element;
        if(isXpath()) element = Android.driver.findElementByXPath(locator);
        else element = Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("selected").equals("true");
    }

    public Point getLocation(){
        WebElement element;
        if(isXpath()) element = Android.driver.findElementByXPath(locator);
        else element = Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getLocation();
    }

    public int getSize(){
        int element;
        if(isXpath())
            element = Android.driver.findElements(By.xpath(locator)).size();
        else
            element = Android.driver.findElementsByAndroidUIAutomator(locator).size();
        return element;
    }

    public String getClassName(){
        WebElement element;
        if(isXpath()) element = Android.driver.findElementByXPath(locator);
        else element = Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("className");
    }

     public boolean isCheckable(){
        WebElement element;
        if(isXpath()) element = Android.driver.findElementByXPath(locator);
        else element = Android.driver.findElementByAndroidUIAutomator(locator);
        return element.getAttribute("checkable").equals("true");
    }

    public boolean isClickable(){
        WebElement element;
        if(isXpath())
            element = Android.driver.findElementByXPath(locator);
        else
            element = Android.driver.findElementByAndroidUIAutomator(locator);

        return element.getAttribute("clickable").equals("true");
    }
    */

//    public UiObject scrollTo(){
//        if(!locator.contains("text")) throw new RuntimeException("Scroll to method can only be used with text attributes and current locator: "+locator+" does not contain any text attributes!");
//        if(isXpath()) Android.driver.scrollTo(locator.substring(locator.indexOf("@text=\""), locator.indexOf("\"]")).replace("@text=\"", ""));
//        else{
//            String text;
//            if(locator.contains("textContains")) text = locator.substring(locator.indexOf(".textContains(\""), locator.indexOf("\")")).replace(".textContains(\"", "");
//            else text = locator.substring(locator.indexOf(".text(\""), locator.indexOf("\")")).replace(".text(\"", "");
//            Android.driver.scrollTo(text);
//        }
//        return this;
//    }
}
