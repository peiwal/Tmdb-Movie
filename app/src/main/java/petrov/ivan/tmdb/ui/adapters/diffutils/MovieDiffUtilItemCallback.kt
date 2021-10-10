package petrov.ivan.tmdb.ui.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import petrov.ivan.tmdb.data.TmdbMovie

class MovieDiffUtilItemCallback: DiffUtil.ItemCallback<TmdbMovie>() {
    override fun areItemsTheSame(oldItem: TmdbMovie, newItem: TmdbMovie) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TmdbMovie, newItem: TmdbMovie): Boolean {
        return oldItem == newItem
    }
}