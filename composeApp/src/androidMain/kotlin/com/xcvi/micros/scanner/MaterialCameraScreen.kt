package com.xcvi.micros.scanner

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.core.content.ContextCompat
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.ui.unit.Dp

@Composable
fun MaterialCameraScreen(
    onScan: (List<String>) -> Unit,
    width: Dp,
    height: Dp
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    val scanner = remember { createBarcodeScanner() }

    Box(modifier = Modifier.width(width).height(height)) {
        AndroidView(factory = { ctx ->
            val previewView = androidx.camera.view.PreviewView(ctx)

            val preview = Preview.Builder().build()
            val selector = CameraSelector.DEFAULT_BACK_CAMERA
            preview.setSurfaceProvider(previewView.surfaceProvider)

            val analysis = ImageAnalysis.Builder()
                .build()
            analysis.setAnalyzer(
                ContextCompat.getMainExecutor(ctx),
                CameraXBarcodeAnalyzer(scanner) { barcodes ->
                    onScan(barcodes)
                }
            )

            cameraProviderFuture.get().bindToLifecycle(
                lifecycleOwner,
                selector,
                preview,
                analysis
            )

            previewView
        })
    }
}
