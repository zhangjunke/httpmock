package com.junker.httpmock.util;

import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import java.io.IOException;

public class TomcatStart {
    public static void start(int port,String name,String classPath,String basePath,String confPath) throws LifecycleException, IOException {
        //2.新建一个Tomcat对象
        Tomcat tomcat = new Tomcat();

        //3.创建一个连接器
        Connector connector = tomcat.getConnector();
        //4.连接器有一个端口属性
        connector.setPort(port);

        //5.设置Host
        Host host = tomcat.getHost();

        //6.设置Host的属性，可以参照Server.xml来进行理解
        host.setName(name);
        //host.setAppBase(appBase);

        //7.加载servlet
        //tomcat.getHost().setAppBase(basePath);
        tomcat.addWebapp("",basePath);
        AddMapping.addMapping(tomcat,host,classPath,basePath,confPath);
        tomcat.start();
        //强制Tomcat Server等待，避免main线程执行结束后关闭
        tomcat.getServer().await();
    }

}
