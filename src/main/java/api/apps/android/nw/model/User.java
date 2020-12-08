package api.apps.android.nw.model;

import api.apps.Apps;
import api.apps.android.nw.NubiWallet;
import api.apps.android.nw.model.transactions.Transaction;
import core.Util;
import core.commons.MongoQuery;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class User {

    public String rawPassword;

    private String id;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp modifiedAt;
    public String email;
    private String deviceId;
    private String platform;
    private String name;
    private String lastName;
    private String username;
    private String cuil;
    private String dni;
    private String sexType;
    private boolean ppe;
    private boolean terms;
    private String phoneNumber;
    private Date birthDate;
    private boolean obligedSubject;
    private int dniValidated;

    // This values are from Users table ocupation
    private String occupationId;
    private String occupationName;

    // This is from Users table address
    private List<Address> addresses;

    private List<PrepaidCard> prepaidCardList;

    // With the id that we have we can get the country code and name from country table
    private String nationalityId;
    private String nationality;

    // User_id ambos tienen q tener el prefijo de util.getEmailPrefixWithoutPercent()
    private List<Contact> nubiContacts;
    private List<Contact> nubiBankContacts;
    private List<Contact> bankContacts;

    private List<Account> acounts;

    private List<Sube> subeList;

    private List<Transaction> outgoingTransactions; // Gasta plata de su balance
    private List<Transaction> incomingTransactions; // Recibe plata a su balance

    private boolean lendingCandidate;

    public User() {

    }

    public List<PrepaidCard> getPrepaidCardList() {
        return prepaidCardList;
    }

    public void setPrepaidCardList(List<PrepaidCard> prepaidCardList) {
        this.prepaidCardList = prepaidCardList;
    }

    public boolean isLendingCandidate() {
        return lendingCandidate;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String password) {
        this.rawPassword = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSexType() {
        return sexType;
    }

    public void setSexType(String sexType) {
        this.sexType = sexType;
    }

    public boolean isPpe() {
        return ppe;
    }

    public void setPpe(boolean ppe) {
        this.ppe = ppe;
    }

    public boolean isTerms() {
        return terms;
    }

    public void setTerms(boolean terms) {
        this.terms = terms;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isObligedSubject() {
        return obligedSubject;
    }

    public void setObligedSubject(boolean obligedSubject) {
        this.obligedSubject = obligedSubject;
    }

    public int getDniValidated() {
        return dniValidated;
    }

    public void setDniValidated(int dniValidated) {
        this.dniValidated = dniValidated;
    }

    public String getDniValidationStatus() {
        switch (getDniValidated()) {
            case 0:
                return "NoExisteEnBd";
            case 1:
                return "Validado";
            case 2:
                return "Baneado";
            case 3:
                return "Pendiente";
        }
        return null;
    }

    public String getOccupationId() {
        return occupationId;
    }

    public void setOccupationId(String occupationId) {
        this.occupationId = occupationId;
    }

    public String getOccupationName() {
        return occupationName;
    }

    public void setOccupationName(String occupationName) {
        this.occupationName = occupationName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Contact> getNubiContacts() {
        return nubiContacts;
    }

    public void setNubiContacts(List<Contact> nubiContacts) {
        this.nubiContacts = nubiContacts;
    }

    public List<Contact> getBankContacts() {
        return bankContacts;
    }

    public void setBankContacts(List<Contact> bankContacts) {
        this.bankContacts = bankContacts;
    }

    public List<Contact> getNubiBankContacts() {
        return nubiBankContacts;
    }

    public void setNubiBankContacts(List<Contact> nubiBankContacts) {
        this.nubiBankContacts = nubiBankContacts;
    }

    public List<Account> getAcounts() {
        return acounts;
    }

    public void setAcounts(List<Account> acounts) {
        this.acounts = acounts;
    }

    public List<Transaction> getOutgoingTransactions() {
        return outgoingTransactions;
    }

    public void setOutgoingTransactions(List<Transaction> outgoing) {
        this.outgoingTransactions = outgoing;
    }

    public List<Transaction> getIncomingTransactions() {
        return incomingTransactions;
    }

    public void setIncomingTransactions(List<Transaction> incoming) {
        this.incomingTransactions = incoming;
    }

    public List<Sube> getSubeList() {
        return subeList;
    }

    public void setSubeList(List<Sube> subeList) {
        this.subeList = subeList;
    }

    public boolean getLendingCandidate() { return lendingCandidate; }

    public void setLendingCandidate(boolean lendingCandidate) { this.lendingCandidate = lendingCandidate; }

    public void refreshTransactionsLists() throws Exception {
            this.outgoingTransactions = Apps.mongoQuery.getTransactionsList(Util.OUTGOING, this.getId());
            this.incomingTransactions = Apps.mongoQuery.getTransactionsList(Util.INCOMING, this.getId());
    }

    public void refreshContacts() throws Exception{
        this.nubiContacts = Apps.mongoQuery.getNubiContacts(this.getId());
    }

    public void refreshSubeList()throws Exception{
        this.subeList = Apps.dbQuery.getSubeFromUser(this.getId());
    }


    //refresh the prepaid card available for the current user
    public void refreshPrepaidCardList() throws Exception{
        this.prepaidCardList = Apps.dbQuery.getPrepaidCardFromUser(this.getId());
    }

}
