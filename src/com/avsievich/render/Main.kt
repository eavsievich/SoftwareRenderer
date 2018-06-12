package com.avsievich.render

import com.avsievich.image.JavaImage
import com.avsievich.util.RED
import com.avsievich.util.WHITE

fun main(args: Array<String>){
    val image = JavaImage(2000, 2000, false, true)
    val model = Model("model/african_head/african_head.obj")

    model.renderWireframe(image)

    image.save("output.png")
}