package com.mikhail.dnstestquest.di.modules

import androidx.lifecycle.SavedStateHandle
import com.mikhail.dnstestquest.di.annotations.TaskId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ProductModule {
    @Provides
    @TaskId
    @ViewModelScoped
    fun provideProductId(
        savedStateHandle: SavedStateHandle
    ): String =
        savedStateHandle.get<String>("taskId")
            ?: throw IllegalArgumentException(
                "You have to provide randomNumber as parameter with type String when navigating to details"
            )
}