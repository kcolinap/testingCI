package core.commons.apicall.dtos.contacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import core.commons.apicall.dtos.registration.UserData;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBankingContact {

    /**
     * Parametros obtenidos de la creacion de un contacto bancario exitoso
     */
    private String id;
    private String name;
    private List<BankingData> banking_data;
    private String phone_number;
    private String wallet_data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BankingData> getBanking_data() {
        return banking_data;
    }

    public void setBanking_data(List<BankingData> banking_data) {
        this.banking_data = banking_data;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getWallet_data() {
        return wallet_data;
    }

    public void setWallet_data(String wallet_data) {
        this.wallet_data = wallet_data;
    }

}
