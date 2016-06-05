package nl.saxion.joep.twetter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText searchBar;
    private Button searchButton;


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

        searchBar = (EditText)findViewById(R.id.search_et_searchbox);
        searchButton = (Button)findViewById(R.id.search_bttn_search);

        searchBar.setText(searchQuery);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter.notifyDataSetChanged();
                new GetSearchTweetsASyncTask(adapter).execute(searchBar.getText().toString());
            }
        });



    }


    @Override
    protected void onPause() {
        //adapter.notifyDataSetInvalidated();
        //Log.e("testTag5","notified dataset invalidated");
        super.onPause();
    }
}
