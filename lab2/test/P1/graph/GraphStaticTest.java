/*
 * Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course
 * staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {

    // Testing strategy
    // empty()
    // no inputs, only output is empty graph
    // observe with vertices()

    @Test(expected = AssertionError.class) public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices", Collections.emptySet(), Graph.empty().vertices());
    }

    // test label with Integer
    @Test public void testIntegerLabel() {
        Graph<Integer> graph = Graph.empty();
        final Integer v1 = 1;
        final Integer v2 = 2;
        final Integer v3 = 3;

        final boolean addV1 = graph.add(v1);
        final boolean addV2 = graph.add(v2);

        final int w1 = 2;
        final int w2 = 1;

        final int preW1 = graph.set(v1, v2, w1);
        final int preW2 = graph.set(v3, v1, w2);
        final int initialNumVertices = graph.vertices().size();
        final boolean removedVertex2 = graph.remove(v2);

        assertTrue(addV1);
        assertTrue(addV2);
        assertEquals(0, preW1);
        assertEquals(0, preW2);
        assertEquals(3, initialNumVertices);
        assertTrue(removedVertex2);
        assertEquals(initialNumVertices - 1, graph.vertices().size());
        System.out.println("<Test Integer label>\n" + graph);
    }

    // test label with Character
    @Test public void testCharLabel() {
        Graph<Character> graph = Graph.empty();
        final Character v1 = 'a';
        final Character v2 = 'b';
        final Character v3 = 'c';

        final boolean addV1 = graph.add(v1);
        final boolean addV2 = graph.add(v2);

        final int w1 = 2;
        final int w2 = 1;

        final int preW1 = graph.set(v1, v2, w1);
        final int preW2 = graph.set(v3, v1, w2);
        final int initialNumVertices = graph.vertices().size();
        final boolean removedVertex2 = graph.remove(v2);

        assertTrue(addV1);
        assertTrue(addV2);
        assertEquals(0, preW1);
        assertEquals(0, preW2);
        assertEquals(3, initialNumVertices);
        assertTrue(removedVertex2);
        assertEquals(initialNumVertices - 1, graph.vertices().size());
        System.out.println("<Test character label>\n" + graph.toString());
    }

}
