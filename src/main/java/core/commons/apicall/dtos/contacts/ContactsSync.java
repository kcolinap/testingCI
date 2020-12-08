package core.commons.apicall.dtos.contacts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactsSync {

    /**
     * Parametros del response
     */
    private List phone_contacts;

    public List getPhone_contacts() {
        return phone_contacts;
    }

    public void setPhone_contacts(List phone_contacts) {
        this.phone_contacts = phone_contacts;
    }

}
