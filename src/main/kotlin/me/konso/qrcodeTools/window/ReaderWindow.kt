package me.konso.qrcodeTools.window

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamPanel
import com.github.sarxos.webcam.WebcamResolution
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.awt.FlowLayout
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import javax.swing.JFrame
import javax.swing.JTextArea

class ReaderWindow: JFrame(), Runnable, ThreadFactory {
    private val executor = Executors.newSingleThreadExecutor(this)

    private var camera: Webcam
    private var panel: WebcamPanel
    private val resultArea: JTextArea

    init {
        // JFrame Setting
        this.layout = FlowLayout()
        this.title = "QRCode Reader"
        this.setLocation(140, 200)

        // Get webcam resolution
        val size = WebcamResolution.VGA.size

        // Select webcam
        this.camera = Webcam.getWebcams().first()
        camera.viewSize = size
        panel = WebcamPanel(camera)
        panel.preferredSize = size
        panel.isFPSDisplayed = true

        // Result field
        resultArea = JTextArea()
        resultArea.isEditable = false
        resultArea.preferredSize = size

        this.add(panel)
        this.add(resultArea)
        this.pack()
    }

    fun display(){
        this.isVisible = true
        this.executor.execute(this)
    }

    override fun run() {
        while(true){
            try{
                Thread.sleep(100)
            }catch(e: Exception){
                e.printStackTrace()
            }
            if(!camera.isOpen) continue

            // Read QR code
            val image = camera.image ?: continue
            val src = BufferedImageLuminanceSource(image)
            val bitmap = BinaryBitmap(HybridBinarizer(src))

            val result =try{
                MultiFormatReader().decode(bitmap)
            }catch(e: Exception){
                null
            } ?: continue

            // Display
            resultArea.text = result.text
        }
    }

    override fun newThread(r: Runnable): Thread {
        val thread = Thread(r, "QRCODE-RUNNER")
        thread.isDaemon = true
        return thread
    }

}