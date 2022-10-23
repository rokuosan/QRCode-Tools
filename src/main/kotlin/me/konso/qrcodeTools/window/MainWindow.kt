package me.konso.qrcodeTools.window

import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.UIManager

class MainWindow: JFrame(), ActionListener {

    private val openReaderButton = JButton("Open Reader")
    private val openGeneratorButton = JButton("Open Generator")
    private val openCaptureButton = JButton("Open Desktop Capture")
    private val captureWindow = CaptureWindow()
    private val readerWindow = ReaderWindow()

    init {
        this.layout = FlowLayout()
        this.title = "QRCode Tools"
        this.isResizable = false
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setLocation(100, 100)

        openReaderButton.addActionListener(this)
        openGeneratorButton.addActionListener(this)
        openCaptureButton.addActionListener(this)

        this.add(openReaderButton)
        this.add(openGeneratorButton)
        this.add(openCaptureButton)

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        this.pack()
        this.isVisible = true
    }

    override fun actionPerformed(e: ActionEvent?) {
        if(e == null) return
        when(e.source) {
            this.openGeneratorButton -> TODO()
            this.openReaderButton -> readerWindow.display()
            this.openCaptureButton -> captureWindow.display()
        }
    }


}