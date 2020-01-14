package P2;

import java.util.HashSet;
import java.util.Set;


public class Person {

    private String name;
    private static Set<String> allName = new HashSet<>();// 用于储存所有的名字，判断是否违背名字唯一原则

    /**
     * 新建一个Person对象
     * 
     * @param name 该对象的名字
     */
    public Person(String name) {
        this.name = name;// person名字
        if(Person.allName.contains(name)) {
            System.out.println("Error, everyone should have an unique name!");
            System.exit(-1);
        }
        Person.allName.add(name);
    }

    /**
     * 得到此对象的string名字
     * 
     * @return 返回string名字
     */
    public String getName() {
        return this.name;// 得到person名字
    }
}
