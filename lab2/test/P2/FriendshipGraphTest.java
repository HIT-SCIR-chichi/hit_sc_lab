package P2;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class FriendshipGraphTest {

    //Test strategy
    //判断加入是否成功+判断加入是否重复
    @Test void testAddVertex() {
        FriendshipGraph graph = new FriendshipGraph();
        Person person1 = new Person("Person");
        graph.addVertex(person1);// 判断加入了顶点
        assertTrue(graph.vertices().contains(person1));

        graph.addVertex(person1);// 判断没有重复加入一个具体的顶点
        List<Person> personList = new ArrayList<>();
        for (Person person : graph.vertices()) {
            personList.add(person);
        }
        assertTrue(personList.indexOf(person1) == personList.lastIndexOf(person1));
    }

    //Test strategy
    //判断加入是否成功+加入是否多余
    @Test void testAddEdge() {
        FriendshipGraph graph = new FriendshipGraph();
        Person person1 = new Person("Person1");
        Person person2 = new Person("Person2");
        Person person3 = new Person("Person3");
        graph.addVertex(person3);
        graph.addVertex(person2);
        graph.addVertex(person1);
        graph.addEdge(person1, person2);// 判断有没有加入此边
        assertTrue(graph.targets(person1).containsKey(person2));
        // 判断有没有加入其余的错误边
        assertTrue(graph.targets(person1).size() == 1);
    }

    //Test strategy
    //判断为0，为-1，多条边取最短
    @Test void testGetDistance() {
        FriendshipGraph graph = new FriendshipGraph();
        Person A = new Person("a");
        Person B = new Person("b");
        Person C = new Person("c");
        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addEdge(A, B);
        graph.addEdge(B, A);
        graph.addEdge(B, C);
        graph.addEdge(C, B);
        graph.addEdge(A, C);
        graph.addEdge(C, A);
        // 验证找到距离为最小
        assertEquals(1, graph.getDistance(A, C));

        Person D = new Person("d");
        graph.addVertex(D);
        graph.addEdge(C, D);
        graph.addEdge(D, C);
        graph.addEdge(B, D);
        graph.addEdge(D, B);
        graph.addEdge(A, D);
        graph.addEdge(D, A);
        // 验证找到距离为最小
        assertEquals(1, graph.getDistance(A, D));

        // 验证自己到自己的距离为0
        assertEquals(0, graph.getDistance(A, A));

        Person E = new Person("e");
        graph.addVertex(E);

        // 验证没有关系返回-1
        assertEquals(-1, graph.getDistance(A, E));
    }

}
