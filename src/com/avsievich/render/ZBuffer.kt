package com.avsievich.render

import java.util.*

class ZBuffer(private val width: Int, private val height: Int) {

    private val buffer = FloatArray(width * height) { -Float.MAX_VALUE }

    operator fun set(x: Int, y: Int, z: Float) {
        val i = x + y * width
        if (i < 0 || i >= buffer.size) {
            return
        }
        buffer[i] = z
    }

    operator fun get(x: Int, y: Int): Float {
        val i = x + y * width
        if (i < 0 || i >= buffer.size) {
            return -Float.MAX_VALUE
        }
        return buffer[i]
    }

    fun clear() {
        Arrays.fill(buffer, -Float.MAX_VALUE)
    }
}