package servlet;

import dao.AuthorityDao;
import model.City;
import model.Node;
import model.Province;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by huangxiangyu on 16/5/12.
 */
@WebServlet("/searchServlet")
public class SearchServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        int PorCorN = 0;
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        String query = request.getParameter("query");
        String Level = request.getParameter("level");
        int level = Integer.parseInt(Level);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        AuthorityDao authorityDao = new AuthorityDao();
        JSONObject object = new JSONObject();
        JSONObject resultObject = new JSONObject();
        JSONArray pArray = new JSONArray();
        JSONArray cArray = new JSONArray();
        JSONArray nArray = new JSONArray();
        if (level == 1) {
            Province province = authorityDao.getProvince(query);
            // 若查询层级属于省级
            if (province.getId() > 0) {
                // 若查到省名,直接返回省对象
                object.put("result", "found province in database.");
                PorCorN += 100;
                object.put("check", PorCorN);
                String pName = province.getName();
                int pId = province.getId();
                JSONObject pObject = new JSONObject();
                pObject.put("province_id", pId);
                pObject.put("province_name", pName);
                object.put("province", pObject);
            }
            City city = authorityDao.getCity(query);
            if (city.getId() > 0) {
                // 若查询得到市名,返回市对象
                object.put("result", "found city in database.");
                PorCorN += 10;
                object.put("check", PorCorN);
                String cName = city.getName();
                int cId = city.getId();
                int cLid = city.getLid();
                JSONObject cObject = new JSONObject();
                cObject.put("city_id", cId);
                cObject.put("city_name", cName);
                cObject.put("city_lid", cLid);
                object.put("city", cObject);
            }
            Node node = authorityDao.getNode(query);
            if (node.getId() > 0) {
                // 若查询到节点名, 返回节点对象
                object.put("result", "found node in database.");
                PorCorN += 1;
                object.put("check", PorCorN);
                String nName = node.getName();
                String nLoc = node.getLoc();
                int nId = node.getId();
                double nLon = node.getLon();
                double nLat = node.getLat();
                int nCid = node.getCid();
                JSONObject nObject = new JSONObject();
                nObject.put("node_id", nId);
                nObject.put("node_name", nName);
                nObject.put("node_loc", nLoc);
                nObject.put("node_lon", nLon);
                nObject.put("node_lat", nLat);
                nObject.put("node_cid", nCid);
                object.put("node", nObject);
            }
            if (PorCorN < 1) {
                // 若查不到省名、市名、节点名,返回省列表让用户选择
                object.put("result", "nothing found in database, here are some options:");
                object.put("check", PorCorN);
                List<Province> provinceList = authorityDao.getProvinceList();
                for (Province provinceItem :
                        provinceList) {
                    String pItemName = provinceItem.getName();
                    int pItemId = provinceItem.getId();
                    JSONObject pItem = new JSONObject();
                    pItem.put("province_id", pItemId);
                    pItem.put("province_name", pItemName);
                    pArray.put(pItem);
                }
                object.put("provinces", pArray);
            }
        } else if (level == 2) {
            // 若查询层级属于市级, 说明用户在省级列表中点击某省,返回该省下城市列表
            object.put("result", "here are some options of cities:");
            List<City> cityList = authorityDao.getCityList(Integer.parseInt(query));
            for (City city :
                    cityList) {
                int cId = city.getId();
                int cLid = city.getLid();
                String cName = city.getName();
                JSONObject cItem = new JSONObject();
                cItem.put("city_id", cId);
                cItem.put("city_name", cName);
                cItem.put("city_lid", cLid);
                cArray.put(cItem);
            }
            object.put("cities", cArray);
        } else if (level == 3) {
            // 若查询层级属于节点, 说明用户在市级列表中点击某市,返回该市下节点列表
            object.put("result", "here are some options of nodes:");
            List<Node> nodeList = authorityDao.getNodeList(Integer.parseInt(query));
            for (Node node :
                    nodeList) {
                int nId = node.getId();
                int nCid = node.getCid();
                String nName = node.getName();
                String nLoc = node.getLoc();
                double nLon = node.getLon();
                double nLat = node.getLat();
                JSONObject nItem = new JSONObject();
                nItem.put("node_id", nId);
                nItem.put("node_name", nName);
                nItem.put("node_loc", nLoc);
                nItem.put("node_lon", nLon);
                nItem.put("node_lat", nLat);
                nItem.put("node_cid", nCid);
                nArray.put(nItem);
            }
            object.put("nodes", nArray);
        } else if (level == 4) {
            // 说明用户点击并要关注节点,返回关注提示,修改数据库..
            authorityDao.addFocusNode(Integer.parseInt(userId), Integer.parseInt(query));
            resultObject.put("result", "focus on node success.");
        }
        System.out.println(resultObject);
        response.getOutputStream().write(object.toString().getBytes("UTF-8"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
