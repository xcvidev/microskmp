package com.xcvi.micros

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform