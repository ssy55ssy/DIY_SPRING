package bean;

import beans.factory.annoation.Autowired;
import beans.factory.annoation.Value;
import context.annoation.Component;

import java.util.Random;

@Component("userServiceForTest13")
public class UserServiceForTest13 implements IUserServiceForTest13 {
    @Value("${token}")
    private String token;
    @Autowired
    private UserDaoForTest13 userDao;
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userDao.queryUserName("10001") + "，" + token;
    }

    public void setUserDao(UserDaoForTest13 userDao) {
        this.userDao = userDao;
    }

    public UserDaoForTest13 getUserDao() {
        return userDao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

