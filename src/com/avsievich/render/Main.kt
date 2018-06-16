package com.avsievich.render

import com.avsievich.image.JavaImage

fun main(args: Array<String>){
    val image = JavaImage(2000, 2000, false, true)

    val start = System.currentTimeMillis()
    val model = Model("model/african_head/african_head.obj")
    Renderer.renderFilled(image, model)

    println("draw time ${System.currentTimeMillis() - start}ms")

    image.save("output.png")
}