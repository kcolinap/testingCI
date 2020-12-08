package api.apps.android.nw.operaciones.sube.cargarSaldo;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.HashMap;
import java.util.List;

public class SelectAmountPage extends BasePage {

    /**
     * Properties Section
     */

    private static SelectAmountPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String expectedMainTitleText = "Carg\u00E1 tu SUBE";
    public final String expectedSubTitleText = "Cu\u00E1nto quer\u00E9s cargar a:";

    /**
     * Locators Section
     */

    @AndroidFindBy(id = "cardAmountsTitle")
    private static MobileElement cardAmountsTitle;

    @AndroidFindBy(id = "cardSubeData")
    private static MobileElement cardSubeData;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.tunubi.wallet.qa:id/amountsList']/android.view.ViewGroup")
    private List<MobileElement> amountList;

    /**
     * Methods Section
     */

    private SelectAmountPage(){
        super();
        waitForElementVisibility(cardSubeData);
    }

    public synchronized static SelectAmountPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new SelectAmountPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(cardSubeData)));
        }
        return sSoleInstance;
    }

    public String getCardAmountsTitleText() { return cardAmountsTitle.getText(); }

    public String getCardSubeDataText() { return cardSubeData.getText(); }


    public HashMap<Integer, HashMap<String,  MobileElement>> getAmounts(){
        HashMap<Integer, HashMap<String, MobileElement>> amountsAvailable = new HashMap<>();

        int i = 0;
        for (MobileElement amount : amountList) {
            HashMap<String, MobileElement> itemsMap = new HashMap<>();
            itemsMap.put("amount", !amount.findElements(By.id("amountValue")).isEmpty() ? amount.findElement(By.id("amountValue")) : null);
            amountsAvailable.put(i, itemsMap);
            i++;
        }
        return amountsAvailable;
    }

    public boolean existASpecificAmount(String desireAmount){
        HashMap<Integer, HashMap<String,  MobileElement>> amounts = getAmounts();
        try {
            for (HashMap<String, MobileElement> amount : amounts.values()) {
                if (amount.get("amount").getText().trim().contains(desireAmount))
                    return true;
            }
        }
        catch (Exception e){
            logger.info("El monto " + desireAmount + "no pudo hacer encontrado en la pantalla");
            e.printStackTrace();
        }
        return false;
    }

    public void selectOneAmount(String desireAmount){
        HashMap<Integer, HashMap<String,  MobileElement>> amounts = getAmounts();

        try {
            for (HashMap<String, MobileElement> amount : amounts.values()){
                if (amount.get("amount").getText().contains(desireAmount)){
                    amount.get("amount").click();
                    break;
                }
            }
        }
        catch (NoSuchElementException e){
            logger.info("No se pudo encontrar el monto " + desireAmount + "en la lista");
            e.printStackTrace();
        }
    }

}
