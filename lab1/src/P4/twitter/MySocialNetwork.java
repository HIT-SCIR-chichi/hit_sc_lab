package P4.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MySocialNetwork {

    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        Set<String> allUserNames = new HashSet<>();
        List<Tweet> writtenBy = new ArrayList<>();
        Set<String> followNames = new HashSet<>();
        for (Tweet temp : tweets) {
            allUserNames.add(temp.getAuthor().toLowerCase());
        }
        for (String temp : allUserNames) {
            writtenBy = Filter.writtenBy(tweets, temp);
            followNames = Extract.getMentionedUsers(writtenBy);
            followNames.remove(temp);
            followsGraph.put(temp, followNames);
        } // 以上为普通的猜测联系方法
        for (String temp : followsGraph.keySet()) {
            Set<String> mySocilSet = new HashSet<>();
            for (String follow : followsGraph.get(temp)) {
                mySocilSet = followsGraph.get(temp);
                mySocilSet.addAll(followsGraph.get(follow));//加入所有follow对象的follow对象
            }
            mySocilSet.remove(temp);//同时要将自己移除follow对象
            followsGraph.put(temp, mySocilSet);
        }//以上是改进后的猜测联系方法（若a追随b，b追随c，则a追随c）
        return followsGraph;
    }
}
