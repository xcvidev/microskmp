package com.xcvi.micros.license

interface LicensingService {
    suspend fun isLicensed(): Boolean
}
