package bean;

public class UserServiceForTest8 {
    private String uId;
    private String company;
    private String location;
    private IUserDaoForTest8 userDao;
    public String queryUserInfo() {
        return userDao.queryUserName(uId) + "," + company + "," + location;
    }

    public String getUId() {
        return uId;
    }

    public IUserDaoForTest8 getUserDao() {
        return userDao;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public void setUserDao(IUserDaoForTest8 userDao) {
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
