package fr.fruitice.trome.feature

import android.app.Application
import android.util.Log

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("Application", "init App")
    }
}