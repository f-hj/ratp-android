package fr.fruitice.trome.feature

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_search.*
import com.github.kittinunf.fuel.httpGet
import fr.fruitice.trome.feature.Objects.otp.Search
import fr.fruitice.trome.feature.Objects.ratp.Station
import fr.fruitice.trome.feature.Objects.ratp.Stations
import fr.fruitice.trome.features.StationsAdapter
import kotlinx.android.synthetic.main.activity_line.*
import kotlinx.android.synthetic.main.content_search.*


class SearchActivity : AppCompatActivity(), StationsAdapter.OnItemClickListener {

    var stations: ArrayList<Station>? = null
    var mAdapter: StationsAdapter? = null

    override fun onItemClicked(station: Station) {
        val myIntent = Intent()
        var id = ""
        if (station.geoPointA != null) {
            id = station.geoPointA.id!!
        } else if (station.geoPointR != null) {
            id = station.geoPointR.id!!
        }
        myIntent.putExtra("id", id)
        myIntent.putExtra("name", station.name)

        setResult(Activity.RESULT_OK, myIntent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("search", s.toString())
                getStations(s.toString())
            }
        })
    }

    fun getStations(query: String) {
        if (query.isEmpty()) return

        Log.d("searchUrl", "https://ratp-go.dev.fruitice.fr/stations/search/$query")
        "https://ratp-go.dev.fruitice.fr/stations/search/$query".httpGet().responseObject(Stations.Deserializer()) { _, _, result ->
            Log.d("res", "getted")
            Log.d("res", result.toString())

            val (res, err) = result

            // Creates a vertical Layout Manager
            search_recycler.layoutManager = LinearLayoutManager(this)

            if (err != null) {
                Snackbar.make(activity_line, err.response.responseMessage, Snackbar.LENGTH_LONG).show()
                return@responseObject
            }

            if (!res?.ambiguityMessage.isNullOrBlank()) {
                Snackbar.make(activity_line, res?.ambiguityMessage!!, Snackbar.LENGTH_LONG).show()
                return@responseObject
            }

            if (res?.stations == null || res.stations.isEmpty()) return@responseObject

            // Access the RecyclerView Adapter and load the data into it
            stations = res.filterByName()
            Log.d("res", stations.toString())
            mAdapter = StationsAdapter(stations!!.toTypedArray())
            mAdapter!!.onItemClickListener = this

            search_recycler.adapter = mAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
                return true
            }
        }
        return false
    }

}
