/*
 * Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course
 * staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    // AF(vertices, edges) = weighted graph with directed edges and vertices are
    // different
    // Representation invariant:
    // All the weight of the edges > 0
    // Safety from rep exposure:
    // fields are private and final in order to keep the codes safe
    // vertices is mutable, so vertices() make defensive copy to avoid rep exposure
    // edges is immutable

    private void checkRep() {
        assert vertices != null;// 检查是否顶点集为空
        for (L l : vertices) {// 检查是否加入了null顶点对象
            assert l != null;
        }
        for (Edge<L> edge : edges) {// 检查是否存在边权值不合格
            assert edge != null;
            assert edge.getWeight() > 0;
            assert vertices.contains(edge.getSource());// 检查是否有野边
            assert vertices.contains(edge.getTarget());// 检查是否有野边
        }
    }

    /**
     * Add a vertex to this graph.
     * 
     * @param vertex label for the new vertex
     * @return true if this graph did not already include a vertex with the
     *         given label; otherwise false (and this graph is not modified)
     */
    @Override public boolean add(L vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        checkRep();
        return true;// 若已存在，无需添加；若不存在，直接添加
    }

    /**
     * Add, change, or remove a weighted directed edge in this graph.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * vertices with the given labels are added to the graph if they do not
     * already exist.
     * If weight is zero, remove the edge if it exists (the graph is not
     * otherwise modified).
     * 
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     */
    @Override public int set(L source, L target, int weight) {
        this.add(source);// 首先应该将顶点加进去
        this.add(target);
        int index = -1;// 记录是否该边已存在
        for (int i = 0; i < edges.size(); i++) {
            L s = edges.get(i).getSource();
            L t = edges.get(i).getTarget();
            if (s.equals(source) && t.equals(target)) {
                index = i;
                break;
            }
        }
        int w = 0;// 是否是删除该边
        if (weight == 0) {
            if (index != -1) {
                w = edges.get(index).getWeight();
                edges.remove(index);
            }
        } else {
            Edge<L> e = new Edge<L>(source, target, weight);// 防御性复制
            if (index != -1) {
                w = edges.get(index).getWeight();
                edges.remove(index);// 删除旧边
            }
            edges.add(e);// 将新边加进去
        }
        checkRep();
        return w;// 返回原来的权重
    }

    /**
     * Remove a vertex from this graph; any edges to or from the vertex are
     * also removed.
     * 
     * @param vertex label of the vertex to remove
     * @return true if this graph included a vertex with the given label;
     *         otherwise false (and this graph is not modified)
     */
    @Override public boolean remove(L vertex) {
        if (!vertices.contains(vertex)) {
            checkRep();
            return false;
        }
        vertices.remove(vertex);// 删去顶点
        Edge<L> edge;
        for (int i = 0; i < edges.size(); i++) {
            edge = edges.get(i);
            L s = edge.getSource();
            L t = edge.getTarget();
            if (s.equals(vertex) || t.equals(vertex)) {
                edges.remove(i--);// 如果找到符合条件的边，要将其删去
            }
        }
        checkRep();
        return true;
    }

    /**
     * Get all the vertices in this graph.
     * 
     * @return the set of labels of vertices in this graph
     */
    @Override public Set<L> vertices() {
        Set<L> myVertices = new HashSet<>();
        for (L vertice : vertices) {
            L string;
            string = vertice;
            myVertices.add(string);
        } // 进行防御性复制
        checkRep();
        return myVertices;
    }

    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.
     * 
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target
     */
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> sourcesMap = new HashMap<>();
        for (Edge<L> edge : edges) {// 循环遍历判断所有的边
            if (edge.getTarget().equals(target)) {
                sourcesMap.put(edge.getSource(), edge.getWeight());
            }
        }
        return sourcesMap;
    }

    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     * 
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from source to that vertex, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         source to the key
     */
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targetsMap = new HashMap<>();
        for (Edge<L> edge : edges) {// 循环遍历得到所有的边
            if (edge.getSource().equals(source)) {
                targetsMap.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targetsMap;
    }

    @Override public String toString() {
        String string = new String();
        string += "All the vertices : ";
        for (L l : vertices) {
            string += l + " ";// 遍历加上所有的定点
        }
        string += "\n" + "All the edges : ";
        for (Edge<L> edge : edges) {// 遍历加上所有的边
            string += edge.toString();
        }
        return string;
    }

}

/**
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * This class names an immutable data-type which represents an edge in a
 * directed graph.
 * An edge has a source vertex, a target vertex and a positive weight.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {

    private L source;
    private L target;
    private Integer weight = 0;

    // Abstraction function:
    // AF(source,target,weight) = an edge which is from source to target with a
    // positive weight
    // Representation invariant:
    // weight > 0
    // Safety from rep exposure:
    // fields are private and final in order to keep the codes safe
    // checkRep:check the rep invariant
    private void checkRep() {
        assert source != null;
        assert target != null;
        assert weight > 0;
    }

    /**
     * Create an edge with the s, t and w.
     * 
     * @param s source vertex of the edge where it is from
     * @param t target vertex of the edge where it is to
     * @param w weight of the edge
     */
    public Edge(L s, L t, int w) {
        this.source = s;
        this.target = t;
        this.weight = w;
        checkRep();
    }

    /**
     * get the source of the edge
     * 
     * @return source of the edge
     */
    public L getSource() {
        L sourceL;
        sourceL = this.source;
        return sourceL;
    }

    /**
     * get the target of the edge
     * 
     * @return target of the edge
     */
    public L getTarget() {
        L targetL = this.target;
        return targetL;
    }

    /**
     * get the weight of the edge
     * 
     * @return target of the edge
     */
    public int getWeight() {
        return this.weight;
    }

    @Override public String toString() {
        return "(" + source + "->" + target + ":" + weight + ")";
    }
}
