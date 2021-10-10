package petrov.ivan.tmdb.ui.utils

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import petrov.ivan.tmdb.AppConstants
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import java.util.*

fun TextView.setMovieReleaseDateFormatted(releaseDate: String) {
    if (!releaseDate.isEmpty()) {
        val date = java.text.SimpleDateFormat("yyyy-MM-dd").parse(releaseDate)
        val cal = Calendar.getInstance()
        cal.time = date
        text = cal.get(Calendar.YEAR).toString()
    }
}

fun ImageView.loadMovieImage(backdropPath: String?) {
    Glide.with(context)
        .load(AppConstants.TMDB_PHOTO_URL + backdropPath)
        .placeholder(R.drawable.filmstrip)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}

fun ImageView.loadSuggestionItemImage(backdropPath: String?) {
    Glide.with(context)
        .load(AppConstants.TMDB_PHOTO_MINI_URL + backdropPath)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.filmstrip)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}