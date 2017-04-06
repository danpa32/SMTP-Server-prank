package prank;

import mail.Group;
import mail.Message;
import mail.Person;
import parsers.ConfigParser;
import parsers.MessageParser;
import parsers.VictimParser;
import smtp.SMTPClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

        //Creating groups
        ArrayList<Person> victims = vp.getVictims();

        int numberOfPeopleByGroup = victims.size() / numberOfGroups;
        if(numberOfPeopleByGroup * numberOfGroups != victims.size())
            ++numberOfPeopleByGroup;

        ArrayList<Group> groups = new ArrayList<>();
        while(!victims.isEmpty()) {
            Group groupVictims = new Group();

            for(int i = 0; i < numberOfPeopleByGroup; i++)
                if (!victims.isEmpty())
                    groupVictims.addMember(victims.remove(0));

            groups.add(groupVictims);
        }

        //Reading and testing contents
        MessageParser messageParser = null;

        try {
            messageParser = new MessageParser(new File("config/messages.utf8.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SMTPClient smtpClient = new SMTPClient(configParser.getAddress(), configParser.getPort());
        Random random = new Random();

        for(Group g : groups) {
            List<Message> messages = messageParser.getMessages();

            int count = random.nextInt(messages.size());
            Person first = g.getMembers().get(0);

            //Get the message and update it's sender
            Message message = messages.get(count);
            message.setFrom(first.getAddress());

            for(int i = 1; i < g.getMembers().size(); i++) {
                Person member = g.getMembers().get(i);

                //Update the recipient
                message.setTo(member.getAddress());

                //Send the message
                smtpClient.sendMesssage(message);
            }
        }

        smtpClient.close();
    }
}
