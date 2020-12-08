package api.apps.android.nw.model.transactions;


import java.util.ArrayList;
import java.util.Date;

public class Transaction {


    private String _id;
    private String transactionId;
    private String transactionType;
    private int amount;
    private double originalAmount;
    private String category;
    private String comment;
    private Date createdAt;
    private String currency;
    private Date deletedAt;
    private String destinationAccount;
    private String destinationUserId;
    private String id;
    private String originAccount;
    private String originUserId;
    private String status;
    private Date updatedAt;
    private String ownerId;
    private String destinationDescription;
    private String originDescription;
    private Date expirationDate;
    private String destinationBankingKey;
    private String destinationContactId;
    private String destinationCuit;
    private String cardNumber;
    private String subeCardNumber;
    private String subeTransactionId;
    private String company;
    private String operationNumber;
    private String phoneNumber;
    private String securityCode;
    private boolean fees;
    private ArrayList taxes;
    private String card_alias;
    private String operationCode;

    public Transaction() {

    }

    public String getCard_alias() {
        return card_alias;
    }

    public void setCard_alias(String card_alias) {
        this.card_alias = card_alias;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public double getOriginalAmount() {
        return originalAmount /= 100;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public boolean isFees() {
        return fees;
    }

    public void setFees(boolean fees) {
        this.fees = fees;
    }

    public ArrayList getTaxes() {
        return taxes;
    }

    public void setTaxes(ArrayList taxes) {
        this.taxes = taxes;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getDestinationUserId() {
        return destinationUserId;
    }

    public void setDestinationUserId(String destinationUserId) {
        this.destinationUserId = destinationUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(String originAccount) {
        this.originAccount = originAccount;
    }

    public String getOriginUserId() {
        return originUserId;
    }

    public void setOriginUserId(String originUserId) {
        this.originUserId = originUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDestinationDescription() {
        return destinationDescription;
    }

    public void setDestinationDescription(String destinationDescription) {
        this.destinationDescription = destinationDescription;
    }

    public String getOriginDescription() {
        return originDescription;
    }

    public void setOriginDescription(String originDescription) {
        this.originDescription = originDescription;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDestinationBankingKey() {
        return destinationBankingKey;
    }

    public void setDestinationBankingKey(String destinationBankingKey) {
        this.destinationBankingKey = destinationBankingKey;
    }

    public String getDestinationContactId() {
        return destinationContactId;
    }

    public void setDestinationContactId(String destinationContactId) {
        this.destinationContactId = destinationContactId;
    }

    public String getDestinationCuit() {
        return destinationCuit;
    }

    public void setDestinationCuit(String destinationCuit) {
        this.destinationCuit = destinationCuit;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSubeCardNumber() {
        return subeCardNumber;
    }

    public void setSubeCardNumber(String subeCardNumber) {
        this.subeCardNumber = subeCardNumber;
    }

    public String getSubeTransactionId() {
        return subeTransactionId;
    }

    public void setSubeTransactionId(String subeTransactionId) {
        this.subeTransactionId = subeTransactionId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(String operationNumber) {
        this.operationNumber = operationNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
