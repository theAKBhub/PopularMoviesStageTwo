package com.example.android.popularmoviesstagetwo.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.android.popularmoviesstagetwo.utils.BuildConfig;
import com.google.gson.annotations.SerializedName;

/**
 * A {@link MovieTrailer} object that contains details related to a single Movie Trailer
 * Created by aditibhattacharya on 28/12/2017.
 */

public class MovieTrailer implements Parcelable {

    /**
     * {@link MovieTrailer} Attributes
     * Each attribute has a corresponding @SerializedName that is needed for GSON
     * to map the JSON keys (from TMDB API) with the attributes of {@link MovieTrailer} object.
     */

    // Movie Trailer ID
    @SerializedName("id")
    private String mTrailerId;

    // Trailer ISO Code 639
    @SerializedName("iso_639_1")
    private String mTrailerIsoCode639;

    // Trailer ISO Code 3166
    @SerializedName("iso_3166_1")
    private String mTrailerIsoCode3166;

    // Trailer Key
    @SerializedName("key")
    private String mTrailerKey;

    // Trailer Name
    @SerializedName("name")
    private String mTrailerName;

    //Trailer Site
    @SerializedName("site")
    private String mTrailerSite;

    // Trailer Size
    @SerializedName("size")
    private int mTrailerSize;

    // Trailer Type
    @SerializedName("type")
    private String mTrailerType;


    /**
     * Empty constructor
     */
    public MovieTrailer() {}

    /**
     * Default Constructor - Constructs a new {@link MovieTrailer} object
     * Scope for this constructor is private so CREATOR can access it
     * @param parcel
     */
    private MovieTrailer(Parcel parcel) {
        mTrailerId = parcel.readString();
        mTrailerIsoCode639 = parcel.readString();
        mTrailerIsoCode3166 = parcel.readString();
        mTrailerKey = parcel.readString();
        mTrailerName = parcel.readString();
        mTrailerSite = parcel.readString();
        mTrailerSize = parcel.readInt();
        mTrailerType = parcel.readString();
    }

    /** Getter method - Trailer ID */
    public String getId() {
        return mTrailerId;
    }

    /** Setter method - Trailer ID */
    public void setId(String trailerId) {
        mTrailerId = trailerId;
    }

    /** Getter method - Trailer ISO Code 639 */
    public String getTrailerIsoCode639() {
        return mTrailerIsoCode639;
    }

    /** Setter method - Trailer ISO Code 639 */
    public void setTrailerIsoCode639(String trailerIsoCode639) {
        mTrailerIsoCode639 = trailerIsoCode639;
    }

    /** Getter method - Trailer ISO Code 3166 */
    public String getTrailerIsoCode3166() {
        return mTrailerIsoCode3166;
    }

    /** Setter method - Trailer ISO Code 3166 */
    public void setTrailerIsoCode3166(String trailerIsoCode3166) {
        mTrailerIsoCode639 = trailerIsoCode3166;
    }

    /** Getter method - Trailer Key */
    public String getTrailerKey() {
        return mTrailerKey;
    }

    /** Setter method - Trailer Key */
    public void setTrailerKey(String trailerKey) {
        mTrailerKey = trailerKey;
    }

    /** Getter method - Trailer Name */
    public String getTrailerName() {
        return mTrailerName;
    }

    /** Setter method - Trailer Name */
    public void setTrailerName(String trailerName) {
        mTrailerName = trailerName;
    }

    /** Getter method - Trailer Site */
    public String getTrailerSite() {
        return mTrailerSite;
    }

    /** Setter method - Trailer Site */
    public void setTrailerSite(String trailerSite) {
        mTrailerSite = trailerSite;
    }

    /** Getter method - Trailer Size */
    public int getTrailerSize() {
        return mTrailerSize;
    }

    /** Setter method - Trailer Size */
    public void setTrailerSize(int trailerSize) {
        mTrailerSize = trailerSize;
    }

    /** Getter method - Trailer Type */
    public String getTrailerType() {
        return mTrailerType;
    }

    /** Setter method - Trailer Type */
    public void setTrailerType(String trailerType) {
        mTrailerType = trailerType;
    }

    /**
     * Getter method for Video URL
     * @param movieTrailer
     * @return Video URL
     */
    public String getVideoUrl(MovieTrailer movieTrailer) {
        return (movieTrailer.getTrailerSite().equals(BuildConfig.VIDEO_SITE_NAME)) ?
                String.format(BuildConfig.BASE_VIDEO_URL, movieTrailer.getTrailerKey()) : null;
    }

    /**
     * Getter method for Video Thumbnail Image URL
     * @param movieTrailer
     * @return Video Thumbnail Image URL
     */
    public String getVideoThumbImage(MovieTrailer movieTrailer) {
        return (movieTrailer.getTrailerSite().equals(BuildConfig.VIDEO_SITE_NAME)) ?
                String.format(BuildConfig.BASE_VIDEO__THUMB_URL, movieTrailer.getTrailerKey()) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTrailerId);
        dest.writeString(mTrailerIsoCode639);
        dest.writeString(mTrailerIsoCode3166);
        dest.writeString(mTrailerKey);
        dest.writeString(mTrailerName);
        dest.writeString(mTrailerSite);
        dest.writeInt(mTrailerSize);
        dest.writeString(mTrailerType);
    }

    public static final Parcelable.Creator<MovieTrailer> CREATOR
            = new Parcelable.Creator<MovieTrailer>() {

        @Override
        public MovieTrailer createFromParcel(Parcel source) {
            return new MovieTrailer(source);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };

}
