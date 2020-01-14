/*
 * Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course
 * staff.
 */
package P1.graph;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();

    @Test(expected = AssertionError.class) public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
    }

    // Testing strategy
    // graph：空+非空
    // vertex：待加边不存在+待加边存在
    // 结果：若返回值为true，顶点数目++且顶点集中包含vertex；否则，顶点集不变且顶点集中包含vertex
    // number of vertices increases by 1 else graph unmodified
    // observe with vertices()
    @Test public void testAdd() {
        Graph<String> graph = emptyInstance();
        final int size0 = graph.vertices().size();
        final String string0 = "string0";
        // 判断空的graph加入一个顶点（覆盖情况：graph为空+vertex待加边不存在+返回值为true，顶点数++）
        assertTrue(graph.add(string0));// 验证空的graph不含string0
        assertTrue(size0 == 0 && graph.vertices().size() == 1);// 验证加入后顶点数为1且加入前为0
        assertTrue(graph.vertices().contains(string0));// 验证加入了该顶点
        // 判断上面的graph再加入string0（覆盖情况：graph非空+待加边存在+返回值为false且顶点集不变且包含string0）
        final String string1 = "string1";
        graph.add(string1);
        assertFalse(graph.add(string0));// 验证返回值为false
        assertTrue(graph.vertices().size() == 2 && graph.vertices().contains(string0));// 验证加入了string0
    }

    // Testing strategy
    // graph：空+非空
    // vertex：待设置顶点已经存在+不存在
    // weight：为0时，删除；非零，修改
    // 返回值：为0，边不存在；否则，边存在，且返回值是原来的权重
    @Test public void testSet() {
        Graph<String> graph = emptyInstance();
        final String string0 = "string0";
        final String string1 = "string1";
        // 验证空的graph设置边weight（覆盖情况：graph为空+待设置边不存在+weight不为0+返回值为0）
        assertTrue(graph.set(string0, string1, 1) == 0);
        assertTrue(graph.vertices().size() == 2 && graph.vertices().contains(string0)
                && graph.vertices().contains(string1));
        assertTrue(graph.targets(string0).containsKey(string1) && graph.targets(string0).size() == 1);
        assertTrue(graph.sources(string1).containsKey(string0) && graph.sources(string1).size() == 1);
        assertTrue(graph.targets(string0).get(string1) == 1);// 验证新设置的边权重为1
        // 验证非空的graph设置边weight（覆盖情况：graph非空+待设置边存在+weight不为0+返回值非0）
        assertTrue(graph.set(string0, string1, 2) == 1);// 返回权重为1
        assertTrue(graph.vertices().size() == 2 && graph.vertices().contains(string0)
                && graph.vertices().contains(string1));
        assertTrue(graph.targets(string0).containsKey(string1) && graph.targets(string0).size() == 1);
        assertTrue(graph.sources(string1).containsKey(string0) && graph.sources(string1).size() == 1);
        assertTrue(graph.targets(string0).get(string1) == 2);// 验证新设置的边权重为2
        // 验证非空graph删除边（覆盖情况：graph非空+待设置顶点存在+weight为0+返回值非0）
        assertTrue(graph.set(string0, string1, 0) == 2);// 返回权重为2
        assertTrue(graph.vertices().size() == 2 && graph.vertices().contains(string0)
                && graph.vertices().contains(string1));
        assertTrue(graph.targets(string0).size() == 0 && graph.sources(string1).size() == 0);

    }

    // Testing strategy
    // graph：空+非空
    // vertex：待删顶点存在于顶点集中+不存在；待删顶点存在于边中+不存在
    // 结果：若返回值为false，不存在该顶点，顶点集数目不变，边集不变；否则，在顶点集或者边集存在该顶点，顶点集数目--
    @Test public void testRemove() {
        Graph<String> graph = emptyInstance();
        final String string0 = "string0";
        final String string1 = "string1";
        final String string2 = "string2";
        // 验证空的graph删除一个顶点（覆盖情况：graph为空+vertex待删边不存在+返回值为false，顶点数不变）
        assertFalse(graph.remove(string0));
        assertFalse(graph.vertices().contains(string0));
        // 验证非空graph删除一个顶点（覆盖情况：graph非空+vertex待删边存在+返回值为true且顶点数--且顶点集中不含该顶点）
        graph.add(string0);
        graph.add(string1);
        assertTrue(graph.remove(string0));// 判断返回值为true
        assertFalse(graph.vertices().contains(string0));// 判断删除后不含该该顶点
        assertTrue(graph.vertices().size() == 1);// 判断顶点集数目--
        // 验证非空graph删除一个顶点（覆盖情况：边集中存在该顶点）
        graph.add(string0);
        graph.add(string2);
        graph.set(string0, string1, 1);
        graph.set(string0, string2, 2);
        graph.set(string1, string2, 3);
        assertTrue(graph.remove(string0));// 验证返回值为true
        assertTrue(graph.vertices().size() == 2 && !graph.vertices().contains(string0));// 验证顶点集数目--且不含该顶点
        assertTrue(graph.sources(string0).size() == 0 && graph.sources(string0).size() == 0);// 验证边集中不含该顶点
        // 验证graph的边集中不含该顶点（覆盖情况：边集中不含该顶点）
        graph.add(string0);
        assertTrue(graph.remove(string0));
    }

    // Testing strategy
    // graph：空+非空
    @Test public void testVertices() {
        Graph<String> graph = emptyInstance();
        // 验证graph为空的情况
        assertEquals(Collections.emptySet(), graph.vertices());
        // 验证graph非空的情况
        String string0 = "string0";
        String string1 = "string1";
        graph.add(string0);
        graph.add(string1);
        assertTrue(graph.vertices().size() == 2 && graph.vertices().contains(string0)
                && graph.vertices().contains(string1));
    }

    // Testing strategy
    // graph：空+非空
    // vertex：待寻找顶点存在于顶点集中+不存在；
    // edges：待寻找顶点存在入边+不存在
    // 结果：返回的map含有所有的入边
    @Test public void testSources() {
        Graph<String> graph = emptyInstance();
        String string0 = new String("string0");
        // 验证graph为空的情况(覆盖情况：graph为空+待寻找节点不存在于顶点集合)
        assertEquals(Collections.emptyMap(), graph.sources(string0));
        // 验证graph非空且待寻找顶点不存在于边集中
        String string1 = new String("string1");
        graph.add(string0);
        graph.add(string1);
        assertEquals(Collections.emptyMap(), graph.sources(string0));
        // 验证graph非空且存在该节点的入边
        graph.set(string1, string0, 1);
        assertTrue(graph.sources(string0).size() == 1 && graph.sources(string0).containsKey(string1)
                && graph.sources(string0).get(string1) == 1);
    }

    // Testing strategy
    // graph：空+非空
    // vertex：待寻找顶点存在于顶点集中+不存在；
    // edges：待寻找顶点存在出边+不存在
    // 结果：返回的map含有所有的出边
    @Test public void testTargets() {
        Graph<String> graph = emptyInstance();
        String string0 = new String("string0");
        // 验证graph为空的情况(覆盖情况：graph为空+待寻找节点不存在于顶点集合)
        assertEquals(Collections.emptyMap(), graph.sources(string0));
        // 验证graph非空且待寻找顶点不存在于边集中
        String string1 = new String("string1");
        graph.add(string0);
        graph.add(string1);
        assertEquals(Collections.emptyMap(), graph.sources(string0));
        // 验证graph非空且存在该节点的出边
        graph.set(string1, string0, 1);
        assertTrue(graph.targets(string1).size() == 1 && graph.targets(string1).containsKey(string0)
                && graph.targets(string1).get(string0) == 1);
    }
}
