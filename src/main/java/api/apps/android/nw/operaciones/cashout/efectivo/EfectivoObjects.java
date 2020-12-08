package api.apps.android.nw.operaciones.cashout.efectivo;

import api.apps.android.nw.SelectorList;
import api.apps.android.nw.operaciones.cashout.CashOutObjects;
import core.UiObject;
import core.UiSelector;

public class EfectivoObjects extends CashOutObjects {

    private static UiObject
            lblCodigoCashOut,
            btnGenerarCodigo,
            vgroupWarning;


    public UiObject lblCodigoCashOut() {
        if (lblCodigoCashOut == null)
            lblCodigoCashOut = new UiSelector().resourceId(SelectorList.LBL_CODIGO_CASHOUT.getSelector()).makeUiObject();
        return lblCodigoCashOut;
    }

    public UiObject vgroupWarning() {
        if (vgroupWarning == null)
            vgroupWarning = new UiSelector().resourceId(SelectorList.LBL_CODIGO_CASHOUT.getSelector()).makeUiObject();
        return vgroupWarning;
    }

    public UiObject btnGenerarCodigo() {
        if (btnGenerarCodigo == null)
            btnGenerarCodigo = new UiSelector().resourceId(SelectorList.BUTTON_GENERAR_CODIGO_CASHOUT.getSelector()).makeUiObject();
        return btnGenerarCodigo;
    }

}
