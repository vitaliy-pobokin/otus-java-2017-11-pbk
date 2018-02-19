package org.examples.pbk.otus.l121homework.servlet;

import org.examples.pbk.otus.l121homework.cache.CacheEngineImpl;
import org.examples.pbk.otus.l121homework.dao.AccountDataSetHibernateDao;
import org.examples.pbk.otus.l121homework.dataset.AccountDataSet;
import org.examples.pbk.otus.l121homework.dbservice.AccountCachedHibernateDbService;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private AccountCachedHibernateDbService dbService;

    @Override
    public void init() throws ServletException {
        this.dbService = new AccountCachedHibernateDbService(
                new AccountDataSetHibernateDao(),
                new CacheEngineImpl<>(0, 0));
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
