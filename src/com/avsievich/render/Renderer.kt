package com.avsievich.render

import com.avsievich.util.*
import com.curiouscreature.kotlin.math.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

open class Renderer(val width: Int, val height: Int) {

    private val image = Image(width, height, true)
    private val zBuffer = ZBuffer(width, height)
    private val lightDir = Float3(0f, 0f, -1f)

    fun save(name: String, withAlpha: Boolean) {
        val i = BufferedImage(width, height, if (withAlpha) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_RGB)
        i.setRGB(0, 0, width, height, image.rawPixels(), 0, width)
        ImageIO.write(i, "png", File(name))
    }

    fun render(model: Model) {
        for (i in 0 until model.size) {
            val polygon = model.polygons[i]
            val texture = model.textures[i]

            // Screen coordinates
            val a = toScreenCoordinates(model.vertices[polygon[0]])
            val b = toScreenCoordinates(model.vertices[polygon[1]])
            val c = toScreenCoordinates(model.vertices[polygon[2]])

            // Illumination based on world coordinates
            val triangleA = model.vertices[polygon[2]] - model.vertices[polygon[0]]
            val triangleB = model.vertices[polygon[1]] - model.vertices[polygon[0]]
            val n = normalize(cross(triangleA, triangleB))
            val intensity = dot(n, lightDir)

            // Texture coordinates
            val ta = model.textureCoords[texture[0]]
            val tb = model.textureCoords[texture[1]]
            val tc = model.textureCoords[texture[2]]

            if (intensity > 0) {
                triangle(a, b, c, ta, tb, tc, model.diffuseTexture, intensity)
            }
        }
    }


    private fun triangle(a: Float3, b: Float3, c: Float3,
                         ta: Float2, tb: Float2, tc: Float2,
                         texture: Image, colorIntensity: Float) {

        val minY = min(a.y, min(b.y, c.y)).toInt()
        val maxY = max(a.y, max(b.y, c.y)).toInt() + 1
        val minX = min(a.x, min(b.x, c.x)).toInt()
        val maxX = max(a.x, max(b.x, c.x)).toInt() + 1

        val p = Float3()

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                p.x = x.toFloat()
                p.y = y.toFloat()

                val bc = barycentric(a, b, c, p) // barycentric coordinates of current point
                if (bc.x < 0 || bc.y < 0 || bc.z < 0) {
                    continue
                }

                // Depth
                p.z = (a.z * bc.x) + (b.z * bc.y) + (c.z * bc.z)

                // Update pixel color only if we're closer to the camera than before
                if (zBuffer[x, y] < p.z) {
                    zBuffer[x, y] = p.z

                    // Get texture color with applied intensity
                    val textureCoords = ta * bc.x + tb * bc.y + tc * bc.z
                    textureCoords.x *= texture.width
                    textureCoords.y *= texture.height
                    val color = texture[textureCoords.x.toInt(), textureCoords.y.toInt()]

                    image[x, y] = rgb(
                            (color.red() * colorIntensity).toInt(),
                            (color.green() * colorIntensity).toInt(),
                            (color.blue() * colorIntensity).toInt()
                    )
                }
            }
        }
    }

    private fun barycentric(a: Float3, b: Float3, c: Float3, p: Float3): Float3 {
        val cross = cross(
                Float3(c.x - a.x, b.x - a.x, a.x - p.x),
                Float3(c.y - a.y, b.y - a.y, a.y - p.y))

        if (abs(cross.z) < 1e-2f) {
            return Float3(-1f, 1f, 1f)
        }

        return Float3(
                1 - (cross.x + cross.y) / cross.z,
                cross.y / cross.z,
                cross.x / cross.z)
    }

    private fun toScreenCoordinates(raw: Float3) =
            Float3((raw.x + 1f) * width / 2f, (raw.y + 1f) * height / 2f, raw.z)

}