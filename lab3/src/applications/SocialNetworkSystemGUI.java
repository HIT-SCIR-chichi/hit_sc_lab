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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import circularOrbit.SocialNetworkCircle;
import physicalObject.Friend;
import track.Track;

public class SocialNetworkSystemGUI {

    private JFrame frame = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();

    private JComboBox<String> comboBox1 = new JComboBox<String>();
    private JButton button2 = new JButton();
    private JTextField textField2 = new JTextField();
    private JComboBox<String> comboBox3 = new JComboBox<>();
    private JTextField textField4 = new JTextField();
    private JComboBox<String> comboBox5 = new JComboBox<>();
    private JTextField textField5 = new JTextField();
    private JComboBox<String> comboBox6 = new JComboBox<>();
    private JTextField textField6 = new JTextField();
    private JButton button7 = new JButton();
    private JTextField textField7 = new JTextField();
    private JButton button8 = new JButton();
    private JTextField textField8 = new JTextField();
    private JButton button9 = new JButton();

    private SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    File socialNetworkFile = new File("");

    private void changeAtomStructure(int flag) throws IOException {
        if (flag == 1) {
            socialNetworkFile = new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt");
        } else if (flag == 2) {
            socialNetworkFile = new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle_Medium.txt");
        } else if (flag == 3) {
            socialNetworkFile = new File("src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle_Larger.txt");
        } else {
            System.out.println("文件选取参数有误！");
            System.exit(0);
        }
        socialNetworkCircle = new SocialNetworkCircle();
        socialNetworkCircle.readFileAndCreateSystem(socialNetworkFile);
    }

    private void initFrame() {
        frame.setTitle("SocialNetwork System GUI @Author ZJR");
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
                ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/img/Social Network.jpg"));
                imageIcon.setImage(
                        imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_AREA_AVERAGING));
                g.drawImage(imageIcon.getImage(), 0, 0, this);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) dimension.getWidth() / 4;
                int y = (int) (dimension.getHeight() - frame.getInsets().top) / 2;
                int count = socialNetworkCircle.getTracksNumber();
                g.setColor(Color.green);
                double r = (double) (x - 10) / count;
                if (socialNetworkCircle.getCentralPoint() != null) {
                    g.fillOval(x - 5, y - 5, 10, 10);
                }
                Graphics2D g2d = (Graphics2D) g;
                for (int i = count - 1; i >= 0; i--) {
                    double d = r * (i + 1);
                    g2d.setColor(Color.orange);
                    g2d.draw(new Ellipse2D.Double(x - d - 5, y - d - 5, 2 * d + 10, 2 * d + 10));
                    for (int j = 0; j < socialNetworkCircle.getTrack(i + 1).getNumberOfObjects(); j++) {
                        g2d.setColor(Color.green);
                        g2d.draw(new Ellipse2D.Double(x + Math.cos(j * 90) * (d + 5) - 5,
                                y + Math.sin(j * 90) * (d + 5) - 5, 10, 10));
                    }
                }
                g2d.setColor(Color.red);
                for (int i = 0; i < socialNetworkCircle.getTracksNumber(); i++) {
                    Track<Friend> track = socialNetworkCircle.getTrack(i + 1);
                    double d = r * (i + 1);
                    if (i == 0) {
                        for (int j = 0; j < track.getNumberOfObjects(); j++) {
                            g2d.drawLine(x, y, x + (int) (Math.cos(j * 90) * (r + 5)),
                                    y + (int) (Math.sin(j * 90) * (r + 5)));
                        }
                    }
                    for (int j = 0; j < track.getNumberOfObjects(); j++) {
                        Friend friend1 = track.getTrackObjects().get(j);
                        for (int k = 0; k < friend1.getAllFriends().size(); k++) {
                            Friend friend2 = friend1.getAllFriends().get(k);
                            if (!friend2.equals(socialNetworkCircle.getCentralPoint())) {
                                int fr2Radius = (int) friend2.getTrackRadius();
                                int numOfFr2 = socialNetworkCircle.getTrackByRadius(fr2Radius)
                                        .getPhysicalObjectIndex(friend2);
                                g2d.drawLine(x + (int) (Math.cos(numOfFr2 * 90) * (fr2Radius * r + 5)),
                                        y + (int) (Math.sin(numOfFr2 * 90) * (fr2Radius * r + 5)),
                                        x + (int) (Math.cos(j * 90) * (d + 5)), y + (int) (Math.sin(j * 90) * (d + 5)));
                            }
                        }
                    }
                }

            }
        };
    }

    private void initPanel2() {
        GridLayout gridLayout = new GridLayout(15, 1);
        panel2.setLayout(gridLayout);
        JLabel label = new JLabel("功能展示");
        panel2.add(label);

        // 功能一：文件生成
        comboBox1.addItem("文件生成");
        panel2.add(comboBox1);
        initFunc1();

        // 增加朋友，必须输入所有的参数
        panel2.add(button2);
        panel2.add(textField2);
        initFunc2();

        // 删除朋友，要删除所有的关系
        panel2.add(comboBox3);
        initFunc3();
        comboBox3.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (comboBox3.getSelectedIndex() != 0) {
                        String[] strings = comboBox3.getSelectedItem().toString().split(":");
                        Friend friend = socialNetworkCircle.getFriendByName(strings[1]);
                        socialNetworkCircle.removeFriend(friend);
                        initFunc3();
                        textField4.setText("熵值：" + socialNetworkCircle.getSystemEntropy());
                        initFunc5();
                        comboBox5.repaint();
                        textField5.setText("查询朋友在第几层轨道上");
                        initFunc6();
                        comboBox6.repaint();
                        textField7.setText("请输入要增加关系的两人姓名及亲密度：friend1 friend2 intimacy");
                        textField8.setText("请输入要删除关系的两人姓名：friend1 friend2");
                    }
                }
            }
        });

        panel2.add(textField4);
        textField4.setEditable(false);
        textField4.setText("熵值：0");

        panel2.add(comboBox5);
        panel2.add(textField5);
        textField5.setEditable(false);
        textField5.setText("查询朋友在第几层轨道上");
        initFunc5();
        comboBox5.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (comboBox5.getSelectedIndex() != 0) {
                        String[] strings = comboBox5.getSelectedItem().toString().split(":");
                        Friend friend = socialNetworkCircle.getFriendByName(strings[1]);
                        if ((int) friend.getTrackRadius() == -1) {
                            textField5.setText(friend + "不可被认识");
                        } else {
                            textField5.setText(friend + "在第" + (int) friend.getTrackRadius() + "层轨道上");
                        }
                        textField7.setText("请输入要增加关系的两人姓名及亲密度：friend1 friend2 intimacy");
                        textField8.setText("请输入要删除关系的两人姓名：friend1 friend2");
                    }
                }
            }
        });

        panel2.add(comboBox6);
        panel2.add(textField6);
        textField6.setEditable(false);
        textField6.setText("查询中心用户某直接好友的扩散程度");
        initFunc6();
        comboBox6.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (comboBox6.getSelectedIndex() != 0) {
                        String[] strings = comboBox6.getSelectedItem().toString().split(":");
                        Friend friend = socialNetworkCircle.getFriendByName(strings[1]);
                        textField6.setText(friend + "的信息扩散程度为：" + socialNetworkCircle.Informationdiffusivity(friend));
                        textField7.setText("请输入要增加关系的两人姓名及亲密度：friend1 friend2 intimacy");
                        textField8.setText("请输入要删除关系的两人姓名：friend1 friend2");
                    }
                }
            }
        });

        panel2.add(button7);
        panel2.add(textField7);
        initFunc7();

        panel2.add(button8);
        panel2.add(textField8);
        initFunc8();

        panel2.add(button9);
        initFunc9();
    }

    private void initFunc1() {
        comboBox1.addItem("小文件");
        comboBox1.addItem("中文件");
        comboBox1.addItem("大文件");
        comboBox1.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                try {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        if (e.getItem().equals("小文件")) {
                            changeAtomStructure(1);
                        } else if (e.getItem().equals("中文件")) {
                            changeAtomStructure(2);
                        } else if (e.getItem().equals("大文件")) {
                            changeAtomStructure(3);
                        }
                        initFunc3();
                        textField2.setText("请输入一个朋友的信息：姓名 年龄 性别（M|F）");
                        comboBox3.repaint();
                        textField4.setText("熵值：" + socialNetworkCircle.getSystemEntropy());
                        initFunc5();
                        comboBox5.repaint();
                        textField5.setText("查询朋友在第几层轨道上");
                        initFunc6();
                        comboBox6.repaint();
                        textField6.setText("查询中心用户某直接好友的扩散程度");
                        textField7.setText("请输入要增加关系的两人姓名及亲密度：friend1 friend2 intimacy");
                        textField8.setText("请输入要删除关系的两人姓名：friend1 friend2");
                    }
                } catch (Exception e2) {
                    System.out.println("未找到文件！");
                    e2.printStackTrace();
                }

            }
        });
    }

    private void initFunc2() {
        button2.setText("增加一个朋友");
        button2.setFocusable(false);
        textField2.setText("请输入一个朋友的信息：姓名 年龄 性别（M|F）");
        button2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    String[] strings = textField2.getText().split(" ");
                    if (socialNetworkCircle.getFriendByName(strings[0]) != null) {
                        textField2.setText("该名字已被占用，请重新输入");
                    } else {
                        Friend friend = new Friend();
                        friend.setFriendName(strings[0]);
                        friend.setAge(Integer.parseInt(strings[1]));
                        friend.setSex(strings[2]);
                        socialNetworkCircle.addFriend(friend);
                        textField2.setText(friend + "添加成功");
                        initFunc3();
                        textField4.setText("熵值：" + socialNetworkCircle.getSystemEntropy());
                        initFunc5();
                        comboBox5.repaint();
                        textField5.setText("查询朋友在第几层轨道上");
                        initFunc6();
                        comboBox6.repaint();
                        textField6.setText("查询中心用户某直接好友的扩散程度");
                        textField7.setText("请输入要增加关系的两人姓名及亲密度：friend1 friend2 intimacy");
                        textField8.setText("请输入要删除关系的两人姓名：friend1 friend2");
                    }
                } catch (Exception e2) {
                    textField2.setText("输入格式非法！");
                }
            }
        });
    }

    private void initFunc3() {
        comboBox3.removeAllItems();
        comboBox3.addItem("删除朋友");
        for (int i = 0, j = 0; i < socialNetworkCircle.getFriendNum(); i++) {
            if (!socialNetworkCircle.getFriend(i + 1).getFriendName()
                    .equals(socialNetworkCircle.getCentralPoint().getFriendName())) {
                comboBox3.addItem((++j) + ":" + socialNetworkCircle.getFriend(i + 1).getFriendName());
            }
        }
        textField4.setText("熵值：" + socialNetworkCircle.getSystemEntropy());
        textField6.setText("查询中心用户某直接好友的扩散程度");
        textField7.setText("请输入要增加关系的两人姓名及亲密度：friend1 friend2 intimacy");
        textField8.setText("请输入要删除关系的两人姓名：friend1 friend2");
    }

    private void initFunc5() {
        comboBox5.removeAllItems();
        comboBox5.addItem("判断轨道");
        for (int i = 0, j = 0; i < socialNetworkCircle.getFriendNum(); i++) {
            if (!socialNetworkCircle.getFriend(i + 1).equals(socialNetworkCircle.getCentralPoint())) {
                comboBox5.addItem((++j) + ":" + socialNetworkCircle.getFriend(i + 1).getFriendName());
            }
        }
    }

    private void initFunc6() {
        comboBox6.removeAllItems();
        comboBox6.addItem("关系扩散");
        if (socialNetworkCircle.getTracksNumber() == 0) {
            return;
        }
        Track<Friend> track = socialNetworkCircle.getTrack(1);
        for (int i = 0; i < track.getNumberOfObjects(); i++) {
            comboBox6.addItem((i + 1) + ":" + track.getTrackObjects().get(i).getFriendName());
        }
    }

    private void initFunc7() {
        button7.setText("增加关系");
        button7.setFocusable(false);
        textField7.setText("请输入要增加关系的两人姓名及亲密度：friend1 friend2 intimacy");
        button7.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String[] strings = textField7.getText().split(" ");
                if (strings.length == 3) {
                    Friend friend1 = socialNetworkCircle.getFriendByName(strings[0]);
                    Friend friend2 = socialNetworkCircle.getFriendByName(strings[1]);
                    if (friend1 != null && friend2 != null) {
                        socialNetworkCircle.addRelationAndRefactor(friend1, friend2, Double.parseDouble(strings[2]));
                        textField7.setText(friend1.toString() + friend2 + "已成功与添加关系！");
                        initFunc6();
                        comboBox6.repaint();
                    } else {
                        textField7.setText("输入用户不存在");
                    }
                } else {
                    textField7.setText("输入参数错误，请重新输入！");
                }
                textField8.setText("请输入要删除关系的两人姓名：friend1 friend2");
            }
        });
    }

    private void initFunc8() {
        button8.setText("删除关系");
        button8.setFocusable(false);
        textField8.setText("请输入要删除关系的两人姓名：friend1 friend2");
        button8.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String[] strings = textField8.getText().split(" ");
                if (strings.length == 2) {
                    Friend friend1 = socialNetworkCircle.getFriendByName(strings[0]);
                    Friend friend2 = socialNetworkCircle.getFriendByName(strings[1]);
                    if (friend1 != null && friend2 != null) {
                        if (friend1.getAllFriends().contains(friend2)) {
                            socialNetworkCircle.deleteRelationAndRefactor(friend1, friend2);
                            textField8.setText(friend1.toString() + friend2 + "已成功与删除关系！");
                            initFunc6();
                            comboBox6.repaint();
                        } else {
                            textField8.setText("输入参数错误：两人无关系！");
                        }
                    } else {
                        textField8.setText("输入用户不存在");
                    }
                } else {
                    textField8.setText("输入参数错误，请重新输入！");
                }
            }
        });
    }

    private void initFunc9() {
        button9.setText("重选游戏系统");
        button9.setFocusable(false);
        button9.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                App.main(null);
            }
        });
    }

    public void GUI() {
        initFrame();
        frame.validate();
    }

    @SuppressWarnings("unused") private static void showSocialNetworkSystem() {
        System.out.println("11，删除关系\t12，重选系统");
    }
}
