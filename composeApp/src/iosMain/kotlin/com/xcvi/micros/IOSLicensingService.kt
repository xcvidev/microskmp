package com.xcvi.micros

import com.xcvi.micros.license.LicensingService
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.*
import kotlin.coroutines.resume

class IOSLicensingService: LicensingService {

    override suspend fun isLicensed(): Boolean = suspendCancellableCoroutine { cont ->

        val receiptUrl = NSBundle.mainBundle.appStoreReceiptURL
        if (receiptUrl == null) {
            cont.resume(false)
            return@suspendCancellableCoroutine
        }

        val receiptData = NSData.dataWithContentsOfURL(receiptUrl)
        if (receiptData == null) {
            cont.resume(false)
            return@suspendCancellableCoroutine
        }
        cont.resume(receiptData.length > 0u)
    }
}


/*
class IOSLicensingService : LicensingService {
    override suspend fun isLicensed(): Boolean {
        val receiptUrl = NSBundle.mainBundle.appStoreReceiptURL
        val receiptData = receiptUrl?.let { NSData.dataWithContentsOfURL(it) }
        return receiptData != null && receiptData.length > 0
    }
}
 */