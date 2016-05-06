package dao;

import db.DBHelper;
import model.PersonalData;
import model.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by huangxiangyu on 16/5/5.
 */
public class PostDataDao {
    /**
     * 查询[个人发布数据]概要集合
     */
    public List<PersonalData> getPeerDataList(String strwhere, String strorder) {
        String query = "select * from upostdata ";
        if (!(isInvalid(strwhere))) {
            query += " where " + strwhere;
        }
        if (!(isInvalid(strorder))) {
            query += " order by " + strorder;
        }
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        List<PersonalData> list = new ArrayList<PersonalData>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                PersonalData personalData = new PersonalData();
                personalData.setId(resultSet.getInt("pid"));
                personalData.setLat(resultSet.getDouble("plat"));
                personalData.setLon(resultSet.getDouble("plon"));
                personalData.setCity(resultSet.getString("pcity"));
                personalData.setTime(resultSet.getTimestamp("ptime"));
                personalData.setPm2_5(resultSet.getDouble("pm25"));
                personalData.setPm10(resultSet.getDouble("pm10"));
                personalData.setUid(resultSet.getInt("uid"));
                list.add(personalData);
            }
        } catch (SQLException e) {
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
        return list;
    }

    /**
     * 查询[个人发布数据]
     */
    public PersonalData queryPeerData(int id) {
        String query = "select * from upostdata where pid = " + id;
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        PersonalData personalData = new PersonalData();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                personalData.setId(resultSet.getInt("pid"));
                personalData.setLat(resultSet.getDouble("plat"));
                personalData.setLon(resultSet.getDouble("plon"));
                personalData.setCity(resultSet.getString("pcity"));
                personalData.setTime(resultSet.getTimestamp("ptime"));
                personalData.setPm2_5(resultSet.getDouble("pm25"));
                personalData.setPm10(resultSet.getDouble("pm10"));
                personalData.setUid(resultSet.getInt("uid"));
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return personalData;
    }

    /**
     * 发布[个人发布数据]
     */
    public void postPeerData(PersonalData personalData) {
        String update = "insert into upostdata " +
                "(plon, plat, pcity, ptime, pm25, pm10, uid) values ("
                + personalData.getLat() + ", "
                + personalData.getLon() + ", '"
                + personalData.getCity() + "', '"
                + personalData.getTime() + "', "
                //+ "'2016-5-6 15:16:25', "
                + personalData.getPm2_5() + ", "
                + personalData.getPm10() + ", "
                + personalData.getUid() + ")";
        Statement statement = null;
        Connection connection = new DBHelper().getConn();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除[个人发布数据]
     */
    public void deletePeerData(int id) {
        String update = "delete from upostdata where pid = " + id;
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(update);
        } catch (SQLException e) {
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
    }

    //判断时否空值
    private boolean isInvalid(String value) {
        return (value == null || value.length() == 0);
    }

}
