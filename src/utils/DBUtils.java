package utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by song on 2017/7/22.
 */
public class DBUtils {
    private static ThreadLocal<Connection> tt = new ThreadLocal<>();

    /**
     * 获取Connection方法
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = tt.get();
        if (conn == null) {
            //PropertiesUtils.loadFile();
            String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
             String  username = "oracle";
             String  password = "oreacle";
            String driver = "oracle.jdbc.driver.OracleDriver";
            /*String url = PropertiesUtils.getPropertyValue("url").trim();
            String username = PropertiesUtils.getPropertyValue("username").trim();
            String password = PropertiesUtils.getPropertyValue("password").trim();
            String driver = PropertiesUtils.getPropertyValue("driver").trim();*/
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
                tt.set(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 关闭数据库的资源
     *
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (st != null) {
                st.close();
                st = null;
            }
            if (conn != null) {
                tt.remove();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据sql语句和参数执行DML语句
     *
     * @param sql
     * @param obj
     * @return
     * @throws Exception
     */
    public static int update(String sql, Object... obj) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 1;
        for (Object o : obj) {
            ps.setObject(i++, o);
        }
        int len = ps.executeUpdate();
        return len;
    }

    /**
     * 插入数据
     *
     * @param sql
     * @param obj
     * @return
     * @throws Exception
     */
    public static int insert(String sql, Object... obj) throws Exception {
        return update(sql, obj);
    }

    /**
     * 删除语句
     *
     * @param sql
     * @param obj
     * @return
     * @throws Exception
     */
    public static int delete(String sql, Object... obj) throws Exception {
        return update(sql, obj);
    }

    /**
     * t对象和sql语句执行DML语句
     *
     * @param t
     * @param sql
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> int update(T t, String sql) throws Exception {
        Connection conn = getConnection();
        //处理sql语句
        String regex = "[:](\\w{1,})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);
        List<String> keys = new ArrayList<>();
        //把匹配的元素换成?号
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            keys.add(matcher.group(1));
            matcher.appendReplacement(stringBuffer, "?");
        }
        matcher.appendTail(stringBuffer);
        sql = stringBuffer.toString();
        PreparedStatement ps = conn.prepareStatement(sql);
        //设置值
        for (int i = 1; i <= keys.size(); i++) {
            String key = keys.get(i - 1);
            //获取t对象中的key属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (key.equals(f.getName())) {
                    ps.setObject(i, f.get(t));
                }
            }
        }
        int len = ps.executeUpdate();
        return len;
    }

    /**
     * 插入数据
     *
     * @param t
     * @param sql
     * @return
     * @throws Exception
     */
    public static <T> int insert(T t, String sql) throws Exception {
        return update(t, sql);
    }

    /**
     * 删除数据
     *
     * @param t
     * @param sql
     * @return
     * @throws Exception
     */
    public static <T> int delete(T t, String sql) throws Exception {
        return update(t, sql);
    }

    /**
     * 返回List集合，其中封装着结果集
     *
     * @param sql
     * @param obj
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> queryForList(String sql, Object... obj) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 1;
        for (Object o : obj) {
            ps.setObject(i++, o);
        }
        //执行sql并且获取结果集List
        ResultSet rs = ps.executeQuery();
        List<Map<String, Object>> list = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> map = new HashMap<>();
            //获取结果集中的所有查询的字段
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取查询字段个数
            int len = rsmd.getColumnCount();
            for (int j = 1; j <= len; j++) {
                String columnName = rsmd.getColumnName(j);
                Object columnNameValue = rs.getObject(columnName);
                map.put(columnName, columnNameValue);
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 返回Map结果集
     *
     * @param sql
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> queryForMap(String sql, Object... obj) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 1;
        for (Object o : obj) {
            ps.setObject(i++, o);
        }
        //执行sql并且获取结果集List
        ResultSet rs = ps.executeQuery();
        Map<String, Object> map = new HashMap<>();
        while (rs.next()) {
            //获取结果集中的所有查询的字段
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取查询字段个数
            int len = rsmd.getColumnCount();
            for (int j = 1; j <= len; j++) {
                String columnName = rsmd.getColumnName(j);
                Object columnNameValue = rs.getObject(columnName);
                map.put(columnName, columnNameValue);
            }
        }
        return map;
    }

    /**
     * 根据sql语句和参数返回影响条数
     *
     * @param sql
     * @param obj
     * @return
     * @throws Exception
     */
    // 292
    public static int queryForInt(String sql, Object... obj) throws Exception {

        Connection conn = getConnection();
        //获取sql执行器
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 1;
        //设置值
        for (Object o : obj) {
            ps.setObject(i++, o);
        }

        //执行sql语句并获取结果集
        ResultSet rs = ps.executeQuery();

        Integer len = null;
        if (rs.next()) {
            len = rs.getInt(1);
        }
        return len;
    }

    /**
     * 获取一条记录 返回该记录的对象
     *
     * @param sql
     * @param clz
     * @param obj
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T queryForObject(String sql, Class<T> clz, Object... obj) throws Exception {

        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 1;
        //设置值
        for (Object o : obj) {
            ps.setObject(i++, o);
        }

        ResultSet rs = ps.executeQuery();

        //获取T对象
        T t = null;
        if (rs.next()) {
            t = clz.newInstance();
            //获取结果集中所有de查询字段
            ResultSetMetaData metaData = rs.getMetaData();
            //获取查询字段个数
            int len = metaData.getColumnCount();

            //数据库从1开始遍历
            for (int k = 0; k < len; k++) {
                String column = metaData.getColumnName(k);
                int columnType = metaData.getColumnType(k);

                //获取对象中多有的属性
                Field[] fields = clz.getDeclaredFields();

                for (Field f : fields) {
                    f.setAccessible(true);

                    if (f.getName().equalsIgnoreCase(column)) {
                        //对f进行赋值
                        f.set(t, convert(rs.getObject(column), f.getType(), columnType));
                        break;
                    }
                }
            }
        }
        return t;
    }

    /**
     * sql语句和Class及参数返回List集合
     * 返回多条记录  如 查询整张表中的数据
     *
     * @param sql
     * @param clz
     * @param obj
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> queryForList(String sql, Class<T> clz, Object... obj) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        int i = 1;
        for (Object o : obj) {
            ps.setObject(i++, o);
        }
        ResultSet rs = ps.executeQuery();
        //获取T对象
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            T t = clz.newInstance();
            //获取结果集中的所有的查询字段
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取查询字段的个数
            int len = rsmd.getColumnCount();
            for (int j = 1; j <= len; j++) {
                String columnName = rsmd.getColumnName(j);
                int columnType = rsmd.getColumnType(j);
                Field[] fields = clz.getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    if (f.getName().equalsIgnoreCase(columnName)) {
                        f.set(t, convert(rs.getObject(columnName), f.getType(), columnType));
                        break;
                    }
                }
            }
            list.add(t);
        }
        return list;
    }

    public static Object convert(Object val, Class<?> type, int columnType) throws Exception {
        if (val == null) return val;

        if ((type == Byte.class || type == byte.class) && (columnType == Types.NUMERIC || columnType == Types.BIT)) {
            return Byte.parseByte(String.valueOf(val));
        }

        if ((type == Short.class || type == short.class) && (columnType == Types.NUMERIC)) {
            return Short.parseShort(String.valueOf(val));
        }

        if ((type == Integer.class || type == int.class) && (columnType == Types.INTEGER || columnType == Types.NUMERIC)) {
            return Integer.parseInt(String.valueOf(val));
        }

        if ((type == Long.class || type == long.class) && (columnType == Types.INTEGER || columnType == Types.NUMERIC)) {
            return Long.parseLong(String.valueOf(val));
        }

        if ((type == Float.class || type == float.class) && (columnType == Types.FLOAT || columnType == Types.NUMERIC)) {
            return Float.parseFloat(String.valueOf(val));
        }

        if ((type == double.class || type == Double.class) && (columnType == Types.DOUBLE || columnType == Types.NUMERIC)) {
            return Double.parseDouble(String.valueOf(val));
        }

        if ((type == String.class) && (columnType == Types.VARCHAR || columnType == Types.NUMERIC)) {
            return String.valueOf(val);
        }

        if ((type == Character.class || type == char.class) && (columnType == Types.VARCHAR || columnType == Types.CHAR)) {
            return String.valueOf(val).charAt(0);
        }

        if ((type == java.util.Date.class) && (columnType == Types.DATE || columnType == Types.TIMESTAMP)) {
            return val;
        }

        if (type == byte[].class && Types.BLOB == columnType) {
            InputStream in = null;
            try {
                //blob转为byte【】
                Blob blob = (Blob) val;
                //把blob对象转成inputStream
                in = blob.getBinaryStream();
                return IOUtils.toByteArray(in);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                        in = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        if (type == String.class && Types.CLOB == columnType) {
            //把clob转成字符串
            Clob clob = (Clob) val;

            //把clob转成clob
            InputStream in = null;
            try {
                in = clob.getAsciiStream();
                IOUtils.toString(in, String.valueOf(Charset.forName("UTF-8")));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                        in = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("请检查数据库字段和返回类型是够一致");
    }
}
