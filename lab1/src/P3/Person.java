package P3;

import java.util.LinkedList;

public class Person {

    private String name;
    private LinkedList<Person> connect = new LinkedList<>();// 与person有直接关系的人

    public Person(String name) {
        this.name = name;// person名字
    }

    public void addRelation(Person person) {
        this.connect.add(person);// 得到person关系
    }

    public String getName() {
        return this.name;// 得到person名字
    }

    public LinkedList<Person> getRelation() {
        return this.connect;// 得到与之有关系的人
    }
}
