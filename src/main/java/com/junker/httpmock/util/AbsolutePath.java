package com.junker.httpmock.util;

public class AbsolutePath {
    public static String classPath="";
    public static String basePath="";
    public static String serverConfPath="";
    public static String dbConfPath="";

    public static void  getPath(){
        String os = System.getProperty("os.name");
        classPath = System.getProperty("user.dir");
        if(os.toLowerCase().startsWith("win")){
            basePath= classPath+"\\src\\main\\webapp";
            serverConfPath = classPath+"\\src\\main\\webapp\\Properties\\server.properties";
            dbConfPath = classPath+"\\src\\main\\webapp\\Properties\\mysql.properties";
        }else{
            basePath= classPath+"/src/main/webapp";
            serverConfPath = classPath+"/src/main/webapp/Properties/server.properties";
            dbConfPath = classPath+"/src/main/webapp/Properties/mysql.properties";
        }

    }
}
