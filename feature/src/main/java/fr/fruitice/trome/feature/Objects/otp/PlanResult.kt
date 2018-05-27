package fr.fruitice.trome.feature.Objects.otp

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class PlanResult(val plan: Plan) {
    class Deserializer : ResponseDeserializable<PlanResult> {
        override fun deserialize(content: String) = Gson().fromJson(content, PlanResult::class.java)
    }
}