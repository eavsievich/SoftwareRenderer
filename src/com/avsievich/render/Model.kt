package com.avsievich.render

import com.avsievich.util.FastReader
import com.curiouscreature.kotlin.math.Float2
import com.curiouscreature.kotlin.math.Float3
import java.io.FileInputStream
import java.util.*
import kotlin.collections.ArrayList

class Model(private val filename: String) {

    private val modelVerts = ArrayList<Float3>()
    private val modelFaces = ArrayList<ArrayList<IntArray>>() // this IntArray means vertex/uv/normal
    private val modelFacesVert = ArrayList<IntArray>()
    private val modelNorms = ArrayList<Float3>()
    private val modelUv = ArrayList<Float2>()

    val verts = Collections.unmodifiableList(modelVerts)
    val faces = Collections.unmodifiableList(modelFaces)
    val facesVert = Collections.unmodifiableList(modelFacesVert)
    val norms = Collections.unmodifiableList(modelNorms)
    val uv = Collections.unmodifiableList(modelUv)

    init {
        parse()
        warmUpFacesVert()
        println("$filename v# ${modelVerts.size}, vn# ${modelNorms.size}, vt# ${modelUv.size}, f# ${modelFaces.size}, fv# ${modelFacesVert.size}")
    }

    private fun parse() {
        val r = FastReader(FileInputStream(filename))
        while (r.hasNext()) {
            when (r.string()) {
                "v" -> modelVerts.add(r.float3())
                "vn" -> modelNorms.add(r.float3())
                "vt" -> modelUv.add(r.float2())
                "f" -> {
                    val f = ArrayList<IntArray>()
                    while (r.tokenizer().hasMoreTokens()) {
                        val facesTokenizer = StringTokenizer(r.string(), "/")
                        f.add(IntArray(3, { facesTokenizer.nextToken().toInt() - 1 }))
                    }
                    modelFaces.add(f)
                }
            }
        }
        r.close()
    }

    private fun warmUpFacesVert() {
        modelFaces.forEach { f ->
            modelFacesVert.add(IntArray(f.size, { i -> f[i][0] }))
        }
    }
}