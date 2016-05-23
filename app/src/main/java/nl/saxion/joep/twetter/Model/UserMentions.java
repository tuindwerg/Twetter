package nl.saxion.joep.twetter.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dilan on 13-5-2016.
 */
public class UserMentions {

    private String screemName;
    private String name;

    private int id;
    private int startIndex;
    private int endIndex;

    public UserMentions(JSONObject jsonObject) {
        try {
            screemName = jsonObject.getString("screen_name");
            name = jsonObject.getString("name");
            id = jsonObject.getInt("id");

            // indeces is een array met 2 elementen
            JSONArray indeces = jsonObject.getJSONArray("indices");
            startIndex = indeces.getInt(0);
            endIndex = indeces.getInt(1);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public String getScreemName() {
        return screemName;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}
