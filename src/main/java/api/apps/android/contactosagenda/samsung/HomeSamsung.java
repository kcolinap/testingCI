package api.apps.android.contactosagenda.samsung;

import api.interfaces.Activity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomeSamsung implements Activity {

    public HomeSamsungUIObjects uiObject = new HomeSamsungUIObjects();
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Object waitToLoadScreen() {
        try {
            uiObject.btnAgregarContacto().waitToAppear(15);
            return this;
        } catch (AssertionError e) {
            logger.error("\n\n" + e.getMessage() + "\n");
            throw new AssertionError("Error al cargar aplicacion contactos en Samsung");
        }
    }

    public void tapAgregarContacto() {
        try {
            uiObject.btnAgregarContacto().tap();
        } catch (AssertionError e) {
            throw new AssertionError("Error al pulsar agregar contactos en Samsung");
        }
    }

    public void setNombre(String nombre) {
        try {
            uiObject.txtNombre().typeText(nombre);
        } catch (AssertionError e) {
            throw new AssertionError("Error al setear nombre de contacto");
        }
    }

    public void setTlfn(String tlfn) {
        try {
            uiObject.txtTelefono().typeText(tlfn);
        } catch (AssertionError e) {
            throw new AssertionError("Error al setear numero de telefono");
        }
    }

    public void tapBtnGuardarContacto() {
        try {
            uiObject.menu_done().tap();
        } catch (AssertionError e) {
            throw new AssertionError("Error al pulsar guardar contacto");
        }
    }
}
