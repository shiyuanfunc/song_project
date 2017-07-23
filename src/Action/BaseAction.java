package Action;

import com.opensymphony.xwork2.ModelDriven;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by song on 2017/7/22.
 */
public abstract class BaseAction<T> extends UploadBaseAction implements ModelDriven<T> {

    private T t;

    public BaseAction() {
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getAnnotatedSuperclass();
            Class<T> clz = (Class<T>) type.getActualTypeArguments()[0];
            t = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T getModel() {
        return t;
    }
}
