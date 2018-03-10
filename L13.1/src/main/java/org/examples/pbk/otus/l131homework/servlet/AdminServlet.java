package org.examples.pbk.otus.l131homework.servlet;

import org.examples.pbk.otus.l131homework.RandomDbTask;
import org.examples.pbk.otus.l131homework.cache.CacheEngineImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
    private static final int REFRESH_PERIOD = 1000;

    private CacheEngineImpl cacheEngine;
    private RandomDbTask randomDbTask;

    @Override
    public void init() {
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.cacheEngine = (CacheEngineImpl) ac.getBean("testCacheEngine");
        this.randomDbTask = (RandomDbTask) ac.getBean("randomDbTask");
        randomDbTask.start(1000, 1000);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("refreshPeriod", REFRESH_PERIOD);
        pageVariables.put("size", cacheEngine.getCacheSize());
        pageVariables.put("hits", cacheEngine.getHitCount());
        pageVariables.put("misses", cacheEngine.getMissCount());
        pageVariables.put("lifetime", cacheEngine.getLifeTime());
        pageVariables.put("idletime", cacheEngine.getIdleTime());
        resp.setCharacterEncoding("UTF-8");
        if (req.getSession(false) != null) {
            resp.getWriter().write(TemplateProcessor.instance().getPage("admin.html", pageVariables));
        } else {
            resp.sendRedirect("/");
        }
    }
}
