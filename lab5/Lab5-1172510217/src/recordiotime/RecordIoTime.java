package recordiotime;

import circularorbit.SocialNetworkCircle;
import circularorbit.StellarSystem;
import iostrategy.BufferedIoStrategy;
import iostrategy.NioStrategy;
import iostrategy.ScannerIoStrategy;
import java.io.File;
import java.io.IOException;
import myexception.FileChooseException;

public class RecordIoTime {

  /**
   * this is a test code to record the IO time.
   * 
   * @param  args                命令参数
   * @throws FileChooseException 异常
   * @throws IOException         异常
   */
  public static void main(String[] args)
      throws IOException, FileChooseException {

    StellarSystem stellarSystem = new StellarSystem();
    stellarSystem.setReadFile(
        new File("src/Spring2019_HITCS_SC_Lab5-master/StellarSystem.txt"));
    stellarSystem.setWriteFilePath("src/outputFile/StellarSystem.txt");

    stellarSystem.readFileAndCreateSystem(new NioStrategy());
    stellarSystem.saveSystemInfoInFile(new NioStrategy());

    stellarSystem.readFileAndCreateSystem(new BufferedIoStrategy());
    stellarSystem.saveSystemInfoInFile(new BufferedIoStrategy());

    stellarSystem.readFileAndCreateSystem(new ScannerIoStrategy());
    stellarSystem.saveSystemInfoInFile(new ScannerIoStrategy());

    SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle();
    socialNetworkCircle.setReadFile(new File(
        "src/Spring2019_HITCS_SC_Lab5-master/SocialNetworkCircle.txt"));
    socialNetworkCircle.setWriteFilePath("src/outputFile/SocialNetwork.txt");

    socialNetworkCircle.readFileAndCreateSystem(new BufferedIoStrategy());
    socialNetworkCircle.saveSystemInfoInFile(new BufferedIoStrategy());

    socialNetworkCircle.readFileAndCreateSystem(new ScannerIoStrategy());
    socialNetworkCircle.saveSystemInfoInFile(new ScannerIoStrategy());

    socialNetworkCircle.readFileAndCreateSystem(new NioStrategy());
    socialNetworkCircle.saveSystemInfoInFile(new NioStrategy());

  }

}
