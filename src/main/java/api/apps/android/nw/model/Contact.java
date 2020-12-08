package api.apps.android.nw.model;

import java.util.Date;
import java.util.List;

public class Contact {

    private String _id;
    private String id;
    private Date createdAt;
    private Date deletedAt;
    private Date updatedAt;
    private String name;
    private String phoneNumber;
    private String ownerId;
    private List<WalletData> walletData;
    private List<BankingData> bankingData;

    public Contact() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<WalletData> getWalletData() {
        return walletData;
    }

    public void setWalletData(List<WalletData> walletData) {
        this.walletData = walletData;
    }

    public List<BankingData> getBankingData() {
        return bankingData;
    }

    public void setBankingData(List<BankingData> bankingData) {
        this.bankingData = bankingData;
    }
}
