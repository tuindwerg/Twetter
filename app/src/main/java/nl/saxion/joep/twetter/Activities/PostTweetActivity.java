package nl.saxion.joep.twetter.Activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;

public class PostTweetActivity extends AppCompatActivity {
    private Button closeButton, postButton;
    private EditText et_tweet;
    private int charsLeft = 140;
    private TextView charCount_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_post_tweet);
        setFinishOnTouchOutside(false);


        charCount_TextView = (TextView) findViewById(R.id.tv_charcount);
        et_tweet = (EditText) findViewById(R.id.et_tweet);
        et_tweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                charsLeft = 140 - et_tweet.getText().toString().length();
                charCount_TextView.setText(charsLeft + "");

            }
        });

        closeButton = (Button) findViewById(R.id.bttn_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_tweet.getText().toString().contentEquals("")) {

                    launchAlertDialog();

                } else {
                    finish();
                }
            }
        });

        postButton = (Button) findViewById(R.id.bttn_send);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO rewrite this so charsLeft variable makes sense
                if (charsLeft > 0) {

                    if (charsLeft <= 140) {
                        //TODO implement post tweet async task

                        PostTweetTask task = new PostTweetTask();
                        task.execute(et_tweet.getText().toString() + "");
                        finish();


                    } else {

                        new AlertDialog.Builder(PostTweetActivity.this)
                                .setTitle("Alert")
                                .setMessage("Please make sure your Tweet does not exceed 140 characters.")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }

                }
                return;
            }
        });


    }


    private void launchAlertDialog() {
        new AlertDialog.Builder(PostTweetActivity.this).setTitle("Alert")
                .setMessage("Are you sure you want to cancel this tweet?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    class PostTweetTask extends AsyncTask<String, Void, Boolean> {
        String responseString = "";

        @Override
        protected Boolean doInBackground(String... params) {

            OAuthRequest request =
                    new OAuthRequest(Verb.POST, "https://api.twitter.com/1.1/statuses/update.json" , TwetterModel.getAuthService());

            request.addParameter("status" , params[0]);

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
                Toast.makeText(PostTweetActivity.this, "success" , Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(PostTweetActivity.this, "not success" , Toast.LENGTH_SHORT).show();
            }
        }
    }

}
