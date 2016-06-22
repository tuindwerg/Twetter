package nl.saxion.joep.twetter.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;
import nl.saxion.joep.twetter.View.TweetListAdapter;

/**
 * Created by joepv on 22.jun.2016.
 */

public class UserTimelineActivity extends AppCompatActivity {
    ListView listView;
    TweetListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertimeline);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView= (ListView) findViewById(R.id.usertimeline_listview);
        adapter = new TweetListAdapter(this, TwetterModel.getInstance().getUserTimeLineTweetList());
        listView.setAdapter(adapter);
    }
}
