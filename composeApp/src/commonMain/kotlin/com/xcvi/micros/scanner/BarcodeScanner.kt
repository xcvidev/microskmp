package com.xcvi.micros.scanner


interface BarcodeScanner {
    /**
     * Scan a single frame.
     * @param imageData - raw camera image
     * @param width - image width
     * @param height - image height
     * @param rotation - rotation degrees (0, 90, 180, 270)
     */
    suspend fun scanFrame(imageData: ByteArray, width: Int, height: Int, rotation: Int = 0): List<String>
}


expect fun createBarcodeScanner(): BarcodeScanner
