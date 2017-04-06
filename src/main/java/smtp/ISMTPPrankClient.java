package smtp;

import mail.Message;

import java.io.IOException;

public interface ISMTPPrankClient {
    void sendMesssage(Message m) throws IOException;
}
