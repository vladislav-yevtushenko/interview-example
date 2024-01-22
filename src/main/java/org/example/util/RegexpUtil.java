package org.example.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpUtil {

    public static String getByRegexp(String content, String pattern) {
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}