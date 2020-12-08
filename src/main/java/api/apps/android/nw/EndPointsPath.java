package api.apps.android.nw;

public enum EndPointsPath {

    /**
     * configuracion
     */
    CONTENT_TYPE("application/json"),

    /**
     * REGISTRATION
     */
    REGISTRATION_EMAIL("/registration/email"),
    REGISTRATION_CONFIRM_EMAIL("/registration/confirm/"),
    REGISTRATION_CONFIRMED("/registration/confirmed"),
    REGISTRATION_CUIL("/registration/cuil"),
    REGISTRATION_USERNAME("/registration/username_reservations"),
    REGISTRATION_PHONE_NUMBER("/registration/phone_numbers"),
    REGISTRATION_PHONE_NUMBER_CONFIRM("/registration/phone_numbers/confirm"),
    REGISTRATION_PIN_VALIDATE("/registration/pin/validate"),
    REGISTRATION_COMPLETE("/registration/complete"),

    /**
     * ACCOUNTS ENDPOINTS
     */
    ACCOUNTS_BALANCE("/accounts/balance"),

    /**
     * DEALER API
     */
    URL_CASHOUT_GP("/lmbmock/services/notifications/cashout-gp"),
    URL_CASHOUT_GP_CANCEl("/lmbmock/services/notifications/cashout-gp?cancel=true"),

    /**
     * P2P
     */
    //POST
    URL_P2P_REQUEST("/p2p/requests"),
    URL_P2P_SENDINGS("/p2p/sendings"),

    /**
     * SUBE ENDPOINTS
     */
    URL_SUBE_CHARGE("/sube/charge"),
    URL_DELETE_SUBE("/sube/card"),


    /**
     * AUTH ENDPOINTS
     */

    //POST
    AUTH_LOGIN("/auth/login"),

    //POST
    PIN_VALIDATE("auth/pin/validate"),

    //Post
    RECOVER_PASSWORD("/auth/password/recover"),

    //Patch
    PATCH_RECOVER_PASSWORD("/auth/password/recover"),

    //Post
    CHANGE_PIN("/auth/credentials/pin"),

    //PATCH
    PATCH_RECOVER_PIN("/auth/pin/recover"),


    /**
     * TRANSACTIONS
     */
    EXPIRE_OUTDATED("/transactions/p2p/requests/expire-outdated"),

    /**
     * DNI VALIDATION
     */
    //GET
    DNI_USER_STATUS("/dni-validation/user-status"),
    //PUT
    DNI_VALIDATION_MANUAL_CONFIRM("/dni-validation/manual/confirm/"),


    /****Rapipago api **/
    CELLPHONE_RECHARGE("/rapipago/cellphone/recharge"),
    AVAILABLE_COMPANIES("/rapipago/service-payment/companies"),

    /**
     * CONTACTS ENDPOINTS
     */
    BANKING("/contacts/banking"),
    CONTACTS_SYNC("/contacts/sync"),

    /**
     * USERS
     */
    USERS("/users"),


    /**
     * PREPAID CARD
     */
    //PATH PREPAID CARD UPDATE
    PREPAID_CARD_UPDATE("/prepaid-card");


    private String text;

    EndPointsPath(String text) {

        this.text = text;
    }

    public String getText() {

        return text;
    }
}
