package fr.fruitice.trome.feature

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.app.Fragment
import android.arch.persistence.room.Room
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.fuel.httpGet
import fr.fruitice.trome.feature.Adapters.LinesAdapter
import fr.fruitice.trome.feature.Objects.ratp.Line
import fr.fruitice.trome.feature.Objects.ratp.Lines
import fr.fruitice.trome.feature.Objects.trome.AppDatabase
import kotlinx.android.synthetic.main.fragment_lines.view.*

class LinesFragment : Fragment(), LinesAdapter.OnItemClickListener {
    var mAdapter: LinesAdapter? = null

    override fun onItemClicked(type: Lines.Type?, line: Line?) {
        val intent = Intent(context, LineActivity::class.java)
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lines, container, false)!!

        // Creates a vertical Layout Manager
        view.lines_recycler.layoutManager = LinearLayoutManager(context)

        "https://wsiv-api.trome.app/prettyLines".httpGet().responseObject(Lines.Deserializer()) { _, _, result ->
            Log.d("res", "getted")
            Log.d("res", result.toString())

            val (res, err) = result

            if (err != null) {
                Snackbar.make(container!!, err.response.responseMessage, Snackbar.LENGTH_LONG).show()
                return@responseObject
            }

            // Access the RecyclerView Adapter and load the data into it
            mAdapter = LinesAdapter(res!!, context!!)
            mAdapter!!.onItemClickListener = this

            view.lines_recycler.adapter = mAdapter

            val db = Room
                    .databaseBuilder(context, AppDatabase::class.java, "database-name")
                    .build()

            for (i in 0..res.getSize()) {
                db.lineDao().insert(res.getItem(i)!!)
            }
        }

        return view
    }

}