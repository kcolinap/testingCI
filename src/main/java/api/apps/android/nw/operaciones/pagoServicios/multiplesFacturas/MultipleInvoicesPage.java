package api.apps.android.nw.operaciones.pagoServicios.multiplesFacturas;

import api.apps.android.Android;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.HashMap;
import java.util.List;

public class MultipleInvoicesPage extends BasePage {

    /**
     * Properties Section
     */

    private static MultipleInvoicesPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();
    public final String MAIN_TITLE_TEXT = "Pago de servicios";
    public final String INFORMATION_MESSAGE_TEXT = "Eleg\u00ED el servicio que quer\u00E9s pagar";

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "selectInvoiceTitle")
    private static MobileElement lblTitle;

    @AndroidFindBy(id = "featureIcon")
    private static MobileElement imgInvoice;

    @AndroidFindBy(id = "informationMessage")
    private static MobileElement lblInformationMessage;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup")
    private static List<MobileElement> invoiceListContainer;

    private static HashMap<Integer, InvoiceItem> invoiceList = new HashMap<>();

    /**
     * Methods Section
     */

    private MultipleInvoicesPage(){
        super();
        waitForElementVisibility(lblInformationMessage);
        populateInvoiceList();
    }

    public synchronized static MultipleInvoicesPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new MultipleInvoicesPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(lblInformationMessage)));
        }
        return sSoleInstance;
    }

    public MobileElement getLblSelectInvoiceTitle() { return lblTitle; }
    public MobileElement getImgInvoice() { return imgInvoice; }
    public MobileElement getLblInformationMessage() { return lblInformationMessage; }

    public void populateInvoiceList(){

        invoiceList = new HashMap<>();
        int i = 0;

        for (MobileElement item : invoiceListContainer){
            invoiceList.put(i, new InvoiceItem(item));
            i++;
        }
    }

    /**
     * Valida que la pantalla muestre al menos un item en la lista de factruas a pagar y que esta(s) a su vez
     * muestre(n) el icono, el nombre de la compañia y el monto a pagar
     * @return true o false
     */
    public boolean billsExistence(){

        boolean icon = false, companyName = false, invoiceAmount = false;

        for (int i = 0; i < invoiceList.size(); i++){
            icon = invoiceList.get(i).icon.isDisplayed();
            companyName = invoiceList.get(i).companyName.isDisplayed();
            invoiceAmount = invoiceList.get(i).invoiceAmount.isDisplayed();
        }

        if (icon && companyName && invoiceAmount)
            return true;
        else
            return false;
    }

    /**
     * Pulsa sobre una factura en especifico.
     * @param invoiceNumber el numero de factura sobre la que queremos hacer tap. Se cuenta en forma ascendente a partir
     *                       del numero 0, siendo la menor la que esta en la parte superior de la lista.
     * @throws Exception cuando el parametro invoiceNumber es mayor al numero de elementos de la lista
     */

    public void tapOnSpecificBill(int invoiceNumber) throws Exception{

        try {
            invoiceList.get(invoiceNumber).companyName.click();
        }
        catch (Exception e){
            logger.error("El numero de factura no existe en la lista");
            throw new Exception();
        }

    }

    /**
     * Devuelve el texto (content-type en el caso de icon) del icono, el nombre de la compañia y el monto de una factura de la lista
     * @param item el numero de la factura en la lista que nos interesa.
     * @param attribute icon, company o amount dependiendo de a cual nos interesa sacarle el texto.
     * @return String del texto del elemento.
     */

    public String getTextFromOneInvoiceItem(int item, String attribute){

        switch (attribute.toLowerCase()){
            case "icon":
                return invoiceList.get(item).icon.getAttribute("content-desc");
            case "company":
                return invoiceList.get(item).companyName.getText();
            case "amount":
                return invoiceList.get(item).invoiceAmount.getText();
        }

        return null;
    }

}

class InvoiceItem {

    private MobileElement _item = null;

    public MobileElement icon;
    public MobileElement companyName;
    public MobileElement invoiceAmount;

    InvoiceItem(MobileElement item){
        _item = item;
        populateInvoiceItem();
    }

    private void populateInvoiceItem(){
        icon = _item.findElement(By.id("ic_invoice")) ;
        companyName = _item.findElement(By.id("companyName"));
        invoiceAmount = _item.findElement(By.id("invoiceAmount"));
    }
}