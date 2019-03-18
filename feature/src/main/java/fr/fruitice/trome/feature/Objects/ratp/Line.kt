package fr.fruitice.trome.feature.Objects.ratp

import fr.fruitice.trome.feature.R
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.util.Log

@Entity(tableName = "lines")
class Line {

    @PrimaryKey
    @ColumnInfo(name = "id")
    internal var id: String = ""
    fun getId(): String {
        return this.id
    }
    fun setId(id: String) {
        this.id = id
    }

    @ColumnInfo(name = "code")
    internal var code: String? = null
    fun getCode(): String? {
        return this.code
    }
    fun setCode(code: String?) {
        this.code = code
    }

    @ColumnInfo(name = "name")
    internal var name: String? = null
    fun getName(): String? {
        return this.name
    }
    fun setName(name: String?) {
        this.name = name
    }

    @Ignore
    internal var reseau: Reseau? = null

    @ColumnInfo(name = "computed_code")
    internal var computedCode: String? = null
    fun setComputedCode(computedCode: String?) {
        this.computedCode = computedCode
    }

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
            "BT1" -> R.style.ratp_azur
            "BT2" -> R.style.ratp_parme
            "BT3a" -> R.style.ratp_orange
            "BT3b" -> R.style.ratp_prairie
            "BT5" -> R.style.ratp_iris
            "BT6" -> R.style.ratp_coquelicot
            "BT7" -> R.style.ratp_marron
            "BT8" -> R.style.ratp_olive
            else -> R.style.ratp_vert
        }
    }

    fun getStyleId(): Int {
        return getStyleId(getComputedCode()!!)
    }

    fun getComputedCode(): String? {
        // WTF? I need an explanation for that...
        if (this.computedCode != null) {
            Log.d("line", "code already calculated")
            return this.computedCode
        }
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