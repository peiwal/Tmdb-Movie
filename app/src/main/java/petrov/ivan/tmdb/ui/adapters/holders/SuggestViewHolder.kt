package petrov.ivan.tmdb.ui.adapters.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.databinding.SuggestArapterItemBinding

class SuggestViewHolder private constructor(val binding: SuggestArapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tmdbMovie: TmdbMovie) {
        binding.apply {
            movie = tmdbMovie
            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup): SuggestViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SuggestArapterItemBinding.inflate(layoutInflater, parent, false)
            return SuggestViewHolder(binding)
        }
    }
}