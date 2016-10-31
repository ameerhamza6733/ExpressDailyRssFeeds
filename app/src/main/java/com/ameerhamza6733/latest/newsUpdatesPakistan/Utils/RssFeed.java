package com.ameerhamza6733.latest.newsUpdatesPakistan.Utils;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AmeerHamza on 9/21/2016.
 */

public class RssFeed implements Parcelable{
    private String ItemTitle;
    private String PostLink;
    private String pubDate;
    private String category;
    private String isPermaLink;
    private String description;
    private String itemImageLink;
    private Context context;
    private String Details;



    public RssFeed(String itemTitle, String postLink, String pubDate, String category, String isPermaLink, String description, String itemImageLink,Context context,String Details) {
        ItemTitle = itemTitle;
        PostLink = postLink;
        this.pubDate = pubDate;
        this.category = category;
        this.isPermaLink = isPermaLink;
        this.description = description;
        this.itemImageLink=itemImageLink;
        this.Details=Details;
        this.context=context;
    }

    protected RssFeed(Parcel in) {
        ItemTitle = in.readString();
        PostLink = in.readString();
        pubDate = in.readString();
        category = in.readString();
        isPermaLink = in.readString();
        description = in.readString();
        itemImageLink = in.readString();
        Details = in.readString();
    }

    public static final Creator<RssFeed> CREATOR = new Creator<RssFeed>() {
        @Override
        public RssFeed createFromParcel(Parcel in) {
            return new RssFeed(in);
        }

        @Override
        public RssFeed[] newArray(int size) {
            return new RssFeed[size];
        }
    };

    public String getDetails() {
        return Details;
    }

    public String getItemTitle() {
        return ItemTitle;
    }

    public String getPostLink() {
        return PostLink;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getCategory() {
        return category;
    }

    public String getIsPermaLink() {
        return isPermaLink;
    }

    public String getDescription() {
        return description;
    }
    public Context getContext() {
        return context;
    }

    public String getItemImageLink() {
        return itemImageLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ItemTitle);
        dest.writeString(PostLink);
        dest.writeString(pubDate);
        dest.writeString(category);
        dest.writeString(isPermaLink);
        dest.writeString(description);
        dest.writeString(itemImageLink);
        dest.writeString(Details);
    }
}
