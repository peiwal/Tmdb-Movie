package petrov.ivan.tmdb.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_favorites.*
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.database.FavoritesDatabase
import petrov.ivan.tmdb.database.FavoritesDatabaseDao
import petrov.ivan.tmdb.ui.adapters.listeners.MovieListener
import petrov.ivan.tmdb.ui.base.BaseFragmentViewModel
import petrov.ivan.tmdb.ui.favorites.features.DaggerFragmentFavoritesComponent
import petrov.ivan.tmdb.ui.favorites.features.FragmentFavoritesComponent
import petrov.ivan.tmdb.ui.favorites.features.FragmentFavoritesModule

class FragmentFavorites : BaseFragmentViewModel() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private val itemClickListener = MovieListener { movie ->
        this.findNavController().navigate(
            FragmentFavoritesDirections.actionFavoritesFragmentToFragmentMovieInfo(movie)
        )
    }
    private val fragmentPopularMoviesComponent: FragmentFavoritesComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerFragmentFavoritesComponent.builder()
            .fragmentFavoritesModule(FragmentFavoritesModule(application, itemClickListener))
            .build()
    }
    private lateinit var dataSource: FavoritesDatabaseDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dataSource = FavoritesDatabase.invoke(application).favoritesDatabaseDao

        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }


    override fun createViewModel() {
        val viewModelFactory = FavoritesViewModelFactory(dataSource, application)
        favoritesViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(FavoritesViewModel::class.java)
    }

    override fun registerObservers() {
        favoritesViewModel.favoritesList.observe(this) { value ->
            fragmentPopularMoviesComponent.getMovieListAdapter().items = ArrayList(value)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            adapter = fragmentPopularMoviesComponent.getMovieListAdapter()
            setItemViewCacheSize(10)
        }
        favoritesViewModel.loadData()
    }
}