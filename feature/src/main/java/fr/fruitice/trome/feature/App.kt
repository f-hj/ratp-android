package fr.fruitice.trome.feature

import android.app.Application
import android.util.Log
import com.raizlabs.android.dbflow.config.FlowManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("Application", "init FlowManager")
        FlowManager.init(this)
    }
}