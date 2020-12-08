package api.apps.android.nw.operaciones.pagoServicios.transaccionFallida;

import api.interfaces.Activity;

public class TransaccionFallida implements Activity {

    public TransaccionFallidaUiObjects uiObject = new TransaccionFallidaUiObjects();

    @Override
    public TransaccionFallida waitToLoadScreen() {
        uiObject.lblSubtitle().waitToAppear(15);
        return this;
    }

    /**
     * Devuelve el texto del titulo principal de la pantalla
     */
    public String getFailedTransactionTitleText() {
        return uiObject.lblMainTitle().getText();
    }

    /**
     * Valida que exista el icono de error en la pantalla
     */
    public boolean isErrorIconDisplayed() {
        return uiObject.imgErrorIcon().exists();
    }

    /**
     * Devuelve el texto del subtitulo de la pantalla
     */
    public String getSubtitleText() {
        return uiObject.lblSubtitle().getText();
    }

    /**
     * Devuelve el texto del boton "Volver al Inicio"
     */
    public String getBackToHomeButtonText() {
        return uiObject.btnBackToHome().getText();
    }

    /**
     * Valida que el boton "Volver al Inicio" este habilitado
     */
    public boolean isBackToHomeButtonEnabled() {
        return uiObject.btnBackToHome().isEnabled();
    }

    /**
     * Presiona sobre sobre el boton "Volver al Inicio"
     */
    public void tapBackToHomeButton() {
        uiObject.btnBackToHome().tap();
    }

}
