package mail;

public class Person {
    private final String mailAddress;

    public Person(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getAddress() {
        return mailAddress;
    }
}
