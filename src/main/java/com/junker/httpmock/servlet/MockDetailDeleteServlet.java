package com.junker.httpmock.servlet;

import com.junker.httpmock.dao.SearchMockConfDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "MockDetailDeleteServlet")
public class MockDetailDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        SearchMockConfDAO smf =new SearchMockConfDAO();
        String id=request.getParameter("mockDetail_id");
        int result=0;
        String r="提交成功！";
        try {
            result=smf.mockDetailDelete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result==10000){
            r="系统创建，不允许删除！";
        }
        PrintWriter out = response.getWriter();
        out.println(r);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
