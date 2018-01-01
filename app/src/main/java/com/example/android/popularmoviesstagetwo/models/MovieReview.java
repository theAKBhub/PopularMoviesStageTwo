package com.example.android.popularmoviesstagetwo.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * A {@link MovieReview} object that contains details related to a single Movie Review record
 * Created by aditibhattacharya on 28/12/2017.
 */

public class MovieReview implements Parcelable {

    /**
     * {@link MovieReview} Attributes
     * Each attribute has a corresponding @SerializedName that is needed for GSON
     * to map the JSON keys (from TMDB API) with the attributes of {@link MovieReview} object.
     */

    // Movie Review ID
    @SerializedName("id")
    private String mReviewId;

    // Review Author
    @SerializedName("author")
    private String mReviewAuthor;

    // Review Content
    @SerializedName("content")
    private String mReviewContent;

    //Review URL
    @SerializedName("url")
    private String mReviewUrl;


    /**
     * Empty constructor
     */
    public MovieReview() {}

    /**
     * Default Constructor - Constructs a new {@link MovieReview} object
     * Scope for this constructor is private so CREATOR can access it
     * @param parcel
     */
    private MovieReview(Parcel parcel) {
        mReviewId = parcel.readString();
        mReviewAuthor = parcel.readString();
        mReviewContent = parcel.readString();
        mReviewUrl = parcel.readString();
    }

    /** Getter method - Review ID */
    public String getId() {
        return mReviewId;
    }

    /** Setter method - Review ID */
    public void setId(String reviewId) {
        mReviewId = reviewId;
    }

    /** Getter method - Review Author */
    public String getReviewAuthor() {
        return mReviewAuthor;
    }

    /** Setter method - Review Author */
    public void setReviewAuthor(String reviewAuthor) {
        mReviewAuthor = reviewAuthor;
    }

    /** Getter method - Review Content */
    public String getReviewContent() {
        return mReviewContent;
    }

    /** Setter method - Review Content */
    public void setReviewContent(String reviewContent) {
        mReviewContent = reviewContent;
    }

    /** Getter method - Review URL */
    public String getReviewUrl() {
        return mReviewUrl;
    }

    /** Setter method - Review URL */
    public void setReviewUrl(String reviewUrl) {
        mReviewUrl = reviewUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mReviewId);
        dest.writeString(mReviewAuthor);
        dest.writeString(mReviewContent);
        dest.writeString(mReviewUrl);
    }

    public static final Parcelable.Creator<MovieReview> CREATOR
            = new Parcelable.Creator<MovieReview>() {

        @Override
        public MovieReview createFromParcel(Parcel source) {
            return new MovieReview(source);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };

}
