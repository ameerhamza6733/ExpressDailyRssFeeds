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
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ameerhamza6733.latest.newsUpdatesPakistan.R;
import com.ameerhamza6733.latest.newsUpdatesPakistan.Utils.Constant;
import com.ameerhamza6733.latest.newsUpdatesPakistan.Utils.RssFeed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    public static  int fountType;

    protected static ArrayList<RssFeed> mDataSet;
    private SharedPreferences sharedPref;



    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitle;
        private final TextView mDetails;
        private final TextView mPubdate;
        private final ImageView mImageView;
        private final Context context;
        private final Typeface font;
        private final TextView mCataguty;





        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.

            mTitle = (TextView) v.findViewById(R.id.item_title);
            mDetails= (TextView) v.findViewById(R.id.item_detail);
            mPubdate= (TextView) v.findViewById(R.id.date);
            mImageView= (ImageView) v.findViewById(R.id.item_thumbnail);
            mCataguty= (TextView) v.findViewById(R.id.category);
            context=v.getContext();



            if(fountType==-1)
            {

                font=Typeface.DEFAULT;
            }
            else if (fountType==0) {
                font = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/asunaskh.ttf");

            } else if (fountType==1) {

                font = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/FajerNooriNastalique.ttf");
            } else if (fountType==2) {
                font = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/PakNastaleeq.ttf");
            } else {
                font = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/PakNastaleeq.ttf");
            }
            mTitle.setTypeface(font);
            mDetails.setTypeface(font);

            v.setOnClickListener(this);

        }

        public ImageView getmImageView() {
            return mImageView;
        }
        public TextView getTextView() {
            return mTitle;
        }
        public TextView getmDetails() {
            return mDetails;
        }

        public TextView getmCataguty() {
            return mCataguty;
        }

        public TextView getmPubdate() {return mPubdate;


        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,DetailsScreen.class);
            intent.putExtra(Constant.INTENT_POST_DETAIL_KEY,mDataSet.get(getAdapterPosition()).getDetails());
            intent.putExtra(Constant.INTENT_POST_TITLE_KEY,mDataSet.get(getAdapterPosition()).getItemTitle());
            intent.putExtra(Constant.INTENT_POST_IMAGE_LINK,mDataSet.get(getAdapterPosition()).getItemImageLink());
            intent.putExtra(Constant.INTENT_POST_PUB_DATE_KEY,mDataSet.get(getAdapterPosition()).getPubDate());
            intent.putExtra(Constant.MY_POST_LINK,mDataSet.get(getAdapterPosition()).getPostLink());
            context.startActivity(intent);



        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(ArrayList<RssFeed> dataSet) {
        mDataSet = dataSet;
    }



    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);


        try {


            sharedPref = PreferenceManager.getDefaultSharedPreferences(v.getContext());
            String getPref = sharedPref.getString("example_list", "0");
            fountType = Integer.parseInt(getPref);
            Log.v("fount", "" + fountType);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        Picasso.with(mDataSet.get(position).getContext())
                .load(mDataSet.get(position).getItemImageLink())
                .fit()
                .into(viewHolder.getmImageView());
        viewHolder.getTextView().setText(mDataSet.get(position).getItemTitle());
        viewHolder.getmDetails().setText(mDataSet.get(position).getDescription());

        viewHolder.getmCataguty().setText(mDataSet.get(position).getCategory());
        viewHolder.getmPubdate().setText(mDataSet.get(position).getPubDate());
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mDataSet.isEmpty())
        {
            Log.e(TAG,"mdata set empty");
        }
        return mDataSet.size();
    }


}
