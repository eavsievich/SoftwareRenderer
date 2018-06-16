package com.avsievich.render

class ZBuffer(private val width: Int, private val height: Int) {

    private val buffer = FloatArray(width * height) { -Float.MAX_VALUE }

    operator fun set(x: Int, y: Int, z: Float) {
        if (x + y * width >= buffer.size) {
            return
        }
        buffer[x + y * width] = z
    }

    operator fun get(x: Int, y: Int): Float {
        if (x + y * width >= buffer.size) {
            return -Float.MAX_VALUE
        }
        return buffer[x + y * width]
    }
}