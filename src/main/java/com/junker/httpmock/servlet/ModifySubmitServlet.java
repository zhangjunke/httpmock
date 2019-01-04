package com.junker.httpmock.servlet;

import com.junker.httpmock.dao.SearchMockConfDAO;
import com.junker.httpmock.util.JsonFormatCheck;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "ModifySubmitServlet")
public class ModifySubmitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String detailid=request.getParameter("detailid");
        String caseName=request.getParameter("caseName");
        String code=request.getParameter("code");
        String time=request.getParameter("time");
        String msg=request.getParameter("msg");
        String header=request.getParameter("header");
        //String condition=request.getParameter("condition");
        System.out.println("modifySubmit_detailid:"+detailid);
        System.out.println("modifySubmit_caseName:"+caseName);
        System.out.println("modifySubmit_code:"+code);
        System.out.println("modifySubmit_time:"+time);
        System.out.println("modifySubmit_msg:"+msg);
        System.out.println("modifySubmit_header:"+header);
        JsonFormatCheck jfc=new JsonFormatCheck();
        int result1=jfc.checkFormat_Json(msg);
        int result2=jfc.checkFormat_Json(header);
        SearchMockConfDAO smf =new SearchMockConfDAO();
        String modifyResult="";
        if(result1!=0||result2!=0){
            modifyResult="1";
        }else {
            try {
                smf.mockDetailUpdate("返回值", caseName, time, code, msg, header,detailid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            modifyResult="0";
        }
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        out.println(modifyResult);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
