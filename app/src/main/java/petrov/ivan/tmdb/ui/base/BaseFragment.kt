package petrov.ivan.tmdb.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import petrov.ivan.tmdb.application.TmdbApplication
import petrov.ivan.tmdb.components.TmdbComponents

abstract class BaseFragment : Fragment() {

    protected lateinit var application: TmdbApplication

    protected val tmdbComponents: TmdbComponents by lazy(mode = LazyThreadSafetyMode.NONE) {
        application.getTmdbComponent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        application = requireNotNull(this.activity).application as TmdbApplication
    }
}