package dao;

import db.DBHelper;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by huangxiangyu on 16/5/4.
 */
public class UserDao {
    /**
     * 验证用户登录
     */
    public int checkLogin(String username, String password) {
        int id = 0;
        String query = "select * from user where " +
                "username = '" + username + "' and password = '" + password + "'";
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                id = resultSet.getInt("uid");
            }
            System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 用户注册
     */
    public String register() {
        return null;
    }

    /**
     * 修改方法
     */
    protected Boolean alter(String query) {
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 用户修改邮箱
     */
    public Boolean alterEmail(String username, String email) {
        String query = "update user set email = '"
                + email + "' where username = '" + username + "'";
        return alter(query);
    }

    /**
     * 用户修改密码
     */
    public Boolean alterPassword(String username, String password) {
        String query = "update user set password = '"
                + password + "' where username = '" + username + "'";
        return alter(query);
    }

    /**
     * 用户修改警戒值
     */
    public Boolean alterAlarm(String username, double alarm) {
        String query = "update user set alarm = "
                + alarm + " where username = '" + username + "'";
        return alter(query);
    }

    /**
     * 查询用户名
     */
    public String findUsername(int id) {
        String username = null;
        String query = "SELECT * FROM USER WHERE uid = " + id;
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            while (resultSet.next()) {
                username = resultSet.getString("username");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return username;
    }

    public static void main(String[] args) {
        new UserDao().checkLogin("admin", "admin");
    }
}
