package nl.saxion.joep.twetter.Model.ASync;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by joepv on 26.mei.2016.
 * http://stackoverflow.com/questions/5776851/load-image-from-url
 * new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
 .execute(MY_URL_STRING);
 */

public class GetImagesASyncTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public GetImagesASyncTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream inputStream = new URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(inputStream);


        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
            e.printStackTrace();
        }

        return mIcon11;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {

        bmImage.setImageBitmap(bitmap);
    }
}
