package api.apps.android.nw.operaciones;

import api.apps.android.nw.NubiWalletUiObjects;
import api.apps.android.nw.SelectorList;
import core.UiObject;
import core.UiSelector;

public class MovimientosUIObjects extends NubiWalletUiObjects {

    private static UiObject
            title,
            backButton,
            transactionSwitch,
            subtitleQuestion,
            amountInput,
            btnContinuar,
            transactionLabel,
            amountMoney,
            titleTransferDetail,
            nextButton,
            animationView,
            transactionMessage,
            finishButton,
            menuFab, //cierra tab de botones
            sendButton, //button enviar
            requestButton,
            lblCurrentBalance;


    public UiObject nextButton() {
        if (transactionSwitch == null)
            transactionSwitch = new UiSelector().resourceId(SelectorList.NEXT_BUTTON.getSelector()).makeUiObject();
        return transactionSwitch;
    }

    public UiObject transactionSwitch() {
        if (transactionSwitch == null)
            transactionSwitch = new UiSelector().resourceId(SelectorList.TRANSACTION_SWITCH.getSelector()).makeUiObject();
        return transactionSwitch;
    }

    public UiObject subtitleQuestion() {
        if (subtitleQuestion == null)
            subtitleQuestion = new UiSelector().resourceId(SelectorList.LBL_SUBTITLE.getSelector()).makeUiObject();
        return subtitleQuestion;
    }

    public UiObject amountInput() {
        if (amountInput == null)
            amountInput = new UiSelector().resourceId(SelectorList.AMOUNT_INPUT.getSelector()).makeUiObject();
        return amountInput;
    }

    public UiObject btnContinuar() {
        if (btnContinuar == null)
            btnContinuar = new UiSelector().resourceId(SelectorList.CONTINUE_BUTTON.getSelector()).makeUiObject();
        return btnContinuar;
    }

    public UiObject transactionLabel() {
        if (transactionLabel == null)
            transactionLabel = new UiSelector().resourceId(SelectorList.LBL_TRANSACTION.getSelector()).makeUiObject();
        return transactionLabel;
    }

    public UiObject amountMoney() {
        if (amountMoney == null)
            amountMoney = new UiSelector().resourceId(SelectorList.AMOUNT_MONEY.getSelector()).makeUiObject();
        return amountMoney;
    }

    public UiObject titleTransferDetail() {
        if (titleTransferDetail == null)
            titleTransferDetail = new UiSelector().resourceId(SelectorList.LBL_TRANSFER_DETAIL_TITLE.getSelector()).makeUiObject();
        return titleTransferDetail;
    }

    public UiObject lblCurrentBalance() {
        if (lblCurrentBalance == null)
            lblCurrentBalance = new UiSelector().resourceId(SelectorList.CURRENT_BALANCE.getSelector()).makeUiObject();
        return lblCurrentBalance;
    }

    /**
     * successfully screen objects
     */

    public UiObject animationView() {
        if (animationView == null)
            animationView = new UiSelector().resourceId(SelectorList.ANIMATION_VIEW.getSelector()).makeUiObject();
        return animationView;
    }

    public UiObject finishButton() {
        if (finishButton == null)
            finishButton = new UiSelector().resourceId(SelectorList.FINISH_BUTTON.getSelector()).makeUiObject();
        return finishButton;
    }

}
