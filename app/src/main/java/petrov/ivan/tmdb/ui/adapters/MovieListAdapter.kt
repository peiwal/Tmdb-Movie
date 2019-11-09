package petrov.ivan.tmdb.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.databinding.PopularMovieAdapterItemBinding
import java.util.*


class MovieListAdapter(val context: Context, val clickListener: MovieListener) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    var items = ArrayList<TmdbMovie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items.get(position), clickListener)
    }

    class ViewHolder private constructor(val binding: PopularMovieAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tmdbMovie: TmdbMovie, clickListener: MovieListener) {
            binding.apply {
                movie = tmdbMovie
                this.clickListener = clickListener
                executePendingBindings()
                root.findViewById<TextView>(R.id.tvOverview).maxLines = 3
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PopularMovieAdapterItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class MovieListener(val clickListener: (movie: TmdbMovie) -> Unit) {
        fun onClick(movie: TmdbMovie) = clickListener(movie)
    }
}
