/*
 * Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course
 * staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

//import P4.twitter.*;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment
     * looks like.
     * Make sure you have partitions.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about brivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "BBitdiddle", "rivest talk in 30 minutes #hype", d2);

    @Test(expected = AssertionError.class) public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");

        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        writtenBy = Filter.writtenBy(Arrays.asList(tweet1), "xiaoming");// 验证为空的情况
        assertTrue(writtenBy.size() == 0);
        
        writtenBy = Filter.writtenBy(Arrays.asList(tweet2, tweet3), "bbitdiddle");// 验证名字大小写均计算的情况
        assertTrue(writtenBy.size() == 2 && writtenBy.contains(tweet2) && writtenBy.contains(tweet3));
    }

    @Test public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));

        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
        Instant d3 = Instant.parse("2016-02-17T20:00:00Z");// 不在区间内
        Instant d4 = Instant.parse("2016-02-17T00:00:00Z");// 不在区间内
        Tweet tweet4 = new Tweet(4, "BBitdiddle", "rivest talk in 30 minutes #hype", d3);
        Tweet tweet5 = new Tweet(5, "BBitdiddle", "rivest talk in 30 minutes #hype", d4);// 交换tweet1和tweet2顺序，测试输出顺序
        inTimespan = Filter.inTimespan(Arrays.asList(tweet2, tweet4, tweet5, tweet1), new Timespan(testStart, testEnd));
        assertTrue(inTimespan.containsAll(Arrays.asList(tweet2, tweet1)));
        assertTrue(inTimespan.indexOf(tweet1) == 1);
    }

    @Test public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
        //判断有多个关键字的情况，同时判断是否重复加入
        containing = Filter.containing(Arrays.asList(tweet1,tweet2), Arrays.asList("talk","to"));
        assertTrue(containing.size() == 2);
        assertTrue(containing.containsAll(Arrays.asList(tweet1,tweet2)) && containing.indexOf(tweet1) == 0);
        //判断不区分大小写
        containing = Filter.containing(Arrays.asList(tweet1,tweet2), Arrays.asList("talk","To"));
        assertTrue(containing.size() == 2);
        assertTrue(containing.containsAll(Arrays.asList(tweet1)) && containing.indexOf(tweet1) == 0);
        //判断单词必须被空格隔开或者在段落末尾
        containing = Filter.containing(Arrays.asList(tweet1,tweet2), Arrays.asList("tal","rivest"));
        assertTrue(containing.size() == 1);
        assertTrue(containing.contains(tweet2));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
