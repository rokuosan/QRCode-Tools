package me.konso.qrcodeTools.window

import me.konso.qrcodeTools.qrcode.Capture
import me.konso.qrcodeTools.qrcode.Reader
import java.awt.FlowLayout
import java.awt.Image
import java.awt.MediaTracker
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextArea
import kotlin.random.Random

class CaptureWindow: JFrame(){

    // Global Variable
    private val tracker = MediaTracker(this)
    private val imageLabel = JLabel()
    private lateinit var desktop: ImageIcon
    private lateinit var small: Image
    private val resultArea: JTextArea

    // Static
    companion object{
        var isWindowOpen = false
    }

    // Constructor
    init{
        this.layout = FlowLayout()
        this.title = "Read from Desktop"
        this.isResizable = false
        this.addWindowListener(CaptureWindowListener())

        this.resultArea = JTextArea()
        this.resultArea.isEditable = false
        this.resultArea.columns = 40
        this.resultArea.rows = 25
        this.resultArea.lineWrap = true

        this.add(imageLabel)
        this.add(resultArea)
    }

    /**
     * ウィンドウを表示する関数
     *
     */
    fun display(){
        isWindowOpen = true

        Thread{
            while(true){
                if(!isWindowOpen) break

                this.update()
                this.imageLabel.repaint()
                this.pack()

                try{
                    Thread.sleep(33)
                }catch(e: Exception){
                    e.printStackTrace()
                }
            }
        }.start()

        this.isVisible = true
    }

    private fun update(){
        desktop = ImageIcon(Capture.getDesktopImage())
        small = desktop.image.getScaledInstance((desktop.iconWidth * 0.4).toInt(), -1, Image.SCALE_FAST)

        val r = Random.nextInt()
        tracker.addImage(small, r)
        tracker.waitForID(r)
        tracker.removeImage(small, r)

        imageLabel.icon = ImageIcon(small)

        Reader.read(Capture.getDesktopImage())?.let {
            resultArea.text = it
        }
    }
}

class CaptureWindowListener: WindowListener{
    override fun windowOpened(e: WindowEvent?) {
    }

    override fun windowClosing(e: WindowEvent?) {
        CaptureWindow.isWindowOpen = false
    }

    override fun windowClosed(e: WindowEvent?) {
    }

    override fun windowIconified(e: WindowEvent?) {
    }

    override fun windowDeiconified(e: WindowEvent?) {
    }

    override fun windowActivated(e: WindowEvent?) {
    }

    override fun windowDeactivated(e: WindowEvent?) {
    }
}