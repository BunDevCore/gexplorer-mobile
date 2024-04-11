package com.bundev.gexplorer_mobile

import android.content.Context
import android.icu.util.MeasureUnit
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bundev.gexplorer_mobile.classes.Funi

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

val FIRST_TIME = booleanPreferencesKey("first_time")
val TOKEN = stringPreferencesKey("token")
val USERNAME = stringPreferencesKey("userName")
val USER_ID = stringPreferencesKey("userId")

val funi = Funi()
var distanceUnit: MeasureUnit = MeasureUnit.METER
var selectedTabSave = ""