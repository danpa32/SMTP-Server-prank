package smtp;

public abstract class SMTPPrankProtocol {
    public static final String ADDRESS = "localhost";
    public static final int PORT = 2525;

    public static final String EHLO_RESPONSE_PREFIX = "250";
    public static final String EHLO_RESPONSE_TOKEN = EHLO_RESPONSE_PREFIX + "-";

    public static final String CONTENT_TYPE = "Content-Type: text/plain; charset=\"utf-8\"";

    public static final String LINEEND = "\r\n";

    public static final String EHLO = "EHLO ";
    public static final String FROM = "MAIL FROM: ";
    public static final String TO = "RCPT TO: ";
    public static final String DATA = "DATA" + LINEEND;

    public static final String DATA_FROM = "From: ";
    public static final String DATA_TO = "To: ";
    public static final String DATA_SUBJECT = "Subject: ";

    public static final String END_TRANSACTION = "." + LINEEND;

    public static final String END_CONNECTION = "quit" + LINEEND;
}
