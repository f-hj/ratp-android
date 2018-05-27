package fr.fruitice.trome.feature.Objects.otp

class Itinerary(
        val duration: Int,
        val startTime: Long,
        val endTime: Long,
        val walkTime: Int,
        val transitTime: Int,
        val waitingTime: Int,
        val legs: Array<Leg>
) {
}