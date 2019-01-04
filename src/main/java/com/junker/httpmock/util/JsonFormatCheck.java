package com.junker.httpmock.util;

import com.alibaba.fastjson.JSONObject;

public class JsonFormatCheck {
    public static int checkFormat_Json(String string){
        try {
            JSONObject jsonStr= JSONObject.parseObject(string);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
    public static void main(String[] args){
        JsonFormatCheck jfc=new JsonFormatCheck();
        String s="\"Content-type\", \"text/html;charset=UTF-8\"1";
        System.out.println(jfc.checkFormat_Json(s));
    }
}
