package petrov.ivan.tmdb.ui.adapters.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.tmdb.database.MovieData
import petrov.ivan.tmdb.databinding.PopularMovieAdapterItemBinding
import petrov.ivan.tmdb.ui.utils.loadMovieImage
import petrov.ivan.tmdb.ui.utils.parseMovieReleaseYear

class MovieViewHolder private constructor(val binding: PopularMovieAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {

    val imageView by lazy { binding.movieLayout.imageView }

    fun bind(tmdbMovie: MovieData) {
        ViewCompat.setTransitionName(imageView, tmdbMovie.id)
        binding.movieLayout.apply {
            tvOverview.maxLines = 3
            imageView.loadMovieImage(tmdbMovie.imageUrl)
            tvTitle.text = tmdbMovie.title
            tvRating.text = tmdbMovie.rating.toString()
            tvReleaseDate.text = parseMovieReleaseYear(tmdbMovie.releaseDate)
            tvOverview.text = tmdbMovie.overview
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
