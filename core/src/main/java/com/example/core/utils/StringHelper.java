package com.example.core.utils;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by kevinhao on 16/7/20.
 */
public class StringHelper {
    private static final Pattern phone = Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
    private static final Pattern emailer = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");


    public static boolean isPhone(CharSequence phoneNum) {
        return isEmpty(phoneNum)?false:phone.matcher(phoneNum).matches();
    }

    public static boolean isEmail(CharSequence email) {
        return isEmpty(email)?false:emailer.matcher(email).matches();
    }

    public static boolean isEmpty(CharSequence input) {
        if(input != null && !"".equals(input)) {
            for(int i = 0; i < input.length(); ++i) {
                char c = input.charAt(i);
                if(c != 32 && c != 9 && c != 13 && c != 10) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isEquals(CharSequence a, CharSequence b) {
        return a == null ? b == null? true: false : a.equals(b);
    }
}
