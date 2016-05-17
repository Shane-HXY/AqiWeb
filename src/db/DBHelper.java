package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by huangxiangyu on 16/5/4.
 */
public class DBHelper {

    public static Connection getConn() {
        Connection conn = null;
        try {
            String jdbcName = "com.mysql.jdbc.Driver";
            Class.forName(jdbcName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/pm2_5";
            String dbUser = "root";
            String dbPassword = "root";
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }
//    public static void main(String[] args) {
//        try {
//            Connection connection = getConn();
//            Statement sql_statement = connection.createStatement();
//            String query = "select * from user";
//            ResultSet result = sql_statement.executeQuery(query);
//            while (result.next()) {
//                String name = result.getString("username");
//                String password = result.getString("password");
//                System.out.println(name + ",," + password);
//            }
//            String query2 = "select * from city";
//            ResultSet resultSet = sql_statement.executeQuery(query2);
//            while (resultSet.next()) {
//                String cname = resultSet.getString("cname");
//                String ccode = resultSet.getString("ccode");
//                System.out.println(cname + ",," + ccode);
//            }
//
//            sql_statement.close();
//            connection.close();
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//    }
}
