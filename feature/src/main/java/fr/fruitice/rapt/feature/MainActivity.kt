package fr.fruitice.rapt.feature

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import fr.fruitice.rapt.feature.Adapters.LinesAdapter
import fr.fruitice.rapt.feature.Objects.Line
import fr.fruitice.rapt.feature.Objects.Lines
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LinesAdapter.OnItemClickListener {
    var mAdapter: LinesAdapter? = null

    override fun onItemClicked(type: Lines.Type?, line: Line?) {
        val intent = Intent(this, LineActivity::class.java)
        intent.putExtra("lineId", line?.id)
        intent.putExtra("lineType", type?.type)
        intent.putExtra("lineCode", line?.getComputedCode())
        intent.putExtra("lineName", line?.getComputedName())
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

        "https://rapt-api.kiwi.fruitice.fr/prettyLines".httpGet().responseObject(Lines.Deserializer()) {_, _, result ->
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
            mAdapter = LinesAdapter(res!!, this)
            mAdapter!!.onItemClickListener = this

            lines_recycler.adapter = mAdapter
        }
    }
}
