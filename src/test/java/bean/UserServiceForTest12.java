package bean;

import context.annoation.Component;

import java.util.Random;

@Component(value = "userService")
public class UserServiceForTest12 implements IUserServiceForTest12 {
    private String token;
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
    @Override
    public String toString() {
        return "UserService#token = { " + token + " }";
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
