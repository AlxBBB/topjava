package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.AuthorizedUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    public AuthorizedUser authorizedUser = new AuthorizedUser();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Post action {}", request.getParameter("action"));
        String action = request.getParameter("action");
        switch (action == null ? "none" : action) {
            case "login":
                AuthorizedUser.setId(Integer.parseInt(request.getParameter("authUser")));
                response.sendRedirect("meals");
                break;
            default:
                response.sendRedirect("index.html");
                break;
        }


    }
}
