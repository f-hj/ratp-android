package fr.fruitice.rapt.feature

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.github.kittinunf.fuel.httpGet
import fr.fruitice.rapt.feature.Objects.Lines
import fr.fruitice.rapt.feature.Objects.Schedule
import fr.fruitice.rapt.feature.Objects.Schedules
import fr.fruitice.rapt.feature.Objects.SchedulesResult
import fr.fruitice.rapt.feature.R.layout.activity_station
import kotlinx.android.synthetic.main.activity_line.*
import kotlinx.android.synthetic.main.activity_station.*
import kotlinx.android.synthetic.main.content_station.*
import java.util.*
import android.os.AsyncTask.execute



class StationActivity : AppCompatActivity() {

    var lineType: String? = null
    var lineCode: String? = null
    var stationSlug: String? = null

    var done: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Log.d("title", intent.getStringExtra("stationName"))

        toolbar_layout.title = intent.getStringExtra("stationName")
        toolbar_layout.subtitle = intent.getStringExtra("lineName")

        lineType = intent.getStringExtra("lineType")
        lineCode = intent.getStringExtra("lineCode")
        stationSlug = intent.getStringExtra("stationSlug")

        srl_station.setOnRefreshListener { -> refresh() }

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread { refresh() }
            }
        }, 0, 4000)
    }

    private fun refresh() {
        done = 0
        srl_station.isRefreshing = true

        "https://api-ratp.pierre-grimaud.fr/v3/schedules/$lineType/$lineCode/$stationSlug/A".httpGet().responseObject(SchedulesResult.Deserializer()) { _, _, result ->
            val (res, err) = result
            if (err != null) {
                Snackbar.make(activity_station, err.response.responseMessage, Snackbar.LENGTH_LONG).show()
                return@responseObject
            }
            showResults("A", res?.result?.schedules!!)
        }

        "https://api-ratp.pierre-grimaud.fr/v3/schedules/$lineType/$lineCode/$stationSlug/R".httpGet().responseObject(SchedulesResult.Deserializer()) { _, _, result ->
            val (res, err) = result
            if (err != null) {
                Snackbar.make(activity_station, err.response.responseMessage, Snackbar.LENGTH_LONG).show()
                return@responseObject
            }
            showResults("R", res?.result?.schedules!!)
        }
    }

    private fun showResults(way: String, schedules: Array<Schedule>) {
        done++

        if (done == 2) {
            srl_station.isRefreshing = false
        }

        if (lineType != "rers") {
            when (way) {
                "A" -> way_a.text = schedules.get(0).destination
                "R" -> way_b.text = schedules.get(0).destination
            }
        }
        var text: String = ""
        schedules.forEach { schedule: Schedule ->
            if (lineType == "rers") {
                text += schedule.message + " (" + schedule.destination + ")\n"
            } else {
                text += schedule.message + "\n"
            }
        }
        when (way) {
            "A" -> way_a_schedule.text = text
            "R" -> way_b_schedule.text = text
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
