package smtp;

import mail.Message;

import java.io.IOException;

public interface ISMTPClient {
    void sendMesssage(Message m) throws IOException;
}
