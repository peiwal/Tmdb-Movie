package petrov.ivan.tmdb.modules

import android.content.Context
import android.view.View
import android.widget.TextView
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return retrofit.create(TmdbApi::class.java)
    }

    private fun moshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    fun moshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}