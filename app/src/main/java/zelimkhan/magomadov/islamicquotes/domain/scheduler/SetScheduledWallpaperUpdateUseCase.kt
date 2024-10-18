package zelimkhan.magomadov.islamicquotes.domain.scheduler

class SetScheduledWallpaperUpdateUseCase(
    private val scheduler: Scheduler
) {
    companion object {
        private const val INTERVAL_HALF_HOUR = 1_800_000L
    }

    operator fun invoke() {
        scheduler.setRepeatingExecution(INTERVAL_HALF_HOUR)
    }
}