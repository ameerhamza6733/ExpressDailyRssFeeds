package com.ameerhamza6733.latest.newsUpdatesPakistan.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ameerhamza6733.latest.newsUpdatesPakistan.MainActivity;
import com.ameerhamza6733.latest.newsUpdatesPakistan.R;
import com.ameerhamza6733.latest.newsUpdatesPakistan.Utils.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Picasso;

public class DetailsScreen extends AppCompatActivity {

    private TextView mDetail;
    private ImageView mImageView;
    private TextView mPubDate;
    private TextView mTitle;
    private String Details,Title,pubDate,ImageLink,PostLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mImageView= (ImageView) findViewById(R.id.mDetailImageView);
        mDetail= (TextView) findViewById(R.id.mDetailDetails);
        mTitle= (TextView) findViewById(R.id.mDetailTitle);
        mPubDate= (TextView) findViewById(R.id.mDetailTimeTextView);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent=getIntent();
        if(intent!=null)
        {

            Details=intent.getStringExtra(Constant.INTENT_POST_DETAIL_KEY);
            Title=intent.getStringExtra(Constant.INTENT_POST_TITLE_KEY);
            pubDate=intent.getStringExtra(Constant.INTENT_POST_PUB_DATE_KEY);
            ImageLink=intent.getStringExtra(Constant.INTENT_POST_IMAGE_LINK);
            PostLink=intent.getStringExtra(Constant.MY_POST_LINK);


            mPubDate.setText(pubDate);
            mTitle.setText(Title);
            mDetail.setText(Details);
            Picasso.with(this)
                    .load(ImageLink)
           . placeholder(R.drawable.back_ground)
                    .fit()
                    .into(mImageView);
        }


        NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView2);

        AdRequest request = new AdRequest.Builder()

                .build();
        adView.loadAd(request);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Title +
                        "\n"+Details+"Read at:"+PostLink);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;

    }

}
