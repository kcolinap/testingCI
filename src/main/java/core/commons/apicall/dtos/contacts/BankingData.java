package core.commons.apicall.dtos.contacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import core.commons.apicall.dtos.registration.UserData;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankingData {

    /**
     * Parametros obtenidos con la info de contacto despues de la creacion de un contacto bancario exitoso
     */
    private String banking_key;
    private String banking_key_type;
    private String owner_name;
    private String bank_name;
    private String cuit;

    public String getBanking_key() {
        return banking_key;
    }

    public void setBanking_key(String banking_key) {
        this.banking_key = banking_key;
    }

    public String getBanking_key_type() {
        return banking_key_type;
    }

    public void setBanking_key_type(String banking_key_type) {
        this.banking_key_type = banking_key_type;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

}
