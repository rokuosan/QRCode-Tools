package me.konso.qrcodeTools

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import me.konso.qrcodeTools.WindowType.CAMERA
import me.konso.qrcodeTools.WindowType.DESKTOP
import me.konso.qrcodeTools.WindowType.GENERATOR
import me.konso.qrcodeTools.WindowType.values
import me.konso.qrcodeTools.window.GeneratorWindow
import me.konso.qrcodeTools.window.ReaderWindow

enum class WindowType(
    val title: String,
    val text: String,
) {
    GENERATOR("QR Code Generator", "Open Generator"),
    CAMERA("QR Code Reader [Camera]", "Open Camera"),
    DESKTOP("QR Code Reader [Desktop]", "Open Desktop Reader")
}
fun main() = application{
    CompositionLocalProvider(LocalAppResources provides rememberAppResources()){
        AppWindow(this)
    }
}

@Composable
fun AppWindow(app: ApplicationScope) {
    val windows=values().toList()
    val isOpen=remember { mutableStateMapOf<String, Boolean>() }

    // Initialize state
    for(w in windows) {
        isOpen+=w.name to false
    }

    Window(
        title="QRCode Tools",
        onCloseRequest={ app.exitApplication() },
        resizable = false,
        state=WindowState(
            size = DpSize(600.dp, 200.dp),
            placement = WindowPlacement.Floating,
            position=WindowPosition(Alignment.Center)
        )
    ) {
        Row(
            modifier=Modifier.fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalArrangement =  Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for(w in windows) {
                if(w==CAMERA) continue

                Button(
                    onClick={ isOpen[w.name]=true },
                    enabled=!isOpen[w.name]!!
                ) {
                    Text(w.text)
                }

                if(isOpen[w.name]!!) {
                    Window(
                        onCloseRequest={
                            isOpen[w.name]=false
                        },
                        title=w.title,
                        resizable=false
                    ) {
                        when(w){
                            GENERATOR -> GeneratorWindow()
                            DESKTOP -> TODO()
                            else -> {}
                        }
                    }
                }
            }

            Button(
                onClick={ isOpen[CAMERA.name]=true },
                enabled=!isOpen[CAMERA.name]!!
            ) {
                Text(CAMERA.text)
            }

            if(isOpen[CAMERA.name]!!) {
                Window(
                    onCloseRequest={
                        isOpen[CAMERA.name]=false
                    },
                    title=CAMERA.title,
                    resizable=false,
                ) {
                    ReaderWindow()
                }
            }
        }
    }
}