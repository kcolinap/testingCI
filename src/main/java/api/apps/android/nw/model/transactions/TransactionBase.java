package api.apps.android.nw.model.transactions;


import java.util.Date;

public class TransactionBase {


    private static String _id;
    private static String transactionId;
    private static String transactionType;
    private static double amount;
    private static Date createdAt;
    private static Date deletedAt;
    private static String id;
    private static String originUserId;
    private Date updatedAt;
    private String ownerId;

    public static String get_id() {
        return _id;
    }

    public static void set_id(String _id) {
        TransactionBase._id = _id;
    }

    public static String getTransactionId() {
        return transactionId;
    }

    public static void setTransactionId(String transactionId) {
        TransactionBase.transactionId = transactionId;
    }

    public static String getTransactionType() {
        return transactionType;
    }

    public static void setTransactionType(String transactionType) {
        TransactionBase.transactionType = transactionType;
    }

    public static double getAmount() {
        return amount;
    }

    public static void setAmount(double amount) {
        TransactionBase.amount = amount;
    }

    public static Date getCreatedAt() {
        return createdAt;
    }

    public static void setCreatedAt(Date createdAt) {
        TransactionBase.createdAt = createdAt;
    }

    public static Date getDeletedAt() {
        return deletedAt;
    }

    public static void setDeletedAt(Date deletedAt) {
        TransactionBase.deletedAt = deletedAt;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        TransactionBase.id = id;
    }

    public static String getOriginUserId() {
        return originUserId;
    }

    public static void setOriginUserId(String originUserId) {
        TransactionBase.originUserId = originUserId;
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

    public TransactionBase() {

    }


}
