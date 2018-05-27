package fr.fruitice.trome.feature.Objects.ratp

import android.util.Log
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class Stations(val stations: Array<Station>, val ambiguityMessage: String?) {
    class Deserializer : ResponseDeserializable<Stations> {
        override fun deserialize(content: String) = Gson().fromJson(content, Stations::class.java)
    }

    fun filterByName() : ArrayList<Station> {
        val final: ArrayList<Station> = ArrayList()
        val names: ArrayList<String> = ArrayList()

        stations.forEach {
            Log.d("test", it.name)
            if (names.contains(it.name)) return@forEach
            names.add(it.name)
            final.add(it)
        }

        return final
    }
}