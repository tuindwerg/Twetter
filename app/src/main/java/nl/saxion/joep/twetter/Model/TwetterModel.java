package nl.saxion.joep.twetter.Model;

import android.os.AsyncTask;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.util.ArrayList;

import nl.saxion.joep.twetter.Model.ASync.TwitterApi;

/**
 * Created by joepv on 30.apr.2016.
 */
public class TwetterModel {
    private static TwetterModel ourInstance = new TwetterModel();
    private ArrayList<Tweet> tweetArrayList;
    private ArrayList<Tweet> userTimeLineTweetList;
    private ArrayList<Tweet> searchTweetArrayList;
    private String bearertoken;

    private final String API_KEY = "tvb7d9ZTsDNaLwlkEHrGrjiyD";
    private final String API_SECRET = "aILd0hy5piSZs9p4QM51V98vZeyqnF3qK4g2es8puxeD2lIbGQ";
    private final String OAUTH_CALLBACK_URL = "http://www.defaultCallback.com";
    private String consumerKey = "";
    private String consumerSecret = "";
    private String authUrl;
    private OAuth1RequestToken requestToken;

    private ActiveUser user;

    public ActiveUser getUser() {
        return user;
    }

    public void setUser(ActiveUser user) {
        this.user = user;
    }

    public ArrayList<Tweet> getUserTimeLineTweetList() {
        return userTimeLineTweetList;
    }
    public void addUserTimeLineTweet(Tweet tweet) {
        userTimeLineTweetList.add(tweet);
    }

    private OAuth1AccessToken accessToken;

    public OAuth1AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(OAuth1AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public OAuth1RequestToken getRequestToken() {
        return requestToken;
    }

    private static OAuth10aService authService;

    public static OAuth10aService getAuthService() {
        return authService;
    }

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
        userTimeLineTweetList = new ArrayList<>();
        authService = new ServiceBuilder()
                .apiKey(API_KEY)
                .apiSecret(API_SECRET)
                .callback(OAUTH_CALLBACK_URL)
                .build(TwitterApi.getInstance());

        OAuthAsync oAuthAsync = new OAuthAsync();
        oAuthAsync.execute();


    }

    public String getAuthUrl() {
        return authUrl;
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

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getBearertoken() {
        return bearertoken;
    }

    public void setBearertoken(String bearertoken) {
        this.bearertoken = bearertoken;
    }

    class OAuthAsync extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            requestToken = authService.getRequestToken();
            authUrl = authService.getAuthorizationUrl(requestToken);

            return null;
        }
    }

}
