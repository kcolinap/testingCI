package interfaces;

import core.Util;
import core.commons.DBQuery;
import core.managers.DriverManager;

import java.util.Properties;

public interface Registration {

    DriverManager driverManager = new DriverManager();
    Util util = new Util();
    DBQuery db = new DBQuery();
    Properties properties = new Properties();

}
