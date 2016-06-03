package nl.saxion.joep.twetter.Model.ASync;

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
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);

            String authString = URLEncoder.encode(API_KEY, CHARSET_UTF_8) + ":" + URLEncoder.encode(API_SECRET, CHARSET_UTF_8);

            String authStringBase64 = Base64.encodeToString(authString.getBytes(CHARSET_UTF_8), Base64.NO_WRAP);


            httpURLConnection.setRequestProperty("Authorization", "Basic " + authStringBase64);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");


            httpURLConnection.setDoOutput(true);


            body = "grant_type=client_credentials".getBytes(CHARSET_UTF_8);

            httpURLConnection.setFixedLengthStreamingMode(body.length);

            os = new BufferedOutputStream(httpURLConnection.getOutputStream());

            os.write(body);
            os.close();


            Log.e(LOGTAG, "responsecode = " + httpURLConnection.getResponseCode());
            is = new BufferedInputStream(httpURLConnection.getInputStream());

            String response = IOUtils.toString(is, CHARSET_UTF_8);

            Log.e(LOGTAG, "response = " + response);
            os.close();
            return response;


        } catch (MalformedURLException e) {
            Log.e(LOGTAG, "MALFORMEDURL EXCEPTION!!!");
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e(LOGTAG, "PROTOCOL EXCEPTION!!!    + " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(LOGTAG, "IO EXCEPTION!!!");
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
        Log.e(LOGTAG, "onPostExecute: received result equals to= " + result);
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
