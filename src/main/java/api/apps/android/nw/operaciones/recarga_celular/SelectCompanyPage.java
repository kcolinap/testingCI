package api.apps.android.nw.operaciones.recarga_celular;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SelectCompanyPage extends BasePage {

    private static SelectCompanyPage sSoleInstance;

    public static final String
            RECHARGE_CELULAR_TITLE_TEXT = "Recargá tu celular",
            SELECT_COMPANY_SUBTITLE_TEXT = "Elegí la compañía";

    public static final String[] companyNames = {"Claro", "Movistar", "Nextel", "Personal", "Tuenti"};

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView//android.widget.ImageView")
    private List<MobileElement> companyImage;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView//android.widget.TextView")
    private List<MobileElement> companyName;

    @AndroidFindBy(id = "subtitleRechargeMobile")
    private static MobileElement subtitleRechargeMobile;

    public List<MobileElement> getCompanyImage() {
        return companyImage;
    }

    public List<MobileElement> getCompanyName() {
        return companyName;
    }

    public MobileElement getSubtitleRechargeMobile() {
        return subtitleRechargeMobile;
    }

    public SelectCompanyPage(){
        super();
        waitForElementVisibility(subtitleRechargeMobile);
    }

    public synchronized static SelectCompanyPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new SelectCompanyPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,30);
            Wrapper.init_elements(SelectCompanyPage.class);
            wait.until(ExpectedConditions.visibilityOf(subtitleRechargeMobile));
        }
        return sSoleInstance;
    }

    public void click_on_company(String nameCompany){
        boolean elementExist = false;
        do{
            for(MobileElement element : companyName){
                if(element.getText().toUpperCase().contains(nameCompany.toUpperCase())){
                    element.click();
                    elementExist = true;
                    break;
                }
            }
            if(!elementExist)
                util.scrollTo();

        }while (!elementExist);

    }

    public String get_company_name(String nameCompany){
        boolean elementExist = false;
        String name = "";
        do{
            for(MobileElement element : companyName){
                if(element.getText().toUpperCase().contains(nameCompany.toUpperCase())){
                    elementExist = true;
                    name = element.getText();
                    break;
                }
            }
            if(!elementExist)
                util.scrollTo();

        }while (!elementExist);

        return name;

    }
}
