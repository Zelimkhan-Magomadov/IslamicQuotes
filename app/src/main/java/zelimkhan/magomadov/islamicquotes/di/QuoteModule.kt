package zelimkhan.magomadov.islamicquotes.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zelimkhan.magomadov.islamicquotes.data.quote.QuoteDataSource
import zelimkhan.magomadov.islamicquotes.data.quote.QuoteRepository
import zelimkhan.magomadov.islamicquotes.data.quote.local.QuoteLocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class QuoteModule {

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
        return QuoteRepository(quoteLocalDataSource)
    }
}
