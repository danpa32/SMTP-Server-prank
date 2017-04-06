package parsers;

import mail.Person;

import java.io.*;
import java.util.ArrayList;

public class VictimParser {
    private ArrayList<Person> victims;

    public VictimParser(File file) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        victims = new ArrayList<>();
        String mailAddress = "";

        try {
            while(mailAddress != null) {
                mailAddress = br.readLine();

                if (mailAddress == null)
                    break;

                Person p = new Person(mailAddress);
                victims.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Person> getVictims() {
        return victims;
    }
}
