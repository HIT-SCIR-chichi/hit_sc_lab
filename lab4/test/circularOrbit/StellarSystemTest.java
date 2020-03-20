package circularOrbit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import centralObject.Stellar;
import myException.FileChooseException;
import physicalObject.StellarSystemObject;
import track.Track;

class StellarSystemTest {

    /**
     * 恒星label错误
     */
    @Test void lableTest() throws IOException {
        File file = new File("src/TestFile/StellarSystemFile/LabelTest.txt");
        StellarSystem stellarSystem = new StellarSystem();
        try {
            stellarSystem.readFileAndCreateSystem(file);
            assertTrue(false, "此处不可达");
        } catch (FileChooseException e) {
            assertEquals(e.getClass(), new FileChooseException().getClass());
        }
    }

    /**
     * 科学记数法表示错误
     */
    @Test void e_notationTest() throws IOException {
        File file = new File("src/TestFile/StellarSystemFile/E_NotationTest.txt");
        StellarSystem stellarSystem = new StellarSystem();
        try {
            stellarSystem.readFileAndCreateSystem(file);
            assertTrue(false, "此处不可达");
        } catch (FileChooseException e) {
            assertEquals(e.getClass(), new FileChooseException().getClass());
        }
    }

    /**
     * 测试公转方向错误
     */
    @Test void revolutionDirectionTest() throws IOException {
        File file = new File("src/TestFile/StellarSystemFile/RevolutionDirectionTest.txt");
        StellarSystem stellarSystem = new StellarSystem();
        try {
            stellarSystem.readFileAndCreateSystem(file);
            assertTrue(false, "此处不可达");
        } catch (FileChooseException e) {
            assertEquals(e.getClass(), new FileChooseException().getClass());
        }
    }

    /**
     * 测试初始角度错误
     */
    @Test void angelTest() throws IOException {
        File file = new File("src/TestFile/StellarSystemFile/AngelTest.txt");
        StellarSystem stellarSystem = new StellarSystem();
        try {
            stellarSystem.readFileAndCreateSystem(file);
            assertTrue(false, "此处不可达");
        } catch (FileChooseException e) {
            assertEquals(e.getClass(), new FileChooseException().getClass());
        }
    }
    
    /**
     * 测试中心恒星缺失
     */
    @Test void noStellarTest() throws IOException {
        File file = new File("src/TestFile/StellarSystemFile/NoStellarTest.txt");
        StellarSystem stellarSystem = new StellarSystem();
        try {
            stellarSystem.readFileAndCreateSystem(file);
            assertTrue(false, "此处不可达");
        } catch (FileChooseException e) {
            assertEquals(e.getClass(), new FileChooseException().getClass());
        }
    }

    /**
     * testing strategy : 无中心物体 + 轨道上物体数目异常 + 相邻轨道半径之差小于两相邻行星半径之和+标签相同
     */
    @Test void testCheckRep() {
        StellarSystem stellarSystem = new StellarSystem();
        try {
            stellarSystem.checkRep();
        } catch (FileChooseException e) {
            assertTrue(true);
        }
        Stellar stellar = new Stellar();
        stellar.setName("@Author ZJR");
        stellar.setMess(100);
        stellar.setRadius(1);
        stellarSystem.addCentralPoint(stellar);
        try {
            stellarSystem.checkRep();
            assertTrue(true);
        } catch (FileChooseException e) {
            assertTrue(false);
        }
        Track<StellarSystemObject> track = new Track<>(7);
        stellarSystem.addTrack(track);
        try {
            stellarSystem.checkRep();
        } catch (FileChooseException e) {
            assertTrue(true);
        }
        StellarSystemObject planet = new StellarSystemObject("A", "B", "C", 1, 2, "CW", 0);
        planet.setTrackRadius(7);
        track.add(planet);
        try {
            assertTrue(true);
            stellarSystem.checkRep();
        } catch (FileChooseException e) {
            assertTrue(false);
        }
        Track<StellarSystemObject> track2 = new Track<>(10);
        stellarSystem.addTrack(track2);
        StellarSystemObject planet2 = new StellarSystemObject("A", "B", "C", 1, 2, "CW", 0);
        planet2.setTrackRadius(10);
        track2.add(planet2);
        try {
            stellarSystem.checkRep();
        } catch (FileChooseException e) {
            assertTrue(true);
        }
        Track<StellarSystemObject> track3 = new Track<>(11);
        stellarSystem.addTrack(track3);
        StellarSystemObject planet3 = new StellarSystemObject("D", "B", "C", 4, 2, "CW", 0);
        planet3.setTrackRadius(11);
        track3.add(planet3);
        stellarSystem.deleteTrack(track2);
        try {
            stellarSystem.checkRep();
        } catch (FileChooseException e) {
            assertTrue(true);
        }
    }

    /**
     * testing strategy : 旋转方向为CW，旋转方向为CCW
     */
    @Test void testCalculatePosition() {
        StellarSystem stellarSystem = new StellarSystem();
        Stellar stellar = new Stellar();
        Track<StellarSystemObject> track = new Track<>(4);
        StellarSystemObject planet = new StellarSystemObject("A", "B", "C", 1, 2, "CW", 0);
        stellarSystem.addCentralPoint(stellar);
        stellarSystem.addTrack(track);
        track.add(planet);
        planet.setTrackRadius(4);
        Calendar targetTime = Calendar.getInstance();
        stellarSystem.setReadTime(targetTime);
        Calendar newTime = Calendar.getInstance();
        newTime.setTimeInMillis(newTime.getTimeInMillis() + 1000);
        // 旋转方向为CW
        assertEquals(0, stellarSystem.calculatePosition(planet, targetTime));
        // 旋转方向为CCW
        StellarSystemObject planet2 = new StellarSystemObject("A", "B", "C", 1, 2, "CCW", 0);
        assertEquals(0, stellarSystem.calculatePosition(planet2, targetTime));
    }

    /**
     * testing strategy : 测试正常情况：均非空
     */
    @Test void testGetPhysicalDistance() {
        StellarSystem stellarSystem = new StellarSystem();
        Stellar stellar = new Stellar();
        Track<StellarSystemObject> track1 = new Track<>(2);
        Track<StellarSystemObject> track2 = new Track<>(4);
        StellarSystemObject planet1 = new StellarSystemObject("A", "B", "C", 1, 2, "CW", 0);
        StellarSystemObject planet2 = new StellarSystemObject("A1", "B", "C", 1, 2, "CW", 0);
        stellarSystem.addCentralPoint(stellar);
        stellarSystem.addTrack(track1);
        track1.add(planet1);
        track2.add(planet2);
        planet1.setTrackRadius(2);
        planet2.setTrackRadius(4);
        assertEquals(2, stellarSystem.getPhysicalDistance(planet1, planet2));
    }

    /**
     * testing strategy : 测试正常情况
     */
    @Test void testGetPhysicalDistanceStar() {
        StellarSystem stellarSystem = new StellarSystem();
        Stellar stellar = new Stellar();
        Track<StellarSystemObject> track = new Track<>(2);
        StellarSystemObject planet = new StellarSystemObject("A", "B", "C", 1, 2, "CW", 0);
        stellarSystem.addCentralPoint(stellar);
        stellarSystem.addTrack(track);
        track.add(planet);
        planet.setTrackRadius(2);
        assertEquals(2, stellarSystem.getPhysicalDistanceStar(planet));
    }

}
