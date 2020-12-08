package api.apps.android.nw.operaciones.pagoServicios.transaccionFallida;

import api.apps.android.nw.NubiWalletUiObjects;
import api.apps.android.nw.SelectorList;
import core.UiObject;
import core.UiSelector;

public class TransaccionFallidaUiObjects extends NubiWalletUiObjects {

    private static UiObject
            lblMainTitle,
            imgErrorIcon,
            lblSubtitle,
            btnBackToHome;

    public UiObject lblMainTitle() {
        if (lblMainTitle == null)
            lblMainTitle = new UiSelector().resourceId(SelectorList.TITLE_PAYMENT.getSelector()).makeUiObject();
        return lblMainTitle;
    }

    public UiObject imgErrorIcon() {
        if (imgErrorIcon == null)
            imgErrorIcon = new UiSelector().resourceId(SelectorList.ERROR_ICON.getSelector()).makeUiObject();
        return imgErrorIcon;
    }

    // id/title se extiende de la clase NubiWalletUiObjects

    public UiObject lblSubtitle() {
        if (lblSubtitle == null)
            lblSubtitle = new UiSelector().resourceId(SelectorList.LBL_SUBTITLE.getSelector()).makeUiObject();
        return lblSubtitle;
    }

    public UiObject btnBackToHome() {
        if (btnBackToHome == null)
            btnBackToHome = new UiSelector().resourceId(SelectorList.MAIN_BUTTON.getSelector()).makeUiObject();
        return btnBackToHome;
    }

}
