package nl.saxion.joep.twetter.Model.ASync;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.services.SignatureService;

/**
 * Created by Gebruiker on 6-6-2016.
 */
public class TwitterApi extends DefaultApi10a {
    private static TwitterApi ourInstance = new TwitterApi();

    public static TwitterApi getInstance() {
        return ourInstance;
    }

    private TwitterApi() {
    }


    @Override
    public String getRequestTokenEndpoint() {
        return "https://api.twitter.com/oauth/request_token";
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.twitter.com/oauth/access_token";
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return "https://api.twitter.com/oauth/authorize?oauth_token="+requestToken.getToken();
    }


}
