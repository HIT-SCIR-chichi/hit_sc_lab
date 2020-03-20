package debug;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TopVotedCandidateTest {

    /**
     * testing strategy : 测试正常时间
     */
    @Test void testNomal() {
        int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
        int[] times = { 0, 5, 10, 15, 20, 25,30 };
        TopVotedCandidate topVotedCandidate = new TopVotedCandidate(persons, times);
        assertEquals(0, topVotedCandidate.q(3));
    }
    
    /**
     * testing strategy : 测试当平局时，选取最近当选的
     */
    @Test void testEqual() {
        int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
        int[] times = { 0, 5, 10, 15, 20, 25,30 };
        TopVotedCandidate topVotedCandidate = new TopVotedCandidate(persons, times);
        assertEquals(1, topVotedCandidate.q(26));
    }
    
    /**
     * testing strategy : 测试时间和投票时间一致时，按照投票结束处理
     */
    @Test void testTimeEqual() {
        int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
        int[] times = { 0, 5, 10, 15, 20, 25,30 };
        TopVotedCandidate topVotedCandidate = new TopVotedCandidate(persons, times);
        assertEquals(1, topVotedCandidate.q(25));
    }

}
