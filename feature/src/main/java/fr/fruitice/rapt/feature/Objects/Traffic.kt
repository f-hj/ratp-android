package fr.fruitice.rapt.feature.Objects

class Traffic(val slug: String, val title: String, val message: String) {
    fun isNormal(): Boolean {
        return slug == "normal"
    }
}