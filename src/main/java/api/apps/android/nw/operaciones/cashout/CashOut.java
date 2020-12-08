package api.apps.android.nw.operaciones.cashout;

import api.apps.android.nw.TextList;
import api.apps.android.nw.operaciones.cashout.efectivo.Efectivo;
import org.junit.Assert;

public class CashOut {

    public Efectivo efectivo = new Efectivo();

    public CashOutObjects objects = new CashOutObjects();

    public CashOut waitScreen() {
        try {
            objects.btnEfectivoCashOut().waitToAppear(15);

            Assert.assertEquals(TextList.TEXT_TITLE_CASH_OUT.getText(), objects.lblTitulo().getText());
            Assert.assertTrue(objects.lblSubtitulo().getText().replace("\n", " ").contains(TextList.TEXT_SUBTITLE_CASH_OUT.getText()));
            Assert.assertTrue(objects.btnEfectivoCashOut().exists());

        } catch (Exception | Error e) {
            throw e;
        }

        return this;
    }

    public Efectivo tapBtnEfectivo() {

        objects.btnEfectivoCashOut().tap();

        return this.efectivo;
    }
}
