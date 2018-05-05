package fr.fruitice.rapt.feature

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.github.kittinunf.fuel.httpGet
import fr.fruitice.rapt.feature.Objects.*
import fr.fruitice.rapt.feature.R.layout.activity_line
import fr.fruitice.rapt.features.StationsAdapter
import kotlinx.android.synthetic.main.activity_line.*
import kotlinx.android.synthetic.main.activity_station.*
import android.R.attr.data
import android.app.PendingIntent.getActivity
import android.util.TypedValue


class LineActivity : AppCompatActivity(), StationsAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {

    var destinations: Array<Destination>? = null
    var currentWay = "A"
    var currentWayName: String? = null

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("nothingSelected", "really nothing")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("itemSelected", "yes, this is $position")
        currentWayName = destinations?.get(position)?.name

        Log.d("currentWay", currentWay)
        if (currentWay != destinations?.get(position)?.sens ?: "A") {
            currentWay = destinations?.get(position)?.sens ?: "A"
            Log.d("reversing it", "to $currentWay")
            stations?.reverse()
            mAdapter?.notifyDataSetChanged()
        }
    }


    private var lineType: String? = null
    private var lineCode: String? = null
    private var lineName: String? = null
    private var lineId: String? = null

    override fun onItemClicked(station: Station) {
        val intent = Intent(this, StationActivity::class.java)
        intent.putExtra("lineType", lineType)
        intent.putExtra("lineCode", lineCode)
        intent.putExtra("lineId", lineId)
        intent.putExtra("lineName", lineName)
        intent.putExtra("way", currentWay)
        intent.putExtra("wayName", currentWayName)
        intent.putExtra("stationId", station.id)
        intent.putExtra("stationName", station.name)
        intent.putExtra("stationAreaId", station.stationArea?.id)
        startActivity(intent)
    }

    var stations: Array<Station>? = null
    var mAdapter: StationsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lineCode = intent.getStringExtra("lineCode")

        val line: Line = Line()
        val styleId = line.getStyleId(lineCode!!)
        setTheme(styleId)

        setContentView(R.layout.activity_line)
        setSupportActionBar(line_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lineType = intent.getStringExtra("lineType")
        lineId = intent.getStringExtra("lineId")
        lineName = intent.getStringExtra("lineName")

        supportActionBar?.title = lineName

        "https://rapt-api.kiwi.fruitice.fr/lines/$lineCode/directions".httpGet().responseObject(Destinations.Deserializer()) { _, _, result ->
            val (res, err) = result

            if (err != null) {
                Log.d("err", err.response.responseMessage)
                Snackbar.make(activity_line, err.response.responseMessage, Snackbar.LENGTH_LONG).show()
                return@responseObject
            }

            destinations = res?.directions
            currentWayName = destinations?.get(0)?.name
            val destsStrings = destinations?.map { r -> "→  ${r.name}" }

            val adapter = ArrayAdapter<String>(
                    this,
                    R.layout.direction_dropdown_item,
                    destsStrings
            )


            cmbToolbar.adapter = adapter
            cmbToolbar.onItemSelectedListener = this
        }

        "https://rapt-api.kiwi.fruitice.fr/lines/$lineCode/stations/$currentWay".httpGet().responseObject(Stations.Deserializer()) { _, _, result ->
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
            stations = res?.stations
            Log.d("res", stations.toString())
            mAdapter = StationsAdapter(stations!!)
            mAdapter!!.onItemClickListener = this

            stations_recycler.adapter = mAdapter
        }

        "https://rapt.kiwi.fruitice.fr/traffic/$lineType/$lineCode".httpGet().responseObject(TrafficResult.Deserializer()) { _, _, result ->
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
