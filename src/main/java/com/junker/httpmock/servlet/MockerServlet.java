package com.junker.httpmock.servlet;

import com.junker.httpmock.util.JsonFormatCheck;
import com.junker.httpmock.util.JsonStringExchangeUtil;
import com.junker.httpmock.util.MockUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

@WebServlet(name = "MockerServlet")
public class MockerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger =Logger.getLogger("logger");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer resultBuffer = new StringBuffer();
        request.setCharacterEncoding("utf-8");
        JsonFormatCheck jfc = new JsonFormatCheck();

        String requestURI=request.getRequestURI();//获取接口相对路径

        Enumeration<String> requestHeaderNames=request.getHeaderNames();//获取请求头枚举列表
        String requestHeadersString = "";
        while (requestHeaderNames.hasMoreElements()) {
            String requestHeaderString = requestHeaderNames.nextElement();
            System.out.println("requestHeaderS："+requestHeaderString);
            String headerValueString = "";
            headerValueString = request.getHeader(requestHeaderString);
            requestHeadersString+=requestHeaderString+"="+headerValueString+"&";
        }
        if(requestHeadersString.endsWith("&")){
            requestHeadersString=requestHeadersString.substring(0,requestHeadersString.length()-1);
        }
        System.out.println("requestHeadersString:"+requestHeadersString);


        Enumeration<String> paramNames = request.getParameterNames();//获取请求参数枚举列表
        String paraString="";
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
        mu.postMocker(requestURI,paraString,requestHeadersString);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String msg=mu.msg;
        String timeout=mu.timeout;
        String code=mu.code;
        String headers="["+mu.header+"]";
        System.out.println("Mockermsg:"+msg);
        System.out.println("Mockertimeout:"+timeout);
        System.out.println("Mockercode:"+code);
        System.out.println("Mockerheaders:"+headers);

        JSONArray json = JSONArray.fromObject(headers);
        for(int i=0; i<json.size(); i++){
            JSONObject obj = (JSONObject) json.get(i);
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = it.next().toString();
                String value = obj.get(key).toString();
                response.setHeader(key,value);//设置responseheader
            }
        }

        try {
            Thread.sleep(Long.parseLong(timeout));
            response.setStatus(Integer.parseInt(code));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resultBuffer.append(msg);
        PrintWriter out = response.getWriter();
        out.println(resultBuffer.toString());
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    public static void main(String[] args){
        String headers="[{\"Content-type\":\"text/html;charset=UTF-8\"}]";
        JSONArray json = JSONArray.fromObject(headers);
    }
}
