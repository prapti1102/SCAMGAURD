package com.example.whatsappsecurity

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit // Import KTX edit function

object BlockManager {
    private const val PREFS_NAME = "BlockedNumbers"
    private const val BLOCKED_LIST_KEY = "blocked_list"

    // Function to block a number by adding it to SharedPreferences
    fun blockNumber(context: Context, number: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        // Get the current blocked list or create a new one if it doesn't exist
        val blocked = prefs.getStringSet(BLOCKED_LIST_KEY, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        // Add the new number to the blocked list
        blocked.add(number)
        // Save the updated blocked list using KTX extension
        prefs.edit {
            putStringSet(BLOCKED_LIST_KEY, blocked)
        }
    }
}
