package me.konso.qrcodeTools.window

import me.konso.qrcodeTools.Store
import me.konso.qrcodeTools.qrcode.Generator
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea

class GeneratorWindow: JFrame(), ActionListener {

    private val generateButton = JButton("Generate!")
    private val saveButton = JButton("Save this image")
    private val outputImagePanel = JLabel()
    private val inputArea = JTextArea()

    private var image: BufferedImage? = null

    init {
        this.layout = FlowLayout()
        this.title = "QRCode Generator"
        this.setLocation( 150 ,200)

        // Controller
        val container = JPanel()
        container.layout = BorderLayout()

        // Add EventListener
        this.generateButton.addActionListener(this)
        this.saveButton.addActionListener(this)
        this.addWindowListener(GeneratorWindowListener())

        // Input
        inputArea.tabSize = 4
        inputArea.columns = 30
        inputArea.rows = 10
        inputArea.text = "This is sample QR Code"
        container.add(BorderLayout.NORTH, inputArea)
        container.add(BorderLayout.WEST, generateButton)
        container.add(BorderLayout.EAST, saveButton)

        // Output Image
        outputImagePanel.setSize(512, 512)
        updateQRCode()

        this.add(container)
        this.add(outputImagePanel)
        this.pack()
        this.isVisible = true
    }

    private fun updateQRCode(){
        try{
            image = Generator.makeQRCode(inputArea.text)
            this.outputImagePanel.icon = ImageIcon(image)
        }catch(e: Exception){
            e.printStackTrace()
        }
    }

    private fun saveImage(image: BufferedImage?){
        image?:return

        try{
            // 存在しないファイル名を取る
            var tag = 0
            while(true){
                if(!Files.exists(Paths.get("code-$tag.png"))) break
                tag++
            }

            // 出力
            ImageIO.write(image, "png", File("code-$tag.png"))
            println("[IMAGE] Save code-$tag.png (${inputArea.text})")
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        if(e == null) return

        when(e.source){
            this.generateButton -> this.updateQRCode()
            this.saveButton -> this.saveImage(image)
        }
    }
}

class GeneratorWindowListener: WindowListener{
    override fun windowOpened(e: WindowEvent?) {
    }

    override fun windowClosing(e: WindowEvent?) {
        Store.isOpenWindows[Store.OPEN_GENERATOR] = false
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