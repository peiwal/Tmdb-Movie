package petrov.ivan.tmdb.ui.base

import android.os.Bundle
import android.view.View

abstract class BaseFragmentViewModel : BaseFragment() {

    protected abstract fun createViewModel()

    protected abstract fun registerObservers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
        registerObservers()
    }
}
