package petrov.ivan.tmdb.ui.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import petrov.ivan.tmdb.AppConstants
import petrov.ivan.tmdb.AppConstants.MOVIE_RELEASE_DATE_FORMAT
import petrov.ivan.tmdb.R
import timber.log.Timber
import java.util.*

fun <T> CoroutineScope.launchOnIO(runOnIO: () -> T, resultOnMain: (result: T) -> Unit) {
    launch(Dispatchers.Main) {
        resultOnMain(
            withContext(Dispatchers.IO) {
                runOnIO()
            }
        )
    }
}

fun parseMovieReleaseYear(releaseDate: String): String {
    if (releaseDate.isNotEmpty()) {
        try {
            val date = java.text.SimpleDateFormat(MOVIE_RELEASE_DATE_FORMAT, Locale.getDefault())
                .parse(releaseDate)
            val cal = Calendar.getInstance()
            cal.time = date
            return cal.get(Calendar.YEAR).toString()
        } catch (e: Exception) {
            Timber.e(e, "getMovieReleaseYear failed")
        }
    }
    return releaseDate
}

fun ImageView.loadMovieImage(backdropPath: String?, requestListener: RequestListener<Drawable>? = null) {
    Glide.with(context)
        .load(AppConstants.TMDB_PHOTO_URL + backdropPath)
        .placeholder(R.drawable.filmstrip)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .listener(requestListener)
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
