package core;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;

public class SSHConector {

    private static final String ENTER_KEY = "n";
    private Session session;
    private String userDir = System.getProperty("user.dir");
    private Util util = new Util();

    public void connect(String username, String host, int port)
            throws JSchException, IllegalAccessException {
        if (this.session == null || !this.session.isConnected()) {
            JSch jSch = new JSch();

            jSch.addIdentity(userDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "ssh.pem");

            this.session = jSch.getSession(username, host, port);
            this.session.setConfig("StrictHostKeyChecking", "no");

            this.session.connect();
        } else {
            throw new IllegalAccessException("SSH session already inicialized");
        }
    }


    public final String executeCommand(String command) throws IllegalAccessException, JSchException, IOException {
        if (this.session != null && this.session.isConnected()) {

            ChannelExec channelExec = (ChannelExec) this.session.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            //Execute command
            channelExec.setCommand(command);
            channelExec.connect();

            //Obtain console output
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(ENTER_KEY);
            }

            //Close ssh channel
            channelExec.disconnect();

            //Back text on console
            return builder.toString();
        } else {
            throw new IllegalAccessException("SSH session not initcialized");
        }
    }

    public final void disconnect() {
        this.session.disconnect();
    }

}
