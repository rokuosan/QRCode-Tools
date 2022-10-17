package me.konso.qrcodeTools.window

import me.konso.qrcodeTools.qrcode.Generator
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
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

    init {
        this.layout = FlowLayout()
        this.title = "QRCode Generator"
        this.setLocation(200, 200)

        // Controller
        val container = JPanel()
        container.layout = BorderLayout()

        // Add EventListener
        this.generateButton.addActionListener(this)
        this.saveButton.addActionListener(this)

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
            val image = Generator().makeQRCode(inputArea.text)
            this.outputImagePanel.icon = ImageIcon(image)
        }catch(e: Exception){
            e.printStackTrace()
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        if(e == null) return

        when(e.source){
            this.generateButton -> this.updateQRCode()
            this.saveButton -> {

            }
        }
    }
}