package petrov.ivan.tmdb.ui.popularMovies

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.databinding.FragmentPopularMoviesBinding
import petrov.ivan.tmdb.service.TmdbApi
import petrov.ivan.tmdb.ui.adapters.MovieListPagingAdapter
import petrov.ivan.tmdb.ui.adapters.listeners.MovieListener
import petrov.ivan.tmdb.ui.base.BaseFragmentViewModel
import petrov.ivan.tmdb.ui.popularMovies.features.DaggerFragmentPopularMoviesComponent
import petrov.ivan.tmdb.ui.popularMovies.features.FragmentPopularMoviesComponent
import petrov.ivan.tmdb.ui.popularMovies.features.FragmentPopularMoviesModule


class FragmentPopularMovies: BaseFragmentViewModel() {

    private lateinit var binding: FragmentPopularMoviesBinding
    private lateinit var viewModel: PopularMoviesViewModel
    private val itemClickListener = MovieListener { movie ->
        this.findNavController().navigate(
            FragmentPopularMoviesDirections.actionFragmentPopularMoviesToFragmentMovieInfo(movie)
        )
    }
    private val fragmentPopularMoviesComponent: FragmentPopularMoviesComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerFragmentPopularMoviesComponent.builder()
            .fragmentPopularMoviesModule(FragmentPopularMoviesModule(application, itemClickListener))
            .build()
    }
    private val adapter: MovieListPagingAdapter by lazy {  fragmentPopularMoviesComponent.getMovieListAdapter() }
    private val movieService: TmdbApi by lazy(mode = LazyThreadSafetyMode.NONE) {
        tmdbComponents.getTmdbService()
    }
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) = retryLoading()
    }
    private val connectivityManager by lazy { requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    private val networkChangeFilter = NetworkRequest.Builder().build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener {
            fragmentPopularMoviesComponent.getMovieListAdapter().refresh()
        }

        binding.fbSearch.setOnClickListener {
            this.findNavController()
                .navigate(FragmentPopularMoviesDirections.actionFragmentPopularMoviesToFragmentSearch())
        }

        binding.recyclerView.apply {
            adapter = fragmentPopularMoviesComponent.getMovieListAdapter()
            setItemViewCacheSize(10)
        }
    }

    override fun createViewModel() {
        val viewModelFactory = PopularMoviesViewModelFactory(movieService, application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PopularMoviesViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        connectivityManager.registerNetworkCallback(networkChangeFilter, networkCallback)
    }

    override fun onPause() {
        super.onPause()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun registerObservers() {
        viewModel.pagingData.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                binding.swipeRefreshLayout.isRefreshing = loadState.mediator?.refresh is LoadState.Loading
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Snackbar.make(binding.root, R.string.error_load_data, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun retryLoading() {
        lifecycleScope.launch {
            adapter.retry()
        }
    }
}