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
import java.io.IOException;
import java.util.List;

/**
 * Created by huangxiangyu on 16/5/15.
 */
@WebServlet("/addServlet")
public class addServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, JSONException {
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        String query = request.getParameter("query");
        String level = request.getParameter("level");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        AuthorityDao authorityDao = new AuthorityDao();
        JSONObject object = new JSONObject();
        JSONArray pArray = new JSONArray();
        JSONArray cArray = new JSONArray();
        JSONArray nArray = new JSONArray();
        if (level.equals("province")) {
            // 返回省列表让用户选择
            object.put("result", "here are some provinces' options:");
            List<Province> provinceList = authorityDao.getProvinceList();
            for (Province provinceItem :
                    provinceList) {
                JSONObject pItem = new JSONObject();
                pItem.put("province_id", provinceItem.getId());
                pItem.put("province_name", provinceItem.getName());
                pItem.put("province_code", provinceItem.getCode());
                pArray.put(pItem);
            }
            object.put("provinces", pArray);
        } else if (level.equals("city")) {
            // 若查询层级属于市级, 说明用户在省级列表中点击某省,返回该省下城市列表
            object.put("result", "here are some cities' options:");
            List<City> cityList = authorityDao.getCityList(Integer.parseInt(query));
            for (City city :
                    cityList) {
                JSONObject cItem = new JSONObject();
                cItem.put("city_id", city.getId());
                cItem.put("city_name", city.getName());
                cItem.put("city_lid", city.getLid());
                cItem.put("city_code", city.getCode());
                cArray.put(cItem);
            }
            object.put("cities", cArray);
        } else if (level.equals("node")) {
            // 若查询层级属于节点, 说明用户在市级列表中点击某市,返回该市下节点列表
            object.put("result", "here are some options of nodes:");
            List<Node> nodeList = authorityDao.getNodeList(Integer.parseInt(query));
            for (Node node :
                    nodeList) {
                JSONObject nItem = new JSONObject();
                nItem.put("node_id", node.getId());
                nItem.put("node_name", node.getName());
                nItem.put("node_loc", node.getLoc());
                nItem.put("node_lon", node.getLon());
                nItem.put("node_lat", node.getLat());
                nItem.put("node_cid", node.getCid());
                nArray.put(nItem);
            }
            object.put("nodes", nArray);
        } else {
            // 说明用户点击并要关注节点,返回关注提示,修改数据库..
            authorityDao.addFocusNode(Integer.parseInt(userId), Integer.parseInt(query));
            object.put("result", "focus on node success.");
        }
        System.out.println(object);
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
