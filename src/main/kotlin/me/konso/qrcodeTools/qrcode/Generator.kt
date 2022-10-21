package me.konso.qrcodeTools.qrcode

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.awt.image.BufferedImage
import java.util.*

object Generator {
    private val writer = QRCodeWriter()

    fun makeQRCode(text: String, width: Int = 512, height: Int = 512): BufferedImage{
        val hints: MutableMap<EncodeHintType, Any> = EnumMap(EncodeHintType::class.java)
        hints[EncodeHintType.ERROR_CORRECTION]=ErrorCorrectionLevel.L
        hints[EncodeHintType.CHARACTER_SET]=Charsets.UTF_8.displayName()
        hints[EncodeHintType.MARGIN]=4

        return MatrixToImageWriter.toBufferedImage(writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints))
    }
}