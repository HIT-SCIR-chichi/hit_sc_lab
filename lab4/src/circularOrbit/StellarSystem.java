package circularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import applications.App;
import centralObject.Stellar;
import myException.CentralObjectException;
import myException.FileChooseException;
import myException.FileGrammerException;
import myException.LabelSameException;
import myException.PhysicalObjectException;
import myException.StellarRadiusException;
import physicalObject.StellarSystemObject;
import track.Track;

public class StellarSystem extends ConcreteCircularOrbit<Stellar, StellarSystemObject> {

    /* 该系统必须在使用时更新readTime */
    private Calendar readTime = null;// 读取文件建立此系统的时刻
    private static final Logger logger = Logger.getLogger(App.class.getSimpleName());

    /**
     * @throws FileChooseException
     * @throws IOException
     */
    public void readFileAndCreateSystem(File file) throws FileChooseException, IOException {
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bfReader = new BufferedReader(reader);
            String lineString = new String();
            Pattern pattern1 = Pattern.compile("Stellar ::= <[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*>");
            Pattern pattern2 = Pattern.compile(
                    "Planet ::= <[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*,[ ]*(\\S+)[ ]*>");
            Matcher matcher;
            int lineCount = 0;
            int count = 0;
            while ((lineString = bfReader.readLine()) != null) {
                lineCount++;
                if ((matcher = pattern1.matcher(lineString)).find()) {
                    count++;
                    String group1 = matcher.group(1);
                    String group2 = matcher.group(2);
                    String group3 = matcher.group(3);
                    if (!Pattern.matches("[0-9a-zA-Z]+", group1)) {
                        bfReader.close();
                        throw new FileGrammerException("恒星名字错误，行号：" + lineCount, 10);
                    }
                    if (!Pattern.matches("([1-9][0-9]{1,3}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?)|[0]",
                            group2)) {
                        bfReader.close();
                        throw new FileGrammerException("恒星半径错误，行号：" + lineCount, 10);
                    }
                    if (!Pattern.matches("([1-9][0-9]{1,3}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?)|[0]",
                            group3)) {
                        bfReader.close();
                        throw new FileGrammerException("恒星质量错误，行号：" + lineCount, 10);
                    }
                    Stellar stellar = new Stellar();
                    stellar.setName(group1);
                    stellar.setRadius(Double.parseDouble(group2));
                    stellar.setMess(Double.parseDouble(group3));
                    addCentralPoint(stellar);
                } else if ((matcher = pattern2.matcher(lineString)).find()) {
                    String group1 = matcher.group(1), group2 = matcher.group(2), group3 = matcher.group(3),
                            group4 = matcher.group(4);
                    String group5 = matcher.group(5), group6 = matcher.group(6), group7 = matcher.group(7),
                            group8 = matcher.group(8);
                    if (!Pattern.matches("[0-9a-zA-Z]+", group1)) {
                        bfReader.close();
                        throw new FileGrammerException("行星名错误，行号：" + lineCount, 10);
                    }
                    if (!Pattern.matches("[0-9a-zA-Z]+", group2)) {
                        bfReader.close();
                        throw new FileGrammerException("行星状态错误，行号：" + lineCount, 10);
                    }
                    if (!Pattern.matches("[0-9a-zA-Z]+", group3)) {
                        bfReader.close();
                        throw new FileGrammerException("行星颜色错误，行号：" + lineCount, 10);
                    }
                    if (!Pattern.matches("([1-9][0-9]{1,3}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?)|[0]",
                            group4)) {
                        bfReader.close();
                        throw new FileGrammerException("行星半径错误，行号：" + lineCount, 10);
                    }
                    if (!Pattern.matches("([1-9][0-9]{1,3}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?)|[0]",
                            group5)) {
                        bfReader.close();
                        throw new FileGrammerException("轨道半径错误，行号：" + lineCount, 10);
                    }
                    if (!Pattern.matches("([1-9][0-9]{1,3}([.][0-9]+)?|[1-9]([.][0-9]+)?([e][+-]?[0-9]+)?)|[0]",
                            group6)) {
                        bfReader.close();
                        throw new FileGrammerException("公转速度错误，行号：" + lineCount, 10);
                    }
                    if (!Pattern.matches("(CW)|(CCW)", group7)) {
                        bfReader.close();
                        throw new FileGrammerException("公转方向错误，行号：" + lineCount, 10);
                    }
                    if (!Pattern.matches("[0-9]{1,3}([.][0-9]+)?", group8)) {
                        bfReader.close();
                        throw new FileGrammerException("初始角度错误，行号：" + lineCount, 10);
                    }
                    double angle = Double.parseDouble(group8);
                    if (angle < 0 || angle >= 360) {
                        bfReader.close();
                        throw new FileGrammerException("初始角度范围错误，行号：" + lineCount, 10);
                    }
                    StellarSystemObject planet = new StellarSystemObject(group1, group2, group3,
                            Double.parseDouble(group4), Double.parseDouble(group6), group7, angle);
                    planet.setTrackRadius(Double.parseDouble(group5));
                    Track<StellarSystemObject> track = getTrackByRadius(Double.parseDouble(group5));
                    if (track == null) {
                        track = new Track<>(Double.parseDouble(matcher.group(5)));
                    }
                    track.add(planet);
                    addTrack(track);
                } else {
                    if (!lineString.equals("")) {
                        bfReader.close();
                        throw new FileGrammerException("无用信息行，行数：" + lineCount, 10);
                    }
                }
            }
            if (count != 1) {
                bfReader.close();
                throw new CentralObjectException("恒星缺失或多余");
            }
            bfReader.close();
            sortTrack();
            checkRep();
        } catch (CentralObjectException e) {
            throw new FileChooseException("文件中心恒星错误，需要重新选择文件：" + e.getMessage());
        } catch (FileGrammerException e) {
            throw new FileChooseException("文件语法解析错误，需要重新选择文件：" + e.getMessage());
        }
    }

    public void checkRep() throws FileChooseException {
        try {
            // 判断中心天体存在且唯一
            if (getCentralPoint() == null) {
                throw new CentralObjectException("中心恒星缺失");
            }
            Set<String> labelSet = new HashSet<>();
            labelSet.add(getCentralPoint().getName());
            for (int i = 0; i < getTracksNumber(); i++) {
                Track<StellarSystemObject> track = getTrack(i + 1);
                // 判断是否一个轨道上有且只有一个行星
                if (track.getNumberOfObjects() != 1) {
                    throw new PhysicalObjectException("轨道上行星数目非法");
                }
                // 判断该系统中是否存在标签相同的情况
                StellarSystemObject planet = track.getTrackObjects().get(0);
                if (!labelSet.add(planet.getPlanetName())) {
                    throw new LabelSameException("系统中天体名存在相同情况");
                }
                // 判断是否存在两个轨道半径之差小于等于两个天体半径之和的情况
                double radiusSum = planet.getPlanetRadius();
                double trackRadiusDif = planet.getTrackRadius();
                radiusSum = (i == 0) ? radiusSum + getCentralPoint().getRadius()
                        : radiusSum + getTrack(i).getTrackObjects().get(0).getPlanetRadius();
                trackRadiusDif = (i == 0) ? trackRadiusDif
                        : trackRadiusDif - getTrack(i).getTrackObjects().get(0).getTrackRadius();
                if (trackRadiusDif < radiusSum) {
                    throw new StellarRadiusException("行星轨道半径之差小于两相邻星体轨道半径之和:" + (i + 1));
                }
            }
        } catch (CentralObjectException e) {
            throw new FileChooseException("checkRep接收到异常，需要重新选择文件：" + e.getMessage());
        } catch (PhysicalObjectException e) {
            throw new FileChooseException("checkRep接收到异常，需要重新选择文件：" + e.getMessage());
        } catch (LabelSameException e) {
            throw new FileChooseException("checkRep接收到异常，需要重新选择文件：" + e.getMessage());
        } catch (StellarRadiusException e) {
            throw new FileChooseException("checkRep接收到异常，需要重新选择文件：" + e.getMessage());
        }
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
        assert planet != null && newTime != null : logIn("参数错误：null");
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
        assert planet1 != null && planet2 != null : logIn("参数错误：null");
        double radius1 = planet1.getTrackRadius();
        double radius2 = planet2.getTrackRadius();
        double angleDif = planet1.getAngle() - planet2.getAngle();
        double distance = Math.pow(radius1, 2) + Math.pow(radius2, 2) - 2 * radius1 * radius2 * Math.cos(angleDif);
        return Math.pow(distance, 0.5);
    }

    /**
     * 得到恒星与某行星之间的物理距离
     * 
     * @param planet 待计算距离的行星;planet != null
     * @return 返回距离
     */
    public double getPhysicalDistanceStar(StellarSystemObject planet) {
        assert planet != null : logIn("参数错误：null");
        return planet.getTrackRadius();
    }

    private static String logIn(String message) {
        logger.severe(message);
        return "已将assert错误信息加载在日志文件里";
    }

}
