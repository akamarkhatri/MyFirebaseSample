package com.qaitamarkhatri.myfirebasesample;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "DEEPLink";
    private static final String FIREBASE_TAG = "FireBase";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAnalytics mFirebaseAnalytics;
    private TextSwitcher textSwitcher;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        checkIsAppOpenFromDynamicLink();*/
        textSwitcher= (TextSwitcher) findViewById(R.id.textSwitcher);

        TranslateAnimation translateInAnimation = new TranslateAnimation(0, 0, 100,0);
        translateInAnimation.setDuration(500);
        TranslateAnimation translateOutAnimation = new TranslateAnimation(0, 0, 0, -100);
        translateOutAnimation.setDuration(500);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView switcherTextView = new TextView(getApplicationContext());
                switcherTextView.setTextSize(24);
//                switcherTextView.setTextColor(Color.RED);
                switcherTextView.setText("Click The Button");
//                switcherTextView.setShadowLayer(6, 6, 6, Color.BLACK);
                return switcherTextView;
            }
        });
        /*Animation translateInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        Animation animationIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);*/
        textSwitcher.setInAnimation(translateInAnimation);
        textSwitcher.setOutAnimation(translateOutAnimation);
        textSwitcher.setCurrentText(""+count);
//        textSwitcher.
    }

    public void onClick(View view) {
        ++count;
        textSwitcher.setText(""+count);
//        textSwitcher.
//        textSwitcher.setText(""+(++count));
//        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
       /* initFireBaseRemoteConfigAndFetchValue();
        Bundle params = new Bundle();
        params.putString("image_name", "my custom image");
        params.putString("full_text", "my custom full text");
        mFirebaseAnalytics.logEvent("share_image", params);*/
    }
    private void checkIsAppOpenFromDynamicLink() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(AppInvite.API)
                .build();
        boolean autoLaunchDeepLink = false;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(@NonNull AppInviteInvitationResult result) {
                                if (result.getStatus().isSuccess()) {
                                    // Extract deep link from Intent
                                    Intent intent = result.getInvitationIntent();
                                    String deepLink = AppInviteReferral.getDeepLink(intent);

                                    // Handle the deep link. For example, open the linked
                                    // content, or apply promotional credit to the user's
                                    // account.
                                    Log.d(TAG, "getInvitation: deep link found. with following link :\n"+deepLink);
                                    // ...
                                } else {
                                    Log.d(TAG, "getInvitation: no deep link found.");
                                }
                            }
                        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

    public void initFireBaseRemoteConfigAndFetchValue() {
//        DBApplication.getInstance().setUserPropertyFor("");
//        FirebaseApp.initializeApp(this);
      /*  if (FirebaseApp.getApps(this).isEmpty())
        {
            DBUtility.notifyUser(DBConstant.FIREBASE_TAG,"isempty");
            Toast.makeText(this,"app not initialize ",Toast.LENGTH_SHORT).show();
            return;
        }*/
//        FirebaseAnalytics.getInstance(this).setUserProperty("MyCustomOEM","True");
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        long cacheExpiration=0;
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifyUser(FIREBASE_TAG,"Fetch Succeeded");

                            // Once the config is successfully fetched it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
//                            Toast.makeText(getApplicationContext(), "Fetch Failed",
//                                    Toast.LENGTH_SHORT).show();
                            notifyUser(FIREBASE_TAG,"Fetch Failed");
                        }
                        notifyUser(FIREBASE_TAG,"ATF -"+mFirebaseRemoteConfig.getString("my_custom_oem_user"));

                    }
                });
    }

    private void notifyUser(String tag, String msg) {
        Log.d(tag,msg);
    }
}

