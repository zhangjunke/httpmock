package com.junker.httpmock.servlet;

import com.junker.httpmock.dao.SearchMockConfDAO;
import com.junker.httpmock.util.RegexUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

@WebServlet(name = "MockConditionSettingServlet")
public class MockConditionSettingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        SearchMockConfDAO smf =new SearchMockConfDAO();
        String url=request.getRequestURI();
        RegexUtil ru=new RegexUtil();
        SearchMockConfDAO smcf=new SearchMockConfDAO();
        String detailId=ru.getMatcher("detailId=(.*)&conditionId",url);
        String conditionId=ru.getMatcher("conditionId=(.*)&conditionS",url);
        String conditionS1=ru.getMatcher("conditionS=(.*)",url);
        detailId=detailId.replaceAll("\"","");
        conditionId=conditionId.replaceAll("\"","");
        String conditionS=java.net.URLDecoder.decode(conditionS1,"UTF-8").replaceAll("\"","").replaceAll("\'","");
		String result="";

        if (!url.contains("type=delete")&&conditionS.length()!=0&&conditionS.split("=").length!=2) {
            result="1";
        }else {
            try {
                if (url.contains("type=delete")) {
                    smcf.mockConditionDelete(conditionId);
                    result="0";
                }else if (url.contains("type=modify")) {
                    smcf.mockConditionUpdate(conditionId, conditionS);
                    result="0";
                } else{
                    smcf.mockConditionCreate(detailId, conditionS);
                    result="0";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }
public static void main(String[] argx){
        String url="http://localhost:8080/mocker/MockConditionSetting/type=add&detailId=6&conditionId=0&conditionS=%22A=123%22";
    RegexUtil ru=new RegexUtil();
    String detailId=ru.getMatcher("detailId=(.*)&conditionId",url);
    String conditionId=ru.getMatcher("conditionId=(.*)&conditionS",url);
    String conditionS1=ru.getMatcher("conditionS=(.*)",url);
    String conditionS= null;
    try {
        conditionS = java.net.URLDecoder.decode(conditionS1,"UTF-8").replaceAll("\"","");
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    System.out.println(detailId);
    System.out.println(conditionId);
    System.out.println(conditionS);
}

}
