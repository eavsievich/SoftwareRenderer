package com.avsievich.image

import com.avsievich.util.toColorInt
import com.curiouscreature.kotlin.math.Float4
import kotlin.math.abs

abstract class Image(val width: Int,
                     val height: Int,
                     val hasAlpha: Boolean = true,
                     private val flipVertical: Boolean = false) {

    protected val pixels = IntArray(width * height)

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
            pixels.size - (y + 1) * width + x
        } else {
            pixels[y * width + x]
        }
    }

    fun line(fromX: Int, fromY: Int, toX: Int, toY: Int, color: Int) {
        var x0 = fromX
        var y0 = fromY
        var x1 = toX
        var y1 = toY

        var steep = false
        if (abs(x0 - x1) < abs(y0 - y1)) {
            x0 = y0.also { y0 = x0 }
            x1 = y1.also { y1 = x1 }
            steep = true
        }
        if (x0 > x1) {
            x0 = x1.also { x1 = x0 }
            y0 = y1.also { y1 = y0 }
        }

        val dx = x1 - x0
        val dy = y1 - y0
        val derror2 = abs(dy) * 2
        var error2 = 0
        var y = y0

        for (x in x0..x1) {
            if (steep) {
                set(y, x, color)
            } else {
                set(x, y, color)
            }
            error2 += derror2
            if (error2 > dx) {
                y += if (y1 > y0) 1 else -1
                error2 -= dx * 2
            }
        }
    }

    abstract fun save(name: String)
}