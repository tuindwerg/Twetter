package nl.saxion.joep.twetter.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import nl.saxion.joep.twetter.Model.ActiveUser;
import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;

/**
 * Created by joepv on 22.jun.2016.
 */

public class UserProfileActivity extends AppCompatActivity {
    TwetterModel model;
    TextView name, screenname, user_id, location, description, followercount, friendscount, created_at, timezone;
    ActiveUser user = TwetterModel.getUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        model = TwetterModel.getInstance();

        Log.e("testTag13", "active user createdat = " + model.getUser().getCreatedAt());

        name = (TextView) findViewById(R.id.user_tv_name);
        screenname = (TextView) findViewById(R.id.user_tv_screenname);
        user_id = (TextView) findViewById(R.id.user_tv_id);
        location = (TextView) findViewById(R.id.user_tv_location);
        description = (TextView) findViewById(R.id.user_tv_description);
        followercount = (TextView) findViewById(R.id.user_tv_follow_count);
        friendscount = (TextView) findViewById(R.id.user_tv_friends_count);
        created_at = (TextView) findViewById(R.id.user_tv_created_at);
        timezone = (TextView) findViewById(R.id.user_tv_timezone);


        name.setText(user.getName());
        screenname.setText(user.getScreenName());
        user_id.setText(user.getId() + "");

        if (user.getLocation().isEmpty()) {
            location.setText("Unknown location.");
        } else {
            location.setText(user.getLocation());
        }

        if (user.getDescription().isEmpty()) {
            description.setText("No description.");
        } else {
            description.setText(user.getDescription());
        }

        followercount.setText(user.getFollowersCount() + "");
        friendscount.setText(user.getFriendsCount() + "");
        String temp[] = user.getCreatedAt().split("\\+");
        created_at.setText(temp[0]);


        timezone.setText(user.getTimeZone());


    }
}
