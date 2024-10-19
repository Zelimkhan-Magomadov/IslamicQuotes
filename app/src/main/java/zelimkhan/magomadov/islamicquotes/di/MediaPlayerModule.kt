package zelimkhan.magomadov.islamicquotes.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zelimkhan.magomadov.islamicquotes.data.media.MediaPlayerManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MediaPlayerModule {

    @Provides
    @Singleton
    fun provideMediaPlayerManager(
        @ApplicationContext context: Context,
    ): MediaPlayerManager {
        return MediaPlayerManager(context)
    }
}