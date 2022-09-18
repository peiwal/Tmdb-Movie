package petrov.ivan.tmdb.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import petrov.ivan.tmdb.database.FavoritesDatabase
import petrov.ivan.tmdb.database.FavoritesDatabaseDao
import petrov.ivan.tmdb.databinding.FragmentFavoritesBinding
import petrov.ivan.tmdb.ui.adapters.listeners.MovieListener
import petrov.ivan.tmdb.ui.base.BaseFragmentViewModel
import petrov.ivan.tmdb.ui.favorites.features.DaggerFragmentFavoritesComponent
import petrov.ivan.tmdb.ui.favorites.features.FragmentFavoritesComponent
import petrov.ivan.tmdb.ui.favorites.features.FragmentFavoritesModule

class FragmentFavorites : BaseFragmentViewModel() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private val itemClickListener = MovieListener { movie, view ->
        val extras = FragmentNavigatorExtras(
            view to movie.id
        )
        this.findNavController().navigate(
            FragmentFavoritesDirections.actionFavoritesFragmentToFragmentMovieInfo(movie),
            extras
        )
    }
    private val fragmentPopularMoviesComponent: FragmentFavoritesComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerFragmentFavoritesComponent.builder()
            .fragmentFavoritesModule(FragmentFavoritesModule(application, itemClickListener))
            .build()
    }
    private lateinit var dataSource: FavoritesDatabaseDao
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataSource = FavoritesDatabase.invoke(application).favoritesDatabaseDao

        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun createViewModel() {
        val viewModelFactory = FavoritesViewModelFactory(dataSource, application)
        favoritesViewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(FavoritesViewModel::class.java)
    }

    override fun registerObservers() {
        favoritesViewModel.favoritesList.observe(this) { value ->
            fragmentPopularMoviesComponent.getMovieListAdapter().items = ArrayList(value)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prepareTransition()
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = fragmentPopularMoviesComponent.getMovieListAdapter()
            setItemViewCacheSize(10)
        }
        favoritesViewModel.loadData()
    }

    private fun prepareTransition() {
        exitTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
        postponeEnterTransition()
        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }
}
