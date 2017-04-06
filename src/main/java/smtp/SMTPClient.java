package smtp;

import mail.Message;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

import static smtp.SMTPProtocol.*;

public class SMTPClient implements ISMTPClient {

    private static final Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    private static int numberOfSentMail = 0;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SMTPClient(String address, int port) throws IOException {
        socket = new Socket(address, port);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        reader.readLine();
        writer.print(EHLO + PRANK_HOSTNAME + LINEEND);
        writer.flush();

        String line = reader.readLine();

        if (!line.startsWith(EHLO_RESPONSE_PREFIX))
            throw new IOException("SMTP error: " + line);

        while(line.startsWith(EHLO_RESPONSE_TOKEN))
            line = reader.readLine();
    }

    public void sendMesssage(Message m) throws IOException {
        LOG.info("Sending message via SMTP from " + m.getFrom() + " to " + m.getTo());

        writer.write(FROM + m.getFrom() + LINEEND);
        writer.flush();
        reader.readLine();

        writer.write(SMTPProtocol.TO + m.getTo() + LINEEND);
        writer.flush();
        reader.readLine();

        writer.write(DATA);
        writer.flush();
        reader.readLine();

        writer.write(CONTENT_TYPE + LINEEND);
        writer.write(DATA_FROM + m.getFrom() + LINEEND);
        writer.write(DATA_TO + m.getTo() + LINEEND);
        writer.write(DATA_SUBJECT + m.getSubject() + LINEEND);

        writer.write(LINEEND + m.getBody() + LINEEND);

        writer.write(END_TRANSACTION);
        writer.flush();
        reader.readLine();

        numberOfSentMail++;
    }

    public void close() throws IOException {
        LOG.info("Sent a total of " + numberOfSentMail + " mails");
        reader.close();

        writer.write(END_CONNECTION);

        writer.close();
        socket.close();
    }
}
