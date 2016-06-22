package nl.saxion.joep.twetter.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;

/**
 * Created by joepv on 22.jun.2016.
 */

public class UserProfileActivity extends AppCompatActivity {
    TwetterModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        model = TwetterModel.getInstance();

        Log.e("testTag13", "active user createdat = " + model.getUser().getCreatedAt());
    }
}
