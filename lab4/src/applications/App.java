package applications;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class App {

    private static final Logger logger = Logger.getLogger(App.class.getSimpleName());

    public static void main(String[] args) {
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler(App.class.getSimpleName() + "-log.%u.%g.txt");
            fileHandler.setFormatter(new Formatter() {

                @Override public String format(LogRecord record) {
                    String string = "<record>\n";
                    string += "<class>" + record.getSourceClassName() + "<class>\n";
                    string += "<method>" + record.getSourceMethodName() + "<method>\n";
                    string += "<time>" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "<time>\n";
                    string += "<level>" + record.getLevel() + "<level>\n";
                    string += "<message>" + record.getMessage() + "<message>\n";
                    return string;
                }
            });
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        logger.log(Level.INFO, "选择游戏");
        JFrame frame = new JFrame("WELCOME @Author ZJR");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLayout(new GridLayout(1, 3));
        frame.setBackground(Color.black);

        JButton button1 = new JButton("Stellar System");
        button1.setFocusable(false);
        JPanel panel1 = new JPanel(new BorderLayout(1, 2)) {

            @Override public void paint(Graphics g) {
                ImageIcon icon = new ImageIcon("src/img/Stellar System.jpg");
                icon.setImage(icon.getImage().getScaledInstance(getWidth(), getHeight() - button1.getHeight(),
                        Image.SCALE_AREA_AVERAGING));
                g.drawImage(icon.getImage(), 0, button1.getHeight(), this);
            }
        };

        JButton button2 = new JButton("Atom Structure");
        JPanel panel2 = new JPanel(new BorderLayout(1, 2)) {

            @Override public void paint(Graphics g) {
                ImageIcon imageIcon = new ImageIcon("src/img/Atom Structure System.jpg");
                imageIcon.setImage(imageIcon.getImage().getScaledInstance(getWidth(), getHeight() - button1.getHeight(),
                        Image.SCALE_AREA_AVERAGING));
                g.drawImage(imageIcon.getImage(), 0, button1.getHeight(), this);
            }
        };
        button2.setFocusable(false);

        JButton button3 = new JButton("Social Network System");
        button3.setFocusable(false);
        JPanel panel3 = new JPanel(new BorderLayout(1, 2)) {

            @Override public void paint(Graphics g) {
                ImageIcon imageIcon = new ImageIcon("src/img/Social Network System.jpg");
                imageIcon.setImage(
                        imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_AREA_AVERAGING));
                g.drawImage(imageIcon.getImage(), 0, button1.getHeight(), this);
            }
        };

        panel1.add(button1, BorderLayout.NORTH);
        panel2.add(button2, BorderLayout.NORTH);
        panel3.add(button3, BorderLayout.NORTH);

        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);

        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "选择行星系统");
                frame.dispose();
                StellarSystemGUI stellarSystemGUI = new StellarSystemGUI();
                stellarSystemGUI.GUI();
            }
        });
        button2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "选择原子系统");
                frame.dispose();
                AtomStructureGUI atomStructureGUI = new AtomStructureGUI();
                atomStructureGUI.GUI();
            }
        });
        button3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "选择社交系统");
                frame.dispose();
                SocialNetworkSystemGUI socialNetworkSystemGUI = new SocialNetworkSystemGUI();
                socialNetworkSystemGUI.GUI();
            }
        });
    }
}
