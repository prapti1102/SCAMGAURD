package com.example.whatsappsecurity

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager


class FloatingBubbleService : Service() {

    private lateinit var windowManager: WindowManager
    private var bubbleView: View? = null

    companion object {
        var latestMessage: String = ""
        var latestScanResult: VirusScanManager.ScanResult = VirusScanManager.ScanResult.SAFE

        fun updateLatestScan(message: String, result: VirusScanManager.ScanResult) {
            latestMessage = message
            latestScanResult = result
        }
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        addBubble()
    }

    private fun addBubble() {
        val inflater = LayoutInflater.from(this)
        bubbleView = inflater.inflate(R.layout.bubble_layout, null)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        bubbleView?.setOnClickListener {
            AlertDialogHelper.showAlert(
                context = this,
                title = "WhatsApp Security Alert",
                message = when (latestScanResult) {
                    VirusScanManager.ScanResult.SAFE -> "Safe Message:\n$latestMessage"
                    VirusScanManager.ScanResult.MALICIOUS -> "Malicious Content Detected!\n$latestMessage"
                    VirusScanManager.ScanResult.UNKNOWN -> "Unknown Content:\n$latestMessage"
                },
                onBlock = {
                    // fake number for now
                    BlockManager.blockNumber(this, "BlockedNumberPlaceholder")
                },
                onReport = {
                    // Just log/report
                },
                onCancel = {}
            )
        }

        windowManager.addView(bubbleView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bubbleView != null) windowManager.removeView(bubbleView)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
