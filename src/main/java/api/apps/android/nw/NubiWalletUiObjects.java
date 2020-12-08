package api.apps.android.nw;

import core.UiObject;
import core.UiSelector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NubiWalletUiObjects {

    /**
     * Contiene objetos que son comunes en toda la app
     */

    private static UiObject
            txtPinMask1,
            txtPinMask2,
            txtPinMask3,
            txtPinMask4,
            btnAtras,
            btnCerrar,
            btnSiguiente,
            lblTitulo,
            imgGenerica,
            lblSubtitulo,
            lblSubtitulo2,
            lblErrorMsg,
            btnCirculoSiguiente,
            lblInputError,
            lblMensaje,
            txtGenerico,
            esqueleton,
            txtContrasenia,
            btnSiguiente2,
            btnContinuar,
            lblDescripcionGenerico,
            lblTransactionMessage,
            btnFinish,
            lblDataTitle,
            imgCompany,
            btnMain,
            btnSecondary,
            imgIcono,
            btnGenerico,
            btnActionButtonPrimary,
            lblDisclaimer,
            btnConfirmar;

    private static final Logger logger = LogManager.getLogger();

    //Titulo para las secciones de informacion personal
    public UiObject lblDataTitle() {
        if (lblDataTitle == null)
            lblDataTitle = new UiSelector().resourceId(SelectorList.DATA_TITLE.getSelector()).makeUiObject();
        return lblDataTitle;
    }

    public UiObject esqueleton() {
        String xpath = "";
        if (esqueleton == null)

            xpath = "//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[1]";
        esqueleton = new UiSelector().xPath(xpath).makeUiObject();
        return esqueleton;
    }

    public UiObject btnAtras() {
        if (btnAtras == null)
            btnAtras = new UiSelector().resourceId(SelectorList.BACK_BUTTON.getSelector()).makeUiObject();
        return btnAtras;
    }

    public UiObject btnGenerico() {
        if (btnGenerico == null)
            btnGenerico = new UiSelector().resourceId(SelectorList.BUTTON.getSelector()).makeUiObject();
        return btnGenerico;
    }

    public UiObject btnMain() {
        if (btnMain == null)
            btnMain = new UiSelector().resourceId(SelectorList.MAIN_BUTTON.getSelector()).makeUiObject();
        return btnMain;
    }

    public UiObject btnSecondary() {
        if (btnSecondary == null)
            btnSecondary = new UiSelector().resourceId(SelectorList.SECONDARY_BUTTON.getSelector()).makeUiObject();
        return btnSecondary;
    }

    public UiObject btnCerrar() {
        if (btnCerrar == null)
            btnCerrar = new UiSelector().resourceId(SelectorList.CLOSE_BUTTON.getSelector()).makeUiObject();
        return btnCerrar;
    }

    public UiObject btnSiguiente2() {
        if (btnSiguiente2 == null)
            btnSiguiente2 = new UiSelector().resourceId(SelectorList.NEXT_BOTTOM.getSelector()).makeUiObject();
        return btnSiguiente2;
    }

    public UiObject btnSiguiente() {
        if (btnSiguiente == null)
            btnSiguiente = new UiSelector().resourceId(SelectorList.NEXT_BUTTON.getSelector()).makeUiObject();
        return btnSiguiente;
    }

    public UiObject lblTitulo() {
        if (lblTitulo == null)
            lblTitulo = new UiSelector().resourceId(SelectorList.LBL_TITLE.getSelector()).makeUiObject();
        return lblTitulo;
    }

    public UiObject imgGenerica() {
        if (imgGenerica == null)
            imgGenerica = new UiSelector().resourceId(SelectorList.IMAGE.getSelector()).makeUiObject();
        return imgGenerica;
    }

    public UiObject lblSubtitulo() {
        if (lblSubtitulo == null)
            lblSubtitulo = new UiSelector().resourceId(SelectorList.LBL_SUBTITLE.getSelector()).makeUiObject();
        return lblSubtitulo;
    }

    public UiObject lblSubtitulo2() {
        if (lblSubtitulo2 == null)
            lblSubtitulo2 = new UiSelector().resourceId(SelectorList.LBL_SUBTITLE_2.getSelector()).makeUiObject();
        return lblSubtitulo2;
    }

    public UiObject lblErrorMsg() {
        if (lblErrorMsg == null)
            lblErrorMsg = new UiSelector().resourceId(SelectorList.LBL_ERROR_MESSAGE.getSelector()).makeUiObject();
        return lblErrorMsg;
    }

    public UiObject lblInputError() {
        if (lblInputError == null)
            lblInputError = new UiSelector().resourceId(SelectorList.LBL_INPUT_ERROR.getSelector()).makeUiObject();
        return lblInputError;
    }

    public UiObject btnCirculoSiguiente() {
        if (btnCirculoSiguiente == null)
            btnCirculoSiguiente = new UiSelector().resourceId(SelectorList.CIRCLE_BUTTON.getSelector()).makeUiObject();
        return btnCirculoSiguiente;
    }

    public UiObject btnContinuar() {
        if (btnContinuar == null)
            btnContinuar = new UiSelector().resourceId(SelectorList.CONTINUE_BUTTON.getSelector()).makeUiObject();
        return btnContinuar;
    }

    public UiObject btnFinish() {
        if (btnFinish == null)
            btnFinish = new UiSelector().resourceId(SelectorList.FINISH_BUTTON.getSelector()).makeUiObject();
        return btnFinish;
    }

    public UiObject lblMensaje() {
        if (lblMensaje == null)
            lblMensaje = new UiSelector().resourceId(SelectorList.LBL_MESSAGE.getSelector()).makeUiObject();
        return lblMensaje;
    }

    public UiObject txtGenerico() {
        if (txtGenerico == null)
            txtGenerico = new UiSelector().resourceId(SelectorList.INPUT.getSelector()).makeUiObject();
        return txtGenerico;
    }

    public UiObject lblDescripcionGenerico() {
        if (lblDescripcionGenerico == null)
            lblDescripcionGenerico = new UiSelector().resourceId(SelectorList.LBL_GENERIC_DESCRIPTION.getSelector()).makeUiObject();
        return lblDescripcionGenerico;
    }

    public UiObject lblTransactionMessage() {
        if (lblTransactionMessage == null)
            lblTransactionMessage = new UiSelector().resourceId(SelectorList.LBL_TRANSACTION_MESSAGE.getSelector()).makeUiObject();
        return lblTransactionMessage;
    }

    public UiObject imgIcono() {
        if (imgIcono == null)
            imgIcono = new UiSelector().resourceId(SelectorList.GENERIC_ICON.getSelector()).makeUiObject();
        return imgIcono;
    }


    //txt contrasenia
    public UiObject txtContrasenia() {
        if (txtContrasenia == null)
            txtContrasenia = new UiSelector().resourceId(SelectorList.PASSWORD_INPUT.getSelector()).makeUiObject();
        return txtContrasenia;
    }

    /**
     * Elementos para el PIN y codigo sms**?
     */

    private static UiObject
            txtPrimerDigito,
            txtSegundoDigito,
            txtTercerDigito,
            txtCuartoDigito,
            lblPinIncorrecto;

    public UiObject txtPrimerDigito() {
        //if (txtPrimerDigito == null)
        try {
            txtPrimerDigito = new UiSelector().resourceId(SelectorList.INPUT_FIRST_DIGIT.getSelector()).makeUiObject();
        } catch(Exception e) {
            txtPrimerDigito = new UiSelector().resourceId(SelectorList.INPUT_PIN_MASK1.getSelector()).makeUiObject();
        }
        return txtPrimerDigito;
    }

    public UiObject txtSegundoDigito() {
        try {
            txtSegundoDigito = new UiSelector().resourceId(SelectorList.INPUT_SECOND_DIGIT.getSelector()).makeUiObject();
        } catch(Exception e) {
            txtSegundoDigito = new UiSelector().resourceId(SelectorList.INPUT_PIN_MASK2.getSelector()).makeUiObject();
        }
        return txtSegundoDigito;
    }

    public UiObject txtTercerDigito() {
        try {
            txtTercerDigito = new UiSelector().resourceId(SelectorList.INPUT_THIRD_DIGIT.getSelector()).makeUiObject();
        } catch(Exception e) {
            txtTercerDigito = new UiSelector().resourceId(SelectorList.INPUT_PIN_MASK3.getSelector()).makeUiObject();
        }
        return txtTercerDigito;
    }

    public UiObject txtCuartoDigito() {
        try {
            txtCuartoDigito = new UiSelector().resourceId(SelectorList.INPUT_FOURTH_DIGIT.getSelector()).makeUiObject();
        } catch(Exception e) {
            txtCuartoDigito = new UiSelector().resourceId(SelectorList.INPUT_PIN_MASK4.getSelector()).makeUiObject();
        }
        return txtCuartoDigito;
    }

    public UiObject txtPinMask1() {
        if (txtPinMask1 == null)
            txtPinMask1 = new UiSelector().resourceId(SelectorList.INPUT_PIN_MASK1.getSelector()).makeUiObject();
        return txtPinMask1;
    }

    public UiObject txtPinMask2() {
        if (txtPinMask2 == null)
            txtPinMask2 = new UiSelector().resourceId(SelectorList.INPUT_PIN_MASK2.getSelector()).makeUiObject();
        return txtPinMask2;
    }

    public UiObject txtPinMask3() {
        if (txtPinMask3 == null)
            txtPinMask3 = new UiSelector().resourceId(SelectorList.INPUT_PIN_MASK3.getSelector()).makeUiObject();
        return txtPinMask3;
    }

    public UiObject txtPinMask4() {
        if (txtPinMask4 == null)
            txtPinMask4 = new UiSelector().resourceId(SelectorList.INPUT_PIN_MASK4.getSelector()).makeUiObject();
        return txtPinMask4;
    }

    public UiObject lblPinIncorrecto() {
        if (lblPinIncorrecto == null)
            lblPinIncorrecto = new UiSelector().resourceId(SelectorList.LBL_ERROR_MESSAGE.getSelector()).makeUiObject();
        return lblPinIncorrecto;
    }

    private static UiObject
            txtCodigoTelefono,
            txtNumeroTelefono;

    public UiObject txtCodigoTelefono() {
        if (txtCodigoTelefono == null)
            txtCodigoTelefono = new UiSelector().resourceId(SelectorList.AREA_CODE_INPUT.getSelector()).makeUiObject();
        return txtCodigoTelefono;
    }

    public UiObject txtNumeroTelefono() {
        if (txtNumeroTelefono == null)
            txtNumeroTelefono = new UiSelector().resourceId(SelectorList.PHONE_INPUT.getSelector()).makeUiObject();
        return txtNumeroTelefono;
    }

    public UiObject imgCompany() {
        if (imgCompany == null)
            imgCompany = new UiSelector().resourceId(SelectorList.IMG_COMPANY.getSelector()).makeUiObject();
        return imgCompany;
    }

    public UiObject getBtnActionButtonPrimary() {
        if (btnActionButtonPrimary == null)
            btnActionButtonPrimary = new UiSelector().resourceId(SelectorList.ACTION_BUTTON_PRIMARY.getSelector()).makeUiObject();
        return btnActionButtonPrimary;
    }

    public UiObject lblDisclaimer() {
        if (lblDisclaimer == null)
            lblDisclaimer = new UiSelector().resourceId(SelectorList.DISCLAIMER.getSelector()).makeUiObject();
        return lblDisclaimer;
    }

    public UiObject btnConfirmar() {
        if (btnConfirmar == null)
            btnConfirmar = new UiSelector().resourceId(SelectorList.CONFIRM_BUTTON.getSelector()).makeUiObject();
        return btnConfirmar;
    }
}
