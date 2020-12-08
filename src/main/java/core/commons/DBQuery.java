package core.commons;

import api.apps.android.nw.model.*;
import api.apps.android.nw.NubiWallet;
import core.Util;
import core.commons.apicall.Accounts_API;
import core.database.ConnectionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class DBQuery {

    private static Util util = NubiWallet.util;
    private ConnectionDB connectionDB = new ConnectionDB();
    private static final Logger logger = LogManager.getLogger();

    private static String query;

    /****
     *   Este metodo evalua si el email que se va a utilizar para el registro
     *  ya existe en base de datos
     *
     * @param email: email generado para verificarlo en bd
     *
     *************/
    public boolean existeEmail(String email) throws SQLException {
        boolean aux = false;
        ResultSet rs = null;

        try {
            query = "SELECT email FROM \"user\" where email ='" + email + "'";
            rs = simpleQuery(query, "email", "users");
            boolean existemail = rs.next();
            while (existemail) {
                if (!rs.getString("email").contentEquals("")) {
                    aux = true;
                    break;
                } else {
                    aux = false;
                    break;
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            logger.warn("Error validando existencia de email " + email + " en BD");
            return false;
        } finally {
            connectionDB.closeConecction();
        }
        return aux;
    }

    public User getDestinationUser(String originId) throws Exception {
        String env = NubiWallet.util.getEnv();
        connectionDB.iDataBaseConnection(env, "users");

        String aux = "";
        String fixedDateUsers = " AND \"user\".created_at > '" + NubiWallet.util.get_limit_date_to_query().toString() + " 00:00:00' ";
        ResultSet rs = null;
        User user = new User();
        try {
            query = "SELECT * FROM \"user\" WHERE \"user\".email like '" + NubiWallet.util.getEmailPrefix() + "' and id not in ('" + originId + "')" +
            fixedDateUsers + " and \"user\".deleted_at <= '1970-01-01 00:00:00' and length(\"user\".phone_number) < 15 ORDER BY random() LIMIT 1";
            rs = connectionDB.execute(query);
            while (rs.next()) {
                user = setDestinationUserInfoData(user, rs);
                user.setRawPassword(NubiWallet.util.getProperty("credentials", "user.password", "properties"));
                break;
            }

            return user;
        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {
            connectionDB.closeConecction();
        }
    }

    /**
     * devuelve en la tabla user entre los creados por automation y q NO INCLUYA al usuario
     * enviado por parametro
     * @param destinationUserId
     * @return
     * @throws Exception
     */
    public User getOriginUser(String destinationUserId) throws Exception {
        String env = NubiWallet.util.getEnv();
        connectionDB.iDataBaseConnection(env, "users");

        String aux = "";
        ResultSet rs = null;
        User user = new User();
        try {
            query = "SELECT * FROM \"user\" WHERE \"user\".email like '" + NubiWallet.util.getEmailPrefix() + "' and username like '"+ util.getEmailPrefix() +"' and id not in ('" + destinationUserId + "') AND \"user\".created_at > '2020-02-01 00:00:00' ORDER BY random() LIMIT 1";
            rs = connectionDB.execute(query);
            while (rs.next()) {
                user = setDestinationUserInfoData(user, rs);
                user.setRawPassword(NubiWallet.util.getProperty("credentials", "user.password", "properties"));
                break;
            }

            return user;
        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {
            connectionDB.closeConecction();
        }
    }
    /****
     *   devuelve un email ya registrado
     *
     *
     *
     *************/
    public String getUserInfo(String info) throws SQLException {
        String aux = "";
        ResultSet rs = null;

        try {
            query = "SELECT * FROM \"user\" where email like '" + NubiWallet.util.getEmailPrefix() + "' LIMIT 10";
            rs = simpleQuery(query, "id", "users");
            boolean existinfo = rs.next();
            while (existinfo) {
                if (!rs.getString(info).contentEquals("")) {
                    aux = rs.getString(info);
                    break;
                } else {
                    continue;
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            logger.warn("Error obteniendo info: " + info);
        } finally {
            connectionDB.closeConecction();
        }

        return aux;
    }

    /****
     *
     * VALIDATE IF dni EXIST ON BD
     *
     *************/
    public boolean existDni(String dni) throws SQLException {

        boolean aux = false;
        ResultSet rs = null;
        try {

            query = "SELECT dni FROM \"user\" where dni ='" + dni + "'";
            rs = simpleQuery(query, "dni", "users");

            while (rs.next()) {
                if (!rs.getString("dni").contentEquals("")) {
                    aux = true;
                    break;
                } else {
                    aux = false;
                    break;
                }
            }

        } catch (Exception e) {
            logger.info("No existent DNI");
            return false;
        } finally {
            connectionDB.closeConecction();
        }

        return aux;
    }

    /*******
     *  VALIDATE IF USER EXIST ON BD
     ******/
    public boolean existeUsuario(String user) throws SQLException {
        boolean aux = false;
        ResultSet rs = null;
        try {
            query = "SELECT * FROM username_reservation where username ='" + user + "'";
            rs = simpleQuery(query, "username", "registration");
            boolean hayRegistro = rs.next();
            while (hayRegistro) {
                if (!rs.getString("username").contentEquals("")) {
                    aux = true;
                    break;
                } else {
                    aux = false;
                    break;
                }
            }
        } catch (Exception e) {
            logger.info("No existent user");
            return false;
        } finally {
            connectionDB.closeConecction();
        }
        return aux;
    }

    /*****
     *   VALIDATE si el numero telefonico existe en BD
     *****/
    public boolean existPhoneNumber(String phone) throws SQLException {
        boolean aux = false;
        ResultSet rs;
        try {
            query = "SELECT * FROM \"user\" where phone_number ='" + phone + "'";
            rs = simpleQuery(query, "phone_number", "users");

            while (rs.next()) {
                if (!rs.getString("phone_number").contentEquals("")) {
                    aux = true;
                    break;
                } else {
                    aux = false;
                    break;
                }
            }

        } catch (Exception e) {
            return false;
        } finally {
            connectionDB.closeConecction();
        }

        return aux;
    }

    /*******
     *  Obtener codigo de sms dado el numero para registro
     ******/
    public String getCodigoSms(String numero) throws SQLException, IOException {
        String codigoSms = "";
        ResultSet rs = null;
        query = "SELECT * FROM phone_data where phone_number ='" + numero + "'";
        rs = simpleQuery(query, "sms_confirmation_code", "registration");
        try {
            while (rs.next()) {
                if (!rs.getString("sms_confirmation_code").contentEquals("")) {
                    codigoSms = rs.getString("sms_confirmation_code");
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionDB.closeConecction();
        }
        return codigoSms;
    }

    /*******
     *  Obtener codigo de sms dado el user id
     * @param userId: el id del iuario que olvido el pin
     * @return el codigo de sms generado
     ******/
    public String getCodigoSmsOlvidoPin(String userId) throws SQLException, IOException {
        String codigoSms = "";
        ResultSet rs = null;

        query = "SELECT * FROM pin_change_request where user_id ='" + userId + "'";
        rs = simpleQuery(query, "code", "auth");

        try {
            while (rs.next()) {
                if (!rs.getString("code").contentEquals("")) {
                    codigoSms = rs.getString("code");
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionDB.closeConecction();
        }

        return codigoSms;
    }

    /**************
     *  GET ACCES TOKEN dado el mail
     ***********/
    public String getAccesToken(String email) {
        String token = "";
        ResultSet rs = null;

        try {
            query = "SELECT * FROM email_registration WHERE email = '" + email + "' ";
            rs = simpleQuery(query, "email_confirmation_jwt", "registration");

            try {
                boolean aux = rs.next();
                while (aux) {
                    if (!rs.getString("email_confirmation_jwt").contentEquals("")) {
                        token = rs.getString("email_confirmation_jwt");
                        break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionDB.closeConecction();
            }

        } catch (Exception e) {
            logger.error("Unable to execute query to get acces token");
            e.printStackTrace();
        }

        return token;
    }

    /**************
     *   Este metodo retorna el token para el recupero de la clave
     *   retorna
     ***********/
    public String returnPassRecoveryToken(String userId) throws SQLException, IOException {
        String aux = "";
        ResultSet rs = null;
        try {
            query = "SELECT token FROM password_recovery_token where user_id ='" + userId + "' order by created_at desc limit 1";
            rs = this.simpleQuery(query, "token", "auth");

            while (rs.next()) {
                if (!rs.getString("token").contentEquals("")) {
                    aux = rs.getString("token");
                    break;
                }
            }
        } catch (Exception | Error e) {
            logger.error("Error al obtener token de recuperacion de clave");
            e.printStackTrace();
            throw e;
        } finally {
            connectionDB.closeConecction();
        }

        return aux;
    }

    /*******
     *  Ejecuta una consulta en la bd establecida
     *
     * @param query: string de la consulta
     * @param field" campo de la tabla a consultar
     * @param  bd: base de datos a consultar
     *
     *****/
    public ResultSet simpleQuery(String query, String field, String bd) throws SQLException, IOException {

        String username = "";
        ResultSet rs = null;

        try {
            String env = NubiWallet.util.getEnv();
            connectionDB.iDataBaseConnection(env, bd);

            //resultQuery = connectionDB.execute(query, field);
            rs = connectionDB.execute(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    /*******
     *  Exceute update
     *
     * @param query: string de la consultar
     * @param  bd: base de datos a consultar
     *
     *****/
    public int execute_update(String query, String bd) throws SQLException, IOException {
        int rows = 0;

        try {
            String env = NubiWallet.util.getEnv();
            connectionDB.iDataBaseConnection(env, bd);

           rows = connectionDB.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
    /**
     * Ejecuta la actualizacion de la fecha de expiración de una solicitud P2P
     *
     * @param dateForUpdate fecha para reemplazar la fecha actual de expiracion
     * @param transactionID id de la transacción
     * @throws SQLException
     */
    public void updateExpirationDateForP2PRequest(String dateForUpdate, String transactionID) throws SQLException {
        try {
            String env = NubiWallet.util.getEnv();
            String db = NubiWallet.util.getProperty("db", "dbP2p", "properties");

            if (!NubiWallet.util.isStaging()) {
                String query = "UPDATE p2p_requests SET expiration_date = '" + dateForUpdate + "' WHERE transaction_id = '" + transactionID + "'";
                connectionDB.iDataBaseConnection(env, db);
                connectionDB.executeUpdate(query);
            } else {
                logger.info("No se pueden ejecutar UPDATES en staging");
            }
        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {
            connectionDB.closeConecction();
        }
    }

    /**
     * Ejecuta la actualizacion del status de una solicitud P2P
     *
     * @param status        nos sirve para indicar el status al cual queremos cambiar
     * @param transactionID el id de la solicitud p2p a la que queremos updatear
     * @throws SQLException
     */
    public void updateStatusForP2PRequest(String status, String transactionID) throws SQLException {
        try {
            String env = NubiWallet.util.getEnv();
            String db = NubiWallet.util.getProperty("db", "dbP2p", "properties");

            if (!NubiWallet.util.isStaging()) {
                String query = "UPDATE p2p_requests SET status = '" + status + "' WHERE transaction_id = '" + transactionID + "'";
                connectionDB.iDataBaseConnection(env, db);
                connectionDB.executeUpdate(query);
            } else {
                logger.info("No se pueden ejecutar UPDATES en staging");
            }
        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {
            connectionDB.closeConecction();
        }
    }

    /**
     * --------------------  REMOVER ESTE METODO DE ABAJO Y PASARLO A API CALLS -------------------------------------
     */

    /*******
     *  Actualizar el usuario dependiendo del tipo dado
     *
     * @param status: tipo de usuario
     *
     *****/
    public void actualizar_dni_validacion_status(int status, String idUsuario) throws SQLException, IOException, ParseException {
        try {
            //Capturo los properties
            String env = NubiWallet.util.getEnv();
            String db = NubiWallet.util.getProperty("db", "dbDniValidation", "properties");

                String query = "UPDATE user_status SET validation_status = #STATUS# WHERE wallet_user_id = '" + idUsuario + "'";

                // , birth_date = '#BIRTH_DATE#'
                //establecer la conexion
                connectionDB.iDataBaseConnection(env, db);
                int afecterows = 0;

                switch (status) {
                    case 1:
                        // cal.add(Calendar.YEAR, -30); // 30 años para atras.
                        query = query.replace("#STATUS#", "1");
                        //query = query.replace("#BIRTH_DATE#", new java.sql.Date(cal.getTime().getTime()).toString());
                        break;
                    case 2:
                        // cal.add(Calendar.YEAR, -20); // 20 años para atras.
                        query = query.replace("#STATUS#", "2");
                        //query = query.replace("#BIRTH_DATE#", new java.sql.Date(cal.getTime().getTime()).toString());
                        break;
                    case 3:
                        //cal.add(Calendar.YEAR, -10); // 10 años para atras.
                        query = query.replace("#STATUS#", "3");
                        // query = query.replace("#BIRTH_DATE#", new java.sql.Date(cal.getTime().getTime()).toString());
                        break;
                }

                //ejecutar el update
                afecterows = connectionDB.executeUpdate(query);
                System.out.println(afecterows);

        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {
            connectionDB.closeConecction();
        }
    }

    /*******
     *  Actualizar el usuario dependiendo del tipo dado
     *
     * @param tipo: 1 mayor de edad
     *
     *****/
    public void actualizar_fecha_nacimiento(int tipo, String idUsuario) throws SQLException {
        try {
            //Capturo los properties
            String env = NubiWallet.util.getEnv();
            String db = NubiWallet.util.getProperty("db", "dbUsers", "properties");

                String query = "UPDATE \"user\" SET birth_date = '#BIRTH_DATE#' WHERE id = '" + idUsuario + "'";

                //establecer la conexion
                connectionDB.iDataBaseConnection(env, db);
                int afecterows = 0;
                Calendar cal = new GregorianCalendar();

                switch (tipo) {
                    case 1:
                        cal.add(Calendar.YEAR, -30); // 30 años para atras.
                        //query = query.replace("#STATUS#", "1");
                        query = query.replace("#BIRTH_DATE#", new java.sql.Date(cal.getTime().getTime()).toString());
                        break;
                    case 0:
                        cal.add(Calendar.YEAR, -10); // 20 años para atras.
                        query = query.replace("#BIRTH_DATE#", new java.sql.Date(cal.getTime().getTime()).toString());
                        break;
                }

                //ejecutar el update
                afecterows = connectionDB.executeUpdate(query);
                System.out.println(afecterows);

        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {
            connectionDB.closeConecction();
        }
    }

    /*******
     *  Actualizar status de tarjeta prepaga
     *
     * @param tipo: 0 desbloqueada
     *              1 bloqueada
     *
     *****/
    public void update_card_status(int tipo, String idUsuario) throws SQLException {
        try {
            //Get properties
            String env = NubiWallet.util.getEnv();
            String db = NubiWallet.util.getProperty("db", "dbPrepaidCard", "properties");


                String query = "UPDATE prepaid_card SET status = '#STATUS#' WHERE user_id = '" + idUsuario + "'";

                //establecer la conexion
                connectionDB.iDataBaseConnection(env, db);
                int afecterows = 0;

                switch (tipo) {
                    case 0:
                        query = query.replace("#STATUS#", "ACTIVATED");
                        break;
                    case 1:
                        query = query.replace("#STATUS#", "BLOCKED");
                        break;

                }

                //ejecutar el update
                afecterows = connectionDB.executeUpdate(query);
                System.out.println(afecterows);

        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {
            connectionDB.closeConecction();
        }
    }

    // -------------------------------- METODOS PARA MODELO DE USUARIO -------------------------

    private User setUserInfoData(User user, ResultSet resultSet) throws Exception {
        user.setId(resultSet.getString("id"));
        user.setCreatedAt(resultSet.getTimestamp("created_at"));
        user.setDeletedAt(resultSet.getTimestamp("deleted_at"));
        user.setModifiedAt(resultSet.getTimestamp("modified_at"));
        user.setEmail(resultSet.getString("email"));
        user.setDeviceId(resultSet.getString("device_id"));
        user.setPlatform(resultSet.getString("platform"));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setUsername(resultSet.getString("username"));
        user.setCuil(resultSet.getString("cuil"));
        user.setDni(resultSet.getString("dni"));
        user.setSexType(resultSet.getString("sex_type"));
        user.setPpe(resultSet.getBoolean("ppe"));
        user.setTerms(resultSet.getBoolean("terms"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setBirthDate(resultSet.getDate("birth_date"));
        user.setObligedSubject(resultSet.getBoolean("obliged_subject"));
        user.setOccupationId(resultSet.getString("occupation_id"));
        user.setOccupationName(resultSet.getString("occ_name"));
        user.setNationalityId(resultSet.getString("nationality_id"));
        // HOY HAY 1 SOLO en la BD por lo tanto queda hardcodeado
        user.setNationality("Argentina");

        // POR AHORA SE HARCODEA PARA QUE TODOS LOS USUARIOS APAREZCAN CON DNI VALIDADO
        //user.setDniValidated(getDniValidation(user.getId()));
        user.setDniValidated(1);

        // HACER EL LLAMADO PARA OBTENER CADA UNO DE LOS CONTACTOS
        user.setBankContacts(new ArrayList<>());
        user.setNubiContacts(new ArrayList<>());

        //user.setAddresses(getAddresses(resultSet.getString("id")));

        return user;
    }

    private User setDestinationUserInfoData(User user, ResultSet resultSet) throws Exception {
        user.setId(resultSet.getString("id"));
        user.setCreatedAt(resultSet.getTimestamp("created_at"));
        user.setDeletedAt(resultSet.getTimestamp("deleted_at"));
        user.setModifiedAt(resultSet.getTimestamp("modified_at"));
        user.setEmail(resultSet.getString("email"));
        user.setDeviceId(resultSet.getString("device_id"));
        user.setPlatform(resultSet.getString("platform"));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setUsername(resultSet.getString("username"));
        user.setCuil(resultSet.getString("cuil"));
        user.setDni(resultSet.getString("dni"));
        user.setSexType(resultSet.getString("sex_type"));
        user.setPpe(resultSet.getBoolean("ppe"));
        user.setTerms(resultSet.getBoolean("terms"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setBirthDate(resultSet.getDate("birth_date"));
        user.setObligedSubject(resultSet.getBoolean("obliged_subject"));
        user.setOccupationId(resultSet.getString("occupation_id"));
        //user.setOccupationName(resultSet.getString("occ_name"));
        user.setNationalityId(resultSet.getString("nationality_id"));
        // HOY HAY 1 SOLO en la BD por lo tanto queda hardcodeado
        user.setNationality("Argentina");

        // POR AHORA SE HARCODEA PARA QUE TODOS LOS USUARIOS APAREZCAN CON DNI VALIDADO
        //user.setDniValidated(getDniValidation(user.getId()));
        user.setDniValidated(1);

        // HACER EL LLAMADO PARA OBTENER CADA UNO DE LOS CONTACTOS
        user.setBankContacts(new ArrayList<>());
        user.setNubiContacts(new ArrayList<>());

        //user.setAddresses(getAddresses(resultSet.getString("id")));

        return user;
    }

    private User setOriginUserInfoData(User user, ResultSet resultSet) throws Exception {
        user.setId(resultSet.getString("id"));
        user.setCreatedAt(resultSet.getTimestamp("created_at"));
        user.setDeletedAt(resultSet.getTimestamp("deleted_at"));
        user.setModifiedAt(resultSet.getTimestamp("modified_at"));
        user.setEmail(resultSet.getString("email"));
        user.setDeviceId(resultSet.getString("device_id"));
        user.setPlatform(resultSet.getString("platform"));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setUsername(resultSet.getString("username"));
        user.setCuil(resultSet.getString("cuil"));
        user.setDni(resultSet.getString("dni"));
        user.setSexType(resultSet.getString("sex_type"));
        user.setPpe(resultSet.getBoolean("ppe"));
        user.setTerms(resultSet.getBoolean("terms"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setBirthDate(resultSet.getDate("birth_date"));
        user.setObligedSubject(resultSet.getBoolean("obliged_subject"));
        user.setOccupationId(resultSet.getString("occupation_id"));
        //user.setOccupationName(resultSet.getString("occ_name"));
        user.setNationalityId(resultSet.getString("nationality_id"));
        // HOY HAY 1 SOLO en la BD por lo tanto queda hardcodeado
        user.setNationality("Argentina");

        // POR AHORA SE HARCODEA PARA QUE TODOS LOS USUARIOS APAREZCAN CON DNI VALIDADO
        //user.setDniValidated(getDniValidation(user.getId()));
        user.setDniValidated(1);

        // HACER EL LLAMADO PARA OBTENER CADA UNO DE LOS CONTACTOS
        user.setBankContacts(new ArrayList<>());
        user.setNubiContacts(new ArrayList<>());

        //user.setAddresses(getAddresses(resultSet.getString("id")));

        return user;
    }

    public List<Address> getAddresses(String userId) throws Exception {
        String env = NubiWallet.util.getEnv();
        ConnectionDB connectionAddressDB = new ConnectionDB();
        connectionAddressDB.iDataBaseConnection(env, "users");

        List<Address> addresses = new ArrayList<>();

        try {

            String query = "SELECT address.id as addr_id,  address.created_at as addr_created_at, address.deleted_at as addr_deleted_at, " +
                    "address.modified_at as addr_modified_at,  address.street_name as addr_street_name, address.street_no as addr_street_no, " +
                    "address.floor_no as addr_floor_no,  address.apartment_no as addr_apartment_no, address.street_intersection as addr_street_intersection, " +
                    "address.city as addr_city,  address.zipcode as addr_zipcode, address.province_id as addr_province_id, address.country_id as addr_country_id " +
                    "FROM \"user\" " +
                    "LEFT JOIN address ON \"user\".address_id = ( " +
                    "   SELECT a.id FROM address a WHERE a.id = \"user\".address_id ORDER BY modified_at DESC LIMIT 1 " +
                    ") " +
                    "where \"user\".id = '" + userId + "'";

            ResultSet addressResultSet = connectionAddressDB.execute(query);

            while (addressResultSet.next()) {
                Address address = new Address();
                address.setId(addressResultSet.getString("addr_id"));
                address.setCreatedAt(addressResultSet.getTimestamp("addr_created_at"));
                address.setDeletedAt(addressResultSet.getTimestamp("addr_deleted_at"));
                address.setModifiedAt(addressResultSet.getTimestamp("addr_modified_at"));
                address.setStreetName(addressResultSet.getString("addr_street_name"));
                address.setStreetNo(addressResultSet.getString("addr_street_no"));
                address.setFloorNo(addressResultSet.getString("addr_floor_no"));
                address.setApartmentNo(addressResultSet.getString("addr_apartment_no"));
                address.setStreetIntersection(addressResultSet.getString("addr_street_intersection"));
                address.setCity(addressResultSet.getString("addr_city"));
                address.setZipCode(addressResultSet.getString("addr_zipcode"));

                // Obtener el nombre dado el ID de la provincia
                address.setProvinceId(addressResultSet.getString("addr_province_id"));
                // Obtener el nombre dado el ID del PAIS
                address.setCountryId(addressResultSet.getString("addr_country_id"));
                addresses.add(address);
            }
            return addresses;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionAddressDB.closeConecction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<User> getUserFromBD(HashMap<String, String> params, int limit) throws Exception {
        String env = util.getEnv();
        connectionDB.iDataBaseConnection(env, "users");

        String emailPrefix = util.getEmailPrefix();

        // AGREGAR LA FECHA DE UTIL.

        String fixedDateUsers = " AND \"user\".created_at > '" + NubiWallet.util.get_limit_date_to_query().toString() + " 00:00:00' ";

        String whereClause = "WHERE \"user\".email like '" + emailPrefix + "' and \"user\".deleted_at <= '1970-01-01 00:00:00' " + fixedDateUsers;
        String accountAndClause = "";
        String prepaidAndClause = "";
        boolean withSube = false;

        if (params != null) {
            for (Map.Entry param : params.entrySet()) {
                // To search several Ids with email prefix
                if (param.getKey().equals("ids_with_prefix") && param.getValue() != null)
                    whereClause = "WHERE \"user\".id in " + param.getValue() + " AND \"user\".email like '" + emailPrefix + "' ";

                if (param.getKey().equals("not_ids_with_prefix") && param.getValue() != null)
                    whereClause = "WHERE \"user\".id not in " + param.getValue() + " AND \"user\".email like '" + emailPrefix + "' ";

                if (param.getKey().equals("ids_with_nubi_contacts") && param.getValue() != null)
                    whereClause = "WHERE \"user\".id in " + param.getValue() + fixedDateUsers;

                if (param.getKey().equals("id") && param.getValue() != null)
                    whereClause = "WHERE \"user\".id = '" + param.getValue() + "' ";

                if (param.getKey().equals("username") && param.getValue() != null)
                    whereClause = "WHERE \"user\".username = '" + param.getValue() + "' ";

                if (param.getKey().equals("email") && param.getValue() != null)
                    whereClause = "WHERE \"user\".email = '" + param.getValue() + "' ";

                if (param.getKey().equals("origin_user") && param.getValue() != null)
                    whereClause = "WHERE \"user\".email like 'jmeter%' and phone_number not in ('" + param.getValue() + "') ";

                if (param.getKey().equals("cvu") && param.getValue() != null) {
                    if (param.getValue().equals("any"))
                        accountAndClause = "AND \"accounts\".cvu is not null ";
                    else if (param.getValue().equals("no"))
                        accountAndClause = "AND \"accounts\".cvu is null ";
                    else
                        accountAndClause = "AND \"accounts\".cvu = " + param.getValue();
                }

                if (param.getKey().equals("prepaid_kind") && param.getValue() != null) {
                    if (prepaidAndClause == "")
                        prepaidAndClause = " AND kind = '" + param.getValue() + "'::kind ";
                    else
                        prepaidAndClause = prepaidAndClause + " AND kind = '" + param.getValue() + "'::kind ";
                }

                if (param.getKey().equals("prepaid_status") && param.getValue() != null) {
                    if (prepaidAndClause == "")
                        prepaidAndClause = " AND status = '" + param.getValue() + "'::status ";
                    else
                        prepaidAndClause = prepaidAndClause + " AND status = '" + param.getValue() + "'::status ";
                }

                if (param.getKey().equals("no_sube")) {
                    withSube = false;
                }

                if (param.getKey().equals("sube")) {
                    withSube = true;
                }
            }
        }

        try {
            List<User> usuarios = new ArrayList<>();

            ResultSet resultSet = connectionDB.execute(
                    "SELECT *, occupation.name as occ_name " +
                            "FROM \"user\" " +
                            "LEFT JOIN occupation ON occupation.id = \"user\".id " +
                            whereClause +
                            "ORDER BY random() LIMIT " + limit);

            MongoQuery mongo = new MongoQuery();
            while (resultSet.next()) {
                User user = new User();

                user = setUserInfoData(user, resultSet);

                if (user.getEmail().contains("nubi49") || user.getEmail().contains("betotest001")) {
                    user.setRawPassword(NubiWallet.util.getProperty("credentials", "loan.user.password", "properties"));
                    user.setLendingCandidate(true);
                }
                else
                    user.setRawPassword(NubiWallet.util.getProperty("credentials", "user.password", "properties"));

                user.setAcounts(getAccountFromUser(user.getId(), user.getEmail(), user.getRawPassword(), accountAndClause, prepaidAndClause));

                // AGREGAR LOS MOVIMIENTOS DE ESE USUARIO

                    user.setNubiContacts(mongo.getNubiContacts(user.getId()));
                    user.setNubiBankContacts(mongo.getNubiBankContacts(user.getId()));
                    user.setBankContacts(mongo.getBankContacts(user.getId()));

                    user.setIncomingTransactions(mongo.getTransactionsList(Util.INCOMING, user.getId()));
                    user.setOutgoingTransactions(mongo.getTransactionsList(Util.OUTGOING, user.getId()));


                user.setSubeList(getSubeFromUser(user.getId()));

                if (user.getAcounts().size() > 0) {
                    if (!withSube) {
                        if (user.getSubeList().size() == 0) {
                            for (Account userAccount : user.getAcounts()) {
                                //if (userAccount.getBalance() > 0) {
                                    usuarios.add(user);
                                //}
                            }
                        }
                    } else {
                        if(user.getSubeList().size() != 0){
                            usuarios.add(user);
                        }else{
                            /**
                             * agregar la sube al usuario por BE
                             */
                        }
                    }
                }else {
                    usuarios.add(user);
                }
            }
            return usuarios;


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionDB.closeConecction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    public List<Account> getAccountFromUser(String userId, String userEmail, String userPassword, String accountWhereClause, String prepaidAndClause) throws Exception {
        String env = NubiWallet.util.getEnv();
        connectionDB.iDataBaseConnection(env, "accounts");

        try {
            ResultSet accountResultSet = connectionDB.execute("SELECT * FROM \"accounts\" where user_id =  '" + userId + "' " + accountWhereClause);
            User u = new User();
            u.setEmail(userEmail);
            u.setRawPassword(userPassword);
            NubiWallet.usuario = u;

            Accounts_API accounts_api = new Accounts_API();

            JSONArray jsonAccounts = null;
            try {
                jsonAccounts = accounts_api.getUserAccounts();
            } catch (Exception e) {
                System.out.println("NO accounts !!!");
            }
            List<Account> accounts = new ArrayList<>();
            while (accountResultSet.next()) {
                Account account = new Account();
                account.setId(accountResultSet.getString("id"));
                account.setCreatedAt(accountResultSet.getTimestamp("created_at"));
                account.setDeletedAt(accountResultSet.getTimestamp("deleted_at"));
                account.setModifiedAt(accountResultSet.getTimestamp("modified_at"));
                account.setNumber(accountResultSet.getString("number"));
                account.setCurrency(accountResultSet.getString("currency"));
                account.setAlias(accountResultSet.getString("alias"));
                account.setCvu(accountResultSet.getString("cvu"));
                account.setCuil(accountResultSet.getString("cuil"));

                account.setPrepaidCard(getPrefixedPrepaidCard(userId, prepaidAndClause));

                if (jsonAccounts != null) {
                    for (int i = 0, size = jsonAccounts.size(); i < size; i++) {
                        JSONObject jsonAccount = (JSONObject) jsonAccounts.get(i);
                        if (jsonAccount.get("number").toString().equals(account.getNumber())) {
                            // Use APICall to get balance -> Do not add the user if it doesn't have balance.
                            account.setBalance(Integer.parseInt(jsonAccount.get("balance").toString()));
                        }
                    }
                    accounts.add(account);
                }
            }
            return accounts;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionDB.closeConecction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public User getPrefixedUserWithoutPrepaidCard() throws SQLException {
        String env = NubiWallet.util.getEnv();
        connectionDB.iDataBaseConnection(env, "prepaid_card");
        String queryPrepaidCard = "select * from \"prepaid_card\" ";

        try {
            ResultSet prepaidCardResultSet = connectionDB.execute(queryPrepaidCard);
            String ids = "";
            while (prepaidCardResultSet.next()) {
                ids = ids + ",'" + prepaidCardResultSet.getString("user_id") + "'";
            }
            if (!ids.equals("")) {
                ids = ids.substring(1);
                ids = "(" + ids + ")";

                DBQuery dbQueryUsers = new DBQuery();
                dbQueryUsers.connectionDB.iDataBaseConnection(env, "users");

                String usersQuery = "SELECT * from \"user\" WHERE id not in " + ids + " and email like '" + NubiWallet.util.getEmailPrefix() + "' limit 1";

                ResultSet users = dbQueryUsers.connectionDB.execute(usersQuery);
                String id = "";
                while (users.next()) {
                    id = users.getString("id");
                }
                dbQueryUsers.connectionDB.closeConecction();

                if (!id.equals("")) {
                    // Retornar el usuario ???
                    HashMap<String, String> params = new HashMap<>();
                    params.put("id", id);
                    List<User> usersList = getUserFromBD(params, 1);
                    if (usersList != null)
                        return usersList.get(0);
                } else {
                    logger.warn("No hay usuarios SIN TARJETA PREPAGA");
                }
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connectionDB.closeConecction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public PrepaidCard getPrefixedPrepaidCard(String userId, String prepaidClause) throws Exception {
        try {
            String env = NubiWallet.util.getEnv();
            connectionDB.iDataBaseConnection(env, "prepaid_card");
            String querPrepaidCard = "";
            String orderBy = " order by created_at desc";
            if (userId == null)
                querPrepaidCard = "select * from \"prepaid_card\" " + prepaidClause + orderBy;
            else {
                querPrepaidCard = "select * from \"prepaid_card\" WHERE user_id = '" + userId + "' " + prepaidClause;
            }
            ResultSet prepaidCardResultSet = connectionDB.execute(querPrepaidCard);

            String ids = "";
            while (prepaidCardResultSet.next()) {
                ids = ids + ",'" + prepaidCardResultSet.getString("user_id") + "'";
            }
            if (!ids.equals("")) {
                ids = ids.substring(1);
                ids = "(" + ids + ")";

                DBQuery dbQueryUsers = new DBQuery();
                dbQueryUsers.connectionDB.iDataBaseConnection(env, "users");

                String usersQuery = "SELECT * from \"user\" WHERE id in " + ids + " limit 1";

                ResultSet users = dbQueryUsers.connectionDB.execute(usersQuery);
                String id = "";
                while (users.next()) {
                    id = users.getString("id");
                }
                dbQueryUsers.connectionDB.closeConecction();

                if (!id.equals("")) {
                    connectionDB.iDataBaseConnection(env, "prepaid_card");
                    String querPrepaidCardPrefix = "select * from \"prepaid_card\" where user_id = '" + id + "' ";
                    ResultSet prepaidCardResultSetWithPrefix = connectionDB.execute(querPrepaidCardPrefix);

                    PrepaidCard prepaidCard = null;
                    while (prepaidCardResultSetWithPrefix.next()) {
                        prepaidCard = new PrepaidCard();
                        prepaidCard.setId(prepaidCardResultSetWithPrefix.getString("id"));
                        prepaidCard.setUserId(prepaidCardResultSetWithPrefix.getString("user_id"));
                        prepaidCard.setCreatedAt(prepaidCardResultSetWithPrefix.getTimestamp("created_at"));
                        prepaidCard.setDeletedAt(prepaidCardResultSetWithPrefix.getTimestamp("deleted_at"));
                        prepaidCard.setModifiedAt(prepaidCardResultSetWithPrefix.getTimestamp("modified_at"));
                        prepaidCard.setAccountNumber(prepaidCardResultSetWithPrefix.getString("account_number"));
                        prepaidCard.setLastFourDigits(prepaidCardResultSetWithPrefix.getString("last_four_digits"));
                        prepaidCard.setKind(prepaidCardResultSetWithPrefix.getString("kind"));
                        prepaidCard.setStatus(prepaidCardResultSetWithPrefix.getString("status"));
                        prepaidCard.setFirstName(prepaidCardResultSetWithPrefix.getString("first_name"));
                        prepaidCard.setLastName(prepaidCardResultSetWithPrefix.getString("last_name"));
                        prepaidCard.setReference(prepaidCardResultSetWithPrefix.getString("reference"));
                    }
                    return prepaidCard;
                } else {
                    System.out.println("No user with prefix with prepaid card.");
                }
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionDB.closeConecction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public PrepaidCard getPrefixedPrepaidCard(String prepaidClause) throws Exception {
        return getPrefixedPrepaidCard(null, prepaidClause);
    }

    public List<Sube> getSubeFromUser(String userId) throws Exception {
        String env = NubiWallet.util.getEnv();
        connectionDB.iDataBaseConnection(env, "sube");

        try {
            ResultSet subeResultSet = connectionDB.execute("select *, card_charge.id as ch_id, card_charge.created_at as ch_created_at, " +
                    "card_charge.deleted_at as ch_deleted_at, card_charge.modified_at as ch_modified_at, card_charge.account_number as ch_account_number, " +
                    "card_charge.card_number as ch_card_number, card_charge.amount as ch_amount, card_charge.bank_response as ch_bank_response, card_charge.card_alias as ch_card_alias " +
                    " from \"card\" left join \"card_charge\" on (card.user_id = card_charge.user_id and card.card_number = card_charge.card_number) where card.user_id = '" + userId + "' and card.deleted_at <= '1970-01-01 00:00:00'");//
            List<Sube> subes = new ArrayList<>();
            String cardNumber = "";
            while (subeResultSet.next()) {
                ResultSet auxSubeResultSet = subeResultSet;
                Sube sube = new Sube();
                sube.setId(subeResultSet.getString("id"));
                sube.setCreatedAt(subeResultSet.getTimestamp("created_at"));
                sube.setDeletedAt(subeResultSet.getTimestamp("deleted_at"));
                sube.setModifiedAt(subeResultSet.getTimestamp("modified_at"));

                sube.setCardNumber(subeResultSet.getString("card_number"));
                sube.setCardAlias(subeResultSet.getString("card_alias"));
                if (!cardNumber.equals(sube.getCardNumber())) {
                    List<SubeCharge> subesChage = new ArrayList<>();
                    cardNumber = sube.getCardNumber();
                    while (auxSubeResultSet.next()) {
                        if (cardNumber.equals(subeResultSet.getString("ch_card_number"))) {
                            SubeCharge subeCharge = new SubeCharge();
                            subeCharge.setId(auxSubeResultSet.getString("ch_id"));
                            subeCharge.setCreatedAt(auxSubeResultSet.getTimestamp("ch_created_at"));
                            subeCharge.setDeletedAt(auxSubeResultSet.getTimestamp("ch_deleted_at"));
                            subeCharge.setModifiedAt(auxSubeResultSet.getTimestamp("ch_modified_at"));
                            subeCharge.setAccountNumber(auxSubeResultSet.getString("ch_account_number"));
                            subeCharge.setAmount(auxSubeResultSet.getInt("ch_amount"));
                            subeCharge.setBankResponse(auxSubeResultSet.getString("ch_bank_response"));
                            subeCharge.setCardAlias(auxSubeResultSet.getString("ch_card_alias"));
                            subesChage.add(subeCharge);
                        }
                    }
                    sube.setSuberCharges(subesChage);
                }
                subes.add(sube);
            }
            return subes;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionDB.closeConecction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<PrepaidCard> getPrepaidCardFromUser(String userId) throws Exception {
        String env = NubiWallet.util.getEnv();
        connectionDB.iDataBaseConnection(env, "prepaid_card");

        try {
            ResultSet pcResultSet = connectionDB.execute("select * from prepaid_card where prepaid_card.user_id = '" + userId + "' and prepaid_card.deleted_at <= '1969-12-31 21:00:00'");//
            List<PrepaidCard> pcs = new ArrayList<>();
            String cardNumber = "";
            while (pcResultSet.next()) {
                PrepaidCard pc = new PrepaidCard();
                pc.setId(pcResultSet.getString("id"));
                pc.setCreatedAt(pcResultSet.getTimestamp("created_at"));
                pc.setDeletedAt(pcResultSet.getTimestamp("deleted_at"));
                pc.setModifiedAt(pcResultSet.getTimestamp("modified_at"));
                pc.setUserId(pcResultSet.getString("user_id"));
                pc.setAccountNumber(pcResultSet.getString("account_number"));
                pc.setLastFourDigits(pcResultSet.getString("last_four_digits"));
                pc.setExpirationDateTime(pcResultSet.getTimestamp("expiration_datetime"));
                pc.setKind(pcResultSet.getString("kind"));
                pc.setStatus(pcResultSet.getString("status"));
                pc.setFirstName(pcResultSet.getString("first_name"));
                pc.setLastName(pcResultSet.getString("last_name"));
                pc.setReference(pcResultSet.getString("reference"));
                pc.setHas_active_pin(pcResultSet.getBoolean("has_active_pin"));

                pcs.add(pc);
            }
            return pcs;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionDB.closeConecction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getUserEmail(String userId) throws SQLException {
        ResultSet rs = null;
        String email = "";
        try {
            query = "SELECT email FROM \"user\" where id ='" + userId + "'";
            rs = simpleQuery(query, "email", "users");
            while (rs.next()) {
                email = rs.getString("email");
            }
            return email;

        } catch (Exception | Error e) {
            e.printStackTrace();
            logger.warn("No existe usuario con id " + userId + " en BD con prefijo de email " + NubiWallet.util.getEmailPrefix());
            return null;
        } finally {
            connectionDB.closeConecction();
        }
    }

    /**
     * This method return the status of user's prepaid card
     * @param userId
     * @return
     * @throws SQLException
     */
    public String getPrepaidCardstatus(String userId) throws SQLException {
        ResultSet rs = null;
        String status = "";
        try {
            query = "SELECT status FROM prepaid_card where user_id ='" + userId + "'";
            rs = simpleQuery(query, "status", "prepaid_card");
            while (rs.next()) {
                status = rs.getString("status");
            }
            return status;

        } catch (Exception | Error e) {
            e.printStackTrace();
            logger.warn(e);
            return null;
        } finally {
            connectionDB.closeConecction();
        }
    }

    /**
     * This method return the fiel has-active_pin
     * @param userId
     * @return
     * @throws SQLException
     */
    public String getPrepaidCardPinStatus(String userId) throws SQLException {
        ResultSet rs = null;
        String pinStatus = "";
        try {
            query = "SELECT has_active_pin FROM prepaid_card where user_id ='" + userId + "'";
            rs = simpleQuery(query, "has_active_pin", "prepaid_card");
            while (rs.next()) {
                pinStatus = rs.getString("has_active_pin");
            }
            return pinStatus;

        } catch (Exception | Error e) {
            e.printStackTrace();
            logger.warn(e);
            return null;
        } finally {
            connectionDB.closeConecction();
        }
    }

    /*******
            *  Update pin extraction status
     *
             * @param status: status for the pin
     * @param idUsuario: id for the user to be updated
     *
             *****/
    public void update_pin_extraction_status(boolean status, String idUsuario) throws SQLException, IOException, ParseException {
        try {
            //Capturo los properties
            String env = NubiWallet.util.getEnv();
            String db = NubiWallet.util.getProperty("db", "dbPrepaidCard", "properties");

            if (!NubiWallet.util.isStaging()) {
                String query = "UPDATE prepaid_card SET has_active_pin = #STATUS# WHERE user_id = '" + idUsuario + "'";

                //establecer la conexion
                connectionDB.iDataBaseConnection(env, db);
                int afecterows = 0;

                if(status){
                    query = query.replace("#STATUS#", "true");
                }else {
                    query = query.replace("#STATUS#", "false");
                }
                //ejecutar el update
                afecterows = connectionDB.executeUpdate(query);
                System.out.println(afecterows);
            } else {
                logger.info("No se pueden ejecutar UPDATES en staging");
            }
        } catch (Exception | Error e) {
            logger.error(e);
            throw e;
        } finally {
            connectionDB.closeConecction();
        }
    }

   /* public String getUserEmail(String userId) throws SQLException {
        ResultSet rs = null;
        String email = "";
        try {
            query = "SELECT email FROM \"user\" where id ='" + userId + "'";
            rs = simpleQuery(query, "email", "users");
            while (rs.next()) {
                email = rs.getString("email");
            }
            return email;

        } catch (Exception | Error e) {
            e.printStackTrace();
            logger.warn("No existe usuario con id " + userId + " en BD con prefijo de email " + util.getEmailPrefix());
            return null;
        } finally {
            connectionDB.closeConecction();
        }
    }*/


    /**
     * Nos permite obtener el status de validacion del DNI de un usuario
     *
     * @param userId del usuario que queremos consultar
     * @return 0 = no existe el usuario en BD, 1 = Validado, 2= Baneado, 3 Pendiente de Validacion
     * @throws Exception
     */
    public int getDniValidation(String userId) throws Exception {
        String env = NubiWallet.util.getEnv();
        ConnectionDB connectionAddressDB = new ConnectionDB();
        connectionAddressDB.iDataBaseConnection(env, "dni_validation");

        try {

            String query = "SELECT validation_status " +
                    "FROM user_status " +
                    "where wallet_user_id = '" + userId + "' ORDER BY created_at DESC LIMIT 1";

            ResultSet dniResultSet = connectionAddressDB.execute(query);

            int validado = 0;

            while (dniResultSet.next()) {
                validado = dniResultSet.getInt("validation_status");
            }
            return validado;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionAddressDB.closeConecction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    public User getUserWithoutSubeCard(HashMap<String, String> params, int limit) throws Exception {
        try {
            List<User> userlist = getUserFromBD(params, limit);
            if (userlist.size() > 0)
                return userlist.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*******
     *  Update field has _ iavlidate_login
     *
     * @param username: user name
     * @param value: value to be setted,
     * @param field; field to be updated
     *
     *****/
    public void update_has_invalidated_field(String username, boolean value, int field) throws SQLException, IOException, ParseException {
        try {

            ResultSet rs = null;
            String loginStatus = "";
            int afectedRows;

            HashMap<Integer, String> mapping = new HashMap<>();
            mapping.put(0, "has_invalidated_login");
            mapping.put(1, "has_invalidated_pin");

           // if (!NubiWallet.util.isStaging()) {

                    //verify the status
                    query = "SELECT * FROM login_credentials where username ='" + username + "'";
                    rs = simpleQuery(query, mapping.get(field), "auth");
                    while (rs.next()) {
                        loginStatus = rs.getString(mapping.get(field));
                    }

                //LoginStatus == true and User value false, make the update
            //LoginStatus == false and User value true, make the update
                if((loginStatus.toUpperCase().contentEquals("T") && !value) || (loginStatus.toUpperCase().contentEquals("F") && value)) {
                    query = "UPDATE login_credentials SET " +mapping.get(field)+ " = " +value+ " WHERE username = '" + username + "'";
                    afectedRows = execute_update(query, "auth");
                    logger.info("Affected rows: " + afectedRows);
                }else{
                    logger.info("No update needed");
                }

           // }else{
           //         logger.info("No se pueden ejecutar UPDATES en staging");
           // }
        }catch (Exception | Error e) {
                logger.error(e);
                throw e;
       } finally {
                connectionDB.closeConecction();
       }
    }

    public String get_login_status(String username) throws Exception {
        try {
            ResultSet rs = null;
            String loginStatus = "";

            //verify the status
            query = "SELECT * FROM login_credentials where username ='" + username + "'";
            rs = simpleQuery(query, "has_invalidated_login", "auth");
            while (rs.next()) {
                loginStatus = rs.getString("has_invalidated_login");
            }

            return loginStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String get_pin_status(String username) throws Exception {
        try {
            ResultSet rs = null;
            String loginStatus = "";

            //verify the status
            query = "SELECT * FROM login_credentials where username ='" + username + "'";
            rs = simpleQuery(query, "has_invalidated_pin", "auth");
            while (rs.next()) {
                loginStatus = rs.getString("has_invalidated_pin");
            }

            return loginStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
