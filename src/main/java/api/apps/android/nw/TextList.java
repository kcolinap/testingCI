package api.apps.android.nw;

import core.Util;
import core.managers.DriverManager;

import java.io.File;
import java.util.Properties;

public enum TextList {
    TEXT_PENDIENTES,
    TEXT_SOLICITUDES,
    TEXT__CREATE_ACCOUNT,
    TEXT_NOT_VALID_EMAIL,
    TEXT_ALREADY_REGISTERED_1,
    TEXT_DISCLAIMER_EXTRACCION_RECHAZADA,
    TEXT_DISCLAIMER_TRANSACCION_DEVUELTA,
    TEXT_DISCLAIMER_TRANSACCION_CANCELADA,
    TEXT_TITULO_MONTO_NETO,
    TEXT_TITULO_TARJETA_PREPAGA,
    TEXT_CAMBIO_PIN_EXITOSO,
    TEXT_ICONO_PAGO_SERVICIOS,
    TEXT_PROCESANDO,
    TEXT_ESPERA_UNOS_SEGUNDOS_MAS,
    TEXT_NOS_ESTA_TOMANDO_MAS_TIEMPO,
    TEXT_NO_NOS_DAMOS_POR_VENCIDO,
    TEXT_SELECCIONA_SERVICIO,
    TEXT_DESCARGAR_COMPROBANTE,
    TEXT_FINALIZAR,
    TEXT_CONFIRMACION_PAGO,
    TEXT_TRANSACCION_FALLIDA,
    TEXT_ALGO_SALIO_MAL,
    TEXT_TUVIMOS_UN_PROBLEMA,
    TEXT_CARGASTE_SUBE,
    TEXT_DETALLE_TITLE,
    TEXT_NUMERO_TARJETA_SUBE,
    TEXT_NUMERO_TRANSACCION,
    TEXT_DATE_LABEL,
    TEXT_HOUR_LABEL,
    TEXT_CATEGORIES_LABEL,
    TEXT_CATEGORY_LABEL,
    TEXT_CATEGORY_NAME_BARES,
    TEXT_CATEGORY_NAME_OTROS,
    TEXT_CATEGORY_NAME_RECARGAS,
    TEXT_SIGUIENTE,
    TEXT_OTHER_CONTACTS,
    TEXT_NUBI_CONTACTS,
    TEXT_OTHER_ACCOUNTS,
    TEXT_NOMBRE,
    TEXT_PHONE,
    TEXT_SUBE_TITLE,
    TEXT_PERSONAL_DATA,
    TEXT_PERSONAL_DATA_TITLE,
    TEXT_SECURITY,
    TEXT_TERMS_CONDITIONS,
    TEXT_PREGUNTAS_FRECUENTES,
    TEXT_CHAT,
    TEXT_CONTACT,
    TEXT_CHANGE_PIN,
    TEXT_FORGOT_PIN,
    TEXT_CHANGE_PASSWORD,
    TEXT_RECARGA_CELULAR,
    TEXT_PAGAR_FACTURAS,
    TEXT_SEND,
    TEXT_REQUEST,
    TEXT_LOAD,
    TEXT_WITHDRAW,
    TEXT_SUBE_UPPERCASE,
    TEXT_LOAD_SUBE,
    TEXT_ADD_SUBE,
    TEXT_CELLPHONE,
    TEXT_CONTACTS_AGENDA_NUBI,
    TEXT_CONTACTS_AGENDA,
    TEXT_CONTACT_LIST_EMPTY,
    TEXT_LOGO_CUENTA_NUBI,
    TEXT_LOGO_CUENTA_BANCARIA,
    TEXT_ENVIO_EXITOSO,
    TEXT_SOLICITUD_EXITOSA,
    TEXT_CAMBIO_PIN,
    TEXT_CAMBIO_CONTRASENIA,
    TEXT_INGRESE_PIN_NUEVO,
    TITLE_CERRAR_SESION,
    TEXT_CERRAR_SESION,
    TEXT_VALIDAR_IDENTIDAD,
    TEXT_TITULO_VALIDAR_IDENTIDAD_CVU,
    TEXT_TITULO_GENERACION_CVU,
    TEXT_GENERAR_CVU,
    TEXT_CVU_GENERADO,
    TEXT_MONTO,
    TEXT_PIN_MASKED,
    TEXT_CASHIN_TITULO,
    TEXTO_CONFIRMACION,
    TEXT_TITLE_DATOS_CUENTA,
    TEXT_ALIAS,
    TEXT_CBU,
    TEXT_CUIT,
    TEXT_RAZON_SCIAL,
    TEXT_CVU,
    TEXT_TITLE_CASH_OUT,
    TEXT_SUBTITLE_CASH_OUT,
    TEXT_BUTTON_GENERAR_CODIGO,
    TEXT_PAY_YOUR_SERVICES_IN_CARD,
    TEXT_PAY_YOUR_BILLS_IN_CARD,
    TEXT_PAY_YOUR_BILLS_TITLE,
    TEXT_PAY_YOUR_BILLS_SUBTITLE,
    TEXT_EXPLANATORY_MESSAGE_FIRST_USE,
    TEXT_SCAN_CODEBAR_BUTTON,
    TEXT_SHOW_AVAILABLE_COMPANIES_BUTTON,
    TEXT_SCAN_YOUR_BILL_TITLE,
    TEXT_SCAN_YOUR_BILL_SUBTITLE,
    TEXT_TYPE_YOUR_BILL_CODE_BUTTON,
    TEXT_AVAILABLE_COMPANIES_TITLE,
    TEXT_MANUAL_CODE_INPUT,
    TEXT_DENIED_CAMERA_ACCESS_MESSAGE,
    TEXT_ACTIVE_CAMERA_ACCESS,
    TEXT_CONTENT_DESC_CAMERA_ICON,
    TEXT_CONFIRMATION_TITLE,
    TEXT_IS_EVERERYTHING_OK,
    TEXT_YOU_ARE_GOING_TO_PAY,
    TEXT_BUTTON_PAY,
    TEXT_OPEN_AMOUNT_MESSAGE,
    TEXT_HIGHER_AMOUNT_WARNING,
    TEXT_LOWER_AMOUNT_WARNING,
    TEXT_SOMETHING_WAS_WRONG,
    TEXT_GENERIC_ERROR_TIPS,
    TEXT_TRY_AGAIN,
    TEXT_BACK_TO_HOME,
    TEXT_TITLE_RECARGA_CELULAR,
    TEXT_SUBTITLE_SELECT_COMPANY,
    TEXT_SUBTITLE_NUMERO_A_RECARGAR,
    TEXT_USAR_MI_TELEFONO,
    TEXT_PHONE_LENGHT,
    TEXT_PHONE_ERROR_15,
    TEXT_PHONE_CODE_INEXISTENTE,
    TEXT_CURRENT_BALANCE,
    TEXT_SUBTITLE_INGRESO_MONTO,
    TEXT_SUBTITLE_OCCUPATION,
    TEXT_INGRESA_PIN,
    TEXT_PIN_INCORRECTO,
    TEXT_LISTO,
    TEXT_SALDO_INSUFICIENTE,
    TEXT_CARGA_SALDO,
    TEXT_DATOS_ADICIONALES,
    TEXT_DIRECCIONACTUAL,
    TEXT_DISCLAIMER_DATOS_ADICIONALES,
    TEXT_DESAMBIGUACION_DIRECCION,
    TEXT_MI_SALDO,
    TEXT_REQUEST_TITLE,
    TEXT_TRANSACC_RECIENTES,
    TEXT_CONTACTOS,
    FULL_PATH_PERMISSION_ALLOW_BUTTON,
    FULL_PATH_PERMISSION_DENY_BUTTON,
    FULL_PATH_OWN_ACCOUNT_SECTIONS,
    LIST_VIEW,
    FULL_PATH_CODEBAR_IMAGE,
    BUTTON_SHEET_PREFIJOS_XPATH,
    COMPANY_NAME,
    BUTTON_ENVIAR,
    BUTTON_SOLICITAR,
    BUTTON_EXTRAER,
    BUTTON_RECARGA_CELULAR,
    BUTTON_SUBE,
    BUTTON_PAGAR_FACTURA,
    BUTTON_CONTACTOS,
    BUTTON_MOVIMIENTOS,
    CONTACT_DATA_VALUE,
    CLASSNAME_IMAGE_BUTTON,
    CLASSNAME_IMAGE_ANIMATION,
    CLASSNAME_VIEW_GROUP,
    CLASSNAME_TEXT_VIEW,
    CLASSNAME_FRAME_LAYOUT,
    CLASSNAME_RADIO_BUTTON,
    ID_CONTACT_DATA_LABEL,
    ID_CONTACT_DATA_VALUE;

    private String text;
    private String PATH = "ui_texts" + File.separator;
    private static Properties properties;

    private void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            properties = Util.propertiesConfiguration(PATH + DriverManager.APK_VERSION);
        }
        text = (String) properties.get(this.toString());
    }

    TextList() {
        loadProperties();
    }

    private String getValue() {
        if (text == null) {
            loadProperties();
        }
        return text;
    }


    public String getText() {
        return getValue();
    }
}