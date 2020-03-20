package circularOrbit;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import physicalObject.StellarSystemObject;
import track.Track;

class StellarSystemTest {

    @Test void testAddTrack() throws IOException {
        StellarSystem stellarSystem = new StellarSystem();
        stellarSystem.readFileAndCreateSystem(new File("src/Spring2019_HITCS_SC_Lab3-master/StellarSystem.txt"));
        Track<StellarSystemObject> track = new Track<>(1000);
        stellarSystem.addTrack(track);
        assertTrue(stellarSystem.getTrackByRadius(1000) != null);
    }

    @Test void testDeleteTrack() throws IOException {
        StellarSystem stellarSystem = new StellarSystem();
        stellarSystem.readFileAndCreateSystem(new File("src/Spring2019_HITCS_SC_Lab3-master/StellarSystem.txt"));
        Track<StellarSystemObject> track = new Track<>(1000);
        stellarSystem.addTrack(track);
        assertTrue(stellarSystem.getTrackByRadius(1000) != null);
        stellarSystem.deleteTrack(track);
        assertTrue(stellarSystem.getTrackByRadius(1000) == null);
    }

    @Test void testGetTracksNumber() {
        StellarSystem stellarSystem = new StellarSystem();
        stellarSystem.addTrack(new Track<>(1000));
        assertTrue(stellarSystem.getTracksNumber() == 1);
    }

}
