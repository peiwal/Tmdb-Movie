package petrov.ivan.tmdb.ui.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.ui.adapters.diffutils.MovieDiffUtilItemCallback
import petrov.ivan.tmdb.ui.adapters.holders.MovieViewHolder
import petrov.ivan.tmdb.ui.adapters.listeners.MovieListener

class MovieListPagingAdapter(val context: Context, val clickListener: MovieListener) : PagingDataAdapter<TmdbMovie, MovieViewHolder>(
    MovieDiffUtilItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
            holder.itemView.setOnClickListener { clickListener.onClick(item, holder.imageView) }
        }
    }
}
