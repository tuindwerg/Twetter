package nl.saxion.joep.twetter.Model;

import java.util.ArrayList;

/**
 * Created by joepv on 30.apr.2016.
 */
public class TwetterModel {
    private static TwetterModel ourInstance = new TwetterModel();
    private ArrayList<Tweet> tweetArrayList;
    private ArrayList<Tweet> searchTweetArrayList;
    private String bearertoken;

    private final String API_KEY = "tvb7d9ZTsDNaLwlkEHrGrjiyD";
    private final String API_SECRET = "aILd0hy5piSZs9p4QM51V98vZeyqnF3qK4g2es8puxeD2lIbGQ";

    public String getApiKey() {
        return API_KEY;
    }

    public String getApiSecret() {
        return API_SECRET;
    }

    public static TwetterModel getInstance() {
        return ourInstance;
    }

    private TwetterModel() {
        tweetArrayList = new ArrayList<>();
        searchTweetArrayList = new ArrayList<>();
    }

    public void addTweet(Tweet tweet) {
        tweetArrayList.add(tweet);
    }

    public void addSearchTweet(Tweet tweet) {
        searchTweetArrayList.add(tweet);
    }

    public ArrayList<Tweet> getTweetArrayList() {
        return tweetArrayList;
    }

    public ArrayList<Tweet> getSearchTweetArrayList() {
        return searchTweetArrayList;
    }


    /**
     * TODO
     */
    public void getTweet() {
        // return tweet
    }

    public String getBearertoken() {
        return bearertoken;
    }

    public void setBearertoken(String bearertoken) {
        this.bearertoken = bearertoken;
    }
}
