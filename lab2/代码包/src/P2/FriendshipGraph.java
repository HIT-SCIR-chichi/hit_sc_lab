package P2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import P1.graph.ConcreteEdgesGraph;

public class FriendshipGraph extends ConcreteEdgesGraph<Person> {

    /**
     * 将一个人加入顶点集，同时会进行是否已经加入过的判断
     * 
     * @param person person to be added
     */
    public void addVertex(Person person) {
        if (this.vertices().contains(person)) {
            System.out.println("The person has been added");
        } else {
            this.add(person);
        }
    }

    /**
     * 将两个人设置为有关系
     * 
     * @param person1 入边
     * @param person2 出边
     */
    public void addEdge(Person person1, Person person2) {
        this.set(person1, person2, 1);
    }

    /**
     * 找到两人的关系长度
     * 
     * @param person1 person1为待寻找点
     * @param person2 person2为目标点
     * @return 返回两者的距离
     */
    public int getDistance(Person person1, Person person2) {
        if (person1 == person2) {// 若同一个人，返回0
            return 0;
        }
        Map<Person, Boolean> visited = new HashMap<>();// 用于判断是否被访问
        Map<Person, Integer> dis = new HashMap<>();// 用于记录距离
        for (Person temp : this.vertices()) {// 将所有person标记为未被访问
            visited.put(temp, false);
        }
        visited.put(person1, true);
        dis.put(person1, 0);
        Queue<Person> queue = new LinkedBlockingQueue<>();// 先广要用队列来做
        queue.add(person1);// person1入队
        while (!queue.isEmpty()) {// 循环直到队列为空
            Person head = queue.poll();// 得到队首元素，并将其出队
            Set<Person> targetSet = this.targets(head).keySet();
            LinkedList<Person> targetList = new LinkedList<>();
            for (Person person : targetSet) {
                targetList.add(person);
            }
            Person temp = targetList.peek();// 得到与其有关的第一个人
            this.targets(head);
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
                if (++i < targetList.size()) {// 继续寻找与其有关系的人
                    temp = targetList.get(i);
                } else {
                    break;
                }
            }
        }
        return -1;
    }

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
