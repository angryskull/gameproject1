package com.example.gameproject1;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardItem;

/*
public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }
}
*/


public class MainActivity extends Activity {
    private RewardedAd rewardedAd;
    private Button reviveButton;
    private TextView reviveText;
    private String AD_UNIT_ID;
    boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AD_UNIT_ID = getString(R.string.rewarded_ad_id_for_test);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        rewardedAd = new RewardedAd(this, AD_UNIT_ID);

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                Log.d("RewaredeAdLoadCallback","successfully loaded");
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

        reviveText = findViewById(R.id.reviveText);
        reviveText.setText("");

        reviveButton = findViewById(R.id.reviveButton);
        //reviveButton.setVisibility(View.INVISIBLE);
        reviveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showRewardedVideo();
                    }
                });
    }

    private void loadRewardedAd() {
        if (rewardedAd == null || !rewardedAd.isLoaded()) {
            rewardedAd = new RewardedAd(this, AD_UNIT_ID);
            isLoading = true;
            rewardedAd.loadAd(
                    new AdRequest.Builder().build(),
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onRewardedAdLoaded() {
                            // Ad successfully loaded.
                            MainActivity.this.isLoading = false;
                            Toast.makeText(MainActivity.this, "onRewardedAdLoaded", Toast.LENGTH_SHORT).show();
                            Log.d("RewaredeAdLoaded","successfully loaded");
                        }

                        @Override
                        public void onRewardedAdFailedToLoad(int errorCode) {
                            // Ad failed to load.
                            MainActivity.this.isLoading = false;
                            Toast.makeText(MainActivity.this, "onRewardedAdFailedToLoad", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
        }
    }

    private void setReviveText() {
        reviveText.setText("부활!");
    }

    private void showRewardedVideo() {
        reviveButton.setVisibility(View.INVISIBLE);
        if (rewardedAd.isLoaded()) {
            RewardedAdCallback adCallback =
                    new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                            Toast.makeText(MainActivity.this, "onRewardedAdOpened", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            // Ad closed.
                            Toast.makeText(MainActivity.this, "onRewardedAdClosed", Toast.LENGTH_SHORT).show();
                            // Preload the next video ad.
                            MainActivity.this.loadRewardedAd();
                        }

                        @Override
                        public void onUserEarnedReward(RewardItem rewardItem) {
                            // User earned reward.
                            Toast.makeText(MainActivity.this, "onUserEarnedReward", Toast.LENGTH_SHORT).show();
                            setReviveText();
                        }

                        @Override
                        public void onRewardedAdFailedToShow(int errorCode) {
                            // Ad failed to display
                            Toast.makeText(MainActivity.this, "onRewardedAdFailedToShow", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    };
            rewardedAd.show(this, adCallback);
        }
    }

}
