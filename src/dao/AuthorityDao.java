package dao;

import db.DBHelper;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangxiangyu on 16/5/6.
 */
public class AuthorityDao {
    /**
     * 查询省份列表
     */
    public List<Province> getProvinceList() {
        String query = "select * from province ";
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        List<Province> list = new ArrayList<Province>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Province province = new Province();
                province.setId(resultSet.getInt("lid"));
                province.setName(resultSet.getString("lname"));
                province.setCode(resultSet.getString("lcode"));
                list.add(province);
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
     * 查询单个省份
     */
    public Province getProvince(String provinceName) {
        String query = "select * from province where lname = '" + provinceName + "'";
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        Province province = new Province();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                province.setId(resultSet.getInt("lid"));
                province.setName(resultSet.getString("lname"));
                province.setCode(resultSet.getString("lcode"));
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
        return province;
    }

    /**
     * 查询城市列表
     */
    public List<City> getCityList(int lid) {
        String query = "select * from city WHERE lid = " + lid;
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        List<City> list = new ArrayList<City>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("cid"));
                city.setName(resultSet.getString("cname"));
                city.setCode(resultSet.getString("ccode"));
                city.setLid(resultSet.getInt("lid"));
                list.add(city);
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
     * 查询单个城市
     */
    public City getCity(String cityName) {
        String query = "select * from city where cname = '" + cityName + "'";
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        City city = new City();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                city.setId(resultSet.getInt("cid"));
                city.setName(resultSet.getString("cname"));
                city.setCode(resultSet.getString("ccode"));
                city.setLid(resultSet.getInt("lid"));
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
        return city;
    }

    /**
     * 查询关注的城市列表
     */
    public List<City> focusCityList(int userId) {
        String query = "SELECT * FROM city WHERE cid = "
                + "(SELECT idCity FROM FocusCity WHERE idUser = "
                + userId + ")";
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        List<City> list = new ArrayList<City>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("cid"));
                city.setName(resultSet.getString("cname"));
                city.setCode(resultSet.getString("ccode"));
                city.setLid(resultSet.getInt("lid"));
                list.add(city);
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
     * 查询节点:用户关注节点、用户查询方法查询节点
     */
    public List<Node> getNList(String query) {
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        List<Node> list = new ArrayList<Node>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Node node = new Node();
                node.setId(resultSet.getInt("nid"));
                node.setName(resultSet.getString("nname"));
                node.setLoc(resultSet.getString("nloc"));
                node.setLon(resultSet.getDouble("nlon"));
                node.setLat(resultSet.getDouble("nlat"));
                node.setVis(resultSet.getBoolean("nvis"));
                node.setCid(resultSet.getInt("cid"));
                list.add(node);
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
    public List<Node> getNodeListFromUser(int id) {
        String query = "select * from node WHERE nid IN (select idNode from FocusNode where idUser = " + id + ")";
        return getNList(query);
    }
    /**
     * 查询节点:查询市下节点
     */
    public List<Node> getNodeList(int id) {
        String query = "SELECT * FROM node WHERE cid = " + id;
        return getNList(query);
    }

    public Node getNode(String nodeName) {
        String query = "select * from node where nname = '" + nodeName + "'";
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        Node node = new Node();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                node.setId(resultSet.getInt("nid"));
                node.setName(resultSet.getString("nname"));
                node.setLoc(resultSet.getString("nloc"));
                node.setLon(resultSet.getDouble("nlon"));
                node.setLat(resultSet.getDouble("nlat"));
                node.setVis(resultSet.getBoolean("nvis"));
                node.setCid(resultSet.getInt("cid"));
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
        return node;
    }

    /**
     * 查询节点的官方数据:节点当前数据,节点一定时间内的历史数据(group by)
     */
    //节点当前数据
    public MeasureData getMesureData(int nodeId) {
        String query = "select * from data where nid = " + nodeId
                + " order BY UNIX_TIMESTAMP('dtime') DESC";
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        MeasureData measureData = new MeasureData();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            measureData.setId(resultSet.getInt("did"));
            measureData.setTime(resultSet.getTimestamp("dtime"));
            measureData.setPm2_5(resultSet.getDouble("dpm25"));
            measureData.setPm10(resultSet.getDouble("dpm10"));
            measureData.setNid(resultSet.getInt("nid"));
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
        return measureData;
    }

    //    查找当天的数据\当月的数据
    public List<MeasureData> getMesureDataList(String timeSpan, int nodeId) {
        String query = null;
        switch (timeSpan) {
            case "month":
                query = "SELECT * FROM data WHERE nid = " + nodeId + " AND dtime >= date_format(date_sub(date_sub(now(), INTERVAL WEEKDAY(NOW()) DAY), INTERVAL 1 WEEK), '%Y-%m-%d')";
                break;
            case "day":
                query = "SELECT * FROM data WHERE DATEDIFF(dtime, NOW()) = 0 AND nid = " + nodeId;
                break;
            default:
                break;
        }
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = new DBHelper().getConn();
        List<MeasureData> list = new ArrayList<MeasureData>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                MeasureData measureData = new MeasureData();
                measureData.setId(resultSet.getInt("did"));
                measureData.setTime(resultSet.getTimestamp("dtime"));
                measureData.setPm2_5(resultSet.getDouble("dpm25"));
                measureData.setPm10(resultSet.getDouble("dpm10"));
                measureData.setNid(resultSet.getInt("nid"));
                list.add(measureData);
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
     * 添加关注
     */
    public void addFocus(int userId, int id, String table) {
        String column = null;
        if (table.equals("FocusNode")) {
            column = "idNode";
        } else if (table.equals("FocusCity")) {
            column = "idCity";
        }
        String update = "INSERT INTO " + table + "(idUser, " + column + ") VALUE ("
                + userId + ", " + id + ")";
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
     * 添加关注节点
     */
    public void addFocusNode(int userId, int id) {
        String table = "FocusNode";
        addFocus(userId, id, table);
    }

    /**
     * 添加关注城市
     */
    public void addFocusCity(int userId, int id) {
        String table = "FocusCity";
        addFocus(userId, id, table);
    }

    /**
     * 取消关注
     */
    public void removeFocus(int userId, int id, String table) {
        String update = "DELETE FROM " + table + " WHERE idNode = " + id
                + " AND idUser = " + userId;
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
     * 取消关注节点
     */
    public void removeFocusNode(int userId, int id) {
        String table = "FocusNode";
        removeFocus(userId, id, table);
    }

    /**
     * 取消关注城市
     */
    public void removeFocusCity(int userId, int id) {
        String table = "FocusCity";
        removeFocus(userId, id, table);
    }

    public static void main(String[] args) {
        Node node = new AuthorityDao().getNode("北京");
        List<City> cityList = new AuthorityDao().getCityList(6);
        if (node != null) {
            System.out.println("有节点");
            System.out.println(node.getLoc());
        } else {
            System.out.println("无节点");
        }
        if (cityList.isEmpty()) {
            System.out.println("无list");
        }
        //System.out.println(cityList.get(0).getName());
    }

}
