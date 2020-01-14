/*
 * Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course
 * staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment
     * looks like.
     * Make sure you have partitions.
     */

    @Test(expected = AssertionError.class) public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        Map<String, Set<String>> resultMap = new HashMap<>();
        assertTrue("expected empty graph", followsGraph.isEmpty());
        Instant d = Instant.parse("2016-02-17T10:00:00Z");
        Tweet tweet1 = new Tweet(1, "xiaohong", "@xiaoming", d);
        Tweet tweet2 = new Tweet(2, "xiaoming", "@xiaohong @xiaoli", d);
        Tweet tweet3 = new Tweet(3, "xiaoli", "@xiaohong @xiaoming @xiaozhang", d);
        Tweet tweet4 = new Tweet(4, "xiaozhang", "@xiaohong @xiaoli @xiaoming @XIAOMING", d);
        //测试一般情况
        followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1,tweet2,tweet3));
        resultMap.put("xiaohong", Set.of("xiaoming"));
        resultMap.put("xiaoming", Set.of("xiaoli","xiaohong"));
        resultMap.put("xiaoli", Set.of("xiaohong","xiaoming","xiaozhang"));
        assertEquals(resultMap, followsGraph);
        //测试@对象大小写
        followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1,tweet2,tweet3,tweet4));
        resultMap.put("xiaozhang",Set.of("xiaohong","xiaoli","xiaoming"));
        assertEquals(resultMap, followsGraph);
        //测试@自己
        Tweet tweet5 = new Tweet(5, "xiaobai", "@xiaobai", d);
        followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet5));
        resultMap.clear();
        resultMap.put("xiaobai", Set.of());
        assertEquals(resultMap, followsGraph);
    }

    @Test public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("expected empty list", influencers.isEmpty());
        Instant d = Instant.parse("2016-02-17T10:00:00Z");
        Tweet tweet1 = new Tweet(1, "xiaohong", "@xiaoli @xiaoming", d);
        Tweet tweet2 = new Tweet(2, "xiaoming", "@xiaoli",d);
        Tweet tweet3 = new Tweet(3, "xiaoli", "",d);
        Tweet tweet4 = new Tweet(4, "xiaozhang","@xiaoli @xiaoming @xiaohong", d);
        //处理判断一般情况
        followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1,tweet2,tweet3));
        influencers = SocialNetwork.influencers(followsGraph);
        assertEquals(List.of("xiaoli","xiaoming","xiaohong"), influencers);
        //处理判断有用户未被关注的情况
        followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1,tweet2,tweet3,tweet4));
        influencers = SocialNetwork.influencers(followsGraph);
        assertEquals(List.of("xiaoli","xiaoming","xiaohong","xiaozhang"), influencers);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
