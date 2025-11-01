package com.xcvi.micros

import android.content.Context
import com.xcvi.micros.license.LicensingService

class AndroidLicensingService(context: Context): LicensingService {
    override suspend fun isLicensed(): Boolean {
        return true
    }

}
