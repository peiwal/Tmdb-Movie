package petrov.ivan.tmdb.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.databinding.SuggestArapterItemBinding
import java.util.*


class SuggestionsAdapter(val context: Context, val clickListener: SuggestionListener) : RecyclerView.Adapter<SuggestionsAdapter.ViewHolder>() {

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
        holder.bind(items[position], clickListener)
    }

    class ViewHolder private constructor(val binding: SuggestArapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tmdbMovie: TmdbMovie, clickListener: SuggestionListener) {
            binding.apply {
                movie = tmdbMovie
                this.clickListener = clickListener
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SuggestArapterItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class SuggestionListener(val clickListener: (movie: TmdbMovie) -> Unit) {
        fun onClick(movie: TmdbMovie) = clickListener(movie)
    }
}