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
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();

    // Abstraction function:
    // AF(vertices) = a directed weighted graph which has a map of its targets and
    // weight
    // Representation invariant:
    // all the weight of the edges > 0
    // Safety from rep exposure:
    // vertices field is private and final;
    // vertices is mutable, so vertices() make defensive copy to avoid rep exposure.
    private void checkRep() {
        for (Vertex<L> vertex : vertices) {
            for (Integer weight : vertex.getRelationMap().values()) {
                assert weight > 0;
            }
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
        for (Vertex<L> vertexL : vertices) {
            if (vertexL.getSource().equals(vertex)) {
                checkRep();
                return false;
            }
        }
        Vertex<L> addVertex = new Vertex<L>(vertex);
        vertices.add(addVertex);
        checkRep();
        return true;
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
        this.add(target);
        if (this.add(source)) {// 若未加入该顶点source
            if (weight == 0) {// 无需删除
                checkRep();
                return 0;
            }
            for (Vertex<L> vertex : vertices) {// 添加一条边
                if (vertex.getSource().equals(source)) {
                    vertex.addEdge(target, weight);
                }
            }
            checkRep();
            return 0;
        } else {// 若该顶点已经加入该集合
            for (Vertex<L> vertex : vertices) {
                if (vertex.getSource().equals(source)) {
                    int w = 0;
                    if (vertex.getRelationMap().containsKey(target)) {
                        w = vertex.getRelationMap().get(target);
                        if (weight == 0) {// 删除边，返回之前的权值
                            vertex.deleteEdge(target);
                        } else {// 重新设置边的权重
                            vertex.addEdge(target, weight);
                        }
                    } else {
                        if (weight != 0) {
                            vertex.addEdge(target, weight);
                        }
                    }
                    checkRep();
                    return w;
                }
            }
        }
        checkRep();
        return 0;
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
        Boolean flagBoolean = false;
        for (int i = 0; i < vertices.size(); i++) {
            Vertex<L> vertexL = vertices.get(i);
            if (vertexL.getSource().equals(vertex)) {// 删除以此点为起点的所有边和点
                vertexL.getRelationMap().clear();
                vertices.remove(vertexL);
                flagBoolean = true;
                i--;
                continue;
            }
            if (vertexL.getRelationMap().containsKey(vertex)) {// 删除以此点为终点的边
                vertexL.getRelationMap().remove(vertex);
                flagBoolean = true;
            }
        }
        checkRep();
        return flagBoolean;
    }

    /**
     * Get all the vertices in this graph.
     * 
     * @return the set of labels of vertices in this graph
     */
    @Override public Set<L> vertices() {
        Set<L> vertexSet = new HashSet<>();
        for (Vertex<L> vertex : vertices) {// 防御性复制
            vertexSet.add(vertex.getSource());
        }
        return vertexSet;
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
        for (Vertex<L> vertex : vertices) {// 遍历所有的边
            if (vertex.getRelationMap().containsKey(target)) {
                sourcesMap.put(vertex.getSource(), vertex.getWeight(target));
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
        Map<L, Integer> targetMap = new HashMap<>();
        for (Vertex<L> vertex : vertices) {
            if (vertex.getSource().equals(source)) {
                targetMap.putAll(vertex.getRelationMap());
                break;
            }
        }
        return targetMap;
    }

    @Override public String toString() {
        String string = new String();
        for (Vertex<L> vertex : vertices) {
            string += vertex.toString() + "\n";
        }
        return string;
    }
}

/**
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * This is a mutable data-type which represents a vertex with all its targets in
 * map with its weight. And weight is positive.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {

    // Abstraction function:
    // a vertex with L and a map that record all the edges from it
    // Representation invariant:
    // all the weight > 0
    // Safety from rep exposure:
    // L is named final which is safe;
    // the map is returned by copying a new map.

    private final L source;
    private Map<L, Integer> relationMap = new HashMap<>();

    private void checkRep() {
        for (int w : relationMap.values()) {
            assert w != 0;// 判断所有的边的权重为正
        }
    }

    /**
     * create a vertex with a source
     * 
     * @param sourceL the edge where it is from
     */
    public Vertex(L sourceL) {
        this.source = sourceL;
        checkRep();
    }

    /**
     * get the source vertex
     * 
     * @return the source where edges are from
     */
    public L getSource() {
        return this.source;
    }

    /**
     * add an edge into the edges
     * 
     * @param targetL the target vertex of the edge
     * @param weight  the weight of the edge to be added
     */
    public void addEdge(L targetL, int weight) {
        relationMap.put(targetL, weight);
        checkRep();
    }

    public void deleteEdge(L targetL) {
        relationMap.remove(targetL);
    }

    /**
     * get the map of all the edges which are from the source
     * 
     * @return get the copy of the targets map
     */
    public Map<L, Integer> getRelationMap() {
        return new HashMap<L, Integer>(relationMap);
    }

    /**
     * get the weight of the object edge which is from source and to targetL
     * 
     * @param targetL the target
     * @return the weight of the object edge
     */
    public int getWeight(L targetL) {
        return relationMap.get(targetL);
    }

    @Override public String toString() {
        String string = new String();
        string += "Vertex : " + source + "\n";// 输出顶点
        for (L target : relationMap.keySet()) {// 输出各条出边
            string += "(" + target + ":" + relationMap.get(target) + ")";
            string += "\t";
        }
        return string;
    }
}
