package nl.saxion.joep.twetter.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by joepv on 13.mei.2016.
 */
public class Tweet {
    private JSONObject entities;
    private TweetOwner owner;

    private ArrayList<UrlTweet> url = new ArrayList<>();
    private ArrayList<HashTags> hashTags = new ArrayList<>();
    private ArrayList<UserMentions> userMentionses = new ArrayList<>();
    private ArrayList<Media> medias = new ArrayList<>();
    private boolean isLikedByMe = false;
    private boolean isRetweetedByMe = false;

    public boolean isLikedByMe() {
        return isLikedByMe;
    }

    public void setLikedByMe(boolean likedByMe) {
        isLikedByMe = likedByMe;
    }

    public boolean isRetweetedByMe() {
        return isRetweetedByMe;
    }

    public void setRetweetedByMe(boolean retweetedByMe) {
        isRetweetedByMe = retweetedByMe;
    }

    private long id;
    private String create;
    private String actualTweetString;

    public String getActualTweetString() {
        return actualTweetString;
    }

    /**
     * Default constructor to create a tweet. Converts JSONObject to Tweet object.
     * @param tweet the tweet to convert to object
     */
    public Tweet(JSONObject tweet) {
        try {
            entities = tweet.getJSONObject("entities");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            create = tweet.getJSONObject("user").getString("created_at");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String profileImage = tweet.getJSONObject("user").getString("profile_image_url");
            String name = tweet.getJSONObject("user").getString("name");
            String screen_name = tweet.getJSONObject("user").getString("screen_name");

            owner = new TweetOwner(profileImage, name, screen_name);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray urlJson = entities.getJSONArray("urls");
            for (int i = 0; i < urlJson.length(); i++) {
                url.add(new UrlTweet(urlJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.i("Tweet_Creation", "No urls present.");
            e.printStackTrace();
        }

        try {
            JSONArray hashTagsJson = entities.getJSONArray("hashtags");
            for (int i = 0; i < hashTagsJson.length(); i++) {
                hashTags.add(new HashTags(hashTagsJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.i("Tweet_Creation", "No hashtags present.");
            e.printStackTrace();
        }

        try {
            JSONArray userMentionJson = entities.getJSONArray("user_mentions");

            for (int i = 0; i < userMentionJson.length(); i++) {
                userMentionses.add(new UserMentions(userMentionJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray mediaJson = tweet.getJSONArray("media");
            for (int i = 0; i < mediaJson.length(); i++) {
                url.add(new UrlTweet(mediaJson.getJSONObject(i)));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            id = tweet.getLong("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            actualTweetString = tweet.getString("text");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("testTag", "cant get tweet text :c");
        }
        try {
            isRetweetedByMe = tweet.getBoolean("retweeted");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            isLikedByMe = tweet.getBoolean("favorited");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    /**
     * for testing purposes
     * @return
     */
    @Override
    public String toString() {
        return actualTweetString;
    }


    public long getId() {
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
