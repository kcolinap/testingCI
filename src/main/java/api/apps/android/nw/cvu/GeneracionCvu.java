package api.apps.android.nw.cvu;

import api.apps.android.nw.TextList;
import api.interfaces.Activity;

public class GeneracionCvu implements Activity {

    public GeneracionCvuObjects uiObjects = new GeneracionCvuObjects();

    @Override
    public GeneracionCvu waitToLoadScreen() {
        return null;
    }

    public GeneracionCvu waitScreenValidarIdentidadCvu() {
        try {
            uiObjects.btnContinuar().waitToAppear(15);
            uiObjects.lblTitulo().waitToAppear(15);

            boolean c1, c2;
            c1 = (uiObjects.lblTitulo().getText().contentEquals(TextList.TEXT_TITULO_VALIDAR_IDENTIDAD_CVU.getText()));
            c2 = (uiObjects.lblDescripcionGenerico().getText().contains(TextList.TEXT_VALIDAR_IDENTIDAD.getText()));
            if (!c1 || !c2)
                throw new AssertionError("Error al cargar pantalla de validacion de identidad para genera CVU");

            return this;
        } catch (AssertionError e) {
            throw new AssertionError("Error al cargar pantalla de generacion de CVU");
        }
    }

    public GeneracionCvu waitScreenGenerarCvu() {
        try {
            uiObjects.btnGenerar().waitToAppear(15);
            uiObjects.lblTitulo().waitToAppear(15);

            boolean c1, c2;
            c1 = (uiObjects.lblTitulo().getText().contentEquals(TextList.TEXT_TITULO_GENERACION_CVU.getText()));
            c2 = (uiObjects.lblDescripcionGenerico().getText().contains(TextList.TEXT_GENERAR_CVU.getText()));
            if (!c1 || !c2)
                throw new AssertionError("Error al cargar pantalla para generar CVU");

            return this;
        } catch (AssertionError e) {
            throw new AssertionError("Error al cargar pantalla de generacion de CVU");
        }
    }

    public GeneracionCvu waitScreenCvuGenerado() {
        try {
            uiObjects.btnFinish().waitToAppear(15);

            boolean c1, c2;
            c1 = (uiObjects.lblTransactionMessage().getText().contains(TextList.TEXT_CVU_GENERADO.getText()));
            if (!c1)
                throw new AssertionError("Error al generar CVU");

            return this;
        } catch (AssertionError e) {
            throw new AssertionError("Error en la generacion de CVU");
        }
    }


    public GeneracionCvu tapBtnGenerar() {
        uiObjects.btnGenerar().tap();
        return this;
    }
}
