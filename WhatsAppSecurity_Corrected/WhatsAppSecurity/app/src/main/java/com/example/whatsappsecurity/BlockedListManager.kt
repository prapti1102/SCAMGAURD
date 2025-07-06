package com.example.whatsappsecurity

object BlockedListManager {
    private val blockedNumbers = mutableSetOf<String>()

    fun blockNumber(number: String) {
        blockedNumbers.add(number)
    }

    fun isBlocked(number: String): Boolean {
        return blockedNumbers.contains(number)
    }

    fun unblockNumber(number: String) {
        blockedNumbers.remove(number)
    }

    fun getAllBlocked(): Set<String> {
        return blockedNumbers
    }
}
