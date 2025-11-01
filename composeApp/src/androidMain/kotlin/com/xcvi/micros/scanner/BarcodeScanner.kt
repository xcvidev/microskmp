package com.xcvi.micros.scanner

import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.common.InputImage
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual fun createBarcodeScanner(): BarcodeScanner = AndroidBarcodeScanner()

class AndroidBarcodeScanner : BarcodeScanner {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            com.google.mlkit.vision.barcode.common.Barcode.FORMAT_ALL_FORMATS
        )
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    override suspend fun scanFrame(
        imageData: ByteArray,
        width: Int,
        height: Int,
        rotation: Int
    ): List<String> = suspendCoroutine { cont ->
        val inputImage = InputImage.fromByteArray(
            imageData,
            width,
            height,
            rotation,
            InputImage.IMAGE_FORMAT_NV21
        )

        scanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                cont.resume(barcodes.mapNotNull { it.rawValue })
            }
            .addOnFailureListener { cont.resumeWithException(it) }
    }
}
