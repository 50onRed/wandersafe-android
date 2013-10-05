package com.fiftyonred.wandersafe;


import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class Utils {

    public static int getJSONFromURL(String url) throws ClientProtocolException, IOException {
        //JSONObject responseJSON = null;
        int level;
        InputStream in = null;

        // Hack to avoid async task implementation
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            in = httpResponse.getEntity().getContent();

            //If invalid response type, throw exception
            if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new IOException("Unable to get JSON from URL: "+url);
            }

            StringBuilder response = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }

            //Parse the response
            // responseJSON = (JSONObject) new JSONTokener(response.toString()).nextValue();
            level = Integer.parseInt(response.toString());
        }
        finally {
            if(in != null) { in.close(); }
        }

        return level;
    }
}
