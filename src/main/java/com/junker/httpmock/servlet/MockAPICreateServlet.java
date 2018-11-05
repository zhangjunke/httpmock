package com.junker.httpmock.servlet;

import com.junker.httpmock.dao.SearchMockConfDAO;
import com.junker.httpmock.data.FormData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(name = "MockAPICreateServlet")
public class MockAPICreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        FormData fd=new FormData();
        String server=request.getParameter("server") ;
        String API=request.getParameter("API") ;

        SearchMockConfDAO smcd = new SearchMockConfDAO();
        HashMap<Integer,String> mockAPI=new HashMap<Integer,String>();
        String result="0";//API不存在且创建成功
        try {
            mockAPI=smcd.mockAPISearch(server);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(mockAPI.containsValue(API)){
            result="1";//API已存在
        }else if(!API.startsWith("/")) {
            result="2";//API非“/”开头
        }else
        {
            try {
                smcd.APICreate(server, API);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
