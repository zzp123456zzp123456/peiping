package edu.is.servlet.filter;

import edu.is.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter("/*")
public class Filter1 extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String url = req.getRequestURI().toString();

        if (!url.contains("static")&&!url.contains("login")) {
            if (!url.contains("index")) {
                HttpSession session = req.getSession();
                User user = (User) session.getAttribute("user");
                if (user == null) {
                    res.sendRedirect("login");
                    return;
                }
            }
        }
        chain.doFilter(req,res);
    }
}
