package api.apps.android.nw.movimientos;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LastMovementsListPage extends BasePage {

    private static Wrapper wrapper = Apps.wrapper;

    Multimap<Integer, Multimap<String, String>> staticObjects;

    private static LastMovementsListPage sSoleInstance;

    public static String TITLE_TEXT = "Movimientos";

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup")
    private List<MobileElement> transactions;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]")
    private MobileElement transaction;

    @AndroidFindBy(id = "transactionIcon")
    private List<MobileElement> transactionIcon;

    @AndroidFindBy(id = "descriptionText")
    private List<MobileElement> transactionDescription;

    @AndroidFindBy(id = "moneyText")
    private List<MobileElement> transactionAmount;

    @AndroidFindBy(id = "timestampText")
    private List<MobileElement> transactionDate;

    @AndroidFindBy(id = "statusText")
    private List<MobileElement> transactionStatus;


    public int getTransactions() {
        return transactions.size();
    }

    public MobileElement getTransaction() {
        return transaction;
    }

    /** GETS **/


    public LastMovementsListPage() {
        super();
        waitForElementVisibility(lblTitle);
    }

    public synchronized static LastMovementsListPage getInstance() throws Exception {
        if (sSoleInstance == null){
            sSoleInstance = new LastMovementsListPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(lblTitle)));
        }
        return sSoleInstance;
    }

    /**
     * Itera sobre el mapa de elementos que representa la lista de ultimos movimientos
     * en buscar de una coincidencia con los siguientes parametros:
     *
     * @param type:        recibe el tipo de transacción (SUBE, Envio, Solicitud, etc)
     * @param description: recibe la descripcion de la transaccion
     * @param amount:      recibe el monto de la transaccion efectuda
     * @param date:        la fecha de la transaccion efectuada
     * @return true si encontro coincidencia, false en caso contrario.
     */

    public boolean getMovementInformation(String status, String type, String description, String amount, String date) throws InterruptedException {

        boolean existTransaction = false;

        if(staticObjects==null)
            staticObjects = getTransactionStaticObjects(status);

        //(Multimap) value).get("icon").toString().contains(type) &&
        //                    (

        for (Object value : staticObjects.values()) {

            if (status.toUpperCase().contentEquals("RECHAZADO") || status.toUpperCase().contentEquals("CANCELADO")) {
                if (((Multimap) value).get("description").toString().contains(description) &&
                        ((Multimap) value).get("status").toString().toUpperCase().contains(status.toUpperCase()) &&
                        ((Multimap) value).get("amount").toString().contains(amount.replace(".", ",")) &&
                        ((Multimap) value).get("date").toString().contains(date)) {
                    existTransaction = true;
                    break;
                }
            } else {
                if (((Multimap) value).get("description").toString().contains(description) &&
                        ((Multimap) value).get("amount").toString().contains(amount.replace(".", ",")) &&
                        ((Multimap) value).get("date").toString().contains(date)) {
                    existTransaction = true;
                    break;
                }
            }
        }

        return existTransaction;
    }

    /**
     * Metodo para obtener todos los elementos que existen en la pantalla
     * ultimos movimientos.
     *
     * @return un mapa con todos los elementos
     */

    public Multimap<Integer, Multimap<String, String>> getTransactionStaticObjects(String status) throws InterruptedException {

        Multimap<Integer, Multimap<String, String>> lastMovements = HashMultimap.create();
        List<MobileElement> movements = transactions;// Android.driver.findElements(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup"));

        for (int i = 0; i < movements.size(); i++) {
            MobileElement movementsDetails = movements.get(i);
            try {
                wait.until(ExpectedConditions.visibilityOf((WebElement)transactionIcon.get(i)));

               // String icon = movementsDetails.findElement(By.id(SelectorList.TRANSACTION_ICON.getSelector())).getAttribute("contentDescription");
               String icon = Apps.wrapper.get_element_attribute(transactionIcon.get(i), "contentDescription");
                String description = Apps.wrapper.getElementText(transactionDescription.get(i));
                //String amount = movementsDetails.findElement(By.id(SelectorList.LBL_AMOUNT.getSelector())).getText();
                String amount = Apps.wrapper.getElementText(transactionAmount.get(i));
               // String date = movementsDetails.findElement(By.id(SelectorList.LBL_TRANSACTION_DATE.getSelector())).getText();
                String date = Apps.wrapper.getElementText(transactionDate.get(i));

                String transStatus = null;
                if (status.toUpperCase().contentEquals("RECHAZADO") || status.toUpperCase().contentEquals("CANCELADO")) {
                    transStatus = Apps.wrapper.getElementText(transactionStatus.get(i));
                }

                if (icon != null && description != null && amount != null && date != null) {
                    Multimap<String, String> itemsMap = HashMultimap.create();
                    itemsMap.put("icon", icon);
                    itemsMap.put("description", description);
                    itemsMap.put("amount", amount);
                    itemsMap.put("date", date);
                    if (transStatus != null) {
                        itemsMap.put("status", transStatus);
                    }

                    lastMovements.put(i, itemsMap);
                }
            } catch (Exception e) {
                // logger.warn("Element not found");
            }
        }
        return lastMovements;
    }
}
