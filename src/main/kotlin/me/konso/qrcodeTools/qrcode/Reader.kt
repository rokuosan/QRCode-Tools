package me.konso.qrcodeTools.qrcode

import com.github.sarxos.webcam.Webcam
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.awt.image.BufferedImage

class Reader(
    private val camera: Webcam
) {
    private lateinit var image: BufferedImage

    fun read(): String?{
        if(!camera.isOpen) return null

        // Get image
        image = camera.image?:return null

        // Create bitmap
        val src = BufferedImageLuminanceSource(image)
        val bitmap = BinaryBitmap(HybridBinarizer(src))

        return try{
            MultiFormatReader().decode(bitmap).text
        }catch(e: Exception){
            null
        }
    }
}