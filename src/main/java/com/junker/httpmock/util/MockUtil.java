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
    public JSONObject jsonMSG=new JSONObject(msg);
    public String timeout="100";
    public String code="200";

    public void  postMocker(String url,String para){
        ArrayList<String> matchlist=new ArrayList<String>();
        SearchMockConfDAO smcd= new SearchMockConfDAO();
        String mockAPI_name="";
        mockAPI_name=url.replaceAll("/httpmock/mocking","");
        try {
            matchlist=smcd.mockSettingMatch(para,mockAPI_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(matchlist.size()>0){
            timeout = matchlist.get(0);
            code = matchlist.get(1);
            msg = matchlist.get(2);
        }
        jsonMSG = new JSONObject(msg);
    }
}
