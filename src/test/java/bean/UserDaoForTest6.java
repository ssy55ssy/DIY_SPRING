package bean;

import java.util.HashMap;
import java.util.Map;

public class UserDaoForTest6 {

    private static Map<String, String> hashMap = new HashMap<>();
    public void initDataMethod(){
        System.out.println("execute：init-method");
        hashMap.put("10001", "felix");
        hashMap.put("10002", "tony");
        hashMap.put("10003", "paul");
    }
    public void destroy(){
        System.out.println("execute：destroy-method");
        hashMap.clear();
    }
    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}
