package servlet;

import dao.AuthorityDao;
import dao.UserDao;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangxiangyu on 16/5/22.
 */
@WebServlet("/detailData")
public class DetailDataServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        request.setCharacterEncoding("UTF-8");
        String nodeName = request.getParameter("nodeName");
        String timeSpan = request.getParameter("timeSpan");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        AuthorityDao authorityDao = new AuthorityDao();
        Node node = authorityDao.getNode(nodeName);
        List<MeasureData> measureDataList = authorityDao.getMesureDataList(timeSpan, node.getId());
        JSONObject jsonObject = new JSONObject();
        if (measureDataList.size() == 0) {
            jsonObject.put("result", "false");
        } else {
            jsonObject.put("result", "true");
            JSONArray jsonArray = new JSONArray();
            for (MeasureData measureData :
                 measureDataList) {
                JSONObject object = new JSONObject();
                object.put("mId", measureData.getId());
                object.put("mPm25", measureData.getPm2_5());
                object.put("mPm10", measureData.getPm10());
                object.put("mTime", measureData.getTime());
                object.put("mNid", measureData.getNid());
                jsonArray.put(object);
            }
            jsonObject.put("data", jsonArray);
        }
        System.out.println(jsonObject);
        response.getOutputStream().write(jsonObject.toString().getBytes("UTF-8"));
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
