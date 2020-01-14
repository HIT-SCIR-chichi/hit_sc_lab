package iostrategy;

import circularorbit.AtomStructure;
import circularorbit.SocialNetworkCircle;
import circularorbit.StellarSystem;
import java.io.File;
import java.io.IOException;
import myexception.FileChooseException;

public interface IoStrategy {

  public void readFileAndCreateSystem(StellarSystem stellarSystem, File file)
      throws IOException, FileChooseException;

  public void readFileAndCreateSystem(AtomStructure atomStructure, File file)
      throws IOException, FileChooseException;

  public void readFileAndCreateSystem(SocialNetworkCircle socialNetworkCircle,
      File file) throws IOException, FileChooseException;

  public void saveSystemInfoInFile(StellarSystem stellarSystem, String filePath)
      throws IOException;

  public void saveSystemInfoInFile(AtomStructure atomStructure, String filePath)
      throws IOException;

  public void saveSystemInfoInFile(SocialNetworkCircle socialNetworkCircle,
      String filePath) throws IOException;
}
