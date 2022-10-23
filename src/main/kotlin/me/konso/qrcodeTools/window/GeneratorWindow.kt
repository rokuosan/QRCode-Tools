package me.konso.qrcodeTools.window

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import me.konso.qrcodeTools.qrcode.Generator.makeQRCode
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO

@Composable
fun GeneratorWindow(){
    var text by remember { mutableStateOf("") }
    var image by remember { mutableStateOf( makeQRCode("Placeholder").toPainter()) }
    val focus = remember { FocusRequester() }

    Column{
        TopAppBar{
            Text("QRCode Generator")
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    if(text.isNotEmpty()){
                        image = makeQRCode(text).toPainter()
                    }
                },
                modifier = Modifier.width(200.dp)
                    .fillMaxHeight()
                    .focusRequester(focus),
                placeholder = { Text("ここにQRコードに変換したい文字列を入力してください") },
                shape = RectangleShape,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent
                )
            )

            LaunchedEffect(Unit){
                focus.requestFocus()
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ){
                Image(
                    image,
                    "",
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

private fun saveImage(image: BufferedImage?){
    image?:return

    try{
        // 存在しないファイル名を取る
        var tag = 0
        while(true){
            if(!Files.exists(Paths.get("code-$tag.png"))) break
            tag++
        }

        // 出力
        ImageIO.write(image, "png", File("code-$tag.png"))
    }catch (e: Exception){
        e.printStackTrace()
    }
}