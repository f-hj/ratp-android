package fr.fruitice.trome.feature

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.github.kittinunf.fuel.httpGet
import fr.fruitice.trome.feature.Objects.ratp.Mission
import fr.fruitice.trome.feature.Objects.ratp.Schedules
import kotlinx.android.synthetic.main.activity_station.*
import kotlinx.android.synthetic.main.content_station.*
import java.util.*
import fr.fruitice.trome.feature.Config.Favorite
import fr.fruitice.trome.feature.Objects.ratp.Line


class StationActivity : AppCompatActivity() {

    var lineType: String? = null
    var lineCode: String? = null
    var lineId: String? = null
    var way: String? = null
    var stationSlug: String? = null
    var stationId: String? = null
    var stationAreaId: String? = null

    var done: Boolean = false

    var interval: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lineCode = intent.getStringExtra("lineCode")

        val line: Line = Line()
        val styleId = line.getStyleId(lineCode!!)
        setTheme(styleId)

        setContentView(R.layout.activity_station)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Log.d("StationActivity", "lineType: $lineType, lineCode: $lineCode, way: $way, stationId: $stationId")
            val fav = Favorite(lineType!!, lineCode!!, way!!, stationId!!)

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Log.d("title", intent.getStringExtra("stationName"))

        lineType = intent.getStringExtra("lineType")
        lineId = intent.getStringExtra("lineId")
        way = intent.getStringExtra("way")
        stationId = intent.getStringExtra("stationId")
        stationAreaId = intent.getStringExtra("stationAreaId")

        toolbar_layout.title = intent.getStringExtra("stationName")
        toolbar_layout.subtitle = intent.getStringExtra("lineName") + " → " + intent.getStringExtra("wayName")

        srl_station.setOnRefreshListener { -> refresh() }

        refresh()
    }

    override fun onResume() {
        super.onResume()

        interval = Timer()
        interval!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (done) refresh()
                }
            }
        }, 4000, 4000)
    }

    override fun onPause() {
        super.onPause()

        interval?.cancel()
    }

    private fun refresh() {
        done = false
        srl_station.isRefreshing = true

        Log.d("req", "https://rapt-api.kiwi.fruitice.fr/lines/$lineCode/stations/$stationId/$way/next")
        "https://rapt-api.kiwi.fruitice.fr/lines/$lineCode/stations/$stationId/$way/next".httpGet().responseObject(Schedules.Deserializer()) { _, _, result ->
            val (res, err) = result
            if (err != null) {
                Snackbar.make(activity_station, err.response.responseMessage, Snackbar.LENGTH_LONG).show()
                return@responseObject
            }
            var text: String = ""
            val sameDestination = res?.isSameDestination()
            res?.missions?.forEach { mission: Mission ->
                mission.stationsMessages.forEach {
                    text += it
                }
                mission.stationsPlatforms.forEach {
                    text += " $it"
                }
                if (mission.stations.isNotEmpty() && !sameDestination!!) {
                    val lastStation = mission.stations[mission.stations.size -1]
                    text += " →  ${lastStation.name} (${lastStation.id})"
                }
                text += "\n"
            }

            res?.perturbations?.forEach {
                text += "${it.level}: ${it.message?.text}\n\n"
            }

            way_a_schedule.text = text

            srl_station.isRefreshing = false
            done = true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return false
    }
}
