package petrov.ivan.tmdb.ui.popularMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.databinding.FragmentPopularMoviesBinding
import petrov.ivan.tmdb.service.TmdbApi
import petrov.ivan.tmdb.ui.adapters.MovieListAdapter
import petrov.ivan.tmdb.ui.base.BaseFragmentViewModel
import petrov.ivan.tmdb.ui.popularMovies.features.DaggerFragmentPopularMoviesComponent
import petrov.ivan.tmdb.ui.popularMovies.features.FragmentPopularMoviesComponent
import petrov.ivan.tmdb.ui.popularMovies.features.FragmentPopularMoviesModule


class FragmentPopularMovies: BaseFragmentViewModel() {

    private lateinit var binding: FragmentPopularMoviesBinding
    private lateinit var viewModel: PopularMoviesViewModel
    private val itemClickListener = MovieListAdapter.MovieListener { movie ->
        this.findNavController().navigate(
            FragmentPopularMoviesDirections.actionFragmentPopularMoviesToFragmentMovieInfo(movie)
        )
    }
    private val fragmentPopularMoviesComponent: FragmentPopularMoviesComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerFragmentPopularMoviesComponent.builder()
            .fragmentPopularMoviesModule(FragmentPopularMoviesModule(application, itemClickListener))
            .build()
    }
    private val movieService: TmdbApi by lazy(mode = LazyThreadSafetyMode.NONE) {
        tmdbComponents.getTmdbService()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_popular_movies, container, false)

        return binding.root
    }

    override fun createViewModel() {
        val viewModelFactory = PopularMoviesViewModelFactory(movieService, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PopularMoviesViewModel::class.java)

        binding.viewModel = viewModel
    }

    override fun registerObservers() {
        viewModel.let {
            it.eventLoadComplete.observe(this, Observer { value ->
                if (value) onLoadDataComplete()
            })

            it.eventErrorLoadData.observe(this, Observer { value ->
                if (value) {
                    Snackbar.make(binding.root, R.string.error_load_data, Snackbar.LENGTH_LONG).show()
                    viewModel.eventErrorLoadData.value = false
                }
            })

            it.eventOnFabClick.observe(this, Observer { value ->
                if (value) {
                    this.findNavController()
                        .navigate(FragmentPopularMoviesDirections.actionFragmentPopularMoviesToFragmentSearch())
                    viewModel.eventOnFabClick.value = false
                }
            })

            it.movieList.observe(this, Observer {
                (binding.recyclerView.adapter as MovieListAdapter).items = ArrayList(it)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener() {
            viewModel.recyclerViewPosition.value = 0
            binding.swipeRefreshLayout.setRefreshing(true)
            viewModel.loadData()
        }

        binding.recyclerView.adapter = fragmentPopularMoviesComponent.getMovieListAdapter()

        viewModel.movieList.value ?: viewModel.loadData()
    }

    private fun onLoadDataComplete() {
        binding.recyclerView.layoutManager?.scrollToPosition(viewModel.recyclerViewPosition.value ?: 0)
        binding.swipeRefreshLayout.setRefreshing(false)
    }

    override fun onStop() {
        super.onStop()
        viewModel.recyclerViewPosition.value = (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }
}