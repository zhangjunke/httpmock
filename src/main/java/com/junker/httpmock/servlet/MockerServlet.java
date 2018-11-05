package com.junker.httpmock.servlet;

import com.junker.httpmock.util.JsonFormatCheck;
import com.junker.httpmock.util.JsonStringExchangeUtil;
import com.junker.httpmock.util.MockUtil;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import org.apache.log4j.Logger;

@WebServlet(name = "MockerServlet")
public class MockerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger =Logger.getLogger("logger");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer resultBuffer = new StringBuffer();
        request.setCharacterEncoding("utf-8");
        String requestURI=request.getRequestURI();
        String paraString="";
        JsonFormatCheck jfc=new JsonFormatCheck();
        Enumeration<String> paramNames = request.getParameterNames();

        while (paramNames.hasMoreElements()) {
            String paraNameString = paramNames.nextElement();
            System.out.println("paraS："+paraNameString);
            if (jfc.checkFormat_Json(paraNameString) == 0) {
                paraString = JsonStringExchangeUtil.JsonStringToPara(paraNameString);
            } else {
                String paraValueString = "";
                paraValueString = request.getParameter(paraNameString);
                paraString+=paraNameString+"="+paraValueString+"&";
            }
        }
        if(paraString.endsWith("&")){
            paraString=paraString.substring(0,paraString.length()-1);
        }
        System.out.println("paraString:"+paraString);
        MockUtil mu=new MockUtil();
        mu.postMocker(requestURI,paraString);

        JSONObject jsonMSG=mu.jsonMSG;
        String timeout=mu.timeout;
        String code=mu.code;

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            Thread.sleep(Long.parseLong(timeout));
            response.setStatus(Integer.parseInt(code));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resultBuffer.append(jsonMSG);
        PrintWriter out = response.getWriter();
        out.println(resultBuffer.toString());
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
