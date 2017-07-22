package Action;

public class UserAction extends UploadBaseAction {

    public String execute() throws Exception {
        String str = super.upload();
        if ("success".equals(str)) {
            return "success";
        }
        return "input";
    }
}
