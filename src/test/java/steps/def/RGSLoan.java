package steps.def;

import api.apps.android.nw.prestamos.detallePlanPago.DownPaymentPage;
import api.apps.android.nw.prestamos.direccionEnvio.DeliveryAddressPage;
import api.apps.android.nw.prestamos.listaProductos.ProductsListPage;
import core.Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import api.apps.android.Android;
import api.apps.android.nw.NubiWallet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class RGSLoan {

    /**
     * Properties Section
     */

    public Util util = new Util();
   // private static NubiWallet nubiWallet = Android.app.nubiWallet;
    private static final Logger logger = LogManager.getLogger();
    private static String productName;
    private static String productColor;
    private static String downPayment;
    private static String paymentFees;
    private static int radioButtonColor;
    private static int productSelected;

    /**
     * Getters and Setters Section
     */

    public static String getProductName() { return productName; }
    public static String getProductColor() { return productColor; }
    public static String getDownPayment() { return downPayment; }
    public static String getPaymentFees() { return paymentFees; }
    public static int getRadioButtonColor() { return radioButtonColor; }
    public static int getProductSelected() { return productSelected; }

    /**
     * Steps Methods Section
     */

    @When("Usuario presiona el boton comprar en el producto {int}")
    public void tapBuyNowButton(int productNumber) throws Exception {
        productSelected = productNumber;
        try {
            //Antes de hacer click en comprar, guardamos los detalles sobre el producto a comprar para comparar en posteriores pantallas
            setProductData(productNumber);
            ProductsListPage.getInstance().tapBuyNowButtonOnSpecificProduct(productSelected);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Then("Se valida los elementos de la pantalla lista de productos en promocion")
    public void assertProductListElements() throws Exception{
        try {
            ProductsListPage productsList = ProductsListPage.getInstance();

            Assert.assertTrue(ProductsListPage.getLblTitle().isDisplayed());
            Assert.assertTrue(productsList.elementExists(productsList.getLblDescription()));
            Assert.assertTrue(productsList.productsExistence());
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @When("Cambiar el color del articulo {int}")
    public void changeProductColor(int productNumber) throws Exception {
        ProductsListPage productsList = ProductsListPage.getInstance();
        productSelected = productNumber;
        productColor = productsList.getTextFromOneProductItem(productSelected, "color");
        try {
            radioButtonColor = 1;
            productsList.selectOneColorProduct(productSelected, radioButtonColor);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("El producto no existe o el producto no tiene mas de un color para elegir");
        }
    }

    @Then("Se valida que haya quedado seleccionado el radiobutton del color y que el nombre del color haya cambiado")
    public void assertColorSelection() throws Exception {
        try {
            ProductsListPage productsList = ProductsListPage.getInstance();
            Assert.assertTrue(productsList.isRadioButtonSelected(productSelected,1));
            Assert.assertNotEquals(productColor, productsList.getTextFromOneProductItem(productSelected, "color"));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Then("Se validan los elementos de la pantalla detalle de plan de pago")
    public void assertDownPaymentElements() throws Exception {

        try {
            DownPaymentPage downPaymentPage = DownPaymentPage.getInstance();
            String downPaymentAmount = downPaymentPage.get_element_text(downPaymentPage.getLblMoneySymbol()) + downPaymentPage.get_element_text(downPaymentPage.getLblMoneyAmount());

            Assert.assertEquals(downPaymentPage.MAIN_TTITLE_TEXT, DownPaymentPage.getLblTitle().getText());
            Assert.assertEquals(downPaymentPage.PRICE_DESCRIPTION_TEXT, DownPaymentPage.getInstance().get_element_text(downPaymentPage.getLblPriceDescription()));
            Assert.assertTrue(downPaymentPage.elementExists(downPaymentPage.getIcnEmoji()));
            Assert.assertEquals(getDownPayment(), downPaymentAmount);
            Assert.assertTrue(downPaymentPage.elementExists(downPaymentPage.getLblSymbol()));
            Assert.assertTrue(downPaymentPage.get_element_text(downPaymentPage.getLblFeesDescription()).contains(downPaymentPage.FEES_DESCRIPTION_PREFIX_TEXT));
            Assert.assertTrue(downPaymentPage.get_element_text(downPaymentPage.getLblFeesDescription()).contains(downPaymentPage.FEES_DESCRIPTION_SUFIX_TEXT));
            Assert.assertTrue(downPaymentPage.get_element_text(downPaymentPage.getLblFeePrice()).contains(getPaymentFees().substring(getPaymentFees().indexOf("$"))));
            Assert.assertTrue(downPaymentPage.get_element_text(downPaymentPage.getLblDisclaimer()).contains(downPaymentPage.TNA_TEXT));
            Assert.assertTrue(downPaymentPage.get_element_text(downPaymentPage.getLblDisclaimer()).contains(downPaymentPage.CFT_TEXT));
            Assert.assertTrue(downPaymentPage.get_element_text(downPaymentPage.getLblDisclaimer()).contains(downPaymentPage.TOTAL_AMOUNT_TEXT));
            Assert.assertEquals(downPaymentPage.CONTINUE_BUTTON_TEXT, downPaymentPage.get_element_text(downPaymentPage.getBtnContinueButton()));
            Assert.assertTrue(downPaymentPage.elementIsEnabled(downPaymentPage.getBtnContinueButton()));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getCause());
        }
    }


    @And("Se corrobora la fecha de la primera cuota")
    public void assertFirstLoanDueDate() throws Exception {
        try {
            DownPaymentPage downPaymentPage = DownPaymentPage.getInstance();
            String expectedDueDate = calculateFirstInstallmentDate();
            String displayedDueDate = downPaymentPage.get_element_text(downPaymentPage.getLblFeesDescription());
            displayedDueDate = displayedDueDate.substring(displayedDueDate.indexOf("08"), displayedDueDate.indexOf(" pagás"));

            Assert.assertEquals(expectedDueDate, displayedDueDate);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }


    }

    @And("Presiona el boton continuar en pantalla downpayment")
    public void tapContinueButtonInDownPaymentPage() throws Exception {
        try {
            DownPaymentPage.getInstance().clickContinueButton();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Then("Se validan los elementos de la pantalla direccion de envio")
    public void assertAddressElementsPage() throws Exception {
        DeliveryAddressPage deliveryAddress = DeliveryAddressPage.getInstance();
        String expectedSubTitle = deliveryAddress.PREFIX_SUBTITLE_TEXT_DEF + getProductName() + deliveryAddress.SUFIX_SUBTITLE_TEXT;

        try {
            Assert.assertEquals(deliveryAddress.MAIN_TITLE_TEXT, DeliveryAddressPage.getLblTitle().getText());
            Assert.assertEquals(expectedSubTitle, deliveryAddress.getLblSubtitle().getText().replace("\n", " "));
            Assert.assertEquals(deliveryAddress.DISCLAIMER_TEXT, deliveryAddress.get_element_text(deliveryAddress.getLblDisclaimer()).replace("\n", " "));
            Assert.assertTrue(deliveryAddress.getAllAddressText().size() != 0);
            Assert.assertEquals(deliveryAddress.ADD_NEW_DIRECTION_BUTTON_TEXT, deliveryAddress.get_element_text(deliveryAddress.getBtnCreateNewAddress()));
            Assert.assertEquals(deliveryAddress.CONTINUE_BUTTON_TEXT, deliveryAddress.get_element_text(deliveryAddress.getBtnContinue()));
            Assert.assertTrue(deliveryAddress.elementIsEnabled(deliveryAddress.getBtnCreateNewAddress()));
            Assert.assertTrue(deliveryAddress.elementIsEnabled(deliveryAddress.getBtnContinue()));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @And("Presiona sobre el boton crear otra dirección")
    public void clickAddNewAdressButton() throws Exception {
        try {
            DeliveryAddressPage.getInstance().clickAddNewAddressButton();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @And("Presiona sobre el boton continuar en la pantalla direccion de envio")
    public void clickContinueButton() throws Exception {
        try {
            DeliveryAddressPage.getInstance().clickContinueButton();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * Guarda los datos mas relevantes del producto a comprar.
     * @param item numero del articulo a comprar
     */
    private void setProductData(int item){
        ProductsListPage productsList = ProductsListPage.getInstance();
        productName = productsList.getTextFromOneProductItem(item, "name");
        productColor = productsList.getTextFromOneProductItem(item, "color");
        downPayment = productsList.getTextFromOneProductItem(item, "down_payment");
        paymentFees = productsList.getTextFromOneProductItem(item, "payment_fees");
    }

    /**
     * Calcula la fecha de pago de la primera cuota basado en los siguientes criterios:
     * caso 1: Si la compra se hace entre el día 1 y 19 inclusive del mes, entonces la 1era cuota se pagará el dia 08 del siguiente mes
     * caso 2: Si la compra se hace entre el día 20 y ultimo inclusive del mes, entonces la 1era cuota se pagará el dia 08 de dos meses adelante.
     * @return la fecha de pago de la priemra cuota en formato string
     */
    private String calculateFirstInstallmentDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM", new Locale("es", "AR"));

        if (LocalDateTime.now().getDayOfMonth() <= 19)
            return "08 de " + dateFormat.format(LocalDateTime.now().plusMonths(1));
        else if (LocalDateTime.now().getDayOfMonth() > 19)
            return "08 de " + dateFormat.format(LocalDateTime.now().plusMonths(2));

        return null;
    }
}
