/*
 * Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course
 * staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment
     * looks like.
     * Make sure you have partitions.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2019-03-12T22:26:00Z");
    private static final Instant d4 = Instant.parse("2010-03-12T22:26:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it @ reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@LiNing_1- rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "xiaoming",
            " hh@Xiao_hong doing Olympics routine What you see is slavery in action", d3);
    private static final Tweet tweet4 = new Tweet(4, "xiaohong", ".@zhanghua haha, this is a test.", d4);
    private static final Tweet tweet5 = new Tweet(5, "xiaobai", "This is a text, too @lining_1-", d4);

    @Test(expected = AssertionError.class) public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));//测试只有两篇tweets

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
        
        timespan = Extract.getTimespan(Arrays.asList(tweet1,tweet2,tweet3,tweet4));//测试4篇tweets
        assertEquals("expected start", d4, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }

    @Test public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));

        assertTrue("expected empty set", mentionedUsers.isEmpty());
        
        mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1,tweet2));
        assertTrue(mentionedUsers.size()==1 && mentionedUsers.contains("lining_1-"));//测试@是第一个字符，同时测试@后是非法字符
        
        mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1,tweet2,tweet3));
        assertTrue(mentionedUsers.size()==1 && mentionedUsers.contains("lining_1-"));//测试@前是非法字符
        
        mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        assertTrue(mentionedUsers.size()==1 && mentionedUsers.contains("zhanghua"));//测试@前是合法字符
        
        mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet2,tweet5));
        assertTrue(mentionedUsers.size()==1 && mentionedUsers.contains("lining_1-"));//测试不区分大小写
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
