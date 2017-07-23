package Action;

import bean.User;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class UserAction extends UploadBaseAction {
    private String path;
    private String userName;
    private User user;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String regist() throws Exception {
        UserService userService = new UserServiceImpl();
        String str = upload();
        if ("error".equals(str)) {
            return "input";
        }
        String path = str.substring(str.indexOf("_") + 1);
        user.setImgPath(path);
        String backStr = userService.saveUser(user);

        if ("success".equals(backStr)) {
            return "success";
        } else {
            return "error";
        }

    }

    public String showAllUser() throws Exception {

        UserService userService = new UserServiceImpl();
        List<User> users = userService.queryUsers();
        System.out.println("users = " + users);
        if (users == null) {
            return "error";
        } else {
            ServletActionContext.getRequest().setAttribute("listUser", users);
            return "success";
        }
    }


    public InputStream getDownloadFile() throws Exception {

        System.out.println("path dw = " + getPath());
        return ServletActionContext.getServletContext().getResourceAsStream(getPath());

    }

    public String execute() throws Exception {
        return "success";
    }
}
