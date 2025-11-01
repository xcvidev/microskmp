package com.xcvi.micros.license

import android.content.Context
import android.os.Build
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityTokenRequest
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.util.UUID
import java.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AndroidLicensingService(
    private val context: Context
) : LicensingService {

    override suspend fun isLicensed(): Boolean = withContext(Dispatchers.IO) {
        try {
            val integrityManager = IntegrityManagerFactory.create(context)
            val response = integrityManager.requestIntegrityToken(
                IntegrityTokenRequest.builder()
                    .setNonce(UUID.randomUUID().toString())
                    .build()
            ).await()

            val token = response.token()
            decodeAndCheckToken(token)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun decodeAndCheckToken(token: String): Boolean {
        // Token is a JWT: header.payload.signature (all Base64)
        val parts = token.split(".")
        if (parts.size < 2) return false

        val payloadJson = try {
            val decoded: ByteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getDecoder().decode(parts[1])
            } else {
                android.util.Base64.decode(parts[1], android.util.Base64.DEFAULT)
            }
            JSONObject(String(decoded))
        } catch (e: Exception) {
            return false
        }

        val appIntegrity = payloadJson.optJSONObject("appIntegrity")
        val verdict = appIntegrity?.optString("appRecognitionVerdict")

        // The expected verdict when installed from Play Store is "PLAY_RECOGNIZED"
        return verdict == "PLAY_RECOGNIZED"
    }
}
