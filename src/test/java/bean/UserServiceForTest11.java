package bean;

import java.util.Random;

public class UserServiceForTest11 implements IUserServiceForTest11 {
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
        return "register：" + userName + " success！";
    }
}
