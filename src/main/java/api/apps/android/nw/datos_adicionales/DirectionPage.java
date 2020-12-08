package api.apps.android.nw.datos_adicionales;

import api.apps.android.Android;
import api.apps.android.Wrapper;
import api.apps.android.nw.basePages.BasePage;
import api.apps.android.nw.tarjetaprepaga.fisica.LandingPhysicalPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DirectionPage extends BasePage {

    private static DirectionPage sSoleInstance;

    public static final String SUBTITLE_TEXT = "¿Dónde vivís?";
    public static final String MAIN_TITLE_LOAN_TEXT = "Configurá tu envío";
    public static final String SUBTITLE_LOAN_TEXT = "Agregá otra dirección";


    @AndroidFindBy(id = "streetNameInput")
    private static MobileElement streetNameInput;

    @AndroidFindBy(id = "streetNumberInput")
    private MobileElement streetNumberInput;

    @AndroidFindBy(id = "floorNumberInput")
    private MobileElement floorNumberInput;

    @AndroidFindBy(id = "apartmentNumberInput")
    private MobileElement apartmentNumberInput;

    @AndroidFindBy(id = "zipCodeInput")
    private MobileElement zipCodeInput;

    @AndroidFindBy(id = "neighbourhoodInput")
    private MobileElement neighbourhoodInput;

    @AndroidFindBy(id = "provinceName")
    private MobileElement provinceName;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout/android.widget.ListView")
    private MobileElement provinceList;

    @AndroidFindBy(id = "button")
    private static MobileElement button;

    public MobileElement getStreetNameInput() {
        return streetNameInput;
    }

    public MobileElement getStreetNumberInput() {
        return streetNumberInput;
    }

    public MobileElement getFloorNumberInput() {
        return floorNumberInput;
    }

    public MobileElement getApartmentNumberInput() {
        return apartmentNumberInput;
    }

    public MobileElement getZipCodeInput() {
        return zipCodeInput;
    }

    public MobileElement getNeighbourhoodInput() {
        return neighbourhoodInput;
    }

    public MobileElement getProvinceName() {
        return provinceName;
    }

    public MobileElement getProvinceList() {
        return provinceList;
    }

    public MobileElement getButton() {
        return button;
    }

    public DirectionPage(){
        super();
        waitForElementVisibility(streetNameInput);
    }

    public synchronized static DirectionPage getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new DirectionPage();
        } else {
            WebDriverWait wait = new WebDriverWait(Android.driver,20);
            Wrapper.init_elements(DirectionPage.class);
            wait.until(ExpectedConditions.visibilityOf(streetNameInput));

        }

        return sSoleInstance;
    }


    /**
     * @param modo       modo de llenado, 0 para obligatorios, 1 para full
     * @param modoDatos: correctos
     *                   Desambiguos
     */
    public void llenar_form(int modo, String modoDatos) {
        boolean result;

        result = (modoDatos.toUpperCase().contentEquals("CORRECTOS")) ? fill_form(modo, modoDatos) : fill_form(modo, modoDatos);

    }

    public boolean fill_form(int modo, String modoDatos) {

        String[] data;

        if (modoDatos.toUpperCase().contentEquals("CORRECTOS"))
            data = getData(0);
        else
            data = getData(1);

        //llenado de obligatorios
       streetNameInput.sendKeys(data[0]);
       streetNumberInput.sendKeys(data[1]);

        //opcionales
        switch (modo) {
            case 1: //full
                floorNumberInput.sendKeys(data[2]);
                apartmentNumberInput.sendKeys(data[3]);
                break;
            default:
                break;
        }

        //obligatorios
       zipCodeInput.sendKeys(data[4]);
        neighbourhoodInput.sendKeys(data[5]);

        //Seleccion de la privincia
        provinceName.click();
        //WebElement element = (WebElement) Android.driver.findElement(By.xpath(TextList.LIST_VIEW.getText()));
        //WebDriverWait wait = new WebDriverWait(Android.driver, 15);
        super.wait.until(ExpectedConditions.visibilityOf(provinceList));

        String xpathprovincia = "//*[@text='" + data[6] + "']";
        util.scrollToElementAndSelectFromList(provinceList, xpathprovincia, 3);

        waitForElementEnabled(button);

        //Pulsar continuar

        button.click();

        return false;
    }

    public void validar_form(int modo, String modoDatos) {

        if (modoDatos.toUpperCase().contentEquals("CORRECTOS")) {
            //validar_info_correcta(modo);
        } else {
           // validar_info_desambigua(modo);
        }

    }

    public String[] getData(int opc) {
        String[] aux = {"", "", "", "", "", "", "", ""};

        switch (opc) {
            case 0:
                aux[0] = util.getProperty("direcciones", "calle", "properties");
                aux[1] = util.getProperty("direcciones", "numero", "properties");
                aux[2] = util.getProperty("direcciones", "piso", "properties");
                aux[3] = util.getProperty("direcciones", "depto", "properties");
                aux[4] = util.getProperty("direcciones", "cp", "properties");
                aux[5] = util.getProperty("direcciones", "localidad", "properties");
                aux[6] = util.getProperty("direcciones", "provincia", "properties");
                aux[7] = util.getProperty("direcciones", "pais", "properties");
                break;
            case 1:
                aux[0] = util.getProperty("direcciones", "calleD", "properties");
                aux[1] = util.getProperty("direcciones", "numeroD", "properties");
                aux[2] = util.getProperty("direcciones", "pisoD", "properties");
                aux[3] = util.getProperty("direcciones", "deptoD", "properties");
                aux[4] = util.getProperty("direcciones", "cpD", "properties");
                aux[5] = util.getProperty("direcciones", "localidadD", "properties");
                aux[6] = util.getProperty("direcciones", "provinciaD", "properties");
                aux[7] = util.getProperty("direcciones", "paisD", "properties");
                break;
            default:
                break;
        }

        return aux;
    }

  /*  public void validar_info_correcta(int modo) {
        objects.btnConfirmar().waitToAppear(20);

        Assert.assertTrue(objects.btnConfirmar().isEnabled());

        //Assert titulo
        Assert.assertEquals(objects.lblTitulo().getText(), TextList.TEXT_DATOS_ADICIONALES.getText());

        //Assert subtitulo
        Assert.assertEquals(objects.lblSubtitulo2().getText(), TextList.TEXTO_CONFIRMACION.getText());

        //Assert disclaimer
        String formatedtext = objects.lblDisclaimer().getText();

        //Assert.assertEquals(TextList.TEXT_DISCLAIMER_DATOS_ADICIONALES.getText(),formatedtext );

        //Assert nombre calle
        while (!objects.viewNombreCalle().exists())
            util.scrollTo();
        Assert.assertTrue(objects.viewNombreCalle().exists());

        //Assert numero calle
        while (!objects.viewNumeroCalle().exists())
            util.scrollTo();
        Assert.assertTrue(objects.viewNumeroCalle().exists());

        if (modo == 1) {
            //Assert numero piso
            while (!objects.viewNumeroPiso().exists())
                util.scrollTo();
            Assert.assertTrue(objects.viewNumeroPiso().exists());
        }

        *//**
         Assert entre calles
         No siempre aparece, sobre todo cuando se selecciona una provincia diferente  capital
         *//*
        int swipes = 0;
        while (!objects.viewEntrecalles().exists() && (swipes < 3)) {
            util.scrollTo();
            swipes++;
        }
        if (swipes < 3)
            Assert.assertTrue(objects.viewEntrecalles().exists());
        else
            Assert.assertFalse(objects.viewEntrecalles().exists());


        //Assert codigo postal
        while (!objects.viewCodigoPostal().exists())
            util.scrollTo();
        Assert.assertTrue(objects.viewCodigoPostal().exists());

        //Assert localidad
        while (!objects.viewLocalidad().exists())
            util.scrollTo();
        Assert.assertTrue(objects.viewLocalidad().exists());

        //Assert provincia
        while (!objects.viewProvincia().exists())
            util.scrollTo();
        Assert.assertTrue(objects.viewProvincia().exists());

    }

    public void validar_info_desambigua(int modo) {
        objects.btnContinuar().waitToAppear(20);

        //Assert titulo
        Assert.assertEquals(TextList.TEXT_DATOS_ADICIONALES.getText(), objects.lblTitulo().getText());

        //Assert subtitulo
        Assert.assertEquals(TextList.TEXT_DESAMBIGUACION_DIRECCION.getText(), objects.lblSubtitulo2().getText());

        int nroElements = Android.driver.findElements(By.xpath("//" + TextList.CLASSNAME_RADIO_BUTTON.getText())).size();
        Assert.assertTrue(nroElements > 1);

        Assert.assertTrue(objects.btnContinuar().isEnabled());

        //Pulsar continuar
        objects.btnContinuar().tap();

        //Ahora valido pantalla de confirmacion
        validar_info_correcta(modo);

    }

    public DatosAdicionales wait_dedicacion_screen(){
        objects.lblSubtitleOcupacion().waitToAppear(14);
        Assert.assertEquals(TextList.TEXT_SUBTITLE_OCCUPATION.getText(), objects.lblSubtitleOcupacion().getText());
        return this;
    }*/
}
