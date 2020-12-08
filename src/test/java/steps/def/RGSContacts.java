package steps.def;

import api.apps.Apps;
import api.apps.android.Wrapper;
import api.apps.android.contactosagenda.OpenNativeContact;
import api.apps.android.nw.contactos.ContactsPage;
import api.apps.android.nw.contactos.addContact.AddContactPage;
import api.apps.android.nw.contactos.detallecontactos.ContactsDetailPage;
import api.apps.android.nw.contactos.saveContact.SaveContactPage;
import api.apps.android.nw.dashboard.DashboardPage;
import com.github.javafaker.App;
import core.Util;
import interfaces.AppInterface;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class RGSContacts implements AppInterface {

    private static String contactName, contactLastName;

    public static String getContactName() {
        return contactName;
    }

    public static void setContactName(String contactName) {
        RGSContacts.contactName = contactName;
    }

    public static String getContactLastName() {
        return contactLastName;
    }

    public static void setContactLastName(String contactLastName) {
        RGSContacts.contactLastName = contactLastName;
    }

    static int i = 0;
    private static final Logger logger = LogManager.getLogger();
    Util util = new Util();

    @Test
    @When("Usuario presiona boton de contactos frecuentes")
    public void tapBtnContactosFrecuentes(){
        try {
            DashboardPage dashboardPage = DashboardPage.getInstance();
            //validamos que se muestre el boton de contactos frecuentes
            //hacemos un swipe hasta que se muestre
            while (!dashboardPage.elementExists(dashboardPage.getFrequentContactsTitle())){
                util.scrollTo();
            }
            // Dos mas porque sino queda SOBRE el boton de Menu.
            int count = 0;
            while (count < 2){
                util.scrollTo();
                count++;
            }


            //Pulsar boton de contactos frecuentes en el dashboard
            dashboardPage.click_frequent_contact_button();
            Thread.sleep(3);

        }catch (Exception | AssertionError e){
            e.printStackTrace();
        }
    }

    @And("Permitir el acceso a los contactos {string}")
    public void set_permisos_contacto(String permiso) throws InterruptedException{
        try {
            permiso = permiso.toLowerCase();

            //Accionar boton segun parametro dado
            ContactsPage contactsPage = new ContactsPage();
            switch (permiso){
                case "s":
                case "si":
                    contactsPage.clickAllow();
                    break;
                case "n":
                case "no":
                    contactsPage.clickReject();
                    break;
                    default:
                        break;
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @And("Permitir el acceso a los contactos {string} por primera vez {string}")
    public void set_permisos_contacto_first_time_only(String permiso, String firstTime) throws InterruptedException{
        try {
            permiso = permiso.toLowerCase();

            WebElement element;
            CommonSteps commonSteps = new CommonSteps();
            //Accionar boton segun parametro dado
            ContactsPage contactsPage = new ContactsPage();

            if ( firstTime.equals("si") ) {
                switch (permiso){
                    case "s":
                    case "si":
                        contactsPage.clickAllow();
                        commonSteps.press_recent_app_button();
                        break;
                    case "n":
                    case "no":
                        contactsPage.clickReject();
                        break;
                }
            }


        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }


    @Then("Validate elements on contactosagenda screen when mode is {string}")
    public void validateElements(String mode){
        ContactsPage contactsPage = new ContactsPage();
        try {
            if (mode.toLowerCase().contentEquals("on")){
                Assert.assertTrue(contactsPage.lblNubiContactsExists());
                Assert.assertTrue(contactsPage.lblContactsExists());
            }else{
                Assert.assertTrue(contactsPage.errorImageExists());
                Assert.assertTrue(contactsPage.contactTitleExists());
            }
        }catch (Exception e){
            util.showTrackTraceError("Unable to tap contact button", e);
        }
    }

    @Then("El usuario ingresa el alias o cbu {string}")
    public void el_usuario_ingresa_el_alias(String alias){
        AddContactPage addContactPage = new AddContactPage();
        addContactPage.setCBUAlias(alias);
    }

    @Then("Validar estado {string} y validar {string}, {string}, {string} y el mensaje es {string}")
    public void validar_estado_y_valores_en_agregar_contacto(String status, String alias, String cbu, String fullName, String error){
        if ( status.equals("exito") ) {
            SaveContactPage saveContactPage = new SaveContactPage();
            if ( !cbu.equals("none") )
                assertEquals(cbu, saveContactPage.getCBU());
            if ( !alias.equals("none") )
                assertEquals(alias, saveContactPage.getAlias());
            if ( !fullName.equals("none") )
                assertEquals(fullName, saveContactPage.getFullName());
        }
        else {
            if (status.equals("alias_cvu")) {
                AddContactPage addContactPage = new AddContactPage();
                assertEquals(error, addContactPage.getWarningMessage().trim());
            }
            else if ( !error.equals("none") ) {
                SaveContactPage saveContactPage = new SaveContactPage();
                assertEquals(error, saveContactPage.getError().trim());
            }
        }
    }

    @Then("Ingresar el nombre del contacto con {string} si estado {string}")
    public void validar_estado_y_valores_en_agregar_contacto(String contactInput, String status) throws IOException {
        if ( status.equals("exito") && !contactInput.equals("none") && !contactInput.equals("alias_cvu") ) {
            SaveContactPage saveContactPage = new SaveContactPage();
            saveContactPage.clearContactName();
            saveContactPage.addContactName(contactInput);
        }
    }

    @Then("Guardar el contacto si estado {string}")
    public void guardar_el_contacto(String status){
        if ( status.equals("exito") ) {
            SaveContactPage saveContactPage = new SaveContactPage();
            saveContactPage.saveContact();
        }
    }

    @Then("Validar que el contacto ingresado {string} existe si estado {string}")
    public void validar_el_contacto_ingresado(String contactName, String status) throws InterruptedException {
        if ( status.equals("exito") ) {
            ContactsPage contactsPage = new ContactsPage();
            try{
                contactsPage.contactFullName(contactName);
            }
            catch (NoSuchElementException e){
                Thread.sleep(3000);
            }
            MobileElement UiContactName = contactsPage.contactFullName(contactName);
            while (!UiContactName.isDisplayed()) util.scrollTo();
            assertEquals(contactName, UiContactName.getText());
        }
    }

    @Then("Click siguiente en pantalla {string} si estado {string}")
    public void click_siguiente_dependiendo_de_estado(String screen, String status) throws Exception {
        if ( status.equals("exito") || status.equals("alias_cvu") ) {
            CommonSteps commonSteps = new CommonSteps();
            commonSteps.click_on_next_button(screen);
        }
    }

    @Then("Click boton back en {string} en pantalla {string} si estado {string}")
    public void click_boton_back_en_pantalla_dependiendo_de_estado(String modo, String screen, String status) throws Exception {
        if (status.equals("close") || (status.equals("alias_cvu"))) {
            CommonSteps commonSteps = new CommonSteps();
            commonSteps.tap_button_back_on_screen(modo, screen);
        }
    }

    @And("Validar que hay contactos {string}")
    public void validar_existencia_contactos(String tipoContacto) throws Exception{
        boolean error = false;
        try{

            tipoContacto = tipoContacto.toLowerCase();

            ContactsPage contactsPage = new ContactsPage();
            switch (tipoContacto){
                case"nubi":
                    do{
                        if(contactsPage.nubiSeparatorExists()) {
                            break;
                        }else {
                            util.scrollTo();
                            i++;
                            if(i==4)
                                break;
                            validar_existencia_contactos(tipoContacto);
                        }

                    }while (!contactsPage.nubiSeparatorExists());

                    break;
                case"bancario":
                    do{
                        if(contactsPage.bankSeparatorExists()) {
                            break;
                        }else {
                            util.scrollTo();
                            i++;
                            if(i==6)
                                break;
                            validar_existencia_contactos(tipoContacto);
                        }

                    }while (!contactsPage.bankSeparatorExists());

                    break;
                case"nonubi":
                    do{
                        if(contactsPage.phoneSeparatorExists()) {
                            break;
                        }else {
                            util.scrollTo();
                            i++;
                            if(i==8)
                                break;
                            validar_existencia_contactos(tipoContacto);
                        }

                    }while (!contactsPage.phoneSeparatorExists());

                    break;
            }

            if (i==8){
                throw new Exception("No hay contactos " + tipoContacto);
            }

        }catch (Exception | Error e){
            if(i==4){
                e.printStackTrace();
                i = 0;
                error = true;
                throw e;

            }else{
                throw e;
            }
        }
    }

    @And("Selecciona contacto {string}")
    public void seleccionar_contacto(String tipoContacto) throws Exception {
        try{

            tipoContacto = tipoContacto.toUpperCase();
            WebElement element = null;

            ContactsPage contactsPage = ContactsPage.getInstance();

            Assert.assertEquals(ContactsPage.CONTACTS_PAGE_TITLE_TEXT, Wrapper.getElementText(contactsPage.getContactTitle()));

            switch (tipoContacto){
                case"NUBI":

                   while (!contactsPage.elementExists(contactsPage.getNubiSeparator()))
                       util.scrollTo();

                   //Select the destination user
                   List<MobileElement> contacts = contactsPage.getContactUserName();
                    boolean finded = false;
                    while (!finded){
                        for(MobileElement contact : contacts){
                            if(Wrapper.getElementText(contact).equals(CommonStepsMovements.destinationUser.getUsername())) {
                                finded = true;
                                contactsPage.clickContactName(contact);
                                break;
                            }
                        }
                        Apps.util.scrollTo();
                    }

                    break;
                case"BANCARIO":

                    while (!contactsPage.elementExists(contactsPage.getBankSeparator()))
                        util.scrollTo();


                    break;
                case"NONUBI":

                    while (!contactsPage.elementExists(contactsPage.getPhoneSeparator()))
                        util.scrollTo();


                    break;
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }

    @And("Validar detalle contacto {string}")
    public void validar_detalle_contacto(String tipoContacto){
        try{

            tipoContacto = tipoContacto.toLowerCase();
            WebElement element = null;

            ContactsDetailPage contactsDetailPage = new ContactsDetailPage();

            //validar titulo
            Assert.assertEquals(contactsDetailPage.getLblTitle().getText(), "Eleg\u00ED una cuenta");

            //Validar que este presente el nombre
            //Assert.assertTrue(contactsDetailPage.lblContactNameExists());

            //validar que este presente el boton editar
           // Assert.assertTrue(contactsDetailPage.btnEditContactExists());
            switch (tipoContacto){
                case"nubi":
                   // Assert.assertTrue(contactsDetailPage.logoContactExists());
                   // Assert.assertTrue(contactsDetailPage.lblPhoneNumberExists());
                    break;
                case"bancario":
                  //  Assert.assertTrue(contactsDetailPage.logoContactExists());
                   // Assert.assertFalse(contactsDetailPage.lblPhoneNumberExists());
                    break;
                case"nonubi":
                    //Assert.assertFalse(contactsDetailPage.logoContactExists());
                    //Assert.assertTrue(contactsDetailPage.lblPhoneNumberExists());
                    break;
            }

        }catch (Exception | Error e){
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * Verifica si en la lista de contactos existe el separador que identifica un determinado tipo
     * de contacto, con lo cual, se garantiza que exista al menos un contacto de ese tipo
     * @param contactType: el tipo de contacto que se espera encontrar.
     */
    @Then("Se visualizan los contactos {string}")
    public void assertAnSpecificContactTypeExist(String contactType) {
        try {
            ContactsPage contactsPage = ContactsPage.getInstance();
            switch (contactType.toLowerCase()){
                case "nubi":
                    Assert.assertTrue(contactsPage.nubiSeparatorExists());
                    break;
                case "otras_cuentas":
                    while(!contactsPage.bankSeparatorExists()) util.scrollTo();
                    Assert.assertTrue(contactsPage.bankSeparatorExists());
                    break;
                case "otros_contactos":
                    while(!contactsPage.phoneSeparatorExists()) util.scrollTo();
                    Assert.assertTrue(contactsPage.phoneSeparatorExists());
                    break;
            }
        }
        catch (AssertionError e){
            logger.error("\n\n"+e.getMessage()+"\n");
            throw new AssertionError("No se encontro el tipo de contacto buscado (" + contactType + ")");
        }
    }

    @Then("Usuario agrega un contacto en el Phonebook")
    public void addPhoneBookContact() {
        // llamada a la BD para pediro por 1 usuario (util.getUSerFromDB())
        OpenNativeContact openContact = new OpenNativeContact();
        openContact.addContact("Test", "Saraza", "+549999999999");
    }

    @Then("Se validan los elementos de la pantalla agregar contacto con CBU o Alias")
    public void validateAddCBUContactPage() {
        AddContactPage addContactPage = AddContactPage.getInstance();
        Assert.assertEquals(AddContactPage.SUBTITLE, addContactPage.get_element_text(addContactPage.getLblSubtitle()).replace("\n", " "));
        addContactPage.waitForElementVisibility(addContactPage.getBackOrCloseBtn());
        addContactPage.waitForElementVisibility(addContactPage.getCBUAliasInput());
    }

    @Then("Presiona sobre el boton en la pantalla detalle contacto {string}")
    public void clickOnAddContactBtn(String btnText) {
        if ( btnText.equals("Agregar otra cuenta") )
            ContactsDetailPage.getInstance().clickSecondaryBtn();
    }
}
