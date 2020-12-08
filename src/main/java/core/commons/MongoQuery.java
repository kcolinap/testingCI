package core.commons;

import api.apps.android.nw.model.*;
import api.apps.android.nw.model.transactions.CASHOUT_WITHDRAW_PC;
import api.apps.android.nw.model.transactions.Transaction;
import com.mongodb.BasicDBObject;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import core.Util;
import core.database.MongoDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;

@SuppressWarnings("unchecked")
public class MongoQuery {

    private static Util util = new Util();

    private static final Logger logger = LogManager.getLogger();

    // DB db;
    MongoDatabase db;
    MongoCollection collection;

    public MongoDB mongoDB = new MongoDB();

    /**
     * 1: contactos nubi wallet
     * 2: contactos nubi bancario
     * 3: contactos bancarios
     */
    public User getUserWithContacts(int tipoContacto) throws Exception {
        try {

            //Capturo los properties
            String env = util.getEnv();
            String db;

            String paramKey = "";

            if (!util.isStaging())
                db = util.getProperty("db", "mgContactos", "properties");
            else
                db = util.getProperty("db", "stgMgContactos", "properties");

            //cursor para obtener los elementos
            MongoCursor cursor = null;

            //establecemos la conexion
            mongoDB.iMongoDataBaseConnection(env, db);

            //seteamos la colleccion
            collection = mongoDB.getDB().getCollection("contacts");

            //Validamos el tipo de contactos que queremos mostrar para armar el filtro
            List<String> list = new ArrayList<>();
            switch (tipoContacto) {
                case 1:
                    DistinctIterable<String> iterable = collection.distinct("owner_id",  ne("wallet_data", null), String.class);
                    MongoCursor<String> cursorString = iterable.iterator();
                    while (cursorString.hasNext()) {
                        list.add(cursorString.next());
                    }
//                    cursor = collection.find(and(
//                            ne("wallet_data", null)
//                    ))
//                            .sort(new BasicDBObject("created_at", -1))
//                            .limit(70)
//                            .iterator();
                    paramKey = "ids_with_nubi_contacts";
                    break;
                case 2:
                    cursor = collection.find(and(
                            not(size("banking_data", 0)),
                            ne("wallet_data", null)
                    ))
                            .sort(new BasicDBObject("created_at", -1))
                            .limit(70)
                            .iterator();

                    paramKey = "ids_with_prefix";
                    break;
                case 3:
                    cursor = collection.find(and(
                            not(size("banking_data", 0)),
                            eq("wallet_data", null)
                    ))
                            .sort(new BasicDBObject("created_at", -1))
                            .limit(70)
                            .iterator();

                    paramKey = "ids_with_prefix";
                    break;
            }


            String ids = "";
            while (cursor != null && cursor.hasNext()) {
                Document tempContact = (Document) cursor.next();
                ids = ids + ",'" + tempContact.get("owner_id") + "'";
            }

            for (int i = 0; i < list.size(); i++)
            {
                ids = ids + ",'" + list.get(i) + "'";
            }

            if (ids.length() > 0) {
                DBQuery dbQuery = new DBQuery();
                ids = ids.substring(1);
                ids = "(" + ids + ")";
                HashMap<String, String> params = new HashMap<>();
                params.put(paramKey, ids);

                List<User> users = dbQuery.getUserFromBD(params, 1);
                if (users != null) {
                    // Loop y ver si nubiContacts esta vacio.
                    for (User u : users) {
                        if (tipoContacto == 1)
                            if (u.getNubiContacts().size() > 0)
                                return u;
                            else if (tipoContacto == 2)
                                if (u.getNubiContacts().size() > 0 && u.getBankContacts().size() > 0)
                                    return u;
                                else if (tipoContacto == 3)
                                    if (u.getBankContacts().size() > 0)
                                        return u;
                    }
                }
            }
        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {

        }

        return null;
    }


    /**
     * Validar que el usuario logueado tenga el tipo de contacto solicitado
     *
     * @param idUser:       id del usuario a loguear
     * @param tipoContacto: tipo de contacto que debe estar asociado al usuario
     *                      1: contactos nubi wallet
     *                      2: contactos nubi bancario
     *                      3: contactos bancarios
     * @return boolean
     * @throws IOException
     */
    public boolean UsuarioTieneContactos(String idUser, int tipoContacto) throws IOException {
        boolean aux = false;
        try {

            //Capturo los properties
            String env = util.getEnv();
            String db = util.getProperty("db", "mgContactos", "properties");

            //cursor para obtener los elementos
            MongoCursor cursor = null;

            //establecemos la conexion
            mongoDB.iMongoDataBaseConnection(env, db);

            //seteamos la colleccion
            collection = mongoDB.getDB().getCollection("contacts");

            //Validamos el tipo de contactos que queremos mostrar para armar el filtro
            switch (tipoContacto) {
                case 1:
                    cursor = collection.find(and(
                            eq("owner_id", idUser),
                            ne("wallet_data", null)
                            // eq(size("banking_data", 0))
                    )).iterator();
                    break;
                case 2:
                    cursor = collection.find(and(
                            eq("owner_id", idUser),
                            not(size("banking_data", 0)),
                            ne("wallet_data", null)
                    )).iterator();
                    break;
                case 3:
                    cursor = collection.find(and(
                            eq("owner_id", idUser),
                            not(size("banking_data", 0)),
                            eq("wallet_data", null)
                    )).iterator();
                    break;
            }


            while (cursor.hasNext()) {
                aux = true;
                break;

            }
        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {

        }
        return aux;
    }

    public List<Contact> getNubiContacts(String idUser) throws Exception {

        List<Contact> nubiContacts = new ArrayList();
        try {

            String env = util.getEnv();
            String db = util.getProperty("db", "mgContactos", "properties");

            //establecemos la conexion
            mongoDB.iMongoDataBaseConnection(env, db);

            //seteamos la colleccion
            collection = mongoDB.getDB().getCollection("contacts");

            MongoCursor cursor = collection.find(and(
                    eq("owner_id", idUser),
                    ne("wallet_data", null)
            )).iterator();


            // ARMAR EL CONTACT
            while (cursor.hasNext()) {
                Document tempContact = (Document) cursor.next();

                Contact nubiContact = setContactData(tempContact);


                String email = util.getUserEmail(nubiContact.getWalletData().get(0).getUserId());
                if (email != null ) {
                    nubiContacts.add(nubiContact);
                }
            }

        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {

        }
        return nubiContacts;
    }

    public String getIdUserWithNubiContact() throws Exception{
        String idUser = null;
        try{
            String env = util.getEnv();
            String db = util.getProperty("db", "mgContactos", "properties");

            //establecemos la conexion
            mongoDB.iMongoDataBaseConnection(env, db);


        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }finally {

        }

        return idUser;
    }

    public List<Contact> getNubiBankContacts(String idUser) throws Exception {

        List<Contact> nubiBankContacts = new ArrayList();
        try {

            String env = util.getEnv();
            String db = util.getProperty("db", "mgContactos", "properties");

            //establecemos la conexion
            mongoDB.iMongoDataBaseConnection(env, db);

            //seteamos la colleccion
            collection = mongoDB.getDB().getCollection("contacts");

            MongoCursor cursor = collection.find(and(
                    eq("owner_id", idUser),
                    not(size("banking_data", 0)),
                    ne("wallet_data", null)
            )).iterator();


            // ARMAR EL CONTACT
            while (cursor.hasNext()) {
                Document tempContact = (Document) cursor.next();

                Contact nubiBankContact = setContactData(tempContact);


                String email = util.getUserEmail(nubiBankContact.getWalletData().get(0).getUserId());
                if (email != null && email.contains(util.getEmailPrefixWithoutPercent())) {
                    nubiBankContacts.add(nubiBankContact);
                }
            }

        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {

        }
        return nubiBankContacts;
    }

    public List<Contact> getBankContacts(String idUser) throws Exception {

        List<Contact> bankContacts = new ArrayList();
        try {

            String env = util.getEnv();
            String db = util.getProperty("db", "mgContactos", "properties");

            //establecemos la conexion
            mongoDB.iMongoDataBaseConnection(env, db);

            //seteamos la colleccion
            collection = mongoDB.getDB().getCollection("contacts");

            MongoCursor cursor = collection.find(and(
                    eq("owner_id", idUser),
                    not(size("banking_data", 0)),
                    eq("wallet_data", null)
            )).iterator();

            while (cursor.hasNext()) {
                Document tempContact = (Document) cursor.next();

                Contact bankContact = setContactData(tempContact);
                bankContacts.add(bankContact);
            }

        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {

        }
        return bankContacts;
    }

    private Contact setContactData(Document contactBson) {
        Contact contact = new Contact();
        contact.set_id(contactBson.get("_id").toString());
        contact.setId(contactBson.get("id").toString());
        contact.setName(contactBson.get("name") != null ? contactBson.get("name").toString() : null);
        contact.setPhoneNumber(contactBson.get("phone_number") != null ? contactBson.get("phone_number").toString() : null);
        contact.setOwnerId(contactBson.get("owner_id") != null ? contactBson.get("owner_id").toString() : null);
        contact.setWalletData(getWalletData((Document) contactBson.get("wallet_data")));
        contact.setBankingData(getBankingData((ArrayList) contactBson.get("banking_data")));
        contact.setCreatedAt((Date) contactBson.get("created_at"));
        contact.setUpdatedAt((Date) contactBson.get("updated_at"));
        contact.setDeletedAt((Date) contactBson.get("deleted_at"));
        return contact;
    }

    private List<WalletData> getWalletData(Document walletDataBson) {
        if (walletDataBson != null) {
            List<WalletData> walletDataList = new ArrayList<>();
            WalletData walletData = new WalletData();
            walletData.setUserId(walletDataBson.get("user_id") != null ? walletDataBson.get("user_id").toString() : null);
            walletData.setUsername(walletDataBson.get("username") != null ? walletDataBson.get("username").toString() : null);
            walletData.setName(walletDataBson.get("name") != null ? walletDataBson.get("name").toString() : null);
            if (walletData.getUsername() != null || walletData.getName() != null || walletData.getUserId() != null) {
                walletDataList.add(walletData);
                return walletDataList;
            }
        }
        return null;
    }

    private List<BankingData> getBankingData(ArrayList bankingDataArray) {
        List<BankingData> bankingDataList = new ArrayList<>();

        for (int i = 0; i < bankingDataArray.size(); i++) {
            Document bkData = (Document) bankingDataArray.get(i);
            BankingData bankingData = new BankingData();
            bankingData.setBankingKey(bkData.get("banking_key").toString());
            bankingData.setBankingKeyType(bkData.get("banking_key_type").toString());
            bankingData.setBankName(bkData.get("bank_name").toString());
            bankingData.setCuit(bkData.get("cuit").toString());
            bankingData.setOwnerName(bkData.get("owner_name").toString());
            bankingDataList.add(bankingData);
        }

        return bankingDataList;
    }

    public List<Transaction> getTransactionsList(String type, String userid) throws Exception {

        List<Transaction> outgoingTransactions = new ArrayList();
        try {

            String env = util.getEnv();
            String db = util.getProperty("db", "dbTransaction", "properties");

            //establecemos la conexion
            mongoDB.iMongoDataBaseConnection(env, db);

            //seteamos la colleccion
            collection = mongoDB.getDB().getCollection("transaction");

            MongoCursor cursor = null;
            if (type.equals(Util.OUTGOING)) {
                cursor = collection.find(and(
                        eq("origin_user_id", userid)
                )).sort(descending("created_at")).limit(1).iterator();
            } else if (type.equals(Util.INCOMING)) {
                // eliminar type SUBE_CHARGE que tengan el mismo ID en ambos origin_user_id y destination_user_id.
                cursor = collection.find(and(
                        eq("destination_user_id", userid),
                        ne("origin_user_id", userid)
                )).sort(descending("created_at")).limit(10).iterator();
            } else {
                return null;
            }

            while (cursor.hasNext()) {
                Document tempTransaction = (Document) cursor.next();
                Transaction transaction = setTransactionData(tempTransaction);
                outgoingTransactions.add(transaction);
            }

        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        }
        return outgoingTransactions;
    }

    public void set_last_transaction(String transactionType, String userid){
        String env = util.getEnv();
        String db = util.getProperty("db", "dbTransaction", "properties");

        //establecemos la conexion
        mongoDB.iMongoDataBaseConnection(env, db);

        //seteamos la colleccion
        collection = mongoDB.getDB().getCollection("transaction");

        MongoCursor cursor = null;

        switch (transactionType.toUpperCase()){
            case "EXTRACCION_TP":
                cursor = collection.find(and(
                        eq("origin_user_id", userid),
                        eq("transaction_type", "CASHOUT_WITHDRAW_PC")
                )).sort(descending("created_at")).limit(1).iterator();

                //set withdraw transaction
                while (cursor.hasNext()){
                    Document tempTransaction = (Document) cursor.next();
                    CASHOUT_WITHDRAW_PC cashout_withdraw_pc = new CASHOUT_WITHDRAW_PC();
                    cashout_withdraw_pc.set_withdraw_transaction(tempTransaction);
                }
                break;
        }

    }


    private Transaction setTransactionData(Document TransactionBson) {
        if (TransactionBson != null) {
            Transaction transaction = new Transaction();
            transaction.set_id(TransactionBson.get("_id") != null ? TransactionBson.get("_id").toString() : null);
            transaction.setTransactionId(TransactionBson.get("transaction_id") != null ? TransactionBson.get("transaction_id").toString() : null);
            transaction.setTransactionType(TransactionBson.get("transaction_type") != null ? TransactionBson.get("transaction_type").toString() : null);
            transaction.setAmount(TransactionBson.get("amount") != null ? Integer.parseInt(TransactionBson.get("amount").toString()) : 0);
            transaction.setCategory(TransactionBson.get("category") != null ? TransactionBson.get("category").toString() : null);
            transaction.setComment(TransactionBson.get("comment") != null ? TransactionBson.get("comment").toString() : null);
            transaction.setCreatedAt(TransactionBson.get("created_at") != null ? (Date) TransactionBson.get("created_at") : null);
            transaction.setCurrency(TransactionBson.get("currency") != null ? TransactionBson.get("currency").toString() : null);
            transaction.setDeletedAt(TransactionBson.get("deleted_at") != null ? (Date) TransactionBson.get("deleted_at") : null);
            transaction.setDestinationAccount(TransactionBson.get("destination_account") != null ? TransactionBson.get("destination_account").toString() : null);
            transaction.setDestinationUserId(TransactionBson.get("destination_user_id") != null ? TransactionBson.get("destination_user_id").toString() : null);
            transaction.setId(TransactionBson.get("id") != null ? TransactionBson.get("id").toString() : null);
            transaction.setOriginAccount(TransactionBson.get("origin_account") != null ? TransactionBson.get("origin_account").toString() : null);
            transaction.setOriginUserId(TransactionBson.get("origin_user_id") != null ? TransactionBson.get("origin_user_id").toString() : null);
            transaction.setStatus(TransactionBson.get("status") != null ? TransactionBson.get("status").toString() : null);
            transaction.setUpdatedAt(TransactionBson.get("updated_at") != null ? (Date) TransactionBson.get("updated_at") : null);
            transaction.setOwnerId(TransactionBson.get("owner_id") != null ? TransactionBson.get("owner_id").toString() : null);
            transaction.setDestinationDescription(TransactionBson.get("destination_description") != null ? TransactionBson.get("destination_description").toString() : null);
            transaction.setOriginDescription(TransactionBson.get("origin_description") != null ? TransactionBson.get("origin_description").toString() : null);
            transaction.setExpirationDate(TransactionBson.get("expiration_date") != null ? (Date) TransactionBson.get("expiration_date") : null);
            transaction.setDestinationBankingKey(TransactionBson.get("destination_banking_key") != null ? TransactionBson.get("destination_banking_key").toString() : null);
            transaction.setDestinationContactId(TransactionBson.get("destination_contact_id") != null ? TransactionBson.get("destination_contact_id").toString() : null);
            transaction.setDestinationCuit(TransactionBson.get("destination_cuit") != null ? TransactionBson.get("destination_cuit").toString() : null);
            transaction.setCardNumber(TransactionBson.get("card_number") != null ? TransactionBson.get("card_number").toString() : null);
            transaction.setSubeCardNumber(TransactionBson.get("sube_card_number") != null ? TransactionBson.get("sube_card_number").toString() : null);
            transaction.setSubeTransactionId(TransactionBson.get("sube_transaction_id") != null ? TransactionBson.get("sube_transaction_id").toString() : null);
            transaction.setCompany(TransactionBson.get("company") != null ? TransactionBson.get("company").toString() : null);
            transaction.setOperationNumber(TransactionBson.get("operation_number") != null ? TransactionBson.get("operation_number").toString() : null);
            transaction.setPhoneNumber(TransactionBson.get("phone_number") != null ? TransactionBson.get("phone_number").toString() : null);
            transaction.setSecurityCode(TransactionBson.get("security_code") != null ? TransactionBson.get("security_code").toString() : null);
            transaction.setFees(TransactionBson.get("fess") != null && (boolean) TransactionBson.get("fees"));
            transaction.setTaxes(TransactionBson.get("taxes") != null ? (ArrayList)TransactionBson.get("taxes") : null);
            transaction.setOriginalAmount(TransactionBson.get("original_amount") != null ? Double.parseDouble(TransactionBson.get("original_amount").toString()) : 0);
            transaction.setCard_alias(TransactionBson.get("card_alias") != null ? TransactionBson.get("card_alias").toString() : null);
            transaction.setOperationCode(TransactionBson.get("operation_code") != null ? TransactionBson.get("operation_code").toString() : null);
            transaction.setSubeCardNumber(TransactionBson.get("sube_card_number") != null ? TransactionBson.get("sube_card_number").toString() : null);
            transaction.setSubeTransactionId(TransactionBson.get("sube_transaction_id") != null ? TransactionBson.get("sube_transaction_id").toString() : null);
            return transaction;
        }

        return null;
    }

    public void delete_contacts_by_owner_id(String ownerId) throws Exception {
        try {

            String env = util.getEnv();
            String db = util.getProperty("db", "mgContactos", "properties");

            //establecemos la conexion
            mongoDB.iMongoDataBaseConnection(env, db);

            //seteamos la colleccion
            collection = mongoDB.getDB().getCollection("contacts");

            DeleteResult result = collection.deleteMany(eq("owner_id", ownerId));
            System.out.println("Contactos borrados: "+result.getDeletedCount());


        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {

        }
    }
}
