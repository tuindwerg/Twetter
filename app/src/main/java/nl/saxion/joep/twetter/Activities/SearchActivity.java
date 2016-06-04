package nl.saxion.joep.twetter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import nl.saxion.joep.twetter.Model.ASync.GetSearchTweetsASyncTask;
import nl.saxion.joep.twetter.Model.Tweet;
import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;
import nl.saxion.joep.twetter.View.TweetListAdapter;

/**
 * Created by joepv on 04.jun.2016.
 */

public class SearchActivity extends AppCompatActivity {
    private ListView searchListView;
    private TweetListAdapter adapter;
    private TwetterModel model;
    private TextView searchLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        model = TwetterModel.getInstance();
        adapter = new TweetListAdapter(this, model.getSearchTweetArrayList());
        Intent searchIntent = getIntent();
        String searchQuery = searchIntent.getStringExtra("searchQuery");
        Log.e("testTag5", "intent received searchquery  = " + searchQuery);

        searchListView = (ListView) findViewById(R.id.lv_search);
        GetSearchTweetsASyncTask getSearchTweetsASyncTask = new GetSearchTweetsASyncTask(adapter);
        getSearchTweetsASyncTask.execute(searchQuery);


        searchListView.setAdapter(adapter);

        searchLabel = (TextView) findViewById(R.id.tv_searchlabel);
        searchLabel.setText("Showing search result for : \"" + searchQuery + "\"");


    }
}
