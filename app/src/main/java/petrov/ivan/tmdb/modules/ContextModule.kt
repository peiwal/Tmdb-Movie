package petrov.ivan.tmdb.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import petrov.ivan.tmdb.interfaces.ApplicationContext

@Module
class ContextModule(internal var context: Context) {

    @ApplicationContext
    @Provides
    fun context(): Context {
        return context.applicationContext
    }
}
