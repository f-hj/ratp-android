package fr.fruitice.trome.feature.Objects.otp

class Leg(
        val startTime: Long,
        val endTime: Long,
        val mode: String,
        val route: String,
        val from: SearchStop,
        val to: SearchStop
) {
}