package com.bundev.gexplorer_mobile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object GexplorerApiModule {
    @Provides
    @Singleton
    fun provideRepo(): GexplorerRepository {
        return GexplorerRepository(null) // add some kind of local properties fetching code here?
    }
}
