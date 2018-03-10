package org.examples.pbk.otus.l131homework.servlet;

import org.examples.pbk.otus.l131homework.dataset.AccountDataSet;
import org.examples.pbk.otus.l131homework.dbservice.AccountCachedHibernateDbService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private AccountCachedHibernateDbService dbService;

    @Override
    public void init() {
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.dbService = (AccountCachedHibernateDbService) ac.getBean("accountDbService");
        dbService.create(new AccountDataSet("admin", "pass"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        AccountDataSet account = dbService.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            req.getSession(true).setAttribute("auth", username + ":" + password);
            
            resp.sendRedirect("/admin");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Unauthorized");
        }
    }
}
