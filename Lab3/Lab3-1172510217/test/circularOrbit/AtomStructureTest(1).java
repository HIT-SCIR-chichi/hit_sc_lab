package circularOrbit;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class AtomStructureTest {

    @Test void testTransit() throws IOException {
        AtomStructure atomStructure = new AtomStructure();
        atomStructure.readFileAndCreateSystem(new File("src/Spring2019_HITCS_SC_Lab3-master/AtomicStructure.txt"));
        int num1 = atomStructure.getTrackObjectsNumber(1);
        int num2 = atomStructure.getTrackObjectsNumber(2);
        atomStructure.transit(atomStructure.getTrack(1).getTrackObjects().get(0), atomStructure.getTrack(2));
        assertTrue(atomStructure.getTrackObjectsNumber(1) == num1 - 1
                && atomStructure.getTrackObjectsNumber(2) == num2 + 1);
    }

}
