package servlet;

import dao.AuthorityDao;
import model.MeasureData;
import model.Node;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by huangxiangyu on 16/5/12.
 */
@WebServlet("/searchServlet")
public class SearchServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        String query = request.getParameter("query");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        AuthorityDao authorityDao = new AuthorityDao();
        JSONObject object = new JSONObject();

        Node node = authorityDao.getNode(query);
        if (node.getId() > 0) {
            // 若查询到节点名, 返回节点对象
            object.put("result", "found node in database.");
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
            // TODO:显示当月当周当天数据表
            MeasureData myMeasureData = authorityDao.getMesureData(nId);
            JSONObject mObject = new JSONObject();
            mObject.put("data_id",myMeasureData.getId());
            mObject.put("data_time",myMeasureData.getTime());
            mObject.put("data_pm25", myMeasureData.getPm2_5());
            mObject.put("data_pm10", myMeasureData.getPm10());
            mObject.put("data_nid", myMeasureData.getNid());
            object.put("data", mObject);
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
