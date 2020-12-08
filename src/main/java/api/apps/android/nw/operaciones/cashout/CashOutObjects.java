package api.apps.android.nw.operaciones.cashout;

import api.apps.android.nw.NubiWalletUiObjects;
import api.apps.android.nw.SelectorList;
import core.UiObject;
import core.UiSelector;

public class CashOutObjects extends NubiWalletUiObjects {

    private static UiObject
            btnEfectivoCashOut;


    public UiObject btnEfectivoCashOut() {
        if (btnEfectivoCashOut == null)
            btnEfectivoCashOut = new UiSelector().resourceId(SelectorList.BTN_CASH_OUT_EFECTIVO_RAPIPAGO.getSelector()).makeUiObject();
        return btnEfectivoCashOut;
    }

}
