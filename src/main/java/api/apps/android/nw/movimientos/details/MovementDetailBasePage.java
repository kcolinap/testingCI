package api.apps.android.nw.movimientos.details;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class MovementDetailBasePage extends BasePage {

    public static String TITLE_TEXT = "Detalle";

    @AndroidFindBy(id = "transactionDetailTitle")
    protected static MobileElement transactionDetailTitle;

    @AndroidFindBy(id = "transactionIcon")
    protected static MobileElement transactionIcon;

    @AndroidFindBy(id = "transactionDescription")
    protected MobileElement transactionDescription;

    @AndroidFindBy(id = "transactionAmount")
    protected MobileElement transactionAmount;

    @AndroidFindBy(id = "dateTimeLabel")
    protected MobileElement dateTimeLabel;

    @AndroidFindBy(id = "editCategory")
    protected MobileElement editCategory;

    @AndroidFindBy(id = "categoryName")
    protected MobileElement categoryName;

    @AndroidFindBy(id = "categoryIcon")
    protected MobileElement categoryIcon;

    public MobileElement getTransactionDetailTitle() {
        return transactionDetailTitle;
    }

    public MobileElement getTransactionIcon() {
        return transactionIcon;
    }

    public MobileElement getTransactionDescription() {
        return transactionDescription;
    }

    public MobileElement getTransactionAmount() {
        return transactionAmount;
    }

    public MobileElement getDateTimeLabel() {
        return dateTimeLabel;
    }

    public MobileElement getEditCategory() {
        return editCategory;
    }

    public MobileElement getCategoryName() {
        return categoryName;
    }

    public MobileElement getCategoryIcon() {
        return categoryIcon;
    }

    /** GETS **/

    public MovementDetailBasePage() {
        super();
    }

}
