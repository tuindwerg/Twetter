package nl.saxion.joep.twetter.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dilan on 13-5-2016.
 */
public class HashTags {

    private String text;

    private int startIndex;
    private int endIndex;


    public HashTags(JSONObject jsonObject) {
        try {
            text = jsonObject.getString("text");

            // indeces is een array met 2 elementen
            JSONArray indeces = jsonObject.getJSONArray("indices");
            startIndex = indeces.getInt(0);
            endIndex = indeces.getInt(1);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }
}
