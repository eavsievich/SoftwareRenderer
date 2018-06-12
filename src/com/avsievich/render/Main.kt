package com.avsievich.render

import com.avsievich.image.JavaImage
import com.avsievich.util.argbFloat4

fun main(args: Array<String>){
    val i = JavaImage(100, 100, false, true)
    val redColor = argbFloat4(255, 255, 0, 0)
    i[55, 30] = redColor
    i.save("output.png")
}