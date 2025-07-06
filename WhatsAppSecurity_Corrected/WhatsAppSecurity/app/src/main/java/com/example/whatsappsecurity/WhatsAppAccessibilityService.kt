// WhatsAppAccessibilityService.kt
package com.example.whatsappsecurity

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WhatsAppAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
            packageNames = arrayOf("com.whatsapp")
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        }
        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.packageName == "com.whatsapp" && event.eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            val notificationText = event.text.joinToString()
            Log.d("WAService", "Notification captured: $notificationText")

            // Calling the scanTextOrUrl without context argument
            CoroutineScope(Dispatchers.Main).launch {
                val scanResult = VirusScanManager.scanTextOrUrl(notificationText)  // Removed context argument
                FloatingBubbleService.updateLatestScan(notificationText, scanResult)
            }
        }
    }

    override fun onInterrupt() {}
}
