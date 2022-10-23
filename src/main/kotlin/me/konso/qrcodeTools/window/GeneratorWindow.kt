package me.konso.qrcodeTools.window

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.konso.qrcodeTools.qrcode.Generator.makeQRCode
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.UIManager
import javax.swing.filechooser.FileFilter

@Composable
fun GeneratorWindow(){
    var text by remember { mutableStateOf("") }
    var rawImage: BufferedImage? by remember { mutableStateOf(null) }
    var image by remember { mutableStateOf( makeQRCode("Placeholder").toPainter()) }
    val focus = remember { FocusRequester() }

    Column{
        TopAppBar(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("QRCode Generator", fontWeight = FontWeight.SemiBold)
                OutlinedButton(
                    onClick = { saveDialog(rawImage) },
                    border = BorderStroke(2.dp, Color.White),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ){
                    Text("Save Image")
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    if(text.isNotEmpty()){
                        rawImage = makeQRCode(text)
                        image = rawImage!!.toPainter()
                    }
                },
                modifier = Modifier.width(200.dp)
                    .fillMaxHeight()
                    .focusRequester(focus),
                placeholder = { Text("ここにQRコードに変換したい文字列を入力してください") },
                shape = RectangleShape,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray
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

private fun saveDialog(image: BufferedImage?){
    image?:return
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val pngFilter = object: FileFilter(){
        override fun accept(f: File?): Boolean {
            f?: return false

            return f.extension == "png"
        }
        override fun getDescription() = "PNG ファイル"
    }

    val chooser = JFileChooser()
    chooser.addChoosableFileFilter(pngFilter)
    val selected = chooser.showSaveDialog(null)

    if(selected != JFileChooser.APPROVE_OPTION) return

    val selectedFile = chooser.selectedFile
    try{
        val path =  if(selectedFile.extension == ""){
            "${selectedFile.absolutePath}.png"
        }else{
            selectedFile.absolutePath
        }

        ImageIO.write(image, "png", File(path))
    }catch(e: Exception){
        e.printStackTrace()
        return
    }

}