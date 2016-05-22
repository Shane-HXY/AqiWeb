package servlet;

import dao.AuthorityDao;
import model.City;
import model.MeasureData;
import model.Node;
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
 * Created by huangxiangyu on 16/5/9.
 */
@WebServlet("/focusCheck")
public class FocusCheck extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println(request.getContentType());   //得到客户端发送过来内容的类型，application/json;charset=UTF-8
        System.out.println(request.getRemoteAddr());    //得到客户端的ip地址
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        System.out.println(stringBuilder.toString());
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
            String userId = jsonObject.getString("userId");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            AuthorityDao authorityDao = new AuthorityDao();
            List<Node> nodes = authorityDao.getNodeListFromUser(Integer.parseInt(userId));
            List<City> cities = authorityDao.focusCityList(Integer.parseInt(userId));
            JSONObject object = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONArray array = new JSONArray();
            JSONArray dataArray = new JSONArray();
            for (Node node : nodes) {
                JSONObject rJson = new JSONObject();
                rJson.put("node_id", node.getId());
                rJson.put("node_name", node.getName());
                rJson.put("node_loc", node.getLoc());
                rJson.put("node_lon", node.getLon());
                rJson.put("node_lat", node.getLat());
                rJson.put("node_vis", node.getVis());
                rJson.put("node_cid", node.getCid());
                jsonArray.put(rJson);
            }
            for (City city : cities) {
                JSONObject rJson = new JSONObject();
                rJson.put("city_id", city.getId());
                rJson.put("city_name", city.getName());
                rJson.put("city_code", city.getCode());
                rJson.put("city_lid", city.getLid());
                array.put(rJson);
            }
            for (Node node : nodes) {
                MeasureData measureData = authorityDao.getMesureData(node.getId());
                JSONObject rJson = new JSONObject();
                rJson.put("measure_id", measureData.getId());
                rJson.put("measure_time", measureData.getTime());
                rJson.put("measure_pm25", measureData.getPm2_5());
                rJson.put("measure_pm10", measureData.getPm10());
                rJson.put("measure_nid", measureData.getNid());
                dataArray.put(rJson);
            }
            object.put("nodes", jsonArray);
            //object.put("cities", array);
            object.put("datas", dataArray);
            System.out.println(object);
            response.getOutputStream().write(object.toString().getBytes("UTF-8"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,IOException {
        processRequest(request, response);
    }
}
