package org;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class HttpsConnectionUtils {

    /**
     * post
     * 
     * @param url
     * @param param
     * @param data
     * @return
     */
    static public String request(String url, List<NameValuePair> param, String data) {
        String result = null;
        String requestParam = URLEncodedUtils.format(param, "UTF-8");
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[] { new MyX509TrustManager() }, null);
            URL requestUrl = new URL(url + "?" + requestParam);
            HttpsURLConnection conn = (HttpsURLConnection) requestUrl.openConnection();
            conn.setSSLSocketFactory(sslcontext.getSocketFactory());
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            if (data != null) {
                out.writeBytes(data);
            }
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            int code = conn.getResponseCode();
            if (HttpsURLConnection.HTTP_OK == code) {
                String temp = in.readLine();
                while (temp != null) {
                    if (result != null) {
                        result += temp;
                    } else {
                        result = temp;
                    }
                    temp = in.readLine();
                }
            }
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
