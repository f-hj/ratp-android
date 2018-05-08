package fr.fruitice.trome.feature.Objects

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class Destinations(val directions: Array<Destination>, val ambiguityMessage: String?) {
    class Deserializer : ResponseDeserializable<Destinations> {
        override fun deserialize(content: String) = Gson().fromJson(content, Destinations::class.java)
    }
}