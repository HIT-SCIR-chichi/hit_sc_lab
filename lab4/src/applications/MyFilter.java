package applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyFilter {

    private File file = new File("");// 待过滤的文件

    public MyFilter(String fileString) {
        file = new File(fileString);
    }

    public List<String> filter(int k, String string1, String string2) throws IOException, ParseException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bfReader = new BufferedReader(reader);
        List<String> resList = new ArrayList<>();
        String lineString = new String();
        while ((lineString = bfReader.readLine()) != null) {
            String string = "";
            Boolean flag = false;
            if (lineString.contains("<record>")) {
                string += lineString + "\n";
                while ((lineString = bfReader.readLine()) != null && !lineString.contains("<record>")) {
                    string += lineString + "\n";
                    if (k == 1) {
                        Matcher matcher;
                        Pattern pattern = Pattern
                                .compile("<time>([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})<time>");
                        if ((matcher = pattern.matcher(lineString)).find()) {
                            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(matcher.group(1));
                            Date dateBefore = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(string1);
                            Date dateAfter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(string2);
                            if (date.after(dateBefore) && date.before(dateAfter)) {
                                flag = true;
                            }
                        }
                    } else if (k == 2) {
                        if (lineString.contains(string1) && lineString.contains("<class>")) {
                            flag = true;
                        }
                    } else if (k == 3) {
                        if (lineString.contains(string1) && lineString.contains("<level>")) {
                            flag = true;
                        }
                    } else if (k == 4) {
                        if (lineString.contains("<method>") && lineString.contains(string1)) {
                            flag = true;
                        }
                    }else if(k == 5){
                        if(lineString.contains("<message>") && lineString.contains(string1)) {
                            flag = true;
                        }
                    }
                }
            }
            if (flag) {
                resList.add(string);
                System.out.println(string);
            }
        }
        bfReader.close();
        return resList;
    }

    public static void main(String[] args) throws IOException, ParseException {
        MyFilter filter = new MyFilter("App-log.0.0.txt");
        filter.filter(3, "SEVERE", "2019-05-18 11:45:30");
    }

}
