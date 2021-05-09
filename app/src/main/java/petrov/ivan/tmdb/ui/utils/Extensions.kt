package petrov.ivan.tmdb.ui.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun <T> CoroutineScope.launchOnIO(runOnIO: () -> T, resultOnMain: (result: T) -> Unit) {
    launch(Dispatchers.Main) {
        resultOnMain(withContext(Dispatchers.IO) {
            runOnIO()
        })
    }
}