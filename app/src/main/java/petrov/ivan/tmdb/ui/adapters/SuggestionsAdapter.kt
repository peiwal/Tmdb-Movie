package petrov.ivan.tmdb.ui.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.tmdb.database.MovieData
import petrov.ivan.tmdb.ui.adapters.diffutils.MovieDiffUtilCallback
import petrov.ivan.tmdb.ui.adapters.holders.SuggestViewHolder
import petrov.ivan.tmdb.ui.adapters.listeners.SuggestionListener

class SuggestionsAdapter(val context: Context, val clickListener: SuggestionListener) : RecyclerView.Adapter<SuggestViewHolder>() {

    var items = ArrayList<MovieData>()
        set(value) {
            val diffCallback = MovieDiffUtilCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestViewHolder {
        return SuggestViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SuggestViewHolder, position: Int) {
        with(items[position]) {
            holder.bind(this)
            holder.itemView.setOnClickListener { clickListener.onClick(this) }
        }
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }
}
