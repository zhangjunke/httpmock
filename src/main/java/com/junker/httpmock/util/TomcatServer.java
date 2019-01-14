package com.junker.httpmock.util;

public class TomcatServer {
    private String context = "";
    private String webapp = "";
    private String name = "localhost";
    private int port = 8080;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getWebapp() {
        return webapp;
    }

    public void setWebapp(String webapp) {
        this.webapp = webapp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }



}
