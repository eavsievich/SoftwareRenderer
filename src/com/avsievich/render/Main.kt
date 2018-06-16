package com.avsievich.render

fun main(args: Array<String>){
    val w = 2000
    val h = 2000
    render(w, h, "african_head", "african_head_out")
    render(w, h, "diablo3_pose", "diablo3_pose_out")
}

private fun render(w: Int, h: Int, name: String, outputName: String) {
    val image = Renderer(w, h)
    val model = Model("model/$name/$name")

    val start = System.currentTimeMillis()
    image.render(model)
    println("$name render time is ${System.currentTimeMillis() - start}ms")

    image.save("$outputName.png", false)
}