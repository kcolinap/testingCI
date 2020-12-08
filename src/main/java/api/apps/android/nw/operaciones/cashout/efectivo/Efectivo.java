package api.apps.android.nw.operaciones.cashout.efectivo;

import api.apps.android.nw.TextList;
import org.junit.Assert;

public class Efectivo {

    public EfectivoObjects objects = new EfectivoObjects();

    public Efectivo waitScreen() {
        try {
            objects.lblSubtitulo().waitToAppear(15);

            Assert.assertEquals(TextList.TEXT_TITLE_CASH_OUT.getText(), objects.lblTitulo().getText());

            if (objects.btnGenerarCodigo().exists()) {
                Assert.assertTrue(objects.btnGenerarCodigo().exists());
                Assert.assertEquals(TextList.TEXT_BUTTON_GENERAR_CODIGO.getText(), objects.btnGenerarCodigo().getText());
                objects.btnGenerarCodigo().tap();
            }

            objects.lblCodigoCashOut().waitToAppear(10);
            Assert.assertTrue(objects.lblCodigoCashOut().exists());
            Assert.assertTrue(objects.lblCodigoCashOut().getText().replace(" ", "").length() == 10);


        } catch (Exception | Error e) {
            throw e;
        }

        return this;
    }
}
