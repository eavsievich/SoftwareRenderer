package com.avsievich.render

import com.avsievich.util.toColorInt
import com.curiouscreature.kotlin.math.Float4
import java.util.*

class Image(val width: Int,
            val height: Int,
            private val flipVertical: Boolean = false,
            private val pixels: IntArray = IntArray(width * height)) {

    operator fun set(x: Int, y: Int, color: Float4) {
        this[x, y] = color.toColorInt()
    }

    operator fun set(x: Int, y: Int, color: Int) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return
        }
        if (flipVertical) {
            pixels[pixels.size - (y + 1) * width + x] = color
        } else {
            pixels[y * width + x] = color
        }
    }

    operator fun get(x: Int, y: Int): Int {
        return if (flipVertical) {
            pixels[pixels.size - (y + 1) * width + x]
        } else {
            pixels[y * width + x]
        }
    }

    fun rawPixels() = pixels

    fun clear() {
        Arrays.fill(pixels, 0)
    }
}
