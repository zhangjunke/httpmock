package com.junker.httpmock.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StaticResponse {

    private OutputStream os = null;
    public StaticResponse(OutputStream os) throws IOException {
        this.os = os;
    }

    public void writerFile(String path)throws  IOException {
        FileInputStream fis = new FileInputStream(path);

        byte[] buff = new byte[1024];
        int len = 0;

        StringBuffer sb =  new StringBuffer();
        sb.append("HTTP/1.1 200 OK\n");
        sb.append("Content-Type: text/html;charset=UTF-8\n");
        sb.append("\r\n");

        //响应头信息写出去
        os.write(sb.toString().getBytes());
        while ((len=fis.read(buff))!= -1) {
            os.write(buff,0,len);
        }
        fis.close();
        os.flush();
        os.close();
    }
}
