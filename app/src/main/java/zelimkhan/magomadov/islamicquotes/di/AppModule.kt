package zelimkhan.magomadov.islamicquotes.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zelimkhan.magomadov.islamicquotes.data.media.MediaPlayerManager
import zelimkhan.magomadov.islamicquotes.data.quote.QuoteDataSource
import zelimkhan.magomadov.islamicquotes.data.quote.QuoteRepositoryImpl
import zelimkhan.magomadov.islamicquotes.data.quote.local.QuoteLocalDataSource
import zelimkhan.magomadov.islamicquotes.data.wallpaper.LockScreenWallpaperGenerator
import zelimkhan.magomadov.islamicquotes.data.scheduler.SchedulerImpl
import zelimkhan.magomadov.islamicquotes.data.wallpaper.WallpaperServiceImpl
import zelimkhan.magomadov.islamicquotes.domain.quote.GetQuoteUseCase
import zelimkhan.magomadov.islamicquotes.domain.quote.QuoteRepository
import zelimkhan.magomadov.islamicquotes.domain.wallpaper.GenerateWallpaperUseCase
import zelimkhan.magomadov.islamicquotes.domain.scheduler.SetScheduledWallpaperUpdateUseCase
import zelimkhan.magomadov.islamicquotes.domain.wallpaper.SetWallpaperUseCase
import zelimkhan.magomadov.islamicquotes.domain.wallpaper.WallpaperGenerator
import zelimkhan.magomadov.islamicquotes.domain.scheduler.Scheduler
import zelimkhan.magomadov.islamicquotes.domain.wallpaper.WallpaperService
import zelimkhan.magomadov.islamicquotes.ui.receiver.WallpaperUpdateReceiver
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWallpaperService(
        @ApplicationContext context: Context,
    ): WallpaperService {
        return WallpaperServiceImpl(context)
    }

    @Provides
    @Singleton
    fun provideMediaPlayerManager(
        @ApplicationContext context: Context,
    ): MediaPlayerManager {
        return MediaPlayerManager(context)
    }

    @Provides
    @Singleton
    fun provideQuoteLocalDataSource(
        @ApplicationContext context: Context
    ): QuoteDataSource {
        return QuoteLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideQuoteRepository(
        quoteLocalDataSource: QuoteDataSource
    ): QuoteRepository {
        return QuoteRepositoryImpl(quoteLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideWallpaperBuilder(
        @ApplicationContext context: Context,
    ): WallpaperGenerator {
        return LockScreenWallpaperGenerator(context)
    }

    @Provides
    @Singleton
    fun provideWallpaperScheduler(
        @ApplicationContext context: Context
    ): Scheduler {
        return SchedulerImpl(context, WallpaperUpdateReceiver::class)
    }

    @Provides
    @Singleton
    fun provideGetQuoteUseCase(
        quoteRepository: QuoteRepository,
    ): GetQuoteUseCase {
        return GetQuoteUseCase(quoteRepository)
    }

    @Provides
    @Singleton
    fun provideGenerateWallpaperUseCase(
        getQuoteUseCase: GetQuoteUseCase,
        wallpaperGenerator: WallpaperGenerator
    ): GenerateWallpaperUseCase {
        return GenerateWallpaperUseCase(getQuoteUseCase, wallpaperGenerator)
    }

    @Provides
    @Singleton
    fun provideSetWallpaperUseCase(
        wallpaperService: WallpaperService,
        generateWallpaperUseCase: GenerateWallpaperUseCase
    ): SetWallpaperUseCase {
        return SetWallpaperUseCase(wallpaperService, generateWallpaperUseCase)
    }

    @Provides
    @Singleton
    fun provideSetScheduledWallpaperUpdateUseCase(
        scheduler: Scheduler
    ): SetScheduledWallpaperUpdateUseCase {
        return SetScheduledWallpaperUpdateUseCase(scheduler)
    }
}
