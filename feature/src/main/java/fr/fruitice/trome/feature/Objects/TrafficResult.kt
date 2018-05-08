package fr.fruitice.trome.feature.Objects

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class TrafficResult(val result: Traffic) {

    class Deserializer : ResponseDeserializable<TrafficResult> {
        override fun deserialize(content: String) = Gson().fromJson(content, TrafficResult::class.java)
    }
}