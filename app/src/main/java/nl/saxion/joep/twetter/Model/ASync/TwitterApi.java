package nl.saxion.joep.twetter.Model.ASync;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

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
        return null;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return null;
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return null;
    }
}
