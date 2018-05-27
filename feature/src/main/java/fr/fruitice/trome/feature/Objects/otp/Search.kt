package fr.fruitice.trome.feature.Objects.otp

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson


class Search(val stops: Array<SearchStop>?) {
    class Deserializer : ResponseDeserializable<Search> {
        override fun deserialize(content: String): Search? {
            return Search(Gson().fromJson(content, Array<SearchStop>::class.java))
        }
    }
}