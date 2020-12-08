package core;

import core.managers.ServerManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Resources {

    public static final String QUEUE = ServerManager.getWorkingDir() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "queue.json";

    public static JSONObject getQueue() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(QUEUE));
        return (JSONObject) obj;
    }

}
