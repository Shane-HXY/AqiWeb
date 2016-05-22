package servlet;

import dao.UserDao;
import model.User;
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

/**
 * Created by huangxiangyu on 16/5/18.
 */
@WebServlet("/getUserInfo")
public class GetUserInfo extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        User user = new UserDao().getUserInfo(Integer.parseInt(userId));
        JSONObject jsonObject = new JSONObject();
        if (user.getId() == 0) {
            jsonObject.put("result", "false");
        } else {
            JSONObject object = new JSONObject();
            object.put("id", user.getId());
            object.put("username", user.getUsername());
            object.put("email", user.getUemail());
            object.put("alarm", user.getAlarm());
            jsonObject.put("user", object);
            jsonObject.put("result", true);
        }
        System.out.println(jsonObject);
        response.getOutputStream().write(jsonObject.toString().getBytes("UTF-8"));
    }

    protected void processResponse(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, JSONException {
        request.setCharacterEncoding("UTF-8");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        System.out.println(stringBuilder.toString());
        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
        int userId = jsonObject.getInt("userId");
        String oldPassword = jsonObject.getString("oldPassword");
        String newPassword = jsonObject.getString("newPassword");
        String email = jsonObject.getString("email");
        String alarm = jsonObject.getString("alarm");
        UserDao userDao = new UserDao();
        User user = userDao.getUserInfo(userId);
        JSONObject object = new JSONObject();
        if (email != null && !email.equals(user.getUemail())) {
            userDao.alterEmail(userId, email);
            object.put("result", "Alter email success");
        }
        if (alarm != null && !alarm.equals(user.getAlarm())) {
            userDao.alterAlarm(userId, Double.parseDouble(alarm));
            object.put("result", "Alter alarm success");
        }
        if (oldPassword != null && oldPassword.equals(user.getPassword())) {
            if (newPassword != null && !newPassword.equals(user.getPassword())) {
                userDao.alterPassword(userId, newPassword);
                object.put("result", "Alter password success");
            }
        }
        if (object.getString("result") == null) {
            object.put("result", "Wrong format");
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            processResponse(request, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
