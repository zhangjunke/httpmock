package com.junker.httpmock.data;

import java.util.HashMap;

public class FormData {

    public static HashMap<Integer,String> department=new HashMap<Integer,String>();
    public static HashMap<Integer,String> server=new HashMap<Integer,String>();
    public static HashMap<Integer,String> user=new HashMap<Integer,String>();
    public static HashMap<Integer,String> mockAPI=new HashMap<Integer,String>();

    public HashMap<Integer, String> getDepartment() {
        return department;
    }

    public void setDepartment(HashMap<Integer, String> department) {
        this.department = department;
    }

    public HashMap<Integer, String> getServer() {
        return server;
    }

    public void setServer(HashMap<Integer, String> server) {
        this.server = server;
    }

    public HashMap<Integer, String> getUser() {
        return user;
    }

    public void setUser(HashMap<Integer, String> user) {
        this.user = user;
    }

    public HashMap<Integer, String> getMockAPI() {
        return mockAPI;
    }

    public void setMockAPI(HashMap<Integer, String> mockAPI) {
        this.mockAPI = mockAPI;
    }



}
