package com.example.aihack.utils

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.aihack.models.TestList
import com.example.aihack.models.Xp
import com.example.aihack.models.XpList
import com.google.gson.Gson
import com.google.gson.stream.JsonReader

class GsonParser {
    private lateinit var testList: TestList
    private lateinit var xpList: XpList
    
    fun init(activity: FragmentActivity){
        val gson = Gson()
        val reader = JsonReader(activity.assets.open("tests.json").bufferedReader())
        testList = gson.fromJson(reader, TestList::class.java)
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
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

    fun addXp(level: Int, test: Int, xp: Int, activity: FragmentActivity) {
        xpList.list.add(Xp(level, test, xp))
        saveXp(xpList, activity)
    }

    private fun saveXp(xpList: XpList, activity: FragmentActivity) {
        val gson = Gson()
        val json: String = gson.toJson(xpList)
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("xp", json)
            commit()
        }
    }

    companion object {
        val instance = GsonParser()
    }
}