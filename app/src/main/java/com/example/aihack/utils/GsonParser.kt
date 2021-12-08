package com.example.aihack.utils

import androidx.fragment.app.FragmentActivity
import com.example.aihack.models.TestList
import com.google.gson.Gson
import com.google.gson.stream.JsonReader

class GsonParser(private val activity: FragmentActivity) {
    fun getTest(level: Int, test: Int): String? {
        val gson = Gson()
        val reader = JsonReader(activity.assets.open("tests.json").bufferedReader())
        val tests = gson.fromJson<TestList>(reader, TestList::class.java)
        for (t in tests.list) {
            if (t.level == level && t.test == test) {
                return t.text
            }
        }
        return null
    }

    fun getXp(){

    }
}