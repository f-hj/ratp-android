package fr.fruitice.trome.feature.Objects.ratp

import fr.fruitice.trome.feature.R

data class Line(val id: String, val code: String?, val name: String?, val reseau: Reseau?) {
    constructor(id: String) : this(id, null, null, null)
    constructor() : this("", null, null, null)

    fun getStyleId(uCode: String): Int {
        return when (uCode) {
            "M1" -> R.style.ratp_boutonor
            "M2" -> R.style.ratp_azur
            "M3" -> R.style.ratp_olive
            "M3B" -> R.style.ratp_prairie
            "M4" -> R.style.ratp_parme
            "M5" -> R.style.ratp_orange
            "M6" -> R.style.ratp_menthe
            "M7" -> R.style.ratp_rose
            "M7B" -> R.style.ratp_menthe
            "M8" -> R.style.ratp_lilas
            "M9" -> R.style.ratp_acacia
            "M10" -> R.style.ratp_ocre
            "M11" -> R.style.ratp_marron
            "M12" -> R.style.ratp_sapin
            "M13" -> R.style.ratp_pervenche
            "M14" -> R.style.ratp_iris
            "RA" -> R.style.ratp_coquelicot
            "RB" -> R.style.ratp_cobalt
            "RC" -> R.style.ratp_ocre
            "RD" -> R.style.ratp_prairie
            "RE" -> R.style.ratp_fuchsia
            else -> R.style.ratp_vert
        }
    }

    fun getStyleId(): Int {
        return getStyleId(getComputedCode()!!)
    }

    fun getComputedCode(): String? {
        // WTF? I need an explanation for that...
        return when (reseau?.code) {
            "metro" -> "M${code?.toUpperCase()}"
            "rer" -> "R$code"
            "tram" -> "B$code" // Gne?
            else -> code
        }
    }

    fun getComputedName(): String? {
        return "${reseau?.name} $code"
    }
}