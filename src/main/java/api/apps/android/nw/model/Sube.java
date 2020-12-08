package api.apps.android.nw.model;

import java.sql.Timestamp;
import java.util.List;

public class Sube {

    private String id;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp modifiedAt;
    private String cardNumber;
    private String cardAlias;
    List<SubeCharge> suberCharges;

    public Sube() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardAlias() {
        return cardAlias;
    }

    public void setCardAlias(String cardAlias) {
        this.cardAlias = cardAlias;
    }

    public List<SubeCharge> getSuberCharges() {
        return suberCharges;
    }

    public void setSuberCharges(List<SubeCharge> suberCharges) {
        this.suberCharges = suberCharges;
    }
}
