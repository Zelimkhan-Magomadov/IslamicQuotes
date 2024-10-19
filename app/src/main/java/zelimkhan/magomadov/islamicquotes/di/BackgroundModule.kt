package zelimkhan.magomadov.islamicquotes.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zelimkhan.magomadov.islamicquotes.data.background.BackgroundRepository
import zelimkhan.magomadov.islamicquotes.data.background.ColorBackgroundGenerator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BackgroundModule {

    @Provides
    @Singleton
    fun provideBackgroundRepository(
        @ApplicationContext context: Context,
        colorBackgroundGenerator: ColorBackgroundGenerator
    ): BackgroundRepository {
        return BackgroundRepository(
            context = context,
            colorBackgroundGenerator = colorBackgroundGenerator
        )
    }
}