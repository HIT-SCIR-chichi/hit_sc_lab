package P3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FriendshipGraphTest {

    @Test void testAddVertex() {
        FriendshipGraph graph = new FriendshipGraph();
        Person person = new Person("Person");
        graph.addVertex(person);//判断加入了顶点
        assertTrue(graph.Allperson.contains(person));
        graph.addVertex(person);//判断没有重复加入一个具体的顶点
        assertTrue(graph.Allperson.lastIndexOf(person)==graph.Allperson.indexOf(person));
    }

    @Test void testAddEdge() {
        Person person1 = new Person("Person1");
        Person person2 = new Person("Person2");
        Person person3 = new Person("Person3");
        person1.addRelation(person2);
        assertTrue(person1.getRelation().contains(person2));
        assertFalse(person1.getRelation().contains(person3));
    }

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
        //验证找到距离为最小
        assertEquals(1, graph.getDistance(A, C));
        
        Person D = new Person("d");
        graph.addVertex(D);
        graph.addEdge(C, D);
        graph.addEdge(D, C);
        graph.addEdge(B, D);
        graph.addEdge(D, B);
        graph.addEdge(A, D);
        graph.addEdge(D, A);
        //验证找到距离为最小
        assertEquals(1, graph.getDistance(A, D));
        
        //验证自己到自己的距离为0
        assertEquals(0, graph.getDistance(A, A));
        
        Person E = new Person("e");
        graph.addVertex(E);
        
        //验证没有关系返回-1
        assertEquals(-1, graph.getDistance(A, E));
    }

}
