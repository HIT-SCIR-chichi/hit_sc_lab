/*
 * Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course
 * staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>
 * GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     Hello, HELLO, hello, goodbye!
 * </pre>
 * <p>
 * the graph would contain two edges:
 * <ul>
 * <li>("hello,") -> ("hello,") with weight 2
 * <li>("hello,") -> ("goodbye!") with weight 1
 * </ul>
 * <p>
 * where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>
 * Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     This is a test of the Mugar Omni Theater sound system.
 * </pre>
 * <p>
 * on this input:
 * 
 * <pre>
 *     Test the system.
 * </pre>
 * <p>
 * the output poem would be:
 * 
 * <pre>
 *     Test of the system.
 * </pre>
 * 
 * <p>
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 * 
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    // a graph with the ConcreteEdgesGraph that represents a graph
    // Representation invariant:
    // vertices of the graph are non-empty case-insensitive strings
    // of non-space non-newline characters
    // Safety from rep exposure:
    // graph field is private and final.

    private void checkRep() {
        for (String vertex : graph.vertices()) {// 桥接词不区分大小写
            String compare = vertex.toLowerCase();
            assert vertex.equals(compare);
            assert !vertex.equals("");// 证明顶点内容单词非空
        }
    }

    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(corpus));
        BufferedReader bfReader = new BufferedReader(reader);
        String line = new String();
        String[] words = new String[100];
        int count = 0;// 将所有的单词存入words数组里
        while ((line = bfReader.readLine()) != null) {
            String[] linewords = line.split(" ");// 根据空格将文件中的字符串分割成一个个的单词
            for (int i = 0; i < linewords.length; i++) {
                words[count++] = linewords[i].toLowerCase();// 注意到不区分大小写
            }
        }
        graph.add(words[count - 1]);
        for (int i = 0; i < count - 1; i++) {// 加顶点以及加边
            graph.add(words[i]);
            int w = graph.set(words[i], words[i + 1], 0) + 1;
            graph.set(words[i], words[i + 1], w);// 加边，边的出现次数视为权重
        }
        bfReader.close();
        checkRep();
    }

    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] inputWords = input.split(" ");
        List<String> outputWords = new ArrayList<>();
        int inputLength = inputWords.length;
        for (int i = 0; i < inputLength - 1; i++) {
            Map<String, Integer> targetsMap = graph.targets(inputWords[i].toLowerCase());//用于储存所有的出边
            Map<String, Integer> sourcesMap = graph.sources(inputWords[i + 1].toLowerCase());//用于储存所有的入边
            Map<String, Integer> chosenMap = new HashMap<>();
            outputWords.add(inputWords[i]);
            // 找到所有的桥接单词
            for (String bridge : targetsMap.keySet()) {
                if (sourcesMap.containsKey(bridge)) {
                    chosenMap.put(bridge, targetsMap.get(bridge) + sourcesMap.get(bridge));
                }
            }
            // 找到权值最大的桥接单词
            String bridgeString = new String("");
            int weight = 0;
            for (String bridge : chosenMap.keySet()) {
                if (chosenMap.get(bridge) > weight) {
                    weight = chosenMap.get(bridge);
                    bridgeString = bridge;
                }
            }
            if (!bridgeString.contentEquals("")) {//将空白字符去掉，加入桥接词
                outputWords.add(bridgeString);
            }
        }
        outputWords.add(inputWords[inputLength - 1]);//加入待输出集合
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < outputWords.size() - 1; i++) {//依次加上所有的桥接词
            sBuilder.append(outputWords.get(i) + " ");
        }
        sBuilder.append(outputWords.get(outputWords.size() - 1));
        checkRep();
        return sBuilder.toString();
    }

    @Override public String toString() {
        String s = new String();
        for (String string : graph.vertices()) {
            s += string + " : ";
            for (String target : graph.targets(string).keySet()) {
                s += "(" + target + "," + graph.targets(string).get(target) + ")";// 遍历每一个顶点的边
            } // 输出出边和权重
            s += "\n";
        }
        return s;
    }

}
