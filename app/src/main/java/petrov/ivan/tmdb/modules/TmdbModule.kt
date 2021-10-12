package petrov.ivan.tmdb.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import petrov.ivan.tmdb.AppConstants
import petrov.ivan.tmdb.interfaces.Singleton
import petrov.ivan.tmdb.service.TmdbApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(includes = arrayOf(OkHttpClientModule::class))
class TmdbModule {

    @Singleton
    @Provides
    fun tmdbApi(okHttpClient: OkHttpClient): TmdbApi {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(AppConstants.TMDB_BASE_URL)
            .addConverterFactory(moshiConverterFactory())
            .build()
        return retrofit.create(TmdbApi::class.java)
    }

    private fun moshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }
}
