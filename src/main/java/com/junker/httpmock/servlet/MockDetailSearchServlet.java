package com.junker.httpmock.servlet;

import com.junker.httpmock.dao.SearchMockConfDAO;
import com.junker.httpmock.data.FormData;
import com.junker.httpmock.data.MockDetailData;
import com.junker.httpmock.util.JsonStringExchangeUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "MockDetailSearchServlet")
public class MockDetailSearchServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(MockDetailSearchServlet.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        FormData fd=new FormData();
        MockDetailData mdd =new MockDetailData();
        String serverid=request.getParameter("server") ;
        String departmentid=request.getParameter("department") ;
        String mockAPI_nameid=request.getParameter("API") ;
        String userid=request.getParameter("name") ;

        String server=fd.server.get(Integer.parseInt(serverid));
        String mockAPI_name=fd.mockAPI.get(Integer.parseInt(mockAPI_nameid));
        String department=fd.department.get(Integer.parseInt(departmentid));
        String user=fd.user.get(Integer.parseInt(userid));
        logger.debug("server:"+server);
        logger.debug("mockAPI_name:"+mockAPI_name);
        logger.debug("department:"+department);
        logger.debug("user:"+user);
        System.out.println("server:"+server);
        System.out.println("mockAPI_name:"+mockAPI_name);
        System.out.println("department:"+department);
        System.out.println("user:"+user);
        SearchMockConfDAO smcd = new SearchMockConfDAO();
        String JSONPrefix="{\"list\":[";
        String JSONPostfix="]}";
        String JSONString="";
        JSONObject jsonMSG =JSONObject.fromObject("{}");
        try {
            HashMap<Integer, HashMap<String,String>> mockAPIMap=smcd.mockDetailsSearch(server,mockAPI_name,department,user);
            int count =mockAPIMap.size();
            String jsonString="";
            for(int i=0;i<count;i++){
                HashMap<String, String> mockAPI = new HashMap<String, String>();
                mockAPI=mockAPIMap.get(i);
                int apicount=mockAPI.size();
                String id="";
                String author="";
                String mockType="";
                String mockCaseName="";
                String mock_timeout="";
                String mockCode="";
                String mockResponseMsg="";
                String eachAPIString="";
                for(int j=0;j<apicount;j++){
                    id=mockAPI.get("id");
                    author=mockAPI.get("author");
                    mockType=mockAPI.get("mockType");
                    mockCaseName=mockAPI.get("mockCaseName");
                    mock_timeout=mockAPI.get("mock_timeout");
                    mockCode=mockAPI.get("mockCode");
                    mockResponseMsg=mockAPI.get("mockResponseMsg");
                    smcd.mockConditionSearch(id);
                    HashMap<String,String> conditions =mdd.conditions;
                    String conditionString="";

                    Set<String> setKey = conditions.keySet();
                    Iterator<String> iterator = setKey.iterator();
                    while(iterator.hasNext()){
                        String key = iterator.next();
                        conditionString+=conditions.get(key)+"|";
                        // 此时的String类型的key就是我们需要的获取的值
                    }
                    eachAPIString="{\"id\":\""+id+"\",\"author\":\""+author+"\","+"\"mockType\":\""+mockType+"\","+"\"mockCaseName\":\""+mockCaseName+"\","+"\"mock_timeout\":\""+mock_timeout+"\","+
                            "\"mockCode\":\""+mockCode+"\","+"\"mockCondition\":\""+conditionString+"\","+"\"mockResponseMsg\":"+mockResponseMsg+"}";
                }
                jsonString+=eachAPIString+",";
            }
            JsonStringExchangeUtil jseu=new JsonStringExchangeUtil();
            JSONString=JSONPrefix+jsonString+JSONPostfix;
            JSONString=jseu.replaceBlank(JSONString);
            JSONString=JSONString.replace("},]}","}]}");
            System.out.println("JSONString:|"+JSONString+"|");
            //jsonMSG =JSONObject.fromObject(JSONString);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JSONString);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
