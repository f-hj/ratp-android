package fr.fruitice.rapt.feature.Objects

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class Schedules(val missions: Array<Schedule>, val perturbations: Array<Perturbation>) {
    class Deserializer : ResponseDeserializable<Schedules> {
        override fun deserialize(content: String) = Gson().fromJson(content, Schedules::class.java)
    }
}