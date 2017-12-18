package base.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpServletRequestUtils {

    public static Map getRequestMap(HttpServletRequest request) {
        Map properties = request.getParameterMap();
        HashMap returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        String name = "";
        String value = "";

        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (!(valueObj instanceof String[])) {
                value = valueObj.toString();
            } else {
                String[] values = (String[]) ((String[]) valueObj);

                for (int i = 0; i < values.length; ++i) {
                    value = values[i] + ",";
                }

                value = value.substring(0, value.length() - 1);
            }

            try {
                returnMap.put(name, new String(value.getBytes("iso8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException var10) {
                ;
            }
        }

        return returnMap;
    }

    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String getIPEx(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-Server");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-Host");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String getSiteURLEx(HttpServletRequest request) {
        StringBuffer url_buffer = request.getRequestURL();
        String name = request.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(url_buffer.subSequence(0, url_buffer.indexOf(name) + name.length()));
        return url.toString();
    }

    public static String getServiceURL(HttpServletRequest request) {
        StringBuffer url_buffer = request.getRequestURL();
        String name = request.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(url_buffer.subSequence(0, url_buffer.indexOf(name)));
        return url.toString();
    }
}
