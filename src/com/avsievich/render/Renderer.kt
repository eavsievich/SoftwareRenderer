package com.avsievich.render

import com.avsievich.image.Image
import com.avsievich.util.argb
import com.curiouscreature.kotlin.math.Float3
import com.curiouscreature.kotlin.math.cross
import com.curiouscreature.kotlin.math.dot
import com.curiouscreature.kotlin.math.normalize

/**
 * Not thread safe
 */
object Renderer {

    private val lightDir = Float3(0f, 0f, -1f)

    fun renderFilled(image: Image, model: Model) {
        model.facesVert.forEach { face ->
            // Screen coordinates
            val x0 = toScreenWidth(model.verts[face[0]].x, image.width)
            val y0 = toScreenWidth(model.verts[face[0]].y, image.height)
            val x1 = toScreenWidth(model.verts[face[1]].x, image.width)
            val y1 = toScreenWidth(model.verts[face[1]].y, image.height)
            val x2 = toScreenWidth(model.verts[face[2]].x, image.width)
            val y2 = toScreenWidth(model.verts[face[2]].y, image.height)

            // Illumination based on world coordinates
            val triangleA = model.verts[face[2]] - model.verts[face[0]]
            val triangleB = model.verts[face[1]] - model.verts[face[0]]
            val n = normalize(cross(triangleA, triangleB))
            val intensity = dot(n, lightDir)

            if (intensity > 0) {
                image.triangle(x0, y0, x1, y1, x2, y2, argb(1f, intensity, intensity, intensity))
            }
        }
    }

    private fun toScreenWidth(c: Float, imageDimension: Int) =
            ((c + 1f) * imageDimension / 2f)

}