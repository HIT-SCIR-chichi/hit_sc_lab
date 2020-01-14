package physicalobject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import physicalobject.StellarSystemObject;

class StellarSystemObjectTest {

  /**
   * testing strategy : 正常测试，选取一个测试用例.
   */
  @Test void testToString() {
    StellarSystemObject planet =
        new StellarSystemObject("A", "B", "C", 100, 200, "CW", 0);
    assertEquals("[name=A]", planet.toString());
  }

  /**
   * testing strategy : 正常测试，选取一个测试用例.
   */
  @Test void testGetPlanetName() {
    StellarSystemObject planet =
        new StellarSystemObject("A", "B", "C", 100, 200, "CW", 0);
    assertEquals("A", planet.getPlanetName());
  }

  /**
   * testing strategy : 正常测试，选取一个测试用例.
   */
  @Test void testGetPlanetState() {
    StellarSystemObject planet =
        new StellarSystemObject("A", "B", "C", 100, 200, "CW", 0);
    assertEquals("B", planet.getPlanetState());
  }

  /**
   * testing strategy : 正常测试，选取一个测试用例.
   */
  @Test void testGetPlanetColor() {
    StellarSystemObject planet =
        new StellarSystemObject("A", "B", "C", 100, 200, "CW", 0);
    assertEquals("C", planet.getPlanetColor());
  }

  /**
   * testing strategy : 正常测试，选取一个测试用例.
   */
  @Test void testGetPlanetRadius() {
    StellarSystemObject planet =
        new StellarSystemObject("A", "B", "C", 100, 200, "CW", 0);
    assertEquals(100, planet.getPlanetRadius());
  }

  /**
   * testing strategy : 正常测试，选取一个测试用例.
   */
  @Test void testGetRevolutionSpeed() {
    StellarSystemObject planet =
        new StellarSystemObject("A", "B", "C", 100, 200, "CW", 0);
    assertEquals(200, planet.getRevolutionSpeed());
  }

  /**
   * testing strategy : 正常测试，选取一个测试用例.
   */
  @Test void testGetRevolutionDiretion() {
    StellarSystemObject planet =
        new StellarSystemObject("A", "B", "C", 100, 200, "CW", 0);
    assertEquals("CW", planet.getRevolutionDiretion());
  }

  /**
   * testing strategy : 正常测试，选取一个测试用例.
   */
  @Test void testGetAngle() {
    StellarSystemObject planet =
        new StellarSystemObject("A", "B", "C", 100, 200, "CW", 0);
    assertEquals(0, planet.getAngle());
  }

}
