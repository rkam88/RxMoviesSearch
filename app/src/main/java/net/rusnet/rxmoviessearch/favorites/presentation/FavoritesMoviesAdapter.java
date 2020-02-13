package net.rusnet.rxmoviessearch.favorites.presentation;

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

import java.util.List;

public class FavoritesMoviesAdapter extends RecyclerView.Adapter<FavoritesMoviesAdapter.ViewHolder> {

    interface OnFavoritesButtonClickListener {
        void onClick(int movieToDeletePosition);
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
                            mOnFavoritesButtonClickListener.onClick(position);
                        }
                    }
                }
            });
        }
    }

    private static final String EMPTY_STRING = "";
    private List<Movie> mFavoriteMoviesList;
    private OnFavoritesButtonClickListener mOnFavoritesButtonClickListener;

    public FavoritesMoviesAdapter(@NonNull List<Movie> favoriteMoviesList,
                                  @NonNull OnFavoritesButtonClickListener onFavoritesButtonClickListener) {
        mFavoriteMoviesList = favoriteMoviesList;
        mOnFavoritesButtonClickListener = onFavoritesButtonClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mFavoriteMoviesList.get(position);

        TextView titleTextView = holder.mTitleTextView;

        ImageView favoritesButton = holder.mFavoritesButton;
        favoritesButton.setImageResource(R.drawable.ic_star_selected_yellow_24dp);

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
    }

    @Override
    public int getItemCount() {
        return mFavoriteMoviesList.size();
    }

}
