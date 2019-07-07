package com.example.android.popularmoviesstagetwo.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import androidx.core.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import com.example.android.popularmoviesstagetwo.R;
import com.example.android.popularmoviesstagetwo.fragments.MovieDetailsFragment;
import com.example.android.popularmoviesstagetwo.fragments.MovieReviewFragment;
import com.example.android.popularmoviesstagetwo.fragments.MovieTrailerFragment;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;
    private static final String [] TAB_TITLES = new String[] { "Details", "Trailers", "Reviews" };
    private Context mContext;

    private static final int [] TAB_ICON_RESID = {
            R.drawable.movie_roll_white,
            R.drawable.youtube_play_white,
            R.drawable.message_draw_white
    };


    /**
     * Default constructor
     * @param fragmentManager
     * @param context
     */
    public FragmentAdapter (FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;


        switch(position) {
            case 0:
                fragment = new MovieDetailsFragment();
                break;
            case 1:
                fragment = new MovieTrailerFragment();
                break;
            case 2:
                fragment = new MovieReviewFragment();
                break;
            default:
                fragment = new MovieDetailsFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Return image resources and titles for tab icons
        Drawable image = ContextCompat.getDrawable(mContext, TAB_ICON_RESID[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());

        SpannableString spannableString = new SpannableString("  " + TAB_TITLES[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);

        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_INTERMEDIATE);
        return spannableString;
    }
}
