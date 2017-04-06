package parsers;

import mail.Message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageParser {

    private static final String DELIM = "===";

    private ArrayList<Message> messages = new ArrayList<>();

    public MessageParser(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String str = "";

        while(str != null) {
            str = br.readLine();

            if(str == null)
                break;

            String subject = str.substring(str.indexOf(':') + 1);
            String content = "";

            while(!str.equals(DELIM)) {
                str = br.readLine();

                if(!str.equals(DELIM))
                    content += str + "\n";
            }

            messages.add(new Message(subject, content));
        }
    }

    public List<Message> getMessages() {
        return messages;
    }
}
