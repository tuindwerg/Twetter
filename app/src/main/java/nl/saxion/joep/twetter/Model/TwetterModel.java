package nl.saxion.joep.twetter.Model;

import java.util.ArrayList;

/**
 * Created by joepv on 30.apr.2016.
 */
public class TwetterModel {
    private static TwetterModel ourInstance = new TwetterModel();
    private ArrayList<Tweet> tweetArrayList;
    private String bearertoken;

    public static TwetterModel getInstance() {
        return ourInstance;
    }

    private TwetterModel() {
        tweetArrayList = new ArrayList<>();
    }

    public void addTweet(Tweet tweet){
        tweetArrayList.add(tweet);
    }

    public ArrayList<Tweet> getTweetArrayList() {
        return tweetArrayList;
    }

    /**
     * TODO
     */
    public void getTweet(){
        // return tweet
    }

    public String getBearertoken() {
        return bearertoken;
    }

    public void setBearertoken(String bearertoken) {
        this.bearertoken = bearertoken;
    }
}
