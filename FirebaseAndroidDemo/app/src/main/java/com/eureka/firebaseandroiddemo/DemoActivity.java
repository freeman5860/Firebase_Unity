package com.eureka.firebaseandroiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchException;

public class DemoActivity extends Activity {

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private Button mTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mTestButton = (Button)findViewById(R.id.test_btn);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setDefaults(R.xml.default_cfg);
        mFirebaseRemoteConfig.fetch().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mFirebaseRemoteConfig.activateFetched();
                boolean retValue = mFirebaseRemoteConfig.getBoolean("enable_button");
                mTestButton.setEnabled(retValue);

                Log.e("hjy", "fetch");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseRemoteConfigFetchException) {
                    FirebaseRemoteConfigFetchException fe = (FirebaseRemoteConfigFetchException)e;
                    Log.e("hjy", "fetch failed " + fe.getMessage());
                }
            }
        });
    }
}
