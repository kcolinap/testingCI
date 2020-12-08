package core.database;

import core.Util;

import java.sql.*;

public class ConnectionDB implements Conection {

    public static Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;

    //Constants for BD connection
    private static String dbServer;
    private static String port;
    private static String user;

    public void iDataBaseConnection(String environment, String db) throws SQLException {
        try {

            environment = environment.toLowerCase();
            String password = "";

            if (environment.contentEquals(Util.TEST)) {
                dbServer = util.getProperty("db", "urlTEST", ".properties");
                port = util.getProperty("db", "port1", ".properties");
                if (db.contentEquals("users")) {
                    db = util.getProperty("db", "dbUsers", ".properties");
                } else if (db.contentEquals("registro")) {
                    db = util.getProperty("db", "dbRegistration", ".properties");
                } else if (db.contentEquals("auth")) {
                    db = util.getProperty("db", "dbAuth", ".properties");
                } else if (db.contentEquals("sube")) {
                    db = util.getProperty("db", "dbSube", ".properties");
                } else if (db.contentEquals("prepaid_card")) {
                    db = util.getProperty("db", "dbPrepaidCard", ".properties");
                } else if (db.contentEquals("accounts")) {
                    db = util.getProperty("db", "dbAccounts", ".properties");
                } else if (db.contentEquals("dni_validation")) {
                    db = util.getProperty("db", "dbDniValidation", ".properties");
                } else if (db.contentEquals("p2p")) {
                    db = util.getProperty("db", "dbP2p", ".properties");
                }


                user = util.getProperty("db", "user", ".properties");

            } else if (environment.contentEquals(Util.DEV)) {
                dbServer = util.getProperty("db", "urlDEV", ".properties");
                port = util.getProperty("db", "port1", ".properties");
                if (db.contentEquals("users")) {
                    db = util.getProperty("db", "dbUsers", ".properties");
                } else if (db.contentEquals("registro")) {
                    db = util.getProperty("db", "dbRegistration", ".properties");
                } else if (db.contentEquals("auth")) {
                    db = util.getProperty("db", "dbAuth", ".properties");
                } else if (db.contentEquals("sube")) {
                    db = util.getProperty("db", "dbSube", ".properties");
                } else if (db.contentEquals("prepaid_card")) {
                    db = util.getProperty("db", "dbPrepaidCard", ".properties");
                } else if (db.contentEquals("accounts")) {
                    db = util.getProperty("db", "dbAccounts", ".properties");
                } else if (db.contentEquals("dni_validation")) {
                    db = util.getProperty("db", "dbDniValidation", ".properties");
                } else if (db.contentEquals("p2p")) {
                    db = util.getProperty("db", "dbP2p", ".properties");
                }

                user = util.getProperty("db", "user", ".properties");

            } else if (environment.contentEquals(Util.STAGE)) {
                dbServer = util.getProperty("db", "urlSTAGE", ".properties");
                port = util.getProperty("db", "port1", ".properties");
                if (db.contentEquals("users")) {
                    db = util.getProperty("db", "dbUsers", ".properties");
                } else if (db.contentEquals("registro")) {
                    db = util.getProperty("db", "dbRegistration", ".properties");
                } else if (db.contentEquals("auth")) {
                    db = util.getProperty("db", "dbAuth", ".properties");
                } else if (db.contentEquals("sube")) {
                    db = util.getProperty("db", "dbSube", ".properties");
                } else if (db.contentEquals("prepaid_card")) {
                    db = util.getProperty("db", "dbPrepaidCard", ".properties");
                } else if (db.contentEquals("accounts")) {
                    db = util.getProperty("db", "dbAccounts", ".properties");
                } else if (db.contentEquals("dni_validation")) {
                    db = util.getProperty("db", "dbDniValidation", ".properties");
                } else if (db.contentEquals("p2p")) {
                    db = util.getProperty("db", "dbP2p", ".properties");
                }

                user = util.getProperty("db", "user", ".properties");

            }
            String baseUrl = "jdbc:postgresql://";
            String urlConexion = baseUrl + dbServer + ":" + port + "/" + db;

            if (environment.contentEquals(Util.STAGING)) {
                dbServer = util.getProperty("db", "staging.common_url", ".properties");
                String endpoint = "";
                switch (db.toUpperCase()) {
                    case "USERS":
                        endpoint = util.getProperty("db", "staging.usersEndpoint", ".properties");
                        break;
                    case "REGISTRATION":
                        endpoint = util.getProperty("db", "staging.registrationEndpoint", ".properties");
                        break;
                    case "AUTH":
                        endpoint = util.getProperty("db", "staging.authEndpoint", ".properties");
                        break;
                    case "SUBE":
                        endpoint = util.getProperty("db", "staging.subeEndpoint", ".properties");
                        break;
                    case "ACCOUNTS":
                        endpoint = util.getProperty("db", "staging.accountsEndpoint", ".properties");
                        break;
                    case "PREPAID_CARD":
                        endpoint = util.getProperty("db", "staging.cardEndpoint", ".properties");
                        break;
                    case "DNI_VALIDATION":
                        endpoint = util.getProperty("db", "staging.dniValidationEndpoint", ".properties");
                        break;
                    case "P2P":
                        endpoint = util.getProperty("db", "staging.p2pEndpoint", ".properties");
                        break;
                    default:
                        break;
                }

                String dbAllEndpoints = util.getProperty("db", "staging.defaultDB", ".properties");
                user = util.getProperty("db", "staging.user", ".properties");
                password = util.getProperty("db", "staging.pass", ".properties");

                    urlConexion = baseUrl + endpoint + dbServer + "/" + dbAllEndpoints; //+ "?readOnly=true"
            }
            connection = DriverManager.getConnection(urlConexion, user, password);
        } catch (SQLException e) {
            e.getMessage();
            throw e;
        }
    }

    public ResultSet execute(String query) throws SQLException {
        stmt = connection.createStatement();
        rs = stmt.executeQuery(query);
        return rs;
    }

    public int executeUpdate(String query) throws SQLException {
        stmt = connection.createStatement();
        return stmt.executeUpdate(query);
    }

    public void closeConecction() throws SQLException {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (connection != null) connection.close();
    }
}
