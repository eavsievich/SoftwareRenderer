package com.avsievich.image

import com.avsievich.util.toColorInt
import com.curiouscreature.kotlin.math.Float4
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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
            pixels[pixels.size - (y + 1) * width + x]
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

    fun triangle(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, color: Int) {
        val minY = min(y0, min(y1, y2))
        val maxY = max(y0, max(y1, y2))
        val minX = min(x0, min(x1, x2))
        val maxX = max(x0, max(x1, x2))

        for (x in minX.toInt()..maxX.toInt()) {
            for (y in minY.toInt()..maxY.toInt()) {
                if (isPointInsideTriangle(x, y, x0, y0, x1, y1, x2, y2)) {
                    this[x, y] = color
                }
            }
        }
    }

    /**
     * Some black magic from https://stackoverflow.com/a/9755252
     */
    private fun isPointInsideTriangle(x: Int, y: Int,
                                      x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float): Boolean {
        val as_x = (x - x0)
        val as_y = (y - y0)
        val s_ab = (x1 - x0) * as_y - (y1 - y0) * as_x > 0f
        if ((x2 - x0) * as_y - (y2 - y0) * as_x > 0f == s_ab) return false
        return (x2 - x1) * (y - y1) - (y2 - y1) * (x - x1) > 0f == s_ab
    }

    abstract fun save(name: String)
}
