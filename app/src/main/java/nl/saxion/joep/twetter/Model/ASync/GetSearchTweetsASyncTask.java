package nl.saxion.joep.twetter.Model.ASync;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import nl.saxion.joep.twetter.Model.Tweet;
import nl.saxion.joep.twetter.Model.TwetterModel;

/**
 * Created by joepv on 20.mei.2016.
 */
public class GetSearchTweetsASyncTask extends  AsyncTask<String, Void, String> {
    private static final String CHARSET_UTF_8 = "UTF-8";

    public GetSearchTweetsASyncTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     */
    @Override
    protected String doInBackground(String... params) {
        String response;
        InputStream is = null;
        try {
            String query = URLEncoder.encode(params[0], CHARSET_UTF_8);

            URL url = new URL("https://api.twitter.com/1.1/search/tweets.json?q=" + query);


            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Authorization", "Bearer "  + TwetterModel.getInstance().getBearertoken());


            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode ==HttpURLConnection.HTTP_OK){
               is = httpURLConnection.getInputStream();
                response = IOUtils.toString(is, "UTF-8");
                is.close();

                return response;



            }else{
                Log.e("ERROR","Not 200 response, therefor error");
            }
            httpURLConnection.disconnect();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject responseObject = new JSONObject(result);
            JSONArray statuses = responseObject.getJSONArray("statuses");
            for (int i = 0; i < statuses.length(); i++) {
                JSONObject newTweetJsonObject = statuses.getJSONObject(i);
                TwetterModel.getInstance().addTweet(new Tweet(newTweetJsonObject));
            }






        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.getMessage());
        }


    }
}














