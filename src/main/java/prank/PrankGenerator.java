package prank;

import mail.Group;
import mail.Message;
import mail.Person;
import parsers.ConfigParser;
import parsers.ContentParser;
import parsers.VictimParser;
import smtp.SMTPPrankClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PrankGenerator {
    public static void main(String... args) throws IOException {

        //Reading and testing configuration file
        ConfigParser configParser;
        try {
            configParser = new ConfigParser(new File("config/config.smtp.txt"));
        } catch (IOException e) {
            System.out.println("Invalid configuration file !");
            return;
        }

        //Reading the list of victims
        VictimParser vp;
        try {
            vp = new VictimParser(new File("config/victims.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Invalid victims file !");
            return;
        }

        //Reading the number of groups
        int numberOfGroups = configParser.getNumberOfGroups();
        System.out.println("Number of groups: " + numberOfGroups);

        //Creating groups
        ArrayList<Person> victims = vp.getVictims();

        int numberOfPeopleByGroup = victims.size() / numberOfGroups;
        if(numberOfPeopleByGroup * numberOfGroups != victims.size()) {
            ++numberOfPeopleByGroup;
        }

        ArrayList<Group> groups = new ArrayList<Group>();
        while(!victims.isEmpty()) {
            Group groupVictims = new Group();
            for(int i = 0; i < numberOfPeopleByGroup; i++) {
                if (!victims.isEmpty()) {
                    groupVictims.addMember(victims.remove(0));
                }
            }
            groups.add(groupVictims);
        }



        //Reading and testing contents
        ContentParser cp = null;

        try {
            cp = new ContentParser(new File("config/messages.utf8.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Message> emailsToSend = new ArrayList<>();

        int count;
        for(Group g : groups) {
            Random r = new Random();
            count = r.nextInt(cp.getContent().length);
            Person first = g.getMembers().get(0);
            for (Person p : g.getMembers()) {
                if (p != g.getMembers().get(0)) {
                    emailsToSend.add(new Message(first.getAddress(), p.getAddress(), cp.getSubjects()[count], cp.getContent()[count]));
                }
            }
        }

        SMTPPrankClient smtpPrankClient = new SMTPPrankClient();

        //Sending emails
        for (Message m : emailsToSend) {
            smtpPrankClient.sendMesssage(m);
        }

        smtpPrankClient.close();
    }
}
