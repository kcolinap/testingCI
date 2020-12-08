package core.commons.apicall.dtos.dniValidation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DNIManualConfirm {

    /**
     * Parametros del response
     */
    private String dni;
    private String last_name;
    private String name;
    private Character sex_type;
    private String birth_date;
    private String nationality;
    private String cuil;

    public String getDni() {
        return dni;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getName() {
        return name;
    }

    public Character getSex_type() {
        return sex_type;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCuil() {
        return cuil;
    }
}
