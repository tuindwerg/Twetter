package nl.saxion.joep.twetter.View;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.squareup.picasso.Picasso;

import java.util.List;

import nl.saxion.joep.twetter.Model.Tweet;
import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;

/**
 * Created by joepv on 13.mei.2016.
 */
public class TweetListAdapter extends ArrayAdapter<Tweet> {


    public TweetListAdapter(Context context, List<Tweet> objects) {
        super(context, -1, objects);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_list_item, parent, false);

        }

        TextView name, screenname, tweetText;
        ImageView profilePicture;

        name = (TextView) convertView.findViewById(R.id.tv_display_name);
        screenname = (TextView) convertView.findViewById(R.id.tv_screen_name);
        tweetText = (TextView) convertView.findViewById(R.id.tv_tweet_text);
        profilePicture = (ImageView) convertView.findViewById(R.id.iv_profile_picture);

        name.setText(getItem(position).getOwner().getName());
        screenname.setText(getItem(position).getOwner().getScreen_name());
        Picasso.with(getContext())
                .load(getItem(position).getOwner().getProfileImage())
                .into(profilePicture);

        tweetText.setText(getItem(position).getActualTweetString());

        ImageButton like = (ImageButton) convertView.findViewById(R.id.likeButton);


        ImageButton retweet = (ImageButton) convertView.findViewById(R.id.retweet_button);

        if (getItem(position).isLikedByMe()) {
            like.setBackgroundResource(R.drawable.emoticon_cool_unlike);
        } else {
            like.setBackgroundResource(R.drawable.emoticon_cool_like);
        }

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getItem(position).isLikedByMe()) {
                    v.setBackgroundResource(R.drawable.emoticon_cool_unlike);
                    getItem(position).setLikedByMe((!getItem(position).isLikedByMe()));

                    LikeTask lTask = new LikeTask(getContext());
                    lTask.execute(getItem(position).getId());

                } else {
                    v.setBackgroundResource(R.drawable.emoticon_cool_like);
                    getItem(position).setLikedByMe((!getItem(position).isLikedByMe()));
                    UnlikeTask lTask = new UnlikeTask(getContext());
                    lTask.execute(getItem(position).getId());
                }

            }
        });

        retweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getItem(position).isRetweetedByMe()) {
                    v.setBackgroundResource(R.drawable.twitter_un_retweet);
                    getItem(position).setRetweetedByMe((!getItem(position).isRetweetedByMe()));

                    RetweetTask retweetTask = new RetweetTask(getContext());
                    retweetTask.execute(getItem(position).getId());

                } else {
                    v.setBackgroundResource(R.drawable.twitter_retweet);
                    getItem(position).setRetweetedByMe((!getItem(position).isRetweetedByMe()));

                    UnRetweetTask unRetweetTask = new UnRetweetTask(getContext());
                    unRetweetTask.execute(getItem(position).getId());
                }
            }
        });


        return convertView;
    }

    public class UnlikeTask extends AsyncTask<Long, Void, Boolean> {
        Context mContext;
        String responseString = "";

        public UnlikeTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Long... params) {

            OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1.1/favorites/destroy.json", TwetterModel.getAuthService());
            request.addParameter("id", params[0].toString());

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

            if (success) {
                Toast.makeText(mContext, "Status unliked", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Status not succesfully unliked", Toast.LENGTH_SHORT).show();

            }

        }
    }

    public class LikeTask extends AsyncTask<Long, Void, Boolean> {
        String responseString = "";
        Context mContext;

        public LikeTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Long... params) {

            OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1.1/favorites/create.json", TwetterModel.getAuthService());
            request.addParameter("id", params[0].toString());

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

            if (success) {
                Toast.makeText(mContext, "Status liked", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Status not succesfully liked", Toast.LENGTH_SHORT).show();

            }

        }
    }


    public class UnRetweetTask extends AsyncTask<Long, Void, Boolean> {
        Context mContext;
        String responseString = "";

        public UnRetweetTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Long... params) {
            String url = "https://api.twitter.com/1.1/statuses/unretweet/";
            url += params[0].toString();
            url += ".json";

            OAuthRequest request = new OAuthRequest(Verb.POST, url, TwetterModel.getAuthService());

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

            if (success) {
                Toast.makeText(mContext, "Status un-Retweeted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Status not succesfully un-Retweeted", Toast.LENGTH_SHORT).show();

            }

        }
    }

    public class RetweetTask extends AsyncTask<Long, Void, Boolean> {
        String responseString = "";
        Context mContext;

        public RetweetTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Long... params) {
            String url = "https://api.twitter.com/1.1/statuses/retweet/";
            url += params[0].toString();
            url += ".json";

            OAuthRequest request = new OAuthRequest(Verb.POST, url, TwetterModel.getAuthService());

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

            if (success) {
                Toast.makeText(mContext, "Status Retweeted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Status not succesfully Retweeted", Toast.LENGTH_SHORT).show();

            }

        }
    }
}
