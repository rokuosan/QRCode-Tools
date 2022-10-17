package me.konso.qrcodeTools.qrcode

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter

class Generator {
    private val writer = QRCodeWriter()

    fun makeQRCode(text: String, width: Int = 512, height: Int = 512) =
        MatrixToImageWriter.toBufferedImage(writer.encode(text, BarcodeFormat.QR_CODE, width, height))
}