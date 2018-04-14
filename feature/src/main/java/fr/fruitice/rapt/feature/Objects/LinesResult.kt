package fr.fruitice.rapt.feature.Objects

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class LinesResult(val result: Lines) {

    class Deserializer : ResponseDeserializable<LinesResult> {
        override fun deserialize(content: String) = Gson().fromJson(content, LinesResult::class.java)
    }

}