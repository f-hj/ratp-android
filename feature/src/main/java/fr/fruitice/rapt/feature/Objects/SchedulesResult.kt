package fr.fruitice.rapt.feature.Objects

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class SchedulesResult(val result: Schedules) {

    class Deserializer : ResponseDeserializable<SchedulesResult> {
        override fun deserialize(content: String) = Gson().fromJson(content, SchedulesResult::class.java)
    }
}