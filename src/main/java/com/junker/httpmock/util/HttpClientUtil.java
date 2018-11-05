package com.junker.httpmock.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.junker.httpmock.global.SSLClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
public class HttpClientUtil {
    public String doGet(String url,Map<String,String> HeaderMap,String charset){
        HttpClient httpClient = null;

        // get method
        HttpGet httpGet = new HttpGet(url);

        // set headers
        List<NameValuePair> HeaderList = new ArrayList<NameValuePair>();
        Iterator iterator1 = HeaderMap.entrySet().iterator();
        while(iterator1.hasNext()){
            Entry<String,String> elem = (Entry<String, String>) iterator1.next();
            httpGet.setHeader(elem.getKey(),elem.getValue());
        }

        //response
        HttpResponse response = null;
        try{
            response = httpClient.execute(httpGet);
        }catch (Exception e) {}

        //get response into String
        String temp="";
        try{
            HttpEntity entity = response.getEntity();
            temp=EntityUtils.toString(entity,"UTF-8");
        }catch (Exception e) {}

        return temp;
    }
    public String doPost(String url,Map<String,String> map,String charset){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Entry<String,String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
