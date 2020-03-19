package applications;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import circularOrbit.StellarSystem;
import myException.FileChooseException;
import physicalObject.StellarSystemObject;
import track.Track;

public class StellarSystemGUI {

    private static final Logger logger = Logger.getLogger(App.class.getSimpleName());

    private JFrame frame = new JFrame();
    private JPanel panel1;
    private JPanel panel2 = new JPanel();
    private Map<String, Color> colorMap = new HashMap<String, Color>();
    private double time = 0;
    private int controlSpan = 0;

    private JComboBox<String> comboBox1 = new JComboBox<>();
    private JButton button2 = new JButton();
    private JComboBox<String> comboBox3 = new JComboBox<>();
    private JTextField textField4 = new JTextField();

    private JButton button5 = new JButton();
    private JTextField textField5 = new JTextField();

    private JButton button6 = new JButton();
    private JTextField textField6 = new JTextField();

    private JButton button7 = new JButton();

    private JButton button8 = new JButton();

    private StellarSystem stellarSystem = new StellarSystem();
    File stellarSystemFile = new File("");

    private void changeStellarSystem(int flag) throws FileChooseException, IOException {
        if (flag == 1) {
            stellarSystemFile = new File("src/Spring2019_HITCS_SC_Lab3-master/StellarSystem.txt");
        } else if (flag == 2) {
            stellarSystemFile = new File("src/Spring2019_HITCS_SC_Lab3-master/StellarSystem_Medium.txt");
        } else if (flag == 3) {
            stellarSystemFile = new File("src/Spring2019_HITCS_SC_Lab3-master/StellarSystem_Larger.txt");
        } else {
            System.out.println("文件选取参数有误！");
            System.exit(0);
        }
        stellarSystem = new StellarSystem();
        stellarSystem.readFileAndCreateSystem(stellarSystemFile);
    }

    private void changeStellarSystem(String fileString) {
        stellarSystemFile = new File(fileString);
        stellarSystem = new StellarSystem();
        try {
            stellarSystem.readFileAndCreateSystem(stellarSystemFile);
        } catch (FileChooseException e) {
            logger.log(Level.SEVERE, e.getMessage());
            String fileString1 = JOptionPane.showInputDialog("警告：选取的文件不合乎规范，请重新输入要选择的文件");
            if (fileString1 != null) {
                logger.log(Level.INFO, "重新选择读取输入的文件：" + fileString1);
                changeStellarSystem(fileString1);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "无法处理的IO异常");
        }
    }

    private void initFrame() {
        frame.setTitle("StellarSystem GUI @Author ZJR");
        frame.setLayout(new GridLayout());
        frame.setVisible(true);// 设为可见
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);// 设置窗口全屏
        frame.setDefaultCloseOperation(3);

        initPanel2();
        initPanel1();

        frame.add(panel1);
        frame.add(panel2);
    }

    private void initPanel1() {
        panel1 = new JPanel() {

            @Override public void paint(Graphics g) {
                panel1.updateUI();
                super.paint(g);
                panel1.setBackground(Color.DARK_GRAY);
                ImageIcon imageIcon = new ImageIcon("src/img/Stellar.jpg");
                imageIcon.setImage(
                        imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_AREA_AVERAGING));
                g.drawImage(imageIcon.getImage(), 0, 0, this);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) dimension.getWidth() / 4;
                int y = (int) (dimension.getHeight() - frame.getInsets().top) / 2;
                int count = stellarSystem.getTracksNumber();
                g.setColor(Color.green);
                double r = (double) (x - 10) / count;
                if (stellarSystem.getCentralPoint() != null) {
                    g.fillOval(x - 5, y - 5, 10, 10);
                }
                Graphics2D g2d = (Graphics2D) g;
                int direction = 0;
                for (int i = count - 1; i >= 0; i--) {
                    double d = r * (i + 1);
                    g2d.setColor(Color.orange);
                    g2d.draw(new Ellipse2D.Double(x - d - 5, y - d - 5, 2 * d + 10, 2 * d + 10));
                    for (int j = 0; j < stellarSystem.getTrack(i + 1).getNumberOfObjects(); j++) {
                        StellarSystemObject planet = stellarSystem.getTrack(i + 1).getTrackObjects().get(0);
                        String color = planet.getPlanetColor();
                        String directionString = planet.getRevolutionDiretion();
                        if (directionString.equals("CCW")) {
                            direction = -1;
                        } else if (directionString.equals("CW")) {
                            direction = 1;
                        } else {
                            System.out.println("错误旋转方向");
                        }
                        double angle = planet.getAngle();
                        double angleDif = direction * planet.getRevolutionSpeed() * 360
                                / (2 * Math.PI * planet.getTrackRadius());
                        g2d.setColor(colorMap.get(color));
                        g2d.draw(new Ellipse2D.Double(
                                x + Math.cos(angle + (controlSpan % 2) * time * angleDif) * (d + 5) - 5,
                                y + Math.sin(angle + (controlSpan % 2) * angleDif * time) * (d + 5) - 5, 10, 10));
                    }
                }

            }
        };
    }

    private void initPanel2() {
        GridLayout gridLayout = new GridLayout(12, 1);
        panel2.setLayout(gridLayout);
        JTextField textField = new JTextField();
        textField.setText("功能展示");
        textField.setFocusable(false);
        textField.setEditable(false);
        panel2.add(textField);

        // 功能一：文件生成
        comboBox1.addItem("文件生成");
        panel2.add(comboBox1);
        initFunc1();

        panel2.add(button2);
        initFunc2();

        // 功能三：删除特定序号的轨道
        panel2.add(comboBox3);
        initFunc3();
        comboBox3.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int num = comboBox3.getSelectedIndex();
                    if (num != 0) {
                        logger.log(Level.INFO, "删除轨道：" + num);
                        stellarSystem.deleteTrack(stellarSystem.getTrack(num));
                        panel1.repaint();
                        initFunc3();
                        comboBox3.repaint();
                        textField4.setText("熵值：" + stellarSystem.getSystemEntropy());
                    }
                }
            }
        });

        // 功能四：计算系统的熵值
        textField4.setText("熵值：");
        textField4.setEditable(false);
        panel2.add(textField4);

        // 功能五：计算某时刻某天体的角度位置
        panel2.add(button5);
        panel2.add(textField5);
        initFunc5();

        // 功能六：计算某两个天体之间的距离
        panel2.add(button6);
        panel2.add(textField6);
        initFunc6();

        panel2.add(button7);
        initFunc7();

        panel2.add(button8);
        initFunc8();
    }

    private void initFunc1() {
        comboBox1.addItem("小文件");
        comboBox1.addItem("中文件");
        comboBox1.addItem("大文件");
        comboBox1.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        if (e.getItem().equals("小文件")) {
                            logger.log(Level.INFO, "读取小文件");
                            changeStellarSystem(1);
                        } else if (e.getItem().equals("中文件")) {
                            logger.log(Level.INFO, "读取中文件");
                            changeStellarSystem(2);
                        } else if (e.getItem().equals("大文件")) {
                            logger.log(Level.INFO, "读取大文件");
                            changeStellarSystem(3);
                        }
                        initFunc3();
                        time = 0;
                        controlSpan = 0;
                        comboBox3.repaint();
                        textField4.setText("熵值：" + stellarSystem.getSystemEntropy());
                        stellarSystem.setReadTime(Calendar.getInstance());
                    } catch (FileChooseException e2) {
                        logger.log(Level.SEVERE, e2.getMessage());
                        String fileString = JOptionPane.showInputDialog("警告：选取的文件不合乎规范，请重新输入要选择的文件");
                        if (fileString != null) {
                            logger.log(Level.INFO, "重新选择读取输入的文件：" + fileString);
                            changeStellarSystem(fileString);
                            initFunc3();
                            time = 0;
                            controlSpan = 0;
                            comboBox3.repaint();
                            textField4.setText("熵值：" + stellarSystem.getSystemEntropy());
                            stellarSystem.setReadTime(Calendar.getInstance());
                        }
                    } catch (IOException e2) {
                        logger.log(Level.SEVERE, "无法处理的IO异常");
                    }
                }
            }
        });
    }

    private void initFunc2() {
        button2.setText("添加轨道/物体");
        button2.setFocusable(false);
        JTextField textField = new JTextField("按顺序输入：轨道半径、名称、形态、颜色、半径、公转速度、公转方向、起始角度");
        panel2.add(textField);
        button2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "添加轨道/物体：" + textField.getText());
                try {
                    String[] strings = textField.getText().split(" ");
                    if (stellarSystem.getTrackByRadius(Double.parseDouble(strings[0])) == null) {
                        Track<StellarSystemObject> track = new Track<>(Double.parseDouble(strings[0]));
                        StellarSystemObject planet = new StellarSystemObject(strings[1], strings[2], strings[3],
                                Double.parseDouble(strings[4]), Double.parseDouble(strings[5]), strings[6],
                                Double.parseDouble(strings[7]));
                        planet.setTrackRadius(Double.parseDouble(strings[0]));
                        stellarSystem.addTrack(track);
                        stellarSystem.addTrackObject(track, planet);
                        initFunc3();
                        comboBox3.repaint();
                        textField4.setText("熵值：" + stellarSystem.getSystemEntropy());
                    }
                } catch (Exception e2) {
                    logger.log(Level.SEVERE, "添加轨道/物体失败：输入不符合格式要求");
                    textField.setText("输入格式非法！");
                }

            }
        });

    }

    private void initFunc3() {
        comboBox3.removeAllItems();
        comboBox3.addItem("删除轨道");
        for (int i = 0; i < stellarSystem.getTracksNumber(); i++) {
            comboBox3.addItem("" + (i + 1));
        }
    }

    private void initFunc5() {
        button5.setText("计算位置");
        button5.setFocusable(false);
        textField5.setText("按顺序输入：物体序号 年 月 日 时 分 秒");
        button5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    String[] strings = textField5.getText().split(" ");
                    if (Integer.parseInt(strings[0]) <= stellarSystem.getTracksNumber()) {
                        Calendar targetTime = Calendar.getInstance();
                        targetTime.set(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]),
                                Integer.parseInt(strings[3]), Integer.parseInt(strings[4]),
                                Integer.parseInt(strings[5]), Integer.parseInt(strings[6]));
                        if (targetTime.before(stellarSystem.getReadTime())) {
                            System.out.println("输入时间错误：早于读取时间！");
                        } else {
                            String targetTimeString = strings[1] + "年" + strings[2] + "月" + strings[3] + "日"
                                    + strings[4] + "时" + strings[5] + "分" + strings[6] + "秒";
                            textField5.setText("物体" + strings[0] + "在指定时刻" + targetTimeString + "的角度位置是"
                                    + stellarSystem.calculatePosition(stellarSystem
                                            .getTrack(Integer.parseInt(strings[0])).getTrackObjects().get(0),
                                            targetTime)
                                    + "°");
                        }
                        logger.log(Level.INFO, "计算某时刻某行星的角度成功：" + textField5.getText());
                    }
                } catch (Exception e2) {
                    logger.log(Level.SEVERE, "计算某时刻某行星的角度失败：输入格式非法");
                    textField5.setText("输入格式非法！");
                }
            }

        });

    }

    private void initFunc6() {
        button6.setText("计算距离");
        button6.setFocusable(false);
        textField6.setText("输入序号：第一个天体 第二个天体（序号0表示中心天体）");
        button6.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    String[] strings = textField6.getText().split(" ");
                    int num1 = Integer.parseInt(strings[0]);
                    int num2 = Integer.parseInt(strings[1]);
                    if (num1 > stellarSystem.getTracksNumber() || num1 < 0 || num2 > stellarSystem.getTracksNumber()
                            || num2 < 0) {
                        textField6.setText("输入违法：超出范围！");
                    } else {
                        if (num1 == num2) {
                            System.out.println("输入违法：数值一样！");
                        } else {
                            if (num1 == 0) {
                                textField6
                                        .setText(
                                                "恒星" + "和行星" + num2 + "的距离是 "
                                                        + stellarSystem.getPhysicalDistanceStar(
                                                                stellarSystem.getTrack(num2).getTrackObjects().get(0))
                                                        + " KM");
                            } else if (num2 == 0) {
                                textField6
                                        .setText(
                                                "行星" + num1 + "和恒星的距离是 "
                                                        + stellarSystem.getPhysicalDistanceStar(
                                                                stellarSystem.getTrack(num1).getTrackObjects().get(0))
                                                        + " KM");
                            } else {
                                textField6.setText("行星" + num1 + "行星" + num2 + "的距离是 "
                                        + stellarSystem.getPhysicalDistance(
                                                stellarSystem.getTrack(num1).getTrackObjects().get(0),
                                                stellarSystem.getTrack(num2).getTrackObjects().get(0))
                                        + " KM");
                            }
                        }
                    }
                    logger.log(Level.INFO, "计算两颗星体的物理距离成功：" + textField6.getText());
                } catch (Exception e2) {
                    logger.log(Level.SEVERE, "计算两颗星体的物理距离失败：输入格式非法");
                    textField6.setText("输入格式非法！");
                }
            }
        });

    }

    private void initFunc7() {
        button7.setText("模拟天体运动");
        button7.setFocusable(false);
        button7.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                controlSpan++;
                if (controlSpan % 2 == 0) {
                    logger.log(Level.INFO, "行星系统动态化停止");
                } else {
                    logger.log(Level.INFO, "行星系统动态化");
                }
                move();
            }
        });
    }

    private void initFunc8() {
        button8.setText("重选游戏系统");
        button8.setFocusable(false);
        button8.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "重选游戏系统：退出行星系统");
                frame.dispose();
                App.main("   ".split(" "));
            }
        });
    }

    private void move() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override public void run() {
                panel1.updateUI();
                time += 0.01;
            }
        };
        timer.schedule(task, 0, 10);
    }

    private void initColorMap() {
        colorMap.put("Pink", Color.pink);
        colorMap.put("Grey", Color.gray);
        colorMap.put("Azure", new Color(240, 255, 255));
        colorMap.put("Blue", Color.blue);
        colorMap.put("Green", Color.green);
        colorMap.put("Red", Color.red);
        colorMap.put("Yellow", Color.yellow);
        colorMap.put("White", Color.white);
        colorMap.put("Dark", Color.darkGray);
        colorMap.put("Purple", new Color(128, 0, 128));
        colorMap.put("Orange", Color.orange);
    }

    public void GUI() {
        initColorMap();// 将颜色字符串与其对应的颜色对象一一对应起来
        initFrame();
        frame.validate();
    }
}
