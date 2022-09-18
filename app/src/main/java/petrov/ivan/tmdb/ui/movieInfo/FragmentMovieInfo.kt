package petrov.ivan.tmdb.ui.movieInfo

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.database.FavoritesDatabase
import petrov.ivan.tmdb.database.FavoritesDatabaseDao
import petrov.ivan.tmdb.database.MovieData
import petrov.ivan.tmdb.databinding.FragmentMovieInfoBinding
import petrov.ivan.tmdb.ui.base.BaseFragmentViewModel
import petrov.ivan.tmdb.ui.utils.loadMovieImage
import petrov.ivan.tmdb.ui.utils.parseMovieReleaseYear
import timber.log.Timber

class FragmentMovieInfo : BaseFragmentViewModel() {

    private lateinit var tmdbMovie: MovieData
    private lateinit var dataSource: FavoritesDatabaseDao
    private lateinit var movieInfoViewModel: MovieInfoViewModel
    private lateinit var binding: FragmentMovieInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postponeEnterTransition()

        binding = FragmentMovieInfoBinding.inflate(inflater, container, false)

        arguments?.let {
            tmdbMovie = FragmentMovieInfoArgs.fromBundle(it).movieData
        } ?: Timber.e("incorrect state: arguments not found")

        dataSource = FavoritesDatabase.invoke(application).favoritesDatabaseDao

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(binding.movieLayout.imageView, tmdbMovie.id)
        binding.fbFavorite.setOnClickListener {
            movieInfoViewModel.fbFavoriteClick()
        }
        binding.movieLayout.apply {
            imageView.loadMovieImage(
                tmdbMovie.imageUrl,
                object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }
                }
            )
            tvTitle.text = tmdbMovie.title
            tvRating.text = tmdbMovie.rating.toString()
            tvReleaseDate.text = parseMovieReleaseYear(tmdbMovie.releaseDate)
            tvOverview.text = tmdbMovie.overview
        }
    }

    override fun createViewModel() {
        val viewModelFactory = MovieInfoViewModelFactory(dataSource, application, tmdbMovie)

        movieInfoViewModel = ViewModelProvider(this, viewModelFactory).get(MovieInfoViewModel::class.java)
    }

    override fun registerObservers() {
        movieInfoViewModel.let {
            it.isFavorite.observe(
                this,
                Observer { value ->
                    binding.fbFavorite.setImageDrawable(
                        if (value) ContextCompat.getDrawable(requireContext(), R.drawable.delete)
                        else ContextCompat.getDrawable(requireContext(), R.drawable.star)
                    )
                }
            )

            it.eventNeedShowDialog.observe(this) { value ->
                if (value) {
                    showDialogDelete()
                    it.dialogShowed()
                }
            }
        }
    }

    private fun showDialogDelete() {
        activity
        val dialogFrag = DeleteFromFavoritesDialogFragment {
            movieInfoViewModel.deleteMovieFromFavorite()
        }
        dialogFrag.show(parentFragmentManager, "dialog delete")
    }
}
