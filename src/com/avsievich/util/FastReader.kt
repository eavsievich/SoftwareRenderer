package com.avsievich.util

import com.curiouscreature.kotlin.math.Float2
import com.curiouscreature.kotlin.math.Float3
import com.curiouscreature.kotlin.math.Float4
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.StringTokenizer

internal class FastReader(stream: InputStream) {

    private val reader: BufferedReader = BufferedReader(InputStreamReader(stream), 32768)
    private var tokenizer: StringTokenizer? = null

    fun hasNext(): Boolean {
        if (tokenizer != null && tokenizer!!.hasMoreTokens()) {
            return true
        }
        var nextString: String?
        do {
            nextString = reader.readLine()
            if (nextString != null) {
                tokenizer = StringTokenizer(nextString)
            } else {
                return false
            }
        } while (!tokenizer!!.hasMoreTokens())

        return true
    }

    fun tokenizer() = tokenizer!!

    fun string(): String {
        while (tokenizer == null || !tokenizer!!.hasMoreTokens()) {
            try {
                tokenizer = StringTokenizer(reader.readLine())
            } catch (e: IOException) {
                throw RuntimeException(e)
            }

        }
        return tokenizer!!.nextToken()
    }

    fun close() {
        reader.close()
    }

    fun int() = string().toInt()

    fun float() = string().toFloat()

    fun float2() = Float2(float(), float())

    fun float3() = Float3(float(), float(), float())

    fun float4() = Float4(float(), float(), float(), float())
}
