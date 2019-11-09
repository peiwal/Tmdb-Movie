package petrov.ivan.tmdb.modules

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import petrov.ivan.tmdb.AppConstants
import petrov.ivan.tmdb.BuildConfig
import timber.log.Timber

@Module(includes = arrayOf(ContextModule::class))
class OkHttpClientModule {

    @Provides
    fun client(loggingInterceptor: HttpLoggingInterceptor, authInterceptor: Interceptor): OkHttpClient {
        if (BuildConfig.DEBUG) {
            return OkHttpClient().newBuilder()
                .cache(null)
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            return OkHttpClient().newBuilder()
                .cache(null)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
        }
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Timber.d(message)
        })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun authInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newUrl = chain.request().url()
                .newBuilder()
                .addQueryParameter("api_key", AppConstants.TMDB_API_KEY)
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
    }

}
