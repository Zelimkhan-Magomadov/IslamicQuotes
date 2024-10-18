package zelimkhan.magomadov.islamicquotes.domain.wallpaper

class SetWallpaperUseCase(
    private val wallpaperService: WallpaperService,
    private val generateWallpaperUseCase: GenerateWallpaperUseCase
) {
    suspend operator fun invoke() {
        val wallpaper = generateWallpaperUseCase()
        wallpaperService.setLockScreenWallpaper(wallpaper)
    }
}