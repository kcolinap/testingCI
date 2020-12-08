package api.apps.android.nw.model;

import java.sql.Timestamp;

public class PrepaidCard {
    private String id;
    private String userId;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp modifiedAt;
    private String lastFourDigits;
    private Timestamp ExpirationDateTime;
    private String kind;
    private String status;
    private String accountNumber;
    private String firstName;
    private String lastName;
    private String reference;
    private boolean has_active_pin;

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

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public Timestamp getExpirationDateTime() {
        return ExpirationDateTime;
    }

    public void setExpirationDateTime(Timestamp expirationDateTime) {
        ExpirationDateTime = expirationDateTime;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isHas_active_pin() {
        return has_active_pin;
    }

    public void setHas_active_pin(boolean has_active_pin) {
        this.has_active_pin = has_active_pin;
    }
}
