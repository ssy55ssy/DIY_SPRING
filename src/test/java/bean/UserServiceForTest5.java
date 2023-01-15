package bean;

public class UserServiceForTest5 {
    private String uId;
    private String company;
    private String location;
    private UserDaoForTest5 userDao;

    public String queryUserInfo() {
        return userDao.queryUserName(uId);
    }

    public String getUId() {
        return uId;
    }

    public UserDaoForTest5 getUserDao() {
        return userDao;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public void setUserDao(UserDaoForTest5 userDao) {
        this.userDao = userDao;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
