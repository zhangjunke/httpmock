package com.junker.httpmock.util;

import com.alibaba.fastjson.JSONObject;

public class JsonFormatCheck {
    public static int checkFormat_JsonOrForm(String string){
        if(string.split("=").length>1){
            return 0;
        }
        try {
            JSONObject jsonStr= JSONObject.parseObject(string);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
    public static void main(String[] args){
        JsonFormatCheck jfc=new JsonFormatCheck();
        String s="api_id=557c656d71f54a7e8f8c681d7a9ab006&api_secret=7ff1e984d75b48f9b3ba3aa3033ad252\n";
        System.out.println(jfc.checkFormat_JsonOrForm(s));
    }
}
