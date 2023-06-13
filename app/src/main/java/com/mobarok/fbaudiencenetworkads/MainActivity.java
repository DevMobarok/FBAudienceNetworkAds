package com.mobarok.fbaudiencenetworkads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AudienceNetwork audienceNetwork;
    Button showAd, showAdStartActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAdStartActivity = findViewById(R.id.showAdStartActivity);
        showAd = findViewById(R.id.showAd);

        audienceNetwork = new AudienceNetwork(this)
                .setFullScreenStatus(true)
                .setBannerStatus(true)
                .init()
                .setInterstitialId("VID_HD_16_9_15S_APP_INSTALL#YOUR_PLACEMENT_ID")
                .setBannerId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
                .setBannerLayoutId(R.id.ad_container)
                .setClick(1)
                .build();
        showAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audienceNetwork.isLoaded()){
                    audienceNetwork.show();
                }else {
                    Toast.makeText(MainActivity.this, "Ad is'nt ready yet", Toast.LENGTH_SHORT).show();
                }
            }
        });


        showAdStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audienceNetwork.isLoaded()){
                    audienceNetwork.show(new AudienceNetwork.Dismissed() {
                        @Override
                        public void onclick() {
                            startActivity(new Intent(MainActivity.this, SecondActivity.class));
                        }
                    });
                }else {
                    Toast.makeText(MainActivity.this, "Ad is'nt ready yet", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                }
            }
        });











    }


}