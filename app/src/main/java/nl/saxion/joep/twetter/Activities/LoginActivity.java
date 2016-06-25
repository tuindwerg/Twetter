package nl.saxion.joep.twetter.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONException;
import org.json.JSONObject;

import nl.saxion.joep.twetter.Model.ActiveUser;
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
    protected void onRestart() {
        super.onRestart();
        TwetterModel.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent z = getIntent();
        final boolean LOGOUT = z.getBooleanExtra("LOGOUT", false);
        if (LOGOUT) {
            TwetterModel.newInstance();
            Log.e("testTag13", "just logged out");
            Log.e("testTag13", "url = " +model.getAuthUrl());
        }



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (model.getAuthUrl() == null) {
                    handler.postDelayed(this, 1000);

                    //doNothing
                } else {
                    Log.e("testTag13", model.getAuthUrl());
                    webView = (WebView) findViewById(R.id.wv_login);


                    if (LOGOUT) {
//                        webView.clearCache(true);
//                        webView.clearHistory();
//                        clearCookies(LoginActivity.this);
                    }
                    webView.loadUrl(model.getAuthUrl());

                    webView.setWebViewClient(new WebViewClient() {

                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            Log.e("testTag10", " url = " + url);
                            Log.e("testTag13", "url = " + url);
                            if (url.startsWith("http://www.defaultcallback.com")) {
                                //authorization granted
                                Uri uri = Uri.parse(url);
                                String verifierKey = uri.getQueryParameter("oauth_verifier");
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

    /*
       Clears all cookies to prevent webpage from re-using previous webview session, even after user has logged onto a different account
     */
    @SuppressWarnings("deprecation")
    public static void clearCookies(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }


    public class VerifierTokenTask extends AsyncTask<String, Void, Void> {
        private String param;

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
                Log.e("testTag1337", "login body = " + resp);
                try {
                    JSONObject user = new JSONObject(resp);
                    //long id, String id_str, String name, String screenName, String location, String description, int followersCount, int friendsCount, String createdAt, String timeZone

                    long id = user.getLong("id");
                    String id_str = user.getString("id_str");
                    String name = user.getString("name");
                    String screenNameuser = user.getString("screen_name");
                    String location = user.getString("location");
                    String description = user.getString("description");
                    int followersCount = user.getInt("followers_count");
                    int friendsCount = user.getInt("friends_count");
                    String createdAt = user.getString("created_at");
                    String timeZone = user.getString("time_zone");

                    model.setUser(new ActiveUser(id, id_str, name, screenNameuser, location, description, followersCount, friendsCount, createdAt, timeZone));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
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
