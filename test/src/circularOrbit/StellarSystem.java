package circularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import centralObject.Stellar;
import physicalObject.StellarSystemObject;
import track.Track;

public class StellarSystem extends ConcreteCircularOrbit<Stellar, StellarSystemObject> {

    /* 该系统必须在使用时更新readTime */
    private Calendar readTime = null;// 读取文件建立此系统的时刻

    /**
     * 读取文件，建立系统
     * 
     * @param file 待读取的文件
     * @throws IOException 抛出异常
     */
    public void readFileAndCreateSystem(File file) throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bfReader = new BufferedReader(reader);
        List<String> list = new ArrayList<>();
        String string = bfReader.readLine();// 读取第一行，恒星行
        String[] s = string.split("[,]|[<]|[>]");
        Pattern pattern1 = Pattern.compile("([a-z]|[A-Z])([a-z]|[A-Z]|[0-9])*|(CW)|(CCW)");// 名称的格式
        Pattern pattern2 = Pattern.compile("([1-9][0-9]{1,3}([.][0-9]+)?|[1-9]([.][0-9]+)?([Ee][+-]?[0-9]+)?)|[0]");// 数值的格式
        Matcher matcher;
        int i = 0;
        while (i < s.length) {
            matcher = pattern1.matcher(s[i]);
            if (matcher.matches()) {
                list.add(s[i]);
            }
            i++;
        }
        Stellar stellar = new Stellar();
        stellar.setName(list.get(0));
        i = 0;
        while (i < s.length) {
            matcher = pattern2.matcher(s[i]);
            if (matcher.matches()) {
                list.add(s[i]);
            }
            i++;
        }
        stellar.setRadius(Double.parseDouble(list.get(1)));
        stellar.setMess(Double.parseDouble(list.get(2)));
        this.addCentralPoint(stellar);
        list.removeAll(list);
        int count = 1;
        while ((string = bfReader.readLine()) != null) {
            count++;
            s = string.split("[,]|[<]|[>]");
            i = 0;
            while (i < s.length && list.size() < 4) {
                matcher = pattern1.matcher(s[i]);
                if (matcher.matches()) {
                    list.add(s[i]);
                }
                i++;
            }
            i = 0;
            while (i < s.length) {
                matcher = pattern2.matcher(s[i]);
                if (matcher.matches()) {
                    list.add(s[i]);
                }
                i++;
            }
            if (list.size() != 8) {
                System.out.println("此行输入非法" + count);
                list.removeAll(list);
                continue;
            }
            StellarSystemObject stellarSystemObject = new StellarSystemObject(list.get(0), list.get(1), list.get(2),
                    Double.parseDouble(list.get(4)), Double.parseDouble(list.get(6)), list.get(3),
                    Double.parseDouble(list.get(7)));
            stellarSystemObject.setTrackRadius(Double.parseDouble(list.get(5)));
            Track<StellarSystemObject> track = new Track<>(Double.parseDouble(list.get(5)));
            this.addTrack(track);
            this.addTrackObject(track, stellarSystemObject);
            list.removeAll(list);
        }
        bfReader.close();
        sortTrack();
    }

    /**
     * 设置readTime
     * 
     * @param readTime
     */
    public void setReadTime(Calendar readTime) {
        this.readTime = readTime;
    }

    /**
     * 得到readTime
     * 
     * @return 返回readTime
     */
    public Calendar getReadTime() {
        return this.readTime;
    }

    /**
     * 计算相对于读取文件时刻的某时刻的行星角度位置
     * 
     * @param planet  待计算的行星
     * @param newTime 新的时刻
     * @return 返回新时刻的角度
     */
    public double calculatePosition(StellarSystemObject planet, Calendar newTime) {
        long timeDif = newTime.getTimeInMillis() - readTime.getTimeInMillis();
        timeDif /= 1000;// 差的秒数
        double angleDif = planet.getRevolutionSpeed() * timeDif * 360 / (2 * Math.PI * planet.getTrackRadius());
        if (planet.getRevolutionDiretion() == "CCW") {
            angleDif += planet.getAngle();
            return angleDif % 360;
        } else {
            angleDif = planet.getAngle() - angleDif;
            angleDif %= 360;
            return angleDif < 0 ? angleDif + 360 : angleDif;
        }
    }

    /**
     * 得到读取文件时刻两个行星之间的物理距离
     * 
     * @param planet1 行星1
     * @param planet2 行星2
     * @return 返回两个行星之间的物理距离
     */
    public double getPhysicalDistance(StellarSystemObject planet1, StellarSystemObject planet2) {
        double radius1 = planet1.getTrackRadius();
        double radius2 = planet2.getTrackRadius();
        double angleDif = planet1.getAngle() - planet2.getAngle();
        double distance = Math.pow(radius1, 2) + Math.pow(radius2, 2) - 2 * radius1 * radius2 * Math.cos(angleDif);
        return Math.pow(distance, 0.5);
    }

    /**
     * 得到恒星与某行星之间的物理距离
     * 
     * @param planet 待计算距离的行星
     * @return 返回距离
     */
    public double getPhysicalDistanceStar(StellarSystemObject planet) {
        return planet.getTrackRadius();
    }
}
