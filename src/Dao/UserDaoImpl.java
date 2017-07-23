package Dao;

import bean.User;
import bean.UserTest;
import utils.DBUtils;

import java.util.List;

/**
 * Created by song on 2017/7/22.
 */
public class UserDaoImpl implements UserDao{

    public List<UserTest> test() throws Exception{
        String sql = "select * from user_img";
           return DBUtils.queryForList(sql,UserTest.class) ;
    }

    public static void main(String[] args) throws Exception {
        List<UserTest> list = new UserDaoImpl().test();
        System.out.println(list);
    }

    @Override
    public int saveUser(User user) throws Exception {
        String sql = "insert into webtest_user(id,userName,password,ImgPath) values(webtest_user_seq.nextval,:userName,:passWord,:ImgPath) ";
        return DBUtils.insert(user,sql);
    }

    @Override
    public List<User> queryUsers() throws Exception {

        String sql = "select * from webtest_user order by id";
        return DBUtils.queryForList(sql,User.class);
    }
}
