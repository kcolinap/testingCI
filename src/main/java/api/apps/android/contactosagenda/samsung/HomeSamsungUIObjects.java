package api.apps.android.contactosagenda.samsung;

import api.apps.android.nw.SelectorList;
import api.apps.android.nw.TextList;
import core.UiObject;
import core.UiSelector;

public class HomeSamsungUIObjects {

    private static UiObject
            btnAgregarContacto,
            txtNombre,
            txtTlfn,
            menu_done;

    public UiObject btnAgregarContacto() {
        if (btnAgregarContacto == null)
            btnAgregarContacto = new UiSelector().resourceId(SelectorList.FLOATING_ACTION_BUTTON_SAMSUNG.getSamsungSelector()).makeUiObject();
        return btnAgregarContacto;
    }

    public UiObject txtNombre() {
        //if(txtNombre == null)txtNombre = new UiSelector().text("Nombre").makeUiObject();
        if (txtNombre == null)
            txtNombre = new UiSelector().resourceId(SelectorList.NAME_EDIT.getSamsungSelector()).makeUiObject();
        return txtNombre;
    }

    public UiObject txtTelefono() {
        if (txtTlfn == null) txtTlfn = new UiSelector().text(TextList.TEXT_PHONE.getText()).makeUiObject();
        return txtTlfn;
    }

    public UiObject menu_done() {
        if (menu_done == null)
            menu_done = new UiSelector().resourceId(SelectorList.MENU_DONE.getSamsungSelector()).makeUiObject();
        return menu_done;
    }
}
