package nl.saxion.joep.twetter.Model;

import org.json.JSONArray;
import org.json.JSONException;
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
    private ArrayList<UserMentions> userMentionses = new ArrayList<>();
    private ArrayList<Media> medias = new ArrayList<>();


    private int id;

    private String rawText;

    public Tweet(JSONObject tweet) {
        try {
            JSONArray urlJson = tweet.getJSONArray("urls");
            for (int i = 0; i < urlJson.length(); i++) {
                url.add(new UrlTweet(urlJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            // betere foutmelding moet nog gedaan worden.
            e.printStackTrace();
        }

        try {
            JSONArray hashTagsJson = tweet.getJSONArray("hashtags");
            for(int i=0;1<hashTagsJson.length();i++){
                url.add(new UrlTweet(hashTagsJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            // betere foutmelding moet nog gedaan worden.
            e.printStackTrace();
        }

        try {
            JSONArray userMentionJson = tweet.getJSONArray("user_mentions");

            for(int i=0;1<userMentionJson.length();i++){
                url.add(new UrlTweet(userMentionJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray mediaJson = tweet.getJSONArray("media");
            for(int i=0;1<mediaJson.length();i++){
                url.add(new UrlTweet(mediaJson.getJSONObject(i)));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            id = tweet.getInt("id");
        } catch (JSONException e) {
            // betere foutmelding.
            e.printStackTrace();
        }


    }

    // dit is de methode om een url toe te voegen voor elk url wat word toegevroeg in een bericht.
    private void addUrl() {
        //    url.add();
    }

    private void addHashTags() {
        //        hashTags.add();
    }


}
