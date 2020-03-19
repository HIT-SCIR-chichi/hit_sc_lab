package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class FriendshipGraph {

    void addVertex(Person person) {// 添加顶点
        if (!Allperson.contains(person)) {
            Allperson.add(person);
            if (nameCheck.get(person.getName()) == null) {
                nameCheck.put(person.getName(), person);
            } else {
                System.out.println("Each person should has an unique name!");
                System.exit(0);
            }
        } else {
            System.out.println("该人已经被创建");
        }
    }

    void addEdge(Person person1, Person person2) {// 添加关系边
        person1.addRelation(person2);
    }

    public int getDistance(Person person1, Person person2) {// 得到两人距离
        if (person1 == person2) {// 若同一个人，返回0
            return 0;
        }
        Map<Person, Boolean> visited = new HashMap<>();// 用于判断是否被访问
        Map<Person, Integer> dis = new HashMap<>();// 用于记录距离
        for (Person temp : Allperson) {// 将所有person标记为未被访问
            visited.put(temp, false);
        }
        visited.put(person1, true);
        dis.put(person1, 0);
        Queue<Person> queue = new LinkedBlockingQueue<>();// 先广要用队列来做
        queue.add(person1);// person1入队
        while (!queue.isEmpty()) {// 循环直到队列为空
            Person head = queue.poll();// 得到队首元素，并将其出队
            Person temp = head.getRelation().peek();// 得到与其有关的第一个人
            int i = 0;
            while (temp != null) {// 循环直到无人与head有直接关系
                if (!visited.get(temp)) {// 若temp未被访问
                    if (temp.equals(person2)) {// 若找到person2
                        return dis.get(head) + 1;
                    } else {// 若未找到
                        visited.put(temp, true);
                        dis.put(temp, dis.get(head) + 1);
                        queue.add(temp);// 将当前person入队
                    }
                }
                if (++i < head.getRelation().size()) {// 继续寻找与其有关系的人
                    temp = head.getRelation().get(i);
                } else {
                    break;
                }
            }
        }
        return -1;
    }

    ArrayList<Person> Allperson = new ArrayList<>();// 用于储存所有的person
    Map<String, Person> nameCheck = new HashMap<>();

    public static void main(String[] args) {

        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
         graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross));
        // should print 1
        System.out.println(graph.getDistance(rachel, ben));
        // should print 2
        System.out.println(graph.getDistance(rachel, rachel));
        // should print 0
        System.out.println(graph.getDistance(rachel, kramer));
        // should print -1
    }
}
