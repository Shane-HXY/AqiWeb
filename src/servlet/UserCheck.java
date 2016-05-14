package servlet;

import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huangxiangyu on 16/5/7.
 */
@WebServlet("/webCheck")
public class UserCheck extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String LOGIN_FLAG = null;
        //int login_flag = 0;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int id = new UserDao().checkLogin(username, password);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        try {
            DataOutputStream outputStream = new DataOutputStream(response.getOutputStream());
            if (id > 0) {
                LOGIN_FLAG = "success";
                outputStream.writeUTF(LOGIN_FLAG + id);
                System.out.println(LOGIN_FLAG + "" + df.format(new Date()));
                outputStream.close();
            } else {
                LOGIN_FLAG = "failure";
                System.out.println(LOGIN_FLAG + "" + df.format(new Date()));
                outputStream.writeUTF(LOGIN_FLAG);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
    public String getServletInfo() {
        return "Short description";
    }
}
