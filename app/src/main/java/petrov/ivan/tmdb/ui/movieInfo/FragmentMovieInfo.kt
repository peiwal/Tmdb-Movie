package petrov.ivan.tmdb.ui.movieInfo

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.database.FavoritesDatabase
import petrov.ivan.tmdb.database.FavoritesDatabaseDao
import petrov.ivan.tmdb.databinding.FragmentMovieInfoBinding
import petrov.ivan.tmdb.ui.base.BaseFragmentViewModel
import timber.log.Timber


class FragmentMovieInfo : BaseFragmentViewModel() {

    private lateinit var tmdbMovie: TmdbMovie
    private lateinit var dataSource: FavoritesDatabaseDao
    private lateinit var movieInfoViewModel: MovieInfoViewModel
    private lateinit var dialogDelete: AlertDialog
    private lateinit var binding: FragmentMovieInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info, container, false)

        arguments?.let {
            val movieDataJson = FragmentMovieInfoArgs.fromBundle(it).movieData

            tmdbMovie =  tmdbComponents.getMoshi().adapter(TmdbMovie::class.java).fromJson(movieDataJson)!!
        } ?: Timber.e("incorrect state: arguments not found")

        dataSource = FavoritesDatabase.invoke(application).favoritesDatabaseDao

        return binding.root
    }

    override fun createViewModel() {
        val viewModelFactory = MovieInfoViewModelFactory(dataSource, application, tmdbMovie)

        movieInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieInfoViewModel::class.java)

        binding.movieInfoViewModel = movieInfoViewModel
    }

    override fun registerObservers() {
        movieInfoViewModel.let {
            it.isFavorite.observe(this, Observer { value ->
                binding.fbFavorite.setImageDrawable(
                    if (value) resources.getDrawable(R.drawable.delete, null)
                    else resources.getDrawable(R.drawable.star, null)
                )
            })

            it.eventNeedShowDialog.observe(this, Observer { value ->
                if (value) {
                    showDialogDelete()
                    it.dialogShowed()
                }
            })
        }
    }

    private fun showDialogDelete() {
        if (!::dialogDelete.isInitialized) {
            dialogDelete = AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_delete_favorites_title)
                .setPositiveButton(R.string.dialog_delete_button) {dialogInterface, i ->  movieInfoViewModel.deleteMovieFromFavorite()}
                .setNegativeButton(R.string.dialog_cancel_buttion) {dialogInterface, i ->  }
                .show()
        }
        dialogDelete.show()
    }

    override fun onStop() {
        super.onStop()
        if (::dialogDelete.isInitialized) dialogDelete.dismiss()
    }
}