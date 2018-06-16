package com.avsievich.render

import com.avsievich.util.FastReader
import com.avsievich.util.TGAReader
import com.curiouscreature.kotlin.math.Float2
import com.curiouscreature.kotlin.math.Float3
import java.io.FileInputStream
import java.util.*
import kotlin.collections.ArrayList

class Model(private val filename: String) {

    private val _polygons = ArrayList<IntArray>()
    private val _textures = ArrayList<IntArray>()
    private val _vertices = ArrayList<Float3>()
    private val _normals = ArrayList<Float3>()
    private val _textureCoords = ArrayList<Float2>()

    val size: Int
        get() = _polygons.size

    val polygons = Collections.unmodifiableList(_polygons)
    val textures = Collections.unmodifiableList(_textures)

    val vertices = Collections.unmodifiableList(_vertices)
    val textureCoords = Collections.unmodifiableList(_textureCoords)
    val normals = Collections.unmodifiableList(_normals)

    val diffuseTexture: Image

    init {
        parseObj()
        println("$filename v# ${_vertices.size}, vn# ${_normals.size}, vt# ${_textureCoords.size}, f# ${_polygons.size}")

        diffuseTexture = TGAReader.getImage("${filename}_diffuse.tga")
    }

    private fun parseObj() {

        // this IntArray means vertex/uv/normal
        val faces = ArrayList<ArrayList<IntArray>>()

        val r = FastReader(FileInputStream("$filename.obj"))
        while (r.hasNext()) {
            when (r.string()) {
                "v" -> _vertices.add(r.float3())
                "vn" -> _normals.add(r.float3())
                "vt" -> _textureCoords.add(r.float2())
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

        // Split faces
        faces.forEach { f ->
            _polygons.add(IntArray(f.size, { i -> f[i][0] }))
            _textures.add(IntArray(f.size, { i -> f[i][1] }))
        }
    }
}