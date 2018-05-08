package fr.fruitice.trome.feature

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.fuel.httpGet
import fr.fruitice.trome.feature.Adapters.LinesAdapter
import fr.fruitice.trome.feature.Objects.Line
import fr.fruitice.trome.feature.Objects.Lines
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_lines.view.*

class ItineraryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_itinerary, container, false)!!

        return view
    }

}