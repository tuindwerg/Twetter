package nl.saxion.joep.twetter.Activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nl.saxion.joep.twetter.Model.DirectMessage;
import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;

/**
 * Created by joepv on 24.jun.2016.
 */

public class DirectMessagesActivity extends AppCompatActivity {
    private ListView dmListView;
    private ArrayList<DirectMessage> directMessageArrayList;
    private DMListAdapter adapter;
    private TextView dmCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_messages);
        directMessageArrayList = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        dmListView = (ListView) findViewById(R.id.lv_directmessages);
        adapter = new DMListAdapter(this, directMessageArrayList);
        dmListView.setAdapter(adapter);

         dmCount = (TextView)findViewById(R.id.dm_count);

        RetreiveDirectMessagesTask dmTask = new RetreiveDirectMessagesTask();
        dmTask.execute();


    }

    class DMListAdapter extends ArrayAdapter<DirectMessage> {

        public DMListAdapter(Context context, List<DirectMessage> objects) {
            super(context, -1, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.directmessage_list_item, parent, false);
            }

            TextView createdAt = (TextView) convertView.findViewById(R.id.dm_item_created_at);
            String[] tempCreatedAt = getItem(position).getCreatedAt().split("\\+");
            createdAt.setText(tempCreatedAt[0]);

            TextView sender = (TextView) convertView.findViewById(R.id.dm_item_sender);
            sender.setText(getItem(position).getSender());

            TextView text =(TextView)convertView.findViewById(R.id.dm_item_text);
            text.setText(getItem(position).getText());

            return convertView;

        }
    }

    class RetreiveDirectMessagesTask extends AsyncTask<Void, Void, Boolean> {
        String responseString = "";


        @Override
        protected Boolean doInBackground(Void... params) {

            OAuthRequest request =
                    new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/direct_messages.json", TwetterModel.getAuthService());

            TwetterModel.getAuthService().signRequest(TwetterModel.getInstance().getAccessToken(), request);

            Response response = request.send();

            if (response.isSuccessful()) {
                responseString = response.getBody();
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            Log.e("testTag20", "task is " + success);
            Log.e("testTag20", "responseString is " + responseString);
            if (success) {
                directMessageArrayList.clear();
                try {
                    JSONArray direct_MessagesArray = new JSONArray(responseString);

                    for (int i = 0; i < direct_MessagesArray.length(); i++) {
                        JSONObject directMessage = direct_MessagesArray.getJSONObject(i);
                        String tempCreatedAt = directMessage.getString("created_at");
                        Log.e("testTag20", "tempcreated = " + tempCreatedAt);
                        String tempSender = directMessage.getString("sender_screen_name");
                        DirectMessage directMessage1 = new DirectMessage();
                        directMessage1.setCreatedAt(tempCreatedAt);
                        directMessage1.setSender(tempSender);
                        String tempText = directMessage.getString("text");

                        directMessage1.setText(tempText);

                        directMessageArrayList.add(directMessage1);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("testTag20", "json exception in DM task");
                }

                dmCount.setText("Je hebt " + directMessageArrayList.size() + " Direct Messages.");
                adapter.notifyDataSetChanged();
            }
        }
    }


}
