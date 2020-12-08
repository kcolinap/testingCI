package api.apps.android.contactosagenda.motorola;

import api.apps.android.nw.SelectorList;
import api.apps.android.nw.TextList;
import core.UiObject;
import core.UiSelector;

public class HomeMotoUiObjects {

    private static UiObject
            floating_action_button,
            txtNombre,
            txtTlfn,
            menu_save,
            menu_save_pixel;

    public UiObject floating_action_button() {
        if (floating_action_button == null)
            floating_action_button = new UiSelector().resourceId(SelectorList.FLOATING_ACTION_BUTTON_MOTOROLA.getMotorolaSelector()).makeUiObject();
        return floating_action_button;
    }

    public UiObject txtNombre() {
        if (txtNombre == null) txtNombre = new UiSelector().text(TextList.TEXT_NOMBRE.getText()).makeUiObject();
        return txtNombre;
    }

    public UiObject txtTelefono() {
        if (txtTlfn == null) txtTlfn = new UiSelector().text(TextList.TEXT_PHONE.getText()).makeUiObject();
        return txtTlfn;
    }

    public UiObject menu_save() {
        if (menu_save == null)
            menu_save = new UiSelector().resourceId(SelectorList.MENU_SAVE.getMotorolaSelector()).makeUiObject();
        return menu_save;
    }

    public UiObject menu_save_pixel() {
        if (menu_save_pixel == null)
            menu_save_pixel = new UiSelector().resourceId(SelectorList.EDITOR_MENU_SAVE_BUTTON.getMotorolaSelector()).makeUiObject();
        return menu_save_pixel;
    }

}
