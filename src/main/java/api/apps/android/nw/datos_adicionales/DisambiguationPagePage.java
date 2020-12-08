package api.apps.android.nw.datos_adicionales;

import api.apps.android.nw.basePages.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;

public class DisambiguationPagePage extends BasePage {

    public static final String SUBTITLE_TEXT = "Seleccioná tu dirección";


    @AndroidFindBy(xpath = "//android.widget.RadioGroup/android.widget.RadioButton")
    private List<MobileElement> directionOptions;

    @AndroidFindBy(id = "continueButton")
    private MobileElement continueButton;

    public List<MobileElement> getDirectionOptions() {
        return directionOptions;
    }

    public MobileElement getContinueButton() {
        return continueButton;
    }

    public DisambiguationPagePage(){
        super();
        waitForElementVisibility(continueButton);
    }

    public int get_direction_options_size(){
        return directionOptions.size();
    }

    public void click_on_continue_button(){
        continueButton.click();
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
}
