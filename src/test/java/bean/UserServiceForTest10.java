package bean;

import java.util.Random;

public class UserServiceForTest10 implements IUserServiceForTest10 {
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "felix，100001，vancouver";
    }
    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "register new user：" + userName + " success！";
    }
}
