package core.commons.apicall.dtos.transactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpiredTransactions {

    /**
     * Parametros obtenidos de transacciones movida a expiradas de forma exitosa
     */
    private int updated_count;
    private int inserted_count;

    public int getUpdated_count() {
        return updated_count;
    }

    public void setUpdated_count(int updated_count) {
        this.updated_count = updated_count;
    }

    public int getInserted_count() {
        return inserted_count;
    }

    public void setInserted_count(int inserted_count) {
        this.inserted_count = inserted_count;
    }

}
