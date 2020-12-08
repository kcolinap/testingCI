/*
package api.apps.android.contactosagenda.motorola;

import api.apps.android.Android;
import api.interfaces.Activity;
import core.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomeMoto implements Activity {

    public HomeMotoUiObjects uiObject = new HomeMotoUiObjects();
    private static Util util = new Util();
    private static final Logger logger = LogManager.getLogger();

    @Override
    public HomeMoto waitToLoadScreen() {
        try {
            uiObject.floating_action_button().waitToAppear(15);
            return this;
        } catch (AssertionError e) {
            logger.error("\n\n" + e.getMessage() + "\n");
            throw new AssertionError("Fail to load/open Login screen on moto contactosagenda application");
        }
    }

    public HomeMoto tapAddContact() {
        try {
            uiObject.floating_action_button().tap();
            return Android.app.contactosAgenda.homeMoto;
        } catch (AssertionError e) {
            throw new AssertionError("Failed to tap add contact button");
        }
    }

    public HomeMoto setNombre(String name) {
        try {
            uiObject.txtNombre().typeText(name);
            return Android.app.contactosAgenda.homeMoto;
        } catch (AssertionError e) {
            throw new AssertionError("Failed to set user name to add contact");
        }
    }

    public HomeMoto setTlfn(String tlfn) {
        try {
            uiObject.txtTelefono().typeText(tlfn);
            return Android.app.contactosAgenda.homeMoto;
        } catch (AssertionError e) {
            throw new AssertionError("Failed to set user tlfn to add contact");
        }
    }

    public HomeMoto tapSaveButton() {
        try {
            if (uiObject.menu_save().exists()) {
                uiObject.menu_save().tap();
            } else if (uiObject.menu_save_pixel().exists()) {
                uiObject.menu_save_pixel().tap();
            }
            return Android.app.contactosAgenda.homeMoto;
        } catch (AssertionError e) {
            throw new AssertionError("Failed to tap save contact button");
        }
    }
}
*/
