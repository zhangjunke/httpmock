package com.junker.httpmock.data;

import java.util.HashMap;

public class MockDetailData {
    public static  HashMap<Integer, HashMap<String,String>> mockAPIMap = new HashMap<Integer, HashMap<String,String>>();
    public static HashMap<String,String>  conditions = new HashMap<String,String> ();

    public static HashMap<String,String>  getConditions() {
        return conditions;
    }

    public static void setConditions(HashMap<String,String>  conditions) {
        MockDetailData.conditions = conditions;
    }


    public static HashMap<Integer, HashMap<String, String>> getMockAPIMap() {
        return mockAPIMap;
    }

    public static void setMockAPIMap(HashMap<Integer, HashMap<String, String>> mockAPIMap) {
        MockDetailData.mockAPIMap = mockAPIMap;
    }


}
