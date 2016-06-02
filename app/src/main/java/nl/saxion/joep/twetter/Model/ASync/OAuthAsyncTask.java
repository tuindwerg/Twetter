package nl.saxion.joep.twetter.Model.ASync;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

import nl.saxion.joep.twetter.Model.TwetterModel;

/**
 * Created by joepv on 27.mei.2016.
 */

public class OAuthAsyncTask extends AsyncTask<String, Void, String> {
    private static final String API_KEY = "tvb7d9ZTsDNaLwlkEHrGrjiyD";
    private static final String API_SECRET = "aILd0hy5piSZs9p4QM51V98vZeyqnF3qK4g2es8puxeD2lIbGQ";
    private static final String CHARSET_UTF_8 = "UTF-8";

    private final String LOGTAG = "testTag3";

    @Override
    protected String doInBackground(String... params) {
        OutputStream os = null;
        InputStream is = null;
        HttpURLConnection httpURLConnection = null;
        try {
            byte[] body;


            URL url = new URL("https://api.twitter.com/oauth2/token");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            Log.e(LOGTAG, "test1, opening HttpURLConnection");
            httpURLConnection.setRequestMethod("POST");
            Log.e(LOGTAG, "test2, setting request method");
            //httpURLConnection.setConnectTimeout(10000);
           // httpURLConnection.setReadTimeout(10000);

            String authString = URLEncoder.encode(API_KEY, CHARSET_UTF_8) + ":" + URLEncoder.encode(API_SECRET, CHARSET_UTF_8);
            Log.e(LOGTAG, "test3, encoding keys to authString");
            String authStringBase64 = Base64.encodeToString(authString.getBytes(CHARSET_UTF_8), Base64.NO_WRAP);
            Log.e(LOGTAG, "test4, encoding authString to Base64");

            httpURLConnection.setRequestProperty("Authorization", "Basic " + authStringBase64);
            Log.e(LOGTAG, "test5, setting request property [authStringBase64]");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            Log.e(LOGTAG, "test6, setting request property [Content-Type]");

            httpURLConnection.setDoOutput(true);
            //httpURLConnection.setDoInput(true);
            Log.e(LOGTAG, "test7, setting doOutput>true AND setDoInput > true");


            body = "grant_type=client_credentials".getBytes(CHARSET_UTF_8);

            httpURLConnection.setFixedLengthStreamingMode(body.length);
            Log.e(LOGTAG, "test8, setting fixed lenght stream mode");
            os = new BufferedOutputStream(httpURLConnection.getOutputStream());
            Log.e(LOGTAG, "test9, initializing outputstream with httpURLConnection.getOutputStream");

            os.write(body);
            os.close();
            Log.e(LOGTAG, "test10, writing 'body' to outputstream");
            int testInt = httpURLConnection.getResponseCode();
            Log.e(LOGTAG,"responsecode = " + testInt);





            Log.e(LOGTAG,"responsecode = " + httpURLConnection.getResponseCode());

            Log.e(LOGTAG, "test11, closing outputstream");





            if (httpURLConnection.getInputStream() == null) {
                Log.e(LOGTAG, "test12a, FAILED :inputstream is null ");
            } else {
                Log.e(LOGTAG, "test12a, PASSED : inputstream NOT null");
            }
            is = new BufferedInputStream(httpURLConnection.getInputStream());
            Log.e(LOGTAG, "test12b, initializing inputstream");

            String response = IOUtils.toString(is, CHARSET_UTF_8);
            Log.e(LOGTAG, "changing inputstream into String");

            Log.e(LOGTAG, "response = " + response);
            os.close();
            return response;

//        try {
//            // Prepare request
//            Log.e("testTag3", "one");
//            URL url = new URL("https://api.twitter.com/oauth2/token");
//            HttpURLConnection conn =
//                    (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            // Encode API key and secret
//            Log.e("testTag3", "two");
//
//
//            String authString =
//                    URLEncoder.encode(API_KEY, CHARSET_UTF_8) + ":" +
//                            URLEncoder.encode(API_SECRET, CHARSET_UTF_8);
//            // Apply Base64 encoding on the encoded string
//            Log.e("testTag3", "three");
//
//            String authStringBase64 = Base64.encodeToString(
//                    authString.getBytes(CHARSET_UTF_8),
//                    Base64.NO_WRAP);
//
//
//            // Set headers
//            Log.e("testTag3", "four");
//
//            conn.setRequestProperty("Authorization",
//                    "Basic " + authStringBase64);
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//            // Set body
//            Log.e("testTag3", "five");
//
//            byte[] body =
//                    "grant_type=client_credentials".getBytes("UTF-8");
//
//            Log.e("testTag3", "six");
//            conn.setFixedLengthStreamingMode(body.length);
//
//            Log.e("testTag3", "seven");
//
//            os =
//                    new BufferedOutputStream(conn.getOutputStream());
//            os.write(body);
//            Log.e("testTag3", "eight");
//
//
//            conn.connect();
//            String result;
//
//            os.close();
//            Log.e("testTag3", "nine");
//
//            Log.e("testTag3", "responsecode "+conn.getResponseCode());
//
//
//            if (conn.getResponseCode() == conn.HTTP_OK) {
//                InputStream is = new BufferedInputStream(conn.getInputStream());
//                String test = IOUtils.toString(is, "UTF-8");
//
//                Log.e("testTag2", "http = OK");
//                result = test;
//                return result;
//            } else {
//                Log.e("testTag2", "http = DEFFO NOT OK");
//


        } catch (MalformedURLException e) {
            Log.e(LOGTAG,"MALFORMEDURL EXCEPTION!!!");
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e(LOGTAG,"PROTOCOL EXCEPTION!!!    + " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(LOGTAG,"IO EXCEPTION!!!");
            e.printStackTrace();
        } finally {

            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            httpURLConnection.disconnect();

        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.e(LOGTAG,"onPostExecute: received result equals to= " + result);
        if (!result.isEmpty()) {
            try {

                JSONObject authObj = new JSONObject(result);
                TwetterModel.getInstance().setBearertoken(authObj.getString("access_token"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }



}
