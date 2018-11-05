package com.junker.httpmock.servlet;

import com.junker.httpmock.dao.SearchMockConfDAO;
import com.junker.httpmock.data.MockDetailData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@WebServlet(name = "MockConditionSearchServlet")
public class MockConditionSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        SearchMockConfDAO smf =new SearchMockConfDAO();
        String id=request.getParameter("mockDetail_id");
        try {
            smf.mockConditionSearch(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MockDetailData mdd=new MockDetailData();
        HashMap<String,String> conditions =mdd.conditions;
        Set<String> setKey = conditions.keySet();
        Iterator<String> iterator = setKey.iterator();
        String conditionString="";
        String conditionId="";
        String conditionS="";
        while(iterator.hasNext()) {
            conditionId= iterator.next();
            conditionString=conditions.get(conditionId);
            conditionS+=conditionId+":"+conditionString+",";
        }
        PrintWriter out = response.getWriter();
        out.print(conditionS);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
