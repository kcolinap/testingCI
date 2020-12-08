package api.apps.android.nw.operaciones.pagoServicios.saldoInsuficiente;

import api.apps.android.nw.NubiWalletUiObjects;
import api.apps.android.nw.SelectorList;
import core.UiObject;
import core.UiSelector;

public class SaldoInsuficienteUiObjects extends NubiWalletUiObjects {

    private static UiObject
            lblEstaTodoOk;

    public UiObject lblEstaTodoOk() {
        if (lblEstaTodoOk == null)
            lblEstaTodoOk = new UiSelector().resourceId(SelectorList.LBL_SUBTITLE.getSelector()).makeUiObject();
        return lblEstaTodoOk;
    }

}
