package nl.saxion.joep.twetter.Model.ASync;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

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
import nl.saxion.joep.twetter.View.TweetListAdapter;

import static android.R.id.list;

/**
 * Created by joepv on 20.mei.2016.
 */
public class GetSearchTweetsASyncTask extends  AsyncTask<String, Void, String> {
    private static final String CHARSET_UTF_8 = "UTF-8";
    private TweetListAdapter adapter;

    public GetSearchTweetsASyncTask(TweetListAdapter adapter) {
        super();
        this.adapter=adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

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
                Log.e("testTag5","onbackground response : " + response);
                return response;



            }else{
                Log.e("ERROR","SEARCH ERROR :Not 200 response, therefor error. Error code = " + responseCode);
            }
            httpURLConnection.disconnect();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("testTag5", "UnsupportedEncodingException");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("testTag5", "MalformedURLException");

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("testTag5", "IOException");

        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            Log.e("testTag5","onPostExecute : result = " + result);
            if (result == null){
                return;
            }
            JSONObject responseObject = new JSONObject(result);
            JSONArray statuses = responseObject.getJSONArray("statuses");
            for (int i = 0; i < statuses.length(); i++) {
                JSONObject newTweetJsonObject = statuses.getJSONObject(i);
                TwetterModel.getInstance().addSearchTweet(new Tweet(newTweetJsonObject));
            }

            adapter.notifyDataSetInvalidated();




        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.getMessage());
        }


    }
}














