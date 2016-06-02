package nl.saxion.joep.twetter.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import nl.saxion.joep.twetter.Model.ASync.OAuthAsyncTask;
import nl.saxion.joep.twetter.Model.ASync.OAuthAsyncTask2;
import nl.saxion.joep.twetter.Model.JSONParser;
import nl.saxion.joep.twetter.Model.Tweet;
import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;

public class MainActivity extends AppCompatActivity {
    TwetterModel model = TwetterModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checks if device has internet
        if (isNetworkAvailable()){
            Log.e("testTag2","yes, is available");
        }else{
            Log.e("testTag2","no, is NOT available");

        }

        OAuthAsyncTask authenticationTask = new OAuthAsyncTask();
        authenticationTask.execute();

        while (model.getBearertoken() == null){
            Log.e("testTag","still empty");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.e("testTag", "not empty anymore :D:DDD");
        Log.e("testTag", "not empty anymore :D:DDD");
        Log.e("testTag", "not empty anymore :D:DDD");















        try {
            String assetString = JSONParser.readAssetIntoString(this, "tweets.json");
            JSONObject assetOBJ = new JSONObject(assetString);
 
            JSONArray statuses = assetOBJ.getJSONArray("statuses");

            for (int i = 0; 1 < statuses.length(); i++) {
                JSONObject newTweetJsonObject = statuses.getJSONObject(i);

                model.addTweet(new Tweet(newTweetJsonObject));

            }


        } catch (IOException ioE) {
            Log.e("ERROR", "IOException: read asset into string");
        } catch (JSONException jsoE) {
            Log.e("ERROR", "JSONException @ mainactivity");
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
