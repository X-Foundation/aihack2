package com.example.aihack.utils

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.aihack.models.TestList
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
        val prefs = sharedPref.getString("xp", "{}").toString()
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
        if(xpList.list != null)
        for (t in xpList.list) {
            if (t.level == level && t.test == test) {
                return t.xp
            }
        }
        return null
    }

    fun saveXp(xpList: XpList, activity: FragmentActivity) {
        val gson = Gson()
        val json: String = gson.toJson(xpList)
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("xp", json)
            apply()
        }
    }

    companion object {
        val instance = GsonParser()
    }
}