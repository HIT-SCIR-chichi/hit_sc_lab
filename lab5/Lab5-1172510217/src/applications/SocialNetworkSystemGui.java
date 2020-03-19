package applications;

import circularorbit.SocialNetworkCircle;
import iostrategy.BufferedIoStrategy;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import myexception.FileChooseException;
import myexception.FileGrammerException;
import physicalobject.Friend;
import track.Track;

public class SocialNetworkSystemGui {

  private static final Logger logger =
      Logger.getLogger(App.class.getSimpleName());

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
  private JButton button10 = new JButton();

  private SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
  File socialNetworkFile = new File("");

  private void changeSocialNetwork(int flag)
      throws IOException, FileChooseException {
    if (flag == 1) {
      socialNetworkFile = new File(
          "src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle.txt");
    } else if (flag == 2) {
      socialNetworkFile = new File(
          "src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle_Medium.txt");
    } else if (flag == 3) {
      socialNetworkFile = new File(
          "src/Spring2019_HITCS_SC_Lab3-master/SocialNetworkCircle_Larger.txt");
    } else {
      System.out.println("文件选取参数有误！");
    }
    socialNetworkCircle = new SocialNetworkCircle();
    socialNetworkCircle.setReadFile(socialNetworkFile);
    socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
  }

  private void changeSocialNetwork(String fileString) {
    if (fileString != null) {
      socialNetworkFile = new File(fileString);
      socialNetworkCircle = new SocialNetworkCircle();
    } else {
      return;
    }
    try {
      socialNetworkCircle.setReadFile(socialNetworkFile);
      socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
    } catch (FileChooseException e) {
      logger.log(Level.SEVERE, e.toString());
      String fileString1 =
          JOptionPane.showInputDialog("警告：选取的文件不合乎规范，请重新输入要选择的文件");
      logger.log(Level.INFO, "重新选择读取输入的文件：" + fileString1);
      changeSocialNetwork(fileString1);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "无法处理的IO异常");
    }
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
        super.paint(g);
        ImageIcon imageIcon = new ImageIcon("src/img/Social Network.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(getWidth(),
            getHeight(), Image.SCALE_AREA_AVERAGING));
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
          g2d.draw(new Ellipse2D.Double(x - d - 5, y - d - 5, 2 * d + 10,
              2 * d + 10));
          for (int j = 0;
              j < socialNetworkCircle.getTrack(i + 1).getNumberOfObjects();
              j++) {
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
            Friend friend1 = track.getIndexPhysicalObject(j + 1);
            for (int k = 0; k < friend1.getAllFriends().size(); k++) {
              Friend friend2 = friend1.getAllFriends().get(k);
              if (!friend2.equals(socialNetworkCircle.getCentralPoint())) {
                int fr2Radius = (int) friend2.getTrackRadius();
                int numOfFr2 = socialNetworkCircle.getTrackByRadius(fr2Radius)
                    .getPhysicalObjectIndex(friend2) - 1;
                g2d.drawLine(
                    x + (int) (Math.cos(numOfFr2 * 90) * (fr2Radius * r + 5)),
                    y + (int) (Math.sin(numOfFr2 * 90) * (fr2Radius * r + 5)),
                    x + (int) (Math.cos(j * 90) * (d + 5)),
                    y + (int) (Math.sin(j * 90) * (d + 5)));
              }
            }
          }
        }
      }
    };
  }

  private void initPanel2() {
    GridLayout gridLayout = new GridLayout(16, 1);
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
            logger.log(Level.INFO,
                "删除朋友：" + comboBox3.getSelectedItem().toString());
            String[] strings =
                comboBox3.getSelectedItem().toString().split(":");
            Friend friend = socialNetworkCircle.getFriendByName(strings[1]);
            socialNetworkCircle.removeFriend(friend);
            socialNetworkCircle.sortTrack();
            initFunc3();
            textField4.setText("熵值：" + socialNetworkCircle.getSystemEntropy());
            initFunc5();
            comboBox5.repaint();
            textField5.setText("查询朋友在第几层轨道上");
            initFunc6();
            comboBox6.repaint();
            panel1.updateUI();
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
            logger.log(Level.INFO,
                "查询朋友在第几层轨道上：" + comboBox5.getSelectedItem().toString());
            String[] strings =
                comboBox5.getSelectedItem().toString().split(":");
            Friend friend = socialNetworkCircle.getFriendByName(strings[1]);
            if ((int) friend.getTrackRadius() == -1) {
              logger.log(Level.WARNING, friend + "不可被认识！");
              textField5.setText(friend + "不可被认识");
            } else {
              logger.log(Level.INFO,
                  friend + "在第" + (int) friend.getTrackRadius() + "层轨道上");
              textField5.setText(
                  friend + "在第" + (int) friend.getTrackRadius() + "层轨道上");
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
            logger.info(
                "查询中心用户的好友信息扩散程度：" + comboBox6.getSelectedItem().toString());
            String[] strings =
                comboBox6.getSelectedItem().toString().split(":");
            Friend friend = socialNetworkCircle.getFriendByName(strings[1]);
            logger.info(friend + "的信息扩散程度为："
                + socialNetworkCircle.informationdiffusivity(friend));
            textField6.setText(friend + "的信息扩散程度为："
                + socialNetworkCircle.informationdiffusivity(friend));
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

    panel2.add(button10);
    initFunc10();
  }

  private void initFunc1() {
    logger.entering(getClass().getName(), "initFun1");
    comboBox1.addItem("小文件");
    comboBox1.addItem("中文件");
    comboBox1.addItem("大文件");
    comboBox1.addItemListener(new ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        try {
          if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getItem().equals("小文件")) {
              logger.log(Level.INFO, "读取小文件");
              changeSocialNetwork(1);
            } else if (e.getItem().equals("中文件")) {
              logger.log(Level.INFO, "读取中文件");
              changeSocialNetwork(2);
            } else if (e.getItem().equals("大文件")) {
              logger.log(Level.INFO, "读取大文件");
              changeSocialNetwork(3);
            }
            update();
            panel1.updateUI();
          }
        } catch (FileChooseException e1) {
          logger.log(Level.SEVERE, e1.getMessage());
          String fileString =
              JOptionPane.showInputDialog("警告：选取的文件不合乎规范，请重新输入要选择的文件");
          if (fileString != null) {
            logger.log(Level.INFO, "重新选择读取输入的文件：" + fileString);
            changeSocialNetwork(fileString);
          }
          panel1.updateUI();
          update();
        } catch (IOException e1) {
          logger.log(Level.SEVERE, "无法处理的IO异常");
        }

      }

      private void update() {
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
    });
  }

  private void initFunc2() {
    button2.setText("增加一个朋友");
    button2.setFocusable(false);
    textField2.setText("请输入一个朋友的信息：姓名 年龄 性别（M|F）");
    button2.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        try {
          Pattern pattern = Pattern.compile("(\\S+)[ ](\\S+)[ ](\\S+)[ ]");
          Matcher matcher = pattern.matcher(textField2.getText());
          logger.log(Level.INFO, "你的输入是：" + textField2.getText());
          if (matcher.find()) {
            if (!Pattern.matches("[A-Za-z0-9]+", matcher.group(1))) {
              throw new FileGrammerException("中心用户名非法");
            }
            if (!Pattern.matches("\\d+", matcher.group(2))) {
              throw new FileGrammerException("年龄非法");
            }
            if (!Pattern.matches("M|F", matcher.group(3))) {
              throw new FileGrammerException("性别非特定的两字符");
            }
            if (socialNetworkCircle.getFriendByName(matcher.group(1)) != null) {
              textField2.setText("该名字已被占用，请重新输入");
              logger.log(Level.WARNING, "该名字已被占用：" + matcher.group(1));
            } else {
              logger.log(Level.INFO, "朋友添加成功：" + matcher.group(1));
              Friend friend = new Friend();
              friend.setFriendName(matcher.group(1));
              friend.setAge(Integer.parseInt(matcher.group(2)));
              friend.setSex(matcher.group(3));
              socialNetworkCircle.addFriend(friend);
              textField2.setText(friend + "添加成功");
              initFunc3();
              textField4
                  .setText("熵值：" + socialNetworkCircle.getSystemEntropy());
              initFunc5();
              comboBox5.repaint();
              textField5.setText("查询朋友在第几层轨道上");
              initFunc6();
              comboBox6.repaint();
              panel1.updateUI();
              textField6.setText("查询中心用户某直接好友的扩散程度");
              textField7.setText("请输入要增加关系的两人姓名及亲密度：friend1 friend2 intimacy");
              textField8.setText("请输入要删除关系的两人姓名：friend1 friend2");
            }
          }
        } catch (FileGrammerException e2) {
          logger.log(Level.SEVERE, "输入格式非法");
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
        comboBox3.addItem(
            (++j) + ":" + socialNetworkCircle.getFriend(i + 1).getFriendName());
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
      if (!socialNetworkCircle.getFriend(i + 1)
          .equals(socialNetworkCircle.getCentralPoint())) {
        comboBox5.addItem(
            (++j) + ":" + socialNetworkCircle.getFriend(i + 1).getFriendName());
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
      comboBox6.addItem(
          (i + 1) + ":" + track.getIndexPhysicalObject(i + 1).getFriendName());
    }
  }

  private void initFunc7() {
    button7.setText("增加关系");
    button7.setFocusable(false);
    textField7.setText("请输入要增加关系的两人姓名及亲密度：friend1 friend2 intimacy");
    button7.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        logger.log(Level.INFO, "增加关系，你的输入是：" + textField7);
        String[] strings = textField7.getText().split(" ");
        if (strings.length == 3) {
          Friend friend1 = socialNetworkCircle.getFriendByName(strings[0]);
          Friend friend2 = socialNetworkCircle.getFriendByName(strings[1]);
          if (friend1 != null && friend2 != null) {
            logger.log(Level.INFO, "添加关系成功：" + friend1 + friend2);
            socialNetworkCircle.addRelationAndRefactor(friend1, friend2,
                Double.parseDouble(strings[2]));
            socialNetworkCircle.sortTrack();
            textField7.setText(friend1.toString() + friend2 + "已成功与添加关系！");
            initFunc6();
            comboBox6.repaint();
            panel1.updateUI();
          } else {
            logger.log(Level.WARNING, "输入用户不存在！");
            textField7.setText("输入用户不存在");
          }
        } else {
          logger.log(Level.SEVERE, "输入参数错误，请重新输入！");
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
        logger.log(Level.INFO, "删除关系，你的输入是：" + textField8);
        String[] strings = textField8.getText().split(" ");
        if (strings.length == 2) {
          Friend friend1 = socialNetworkCircle.getFriendByName(strings[0]);
          Friend friend2 = socialNetworkCircle.getFriendByName(strings[1]);
          if (friend1 != null && friend2 != null) {
            if (friend1.getAllFriends().contains(friend2)) {
              logger.log(Level.INFO, "删除关系成功！");
              socialNetworkCircle.deleteRelationAndRefactor(friend1, friend2);
              socialNetworkCircle.sortTrack();
              textField8.setText(friend1.toString() + friend2 + "已成功与删除关系！");
              initFunc6();
              comboBox6.repaint();
              panel1.updateUI();
            } else {
              logger.log(Level.WARNING, "输入两人之间无关系！");
              textField8.setText("输入参数错误：两人无关系！");
            }
          } else {
            logger.log(Level.WARNING, "输入用户不存在！");
            textField8.setText("输入用户不存在");
          }
        } else {
          logger.log(Level.SEVERE, "输入参数错误！");
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
        logger.log(Level.INFO, "重选游戏系统：退出社交系统");
        frame.dispose();
        App.main("   ".split(" "));
      }
    });
  }

  private void initFunc10() {
    button10.setText("将现在的系统信息导出到文件：以lab3语法要求的格式");
    button10.setFocusable(false);
    button10.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        try {
          socialNetworkCircle
              .setWriteFilePath("src/outputFile/SocialNetWork.txt");
          socialNetworkCircle.saveSystemInfoInFile(new BufferedIoStrategy());
          logger.info("成功将系统信息写入到默认文件:src/outputFile/SocialNetWork.txt");
        } catch (IOException e1) {
          logger.severe("将系统信息写入文件遇到IO异常");
        }
      }
    });
  }

  public void gui() {
    initFrame();
    frame.validate();
  }
}
