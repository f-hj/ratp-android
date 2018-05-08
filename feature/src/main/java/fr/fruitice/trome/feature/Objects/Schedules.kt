package fr.fruitice.trome.feature.Objects

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class Schedules(val missions: Array<Mission>, val perturbations: Array<Perturbation>) {
    class Deserializer : ResponseDeserializable<Schedules> {
        override fun deserialize(content: String) = Gson().fromJson(content, Schedules::class.java)
    }

    fun isSameDestination(): Boolean {
        var destination = ""
        missions?.forEach { mission: Mission ->
            if (mission.stations.isNotEmpty()) {
                val currentDestination = mission.stations[mission.stations.size -1].id
                if (destination.isBlank()) {
                    destination = currentDestination
                } else if (destination != currentDestination) {
                    return false
                }
            }
        }
        return true
    }
}