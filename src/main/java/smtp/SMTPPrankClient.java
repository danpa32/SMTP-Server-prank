package smtp;

import mail.Message;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

import static smtp.SMTPPrankProtocol.*;

public class SMTPPrankClient implements ISMTPPrankClient {

    private static final Logger LOG = Logger.getLogger(SMTPPrankClient.class.getName());

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SMTPPrankClient() throws IOException {
        socket = new Socket(ADDRESS, PORT);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        LOG.info(reader.readLine());
        writer.print(EHLO + ADDRESS + LINEEND);
        writer.flush();

        String line = reader.readLine();
        LOG.info(line);

        if (!line.startsWith(EHLO_RESPONSE_PREFIX))
            throw new IOException("SMTP error: " + line);

        while(line.startsWith(EHLO_RESPONSE_TOKEN)) {
            line = reader.readLine();
            LOG.info(line);
        }
    }

    private void setReceiver(String to) {
        writer.write(SMTPPrankProtocol.TO + to + LINEEND);
        writer.flush();
    }

    public void sendMesssage(Message m) throws IOException {
        LOG.info("Sending message via SMTP");

        writer.write(FROM + m.getFrom() + LINEEND);
        writer.flush();
        LOG.info(reader.readLine());

        setReceiver(m.getTo());
        LOG.info(reader.readLine());

        writer.write(DATA);
        writer.flush();
        LOG.info(reader.readLine());

        writer.write(CONTENT_TYPE + LINEEND);
        writer.write(DATA_FROM + m.getFrom() + LINEEND);
        writer.write(DATA_TO + m.getTo() + LINEEND);
        writer.write(DATA_SUBJECT + m.getSubject() + LINEEND);

        LOG.info(m.getBody());
        writer.write(LINEEND + m.getBody() + LINEEND);

        writer.write(END_TRANSACTION);
        writer.flush();
    }

    public void close() throws IOException {
        reader.close();

        writer.write(END_CONNECTION);

        writer.close();
        socket.close();
    }
}
