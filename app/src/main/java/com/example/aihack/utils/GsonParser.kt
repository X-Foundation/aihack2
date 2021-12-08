package com.example.aihack.utils

import com.example.aihack.models.Test
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.io.FileReader

class GsonParser {
    companion object {
        fun getTest(level: Int, test: Int): String? {
            val gson = Gson()
            val reader = JsonReader(FileReader(""))
            val tests = gson.fromJson<List<Test>>(reader, Test::class.java)
            for (t in tests) {
                if (t.level == level && t.test == test) {
                    return t.text
                }
            }
            return null
        }
    }
}