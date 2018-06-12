package com.avsievich.render

import com.avsievich.image.Image
import com.avsievich.util.CYAN
import com.avsievich.util.FastReader
import com.curiouscreature.kotlin.math.Float2
import com.curiouscreature.kotlin.math.Float3
import java.io.FileInputStream
import java.util.*
import kotlin.collections.ArrayList

class Model(private val filename: String) {

    private val verts =  ArrayList<Float3>()
    private val faces = ArrayList<ArrayList<IntArray>>() // this IntArray means vertex/uv/normal
    private val facesVert = ArrayList<IntArray>()
    private val norms = ArrayList<Float3>()
    private val uv = ArrayList<Float2>()

    init {
        parse()
        warmUpFacesVert()
        println("$filename v# ${verts.size}, vn# ${norms.size}, vt# ${uv.size}, f# ${faces.size}, fv# ${facesVert.size}")
    }

    private fun parse() {
        val r = FastReader(FileInputStream(filename))
        while (r.hasNext()) {
            when (r.string()) {
                "v" -> verts.add(r.float3())
                "vn" -> norms.add(r.float3())
                "vt" -> uv.add(r.float2())
                "f" -> {
                    val f = ArrayList<IntArray>()
                    while (r.tokenizer().hasMoreTokens()) {
                        val facesTokenizer = StringTokenizer(r.string(), "/")
                        f.add(IntArray(3, { facesTokenizer.nextToken().toInt() - 1 }))
                    }
                    faces.add(f)
                }
            }
        }
        r.close()
    }

    private fun warmUpFacesVert() {
        faces.forEach { f ->
            facesVert.add(IntArray(f.size, { i -> f[i][0] }))
        }
    }

    fun renderWireframe(image: Image) {
        facesVert.forEach { face ->
            for (j in 0 until 3) {
                val v0 = verts[face[j]]
                val v1 = verts[face[(j + 1) % 3]]
                val x0 = ((v0.x + 1.0) * image.width / 2.0).toInt()
                val y0 = ((v0.y + 1.0) * image.height / 2.0).toInt()
                val x1 = ((v1.x + 1.0) * image.width / 2.0).toInt()
                val y1 = ((v1.y + 1.0) * image.height / 2.0).toInt()
                image.line(x0, y0, x1, y1, CYAN)
            }
        }
    }
}