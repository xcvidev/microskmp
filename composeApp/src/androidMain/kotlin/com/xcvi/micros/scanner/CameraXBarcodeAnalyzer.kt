// androidMain/src/main/kotlin/barcode/CameraXBarcodeAnalyzer.kt
package com.xcvi.micros.scanner

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import android.media.Image
import java.nio.ByteBuffer

class CameraXBarcodeAnalyzer(
    private val scanner: BarcodeScanner,
    private val onResult: (List<String>) -> Unit
) : ImageAnalysis.Analyzer {

    private val scope = CoroutineScope(Dispatchers.Main)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            // Convert Image to ByteArray (NV21 format)
            val nv21 = mediaImage.toByteArrayNV21() // we'll implement this helper
            val rotation = imageProxy.imageInfo.rotationDegrees

            scope.launch {
                try {
                    val result = scanner.scanFrame(nv21, mediaImage.width, mediaImage.height, rotation)
                    if (result.isNotEmpty()) onResult(result)
                } catch (e: Exception) {
                    Log.e("BarcodeAnalyzer", "Scan failed: ${e.localizedMessage}")
                } finally {
                    imageProxy.close()
                }
            }
        } else {
            imageProxy.close()
        }
    }
}



fun Image.toByteArrayNV21(): ByteArray {
    val yBuffer = planes[0].buffer
    val uBuffer = planes[1].buffer
    val vBuffer = planes[2].buffer

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)

    yBuffer.get(nv21, 0, ySize)

    val chromaOffset = ySize
    var i = 0
    while (uBuffer.hasRemaining() && vBuffer.hasRemaining()) {
        nv21[chromaOffset + i++] = vBuffer.get()
        nv21[chromaOffset + i++] = uBuffer.get()
    }

    return nv21
}
