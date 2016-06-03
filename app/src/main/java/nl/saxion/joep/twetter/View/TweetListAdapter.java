package nl.saxion.joep.twetter.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nl.saxion.joep.twetter.Model.Tweet;
import nl.saxion.joep.twetter.R;

/**
 * Created by joepv on 13.mei.2016.
 */
public class TweetListAdapter extends ArrayAdapter<Tweet> {

    public TweetListAdapter(Context context, List<Tweet> objects) {
        super(context, -1, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_list_item, parent, false);
        }

        TextView name, screenname, tweetText;
        ImageView profilePicture;

        name = (TextView) convertView.findViewById(R.id.tv_display_name);
        screenname = (TextView) convertView.findViewById(R.id.tv_screen_name);
        tweetText = (TextView) convertView.findViewById(R.id.tv_tweet_text);
        profilePicture = (ImageView) convertView.findViewById(R.id.iv_profile_picture);

        name.setText(getItem(.));
        return convertView;
    }
}
