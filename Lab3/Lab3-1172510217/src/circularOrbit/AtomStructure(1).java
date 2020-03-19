package circularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import centralObject.CentralObject;
import physicalObject.PhysicalObject;
import track.Track;

public class AtomStructure extends ConcreteCircularOrbit<CentralObject, PhysicalObject> {

    /**
     * transit an object to a track
     * 
     * @param physicalObject an object
     * @param track          the new track
     * @return true if the new track is not the same as the older one; else false
     */
    public boolean transit(PhysicalObject physicalObject, Track<PhysicalObject> track) {
        if (physicalObject.getTrackRadius() == track.getRadius()) {
            return false;
        }
        if (deletePhysicalObject(physicalObject)) {
            track.add(physicalObject);
            return true;
        } else {
            System.out.println("将该物体从原轨道上删除失败！");
            return false;
        }
    }

    /**
     * add an object to the system
     * 
     * @param physicalObject an object
     * @return true if the object is not in the system; else false
     */
    public boolean addPhysicalObject(PhysicalObject physicalObject) {
        Track<PhysicalObject> track = getTrackByRadius(physicalObject.getTrackRadius());
        if (track == null) {
            track = new Track<>(physicalObject.getTrackRadius());
            track.add(physicalObject);
            this.addTrack(track);
            return true;
        } else {
            return track.add(physicalObject);
        }
    }

    /**
     * delete an object
     * 
     * @param physicalObject an object
     * @return true if the object is in the system; else false
     */
    public boolean deletePhysicalObject(PhysicalObject physicalObject) {
        Track<PhysicalObject> track = getTrackByRadius(physicalObject.getTrackRadius());
        if (track != null) {
            return track.remove(physicalObject);
        } else {
            return false;
        }
    }

    /**
     * read the files and create the system of AtomStructure
     * 
     * @param file 待读取的文件
     * @throws IOException 抛出异常
     */
    public void readFileAndCreateSystem(File file) throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bfReader = new BufferedReader(reader);
        String regex1 = "[ ]*[A-Z][a-z]?[ ]*";
        Pattern pattern1 = Pattern.compile(regex1);
        String[] strings = bfReader.readLine().split("[:][:][=]");
        Matcher matcher = pattern1.matcher(strings[1]);
        if (matcher.matches()) {
            CentralObject element = new CentralObject();
            element.setName(strings[1]);
            this.addCentralPoint(element);
        } else {
            System.out.println("文件读取元素格式错误！");
            System.exit(-1);
        }
        String regex2 = "[ ]*[0-9]+[ ]*";
        Pattern pattern2 = Pattern.compile(regex2);
        bfReader.readLine();
        strings = bfReader.readLine().split("[:][:][=]|[ ]|[;]|[1-9][/]");
        List<Integer> list = new ArrayList<>();
        int i = 0;
        while (i < strings.length) {
            matcher = pattern2.matcher(strings[i]);
            if (matcher.matches()) {
                list.add(Integer.parseInt(strings[i]));
            }
            i++;
        }
        for (i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i); j++) {
                PhysicalObject physicalObject = new PhysicalObject();
                physicalObject.setTrackRadius(i + 1);
                this.addPhysicalObject(physicalObject);
            }
        }
        bfReader.close();
        sortTrack();
    }

}
