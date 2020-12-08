package api.apps.android.nw.model.transactions;


import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CASHOUT_WITHDRAW_PC extends TransactionBase{


    private String currency;
    private String destinationDescription;
    private boolean fees;
    private double original_amount;
    private String status;
    private String statusDescription;
    private ArrayList taxes;




    public CASHOUT_WITHDRAW_PC() {
    }


    public void set_withdraw_transaction(Document document){
            set_id(document.get("_id") != null ? document.get("_id").toString() : null);
            setTransactionId(document.get("transaction_id") != null ? document.get("transaction_id").toString() : null);
            setTransactionType(document.get("transaction_type") != null ? document.get("transaction_type").toString() : null);
            setAmount(document.get("amount") != null ? Double.parseDouble(document.get("amount").toString()) : 0);
            setCreatedAt(document.get("created_at") != null ? (Date) document.get("created_at") : null);
            setCurrency(document.get("currency") != null ? document.get("currency").toString() : null);
            setDeletedAt(document.get("deleted_at") != null ? (Date) document.get("deleted_at") : null);
            setDestinationDescription(document.get("destination_description") != null ? document.get("destination_description").toString() : null);
            setFees(document.get("fees") != null && (boolean) document.get("fees"));
            setId(document.get("id") != null ? document.get("id").toString() : null);
            setOriginUserId(document.get("origin_user_id") != null ? document.get("origin_user_id").toString() : null);
            setOriginal_amount(document.get("original_amount") != null ? Double.parseDouble(document.get("original_amount").toString()) : 0);
            setOwnerId(document.get("owner_id") != null ? document.get("owner_id").toString() : null);
            setStatus(document.get("status") != null ? document.get("status").toString() : null);
            setStatusDescription(document.get("status_description") != null ? document.get("status_description").toString() : null);
            setTaxes(document.get("taxes") != null ? (ArrayList) document.get("taxes") : null);
            setUpdatedAt(document.get("updated_at") != null ? (Date) document.get("updated_at") : null);
    }

    public String getCurrency() {
        return currency;
    }

    public String getDestinationDescription() {
        return destinationDescription;
    }

    public void setDestinationDescription(String destinationDescription) {
        this.destinationDescription = destinationDescription;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isFees() {
        return fees;
    }

    public void setFees(boolean fees) {
        this.fees = fees;
    }

    public double getOriginal_amount() {
        return original_amount;
    }

    public void setOriginal_amount(double original_amount) {
        this.original_amount = original_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public ArrayList getTaxes() {
        return taxes;
    }

    public void setTaxes(ArrayList taxes) {
        this.taxes = taxes;
    }
}
