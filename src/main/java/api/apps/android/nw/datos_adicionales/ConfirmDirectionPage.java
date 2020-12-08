package api.apps.android.nw.datos_adicionales;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmDirectionPage extends BasePage {

    private static ConfirmDirectionPage sSoleInstance;

    public static final String SUBTITLE_TEXT = "¿Está todo bien?";
    public static final String DISCLAIMER_TEXT = "Recordá que usaremos este domicilio para el envío de tu tarjeta.";


    @AndroidFindBy(id = "streetName")
    private MobileElement streetName;

    @AndroidFindBy(id = "streetNumber")
    private MobileElement streetNumber;

    @AndroidFindBy(id = "floorNumber")
    private MobileElement floorNumber;

    @AndroidFindBy(id = "streetsIntersection")
    private MobileElement streetsIntersection;

    @AndroidFindBy(id = "zipCode")
    private MobileElement zipCode;

    @AndroidFindBy(id = "city")
    private MobileElement city;

    @AndroidFindBy(id = "province")
    private MobileElement province;

    @AndroidFindBy(id = "confirmButton")
    private static MobileElement confirmButton;

    @AndroidFindBy(id = "disclaimerText")
    private MobileElement disclaimerText;

    public MobileElement getStreetName() {
        return streetName;
    }

    public MobileElement getStreetNumber() {
        return streetNumber;
    }

    public MobileElement getFloorNumber() {
        return floorNumber;
    }

    public MobileElement getStreetsIntersection() {
        return streetsIntersection;
    }

    public MobileElement getDisclaimerText() {
        return disclaimerText;
    }

    public MobileElement getZipCode() {
        return zipCode;
    }

    public MobileElement getCity() {
        return city;
    }

    public MobileElement getProvince() {
        return province;
    }

    public MobileElement getConfirmButton() {
        return confirmButton;
    }

    public ConfirmDirectionPage(){
        super();
        waitForElementVisibility(confirmButton);
    }

    public synchronized static ConfirmDirectionPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new ConfirmDirectionPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,20);
            Wrapper.init_elements(ConfirmDirectionPage.class);
            wait.until(ExpectedConditions.visibilityOf(confirmButton));

        }

        return sSoleInstance;
    }

    public void validar_form(int modo, String modoDatos) {

        if (modoDatos.toUpperCase().contentEquals("CORRECTOS")) {
            validar_info_correcta(modo);
        } else {
           validar_info_desambigua(modo);
        }

    }

    public void validar_info_correcta(int modo) {






    }

    public void validar_info_desambigua(int modo) {
       /* objects.btnContinuar().waitToAppear(20);

        //Assert titulo
        Assert.assertEquals(objects.lblTitulo().getText(), TextList.TEXT_DATOS_ADICIONALES.getText());

        //Assert subtitulo
        Assert.assertEquals(objects.lblSubtitulo2().getText(), TextList.TEXT_DESAMBIGUACION_DIRECCION.getText());

        int nroElements = Android.driver.findElements(By.xpath("//" + TextList.CLASSNAME_RADIO_BUTTON.getText())).size();
        Assert.assertTrue(nroElements > 1);

        Assert.assertTrue(objects.btnContinuar().isEnabled());

        //Pulsar continuar
        objects.btnContinuar().tap();*/

        //Ahora valido pantalla de confirmacion
        validar_info_correcta(modo);

    }

    public void click_confirm_button(){
        waitForElementEnabled(confirmButton);
        confirmButton.click();
    }
}
