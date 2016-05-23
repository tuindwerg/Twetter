package nl.saxion.joep.twetter.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dilan on 13-5-2016.
 */
public class UrlTweet {

    private String url; // dit is de expended url
    private String displayUrl;

    private int startIndex;
    private int endIndex;



    public UrlTweet(JSONObject jsonObject) {
        try {
            url = jsonObject.getString("url");

            displayUrl = jsonObject.getString("display_url");

            // indeces is een array met 2 elementen
            JSONArray indeces = jsonObject.getJSONArray("indices");
            startIndex = indeces.getInt(0);
            endIndex = indeces.getInt(1);


        } catch (JSONException e) {

            // betere foutmelding. moet nog gedaan worden.
            e.printStackTrace();
        }
    }



    public String getDisplayUrl() {
        return displayUrl;
    }

    public String getUrl() {
        return url;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}
