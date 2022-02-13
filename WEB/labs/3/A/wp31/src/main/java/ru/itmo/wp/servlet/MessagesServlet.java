package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;


public class MessagesServlet extends HttpServlet {
    private final String FIND_ALL = "/message/findAll", AUTH = "message/auth", ADD = "message/add";

    private enum Path {
        AUTH("auth");
        private final String path;

        Path(String path) {
            this.path = path;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        String json;
        ArrayList<UserData> allUsers = (ArrayList<UserData>) session.getAttribute("allUsers");
        if (allUsers == null) {
            allUsers = new ArrayList<>();
        }

        if (uri.contains(AUTH)) {
            String user = (String) session.getAttribute("user");
            if (user == null) {
                user = request.getParameter("user");
            }
            if (user != null) {
                session.setAttribute("user", user);
            }
            if (existsName(user, allUsers) == -1 && user != null) {
                allUsers.add(new UserData(user, ""));
            }
            session.setAttribute("allUsers", allUsers);
            json = new Gson().toJson(user);
            response.getWriter().write(json);
            response.getWriter().flush();

        } else if (uri.endsWith(FIND_ALL)) {
            json = new Gson().toJson(allUsers);
            response.getWriter().write(json);
            response.getWriter().flush();
        } else if (uri.contains(ADD)) {
            String text = request.getParameter("text");
            String user = (String) session.getAttribute("user");
            int ind = existsName(user, allUsers);
            if (ind > -1) {
                allUsers.get(ind).text = text;
            }
            session.setAttribute("allUsers", allUsers);
        }
    }
    private int existsName(String name, ArrayList<UserData> arr) {
        for (int i = 0; i < arr.size(); i++) {
            if (name.equals(arr.get(i).user)) {
                return i;
            }
        }
        return -1;
    }

}









