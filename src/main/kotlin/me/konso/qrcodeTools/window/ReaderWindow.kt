package me.konso.qrcodeTools.window

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.konso.qrcodeTools.LocalAppResources
import me.konso.qrcodeTools.qrcode.Reader

@Composable
fun ReaderWindow(){
    var text by remember { mutableStateOf("") }
    val focus = remember { FocusRequester() }
    val panel = LocalAppResources.current.webcamPanel


    Column {
        TopAppBar(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("QRCode Reader", fontWeight = FontWeight.SemiBold)
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.width(200.dp)
                    .fillMaxHeight()
                    .focusRequester(focus),
                placeholder = { Text("????????????????????????????????????") },
                shape = RectangleShape,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray
                ),
                readOnly = true
            )
            LaunchedEffect(Unit){
                focus.requestFocus()

                Thread{
                    while(true){
                        if(!panel.webcam.isOpen) break

                        text = Reader.read(panel.webcam)?:text

                        try{
                            Thread.sleep(10)
                        }catch(e: Exception){
                            e.printStackTrace()
                            break
                        }
                    }
                }.start()
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ){

                SwingPanel(
                    factory = {
                        panel
                    }
                )
            }
        }
    }
}