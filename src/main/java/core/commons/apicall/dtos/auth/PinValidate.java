package core.commons.apicall.dtos.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PinValidate {

    /**
     * Parametros del response
     */
    private String pin_token;

    public String getPin_token() {
        return pin_token;
    }

    public void setPin_token(String pin_token) {
        this.pin_token = pin_token;
    }
}
