package smtp;

import mail.Message;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by Valentin Finini & Daniel Palumbo on 04.04.2017.
 */
public class SmtpClient implements ISmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private String smtpServerAddress;
    private int smtpServerPort = 2525;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SmtpClient(String smtpServerAddress, int port) {
        this.smtpServerAddress = smtpServerAddress;
        smtpServerPort = port;
    }

    private void getReceiver(String to) {
        writer.write("RCPT TO:");
        writer.write(to);
        writer.write("\r\n");
        writer.flush();
    }

    public void SendMesssage(Message m) throws IOException {
        LOG.info("Sending message via SMTP");
        Socket socket = new Socket(smtpServerAddress, smtpServerPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        String line = reader.readLine();
        LOG.info(line);
        writer.printf("EHLO loclahost\r\n");
        line = reader.readLine();
        LOG.info(line);
        if (!line.startsWith("250")) {
            throw new IOException("SMTP error: " + line);
        }
        while (line.startsWith("250-")) {
            line = reader.readLine();
            LOG.info(line);
        }

        writer.write("MAIL FROM:");
        writer.write(m.getFrom());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        //for (String to : m.getTo()) {
            getReceiver(m.getTo());
            line = reader.readLine();
            LOG.info(line);
        //}

        /*for (String to : m.getCc()) {
            getReceiver(to);
            line = reader.readLine();
            LOG.info(line);
        }*/

        /*for (String to : m.getBcc()) {
            getReceiver(to);
            line = reader.readLine();
            LOG.info(line);
        }*/

        writer.write("DATA");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
        writer.write("Content-Type: text/plain; charset=\"utf-8\"\r\n");
        writer.write("From: " + m.getFrom() + "\r\n");

        writer.write("To: " + m.getTo());

        writer.write("\r\n");

        writer.write("Subject: " + m.getSubject());
        writer.write("\r\n");
        writer.write("\r\n");
        LOG.info(m.getBody());
        writer.write(m.getBody());
        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");
        writer.flush();
        reader.close();
        writer.close();
        socket.close();
    }
}
