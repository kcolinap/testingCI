package core.commons.apicall.dtos.rapipago;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeAndNameCompany {

    /***
     * PARAMETROS
     */

    private String company_code;
    private String company_description;

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getCompany_description() {
        return company_description.trim();
    }

    public void setCompany_description(String company_description) {
        this.company_description = company_description;
    }

}
