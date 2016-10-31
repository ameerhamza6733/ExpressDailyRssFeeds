package com.ameerhamza6733.latest.newsUpdatesPakistan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ameerhamza6733.latest.newsUpdatesPakistan.UI.myDialogFragment;
import com.ameerhamza6733.latest.newsUpdatesPakistan.UI.RecyclerViewFragment;
import com.ameerhamza6733.latest.newsUpdatesPakistan.UI.SettingsActivity;
import com.ameerhamza6733.latest.newsUpdatesPakistan.Utils.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

     int i;
    private FragmentTransaction transaction;
    private RecyclerViewFragment recyclerViewFragment;
    public static MainActivity context;
    private String TAG = "MainActivity";

    public interface UpdateUI {
        public void onArticleNavigationSeleted(String url , int i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView);

        AdRequest request = new AdRequest.Builder()

                .build();
        adView.loadAd(request);
        context = this;
        recyclerViewFragment = new RecyclerViewFragment();
        if (savedInstanceState == null) {
            transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.sample_content_fragment, recyclerViewFragment);
            transaction.commit();
        } else {
            Log.i(TAG, "savein!null");
            RefrashFragment();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            // Use the Builder class for convenient dialog construction


          super.onBackPressed();




        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void RefrashFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .detach(recyclerViewFragment)
                .attach(recyclerViewFragment)
                .commit();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        try {
            if (id==R.id.All)
            {

                recyclerViewFragment.onArticleNavigationSeleted(Constant.HOME_URL,0);
                RefrashFragment();

            }
            if (id == R.id.Home_Page) {




                recyclerViewFragment.onArticleNavigationSeleted(Constant.HOME_URL,1);
                RefrashFragment();
                // Handle the camera action
            } else if (id == R.id.pakistan) {
                recyclerViewFragment.onArticleNavigationSeleted(Constant.PAKISTAN,2);
                RefrashFragment();


            } else if (id == R.id.business) {


                recyclerViewFragment.onArticleNavigationSeleted(Constant.BUSINESS_URL,3);
                RefrashFragment();

            } else if (id == R.id.world) {
                recyclerViewFragment.onArticleNavigationSeleted(Constant.WORLD_URL,4);
                RefrashFragment();

            } else if (id == R.id.sports) {
                recyclerViewFragment.onArticleNavigationSeleted(Constant.SPORT,5);
                RefrashFragment();
            } else if (id == R.id.magazine) {
                recyclerViewFragment.onArticleNavigationSeleted(Constant.MAGAZAIN,6);
                RefrashFragment();
            } else if (id == R.id.science) {
                recyclerViewFragment.onArticleNavigationSeleted(Constant.SCIENCE,7);
                RefrashFragment();
            } else if (id == R.id.weird_new) {
                recyclerViewFragment.onArticleNavigationSeleted(Constant.WIERD_NEWS,8);
                RefrashFragment();
            } else if (id == R.id.saqafat) {
                recyclerViewFragment.onArticleNavigationSeleted(Constant.SQAFAT,9);
                RefrashFragment();
            }else  if(id==R.id.nav_rate)
            {
                // FIRE ZE MISSILES!
                        final String appPackageName = this.getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }


            }else if(id==R.id.nav_send)
            {
                try{
                    Intent Email = new Intent(Intent.ACTION_SEND);
                    Email.setType("text/email");
                    Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "ameerhamza6733@gmail.com" });
                    Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                    Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
                    startActivity(Intent.createChooser(Email, "Send Feedback:"));

                }catch (Exception e)
                {

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
