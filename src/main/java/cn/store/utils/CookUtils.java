package cn.store.utils;

import javax.servlet.http.Cookie;

public class CookUtils {

    public static Cookie getCookieByName(String name,Cookie[] cookies){
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(name.equals(cookie.getName())){
                    return cookie;
                }
            }
            return null;
        }
        return null;
    }
}
