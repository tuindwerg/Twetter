package nl.saxion.joep.twetter.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import nl.saxion.joep.twetter.Model.TwetterModel;
import nl.saxion.joep.twetter.R;

import static nl.saxion.joep.twetter.Model.TwetterModel.getAuthService;

/**
 * Created by joepv on 10.jun.2016.
 */

public class LoginActivity extends AppCompatActivity {
    private WebView webView;
    private TwetterModel model = TwetterModel.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Log.e("testTag", model.getAuthUrl());
//        webView = (WebView) findViewById(R.id.wv_login);
//        webView.loadUrl(model.getAuthUrl());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (model.getAuthUrl() == null) {
                    //doNothing
                } else {
                    Log.e("testTag", model.getAuthUrl());
                    webView = (WebView) findViewById(R.id.wv_login);
                    webView.loadUrl(model.getAuthUrl());

                    webView.setWebViewClient(new WebViewClient() {

                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            Log.e("testTag10", " url = " + url);

                            if (url.startsWith("http://www.defaultcallback.com")) {
                                //authorization granted
                                Uri uri = Uri.parse(url);
                                String verifierKey = uri.getQueryParameter("oauth_verifier");
                                //model.setAccessToken(TwetterModel.getAuthService().getAccessToken(model.getRequestToken(),verifierKey));
                                VerifierTokenTask task = new VerifierTokenTask(verifierKey);
                                task.execute();


                            }


                            return true;
                        }
                    });

                    handler.removeCallbacksAndMessages(null);
                }
            }
        }, 1000);

    }


    public class VerifierTokenTask extends AsyncTask<String, Void, Void> {
        String param;

        public VerifierTokenTask(String param) {
            this.param = param;
        }

        @Override
        protected Void doInBackground(String... params) {

            model.setAccessToken(TwetterModel.getAuthService().getAccessToken(model.getRequestToken(), param));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("testTag10", "" + model.getAccessToken());
            LoginTask loginTask = new LoginTask();
            loginTask.execute();
        }
    }


    public class LoginTask extends AsyncTask<Void, Boolean, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json", getAuthService());
            getAuthService().signRequest(model.getAccessToken(), request);


            Response response = request.send();

            if (response.isSuccessful()) {
                String resp = response.getBody();
                Log.e("testTag10","returned true");


                return true;
            }

            Log.e("testTag10","returned false");
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);

                startActivity(i);
                finish();
            }

        }
    }
}
