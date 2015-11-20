package pl.pelotasplus.rt_02_groovy;

/**
 * Created by alek on 18/11/15.
 */
public class Person {
    private int age;
    private String name;
    private int height;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void bumpAge() {
        age++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    // WARNING -- has side effect, sets age as well!
    public void setHeight(int height) {
        this.height = height;
        this.age = 10;
    }
}
