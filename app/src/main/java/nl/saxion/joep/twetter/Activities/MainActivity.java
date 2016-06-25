package nl.saxion.joep.twetter.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nl.saxion.joep.twetter.Model.ASync.OAuthAsyncTask;
import nl.saxion.joep.twetter.Model.Tweet;
import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;
import nl.saxion.joep.twetter.View.TweetListAdapter;

import static nl.saxion.joep.twetter.Model.TwetterModel.getAuthService;

public class MainActivity extends AppCompatActivity {
    private TwetterModel model = TwetterModel.getInstance();
    private ListView listView;
    private TweetListAdapter tweetListAdapter;
    private EditText searchBar;
    private Button searchButton;
    private FloatingActionButton floatingActionButton;


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


            GetHomeTimeLineTask getHomeTimeLineTask = new GetHomeTimeLineTask();
            getHomeTimeLineTask.execute();


        } finally {
            listView = (ListView) findViewById(R.id.tweetlistView);
            tweetListAdapter = new TweetListAdapter(this, model.getTweetArrayList());
            searchBar = (EditText) findViewById(R.id.et_searchbox);
            searchButton = (Button) findViewById(R.id.bttn_search);
            listView.setAdapter(tweetListAdapter);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                    searchIntent.putExtra("searchQuery", "" + searchBar.getText());
                    startActivity(searchIntent);

                    //awesome animation for opening activity
                    overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                }
            });

        }

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PostTweetActivity.class);
                startActivity(i);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.options_refresh) {
            refresh();
            return true;
        } else if (item.getItemId() == R.id.options_home) {
            GetUserTimeLineTask task = new GetUserTimeLineTask();
            task.execute();
        } else if (item.getItemId() == R.id.options_profile) {
            Intent i = new Intent(this, UserProfileActivity.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.options_logout) {
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra("LOGOUT", true);
            startActivity(i);
            finish();
        } else if (item.getItemId() == R.id.options_direct_messages) {
            Intent z = new Intent(this, DirectMessagesActivity.class);
            startActivity(z);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    /**
     * Logs large Strings in logcat which would otherwise overflow
     * @param str the large String to log
     */
    public static void longInfo(String str) {
        if (str.length() > 4000) {
            Log.e("testTag12", str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            Log.e("testTag12", str);
    }

    private void refresh() {
        GetHomeTimeLineTask task = new GetHomeTimeLineTask();
        task.execute();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class GetUserTimeLineTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            Log.e("testTag4", "get user timeline : 1");

            OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/statuses/user_timeline.json", getAuthService());
            getAuthService().signRequest(model.getAccessToken(), request);
            Log.e("testTag4", "get user timeline : 2");

            Response response = request.send();
            Log.e("testTag4", "get user timeline : 3");

            if (response.isSuccessful()) {
                Log.e("testTag4", "get user timeline : 4");

                String body = response.getBody();


                try {
                    Log.e("testTag4", "response = " + body);
                    //JSONObject assetOBJ = new JSONObject(body);

                    //JSONArray statuses = assetOBJ.getJSONArray("statuses");
                    JSONArray statuses = new JSONArray(body);
                    for (int i = 0; 1 < statuses.length(); i++) {
                        JSONObject newTweetJsonObject = statuses.getJSONObject(i);

                        model.addUserTimeLineTweet(new Tweet(newTweetJsonObject));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //tweetListAdapter.notifyDataSetChanged();
            //Snackbar.make(findViewById(R.id.tweetlistView),"Refreshed",Snackbar.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, UserTimelineActivity.class);
            startActivity(i);

        }
    }

    public class GetHomeTimeLineTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

            OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/statuses/home_timeline.json", getAuthService());

            getAuthService().signRequest(model.getAccessToken(), request);

            Response response = request.send();

            if (response.isSuccessful()) {

                String body = response.getBody();
                //longInfo(body);

                try {

                    JSONArray statuses = new JSONArray(body);
                    for (int i = 0; 1 < statuses.length(); i++) {
                        JSONObject newTweetJsonObject = statuses.getJSONObject(i);

                        model.addTweet(new Tweet(newTweetJsonObject));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {
                tweetListAdapter.notifyDataSetChanged();
                Snackbar.make(findViewById(R.id.tweetlistView), "Refreshed", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(findViewById(R.id.tweetlistView), "Not refreshed: Check internet connection", Snackbar.LENGTH_SHORT).show();
            }


        }
    }
}
