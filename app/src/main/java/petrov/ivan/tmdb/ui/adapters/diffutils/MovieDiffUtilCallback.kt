package petrov.ivan.tmdb.ui.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import petrov.ivan.tmdb.database.MovieData

class MovieDiffUtilCallback(
    private val oldList: List<MovieData>,
    private val newList: List<MovieData>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
