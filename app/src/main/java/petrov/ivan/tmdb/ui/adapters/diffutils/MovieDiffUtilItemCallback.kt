package petrov.ivan.tmdb.ui.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import petrov.ivan.tmdb.database.MovieData

class MovieDiffUtilItemCallback : DiffUtil.ItemCallback<MovieData>() {
    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem == newItem
    }
}
