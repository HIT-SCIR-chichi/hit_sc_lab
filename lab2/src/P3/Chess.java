//package P3;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Point;
//import java.awt.image.BufferedImage;
//import java.io.File;
//
//import javax.imageio.ImageIO;
//import javax.swing.BorderFactory;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
// 
//public class Chess extends JPanel{ 
//    public  void main(String[] args) 
//    {
//        File file=new File("d:\\img\\5.jpg");
//        BufferedImage bi=ImageIO.read(file);
//        Graphics g=bi.getGraphics();
//        ImageIcon imageIcon = new ImageIcon();
//        imageIcon.setImage(image);
//        imageIcon.getImage();
//        g.drawImage(imageIcon, 0, 0, this);
//        JFrame f=new JFrame("国际象棋棋盘");              //创建窗口
//        f.setSize(168,195);   //窗口设置大小              //边框的长和宽    
//        //窗口设置位置
//        Point point=new Point(350,200);
//        f.setLocation(point);
//        int grids=8;                                //行数和列数
//        int gridsize=20;                            //单元格的高和宽
//            
//       for(int i=0; i<grids; i++)                       //外循环控制行
//       {
//           for(int j=0; j<grids; j++)                   //内循环控制列
//           {
//               JLabel l = new JLabel();         //生成标签实例
//               l.setSize(gridsize,gridsize);        //设置标签大小
//               l.setLocation(i*gridsize,j*gridsize);    //设置标签位置
//               if((i+j)%2==0)
//               {     //当小方格的坐标和刚好是偶数时，
//                   l.setBackground(Color.black);        //设置方格为黑色
//                   l.setOpaque(true);               //设置为不透明 
//               }
//               else
//               {
//                   l.setBackground(Color.white);    //设置方格为白色
//                   l.setOpaque(true);           //设置为不透明 
//               }
//               l.setBorder(BorderFactory.createLineBorder(Color.black));  //设置边界为黑色
//               f.add(l);            //添加标签
//           }
//       }
//       f.setVisible(true);          //显示窗口
//    }
//}