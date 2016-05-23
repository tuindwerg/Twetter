package nl.saxion.joep.twetter.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dilan on 13-5-2016.
 */
public class Media {

    private String url;
    private String type;


    private int id;
    private int startIndex;
    private int endIndex;

    public  Media (JSONObject jsonObject){
        try {
            url = jsonObject.getString("media_url");// is dit wel de goede?????????????
            type = jsonObject.getString("type");

            id = jsonObject.getInt("id");
            JSONArray indeces = jsonObject.getJSONArray("indices");
            startIndex = indeces.getInt(0);
            endIndex = indeces.getInt(1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
