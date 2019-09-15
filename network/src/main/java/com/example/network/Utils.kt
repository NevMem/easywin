package com.example.network

import kotlin.random.Random

class Utils {
    companion object {
        fun createRandomString(): String {
            var result = ""
            val random = Random(0)
            for (i in 0 .. 30) {
                result += random.nextInt(0, 10).toString()
            }
            return result
        }
    }
}