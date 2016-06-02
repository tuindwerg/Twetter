package nl.saxion.joep.twetter.Model.ASync;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class OAuthAsyncTask2 extends AsyncTask<String, Void, Void> {
    private static final String API_KEY = "tvb7d9ZTsDNaLwlkEHrGrjiyD";
    private static final String API_SECRET = "aILd0hy5piSZs9p4QM51V98vZeyqnF3qK4g2es8puxeD2lIbGQ";
    private static final String CHARSET_UTF_8 = "UTF-8";


    @Override
    protected Void doInBackground(String... params) {

        try {
            getBearer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        OutputStream os = null;
//        InputStream is = null; input stream niet nodig (alleen ophalen van bearer via outputstream)
        return null;
    }

    private void getBearer() throws IOException, JSONException {
        URL url = new URL("https://api.twitter.com/oauth2/token");

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
//        httpURLConnection.setConnectTimeout(10000);
//        httpURLConnection.setReadTimeout(10000);

        String authString = URLEncoder.encode(API_KEY, CHARSET_UTF_8) + ":" + URLEncoder.encode(API_SECRET, CHARSET_UTF_8);

        String authStringBase64 = Base64.encodeToString(authString.getBytes(CHARSET_UTF_8), Base64.NO_WRAP);

        httpURLConnection.setRequestProperty("Authorization", "Basic " + authStringBase64);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        httpURLConnection.setDoOutput(true);
//        httpURLConnection.setDoInput(true);  input niet nodig in dit geval

        byte[] body = "grant_type=client_credentials".getBytes(CHARSET_UTF_8);

        httpURLConnection.setFixedLengthStreamingMode(body.length);
        OutputStream os = new BufferedOutputStream(httpURLConnection.getOutputStream());

        os.write(body);
        os.close(); // hier kan outputstream al worden geclosed


        int responsecode = httpURLConnection.getResponseCode();
        Log.d("responsecode ="," " + responsecode);


        if(responsecode == 200){
            Log.d("responsecode ="," " + responsecode);
            //parse

        }


//        if (httpURLConnection.getInputStream() == null) {
//
//            is = new BufferedInputStream(httpURLConnection.getInputStream());
//
//            String response = IOUtils.toString(is, CHARSET_UTF_8);
//
//            os.close();
//            return response;
//        }
        //???

    }

}






    /*
    @Override
    protected void onPostExecute(String result) {
        if (!result.isEmpty()) {
            try {

                JSONObject authObj = new JSONObject(result);
                MainActivity.getInstance().setBearertoken(authObj.getString("access_token"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
    */
