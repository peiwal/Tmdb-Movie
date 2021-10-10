package petrov.ivan.tmdb.ui.search.features

import android.content.Context
import dagger.Module
import dagger.Provides
import petrov.ivan.tmdb.ui.adapters.SuggestionsAdapter
import petrov.ivan.tmdb.ui.adapters.listeners.SuggestionListener

@Module
class SearchFragmentModule(private val context: Context, private val clickListener: SuggestionListener) {

    @Provides
    @SearchFragmentScope
    fun suggestionsAdapter() : SuggestionsAdapter {
        return SuggestionsAdapter(context, clickListener)
    }
}
