package bean;

import context.annoation.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDaoForTest13 {
    private static Map<String, String> hashMap = new HashMap<>();
    static {
        hashMap.put("10001", "felix vancouver");
        hashMap.put("10002", "paul shanghai");
        hashMap.put("10003", "tony san diego");
    }
    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}
