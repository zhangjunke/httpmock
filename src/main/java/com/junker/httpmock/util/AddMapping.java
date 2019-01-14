package com.junker.httpmock.util;

import com.junker.httpmock.servlet.*;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;


public class AddMapping {
    public static void addMapping(Tomcat tomcat, Host host,String classPath,String basePath,String confPath){
        //7.把class加载进来，把启动的工程加入进来了
        Context context = tomcat.addContext(host,"/",classPath);
        context.setResourceOnlyServlets(basePath);
        String dufaultContextXml =PropertiesUtil.getPropertiesValue(confPath,"ENTITY.WEB.CONTEXT");
        if(context instanceof StandardContext) {
            StandardContext standardContext = (StandardContext) context;
            //要给一个默认的web.xml文件
            standardContext.setDefaultContextXml(dufaultContextXml);
            //把server设置进去
            Wrapper wrapper = tomcat.addServlet("/","StaticServlet",new StaticServlet());
            wrapper.addMapping("/");
            Wrapper wrapper21 = tomcat.addServlet("/","IndexPageServlet_department",new IndexSelectServlet());
            wrapper21.addMapping("/mocker/index/type=department");
            Wrapper wrapper22 = tomcat.addServlet("/","IndexPageServlet_server",new IndexSelectServlet());
            wrapper22.addMapping("/mocker/index/type=server");
            Wrapper wrapper31 = tomcat.addServlet("/","SelectOptionServlet_department",new SelectOptionServlet());
            wrapper31.addMapping("/mocker/selectOption/type=department");
            Wrapper wrapper32 = tomcat.addServlet("/","SelectOptionServlet_server",new SelectOptionServlet());
            wrapper32.addMapping("/mocker/selectOption/type=server");
            Wrapper wrapper4 = tomcat.addServlet("/","MockDetailSearchServlet",new MockDetailSearchServlet());
            wrapper4.addMapping("/mocker/MockDetailSearch");
            Wrapper wrapper5 = tomcat.addServlet("/","ModifySubmitServlet",new ModifySubmitServlet());
            wrapper5.addMapping("/mocker/ModifySubmit");
            Wrapper wrapper6 = tomcat.addServlet("/","MockDetailDeleteServlet",new MockDetailDeleteServlet());
            wrapper6.addMapping("/mocker/DeleteDetail");
            Wrapper wrapper7 = tomcat.addServlet("/","MockConditionSearchServlet",new MockConditionSearchServlet());
            wrapper7.addMapping("/mocker/MockConditionSearch");
            Wrapper wrapper8 = tomcat.addServlet("/","MockConditionSettingServlet",new MockConditionSettingServlet());
            wrapper8.addMapping("/mocker/MockConditionSetting");
            Wrapper wrapper9 = tomcat.addServlet("/","MockDetailCreateServlet",new MockDetailCreateServlet());
            wrapper9.addMapping("/mocker/MockDetailCreate");
            Wrapper wrapper10 = tomcat.addServlet("/","MockAPICreateServlet",new MockAPICreateServlet());
            wrapper10.addMapping("/mocker/MockAPICreate");
            Wrapper wrapper11 = tomcat.addServlet("/","MockAuthorCreateServlet",new MockAuthorCreateServlet());
            wrapper11.addMapping("/mocker/MockAuthorCreate");
        }

    }

    }

