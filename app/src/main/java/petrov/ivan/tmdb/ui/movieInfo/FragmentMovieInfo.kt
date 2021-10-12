package petrov.ivan.tmdb.ui.movieInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.database.FavoritesDatabase
import petrov.ivan.tmdb.database.FavoritesDatabaseDao
import petrov.ivan.tmdb.databinding.FragmentMovieInfoBinding
import petrov.ivan.tmdb.ui.base.BaseFragmentViewModel
import petrov.ivan.tmdb.ui.utils.loadMovieImage
import petrov.ivan.tmdb.ui.utils.setMovieReleaseDateFormatted
import timber.log.Timber

class FragmentMovieInfo : BaseFragmentViewModel() {

    private lateinit var tmdbMovie: TmdbMovie
    private lateinit var dataSource: FavoritesDatabaseDao
    private lateinit var movieInfoViewModel: MovieInfoViewModel
    private lateinit var binding: FragmentMovieInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieInfoBinding.inflate(inflater, container, false)

        arguments?.let {
            tmdbMovie = FragmentMovieInfoArgs.fromBundle(it).movieData
        } ?: Timber.e("incorrect state: arguments not found")

        dataSource = FavoritesDatabase.invoke(application).favoritesDatabaseDao

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fbFavorite.setOnClickListener {
            movieInfoViewModel.fbFavoriteClick()
        }
        binding.movieLayout.apply {
            imageView.loadMovieImage(tmdbMovie.backdropPath)
            tvTitle.text = tmdbMovie.title
            tvRating.text = tmdbMovie.voteAverage.toString()
            tvReleaseDate.setMovieReleaseDateFormatted(tmdbMovie.releaseDate)
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
