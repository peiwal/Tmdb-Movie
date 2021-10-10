package petrov.ivan.tmdb.ui.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import petrov.ivan.tmdb.data.TmdbMovie

class MovieDiffUtilCallback(
    private val oldList: List<TmdbMovie>,
    private val newList: List<TmdbMovie>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}