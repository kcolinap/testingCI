package api.apps.android.nw;

import core.Util;
import core.managers.DriverManager;

import java.io.File;
import java.util.Properties;

public enum SelectorList {
    // Selectors
    HISTORY_LIST,
    HISTORY_TITLE,
    PENDING_TITLE,
    ACCEPT_ACTION,
    CANCEL_ACTION,
    ITEM_EXPIRATION,
    INITIALS,
    PENDING_TRANSANTIONS_LIST,
    RIGHT_OPTION,
    LEFT_OPTION,
    STATUS_TEXT,
    DISCLAIMER,
    VALUE,
    RESEND_BUTTON,
    SEND_SMS_BUTTON,
    DELAYED_MESSAGE_TITLE,
    DELAYED_MESSAGE_TEXT,
    SELECT_INVOICE_TITLE,
    TITLE_PAYMENT,
    GHOST_BUTTON,
    SUBE_ERROR_SCREEN_BACK_HOME_BTN,
    SUBE_ERROR_SCREEN_TITLE,
    SUBE_POPUP_EDIT_ALIAS,
    SUBE_POPUP_DELETE_CARD,
    SUBE_TITLE_POPUP_CARD,
    SUBE_CARD_LIST_POPUP,
    ADD_SUBE_BTN,
    INPUT_CARD_ALIAS_SUBE,
    GENERIC_ICON,
    TITLE_RECHARGE_MOBILE,
    CASHIN_BUTTON,
    SERVICES_PAYMENT_CARD_ICON,
    SERVICES_PAYMENT_CARD_TEXT,
    MOBILE_RECHARGE_CARD_ICON,
    MOBILE_RECHARGE_CARD_TEXT,
    ACTIVATE_CAMERA_PERMISSIONS,
    GENERIC_ERROR_VIEW,
    SECONDARY_BUTTON,
    ERROR_ICON,
    ICON_INVOICE,
    INVOICE_AMOUNT,
    COMPANY_NAME,
    INFORMATION_MESSAGE,
    FEATURE_ICON,
    OPEN_AMOUNT_MESSAGE,
    OPEN_AMOUNT_MESSAGE_COMPANY,
    AMOUNT_OPEN_INPUT,
    PAY_BUTTON,
    AMOUNT,
    CONFIRMATION_MESSAGE,
    CONFIRMATION_MESSAGE_COMPANY,
    MANUAL_CODE_INPUT_BUTTON,
    INVOICE_NUMBER_INPUT,
    CARD_NUMBER_TITLE,
    MANUAL_INPUT_BUTTON,
    SHOW_AVAILABLE_COMPANIES,
    DATE_TIME_DIVIDER,
    SCAN_BAR_CODE,
    SERVICES_PAYMENT_CARD,
    TRANSACTION_DETAIL_TITLE,
    SUBE_CARD_NUMBER_LABEL,
    SUBE_TRANSACTION_NUMBER_LABEL,
    SUBE_TRANSACTION_NUMBER,
    TRANSACTION_TYPE_ICON,
    LBL_TRANSACTION_AMOUNT,
    LBL_TRANSACTION_DESCRIPTION,
    LBL_LAST_TRANSACTION_DATE,
    LBL_TRANSACTION_HOUR,
    LBL_DATE_LABEL,
    LBL_HOUR_LABEL,
    LBL_CATEGORY_LABEL,
    CATEGORY_ICON,
    LBL_CATEGORY_NAME,
    BTN_EDIT_CATEGORY,
    ACCOUNT_OWNER_NAME,
    TRANSACTION_ICON,
    LBL_STATUS_TEXT,
    ACCOUNT_LIST,
    LBL_CASH_IN_LEGEND,
    BTN_CASH_IN_EFECTIVO_RAPIPAGO,
    BTN_CASH_OUT_EFECTIVO_RAPIPAGO,
    BTN_TRANSFERENCIA,
    LBL_NO_CUENTA,
    REFRESH_BUTTON,
    LBL_DESCRIPTION,
    LBL_AMOUNT,
    LBL_TRANSACTION_DATE,
    EDITOR_MENU_SAVE_BUTTON,
    MENU_SAVE,
    FLOATING_ACTION_BUTTON_MOTOROLA,
    MENU_DONE,
    NAME_EDIT,
    FLOATING_ACTION_BUTTON_SAMSUNG,
    BACK_BUTTON,
    CLOSE_BUTTON,
    NEXT_BUTTON,
    LBL_TITLE,
    IMAGE,
    LBL_SUBTITLE,
    LBL_SUBTITLE_2,
    LBL_ERROR_MESSAGE,
    LBL_INPUT_ERROR,
    CIRCLE_BUTTON,
    LBL_MESSAGE,
    LBL_WARNING_MESSAGE,
    INPUT,
    PASSWORD_INPUT,
    INPUT_FIRST_DIGIT,
    INPUT_SECOND_DIGIT,
    INPUT_THIRD_DIGIT,
    INPUT_FOURTH_DIGIT,
    INPUT_PIN_MASK1,
    INPUT_PIN_MASK2,
    INPUT_PIN_MASK3,
    INPUT_PIN_MASK4,
    CARD,
    BUTTON,
    CARD_FREEZE_SWITCH,
    SWITCH_TITLE,
    LBL_SUBE_INPUT_CARD,
    INPUT_SUBE_NUMBER,
    FINISH_BUTTON,
    CARD_CHANGE_CONFIRM_TITLE,
    CARD_CHANGE_CONFIRM_REVIEW_MESSAGE,
    SUBE_CARD_NUMBER,
    SUBE_CARD_CHANGE_CONFIRM_NUMBER_TITLE,
    SUBE_CARD_AMOUNT,
    CONFIRM_CHANGE_BUTTON,
    CARD_NUMBER,
    OPEN_BUTTON,
    REQUEST_TRANSACTION_SWITCH,
    ITEM_TITLE,
    ITEM_VALUE,
    ITEM_AMOUNT,
    ITEM_STATE,
    LEFT_ICON,
    RIGHT_ICON,
    CHECKBOX_TERMS,
    CHECKBOX_PUBLIC,
    CREATE_ACCOUNT_BUTTON,
    WELCOME_MESSAGE,
    CONTINUE_BUTTON,
    PIN_CREATION_TITLE,
    AREA_CODE_INPUT,
    PHONE_INPUT,
    NAME_INPUT,
    LAST_NAME_INPUT,
    DNI_INPUT,
    EMAIL_INPUT,
    FEMALE_OPTION,
    MALE_OPTION,
    SUBTITLE_CUIL,
    VERIFICATION_CODE_INPUT,
    PREFIX_INPUT,
    SECONDARY_MESSAGE,
    ACTION_BUTTON_PRIMARY,
    ACTION_BUTTON_LINK,
    EMAIL,
    SUBTITLE_SMS_CODE,
    ERROR_SUBTITLE,
    RESEND_SMS_CODE_LINK,
    PROFILE_NAME,
    PROFILE_USERNAME,
    CLOSE_SESSION,
    ALPHANUMERIC_VALIDATOR_TITLE,
    NAME,
    USERNAME,
    CUIL,
    PHONE_NUMBER,
    CONTACT_DATA_VALUE,
    EXIT_BUTTON,
    SUPPORT_EMAIL,
    CONTACT_BUTTON,
    TRANSACTION_SWITCH,
    AMOUNT_INPUT,
    LBL_TRANSACTION,
    AMOUNT_MONEY,
    LBL_TRANSFER_DETAIL_TITLE,
    NEXT_BOTTOM,
    ANIMATION_VIEW,
    MENU_FAB,
    ADD_CONTACT_FAB,
    BUTTON_HOME,
    PREPAID_CARD,//virtualPrepaidCard
    STATS,
    PROFILE,
    USER_INPUT,
    LOGIN_BUTTON,
    LINK_REGISTER,
    LINK_REMEMBER_PASSWORD,
    LOGO,
    BALANCE_CARD,
    DASHBOARD_TITLE,
    CURRENT_BALANCE,
    BALANCE_TITLE,
    FREQUENT_CONTACTS_CONTENT,
    FREQUENT_CONTACT_TITLE,
    BALANCE,
    REQUESTS,
    MAIN_REQUEST_TEXT,
    ACCEPT_REQUEST_BUTTON,
    REJECT_REQUEST_BUTTON,
    REQUESTS_TITLE,
    MONEY_REQUEST_TEXT,
    PENDING_REQUEST_MESSAGE,
    SEE_MORE_BUTTON,
    RECENT_TRANSACTIONS_TITLE,
    MAIN_BUTTON,
    GOTO_EMAIL_BUTTON,
    USER_EMAIL,
    DIGITS_VALIDATOR_TITLE,
    UPPERCASE_VALIDATOR_TITLE,
    SPECIAL_CHARACTER_VALIDATOR_TITLE,
    SEPARATOR_TITLE,
    CONTACT_LIST,
    ERROR_IMAGE,
    RETRY_BUTTON,
    CONTACT_TITLE,
    CONTACT_NAME,
    CONTACT_FULL_NAME_INPUT,
    SAVE_CONTACT_BUTTON,
    BANKING_KEY_INPUT,
    ENTER_CURRENT_PIN_LABEL,
    ENTER_CURRENT_PIN_LABEL_NEW_UI,
    TRANSFER_BUTTON,
    GENERATE_BUTTON,
    LBL_GENERIC_DESCRIPTION,
    LBL_TRANSACTION_MESSAGE,
    CASH_IN_TITLE,
    CASHIN_ICON,
    TXT_RAPIPAGO_CODE,
    BUTTON_OWN_ACCOUNT,
    BUTTON_OTHER_ACCOUNT,
    VIEW_GROUP_ALIAS,
    START_BUTTON,
    DATA_TITLE,
    LBL_CODIGO_CASHOUT,
    VIEW_GROUP_WARNING_CASHOUT,
    BUTTON_GENERAR_CODIGO_CASHOUT,
    SUBTITULO_PANTALLA_RECARGA_TELEFONO,
    LINK_USE_MY_PHONE,
    IMG_COMPANY,
    CONFIRM_BUTTON,
    STREET_NAME_INPUT,
    STREET_NUMBER_INPUT,
    FLOOR_NUMBER_INPUT,
    APARTMENT_NUMBER_INPUT,
    ZIP_CODE_INPUT,
    NEIGHBOURHOOD_INPUT,
    PROVINCE_NAME,
    COUNTRY_INPUT,
    BETWEEN_STREET_INPUT,
    STREET_NAME,
    STREET_NUMBER,
    FLOOR_NUMBER,
    STREET_INTERSECTIONS,
    ZIP_CODE,
    CITY,
    PROVINCE,
    SUBTITLE_OCCUPATION,
    CARD_TITLE,
    NAME_TITLE,
    NAME_TEXT,
    CARD_NUMBER_TEXT,
    EXPIRATION_DATA_TITLE,
    EXPIRATION_DATA_TEXT,
    SECURITY_CODE_TITLE,
    SECURITY_CODE_TEXT,
    SEE_CARD_INFO_BUTTON,
    CARD_NUMBER_COPY;

    private String PATH = "ui_ids" + File.separator;
    private static Properties properties;
    private String selector;

    private void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            properties = Util.propertiesConfiguration(PATH + DriverManager.APK_VERSION);
        }
        selector = (String) properties.get(this.toString());
    }

    SelectorList() {
        loadProperties();
    }

    private String getValue() {
        if (selector == null) {
            loadProperties();
        }
        return selector;
    }

    public String getSelector() {
        return null;
    }

    public String getSamsungSelector() {
        return null;//Android.app.contactosAgenda.samsungPackageID() + getValue();
    }

    public String getMotorolaSelector() {
        return null; //Android.app.contactosAgenda.motoPackageID() + getValue();
    }

}