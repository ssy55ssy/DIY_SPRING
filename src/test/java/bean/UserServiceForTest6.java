package bean;

import beans.factory.DisposableBean;
import beans.factory.InitializingBean;

public class UserServiceForTest6 implements InitializingBean, DisposableBean {
    private String uId;
    private String company;
    private String location;
    private UserDaoForTest6 userDao;
    @Override
    public void destroy() throws Exception {
        System.out.println("execute：UserService.destroy");
    }
    public String queryUserInfo() {
        return userDao.queryUserName(uId);
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("execute：UserService.afterPropertiesSet");
    }


}
