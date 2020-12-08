package api.apps.android.nw.model;

public class BankingData {

    private String ownerName;
    private String cuit;
    private String bankingKeyType;
    private String bankingKey;
    private String bankName;

    public BankingData() {

    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getBankingKeyType() {
        return bankingKeyType;
    }

    public void setBankingKeyType(String bankingKeyType) {
        this.bankingKeyType = bankingKeyType;
    }

    public String getBankingKey() {
        return bankingKey;
    }

    public void setBankingKey(String bankingKey) {
        this.bankingKey = bankingKey;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
