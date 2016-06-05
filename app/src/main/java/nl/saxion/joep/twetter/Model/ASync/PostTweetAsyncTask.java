package nl.saxion.joep.twetter.Model.ASync;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import nl.saxion.joep.twetter.Model.TwetterModel;

/**
 * Created by joepv on 05.jun.2016.
 */

public class PostTweetAsyncTask extends AsyncTask<String, Void, Boolean> {
    private static final String CHARSET_UTF_8 = "UTF-8";

    public PostTweetAsyncTask() {
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String response;
        InputStream is = null;
        try {
            String text = URLEncoder.encode(params[0], CHARSET_UTF_8);


            URL url = new URL("https://api.twitter.com/1.1/statuses/update.json?status=" + text);





            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Authorization", "Bearer "  + TwetterModel.getInstance().getBearertoken());


            //TODO add proper user authorization


            int responseCode = httpURLConnection.getResponseCode();






            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
    }
}
