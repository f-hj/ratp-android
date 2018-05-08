package fr.fruitice.trome.feature.Objects

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class Stations(val stations: Array<Station>, val ambiguityMessage: String?) {
    class Deserializer : ResponseDeserializable<Stations> {
        override fun deserialize(content: String) = Gson().fromJson(content, Stations::class.java)
    }
}