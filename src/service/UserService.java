package service;

import bean.User;

import java.util.List;

/**
 * Created by song on 2017/7/19.
 */
public interface UserService {

    public String saveUser(User user) throws  Exception;

    public List<User> queryUsers() throws Exception;
}
