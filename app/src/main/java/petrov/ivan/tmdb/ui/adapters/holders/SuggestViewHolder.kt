package petrov.ivan.tmdb.ui.adapters.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.tmdb.database.MovieData
import petrov.ivan.tmdb.databinding.SuggestArapterItemBinding
import petrov.ivan.tmdb.ui.utils.loadSuggestionItemImage

class SuggestViewHolder private constructor(val binding: SuggestArapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tmdbMovie: MovieData) {
        binding.tvTitle.text = tmdbMovie.title
        binding.imgView.loadSuggestionItemImage(tmdbMovie.imageUrl)
    }

    companion object {
        fun from(parent: ViewGroup): SuggestViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SuggestArapterItemBinding.inflate(layoutInflater, parent, false)
            return SuggestViewHolder(binding)
        }
    }
}
