package me.konso.qrcodeTools.window

import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame

class MainWindow: JFrame(), ActionListener {

    private val openReaderButton = JButton("Open Reader")
    private val openGeneratorButton = JButton("Open Generator")
    private val readerWindow = ReaderWindow()
    
    init {
        this.layout = FlowLayout()
        this.title = "QRCode Tools"
        this.isResizable = false
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setLocation(100, 100)

        openReaderButton.addActionListener(this)
        openGeneratorButton.addActionListener(this)

        this.add(openReaderButton)
        this.add(openGeneratorButton)

        this.pack()
        this.isVisible = true
    }

    override fun actionPerformed(e: ActionEvent?) {
        if(e == null) return
        when(e.source) {
            this.openGeneratorButton -> GeneratorWindow()
            this.openReaderButton -> readerWindow.display()
        }
    }


}