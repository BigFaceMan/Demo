package ssp.miniSpring.web;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import ssp.miniSpring.Autowired;
import ssp.miniSpring.Component;
import ssp.miniSpring.PostConstruct;

import java.io.File;

@Component
public class TomcatServer {
    @Autowired
    DispatcherServlet dispatcherServlet;

    @PostConstruct
    public void start() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        int port = 8088;
        tomcat.setPort(port);
        tomcat.getConnector();

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);

        tomcat.addServlet(contextPath, "dispatcherServlet", dispatcherServlet);
        context.addServletMappingDecoded("/*", "dispatcherServlet");
        tomcat.start();
        System.out.println("tomcat start suc, port : " + port);
    }
}
