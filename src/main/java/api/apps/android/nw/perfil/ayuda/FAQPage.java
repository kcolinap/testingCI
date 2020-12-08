package api.apps.android.nw.perfil.ayuda;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FAQPage extends BasePage {

    private static FAQPage sSoleInstance;

    public static String TITLE_TEXT = "Preguntas frecuentes";

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Preguntas frecuentes\")")
    private MobileElement title;

    @AndroidFindBy(id = "fragment_help_menu_search")
    private static MobileElement searchButton;

    @AndroidFindBy(xpath = "//android.widget.ImageButton")
    private MobileElement backButton;

    @AndroidFindBy(id = "category_title")
    private static MobileElement category_title;

    @AndroidFindBy(id = "section_title")
    private MobileElement section_title;

    @AndroidFindBy(id = "article_title")
    private MobileElement article_title;

    public static MobileElement getCategory_title() {
        return category_title;
    }

    public MobileElement getArticle_title() {
        return article_title;
    }

    public MobileElement getSection_title() {
        return section_title;
    }

    public MobileElement getTitle() {
        return title;
    }

    public static MobileElement getSearchButton() {
        return searchButton;
    }

    public MobileElement getBackButton() {
        return backButton;
    }

    public FAQPage() {
        super();
        waitForElementVisibility(category_title);
    }

    public synchronized static FAQPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new FAQPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,20);
            Wrapper.init_elements(FAQPage.class);
            wait.until(ExpectedConditions.visibilityOf(category_title));
        }
        return sSoleInstance;
    }

    public void clickBackButton() {
        actions.singleTap(backButton);
    }
}
