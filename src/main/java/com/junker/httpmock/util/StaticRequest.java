package com.junker.httpmock.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by User on 2017/8/25.
 */
public class StaticRequest {

    private String uri;
    //根据客户端的不同请求uri 而响应不用的资源给客户端

    //解析uri发生时间点在输入流管道接进来的时候应该进行解析
    public StaticRequest(InputStream is) throws IOException{

        resolverRequest(is);
    }

    private void resolverRequest(InputStream is) throws  IOException{
        byte[] buff = new byte[1024];
        int length = 0;
        try {
            length = is.read(buff);
            if(length > 0) {
                //将读取出来的字节信息 转化成明文信息
                String msg = new String(buff,0,length);
                System.out.println("客户端的请求信息：****"+ msg+ "****");
                //解析出来uri
                uri = msg.substring(msg.indexOf("/"),msg.indexOf("HTTP/1.1")-1);


            }else {
                System.out.println("Bad Request ****");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getUri() {
        return uri;
    }
}
