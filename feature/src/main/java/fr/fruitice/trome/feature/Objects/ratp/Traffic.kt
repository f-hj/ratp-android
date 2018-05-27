package fr.fruitice.trome.feature.Objects.ratp

class Traffic(val slug: String, val title: String, val message: String) {
    fun isNormal(): Boolean {
        return slug == "normal"
    }
}