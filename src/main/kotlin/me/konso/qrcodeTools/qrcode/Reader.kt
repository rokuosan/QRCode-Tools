package me.konso.qrcodeTools.qrcode

import com.github.sarxos.webcam.Webcam
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.awt.image.BufferedImage
import java.util.*

object Reader{

    fun read(camera: Webcam): String?{
        if(!camera.isOpen) return null

        // Get image
        camera.image?:return null
        return this.read(camera.image)
    }

    fun read(image: BufferedImage?): String?{
        image?:return null

        // Create bitmap
        val src = BufferedImageLuminanceSource(image)
        val bitmap = BinaryBitmap(HybridBinarizer(src))
        val hints: MutableMap<DecodeHintType, Any> = EnumMap(DecodeHintType::class.java)
        hints[DecodeHintType.CHARACTER_SET]=Charsets.UTF_8.displayName()

        return try{
            MultiFormatReader().decode(bitmap, hints).text
        }catch(e: Exception){
            null
        }
    }
}