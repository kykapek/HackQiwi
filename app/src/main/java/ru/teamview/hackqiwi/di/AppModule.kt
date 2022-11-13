package ru.teamview.hackqiwi.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.teamview.hackqiwi.ROOT_API
import ru.teamview.hackqiwi.data.datasource.PhoneDataSource
import ru.teamview.hackqiwi.data.datasource.TempDataSource
import ru.teamview.hackqiwi.data.repository.PhoneRepository
import ru.teamview.hackqiwi.data.repository.TempBillsRepository
import ru.teamview.hackqiwi.data.service.PhoneService
import ru.teamview.hackqiwi.data.service.TempApiService
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

    @Singleton
    @Provides
    fun provideTempBillsRepository(tempDataSource: TempDataSource) = TempBillsRepository(tempDataSource)

    @Singleton
    @Provides
    fun provideTempApiService(retrofit: Retrofit) : TempApiService = retrofit.create(TempApiService::class.java)

    @Provides
    @Singleton
    fun provideTempDataSource(tempApiService: TempApiService) = TempDataSource(tempApiService)

    @Singleton
    @Provides
    fun providePhoneRepository(phoneDataSource: PhoneDataSource) = PhoneRepository(phoneDataSource)

    @Singleton
    @Provides
    fun providePhoneService(retrofit: Retrofit) : PhoneService = retrofit.create(PhoneService::class.java)

    @Provides
    @Singleton
    fun providePhoneDataSource(phoneService: PhoneService) = PhoneDataSource(phoneService)
}