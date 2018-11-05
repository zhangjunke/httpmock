package com.junker.httpmock.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
        public static String getMatcher(String regex, String source) {
            String result = "";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(source);
            while (matcher.find()) {
                result = matcher.group(1);
             }
            return result;
    }
}
