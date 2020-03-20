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

import circularOrbit.AtomStructure;
import physicalObject.PhysicalObject;
import track.Track;

public class AtomStructureGUI {

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

    private AtomStructure atomStructure = new AtomStructure();
    File atomStructureFile = new File("");

    private void changeAtomStructure(int flag) throws IOException {
        if (flag == 1) {
            atomStructureFile = new File("src/Spring2019_HITCS_SC_Lab3-master/AtomicStructure.txt");
        } else if (flag == 2) {
            atomStructureFile = new File("src/Spring2019_HITCS_SC_Lab3-master/AtomicStructure_Medium.txt");
        } else {
            System.out.println("文件选取参数有误！");
            System.exit(0);
        }
        atomStructure = new AtomStructure();
        atomStructure.readFileAndCreateSystem(atomStructureFile);
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
                panel1.updateUI();
                super.paint(g);
                ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/img/Atom Structure.jpg"));
                imageIcon.setImage(
                        imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_AREA_AVERAGING));
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
                    g2d.draw(new Ellipse2D.Double(x - d - 5, y - d - 5, 2 * d + 10, 2 * d + 10));
                    for (int j = 0; j < atomStructure.getTrack(i + 1).getNumberOfObjects(); j++) {
                        g2d.setColor(Color.white);
                        g2d.draw(new Ellipse2D.Double(x + Math.cos((i + j) * 90) * (d + 5) - 5,
                                y + Math.sin((i + j) * 90) * (d + 5) - 5, 10, 10));
                    }
                }

            }
        };
    }

    private void initPanel2() {
        GridLayout gridLayout = new GridLayout(11, 1);
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
                        atomStructure.deleteTrack(atomStructure.getTrack(num));
                        panel1.repaint();
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
                        int trackNum = Integer.parseInt(comboBox5.getSelectedItem().toString());
                        atomStructure.deletePhysicalObject(atomStructure.getTrack(trackNum).getTrackObjects().get(0));
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

    }

    private void initFunc1() {
        comboBox1.addItem("小文件");
        comboBox1.addItem("中文件");
        comboBox1.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                try {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        if (e.getItem().equals("小文件")) {
                            changeAtomStructure(1);
                        } else if (e.getItem().equals("中文件")) {
                            changeAtomStructure(2);
                        }
                        initFunc3();
                        initFunc5();
                        textField4.setText("请在此处输入要增加电子的轨道序号");
                        textField6.setText("熵值：" + atomStructure.getSystemEntropy());
                        textField7.setText("请输入源轨道序号和目标轨道号：源轨道 目标轨道");
                    }
                } catch (Exception e2) {
                    System.out.println("未找到文件！");
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
                initFunc3();
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
                    int num = Integer.parseInt(textField4.getText());
                    if (num <= 0) {
                        textField4.setText("输入非法：输入数字小于等于0，超出范围");
                    } else {
                        PhysicalObject physicalObject = new PhysicalObject();
                        physicalObject.setTrackRadius(num);
                        atomStructure.addPhysicalObject(physicalObject);
                        initFunc3();
                        comboBox3.repaint();
                        initFunc5();
                        comboBox5.repaint();
                        textField4.setText("增加电子成功：已在第" + num + "层轨道增加了一个电子");
                        textField6.setText("熵值：" + atomStructure.getSystemEntropy());
                    }
                } catch (Exception exception) {
                    textField4.setText("输入格式错误！你的输入是：" + textField4.getText() + ",   应该是：数字");
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
                    String[] strings = textField7.getText().split(" ");
                    int num1 = Integer.parseInt(strings[0]);
                    int num2 = Integer.parseInt(strings[1]);
                    if (num1 <= 0 || num1 > atomStructure.getTracksNumber() || num2 <= 0) {
                        textField7.setText("输入错误：源轨道超出范围！");
                    } else {
                        Track<PhysicalObject> track = atomStructure.getTrackByRadius(num2);
                        if (atomStructure.getTrack(num1).getTrackObjects().size() == 0) {
                            textField7.setText("输入错误：源轨道上无电子！");
                        } else {
                            if (track == null) {
                                track = new Track<>(num2);
                                atomStructure.addTrack(track);
                                panel1.repaint();
                            }
                            atomStructure.transit(atomStructure.getTrack(num1).getTrackObjects().get(0), track);
                            initFunc3();
                            comboBox3.repaint();
                            initFunc5();
                            comboBox5.repaint();
                            textField6.setText("熵值：" + atomStructure.getSystemEntropy());
                        }
                    }
                } catch (Exception e2) {
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
                frame.dispose();
                App.main(null);
            }
        });
    }

    public void GUI() {
        initFrame();
        frame.validate();
    }

}
