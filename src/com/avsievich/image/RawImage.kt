package com.avsievich.image

abstract class RawImage(override val width: Int,
                        override val height: Int,
                        override val hasAlpha: Boolean = true,
                        override val flipVertical: Boolean = false) : Image {

    protected val pixels = IntArray(width * height)

    override fun set(x: Int, y: Int, color: Int) {
        if (flipVertical) {
            pixels[pixels.size - (y + 1) * width + x] = color
        } else {
            pixels[y * width + x] = color
        }
    }

    override fun get(x: Int, y: Int): Int {
        return if (flipVertical) {
            pixels.size - (y + 1) * width + x
        } else {
            pixels[y * width + x]
        }
    }

}