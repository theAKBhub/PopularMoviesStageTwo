package com.example.android.popularmoviesstagetwo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.android.popularmoviesstagetwo.DetailActivity;
import com.example.android.popularmoviesstagetwo.R;
import com.example.android.popularmoviesstagetwo.adapters.TrailerAdapter;
import com.example.android.popularmoviesstagetwo.controllers.MovieApiController;
import com.example.android.popularmoviesstagetwo.controllers.MovieApiInterface;
import com.example.android.popularmoviesstagetwo.exceptions.NoConnectivityException;
import com.example.android.popularmoviesstagetwo.models.Movie;
import com.example.android.popularmoviesstagetwo.models.MovieTrailer;
import com.example.android.popularmoviesstagetwo.models.MovieTrailerResponse;
import com.example.android.popularmoviesstagetwo.utils.BuildConfig;
import com.example.android.popularmoviesstagetwo.utils.Utils;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieTrailerFragment extends Fragment implements TrailerAdapter.ListItemClickListener {

    private static final String LOG_TAG = MovieTrailerFragment.class.getSimpleName();
    private final Movie mMovie = DetailActivity.sCurrentMovie;
    private MovieTrailerResponse mMovieTrailerResponse;
    private List<MovieTrailer> mMovieTrailerList;
    private DetailActivity mParentActivity;
    private View mViewFragment;
    private TrailerAdapter mAdapter;
    private RecyclerView mTrailerView;
    private Toast mToast;
    private RecyclerView.LayoutManager mLayoutManager;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        mParentActivity = (DetailActivity) getActivity();

        // Inflate view object
        mViewFragment = inflater.inflate(R.layout.fragment_detail, container, false);



        mTrailerView = mViewFragment.findViewById(R.id.list_trailers);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mTrailerView.setLayoutManager(mLayoutManager);
        mTrailerView.setHasFixedSize(true);
        mAdapter = new TrailerAdapter(20, this);
        mTrailerView.setAdapter(mAdapter);


        // Load Movie Trailer Data
        // Make Retrofit call to TMDB API
        try {
            //mLoadingIndicator.setVisibility(View.VISIBLE);

            MovieApiInterface apiInterface = MovieApiController.
                    getClient(mParentActivity).
                    create(MovieApiInterface.class);

            final Call<MovieTrailerResponse> responseCall =
                    apiInterface.getMovieTrailerList(mMovie.getId(), BuildConfig.API_KEY);

            responseCall.enqueue(new Callback<MovieTrailerResponse>() {
                @Override
                public void onResponse(Call<MovieTrailerResponse> call, Response<MovieTrailerResponse> response) {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        //mLoadingIndicator.setVisibility(View.INVISIBLE);
                        mMovieTrailerResponse = response.body();
                        mAdapter.setMovieData(mMovieTrailerResponse);
                        mAdapter.notifyDataSetChanged();
                        mMovieTrailerList = mMovieTrailerResponse.getMovieTrailerList();
                        Log.v(LOG_TAG, String.valueOf(mMovieTrailerList.size()));
                        for (MovieTrailer trailer : mMovieTrailerList) {
                            Log.v(LOG_TAG, trailer.getId() + "/" + trailer.getTrailerIsoCode639() + "/" + trailer.getTrailerKey() + "/" + trailer.getTrailerName());
                        }
                    } else {
                        //mLoadingIndicator.setVisibility(View.INVISIBLE);
                        //displayDialog(getString(R.string.error_movie_load_failed) + statusCode);
                        mToast = Utils.showToastMessage(mParentActivity, mToast, getString(R.string.error_movie_load_failed));
                        mToast.show();
                    }
                }

                @Override
                public void onFailure(Call<MovieTrailerResponse> call, Throwable t) {
                    //mLoadingIndicator.setVisibility(View.INVISIBLE);
                    //displayDialog(getString(R.string.error_movie_fetch_failed));
                    mToast = Utils.showToastMessage(mParentActivity, mToast, getString(R.string.error_movie_fetch_failed));
                    mToast.show();
                }
            });
        } catch (NoConnectivityException nce) {
            //mLoadingIndicator.setVisibility(View.INVISIBLE);
            //displayDialog(getString(R.string.error_no_connection));
            mToast = Utils.showToastMessage(mParentActivity, mToast, getString(R.string.error_no_connection));
            mToast.show();
        }

        return mViewFragment;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
