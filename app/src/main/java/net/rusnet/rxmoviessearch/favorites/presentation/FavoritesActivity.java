package net.rusnet.rxmoviessearch.favorites.presentation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import net.rusnet.rxmoviessearch.R;
import net.rusnet.rxmoviessearch.commons.injection.ApplicationComponent;
import net.rusnet.rxmoviessearch.commons.injection.DaggerApplicationComponent;
import net.rusnet.rxmoviessearch.commons.injection.LocalDbModule;
import net.rusnet.rxmoviessearch.commons.injection.NetworkModule;
import net.rusnet.rxmoviessearch.commons.injection.RxSchedulersModule;
import net.rusnet.rxmoviessearch.search.data.source.MoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;
import net.rusnet.rxmoviessearch.search.presentation.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoritesActivity extends AppCompatActivity
        implements FavoritesContract.View,
        FavoritesMoviesAdapter.OnFavoritesButtonClickListener,
        ConfirmationDialogFragment.ConfirmationDialogListener {

    private static final String CONFIRMATION_DIALOG_TAG = "CONFIRMATION_DIALOG_TAG";
    private static final String KEY_MOVIE_TO_DELETE_POSITION = "KEY_MOVIE_TO_DELETE_POSITION";
    private static final String KEY_MOVIE_LIST = "KEY_MOVIE_LIST";
    private static final String KEY_ACTIVITY_RESULT_CODE = "KEY_ACTIVITY_RESULT_CODE";

    @Inject
    FavoritesContract.Presenter mPresenter;

    private List<Movie> mMovieList;
    private RecyclerView mRecyclerView;
    private FavoritesMoviesAdapter mAdapter;
    private FrameLayout mInfoMessage;
    private int mMovieToDeletePosition;
    private int mActivityResultCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ApplicationComponent applicationComponent = DaggerApplicationComponent
                .builder()
                .localDbModule(new LocalDbModule(this))
                .networkModule(new NetworkModule(MoviesRemoteDataSource.BASE_URL))
                .rxSchedulersModule(new RxSchedulersModule())
                .build();
        applicationComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        if (savedInstanceState == null) {
            //noinspection unchecked
            mMovieList = (List) getIntent().getSerializableExtra(SearchActivity.EXTRA_FAVORITE_MOVIES_LIST);
        } else {
            //noinspection unchecked
            mMovieList = (List) savedInstanceState.getSerializable(KEY_MOVIE_LIST);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mInfoMessage = findViewById(R.id.message_frame_layout);
        if (mMovieList.isEmpty()) mInfoMessage.setVisibility(View.VISIBLE);

        mRecyclerView = findViewById(R.id.favorites_recycler_view);
        mAdapter = new FavoritesMoviesAdapter(mMovieList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY_MOVIE_TO_DELETE_POSITION, mMovieToDeletePosition);
        outState.putInt(KEY_ACTIVITY_RESULT_CODE, mActivityResultCode);
        outState.putSerializable(KEY_MOVIE_LIST, new ArrayList<>(mMovieList));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMovieToDeletePosition = savedInstanceState.getInt(KEY_MOVIE_TO_DELETE_POSITION);
        mActivityResultCode = savedInstanceState.getInt(KEY_ACTIVITY_RESULT_CODE);
    }

    @Override
    public void onClick(int movieToDeletePosition) {
        mMovieToDeletePosition = movieToDeletePosition;

        String dialogText = getString(R.string.delete_from_favorites);
        ConfirmationDialogFragment newFragment;
        newFragment = ConfirmationDialogFragment.newInstance(dialogText);
        newFragment.show(getSupportFragmentManager(), CONFIRMATION_DIALOG_TAG);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(mActivityResultCode);
        finish();
    }

    @Override
    public void onPositiveResponse() {
        Movie movie = mMovieList.get(mMovieToDeletePosition);

        mMovieList.remove(mMovieToDeletePosition);
        if (mMovieList.isEmpty()) mInfoMessage.setVisibility(View.VISIBLE);

        mAdapter.notifyItemRemoved(mMovieToDeletePosition);

        mPresenter.deleteFromFavorites(movie);
        mActivityResultCode = RESULT_OK;
    }
}
