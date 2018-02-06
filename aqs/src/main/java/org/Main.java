package org;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

/**
 * Created by shsun on 1/29/18.
 */
public class Main {
    public static void main(String[] args) {

        List<NameValuePair> param = new ArrayList<NameValuePair>();

        String URL = "https://dllgps.qiatuchina.com//api/onlineMall/login";
        String result = HttpsConnectionUtils.request(URL, param, "userId=123456");
        System.out.println("result-->" + result);

    }

}
