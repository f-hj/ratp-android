package fr.fruitice.rapt.feature

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import fr.fruitice.rapt.feature.Adapters.LinesAdapter
import fr.fruitice.rapt.feature.Objects.Line
import fr.fruitice.rapt.feature.Objects.Lines
import fr.fruitice.rapt.feature.Objects.LinesResult
import kotlinx.android.synthetic.main.activity_line.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LinesAdapter.OnItemClickListener {
    var mAdapter: LinesAdapter? = null

    override fun onItemClicked(type: Lines.Type?, line: Line?) {
        val intent = Intent(this, LineActivity::class.java)
        intent.putExtra("lineId", line?.id)
        intent.putExtra("lineType", type?.type)
        intent.putExtra("lineCode", line?.code)
        intent.putExtra("lineName", line?.name)
        startActivity(intent)
    }

    override fun onSubheaderClicked(position: Int) {
        if (mAdapter?.isSectionExpanded(mAdapter?.getSectionIndex(position)!!)!!) {
            mAdapter?.collapseSection(mAdapter?.getSectionIndex(position)!!)
        } else {
            mAdapter?.expandSection(mAdapter?.getSectionIndex(position)!!)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lines -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        "https://api-ratp.pierre-grimaud.fr/v3/lines".httpGet().responseObject(LinesResult.Deserializer()) {_, _, result ->
            Log.d("res", "getted")
            Log.d("res", result.toString())

            val (res, err) = result

            if (err != null) {
                Snackbar.make(container, err.response.responseMessage, Snackbar.LENGTH_LONG).show()
                return@responseObject
            }

            // Creates a vertical Layout Manager
            lines_recycler.layoutManager = LinearLayoutManager(this)

            // Access the RecyclerView Adapter and load the data into it
            mAdapter = LinesAdapter(res?.result!!, this)
            mAdapter!!.onItemClickListener = this

            lines_recycler.adapter = mAdapter
        }
    }
}
