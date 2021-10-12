package petrov.ivan.tmdb.ui.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.ui.adapters.diffutils.MovieDiffUtilCallback
import petrov.ivan.tmdb.ui.adapters.holders.MovieViewHolder
import petrov.ivan.tmdb.ui.adapters.listeners.MovieListener

class MovieListAdapter(val context: Context, val clickListener: MovieListener) : RecyclerView.Adapter<MovieViewHolder>() {
    var items = ArrayList<TmdbMovie>()
        set(value) {
            val diffCallback = MovieDiffUtilCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(items[position]) {
            holder.bind(this)
            holder.itemView.setOnClickListener { clickListener.onClick(this) }
        }
    }
}
