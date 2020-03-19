package gui;

import adt.Monkey;
import adt.SystemConstant;
import factory.LadderFactory;
import factory.MonkeyCrossRiverSystem;
import factory.MonkeyFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Gui {

  private static JFrame frame = new JFrame();
  private static JPanel panel1 = new JPanel();
  private static JScrollPane scrollPane2 = new JScrollPane();
  private static JTextArea textArea2 = new JTextArea();
  private static JPanel panel3 = new JPanel();

  private static JLabel label33 = new JLabel();// 吞吐率展示标签和公平性展示标签
  private static JLabel label34 = new JLabel();// 一次产生的猴子数目以及间隔时间
  private static JLabel label35 = new JLabel();// 猴子总数和梯子总数
  private static JLabel label36 = new JLabel();// 猴子最大速度和梯子长度
  private static JLabel label37 = new JLabel();// 已产生猴子和已过河猴子

  private static void initFrame() {
    frame.setTitle("Monkey Cross River GUI @Author ZJR");
    frame.setLayout(null);
    frame.setVisible(true);// 设为可见
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);// 设置窗口全屏
    frame.setDefaultCloseOperation(3);

    initPanel1();
    initPanel2();
    initPanel3();
    initFunc1();

    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) dimension.getWidth() / 2;
    int y = (int) dimension.getHeight() - frame.getInsets().top;
    frame.add(panel1);
    panel1.setBounds(0, 0, x, y);
    frame.add(scrollPane2);
    scrollPane2.setBounds(x, y / 2, x, y / 2);
    frame.add(panel3);
    panel3.setBounds(x, 0, x, y / 2);
  }

  private static void initPanel1() {
    panel1 = new JPanel() {

      @Override public void paint(Graphics g) {
        super.paint(g);
        ImageIcon imageIcon = new ImageIcon("src/img/System Background.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(getWidth(), getHeight(),
            Image.SCALE_AREA_AVERAGING));
        g.drawImage(imageIcon.getImage(), 0, 0, this);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) dimension.getWidth() / 2;// 屏幕宽度的一半
        int y = (int) dimension.getHeight() - frame.getInsets().top;// 屏幕高度减去顶部信息长度
        int riverWidth = x - 100;
        int riverlength = y - 100;
        g.setColor(Color.GRAY);
        g.drawLine((x - riverWidth) / 2, 0, (x - riverWidth) / 2, y);// 左河岸
        g.drawLine((x + riverWidth) / 2, 0, (x + riverWidth) / 2, y);// 右河岸
        int ladderSize = MonkeyCrossRiverSystem.getLadderNum();
        int ladderLength = MonkeyCrossRiverSystem.getLength();
        double lined1 = riverlength / (double) (ladderSize - 1);// 梯子之间的间隔
        double lined2 = riverWidth / (double) (ladderLength + 1);// 踏板之间的间隔
        // 开始画所有的梯子
        g.setColor(Color.YELLOW);
        for (int i = 0; i < ladderSize; i++) {
          g.drawLine((x - riverWidth) / 2, (int) ((y - riverlength) / 2 + i * lined1),
              (x + riverWidth) / 2, (int) ((y - riverlength) / 2 + i * lined1));
        }
        // 开始画所有的踏板
        g.setColor(Color.magenta);
        for (int i = 1; i <= ladderLength; i++) {
          g.drawLine((x - riverWidth) / 2 + (int) (i * lined2), (y - riverlength) / 2,
              (x - riverWidth) / 2 + (int) (i * lined2), (y + riverlength) / 2);
        }
        // 开始画所有在梯子踏板上的猴子及其编号
        g.setColor(Color.CYAN);
        int monkeyNum = MonkeyFactory.getMonkeyNum();
        int monkeyImagSize = 40;// 猴子图片的大小
        ImageIcon monkeyIcon1 = new ImageIcon("src/img/MonkeyLR.jpg");
        monkeyIcon1.setImage(monkeyIcon1.getImage().getScaledInstance(monkeyImagSize,
            monkeyImagSize, Image.SCALE_AREA_AVERAGING));
        ImageIcon monkeyIcon2 = new ImageIcon("src/img/MonkeyRL.jpg");
        monkeyIcon2.setImage(monkeyIcon2.getImage().getScaledInstance(monkeyImagSize,
            monkeyImagSize, Image.SCALE_AREA_AVERAGING));
        for (int i = 1; i <= monkeyNum; i++) {
          Integer ladderId = LadderFactory.getMonkeyLadder(i);
          if (ladderId != null) {
            Integer rungId = LadderFactory.getMonkeyRung(i);
            if (MonkeyFactory.getMonkeyById(i).getDirection().equals(1)) {
              g.drawImage(monkeyIcon2.getImage(),
                  (int) ((x - riverWidth) / 2 + rungId * lined2) - monkeyImagSize / 2,
                  (int) ((y - riverlength) / 2 + (ladderId - 1) * lined1)
                      - monkeyImagSize / 2,
                  this);
              g.drawString(String.valueOf(i),
                  (int) ((x - riverWidth) / 2 + rungId * lined2) - monkeyImagSize / 3 * 2,
                  (int) ((y - riverlength) / 2 + (ladderId - 1) * lined1));
            } else {
              g.drawImage(monkeyIcon1.getImage(),
                  (int) ((x - riverWidth) / 2 + rungId * lined2) - monkeyImagSize / 2,
                  (int) ((y - riverlength) / 2 + (ladderId - 1) * lined1)
                      - monkeyImagSize / 2,
                  this);
              g.drawString(String.valueOf(i),
                  (int) ((x - riverWidth) / 2 + rungId * lined2) + monkeyImagSize / 3 * 2,
                  (int) ((y - riverlength) / 2 + (ladderId - 1) * lined1));
            }
          }
        }
      }
    };
  }

  private static void initPanel2() {
    scrollPane2.setViewportView(textArea2);
    textArea2.setFont(new Font(null, Font.PLAIN, 18));
    textArea2.setEditable(false);
  }

  private static void initPanel3() {
    panel3.setLayout(new GridLayout(5, 1));
    panel3.add(label33);
    panel3.add(label34);
    panel3.add(label35);
    panel3.add(label36);
    panel3.add(label37);
    label33.setFont(new Font(null, Font.PLAIN, 18));
    label34.setFont(new Font(null, Font.PLAIN, 18));
    label35.setFont(new Font(null, Font.PLAIN, 18));
    label36.setFont(new Font(null, Font.PLAIN, 18));
    label37.setFont(new Font(null, Font.PLAIN, 18));
    label33.setHorizontalAlignment(SwingConstants.CENTER);
    label34.setHorizontalAlignment(SwingConstants.CENTER);
    label35.setHorizontalAlignment(SwingConstants.CENTER);
    label36.setHorizontalAlignment(SwingConstants.CENTER);
    label37.setHorizontalAlignment(SwingConstants.CENTER);
    label33.setText("吞吐率：结束展示" + " 公平性：结束展示");
    label34.setText("相隔时间：" + MonkeyCrossRiverSystem.getTime() + " 产生数目："
        + MonkeyCrossRiverSystem.getNum());
    label35.setText("猴子总数：" + MonkeyCrossRiverSystem.getMonkeyNum() + " 梯子总数："
        + MonkeyCrossRiverSystem.getLadderNum());
    label36.setText("梯子长度：" + MonkeyCrossRiverSystem.getLength() + " 最大速度："
        + MonkeyCrossRiverSystem.getMaxSpeed());
    label37.setText("过河猴子：0" + " 产生猴子：0");
  }

  /**
   * in order to :刷新画板.
   */
  private static void initFunc1() {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

      @Override public void run() {
        panel1.updateUI();
        label37.setText("过河猴子：" + MonkeyFactory.getOverMonkey() + " 产生猴子："
            + MonkeyFactory.getMonkeyNum());
        textArea2.setText(Monkey.getLogString());
        if (MonkeyFactory.isAllOver()) {
          MonkeyFactory.setPopsAndFairness();
          label37.setText("过河猴子：" + MonkeyFactory.getOverMonkey() + " 产生猴子："
              + MonkeyFactory.getMonkeyNum());
          textArea2.setText(Monkey.getLogString());
          label33.setText(
              "吞吐率：" + MonkeyFactory.getPops() + " 公平性：" + MonkeyFactory.getFairness());
          timer.cancel();
        }
      }
    };
    timer.scheduleAtFixedRate(task, 0, 500 / SystemConstant.getSpeedUp());
  }

  /**
   * 客户端通过这里调用系统的GUI.
   * 
   * @param  args        命令参数
   * @throws IOException 抛出读取文件相关的异常
   */
  public static void main(String[] args) throws IOException {
    MonkeyCrossRiverSystem
        .readFileAndCreateSystem("src/ConfigurationFIle/Configuration1.txt");
    initFrame();
  }
}
