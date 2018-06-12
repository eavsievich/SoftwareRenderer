package com.avsievich.util

import com.curiouscreature.kotlin.math.Float3
import com.curiouscreature.kotlin.math.Float4

fun argb(alpha: Int, red: Int, green: Int, blue: Int) =
        alpha shl 24 or (red shl 16) or (green shl 8) or blue

fun argb(alpha: Float, red: Float, green: Float, blue: Float) =
                (alpha * 255.0f + 0.5f).toInt() shl 24 or
                ((red * 255.0f + 0.5f).toInt() shl 16) or
                ((green * 255.0f + 0.5f).toInt() shl 8) or
                (blue * 255.0f + 0.5f).toInt()

fun argbFloat4(alpha: Int, red: Int, green: Int, blue: Int) = Float4(
        red.toFloat() / 255f,
        green.toFloat() / 255f,
        blue.toFloat() / 255f,
        alpha.toFloat() / 255f)

fun rgb(red: Int, green: Int, blue: Int) =
        -0x1000000 or (red shl 16) or (green shl 8) or blue

fun rgb(red: Float, green: Float, blue: Float) =
        -0x1000000 or
                ((red * 255.0f + 0.5f).toInt() shl 16) or
                ((green * 255.0f + 0.5f).toInt() shl 8) or
                (blue * 255.0f + 0.5f).toInt()

fun rgbFloat3(red: Int, green: Int, blue: Int) = Float3(
        red.toFloat() / 255f,
        green.toFloat() / 255f,
        blue.toFloat() / 255f)

fun Int.alpha() = this.ushr(24)

fun Int.red() = this shr 16 and 0xFF

fun Int.green() = this shr 8 and 0xFF

fun Int.blue() = this and 0xFF

fun Float4.toColorInt() = argb(a, r, g, b)

fun Float3.toColorInt() = rgb(r, g, b)

fun Int.toColorFloat4() = Float4(
        this.red().toFloat() / 255f,
        this.green().toFloat() / 255f,
        this.blue().toFloat() / 255f,
        this.alpha().toFloat() / 255f)

fun Int.toColorFloat3() = Float3(
        this.red().toFloat() / 255f,
        this.green().toFloat() / 255f,
        this.blue().toFloat() / 255f)