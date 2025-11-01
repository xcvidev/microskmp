package com.xcvi.micros.scanner

class BarcodeUseCase {
    private val scanner: BarcodeScanner = createBarcodeScanner()

    suspend fun scanImage(imageData: ByteArray, width: Int, height: Int): List<String> {
        return scanner.scanFrame(imageData, width, height)
    }
}
