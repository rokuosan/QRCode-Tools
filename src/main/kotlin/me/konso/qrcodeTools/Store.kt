package me.konso.qrcodeTools

object Store{
    const val OPEN_GENERATOR = "Open Generator"
    const val OPEN_CAMERA = "Open Camera"
    const val OPEN_CAPTURE = "Open Capture"

    val isOpenWindows = mutableMapOf(
        OPEN_GENERATOR to false,
        OPEN_CAMERA to false,
        OPEN_CAPTURE to false
    )
}