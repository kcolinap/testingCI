package core.commons.apicall.dtos.sube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecargaSube {

    /**
     * Parametros del response
     */
    private String transaction_id;
    private String sube_transaction_id;
    private String created_at;
    private String card_alias;
    private String sube_card_number;
    private Integer amount;
    private boolean succes;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getSube_transaction_id() {
        return sube_transaction_id;
    }

    public void setSube_transaction_id(String sube_transaction_id) {
        this.sube_transaction_id = sube_transaction_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCard_alias() {
        return card_alias;
    }

    public void setCard_alias(String card_alias) {
        this.card_alias = card_alias;
    }

    public String getSube_card_number() {
        return sube_card_number;
    }

    public void setSube_card_number(String sube_card_number) {
        this.sube_card_number = sube_card_number;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }
}
