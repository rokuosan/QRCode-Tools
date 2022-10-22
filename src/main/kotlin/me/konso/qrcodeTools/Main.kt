package me.konso.qrcodeTools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import me.konso.qrcodeTools.window.CaptureWindow
import me.konso.qrcodeTools.window.GeneratorWindow
import me.konso.qrcodeTools.window.ReaderWindow

fun main() = application{
    Window(
        onCloseRequest = ::exitApplication,
        title = "QRCode Tools",
        state = rememberWindowState(
            width = 300.dp, height = 350.dp,
            position = WindowPosition(Alignment.Center)
        )
    ){
        MaterialTheme {
            TopAppBar(
                modifier = Modifier.fillMaxWidth()
                    .then(Modifier.height(45.dp)),
            ) {
                Text("QRCode Tools")
            }

            Column(
                Modifier.fillMaxSize().padding(PaddingValues(top=80.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {

                val buttons: Map<String, ()-> Unit> = mapOf(
                    Store.OPEN_GENERATOR to { GeneratorWindow() },
                    Store.OPEN_CAMERA to { ReaderWindow().display() },
                    Store.OPEN_CAPTURE to { CaptureWindow().display() }
                )

                for(b in buttons){
                    Button(
                        onClick = {
                            if(!Store.isOpenWindows[b.key]!!) b.value()

                            Store.isOpenWindows += b.key to true
                        },
                        enabled = !Store.isOpenWindows[b.key]!!
                    ){
                        Text(b.key)
                    }
                }
            }

        }
    }
}