package petrov.ivan.tmdb.ui.adapters.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.databinding.PopularMovieAdapterItemBinding

class MovieViewHolder private constructor(val binding: PopularMovieAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tmdbMovie: TmdbMovie) {
        binding.apply {
            movie = tmdbMovie
            executePendingBindings()
            root.findViewById<TextView>(R.id.tvOverview).maxLines = 3
        }
    }

    companion object {
        fun from(parent: ViewGroup): MovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PopularMovieAdapterItemBinding.inflate(layoutInflater, parent, false)
            return MovieViewHolder(binding)
        }
    }
}