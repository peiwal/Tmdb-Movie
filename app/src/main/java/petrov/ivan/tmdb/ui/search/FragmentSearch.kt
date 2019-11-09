package petrov.ivan.tmdb.ui.search

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search.*
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.service.TmdbApi
import petrov.ivan.tmdb.ui.base.BaseFragment
import petrov.ivan.tmdb.ui.adapters.SuggestionsAdapter
import petrov.ivan.tmdb.ui.base.BaseFragmentViewModel
import petrov.ivan.tmdb.ui.search.features.DaggerSearchFragmentComponent
import petrov.ivan.tmdb.ui.search.features.SearchFragmentComponent
import petrov.ivan.tmdb.ui.search.features.SearchFragmentModule


class FragmentSearch : BaseFragmentViewModel() {

    private lateinit var viewModel: SearchViewModel
    private val itemClickListener = SuggestionsAdapter.SuggestionListener { movie ->
        this.findNavController().navigate(
            FragmentSearchDirections.actionFragmentSearchToFragmentMovieInfo(
                tmdbComponents.getMoshi().adapter(TmdbMovie::class.java).toJson(movie)
            )
        )
    }
    private val searchFragmentComponent: SearchFragmentComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerSearchFragmentComponent.builder()
            .searchFragmentModule(SearchFragmentModule(application, itemClickListener))
            .build()
    }
    private val movieService: TmdbApi by lazy(mode = LazyThreadSafetyMode.NONE) {
        tmdbComponents.getTmdbService()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun createViewModel() {
        val viewModelFactory = SearchViewModelFactory(movieService, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    override fun registerObservers() {
        viewModel.let {
            it.searchText.observe(this, Observer { text ->
                it.loadSuggest(text)
            })

            it.searchItems.observe(this, Observer { value ->
                searchFragmentComponent.getSuggestionsAdapter().items = value
            })
            it.eventErrorLoadData.observe(this, Observer { value ->
                if (value) {
                    Snackbar.make(searchView, R.string.error_load_data, Snackbar.LENGTH_LONG).show()
                    it.eventErrorLoadData.value = false
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchText.value?.let {
            if (!TextUtils.isEmpty(it))
                searchView.setQuery(it, false)
        }

        searchView.apply {
            setOnQueryTextListener(onQueryTextListener)
            setIconifiedByDefault(false)
            requestFocusFromTouch()
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchFragmentComponent.getSuggestionsAdapter()
        }

        showKeyboard()
    }

    override fun onStop() {
        super.onStop()
        viewModel.searchText.value = searchView.query.toString()
        hideKeyboard()
    }

    private val onQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            viewModel.searchText.value = p0
            return true
        }
    }

    private fun showKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0)
    }

}