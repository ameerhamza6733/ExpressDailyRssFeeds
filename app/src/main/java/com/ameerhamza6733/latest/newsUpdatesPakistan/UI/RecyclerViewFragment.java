/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.ameerhamza6733.latest.newsUpdatesPakistan.UI;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.ameerhamza6733.latest.newsUpdatesPakistan.MainActivity;
import com.ameerhamza6733.latest.newsUpdatesPakistan.R;
import com.ameerhamza6733.latest.newsUpdatesPakistan.Utils.Constant;
import com.ameerhamza6733.latest.newsUpdatesPakistan.Utils.RssFeed;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager} and a
 * {@link GridLayoutManager}.
 */
public class RecyclerViewFragment extends Fragment implements MainActivity.UpdateUI {

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    protected int indexofp = 0, indexOfPdash = 0;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<RssFeed> mDataset = new ArrayList<>();
    protected RssFeed rssFeed;
    protected Context context;
    protected String url = Constant.HOME_URL;
    protected ProgressBar progressBar;
    protected ImageButton mImageButton;
    protected boolean OnArticalselected = false;
    protected View view;
    
    private int scrollPosition;
    private int indexofThePost;
    private String mysubstring;
    private int mScrollPosition;
    private DateFormat df;
    private String CurrentDay;

    protected int Current_url =0 ;
    private boolean SetNewAdupter = false;
    int i=0;
    private String mMints;

    @Override
    public void onArticleNavigationSeleted(String url,int current_url) {
        this.url = url;
        this.Current_url=current_url;

        i=current_url;

        if(current_url>0)
        {
            mDataset.clear();
            mAdapter = new CustomAdapter(mDataset);
            mRecyclerView.setAdapter(mAdapter);
            SetNewAdupter=true;
        }

        mDataset.clear();
        Log.e(TAG, "onArticleNavigationSeleted");

    }


    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.

        Log.e(TAG, "onCreate");
        context = getActivity();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);


        Log.e(TAG, "onCreateView");

        this.view = rootView;
        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.myRecylerView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);


        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        // mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.RED).sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy < 0) {
                    // Recycle view scrolling up...

                } else if (dy > 0) {
                    // Recycle view scrolling down...
                    mScrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                            .findLastCompletelyVisibleItemPosition();
                    Log.d("scrollPosition", "" + scrollPosition);
                    if ((mScrollPosition + 1 == mDataset.size()) && (i==0 && Current_url <8 )) {

                        SetNewAdupter=false;
                       try {


                           RecyclerViewFragment.this.url = Constant.MY_URLS[Current_url];
                           new LoadRssFeedsItems().execute("");
                           progressBar.setVisibility(View.VISIBLE);

                       }catch (Exception e)
                       {

                       }


                    }else {
                        SetNewAdupter=true;
                    }

                }
            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e(TAG, "getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT");
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        } else {
            Log.e(TAG, "getResources().getConfiguration().orientation == Configuration.Land");
            mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
        }


        if (savedInstanceState != null) {
            // Restore saved layout manager type.
//            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
//                  .getSerializable(KEY_LAYOUT_MANAGER);
            Log.e(TAG, "savedInstanceState ! =null");
            progressBar.setVisibility(View.VISIBLE);
            mDataset = savedInstanceState.getParcelableArrayList(Constant.MY_DATA_SET_PARCE_ABLE_ARRAY_KEY);
            mAdapter = new CustomAdapter(mDataset);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        } else {


            if (mDataset.isEmpty()) {
                Log.e(TAG, "mdata.isRaplty");
                progressBar.setVisibility(View.VISIBLE);
                initDataset();
            }
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);


        return rootView;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.

        // Save currently selected layout manager.
        //  savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
//        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, (Serializable) progressBar);

        savedInstanceState.putParcelableArrayList(Constant.MY_DATA_SET_PARCE_ABLE_ARRAY_KEY, mDataset);


        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {

        if (mDataset.isEmpty()) {

            SetNewAdupter=true;
            progressBar.setVisibility(View.VISIBLE);

            new LoadRssFeedsItems().execute("");


        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        try {


            // Checks the orientation of the screen
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;

            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

            }
            setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class LoadRssFeedsItems extends AsyncTask<String, Void, Void> {
        private String mTitle, mDescription, mLink, mPubDate;
        private String mCategory, mImageLn;
        private String date, mContent;
        int The_post_index;
        private ProgressBar bar;

        public void setProgressBar(ProgressBar bar) {
            this.bar = bar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            progressBar.setProgress(1);
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(String... params) {

            Document rssDocument = null;
            Document documentSendInput;
            try {
                rssDocument = Jsoup.connect(url).timeout(6000).ignoreContentType(true).parser(Parser.xmlParser()).get();

                Elements mItems = rssDocument.select("item");
                RssFeed rssItem;

                for (Element element : mItems) {


                    mTitle = element.select("title").first().text();
                    mDescription = element.select("description").first().text();
                    mLink = element.select("link").first().text();
                    mPubDate = element.select("pubDate").first().text();
                    mCategory = element.select("category").first().text();
                    mImageLn = element.select("media|content").attr("url").toString();

                    mContent = element.select("content|encoded").first().text();

                    mContent = Jsoup.parse(mContent).text();
                    indexofp = mDescription.indexOf(Constant.P);
                    indexOfPdash = mDescription.indexOf(Constant.P_DASH);
                    mDescription = mDescription.substring(indexofp + 3, indexOfPdash);
                    indexofThePost = mContent.indexOf(Constant.THE_psot);


                    mysubstring = mContent.substring(indexofThePost, mContent.length());
                    mContent = mContent.replace(mysubstring, "");
                    CurrentDay = mPubDate.substring(0, 3);
                    mMints = mPubDate.substring(16,22);

                    df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    if(date.substring(0,3).equals(CurrentDay)) {
                        mPubDate=mPubDate.replace(mPubDate, "آج").concat(mMints);
                    }else {
                        mPubDate=mPubDate.substring(0,3).concat(mMints);
                    }


                    Log.i(TAG, "Item title: " + (mContent == null ? "N/A" : mContent));
                    Log.i(TAG, "Item title: " + (mTitle == null ? "N/A" : mTitle));
                    Log.i(TAG, "Item Description: " + (mDescription == null ? "N/A" : mDescription));
                    Log.i(TAG, "Item link: " + (mLink == null ? "N/A" : mLink));
                    Log.i(TAG, "Item data: " + (mImageLn == null ? "N/A" : mImageLn));
                    Log.i(TAG, "Item data: " + (mPubDate == null ? "N/A" : mPubDate));
                    Log.i(TAG, "system date: " + (date == null ? "N/A" : date));

                    rssFeed = new RssFeed(mTitle, mLink, mPubDate, mCategory, mLink, mDescription, mImageLn, context, mContent);
                    mDataset.add(rssFeed);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mDataset.isEmpty()) {
                try {


                    // mImageButton.setVisibility(View.VISIBLE);
                    Snackbar mSnackbar = Snackbar.make(view, "انٹرنیٹ دستیاب نہیں ہے", Snackbar.LENGTH_INDEFINITE)
                            .setAction("دوبارہ کوشش کریں", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                Snackbar.make(getView(), "CheckIn Cancelled", Snackbar.LENGTH_LONG).show();

                                    new LoadRssFeedsItems().execute("");


                                }
                            });
                    mSnackbar.show();
                } catch (NullPointerException n) {
                    n.printStackTrace();
                }

            }
            else {


                if(!SetNewAdupter && (!mDataset.isEmpty() && mAdapter !=null))
                {
                    mAdapter.notifyDataSetChanged();
                    Current_url++;
                }else
                {


                    progressBar.setVisibility(View.INVISIBLE);
                    mAdapter = new CustomAdapter(mDataset);
                    mRecyclerView.setAdapter(mAdapter);

                }


            }

        }

    }
}
