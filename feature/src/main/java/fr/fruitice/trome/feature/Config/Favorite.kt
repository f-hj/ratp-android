package fr.fruitice.trome.feature.Config


class Favorite() {

    internal var id: String? = null

    internal var lineType: String? = null

    internal var lineCode: String? = null

    internal var way: String? = null

    internal var stationCode: String? = null

    constructor(lineType: String, lineCode: String, way: String, stationCode: String) : this() {
        this.lineType = lineType
        this.lineCode = lineCode
        this.way = way
        this.stationCode = stationCode

        this.id = "$lineType+$lineCode+$way+$stationCode".toLowerCase()
    }

}
