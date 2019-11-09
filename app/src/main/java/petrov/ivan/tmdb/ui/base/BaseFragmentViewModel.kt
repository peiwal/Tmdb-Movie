package petrov.ivan.tmdb.ui.base

import android.os.Bundle
import android.view.View

abstract class BaseFragmentViewModel: BaseFragment() {

    abstract protected fun createViewModel()

    abstract protected fun registerObservers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
        registerObservers()
    }
}