package com.example.core.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Kevin on 2016/6/10.
 */
public class StreamUtils {
    public static String inputStreamToString(InputStream in, String encode) throws IOException {
        String str = "";
        if (encode == null || encode.equals("")) {
            encode = "UTF-8";
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, encode));
        try {
            StringBuffer stringBuffer = new StringBuffer();

            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str).append("\n");
            }
            return stringBuffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            bufferedReader.close();
        }
        return str;
    }

    public static InputStream stringToInputStream(String in, String encode) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(in.getBytes(encode));
    }


    public static Reader stringToReader(String in, String encode) throws UnsupportedEncodingException {
        return new InputStreamReader(stringToInputStream(in, encode));
    }


}
