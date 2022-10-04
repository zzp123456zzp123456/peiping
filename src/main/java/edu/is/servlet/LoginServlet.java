package edu.is.servlet;

import edu.is.entity.User;
import edu.is.service.Service;
import edu.is.util.ThymeleafUtil;
import org.thymeleaf.context.Context;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name =   "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        if (request.getSession().getAttribute("user") != null) {
            ThymeleafUtil.process("file2.html", new Context(), response.getWriter());
        } else {
            ThymeleafUtil.process("login.html", new Context(), response.getWriter());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Service service = new Service();
        response.setContentType("text/html;charset=utf-8");
        String username=null;
        String password=null;

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("username") && parameterMap.containsKey("password")) {
            username = request.getParameter("username");
            password = request.getParameter("password");
            if (service.findUser(username, password)) {

                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("user", new User().setUsername(username)
                        .setPassword(password));
                ThymeleafUtil.process("file2.html", new Context(), response.getWriter());
                return;
            }

        }
        ThymeleafUtil.process("login.html", new Context(), response.getWriter());
    }
}
