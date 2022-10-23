package me.konso.qrcodeTools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamPanel
import com.github.sarxos.webcam.WebcamResolution

class AppResources(
    val webcamPanel: WebcamPanel
)

val LocalAppResources = compositionLocalOf<AppResources> {
    error("No resources are provided.")
}

@Composable
fun rememberAppResources(): AppResources{
    val camera = Webcam.getDefault()
    camera.viewSize = WebcamResolution.VGA.size
    val panel =  WebcamPanel(camera)
    panel.isDisplayDebugInfo = true
    panel.preferredSize = WebcamResolution.VGA.size
    panel.isFPSDisplayed = true

    return remember {
        AppResources(
            webcamPanel = panel
        )
    }
}