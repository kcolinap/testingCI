package api.apps.android.nw.cvu;

import api.apps.android.nw.NubiWalletUiObjects;
import api.apps.android.nw.SelectorList;
import core.UiObject;
import core.UiSelector;

public class GeneracionCvuObjects extends NubiWalletUiObjects {

    private static UiObject
            btnGenerar;

    public UiObject btnGenerar() {
        if (btnGenerar == null)
            btnGenerar = new UiSelector().resourceId(SelectorList.GENERATE_BUTTON.getSelector()).makeUiObject();
        return btnGenerar;
    }
}
