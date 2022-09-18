package petrov.ivan.tmdb.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import petrov.ivan.tmdb.AppConstants
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.components.DaggerTmdbComponents
import petrov.ivan.tmdb.service.TmdbApi
import petrov.ivan.tmdb.ui.utils.parseMovieReleaseYear
import javax.inject.Inject
import kotlin.random.Random

class OneRandomMovieWidget : AppWidgetProvider() {

    @Inject
    lateinit var tmdbService: TmdbApi
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private var currentIndex = 0
    private val maxIndex = 20

    init {
        DaggerTmdbComponents.builder()
            .build().inject(this)
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        context?.let {
            appWidgetIds?.forEach { widgetId ->
                coroutineScope.launch {
                    val language = context.getString(R.string.response_language)
                    val response = tmdbService.getPopularMovie(language = language, page = 1)
                    if (response.results.size == maxIndex) { // this is ...
                        currentIndex = Random.nextInt(20)
                        response.results.get(currentIndex).let { movie ->
                            val widgetView =
                                RemoteViews(context.packageName, R.layout.widget_one_random_movie)
                            widgetView.setTextViewText(R.id.tvTitle, movie.title)
                            widgetView.setTextViewText(R.id.tvRating, movie.rating.toString())
                            widgetView.setTextViewText(
                                R.id.tvReleaseDate,
                                parseMovieReleaseYear(movie.releaseDate)
                            )
                            val intent = Intent(context, OneRandomMovieWidget::class.java).apply {
                                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
                                flags = Intent.FLAG_RECEIVER_FOREGROUND or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            widgetView.setOnClickPendingIntent(R.id.btnNext, PendingIntent.getBroadcast(context, widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT))

                            try {
                                val requestFeature = Glide.with(context)
                                    .asBitmap()
                                    .load(AppConstants.TMDB_PHOTO_URL + movie.imageUrl)
                                    .submit()
                                val image = requestFeature.get()
                                widgetView.setImageViewBitmap(R.id.imageView, image)
                            } catch (e: Exception) {
                                Log.e("load image for widget", e.toString())
                            }
                            // after update widget view
                            updateWidget(widgetId, widgetView, context)
                        }
                    }
                }
            }
        }
    }

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        super.onReceive(context, intent)
        intent.extras?.let { extras ->
            val appWidgetIds = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)

            val smallWidgetView =
                RemoteViews(context.packageName, R.layout.widget_one_random_movie)
            updateWidget(appWidgetIds, smallWidgetView, context)

            this.onUpdate(
                context,
                AppWidgetManager.getInstance(context),
                appWidgetIds = intArrayOf(appWidgetIds)
            )
        }
    }

    private fun updateWidget(
        widgetId: Int,
        remoteViews: RemoteViews,
        context: Context
    ) {
        val manager = AppWidgetManager.getInstance(context)
        manager.updateAppWidget(widgetId, remoteViews)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        job.cancel()
    }
}
