package bean;

import java.util.HashMap;
import java.util.Map;

public class UserDaoForTest4 {
    private static Map<String, String> hashMap = new HashMap<>();
    static {
        hashMap.put("10001", "felix");
        hashMap.put("10002", "tony");
        hashMap.put("10003", "paul");
    }
    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}
