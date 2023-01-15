package bean;

public class UserServiceForTest3 {
    private String uId;
    private UserDaoForTest3 userDao;
    public void queryUserInfo() {
        System.out.println("query user info：" + userDao.queryUserName(uId));
    }
}
