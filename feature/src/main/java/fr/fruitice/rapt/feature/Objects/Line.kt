package fr.fruitice.rapt.feature.Objects

import android.graphics.Color
import fr.fruitice.rapt.feature.R

data class Line(val id: String, val code: String?, val name: String?, val directions: String?) {
    constructor(id: String) : this(id, null, null, null)

    fun getStyleId(): Int {
        return when (id) {
            "76" -> R.style.ratp_acacia
            "67" -> R.style.ratp_azur
            else -> R.style.ratp_vert
        }
    }
}