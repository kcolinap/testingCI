package core.commons.apicall.dtos.rapipago;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CellphoneRecharge {

    /***
     * PARAMETROS
     */
    private String codPuesto;
    private String idCliente;
    private List ticket;
    private String barra;
    private String codResul;
    private String idRecarga;
    private String idTrx;

    public List getTicket() {
        return ticket;
    }

    public void setTicket(List ticket) {
        this.ticket = ticket;
    }

    public String getCodPuesto() {
        return codPuesto;
    }

    public void setCodPuesto(String codPuesto) {
        this.codPuesto = codPuesto;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getBarra() {
        return barra;
    }

    public void setBarra(String barra) {
        this.barra = barra;
    }

    public String getCodResul() {
        return codResul;
    }

    public void setCodResul(String codResul) {
        this.codResul = codResul;
    }

    public String getIdRecarga() {
        return idRecarga;
    }

    public void setIdRecarga(String idRecarga) {
        this.idRecarga = idRecarga;
    }

    public String getIdTrx() {
        return idTrx;
    }

    public void setIdTrx(String idTrx) {
        this.idTrx = idTrx;
    }
}
