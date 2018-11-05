package com.junker.httpmock.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonStringExchangeUtil {
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String JsonStringToPara(String JsonString){
        String para=JsonString;
        if(JsonString.contains("{")&&JsonString.contains("}")){
            para= para.replaceAll("\n","");
            para= para.replaceAll("\r","");
            para= para.replaceAll("\t","");
            para= para.replaceAll("\r\n","");
            para= para.replaceAll("\\s","");
            para=para.trim();
            para= para.replaceAll("\\{","");
            para= para.replaceAll("\\}","");
            para= para.replaceAll(",","&");
            para= para.replaceAll("\"","");
            para= para.replaceAll(":","=");
            if(para.endsWith("&")){
                para=para.substring(0,para.length()-1);
            }
        }
        return para;
    }

    public static void main(String[] args){
        String s="{\"A\":\"12\",\"B\":\"231\"}";
       s= JsonStringExchangeUtil.JsonStringToPara(s);
       System.out.print(s);
    }
}
