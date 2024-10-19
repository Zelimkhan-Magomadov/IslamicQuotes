package zelimkhan.magomadov.islamicquotes.ui.background

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import zelimkhan.magomadov.islamicquotes.ui.background.components.PickColorDialog
import zelimkhan.magomadov.islamicquotes.ui.background.components.crop.CropImage
import zelimkhan.magomadov.islamicquotes.ui.background.components.crop.rememberCropController
import zelimkhan.magomadov.islamicquotes.ui.core.getBitmap
import zelimkhan.magomadov.islamicquotes.ui.theme.IslamicQuotesTheme

@Composable
fun BackgroundScreen() {
    val viewModel: BackgroundViewModel = hiltViewModel()
    val backgroundScreenState by viewModel.state.collectAsStateWithLifecycle()

    BackgroundScreenContent(
        state = backgroundScreenState,
        onIntent = viewModel::processIntent
    )
}

@Composable
fun BackgroundScreenContent(
    modifier: Modifier = Modifier,
    state: BackgroundState = BackgroundState(),
    onIntent: (BackgroundIntent) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val cropController = rememberCropController()
    var showColorPickDialog by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color.White) }
    var imagePicked by remember { mutableStateOf(false) }

    val pickImageLauncher = pickImageLauncher(
        context = context,
        onResult = {
            imagePicked = true
            onIntent(BackgroundIntent.LoadImage(it))
        }
    )

    val permissionResultLauncher = permissionResultLauncher(
        onGranted = { onIntent(BackgroundIntent.LoadLockScreenWallpaper) },
        onNotGranted = {
            Toast.makeText(context, "Пожалуйста, предоставьте разрешение", Toast.LENGTH_LONG).show()
        }
    )

    if (showColorPickDialog) {
        PickColorDialog(
            onDismiss = { showColorPickDialog = false },
            initialColor = selectedColor,
            onColorSelected = { color ->
                selectedColor = color
                onIntent(BackgroundIntent.SetColorBackground(color.toArgb()))
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .then(if (showColorPickDialog) Modifier.blur(12.dp) else Modifier)
    ) {
        CropImage(
            modifier = Modifier.fillMaxSize(),
            image = state.quoteBackground,
            controller = cropController
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { showColorPickDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF737272)),
            ) {
                Text(text = "Выбрать цвет")
            }

            Button(
                onClick = { pickImageLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF737272)),
            ) {
                Text(text = "Выбрать фото")
            }

            Button(
                onClick = {
                    checkManageExternalStoragePermission(
                        context = context,
                        permissionResultLauncher = permissionResultLauncher,
                        onGranted = { onIntent(BackgroundIntent.LoadLockScreenWallpaper) }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF737272)),
            ) {
                Text(text = "Использовать мои обои")
            }

            if (imagePicked) {
                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            cropController.cropImage()?.let {
                                onIntent(BackgroundIntent.SetImageBackground(it))
                                imagePicked = false
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF737272)),
                ) {
                    Text(text = "Выбрать этот фрагмент")
                }
            }
        }
    }
}

@Composable
private fun pickImageLauncher(
    context: Context,
    onResult: (Bitmap) -> Unit
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent(),
    onResult = { uri -> uri.getBitmap(context)?.let { onResult(it) } }
)

@Composable
private fun permissionResultLauncher(
    onGranted: () -> Unit,
    onNotGranted: () -> Unit
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission(),
    onResult = { isGranted -> if (isGranted) onGranted() else onNotGranted() }
)

private fun checkManageExternalStoragePermission(
    context: Context,
    permissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>,
    onGranted: () -> Unit
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        permissionResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        return
    }

    when (Environment.isExternalStorageManager()) {
        true -> onGranted()
        false -> Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).also {
            it.data = Uri.parse("package:${context.packageName}")
            context.startActivity(it)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    IslamicQuotesTheme {
        Surface {
            BackgroundScreenContent()
        }
    }
}