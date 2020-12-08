package core.commons.apicall.dtos.dealer_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import core.commons.apicall.dtos.contacts.BankingData;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DealerApi {

    /**
     * Parametros obtenidos del llamado al dealer api
     */
    private String ipAddress;
    private String terminalData;
    private String phoneNumber;
    private String templatecode;
    private String subject;
    private String message;
    private String data;
    private JSONObject params;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTerminalData() {
        return terminalData;
    }

    public void setTerminalData(String terminalData) {
        this.terminalData = terminalData;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTemplatecode() {
        return templatecode;
    }

    public void setTemplatecode(String templatecode) {
        this.templatecode = templatecode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }
}
