package nl.saxion.joep.twetter.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import nl.saxion.joep.twetter.Model.JSONParser;
import nl.saxion.joep.twetter.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            JSONParser.readAssetIntoString(this,"tweets.json");
        }catch (IOException ioE){
            Log.e("ERROR","IOException: read asset into string");
        }


    }
}
