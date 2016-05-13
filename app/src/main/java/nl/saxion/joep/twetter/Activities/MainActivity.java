package nl.saxion.joep.twetter.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

        try{
            String assetString = JSONParser.readAssetIntoString(this,"tweets.json");
            JSONObject assetOBJ = new JSONObject(assetString);

            JSONArray statuses = new JSONArray(assetOBJ.getJSONArray("statuses"));

            for (int i = 0;1<statuses.length(); i++){
                JSONObject newTweetJsonObject = statuses.getJSONObject(i);

                model.addTweet(new Tweet(newTweetJsonObject));

            }



        }catch (IOException ioE){
            Log.e("ERROR","IOException: read asset into string");
        }catch (JSONException jsoE){
            Log.e("ERROR","JSONException @ mainactivity");
        }


    }
}
