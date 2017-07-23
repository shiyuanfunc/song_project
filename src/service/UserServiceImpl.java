package service;

import Dao.UserDao;
import Dao.UserDaoImpl;
import bean.User;

import java.util.List;

/**
 * Created by song on 2017/7/19.
 */
public class UserServiceImpl implements UserService {

     UserDao userDao = new UserDaoImpl();
    @Override
    public String saveUser(User user) throws Exception {

        int len = userDao.saveUser(user);
        if (len == 1){
            return "success";
        }
        return "error";
    }

    @Override
    public List<User> queryUsers() throws Exception {
        List<User> listUsers = userDao.queryUsers();
        System.out.println(listUsers);
        if (listUsers == null){
            return null;
        } else{
            return listUsers;
        }
    }
}
