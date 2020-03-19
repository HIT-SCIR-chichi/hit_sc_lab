package circularOrbit;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class SocialNetworkCircleTest {

    @Test void testGetDistance() throws IOException {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
        socialNetworkCircle
                .readFileAndCreateSystem(new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt"));
        int distance = socialNetworkCircle.getDistance(socialNetworkCircle.getCentralPoint(),
                socialNetworkCircle.getFriendByName("LisaWong"));
        assertTrue(distance == 1);
    }

    @Test void testAddRelationAndRefactor() throws IOException {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
        socialNetworkCircle
                .readFileAndCreateSystem(new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt"));
        socialNetworkCircle.addRelationAndRefactor(socialNetworkCircle.getCentralPoint(),
                socialNetworkCircle.getFriendByName("JackMa"), 0.9);
        assertTrue(socialNetworkCircle.getTrack(1).getTrackObjects()
                .contains(socialNetworkCircle.getFriendByName("JackMa")));
    }
    
    @Test void testDeleteAndRefactor() throws IOException {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
        socialNetworkCircle
                .readFileAndCreateSystem(new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt"));
        socialNetworkCircle.deleteRelationAndRefactor(socialNetworkCircle.getCentralPoint(), socialNetworkCircle.getFriendByName("TomWong"));
        assertTrue(socialNetworkCircle.getTrackObjectsNumber(1) == 2);
    }

}
