package servlet;

import dao.PostDataDao;
import dao.UserDao;
import model.PersonalData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangxiangyu on 16/5/16.
 */
@WebServlet("/commServlet")
public class CommServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,IOException,JSONException {
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        String where = request.getParameter("where");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PostDataDao postDataDao = new PostDataDao();
        List<PersonalData> personalDataList = postDataDao.getPeerDataList(where, "ptime desc");
        JSONObject jsonObject = new JSONObject();
        if (personalDataList == null) {
            jsonObject.put("result", "Nothing found on "+ where);
        } else {
            JSONArray jsonArray = new JSONArray();
            for (PersonalData personalData :
                 personalDataList) {
                JSONObject object = new JSONObject();
                object.put("data_id", personalData.getId());
                object.put("data_time", personalData.getTime());
                object.put("data_city", personalData.getCity());
                object.put("data_lat", personalData.getLat());
                object.put("data_lon", personalData.getLon());
                UserDao userDao = new UserDao();
                String username = userDao.findUsername(personalData.getUid());
                object.put("data_uname", username);
                jsonArray.put(object);
            }
            jsonObject.put("result", "Here are some results");
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
