package nl.saxion.joep.twetter.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import nl.saxion.joep.twetter.Model.Tweet;

/**
 * Created by joepv on 13.mei.2016.
 */
public class TweetListAdapter extends ArrayAdapter<Tweet> {

    public TweetListAdapter(Context context, List<Tweet> objects) {
        super(context, -1, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==null){
            //convertView= LayoutInflater.from(getContext()).inflate()
        }





        return convertView;
    }
}
