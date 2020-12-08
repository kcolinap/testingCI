package core.commons.apicall.dtos.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateUser {

    /**
     * Parametros del response
     */
    private String occupation_code;
    private boolean obliged_subject;
    private boolean ppe;
    private String nationality;


    public String getOccupation_code() {
        return occupation_code;
    }

    public void setOccupation_code(String occupation_code) {
        this.occupation_code = occupation_code;
    }

    public boolean isObliged_subject() {
        return obliged_subject;
    }

    public void setObliged_subject(boolean obliged_subject) {
        this.obliged_subject = obliged_subject;
    }

    public boolean isPpe() {
        return ppe;
    }

    public void setPpe(boolean ppe) {
        this.ppe = ppe;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
