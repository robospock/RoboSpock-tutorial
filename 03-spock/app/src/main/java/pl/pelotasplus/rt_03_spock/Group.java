package pl.pelotasplus.rt_03_spock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alek on 18/11/15.
 */
public class Group {
    List<Member> members = new ArrayList<Member>();

    public void addMember(Member member) {
        members.add(member);
    }
}
