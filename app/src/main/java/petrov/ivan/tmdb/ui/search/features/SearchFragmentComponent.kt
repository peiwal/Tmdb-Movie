package petrov.ivan.tmdb.ui.search.features

import dagger.Component
import petrov.ivan.tmdb.ui.adapters.SuggestionsAdapter

@Component(modules = arrayOf(SearchFragmentModule::class))
@SearchFragmentScope
interface SearchFragmentComponent {

    fun getSuggestionsAdapter(): SuggestionsAdapter
}
