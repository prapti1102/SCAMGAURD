// AlertDialogHelper.kt
package com.example.whatsappsecurity

import android.content.Context
import androidx.appcompat.app.AlertDialog

object AlertDialogHelper {

    fun showAlert(
        context: Context,
        title: String,
        message: String,
        onBlock: () -> Unit,
        onReport: () -> Unit,
        onCancel: () -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("Block") { _, _ -> onBlock() }
        builder.setNegativeButton("Report") { _, _ -> onReport() }
        builder.setNeutralButton("Cancel") { _, _ -> onCancel() }

        builder.setCancelable(false)
        builder.show()
    }
}
