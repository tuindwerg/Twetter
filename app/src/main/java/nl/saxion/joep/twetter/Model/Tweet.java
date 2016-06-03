package nl.saxion.joep.twetter.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

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
    private String create;


    public Tweet(JSONObject tweet) {

        try {
            create = tweet.getJSONObject("user").getString("created_at");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String profileImage = tweet.getJSONObject("user").getString("profile_image_url");
            String name = tweet.getJSONObject("user").getString("screen_name");
            String screen_name = tweet.getJSONObject("user").getString("name");
            owner = new TweetOwner(profileImage,name,screen_name);


        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public int getId() {
        return id;
    }

    public TweetOwner getOwner() {
        return owner;
    }

    public ArrayList<UrlTweet> getUrl() {
        return url;
    }

    public ArrayList<HashTags> getHashTags() {
        return hashTags;
    }

    public ArrayList<UserMentions> getUserMentionses() {
        return userMentionses;
    }

    public ArrayList<Media> getMedias() {
        return medias;
    }

    public String getCreatedAtString() {
        return create;
    }
}
