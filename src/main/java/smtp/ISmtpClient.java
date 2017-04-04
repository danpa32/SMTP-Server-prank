package smtp;

import mail.Message;

import java.io.IOException;

/**
 * Created by Valentin Finini & Daniel Palumbo on 04.04.2017.
 */
public interface ISmtpClient {
    public void SendMesssage(Message m) throws IOException;
}
