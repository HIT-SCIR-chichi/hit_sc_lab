package physicalobject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import physicalobject.PhysicalObject;

class PhysicalObjectTest {

  /**
   * testing strategy : 正常测试，选取正常的一个用例.
   */
  @Test void testGetTrackRadius() {
    PhysicalObject physicalObject = new PhysicalObject();
    physicalObject.setTrackRadius(100);
    assertEquals(100.0, physicalObject.getTrackRadius());
  }

  /**
   * testing strategy : 正常测试，选取正常的一个用例.
   */
  @Test void testSetTrackRadius() {
    PhysicalObject physicalObject = new PhysicalObject();
    physicalObject.setTrackRadius(100);
    assertEquals(100.0, physicalObject.getTrackRadius());
  }

  /**
   * testing strategy : 正常测试，选取正常的一个用例.
   */
  @Test void testToString() {
    PhysicalObject physicalObject = new PhysicalObject();
    physicalObject.setTrackRadius(100);
    assertEquals("[e]", physicalObject.toString());
  }

}
