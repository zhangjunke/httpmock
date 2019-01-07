package com.junker.httpmock.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.junker.httpmock.dao.SearchMockConfDAO;
import org.json.JSONObject;

public class MockUtil {
    private static Logger logger =Logger.getLogger("logger");
    public String msg="{\n" +
            "\"recode\":\"1111\",\n" +
            "\"remsg\":\"未找到匹配mock数据\",\n"+
            "}\n";
    public String timeout="100";
    public String code="200";
    public String header="";
    public String callbackURL ="";
    public String callbackType ="";
    public String callbackPara ="";
    public void  postMocker(String url,String para,String requestHeadersString){
        ArrayList<String> matchlist=new ArrayList<String>();
        SearchMockConfDAO smcd= new SearchMockConfDAO();
        String mockAPI_name="";
        mockAPI_name=url.replaceAll("/httpmock/mocking","");
        try {
            matchlist=smcd.mockSettingMatch(para,mockAPI_name,requestHeadersString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(matchlist.size()>0){
            timeout = matchlist.get(0);
            code = matchlist.get(1);
            msg = matchlist.get(2);
            header = matchlist.get(3);
            callbackURL= matchlist.get(4);
            callbackType= matchlist.get(5);
            callbackPara= matchlist.get(6);
        }
    }
}
