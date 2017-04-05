package parsers;

import mail.Person;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Daniel on 05.04.2017.
 */
public class VictimParser {
    private ArrayList<Person> victims;

    public VictimParser(File file) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        victims = new ArrayList<Person>();
        String mailAddress = "";
        try {
            while (mailAddress != null) {
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
