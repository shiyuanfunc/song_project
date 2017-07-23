package Dao;

import bean.User;

import java.util.List;

/**
 * Created by song on 2017/7/22.
 */
public interface UserDao {
    public int saveUser(User user) throws Exception;

    public List<User> queryUsers() throws Exception;
}
