package com.avsievich.render

import com.curiouscreature.kotlin.math.Float3
import com.curiouscreature.kotlin.math.Float4
import com.curiouscreature.kotlin.math.Mat4

class Projection {

    private val mat4 = Mat4()
    private val opFloat4 = Float4()

    init {
        distance(1f)
    }

    fun distance(distance: Float) {
        mat4[2, 3] = -1f / distance
    }

    fun apply(coords: Float3): Float3 {
        opFloat4.xyz = coords
        opFloat4.w = 1f

        val res = mat4 * opFloat4
        res.x /= res.w
        res.y /= res.w
        res.z /= res.w
        return res.xyz
    }
}