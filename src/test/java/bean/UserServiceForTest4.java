package bean;

public class UserServiceForTest4 {
    private String uId;
    private UserDaoForTest4 userDao;
    public String queryUserInfo() {
        return "query user info：" + userDao.queryUserName(uId);
    }

    public String getUId() {
        return uId;
    }

    public UserDaoForTest4 getUserDao() {
        return userDao;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public void setUserDao(UserDaoForTest4 userDao) {
        this.userDao = userDao;
    }
}
