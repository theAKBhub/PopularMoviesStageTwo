package com.example.android.popularmoviesstagetwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.popularmoviesstagetwo.adapters.FragmentAdapter;
import com.example.android.popularmoviesstagetwo.models.Movie;
import com.example.android.popularmoviesstagetwo.utils.BuildConfig;


public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static Movie sCurrentMovie;

    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Retrieve Intent Extras
        Intent intent = getIntent();
        if (intent.getParcelableExtra(BuildConfig.INTENT_EXTRA_KEY_MOVIE) != null) {
            sCurrentMovie = intent.getParcelableExtra(BuildConfig.INTENT_EXTRA_KEY_MOVIE);
        }
        if (intent.getExtras() != null) {
            setTitle(intent.getStringExtra(BuildConfig.INTENT_EXTRA_KEY_TITLE));
        }

        // Set up Tab Layout and ViewPager
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), DetailActivity.this));
        mTabLayout.setupWithViewPager(viewPager);
    }
}
