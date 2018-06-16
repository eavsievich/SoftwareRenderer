package com.avsievich.render

fun main(args: Array<String>){
    val image = Renderer(2000, 2000)

    val start = System.currentTimeMillis()
    val model = Model("model/african_head/african_head")
    image.render(model)

    println("draw time ${System.currentTimeMillis() - start}ms")

    image.save("output.png", false)
}