// VirusScanManager.kt
package com.example.whatsappsecurity

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

object VirusScanManager {

    private const val API_KEY = "8a595d1a251610acc46c782055389010e60b8a54d6a9f2fadb0e3ac7df2eed5f"  // <- Replace here

    suspend fun scanTextOrUrl(text: String): ScanResult {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val url = "https://www.virustotal.com/api/v3/urls"

                val encodedUrl = java.net.URLEncoder.encode(text, "UTF-8")

                val request = Request.Builder()
                    .url("$url/$encodedUrl")
                    .addHeader("x-apikey", API_KEY)
                    .build()

                val response: Response = client.newCall(request).execute()
                val body = response.body?.string()

                if (!response.isSuccessful || body == null) {
                    return@withContext ScanResult.SAFE
                }

                val json = JSONObject(body)
                val maliciousCount = json
                    .optJSONObject("data")
                    ?.optJSONObject("attributes")
                    ?.optJSONObject("last_analysis_stats")
                    ?.optInt("malicious", 0) ?: 0

                if (maliciousCount > 0) {
                    return@withContext ScanResult.MALICIOUS
                } else {
                    return@withContext ScanResult.SAFE
                }
            } catch (e: Exception) {
                Log.e("VirusScanManager", "Error: ${e.message}")
                return@withContext ScanResult.UNKNOWN
            }
        }
    }

    enum class ScanResult {
        SAFE, MALICIOUS, UNKNOWN
    }
}
