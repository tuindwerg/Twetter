package nl.saxion.joep.twetter.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import nl.saxion.joep.twetter.Model.ASync.OAuthAsyncTask;
import nl.saxion.joep.twetter.Model.JSONParser;
import nl.saxion.joep.twetter.Model.Tweet;
import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;
import nl.saxion.joep.twetter.View.TweetListAdapter;

public class MainActivity extends AppCompatActivity {
    private TwetterModel model = TwetterModel.getInstance();
    private ListView listView;
    private TweetListAdapter tweetListAdapter;
    private EditText searchBar;
    private Button searchButton;

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checks if device has internet
        if (isNetworkAvailable()) {
            Log.e("testTag2", "yes, is available");
        } else {
            Log.e("testTag2", "no, is NOT available");

        }

        OAuthAsyncTask authenticationTask = new OAuthAsyncTask();
        authenticationTask.execute();


        if (model.getBearertoken() == null) {
            try {
                throw new Exception("bearer token is null");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("testTag", "");
            }
        }

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
        } finally {
            listView = (ListView) findViewById(R.id.tweetlistView);
            tweetListAdapter = new TweetListAdapter(this, model.getTweetArrayList());
            searchBar = (EditText)findViewById(R.id.et_searchbox);
            searchButton = (Button)findViewById(R.id.bttn_search);
            listView.setAdapter(tweetListAdapter);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                    searchIntent.putExtra("searchQuery",""+searchBar.getText());
                    startActivity(searchIntent);
                }
            });

        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
