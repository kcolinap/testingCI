package core.commons.apicall.dtos.p2p;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class P2PRequest {

    /**
     * Parametros obtenidos de una solicitud exitosa
     */
    private String transaction_id;
    private String origin_user_id;
    private String origin_account;
    private String destination_user_id;
    private String destination_account;
    private int amount;
    private String comment;
    private String category;
    private String status;
    private String created_at;
    private String expiration_date;
    private String currency;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOrigin_user_id() {
        return origin_user_id;
    }

    public void setOrigin_user_id(String origin_user_id) {
        this.origin_user_id = origin_user_id;
    }

    public String getOrigin_account() {
        return origin_account;
    }

    public void setOrigin_account(String origin_account) {
        this.origin_account = origin_account;
    }

    public String getDestination_user_id() {
        return destination_user_id;
    }

    public void setDestination_user_id(String destination_user_id) {
        this.destination_user_id = destination_user_id;
    }

    public String getDestination_account() {
        return destination_account;
    }

    public void setDestination_account(String destination_account) {
        this.destination_account = destination_account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
