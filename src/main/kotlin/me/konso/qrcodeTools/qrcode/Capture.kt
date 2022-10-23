package me.konso.qrcodeTools.qrcode

import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage

object Capture {
    fun getDesktopImage(): BufferedImage?{
        return try{
            Robot().createScreenCapture(Rectangle(Toolkit.getDefaultToolkit().screenSize))
        }catch(e: Exception){
            e.printStackTrace()
            null
        }
    }
}