package site.lgong.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 数据库操作工具类
 * @createTime 2020/7/19 16:38
 */
@Slf4j
public class DataBaseHelper {

    //threadLocal可以理解为隔离线程的容器
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    // 本应该从properties文件中加载数据库配置，这里为了省事，就写死了
    static {
        DRIVER = "com.mysql.cj.jdbc.Driver";
        URL = "jdbc:mysql://localhost:3306/study?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
        USERNAME = "root";
        PASSWORD = "root";

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("数据库驱动加载失败", e);
        }
    }

    /**
     * @return Connection
     * @description 获取数据库连接
     */
    public static Connection getConnection() {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                log.error("获取数据库连接失败", e);
            } finally {
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        }
        return connection;
    }

    /**
     * @description 关闭数据库连接
     */
    public static void closeConnection() {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("关闭数据库连接失败", e);
            } finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }

    /**
     * @return a
     * @description 获取查询列表
     * @date: 2020/7/1
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection connection = getConnection();
            entityList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<T>(entityClass));
        } catch (SQLException e) {
            log.error("查询数据集合失败", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entityList;
    }

    /**
     * @return int
     * @description 执行更新语句
     * @date: 2020/7/19
     */
    public static int executeUpdate(String sql, Object[] params) {
        int rows = 0;
        try {
            Connection connection = getConnection();
            rows = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException e) {
            log.error("执行更新失败", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * @return boolean
     * @description 插入实体
     * @date: 2020/7/1
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            log.error("fieldMap不能为空");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * @return String
     * @description 获取类名
     * @date: 2020/7/19
     */
    public static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }
}
