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

@WebServlet(name = "MockDetailCreateServlet")
public class MockDetailCreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        JsonFormatCheck jfc=new JsonFormatCheck();

        String createResult="0";
        String mockType=request.getParameter("mockType");
        String mockCaseName=request.getParameter("mockCaseName");
        String mock_timeout=request.getParameter("mock_timeout");
        String mockCode=request.getParameter("mockCode");
        String mockResponseMsg=request.getParameter("mockResponseMsg");
        String mockAPI=request.getParameter("mockAPI");
        String name=request.getParameter("name");

        int result=jfc.checkFormat_Json(mockResponseMsg);
        if(result!=0){
            createResult="1";
        }else {
            SearchMockConfDAO smf = new SearchMockConfDAO();
            try {
                String APIId=smf.mockAPIidSearch(mockAPI);
                String nameId=smf.useridSearch(name);
                smf.mockDetailCreate(APIId,mockType,mockCaseName,mock_timeout,mockCode,mockResponseMsg,nameId);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        PrintWriter out = response.getWriter();
        out.print(createResult);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
