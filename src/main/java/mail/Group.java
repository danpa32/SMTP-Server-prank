package mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentin Finini & Daniel Palumbo on 04.04.2017.
 */
public class Group {
    private final List<Person> members = new ArrayList<>();

    public void addMember(Person p) {
        members.add(p);
    }

    public List<Person> getMembers() {
        return new ArrayList<Person>(members);
    }
}
