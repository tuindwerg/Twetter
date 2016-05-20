package nl.saxion.joep.twetter.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by joepv on 13.mei.2016.
 */
public class Tweet {
    private JSONObject inputJSONObject;

    private TweetOwner owner;

    private ArrayList<UrlTweet> url = new ArrayList<>();
    private ArrayList<HashTags> hashTags = new ArrayList<>();

    private String rawText;

    public Tweet(JSONObject tweet) {
        inputJSONObject=tweet;
        inputJSONObject.getString()




    }

    // dit is de methode om een url toe te voegen voor elk url wat word toegevroeg in een bericht.
    private void addUrl(){
    //    url.add();
    }

    private void addHashTags(){
    //        hashTags.add();
    }


}
