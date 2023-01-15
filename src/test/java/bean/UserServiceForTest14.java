package bean;

import java.util.Random;

public class UserServiceForTest14 implements  IUserServiceForTest14 {
    private String token;
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "felix，100001，vancouver，" + token;
    }

}
