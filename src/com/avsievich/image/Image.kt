package com.avsievich.image

import com.avsievich.util.toColorInt
import com.curiouscreature.kotlin.math.Float4

interface Image {

    val width: Int

    val height: Int

    val hasAlpha: Boolean

    val flipVertical: Boolean

    operator fun set(x: Int, y: Int, color: Int)

    operator fun set(x: Int, y: Int, color: Float4) {
        this.set(x, y, color.toColorInt())
    }

    operator fun get(x: Int, y: Int): Int

    fun save(name: String)
}