package steps.def;

import api.apps.android.Android;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.dashboard.DashboardPage;
import api.apps.android.nw.menu.MenuPage;
import core.Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;

public class MenuSteps {


    @Test
    /**
     * Click sobre el boton dado en
     * @param btnMenu
     */
    @When("Pulsa el boton {string} en el menu")
    public void tap_boton_menu(String btnMenu) throws Exception{
        try {

            MenuPage menuPage = MenuPage.getInstance();

            //Validamos cual boton pulsar
            switch (btnMenu.toLowerCase()){
                case "principal":
                    menuPage.clickBtnMenu();
                    break;
                case "recarga_sube":
                    menuPage.clickSubeButton();
                    break;
                case "perfil":
                    menuPage.clickBtnPerfil();
                    break;
                case "send":
                case "enviar":
                    menuPage.clickBtnEnviar();
                    break;
                case "extraer_dinero":
                    menuPage.clickExtraerButton();
                    break;
                case "recarga_celular":
                    menuPage.clickRecargaCelular();
                    break;
                case "request_pending":
                case "solicitar":
                    menuPage.clickSolicitarButton();
                    break;
                case "tarjeta_prepaga":
                    menuPage.clickTarjetaPrepagaButton();
                    break;
                case "pagar_factura":
                    menuPage.clickPayBillsButton();
                    break;
                case "solicitudes":
                    menuPage.clickBtnSolicitudes();
                    break;
                case "contactos":
                    menuPage.clickContactsButton();
                    break;
                case "movimientos":
                    menuPage.clickMovementsButton();
                    break;
                case "cash-in":
                    DashboardPage.getInstance().click_cashin_button();
                    break;
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Verifica la existencia de una opcion en el Menu que se despliega en el dashboard
     * al hacer click sobre el boton con el logo de Nubi.
     * @param optMenu
     */
    @Then("Visualizar {string} en el submenu")
    public void assertSubeOptionInMenu(String optMenu) throws Exception {

        try {

            MenuPage menuPage = MenuPage.getInstance();

            optMenu = optMenu.toLowerCase();

            //Validar la opcion
            switch (optMenu){

                case "enviar":
                    Assert.assertTrue(menuPage.elementExists(menuPage.getPopupButton(0)));
                    break;
                case "solicitar":
                    Assert.assertTrue(menuPage.elementExists(menuPage.getPopupButton(1)));
                    break;
                case "cargar":
                    Assert.assertTrue(menuPage.btnCellRechargeExists());
                    break;
                case "extraer":
                    Assert.assertTrue(menuPage.btnExtraerExists());
                    break;
                case "celular":
                    Assert.assertTrue(menuPage.elementExists(menuPage.getPopupButton(3)));
                    break;
                case "sube":
                    Assert.assertTrue(menuPage.btnSubeExists());
                    break;
            }
        }
        catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }
}
