package petrov.ivan.tmdb.ui.adapters.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.databinding.PopularMovieAdapterItemBinding
import petrov.ivan.tmdb.ui.utils.loadMovieImage
import petrov.ivan.tmdb.ui.utils.setMovieReleaseDateFormatted

class MovieViewHolder private constructor(val binding: PopularMovieAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tmdbMovie: TmdbMovie) {
        binding.movieLayout.apply {
            tvOverview.maxLines = 3
            imageView.loadMovieImage(tmdbMovie.backdropPath)
            tvTitle.text = tmdbMovie.title
            tvRating.text = tmdbMovie.voteAverage.toString()
            tvReleaseDate.setMovieReleaseDateFormatted(tmdbMovie.releaseDate)
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
