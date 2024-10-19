package zelimkhan.magomadov.islamicquotes.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zelimkhan.magomadov.islamicquotes.data.background.ColorBackgroundGenerator
import zelimkhan.magomadov.islamicquotes.data.wallpaper.WallpaperGenerator
import zelimkhan.magomadov.islamicquotes.data.wallpaper.WallpaperService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WallpaperModule {

    @Provides
    @Singleton
    fun provideWallpaperService(
        @ApplicationContext context: Context,
    ): WallpaperService {
        return WallpaperService(context)
    }

    @Provides
    @Singleton
    fun provideColorWallpaperGenerator(
        @ApplicationContext context: Context
    ): ColorBackgroundGenerator {
        return ColorBackgroundGenerator(context)
    }

    @Provides
    @Singleton
    fun provideWallpaperGenerator(
        @ApplicationContext context: Context
    ): WallpaperGenerator {
        return WallpaperGenerator(context)
    }
}
