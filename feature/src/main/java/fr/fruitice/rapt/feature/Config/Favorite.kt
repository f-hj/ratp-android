package fr.fruitice.rapt.feature.Config

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import java.util.*

@Table(database = DB::class)
class Favorite() : BaseModel() {

    constructor(lineType: String, lineCode: String, way: String, stationSlug: String) : this() {
        this.lineType = lineType
        this.lineCode = lineCode
        this.way = way
        this.stationSlug = stationSlug

        this.id = "$lineType+$lineCode+$way+$stationSlug".toLowerCase()
    }

    @PrimaryKey
    internal var id: String? = null

    @Column
    internal var lineType: String? = null

    @Column
    internal var lineCode: String? = null

    @Column
    internal var way: String? = null

    @Column
    internal var stationSlug: String? = null

}
