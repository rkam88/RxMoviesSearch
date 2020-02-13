package net.rusnet.rxmoviessearch.search.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.rusnet.rxmoviessearch.R;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private OnScrollListener mOnScrollListener;

    interface OnScrollListener {
        void onScroll(int pageToLoad, @NonNull String searchQuery);
    }

    public void setOnScrollListener(
            @NonNull OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    private OnFavoritesButtonClickListener mOnFavoritesButtonClickListener;

    interface OnFavoritesButtonClickListener {
        void onClick(@NonNull Movie movie);
    }

    public void setOnFavoritesButtonClickListener(
            @NonNull OnFavoritesButtonClickListener onFavoritesButtonClickListener) {
        mOnFavoritesButtonClickListener = onFavoritesButtonClickListener;
    }

    private LoadingMoreIndicator mLoadingMoreIndicator;

    interface LoadingMoreIndicator {
        void showLoadingMoreIndicator();

        void hideLoadingMoreIndicator();
    }

    public void setLoadingMoreIndicator(@NonNull LoadingMoreIndicator loadingMoreIndicator) {
        mLoadingMoreIndicator = loadingMoreIndicator;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTextView;
        ImageView mFavoritesButton;
        ImageView mPosterImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.text_view_title);
            mPosterImageView = itemView.findViewById(R.id.image_view_poster);
            mFavoritesButton = itemView.findViewById(R.id.button_favorites);
            mFavoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnFavoritesButtonClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Movie movie = mMovieList.get(position);
                            movie.setInFavorites(!movie.isInFavorites());
                            int newIcon = (movie.isInFavorites()) ? R.drawable.ic_star_selected_yellow_24dp : R.drawable.ic_star_unselected_yellow_24dp;
                            mFavoritesButton.setImageResource(newIcon);
                            mOnFavoritesButtonClickListener.onClick(movie);
                        }
                    }
                }
            });
        }
    }

    private static final String EMPTY_STRING = "";
    private static final int LOAD_MORE_OFFSET = 5;
    private static final int NEXT_PAGE = 1;
    private List<Movie> mMovieList;
    private List<Movie> mFavoriteMovies;
    private String mSearchQuery;
    private long mTotalResults;
    private int mMoviesPerPage;
    private boolean mLoadingMore;

    public MoviesAdapter(@NonNull List<Movie> movieList,
                         @NonNull String searchQuery,
                         long totalResults,
                         int moviesPerPage) {
        mMovieList = movieList;
        mSearchQuery = searchQuery;
        mTotalResults = totalResults;
        mMoviesPerPage = moviesPerPage;
        mFavoriteMovies = new ArrayList<>();
    }

    @NonNull
    public List<Movie> getMovieList() {
        return mMovieList;
    }

    @NonNull
    public List<Movie> getFavoriteMovies() {
        return mFavoriteMovies;
    }

    @NonNull
    public String getSearchQuery() {
        return mSearchQuery;
    }

    public long getTotalResults() {
        return mTotalResults;
    }

    public int getMoviesPerPage() {
        return mMoviesPerPage;
    }

    public void setMovieList(@NonNull List<Movie> movieList) {
        mMovieList = movieList;
        mMoviesPerPage = mMovieList.size();
        if (!mFavoriteMovies.isEmpty()) {
            for (Movie movie : movieList) {
                for (Movie favoriteMovie : mFavoriteMovies) {
                    if (movie.getImdbID().equals(favoriteMovie.getImdbID()))
                        movie.setInFavorites(true);
                }
            }
        }
    }

    public void setSearchQuery(@NonNull String searchQuery) {
        mSearchQuery = searchQuery;
    }

    public void setTotalResults(long totalResults) {
        mTotalResults = totalResults;
    }

    public void setFavoriteMovies(@NonNull List<Movie> favoriteMovies) {
        mFavoriteMovies = favoriteMovies;
        if (!mMovieList.isEmpty()) {
            for (Movie movie : mMovieList) {
                movie.setInFavorites(false);
                for (Movie favoriteMovie : mFavoriteMovies) {
                    if (movie.getImdbID().equals(favoriteMovie.getImdbID()))
                        movie.setInFavorites(true);
                }
            }
        }
        mLoadingMore = false;
    }

    public void updateMovieList(@NonNull List<Movie> movieList) {
        if (!mFavoriteMovies.isEmpty()) {
            for (Movie movie : movieList) {
                for (Movie favoriteMovie : mFavoriteMovies) {
                    if (movie.getImdbID().equals(favoriteMovie.getImdbID()))
                        movie.setInFavorites(true);
                }
            }
        }
        mMovieList.addAll(movieList);
        mLoadingMore = false;
        mLoadingMoreIndicator.hideLoadingMoreIndicator();
    }

    public void setLoadingStatus(boolean loadingMore) {
        mLoadingMore = loadingMore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_movie, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);

        TextView titleTextView = holder.mTitleTextView;

        ImageView favoritesButton = holder.mFavoritesButton;
        int newIcon = (movie.isInFavorites()) ? R.drawable.ic_star_selected_yellow_24dp : R.drawable.ic_star_unselected_yellow_24dp;
        favoritesButton.setImageResource(newIcon);

        ImageView posterImageView = holder.mPosterImageView;

        String fullTitle = String.format(titleTextView.getContext().getString(R.string.movie_title),
                movie.getTitle(),
                movie.getYear());
        titleTextView.setText(fullTitle);

        String imageUrl = movie.getPosterURL();
        if (imageUrl.equals(EMPTY_STRING)) {
            posterImageView.setImageResource(R.drawable.ic_no_poster_black_48dp);
        } else {
            Glide.with(titleTextView)
                    .load(imageUrl)
                    .fitCenter()
                    .centerCrop()
                    .placeholder(R.drawable.ic_search_black_24dp)
                    .error(R.drawable.ic_error_black_48dp)
                    .into(posterImageView);
        }

        if (mMovieList.size() != mTotalResults &&
                position == mMovieList.size() - LOAD_MORE_OFFSET) {
            int pageToLoad = mMovieList.size() / mMoviesPerPage + NEXT_PAGE;
            mOnScrollListener.onScroll(pageToLoad, mSearchQuery);
            mLoadingMore = true;
        }

        if (mLoadingMore && position == mMovieList.size() - 1) {
            mLoadingMoreIndicator.showLoadingMoreIndicator();
        }
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

}
