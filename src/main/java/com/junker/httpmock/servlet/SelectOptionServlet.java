package com.junker.httpmock.servlet;

import com.junker.httpmock.dao.SearchMockConfDAO;
import com.junker.httpmock.util.JsonStringExchangeUtil;
import com.junker.httpmock.util.RegexUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(name = "SelectOptionServlet")
public class SelectOptionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        {
            SearchMockConfDAO smcd = new SearchMockConfDAO();
            RegexUtil ru =new RegexUtil();
            HashMap<Integer,String> searchResult=new HashMap<Integer,String>();
            request.setCharacterEncoding("utf-8");
            String uri=request.getRequestURI();
            String type = ru.getMatcher("type=(.*)",uri);
            String name = request.getParameter("name");
            String value="";
            String JSONPrefix="{\"list\":[";
            String JSONPostfix="]}";
            String JSONString="";
            JSONObject jsonMSG =JSONObject.fromObject("{}");


            try {
                if (type.equals("department")) {
                    searchResult = smcd.userSearch(name);
                }else if (type.equals("server")) {
                    searchResult = smcd.mockAPISearch(name);
                }
                String valueL="";
                for(int i=1;i<=searchResult.size();i++){
                    value=searchResult.get(i);
                    valueL+="{\"id\":"+"\""+Integer.toString(i)+"\","+"\"name\":"+"\""+value+"\"},";
                }
                JsonStringExchangeUtil jseu=new JsonStringExchangeUtil();
                JSONString=JSONPrefix+valueL+JSONPostfix;
                JSONString=jseu.replaceBlank(JSONString);
                JSONString=JSONString.replace("},]}","}]}");
                System.out.println("JSONString:|"+JSONString+"|");
                //obj=JSONString;
                jsonMSG =JSONObject.fromObject(JSONString);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonMSG);
            out.flush();
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    }

