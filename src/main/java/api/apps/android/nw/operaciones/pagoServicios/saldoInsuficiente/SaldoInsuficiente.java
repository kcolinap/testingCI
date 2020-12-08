package api.apps.android.nw.operaciones.pagoServicios.saldoInsuficiente;

import api.interfaces.Activity;

public class SaldoInsuficiente implements Activity {

    public SaldoInsuficienteUiObjects uiObject = new SaldoInsuficienteUiObjects();

    @Override
    public SaldoInsuficiente waitToLoadScreen() {
        uiObject.lblEstaTodoOk().waitToAppear(10);
        return this;
    }

    /**
     * Devuelve el texto del subtitulo de la pantalla confirmacion de pago
     */
    public String getSubtitleText() {
        return uiObject.lblEstaTodoOk().getText();
    }


}
