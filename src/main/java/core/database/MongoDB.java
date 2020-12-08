package core.database;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import core.Util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;

public class MongoDB implements Conection {

    //Constants for BD connection
    private static String dbServer;
    private static String port;
    private static String datab;

    public MongoDatabase getDB() {
        return DB;
    }

    public void setDB(MongoDatabase DB) {

        this.DB = DB;
    }

    // DB db;
    private MongoDatabase DB;

    private static SSLSocketFactory getNoopSslSocketFactory() {
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");

            // set up a TrustManager that trusts everything
            sslContext.init(null, new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            System.out.println("Couldn't create SSL Context for MongoDB connection");
            throw new RuntimeException(e);
        }
        return sslContext.getSocketFactory();
    }

    public void iMongoDataBaseConnection(String environment, String database) {
        String userName = "";
        String password = "";
        try {
            if (environment.toLowerCase().contentEquals(Util.TEST)) {
                dbServer = util.getProperty("db", "testHost", "properties");
                port = util.getProperty("db", "portMongo1", "properties");
                switch (database) {
                    case "transaction":
                        userName = util.getProperty("db","devTrMongoUser","properties");
                        password = util.getProperty("db","devTrMongoPassword","properties");

                        datab = util.getProperty("db", "dbTransaction", "properties");
                        break;
                    case "contacts":
                        userName = util.getProperty("db","devCtMongoUser","properties");
                        password = util.getProperty("db","devCtMongoPassword","properties");
                        datab = util.getProperty("db", "mgContactos", "properties");
                        break;
                }
            } else if (environment.toLowerCase().contentEquals(Util.DEV)) {
                dbServer = properties.getProperty("devHost");
                port = properties.getProperty("portMongo1");
                if (database.contentEquals("transaction")) {
                    datab = properties.getProperty("dbTransaction");
                }
            }
            else if (environment.toLowerCase().contentEquals(Util.STAGING)) {
                dbServer = util.getProperty("db", "stgHost", "properties");
                port = util.getProperty("db", "portMongo1", "properties");
                switch (database) {
                    case "transaction":
                        datab = util.getProperty("db", "stgDbTransaction", "properties");
                        break;
                    case "contacts":
                        datab = util.getProperty("db", "stgMgContactos", "properties");
                        break;
                }
            }

            if(!environment.toLowerCase().contentEquals(Util.STAGING)){
                //MongoClient mongoClient = new MongoClient(dbServer, Integer.parseInt(port));
                //setDB(mongoClient.getDatabase(datab));
                // --------------------------------------------
                //userName = util.getProperty("db","devMongoUser","properties");
                //password = util.getProperty("db","devMongoPassword","properties");

                MongoCredential credential;
                credential = MongoCredential.createCredential(userName, datab,
                        (password).toCharArray());

                String clusterEndpoint = dbServer + ":" + port;

                ServerAddress serverAddress = new ServerAddress(clusterEndpoint);

                MongoClient mongoClient = new MongoClient(serverAddress,
                        Collections.singletonList(credential), MongoClientOptions.builder().sslEnabled(true).socketFactory(getNoopSslSocketFactory()).build());

                setDB(mongoClient.getDatabase(datab));


            }
            else{
                userName = util.getProperty("db","stgMongoUser","properties");
                password = util.getProperty("db","stgMongoPassword","properties");

                MongoCredential credential;
                credential = MongoCredential.createCredential(userName, datab,
                        (password).toCharArray());

                String clusterEndpoint = dbServer;

                ServerAddress serverAddress = new ServerAddress(clusterEndpoint);

                MongoClient mongoClient = new MongoClient(serverAddress,
                        Collections.singletonList(credential), MongoClientOptions.builder().sslEnabled(true).socketFactory(getNoopSslSocketFactory()).build());

                setDB(mongoClient.getDatabase(datab));
            }

        } catch (Exception e) {
            util.showTrackTraceError("Error conectadose a mongoDB", e);
        }
    }
}
