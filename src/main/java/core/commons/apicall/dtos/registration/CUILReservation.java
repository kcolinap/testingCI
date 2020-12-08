package core.commons.apicall.dtos.registration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CUILReservation {

    /**
     * Parametros obtenidos de una reservacion de cuil exitosa
     */
    private String cuil;

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

}
