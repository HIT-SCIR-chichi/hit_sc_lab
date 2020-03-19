/*
 * Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course
 * staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even
 * exist
 * as a key in the map; this is true even if A is followed by other people in
 * the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *               a list of tweets providing the evidence, not modified by this
     *               method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     * @-mentions Bert in a tweet. This must be implemented. Other kinds
     *            of evidence may be used at the implementor's discretion.
     *            All the Twitter usernames in the returned social network must be
     *            either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        Set<String> allUserNames = new HashSet<>();//保存所有的作者小写的名字
        List<Tweet> writtenBy = new ArrayList<>();//保存作者写的所有推文
        Set<String> followNames = new HashSet<>();//保存作者@的所有人
        for (Tweet temp : tweets) {//忽略大小写得到所有的作者
            allUserNames.add(temp.getAuthor().toLowerCase());
        }
        for (String temp : allUserNames) {
            writtenBy = Filter.writtenBy(tweets, temp);//得到作者写的所有推文
            followNames = Extract.getMentionedUsers(writtenBy);//得到作者@的所有人
            followNames.remove(temp);//将作者自己从@群体中删除
            followsGraph.put(temp, followNames);
        }
        return followsGraph;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *                     a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        List<String> influencers = new ArrayList<>();
        Map<String, Integer> valueMap = new HashMap<>();//用于储存被关注者及其粉丝数目
        for (Set<String> temp : followsGraph.values()) {
            for (String string : temp) {
                if (valueMap.containsKey(string)) {//遍历所有被关注者，若已存在map中，则粉丝数目增加
                    valueMap.put(string, valueMap.get(string) + 1);
                } else {//否则，加入该被关注者，粉丝数目设为1
                    influencers.add(string);
                    valueMap.put(string, 1);
                }
            }
        }//处理有人未被关注的情况
        for (String string : followsGraph.keySet()) {
            if (!valueMap.keySet().contains(string)) {
                influencers.add(string);
                valueMap.put(string, 0);//若有人未被关注，则直接加入
            }
        }
        Collections.sort(influencers, new Comparator<String>() {//降序排列

            public int compare(String o1, String o2) {
                return valueMap.get(o2).compareTo(valueMap.get(o1));
            }
        });
        return influencers;
    }

}
