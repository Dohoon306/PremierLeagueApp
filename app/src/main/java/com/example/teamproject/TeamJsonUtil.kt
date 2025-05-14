package com.example.teamproject

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun loadJsonFromAssets(context: Context, fileName: String): String =
    context.assets.open(fileName).bufferedReader().use { it.readText() }

fun getTeamList(context: Context): List<Team> {
    val json = loadJsonFromAssets(context, "epl_teams.json")
    val type = object : TypeToken<List<Team>>() {}.type
    return Gson().fromJson(json, type)
}