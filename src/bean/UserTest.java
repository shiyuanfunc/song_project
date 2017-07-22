package bean;

/**
 * Created by song on 2017/7/22.
 */
public class UserTest {

    private Integer id;

    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "UserTest{" +
                "id=" + id +
                ", img='" + img + '\'' +
                '}';
    }
}
