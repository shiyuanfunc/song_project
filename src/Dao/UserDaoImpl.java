package Dao;

import bean.UserTest;
import utils.DBUtils;

import java.util.List;

/**
 * Created by song on 2017/7/22.
 */
public class UserDaoImpl {

    public List<UserTest> test() throws Exception{
        String sql = "select * from user_img";
           return DBUtils.queryForList(sql,UserTest.class) ;
    }

    public static void main(String[] args) throws Exception {
        List<UserTest> list = new UserDaoImpl().test();
        System.out.println(list);
    }
}
