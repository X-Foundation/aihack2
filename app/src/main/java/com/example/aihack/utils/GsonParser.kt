package com.example.aihack.utils

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.aihack.models.TestList
import com.example.aihack.models.Xp
import com.example.aihack.models.XpList
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import android.content.SharedPreferences.Editor


class GsonParser(activity: FragmentActivity) {
    private var testList: TestList
    private var xpList: XpList

    init {
        val gson = Gson()
        val reader = JsonReader(activity.assets.open("tests.json").bufferedReader())
        testList = gson.fromJson(reader, TestList::class.java)
        val sharedPref =
            activity.applicationContext.getSharedPreferences("superPrefs", Context.MODE_PRIVATE)
        val prefs = sharedPref.getString("xp", "{'list': []}").toString()
        xpList = gson.fromJson(prefs, XpList::class.java)
    }

    fun getTest(level: Int, test: Int): String? {
        for (t in testList.list) {
            if (t.level == level && t.test == test) {
                return t.text
            }
        }
        return null
    }

    fun getXp(level: Int, test: Int): Int? {
        for (t in xpList.list) {
            if (t.level == level && t.test == test) {
                return t.xp
            }
        }
        return null
    }

    fun getAllXp(): Int {
        var counter = 0
        for (t in xpList.list) {
            counter += t.xp
        }
        return counter
    }

    fun getAllLevelXp(level: Int): Int {
        var counter = 0
        for (t in xpList.list) {
            if(t.level == level)
            counter += t.xp
        }
        return counter
    }

    fun getAllPossibleLevelXp(level: Int): Int {
        var counter = 0
        for (t in testList.list) {
            if(t.level == level)
            counter += t.text.split(' ').size
        }
        return counter
    }

    fun addXp(level: Int, test: Int, xp: Int, activity: FragmentActivity) {
        var flag = false
        for (element in xpList.list)
            if (element.level == level && element.test == test) {
                element.xp = xp
                flag = true
                break
            }
        if (!flag)
            xpList.list.add(Xp(level, test, xp))
        saveXp(xpList, activity)
    }

    private fun saveXp(xpList: XpList, activity: FragmentActivity) {
        val gson = Gson()
        val json: String = gson.toJson(xpList)
        val sharedPref =
            activity.applicationContext.getSharedPreferences("superPrefs", Context.MODE_PRIVATE)
        val editor: Editor = sharedPref.edit()
        editor.putString("xp", json)
        editor.apply()
    }

    fun getLevel(): Int {
        val xp = getAllXp()
        return when {
            xp < 10 -> 1
            xp in 10..29 -> 2
            else -> 3
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: GsonParser? = null

        fun getInstance(activity: FragmentActivity): GsonParser =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: GsonParser(activity).also { INSTANCE = it }
            }
    }
}