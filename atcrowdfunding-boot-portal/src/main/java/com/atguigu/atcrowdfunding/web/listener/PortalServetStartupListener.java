package com.atguigu.atcrowdfunding.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener  //声明监听器对象,需要设置扫描注解@ServletComponentScan
//@WebServlet //声明Servlet对象
//@WebFilter //声明过滤器对象
public class PortalServetStartupListener implements ServletContextListener {
    // 观察服务器启动时,监听器对象是否被创建.
    public PortalServetStartupListener() {
        System.out.println("PortalServetStartupListener...");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        String path = application.getContextPath();
        application.setAttribute("APP_PATH", path);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
}
