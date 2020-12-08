package core.commons.apicall.dtos.registration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsernameReservation {

    /**
     * Parametros obtenidos de una reservacion de username exitosa
     */
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String cuil) {
        this.username = cuil;
    }

}
