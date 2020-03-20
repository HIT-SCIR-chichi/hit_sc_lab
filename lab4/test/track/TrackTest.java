package track;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import physicalObject.PhysicalObject;

class TrackTest {

    /**
     * testing strategy : 测试获取的正确性
     */
    @Test void testGetRadius() {
        Track<PhysicalObject> track = new Track<>(100);
        assertEquals(track.getRadius(), 100);
    }

    /**
     * 测试track中不含某物体+已含有某物体
     */
    @Test void testAdd() {
        Track<PhysicalObject> track = new Track<>(100);
        PhysicalObject physicalObject = new PhysicalObject();
        physicalObject.setTrackRadius(100);
        track.add(physicalObject);
        assertTrue(track.getTrackObjects().contains(physicalObject));
        Boolean res = track.add(physicalObject);
        assertFalse(res);
        assertTrue(track.contains(physicalObject));
    }

    /**
     * 测试track中含有某物体删除的情况或不含有某物体删除的情况
     */
    @Test void testRemove() {
        Track<PhysicalObject> track = new Track<>(100);
        boolean res = true;
        PhysicalObject physicalObject = new PhysicalObject();
        physicalObject.setTrackRadius(100);
        res = track.remove(physicalObject);
        // 测试track中不含有某物体删除的情况
        assertFalse(res);
        assertFalse(track.contains(physicalObject));
        // 测试track中含有某物体删除的情况
        track.add(physicalObject);
        res = track.remove(physicalObject);
        assertTrue(res);
        assertFalse(track.contains(physicalObject));
    }

    /**
     * 测试track中不含某物体（返回false）+含有某物体（返回true）
     */
    @Test void testContains() {
        Track<PhysicalObject> track = new Track<>(100);
        PhysicalObject physicalObject = new PhysicalObject();
        physicalObject.setTrackRadius(100);
        // 测试track中不含某物体（返回false）
        assertFalse(track.contains(physicalObject));
        // 测试含有某物体（返回true）
        track.add(physicalObject);
        assertTrue(track.contains(physicalObject));
    }
    
    /**
     * testing strategy : 轨道中有多个物体
     */
    @Test void testToString() {
        Track<PhysicalObject> track = new Track<>(100);
        PhysicalObject physicalObject = new PhysicalObject();
        physicalObject.setTrackRadius(100);
        PhysicalObject physicalObject1 = new PhysicalObject();
        physicalObject1.setTrackRadius(100);
        track.add(physicalObject);
        track.add(physicalObject1);
        String string = "radius=" + 100.0 + " : ";
        string += "[e]" + " ";
        string += "[e]" + " ";
        assertEquals(string, track.toString());
    }

    /**
     * testing strategy : track不含物体+含有多个物体
     */
    @Test void testGetNumberOfObjects() {
        Track<PhysicalObject> track = new Track<>(100);
        PhysicalObject physicalObject = new PhysicalObject();
        physicalObject.setTrackRadius(100);
        PhysicalObject physicalObject1 = new PhysicalObject();
        physicalObject1.setTrackRadius(100);
        // track不含物体
        assertEquals(0, track.getNumberOfObjects());
        // 含有多个物体
        track.add(physicalObject);
        track.add(physicalObject1);
        track.add(physicalObject1);
        assertEquals(2, track.getNumberOfObjects());
    }

    /**
     * testing strategy : 多个物体
     */
    @Test void testGetTrackObjects() {
        Track<PhysicalObject> track = new Track<>(100);
        PhysicalObject physicalObject = new PhysicalObject();
        physicalObject.setTrackRadius(100);
        PhysicalObject physicalObject1 = new PhysicalObject();
        physicalObject1.setTrackRadius(100);
        track.add(physicalObject);
        track.add(physicalObject1);
        List<PhysicalObject> list = new ArrayList<>();
        list.add(physicalObject);
        list.add(physicalObject1);
        assertEquals(list, track.getTrackObjects());
    }

    /**
     * testing strategy : 不含有某物体，返回-1；含有某物体，返回index
     */
    @Test void testGetPhysicalObjectIndex() {
        Track<PhysicalObject> track = new Track<>(100);
        PhysicalObject physicalObject = new PhysicalObject();
        physicalObject.setTrackRadius(100);
        PhysicalObject physicalObject1 = new PhysicalObject();
        physicalObject1.setTrackRadius(100);
        // 不含有某物体，返回-1
        assertEquals(-1, track.getPhysicalObjectIndex(physicalObject));
        // 含有某物体，返回index
        track.add(physicalObject1);
        track.add(physicalObject);
        assertEquals(1, track.getPhysicalObjectIndex(physicalObject));
    }

}
