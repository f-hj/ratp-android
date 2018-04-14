package fr.fruitice.rapt.feature

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import com.github.kittinunf.fuel.httpGet
import fr.fruitice.rapt.feature.Objects.*
import fr.fruitice.rapt.feature.R.layout.activity_line
import fr.fruitice.rapt.features.StationsAdapter
import kotlinx.android.synthetic.main.activity_line.*
import kotlinx.android.synthetic.main.activity_station.*

class LineActivity : AppCompatActivity(), StationsAdapter.OnItemClickListener {

    private var lineType: String? = null
    private var lineCode: String? = null
    private var lineName: String? = null

    override fun onItemClicked(station: Station) {
        val intent = Intent(this, StationActivity::class.java)
        intent.putExtra("lineType", lineType)
        intent.putExtra("lineCode", lineCode)
        intent.putExtra("lineName", lineName)
        intent.putExtra("stationSlug", station.slug)
        intent.putExtra("stationName", station.name)
        startActivity(intent)
    }

    var mAdapter: StationsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val line: Line = Line(intent.getStringExtra("lineId"))
        //setTheme(line.getStyleId())

        setContentView(R.layout.activity_line)

        lineType = intent.getStringExtra("lineType")
        lineCode = intent.getStringExtra("lineCode")
        lineName = intent.getStringExtra("lineName")

        title = lineName

        "https://api-ratp.pierre-grimaud.fr/v3/stations/$lineType/$lineCode".httpGet().responseObject(StationsResult.Deserializer()) { _, _, result ->
            Log.d("res", "getted")
            Log.d("res", result.toString())

            val (res, err) = result

            // Creates a vertical Layout Manager
            stations_recycler.layoutManager = LinearLayoutManager(this)

            if (err != null) {
                Snackbar.make(activity_line, err.response.responseMessage, Snackbar.LENGTH_LONG).show()
                return@responseObject
            }

            // Access the RecyclerView Adapter and load the data into it
            mAdapter = StationsAdapter(res?.result!!.stations)
            mAdapter!!.onItemClickListener = this

            stations_recycler.adapter = mAdapter
        }

        "https://api-ratp.pierre-grimaud.fr/v3/traffic/$lineType/$lineCode".httpGet().responseObject(TrafficResult.Deserializer()) { _, _, result ->
            val (res, err) = result

            val traffic: Traffic? = res?.result
            Log.d("traffic", "show snackbar")
            traffic?.message?.let { Snackbar.make(activity_line, it, Snackbar.LENGTH_LONG).show() }
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
