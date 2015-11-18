package pl.pelotasplus.rt_03_spock;

/**
 * Created by alek on 18/11/15.
 */
public class Member {
    int age;
    String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameAndAge(String name, int age) {
        setName(name);
        setAge(age);
    }
}
