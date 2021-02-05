package com.coderone95.secu.utility;

public class StringUtils {

    public static boolean isNullOrBlank(String value){
        if(value == null || value == ""){
            return true;
        }
        return false;
    }
}
