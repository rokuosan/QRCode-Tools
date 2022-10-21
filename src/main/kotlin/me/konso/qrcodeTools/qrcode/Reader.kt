package me.konso.qrcodeTools.qrcode

import com.github.sarxos.webcam.Webcam
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.awt.image.BufferedImage

object Reader{
    private lateinit var image: BufferedImage

    fun read(camera: Webcam): String?{
        if(!camera.isOpen) return null

        // Get image
        image = camera.image?:return null
        return this.read(image)
    }

    fun read(image: BufferedImage): String?{
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