package nl.saxion.joep.twetter.Model.ASync;

import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
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

    @Override
    protected String doInBackground(String... params) {
        BufferedOutputStream os = null;
        try {
            // Prepare request
            URL url = new URL("https://api.twitter.com/oauth2/token");
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // Encode API key and secret

            String authString =
                    URLEncoder.encode(API_KEY, CHARSET_UTF_8) + ":" +
                            URLEncoder.encode(API_SECRET, CHARSET_UTF_8);
            // Apply Base64 encoding on the encoded string
            String authStringBase64 = Base64.encodeToString(
                    authString.getBytes(CHARSET_UTF_8),
                    Base64.NO_WRAP);


            // Set headers
            conn.setRequestProperty("Authorization",
                    "Basic " + authStringBase64);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            // Set body
            conn.setDoOutput(true);
            byte[] body =
                    "grant_type=client_credentials".getBytes("UTF-8");
            conn.setFixedLengthStreamingMode(body.length);
            os =
                    new BufferedOutputStream(conn.getOutputStream());
            os.write(body);


            conn.connect();
            String result;

            if (conn.getResponseCode() == conn.HTTP_OK) {
                result = os.toString();
                return result;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
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
