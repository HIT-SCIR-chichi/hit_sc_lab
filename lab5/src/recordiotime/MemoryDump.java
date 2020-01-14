package recordiotime;

import circularorbit.StellarSystem;
import iostrategy.BufferedIoStrategy;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import myexception.FileChooseException;

public class MemoryDump {

  /**
   * 此函数用于方便获取程序读完超大文件之后，内存占用情况.
   * 
   * @param  args                       参数
   * @throws IOException                IO异常
   * @throws FileChooseException        文件选取异常
   * @throws CloneNotSupportedException clone 异常
   */
  public static void main(String[] args)
      throws IOException, FileChooseException, CloneNotSupportedException {
    StellarSystem stellarSystem = new StellarSystem();
    stellarSystem.setReadFile(
        new File("src/Spring2019_HITCS_SC_Lab5-master/StellarSystem.txt"));
    stellarSystem.readFileAndCreateSystem(new BufferedIoStrategy());
    // 让程序停止下来，便于获取当前内存占用情况
    System.out.println("现在可以导出内存占用情况");
    Scanner scanner = new Scanner(System.in);
    String string = scanner.next();
    scanner.close();
    System.out.println(string);
  }

}
