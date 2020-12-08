package api.apps.android.nw.prestamos.listaProductos;

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

public class ProductsListPage extends BasePage {

    /**
     * Properties Section
     */

    private static ProductsListPage sSoleInstance;
    private static final Logger logger = LogManager.getLogger();

    /**
     * Locator Section
     */

    @AndroidFindBy(id = "description")
    private static MobileElement lblDescription;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup")
    private static List<MobileElement> productsListContainer;

    private static HashMap<Integer, ProductItem> productList = new HashMap<>();

    /**
     * Methods Section
     */

    private ProductsListPage(){
        super();
        waitForElementVisibility(lblDescription);
        populateProductList();
    }

    public synchronized static ProductsListPage getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new ProductsListPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,10);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(lblDescription)));
        }
        return sSoleInstance;
    }

    public MobileElement getLblDescription() { return lblDescription; }

    private void populateProductList(){
        int i = 0;

        for (MobileElement item : productsListContainer){
            productList.put(i, new ProductItem(item));
            i++;
        }
    }

    /**
     * Valida que la pantalla muestre al menos un item en la lista de productos en promoción y que esto(s) a su vez
     * muestre(n) la imagen del producto, el selector de color, el nombre del producto, el color del producto,
     * el monto del downpayment, las cuotas a pagar, el boton de comprar y el boton de mas información.
     * @return true o false
     */
    public boolean productsExistence(){

        waitForElementVisibility(productList.get(0).btnMoreInfromation);

        boolean
                imgProductImage = false,
                colorSelector = false,
                lblProductName = false,
                lblProductColor = false,
                lblDownPayment = false,
                lblPaymentFees = false,
                btnBuyNow = false,
                btnMoreInfromation = false;

        for (int i = 0; i < productList.size(); i++){
            imgProductImage = productList.get(i).imgProductImage.isDisplayed();
            colorSelector = productList.get(i).colorSelector.isDisplayed();
            lblProductName = productList.get(i).lblProductName.isDisplayed();
            lblProductColor = productList.get(i).lblProductColor.isDisplayed();
            lblDownPayment = productList.get(i).lblDownPayment.isDisplayed();
            lblPaymentFees = productList.get(i).lblPaymentFees.isDisplayed();
            btnBuyNow = productList.get(i).btnBuyNow.isDisplayed();
            btnMoreInfromation = productList.get(i).btnMoreInfromation.isDisplayed();
        }

        if (imgProductImage && colorSelector && lblProductName && lblProductColor && lblDownPayment && lblPaymentFees && btnBuyNow && btnMoreInfromation)
            return true;
        else
            return false;
    }

    /**
     * Pulsa sobre el boton comprar de un producto en especifico.
     * @param productNumber el numero de producto sobre la que queremos hacer tap en comprar. Se cuenta en forma ascendente a partir
     *                       del numero 0, siendo el menor el que esta en la parte superior de la lista.
     */

    public void tapBuyNowButtonOnSpecificProduct(int productNumber) { productList.get(productNumber).btnBuyNow.click(); }

    /**
     * Pulsa sobre el boton mas informacion de un producto en especifico.
     * @param productNumber el numero de producto sobre la que queremos hacer tap en mas info. Se cuenta en forma ascendente a partir
     *                       del numero 0, siendo el menor el que esta en la parte superior de la lista.
     * @throws Exception cuando el parametro productNumber es mayor al numero de elementos de la lista
     */

    public void tapMoreInfoButtonOnSpecificProduct(int productNumber) { productList.get(productNumber).btnMoreInfromation.click(); }

    /**
     * Devuelve el texto (content-type en el caso de image) de la imagen, del nombre del producto, del color del producto.
     * del down paymenrt, de las cuotas, del boton comprar y del boton mas informacion.
     * @param item el numero del producto en la lista que nos interesa.
     * @param attribute icon, company o amount dependiendo de a cual nos interesa sacarle el texto.
     * @return String del texto del elemento.
     */

    public String getTextFromOneProductItem(int item, String attribute){
        switch (attribute.toLowerCase()){
            case "image":
                return productList.get(item).imgProductImage.getAttribute("content-desc");
            case "name":
                return productList.get(item).lblProductName.getText();
            case "color":
                return productList.get(item).lblProductColor.getText();
            case "down_payment":
                return productList.get(item).lblDownPayment.getText();
            case "payment_fees":
                return productList.get(item).lblPaymentFees.getText();
            case "buy_button":
                return productList.get(item).btnBuyNow.getText();
            case "info_button":
                return productList.get(item).btnMoreInfromation.getText();
        }
        return null;
    }

    /**
     * Nos permite seleccionar un color para el producto que deseemos
     * @param item numero del producto en la lista
     * @param radioNumber numero del radio button que queremos seleccionar.
     * @throws Exception
     */
    public void selectOneColorProduct(int item, int radioNumber) throws Exception{
        List<MobileElement> colorRadioButtonsList = productList.get(item).colorSelector.findElements(By.className("android.widget.RadioButton"));
        colorRadioButtonsList.get(radioNumber).click();
    }


    public boolean isRadioButtonSelected(int item, int radioNumber){
        List<MobileElement> colorRadioButtonsList = productList.get(item).colorSelector.findElements(By.className("android.widget.RadioButton"));
        return Boolean.parseBoolean(colorRadioButtonsList.get(radioNumber).getAttribute("checked"));
    }

}

class ProductItem {

    private MobileElement _item = null;

    public MobileElement imgProductImage;
    public MobileElement colorSelector;
    public MobileElement lblProductName;
    public MobileElement lblProductColor;
    public MobileElement lblDownPayment;
    public MobileElement lblPaymentFees;
    public MobileElement btnBuyNow;
    public MobileElement btnMoreInfromation;

    ProductItem(MobileElement item){
        _item = item;
        populateProductItem();
    }

    private void populateProductItem(){
        imgProductImage = _item.findElement(By.id("image")) ;
        colorSelector = _item.findElement(By.id("selectorColors"));
        lblProductName = _item.findElement(By.id("name"));
        lblProductColor = _item.findElement(By.id("color"));
        lblDownPayment = _item.findElement(By.id("downPayment"));
        lblPaymentFees = _item.findElement(By.id("installmentDetails"));
        btnBuyNow = _item.findElement(By.id("orderButton"));
        btnMoreInfromation = _item.findElement(By.id("infoButton"));
    }
}