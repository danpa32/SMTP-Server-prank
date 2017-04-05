package prank;

import mail.Group;
import mail.Message;
import mail.Person;
import parsers.ConfigParser;
import parsers.ContentParser;
import parsers.VictimParser;
import smtp.SmtpClient;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Valentin Finini & Daniel Palumbo on 04.04.2017.
 */
public class PrankGenerator {
    public static void main(String... args) throws IOException {

        //Reading and testing configuration file
        ConfigParser configParser;
        try {
            configParser = new ConfigParser(new File("src/main/java/config/config.smtp.txt"));
        } catch (IOException e) {
            System.out.println("Invalid configuration file !");
            return;
        }

        //Reading the list of victims
        VictimParser vp;
        try {
            vp = new VictimParser(new File("src/main/java/config/victims.txt"));
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
            cp = new ContentParser(new File("src/main/java/config/messages.utf8.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Message> emailsToSend = new ArrayList<Message>();

        int count = 0;
        for(Group g : groups) {
            Person first = g.getMembers().get(0);
            for (Person p : g.getMembers()) {
                if (p != g.getMembers().get(0)) {
                    emailsToSend.add(new Message(first.getAddress(), p.getAddress(), cp.getSubjects()[count], cp.getContent()[count]));
                }
            }
            ++count;
        }

        SmtpClient emailSender;
        emailSender = new SmtpClient(configParser.getServer(), configParser.getPort());

        //Sending emails
        for (Message m : emailsToSend) {
            emailSender.SendMesssage(m);
        }
    }
}
