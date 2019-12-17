package com.torkmandukk.githubrepos.di

import android.app.Application
import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.torkmandukk.githubrepos.api.GithubService
import com.torkmandukk.githubrepos.api.LiveDataCallAdapterFactory
import com.torkmandukk.githubrepos.room.*
import dagger.Module
import dagger.Provides
import io.reactivex.annotations.NonNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

    @Provides
    @Singleton
    fun provideGithubService(@NonNull retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(
                application.applicationContext,
                AppDatabase::class.java,
                "GithubRepos.db")
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(@NonNull database: AppDatabase): HistoryDao {
        return database.historyDao()
    }

    @Provides
    @Singleton
    fun provideGithubUserDao(@NonNull database: AppDatabase): GithubUserDao {
        return database.githubUserDao()
    }

    @Provides
    @Singleton
    fun provideRepoDao(@NonNull database: AppDatabase): RepoDao {
        return database.repoDao()
    }

    @Provides
    @Singleton
    fun provideCommitDao(@NonNull database: AppDatabase): CommitDao {
        return database.commitDao()
    }
}
