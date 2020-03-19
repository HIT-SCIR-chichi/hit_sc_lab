package applications;

import circularorbit.AtomStructure;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import myexception.FileChooseException;
import physicalobject.PhysicalObject;
import track.Track;

public class AtomStructureGui {

  private static final Logger logger =
      Logger.getLogger(App.class.getSimpleName());

  private JFrame frame = new JFrame();
  private JPanel panel1 = new JPanel();
  private JPanel panel2 = new JPanel();

  private JComboBox<String> comboBox1 = new JComboBox<>();
  private JButton button2 = new JButton();
  private JComboBox<String> comboBox3 = new JComboBox<>();
  private JButton button4 = new JButton();
  private JTextField textField4 = new JTextField();
  private JComboBox<String> comboBox5 = new JComboBox<>();
  private JTextField textField6 = new JTextField();
  private JButton button7 = new JButton();
  private JTextField textField7 = new JTextField();
  private JButton button8 = new JButton();
  private JButton button9 = new JButton();

  private AtomStructure atomStructure = new AtomStructure();
  File atomStructureFile = new File("");

  private void changeAtomStructure(int flag)
      throws IOException, FileChooseException {
    if (flag == 1) {
      atomStructureFile =
          new File("src/Spring2019_HITCS_SC_Lab3-master/AtomicStructure.txt");
    } else if (flag == 2) {
      atomStructureFile = new File(
          "src/Spring2019_HITCS_SC_Lab3-master/AtomicStructure_Medium.txt");
    } else {
      System.out.println("文件选取参数有误！");
    }
    atomStructure = new AtomStructure();
    atomStructure.setReadFile(atomStructureFile);
    atomStructure.readFileAndCreateSystem(new BufferedIoStrategy());
  }

  private void changeAtomStructure(String fileString) {
    atomStructureFile = new File(fileString);
    atomStructure = new AtomStructure();
    try {
      atomStructure.setReadFile(atomStructureFile);
      atomStructure.readFileAndCreateSystem(new BufferedIoStrategy());
    } catch (IOException e) {
      logger.log(Level.SEVERE, "无法处理的IO异常");
    } catch (FileChooseException e) {
      logger.log(Level.SEVERE, e.getMessage());
      String fileString1 =
          JOptionPane.showInputDialog("警告：选取的文件不合乎规范，请重新输入要选择的文件");
      if (fileString1 != null) {
        logger.log(Level.INFO, "重新选择读取输入的文件：" + fileString1);
        changeAtomStructure(fileString1);
      }
    }
  }

  private void initFrame() {
    frame.setTitle("AtomStructure GUI @Author ZJR");
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
        ImageIcon imageIcon = new ImageIcon("src/img/Atom Structure.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(getWidth(),
            getHeight(), Image.SCALE_AREA_AVERAGING));
        g.drawImage(imageIcon.getImage(), 0, 0, this);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) dimension.getWidth() / 4;
        int y = (int) (dimension.getHeight() - frame.getInsets().top) / 2;
        int count = atomStructure.getTracksNumber();
        g.setColor(Color.green);
        double r = (double) (x - 10) / count;
        if (atomStructure.getCentralPoint() != null) {
          g.fillOval(x - 5, y - 5, 10, 10);
        }
        Graphics2D g2d = (Graphics2D) g;
        for (int i = count - 1; i >= 0; i--) {
          double d = r * (i + 1);
          g2d.setColor(Color.red);
          g2d.draw(new Ellipse2D.Double(x - d - 5, y - d - 5, 2 * d + 10,
              2 * d + 10));
          for (int j = 0;
              j < atomStructure.getTrack(i + 1).getNumberOfObjects(); j++) {
            g2d.setColor(Color.white);
            g2d.draw(
                new Ellipse2D.Double(x + Math.cos((i + j) * 90) * (d + 5) - 5,
                    y + Math.sin((i + j) * 90) * (d + 5) - 5, 10, 10));
          }
        }

      }
    };
  }

  private void initPanel2() {
    GridLayout gridLayout = new GridLayout(12, 1);
    panel2.setLayout(gridLayout);
    JLabel label = new JLabel("功能展示");
    panel2.add(label);

    // 功能一：文件生成
    comboBox1.addItem("文件生成");
    panel2.add(comboBox1);
    initFunc1();

    // 增加轨道
    panel2.add(button2);
    initFunc2();

    // 删除轨道
    panel2.add(comboBox3);
    initFunc3();
    comboBox3.addItemListener(new ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          int num = comboBox3.getSelectedIndex();
          if (num != 0) {
            logger.log(Level.INFO, "删除轨道：" + num);
            atomStructure.deleteTrack(atomStructure.getTrack(num));
            atomStructure.sortTrack();
            panel1.updateUI();
            initFunc3();
            initFunc5();

          }
        }
      }
    });

    // 增加物体
    panel2.add(button4);
    panel2.add(textField4);
    initFunc4();

    panel2.add(comboBox5);
    initFunc5();
    comboBox5.addItemListener(new ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          int num = comboBox5.getSelectedIndex();
          if (num != 0) {
            logger.log(Level.INFO, "删除电子，轨道号：" + num);
            int trackNum =
                Integer.parseInt(comboBox5.getSelectedItem().toString());
            Track<PhysicalObject> track = atomStructure.getTrack(trackNum);
            atomStructure.deletePhysicalObject(
                track.getIndexPhysicalObject(track.getNumberOfObjects()));
            atomStructure.sortTrack();
            panel1.updateUI();
            initFunc3();
            initFunc5();
          }
        }
      }
    });

    panel2.add(textField6);
    initFunc6();

    panel2.add(button7);
    panel2.add(textField7);
    initFunc7();

    panel2.add(button8);
    initFunc8();

    panel2.add(button9);
    initFunc9();

  }

  private void initFunc1() {
    comboBox1.addItem("小文件");
    comboBox1.addItem("中文件");
    comboBox1.addItemListener(new ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        try {
          if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getItem().equals("小文件")) {
              logger.log(Level.INFO, "读取小文件");
              changeAtomStructure(1);
            } else if (e.getItem().equals("中文件")) {
              logger.log(Level.INFO, "读取中文件");
              changeAtomStructure(2);
            }
            panel1.updateUI();
            initFunc3();
            initFunc5();
            textField4.setText("请在此处输入要增加电子的轨道序号");
            textField6.setText("熵值：" + atomStructure.getSystemEntropy());
            textField7.setText("请输入源轨道序号和目标轨道号：源轨道 目标轨道");
          }
        } catch (FileChooseException e1) {
          logger.log(Level.SEVERE, e1.getMessage());
          String fileString =
              JOptionPane.showInputDialog("警告：选取的文件不合乎规范，请重新输入要选择的文件");
          if (fileString != null) {
            logger.log(Level.INFO, "重新选择读取输入的文件：" + fileString);
            changeAtomStructure(fileString);
            initFunc3();
            initFunc5();
            panel1.updateUI();
            textField4.setText("请在此处输入要增加电子的轨道序号");
            textField6.setText("熵值：" + atomStructure.getSystemEntropy());
            textField7.setText("请输入源轨道序号和目标轨道号：源轨道 目标轨道");
          }
        } catch (IOException e1) {
          logger.log(Level.SEVERE, "无法处理的IO异常");
        }

      }
    });
  }

  private void initFunc2() {
    button2.setText("增加轨道");
    button2.setFocusable(false);
    button2.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        int radius = atomStructure.getTracksNumber() + 1;
        atomStructure.addTrack(new Track<>(radius));
        atomStructure.sortTrack();
        initFunc3();
        panel1.updateUI();
        comboBox3.repaint();
        textField6.setText("熵值：" + atomStructure.getSystemEntropy());
      }
    });
  }

  private void initFunc3() {
    comboBox3.removeAllItems();
    comboBox3.addItem("删除轨道");
    for (int i = 0; i < atomStructure.getTracksNumber(); i++) {
      comboBox3.addItem("" + (i + 1));
    }
    textField6.setText("熵值：" + atomStructure.getSystemEntropy());
  }

  private void initFunc4() {
    button4.setText("增加电子");
    button4.setFocusable(false);
    textField4.setText("请在此处输入要增加电子的轨道序号");
    button4.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        try {
          logger.log(Level.INFO, "你的输入是：" + textField4.getText());
          int num = Integer.parseInt(textField4.getText());
          if (num <= 0) {
            logger.log(Level.WARNING, "输入非法：输入数字小于等于0");
            textField4.setText("输入非法：输入数字小于等于0，超出范围");
          } else {
            PhysicalObject physicalObject = new PhysicalObject();
            physicalObject.setTrackRadius(num);
            atomStructure.addPhysicalObject(physicalObject);
            atomStructure.sortTrack();
            initFunc3();
            comboBox3.repaint();
            initFunc5();
            comboBox5.repaint();
            logger.log(Level.INFO, "增加电子成功：已在第" + num + "层轨道增加了一个电子");
            textField4.setText("增加电子成功：已在第" + num + "层轨道增加了一个电子");
            textField6.setText("熵值：" + atomStructure.getSystemEntropy());
          }
        } catch (Exception exception) {
          logger.log(Level.SEVERE, "输入格式错误");
          textField4
              .setText("输入格式错误！你的输入是：" + textField4.getText() + ",   应该是：数字");
        }
      }
    });
  }

  private void initFunc5() {
    comboBox5.removeAllItems();
    comboBox5.addItem("删除电子");
    for (int i = 0; i < atomStructure.getTracksNumber(); i++) {
      if (atomStructure.getTrack(i + 1).getNumberOfObjects() != 0) {
        comboBox5.addItem("" + (i + 1));
        textField6.setText("熵值：" + atomStructure.getSystemEntropy());
      }
    }
  }

  private void initFunc6() {
    textField6.setText("熵值：0");
    textField6.setEditable(false);
  }

  private void initFunc7() {
    button7.setText("模拟跃迁");
    button7.setFocusable(false);
    textField7.setText("请输入源轨道序号和目标轨道号：源轨道 目标轨道");
    button7.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        try {
          logger.log(Level.INFO, "模拟电子跃迁，你的输入是：" + textField7.getText());
          String[] strings = textField7.getText().split(" ");
          int num1 = Integer.parseInt(strings[0]);
          int num2 = Integer.parseInt(strings[1]);
          if (num1 <= 0 || num1 > atomStructure.getTracksNumber()
              || num2 <= 0) {
            logger.log(Level.WARNING, "输入错误：源轨道超出范围！");
            textField7.setText("输入错误：源轨道超出范围！");
          } else {
            Track<PhysicalObject> track = atomStructure.getTrackByRadius(num2);
            if (atomStructure.getTrack(num1).getNumberOfObjects() == 0) {
              textField7.setText("输入错误：源轨道上无电子！");
              logger.log(Level.WARNING, "输入错误：源轨道上无电子！");
            } else {
              if (track == null) {
                track = new Track<>(num2);
                atomStructure.addTrack(track);
                panel1.repaint();
              }
              Track<PhysicalObject> track2 = atomStructure.getTrack(num1);
              atomStructure.transit(
                  track2.getIndexPhysicalObject(track2.getNumberOfObjects()),
                  track);
              atomStructure.sortTrack();
              initFunc3();
              panel1.updateUI();
              comboBox3.repaint();
              initFunc5();
              comboBox5.repaint();
              textField6.setText("熵值：" + atomStructure.getSystemEntropy());
            }
          }
        } catch (Exception e2) {
          logger.log(Level.SEVERE, "输入错误：未按格式输入！");
          textField7.setText("输入错误：未按格式输入！");
        }
      }

    });
  }

  private void initFunc8() {
    button8.setText("重选游戏系统");
    button8.setFocusable(false);
    button8.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        logger.log(Level.INFO, "重选游戏系统，退出原子系统");
        frame.dispose();
        App.main("   ".split(" "));
      }
    });
  }

  private void initFunc9() {
    button9.setText("将现在的系统信息导出到文件：以lab3语法要求的格式");
    button9.setFocusable(false);
    button9.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        try {
          atomStructure.setWriteFilePath("src/outputFile/AtomStructure.txt");
          atomStructure.saveSystemInfoInFile(new BufferedIoStrategy());
          logger.info("成功将系统信息写入到默认文件:src/outputFile/AtomStructure.txt");
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
