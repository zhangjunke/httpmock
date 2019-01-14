package com.junker.httpmock.servlet;

import com.junker.httpmock.util.AbsolutePath;
import com.junker.httpmock.util.FileRead;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.logging.Logger;


@WebServlet(name = "StaticServlet")
public class StaticServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger("logger");

    protected void doPost(HttpServletRequest Request, HttpServletResponse Response) throws IOException {
        doGet(Request,Response);
    }
    protected void doGet(HttpServletRequest Request, HttpServletResponse Response) throws IOException {
        StringBuffer resultBuffer = new StringBuffer();
        String requestURI = Request.getRequestURI();
        AbsolutePath.getPath();
        String url = AbsolutePath.basePath + requestURI;
        System.out.println("[[[url]]]"+url);
        if (!new File(url).exists()) {
                System.out.println("servlet");
                MockerServlet ms = new MockerServlet();
                try {
                    ms.doPost(Request, Response);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
                return;
        } else {
            if (url.contains(".html")) {
                Response.setHeader("Content-type", "text/html;charset=UTF-8");
                System.out.println(".html");
            }
            if (url.contains(".js")) {
                Response.setHeader("Content-type", "application/x-javascript;charset=UTF-8");
            }
            Response.setCharacterEncoding("UTF-8");
            PrintWriter out = Response.getWriter();
            resultBuffer.append(FileRead.readToString(url));
            out.println(resultBuffer.toString());
            out.flush();
            out.close();
        }

    }
}
