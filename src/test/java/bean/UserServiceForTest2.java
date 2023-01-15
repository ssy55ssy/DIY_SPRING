package bean;

public class UserServiceForTest2 {
    private String name;
    public UserServiceForTest2(String name) {
        this.name = name;
    }
    public void queryUserInfo() {
        System.out.println("query user info：" + name);
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append("").append(name);
        return sb.toString();
    }
}
