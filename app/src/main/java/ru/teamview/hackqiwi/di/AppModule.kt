package ru.teamview.hackqiwi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.teamview.hackqiwi.ROOT_API
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesBaseUrl(): String {
        return ROOT_API
    }


    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)

        return retrofit.build()
    }
}