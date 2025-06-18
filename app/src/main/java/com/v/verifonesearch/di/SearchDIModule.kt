package com.v.verifonesearch.di

import com.v.verifonesearch.data.repo.DummySearchRepo
import com.v.verifonesearch.domain.repo.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext


@Module
@InstallIn(SingletonComponent::class)
object SearchViewModelModule {

    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineContext = Dispatchers.IO

    @Provides
    @DebounceDuration
    fun providesDebounceDuration(): Long = 300L
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DebounceDuration


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSearchRepository(
        dummySearchRepo: DummySearchRepo
    ): SearchRepository
}
