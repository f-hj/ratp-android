package fr.fruitice.rapt.feature.Objects

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class StationsResult(val result: Stations) {

    class Deserializer : ResponseDeserializable<StationsResult> {
        override fun deserialize(content: String) = Gson().fromJson(content, StationsResult::class.java)
    }

}