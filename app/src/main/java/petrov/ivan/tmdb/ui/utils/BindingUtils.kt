package petrov.ivan.tmdb.ui.utils

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import petrov.ivan.tmdb.AppConstants
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import java.util.*

@BindingAdapter("movieItemReleaseDateFormatted")
fun TextView.setMovieReleaseDateFormatted(movie: TmdbMovie) {
    if (!movie.releaseDate.isEmpty()) {
        val date = java.text.SimpleDateFormat("yyyy-MM-dd").parse(movie.releaseDate)
        val cal = Calendar.getInstance()
        cal.time = date
        text = cal.get(Calendar.YEAR).toString()
    }
}

@BindingAdapter("movieItemImage")
fun ImageView.setMovieImage(movie: TmdbMovie) {
    Glide.with(context)
        .load(AppConstants.TMDB_PHOTO_URL + movie.backdropPath)
        .placeholder(R.drawable.filmstrip)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}

@BindingAdapter("sugestionItemImage")
fun ImageView.setSuggestionItemImage(movie: TmdbMovie) {
    Glide.with(context)
        .load(AppConstants.TMDB_PHOTO_MINI_URL + movie.backdropPath)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.filmstrip)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}