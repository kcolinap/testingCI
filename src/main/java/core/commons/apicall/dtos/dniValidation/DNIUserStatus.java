package core.commons.apicall.dtos.dniValidation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DNIUserStatus {

    /**
     * Parametros del response
     */
    private int validation_status;
    private int attempts_lef;
    private String wallet_user_id;

    public int getValidation_status() {
        return validation_status;
    }

    public void setValidation_status(int validation_status) {
        this.validation_status = validation_status;
    }

    public int getAttempts_lef() {
        return attempts_lef;
    }

    public void setAttempts_lef(int attempts_lef) {
        this.attempts_lef = attempts_lef;
    }

    public String getWallet_user_id() {
        return wallet_user_id;
    }

    public void setWallet_user_id(String wallet_user_id) {
        this.wallet_user_id = wallet_user_id;
    }
}
