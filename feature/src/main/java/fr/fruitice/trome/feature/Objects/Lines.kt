package fr.fruitice.trome.feature.Objects

import android.util.Log
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Lines(val metros: Array<Line>, val rers: Array<Line>, val tramways: Array<Line>, val bus: Array<Line>, val noctiliens: Array<Line>) {

    enum class Type(val type: String) {
        METROS("metros"),
        RERS("rers"),
        TRAMWAYS("tramways"),
        //BUS("bus"),
        //NOCTILIENS("noctiliens")
    }

    fun getItem(position: Int): Line? {
        return when {
            position >= getLastPos(Type.TRAMWAYS) -> bus.get(position - getLastPos(Type.TRAMWAYS))
            position >= getLastPos(Type.RERS) -> tramways.get(position - getLastPos(Type.RERS))
            position >= getLastPos(Type.METROS) -> rers.get(position - getLastPos(Type.METROS))
            else -> metros.get(position)
        }
    }

    fun getType(position: Int): Type? {
        return when {
            //position >= getLastPos(Type.TRAMWAYS) -> Type.BUS
            position >= getLastPos(Type.RERS) -> Type.TRAMWAYS
            position >= getLastPos(Type.METROS) -> Type.RERS
            else -> Type.METROS
        }
    }

    fun getLastPos(type: Type): Int {
        return when (type) {
            Type.METROS -> metros.size
            Type.RERS -> getLastPos(Type.METROS) + rers.size
            Type.TRAMWAYS -> getLastPos(Type.RERS) + tramways.size
            //Type.BUS -> getLastPos(Type.TRAMWAYS) + bus.size
            //Type.NOCTILIENS -> getLastPos(Type.BUS) + noctiliens.size
        }
    }

    fun getSize(): Int {
        //return getLastPos(Type.BUS)
        return getLastPos(Type.TRAMWAYS)
    }

    class Deserializer : ResponseDeserializable<Lines> {
        override fun deserialize(content: String) = Gson().fromJson(content, Lines::class.java)
    }
}