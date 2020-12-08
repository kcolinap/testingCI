package api.apps.android.nw.operaciones.pagoServicios.listadoEmpresas;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import core.commons.apicall.Rapipago_API;
import core.commons.apicall.dtos.rapipago.CodeAndNameCompany;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompaniesListPage extends BasePage {

    /**
     * Properties Section
     */

    private static CompaniesListPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    private CodeAndNameCompany[] availableCompaniesListFromBE;
    public final String MAIN_TITLE_TEXT = "Empresas habilitadas";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "cardNumberTitle")
    private static MobileElement lblAvailableCompaniesTitle;

    private List<String> lblAvailableCompaniesNames;

    /**
     * Methods Section
     */

    private CompaniesListPage() throws Exception {
        super();
        waitForElementVisibility(lblAvailableCompaniesTitle);
        setAvailableCompaniesListFromBE();
    }

    public synchronized static CompaniesListPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new CompaniesListPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(lblAvailableCompaniesTitle)));
        }
        return sSoleInstance;
    }

    public MobileElement getLblAvailableCompaniesTitle() { return lblAvailableCompaniesTitle; }

    public String getAvailableCompaniesTitle(){ return lblAvailableCompaniesTitle.getText(); }

    public CodeAndNameCompany[] getAvailableCompaniesListFromBE() { return availableCompaniesListFromBE; }

    private void setAvailableCompaniesListFromBE() throws Exception {
        Rapipago_API rapipago_api = new Rapipago_API();
        this.availableCompaniesListFromBE = rapipago_api.getAvailableCompanies();
    }

    /**
     * Scroll among the mobile elements looking for an expecific company
     * @param companyToFind the name of the wanted company
     * @return true if the company was displayed, false in the opposite scenario.
     */
    public boolean isTheCompanyPresentInTheScren(String companyToFind){

        String locator = "//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout/android.widget.TextView[@text='" + companyToFind +"']";
        int scrollCounter = 0;
        boolean finded = false;

        while(scrollCounter < 500){
            try {
                finded = super.getDriver().findElementByXPath(locator).isDisplayed();
                break;
            }
            catch (Exception e){
                util.scrollTo();
                scrollCounter++;
            }
        }

        return finded;
    }

    private void setCompaniesNameFromUI(int rowsToCompare){
        String locator = "//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout/android.widget.TextView";
        lblAvailableCompaniesNames = new ArrayList<>();
        while (lblAvailableCompaniesNames.size() < rowsToCompare){
            List<MobileElement> tempList = super.getDriver().findElements(By.xpath(locator));
            for (MobileElement cn : tempList){
                lblAvailableCompaniesNames.add(cn.getText());
            }
            lblAvailableCompaniesNames = lblAvailableCompaniesNames.stream().distinct().collect(Collectors.toList());
            util.scrollTo();
        }
    }

    public boolean companiesOrderedAndDisplayed(int rowsToCompare){
        setCompaniesNameFromUI(rowsToCompare);
        for (int i = 0; i < rowsToCompare; i++){
            if (!lblAvailableCompaniesNames.get(i).contentEquals(getAvailableCompaniesListFromBE()[i].getCompany_description()))
                return false;
        }
        return true;
    }

}
