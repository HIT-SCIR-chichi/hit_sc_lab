package P1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MagicSquare {
    static boolean isLegalMagicSquare(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader bf = new BufferedReader(fr);
        int row = 0;//To record the number of rows
        while(bf.readLine() != null) {
            row ++;
        }
        bf.close();
        FileReader fr1 = new FileReader(filename);
        BufferedReader bf1 = new BufferedReader(fr1);
        String line = null;
        int [][] a = new int [row][row];//用于保存分割文件得到的整数
        int k = 0;
        while((line = bf1.readLine()) != null) {
            String[] temp = line.split("\t");//按照格式\t分割文件的每一行
            for(int i = 0;i < temp.length;i ++) {
                int j = temp[i].length();
                while(--j >= 0) {//判断有无非数字情况存在：可以排除负数，小数
                    if(!Character.isDigit(temp[i].charAt(j))) {
                        System.out.print("Illegal MagicSquare\t");
                        bf1.close();
                        return false;
                    }
                }
            }
            if(temp.length != row) {//判断行数是否等于列数
                System.out.print("Illegal MagicSquare\t");
                bf1.close();
                return false;
            }
            //将字符串转换为数值，同时判断是否为0
            for(int i = 0;i < temp.length;i ++) {
                if( (a[k][i] = Integer.valueOf(temp[i]) ) == 0) {
                    System.out.print("Illegal MagicSquare\t");
                    bf1.close();
                    return false;
                }
            }
            k++;
        }
        bf1.close();
        int []rows = new int [row];
        int []columns = new int [row];
        int diagonal1 = 0, diagonal2 = 0;
        for(int i = 0;i < row;i ++) {//目的是计算行之和，列和，对角线之和
            for(int j = 0;j < row;j ++) {
                rows[i] += a[i][j];
                columns[j] += a[i][j];
            }
            diagonal1 += a[i][i];
            diagonal2 += a[i][row-1];
        }
        for(int i = 0;i < row;i ++) {//判断每行之和，每列之和，对角线之和是否为同一个常数
            if(rows[i] != rows[0] || columns[i] != rows[0]) {
                System.out.print("The rows or the columns don't sum to the same constant!\t");
                return false;
            }
        }
        if(diagonal1 != rows[0] || diagonal2 != rows[0]) {//为了简单，均设置成与第一行之和比较
            System.out.print("The diagonals don't sum to the same constant as the rows!\t");
            return false;
        }
        return true;
    }
    static void generateMagicSquare(int n) throws IOException {
        if(n < 0) {//判断输入的n是否为负数
            System.out.println("False, please input a positive integer!");
            return;
        }
        else if(n % 2 == 0) {//判断输入的n是否为偶数
            System.out.println("False, please input an odd  number!");
            return;
        }
        else {
            int magic[][] = new int[n][n];//用于保存生成的幻方数据
            int row = 0, col = n / 2, i, j, square = n * n;//相当于将n×n个从1开始的连续自然数填入n×n个空格中去
            for (i = 1; i <= square; i++) {//基本原则是依次向右上方填写幻方数据
                magic[row][col] = i;//将1放在第0行中央
                if (i % n == 0)//若原本待填写的位置已经被填写，则变为新的一行
                    row++;
                else {
                    if (row == 0)//若行数为0，则更新行数为n-1
                        row = n - 1;
                    else//否则继续向上填
                        row--;
                    if (col == (n - 1))//若列数已满，则更新为0
                        col = 0;
                    else//否则，继续向右填
                        col++;
                }
            }
            String filename = "src/P1/txt/6.txt";
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    bw.write(magic[i][j] + "\t");//将产生的幻方数据写入到文件6.txt中，并注意格式＋\t
                }
                if(i < n-1) {//记得输出换行
                bw.write("\n");
                }
            }
            bw.close();
        }
    }
    public static void main(String[] args) throws IOException {
        String []filename = new String [6];//将文件名存在字符串数组中加以访问
        filename[0]= "src/P1/txt/1.txt";
        filename[1]= "src/P1/txt/2.txt";
        filename[2]= "src/P1/txt/3.txt";
        filename[3]= "src/P1/txt/4.txt";
        filename[4]= "src/P1/txt/5.txt";
        filename[5]= "src/P1/txt/6.txt";
        for(int i = 0;i < 5;i ++) {//连续五次判断各个文件
            System.out.println(isLegalMagicSquare(filename[i]));
        }
        
        System.out.print("Please input an odd number to generate a MagicSquare:");
        Scanner inputn = new Scanner(System.in);
        int n = inputn.nextInt();
        generateMagicSquare(n);//根据输入的阶数n产生幻方
        inputn.close();
        
        System.out.println(isLegalMagicSquare(filename[5]));//判断6.txt是否满足幻方要求
    }
}