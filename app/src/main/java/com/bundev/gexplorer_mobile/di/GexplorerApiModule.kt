package com.bundev.gexplorer_mobile.di

import android.content.Context
import com.bundev.gexplorer_mobile.TOKEN
import com.bundev.gexplorer_mobile.USERNAME
import com.bundev.gexplorer_mobile.USER_ID
import com.bundev.gexplorer_mobile.dataStore
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object GexplorerApiModule {
    @Provides
    @Singleton
    fun provideRepo(@ApplicationContext context: Context): GexplorerRepository {
        val userFlow: Flow<Triple<String?, String?, String?>> =
            context.dataStore.data.map { preferences ->
                Triple(preferences[TOKEN], preferences[USERNAME], preferences[USER_ID])
            }

        val userTriple = runBlocking {
            userFlow.first()
        }
        val userId = if (userTriple.third.isNullOrEmpty()) null
            else UUID.fromString(userTriple.third)
        return GexplorerRepository(userTriple.first, userTriple.second, userId) // add some kind of local properties fetching code here?
    }
}
